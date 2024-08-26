package jpa.utils;

import jpa.lambda.exception.Consumer;
import jpa.lambda.exception.Function;
import jpa.lambda.exception.Runnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
public class JpaUtils {
    private static final Set<String> entitiesValidated = new HashSet<>();

    public static <T> T copyFromTransient(T entity, T transientEntity) {
        validateEntity(entity.getClass());

        Stream.of(transientEntity.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .forEach(field -> Optional
                        .ofNullable(BeanUtils.getPropertyDescriptor(entity.getClass(), field.getName()))
                        .ifPresent(property -> Optional
                                .ofNullable(property.getReadMethod())
                                .ifPresent(getter -> Optional
                                        .ofNullable(property.getWriteMethod())
                                        .ifPresent(Consumer.THROWS(setter -> {

                                            log.info("<<<{}@{}: invoke:{}()>>>", transientEntity.getClass().getSimpleName(), transientEntity.hashCode(), getter.getName());

                                            Optional.ofNullable(getter.invoke(transientEntity)).ifPresentOrElse(Consumer.THROWS(value -> {
                                                log.info("<<<{}@{}: invoke:{}({})>>>", entity.getClass().getSimpleName(), entity.hashCode(), setter.getName(), value);
                                                setter.invoke(entity, value);
                                            }), Runnable.THROWS(() -> {
                                                if (field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).nullable()) {
                                                    log.info("<<<{}@{}: invoke:{}(null)>>>", entity.getClass().getSimpleName(), entity.hashCode(), setter.getName());
                                                    setter.invoke(entity, (Object) null);
                                                }
                                            }));
                                        })))));

        return entity;
    }

    public static <T> TypedQuery<T> queryProjectionFindById(EntityManager em, Class<?> entity, Class<T> dtoClass) {
        validateEntity(entity);

        String entityName = entity.getName();
        String entityAlias = entity.getSimpleName() + "_";
        String idFiledName = ID_FIELD(entity).getName();
        String dtoClassName = dtoClass.getName();

        String jpql = String.format("select new %s(%s) from %s %s where %s.%s=:%s", dtoClassName, String.join(",", Stream.of(dtoClass.getDeclaredFields()).map(Field::getName).toArray(String[]::new)), entityName, entityAlias, entityAlias, idFiledName, idFiledName);
        return em.createQuery(jpql, dtoClass);
    }

    public static <T> TypedQuery<T> queryProjectionFindAll(EntityManager em, Class<?> entity, Class<T> dtoClass) {
        validateEntity(entity);

        String dtoClassName = dtoClass.getName();
        String entityName = entity.getName();
        String entityAlias = entity.getSimpleName() + "_";

        String jpql = String.format("select new %s(%s) from %s %s", dtoClassName, String.join(",", Stream.of(dtoClass.getDeclaredFields()).map(Field::getName).toArray(String[]::new)), entityName, entityAlias);
        return em.createQuery(jpql, dtoClass);
    }

    public static <T> Query queryUpdate(EntityManager em, T entity) {
        Class<?> entityClass = entity.getClass();

        validateEntity(entityClass);

        List<String> fieldNames = Stream.of(entity.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> Optional
                        .ofNullable(BeanUtils.getPropertyDescriptor(entityClass, field.getName()))
                        .flatMap(property -> Optional
                                .ofNullable(property.getReadMethod())
                                .map(Function.THROWS(getter -> getter.invoke(entity) != null || field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).nullable())))
                        .orElse(false))
                .map(Field::getName)
                .toList();

        String entityName = entityClass.getName();
        String entityAlias = entityClass.getSimpleName() + "_";
        String idFiledName = ID_FIELD(entityClass).getName();

        Query query = em.createQuery(String.format("update %s %s set %s where %s.%s=:%s", entityName, entityAlias, String.join(",", fieldNames.stream().map(n -> String.format("%s_.%s=:%s", entity.getClass().getSimpleName(), n, n)).toArray(String[]::new)), entityAlias, idFiledName, idFiledName));
        fieldNames.forEach(field -> query.setParameter(field, Optional
                .ofNullable(BeanUtils.getPropertyDescriptor(entity.getClass(), field))
                .map(Function.THROWS(property -> property.getReadMethod().invoke(entity))).orElse(null)));
        query.setParameter(idFiledName, ID_VALUE(entity));

        return query;
    }

    private static Field ID_FIELD(Class<?> entityClass) {
        validateEntity(entityClass);

        return Stream.of(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Too many IDs");
                })
                .orElseThrow(() -> new IllegalStateException("Coud not found ID Field in Entity"));
    }

    private static <T> Object ID_VALUE(T entity) {
        Class<?> entityClass = entity.getClass();

        validateEntity(entityClass);

        return Optional.ofNullable(ID_FIELD(entityClass))
                .flatMap(field -> Optional
                        .ofNullable(BeanUtils.getPropertyDescriptor(entityClass, field.getName()))
                        .flatMap(property -> Optional
                                .ofNullable(property.getReadMethod())
                                .map(Function.THROWS(getter -> getter.invoke(entity)))))
                .orElseThrow(() -> new IllegalStateException("Coud not found ID value"));
    }

    private static void validateEntity(Class<?> entityClass) {
        String entityClassName = entityClass.getName();

        if (entitiesValidated.contains(entityClassName)) {
            log.info("<<<Validation Entity({}) Hits, Cach Size:{}>>>", entityClassName, entitiesValidated.size());
            return;
        }

        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Not an entity [" + entityClassName + "]");
        }

        entitiesValidated.add(entityClassName);
        log.info("<<<Validation Entity({}) Cached, Cach Size:{}>>>", entityClassName, entitiesValidated.size());
    }
}
