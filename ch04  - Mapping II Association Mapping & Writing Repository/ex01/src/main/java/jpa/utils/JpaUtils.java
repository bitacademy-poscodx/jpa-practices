package jpa.utils;

import ex01.domain.Guestbook;
import jpa.lambda.exception.Consumer;
import jpa.lambda.exception.Function;
import jpa.lambda.exception.Runnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class JpaUtils {

    public static <T> T copyFromTransient(T entity, T transientEntity) {
        validateEntity(entity);

        Stream.of(transientEntity.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .forEach(field -> Optional
                        .ofNullable(BeanUtils.getPropertyDescriptor(Guestbook.class, field.getName()))
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

    public static <T> Query createUpdateQuery(EntityManager em, T entity) {
        validateEntity(entity);

        List<String> fieldNames = Stream.of(entity.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> Optional
                        .ofNullable(BeanUtils.getPropertyDescriptor(entity.getClass(), field.getName()))
                        .flatMap(property -> Optional
                                .ofNullable(property.getReadMethod())
                                .map(Function.THROWS(getter -> getter.invoke(entity) != null || field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).nullable())))
                        .orElse(false))
                .map(Field::getName)
                .toList();

        Query query = em.createQuery(
                "update Guestbook gb set " +
                        String.join(",", fieldNames.stream().map(n -> String.format("_e.%s=:%s", n, n)).toArray(String[]::new)) +
                        " where _e.id=:id");

        fieldNames.forEach(field -> query.setParameter(field, Optional
                .ofNullable(BeanUtils.getPropertyDescriptor(entity.getClass(), field))
                .map(Function.THROWS(propertyDescriptor -> propertyDescriptor.getReadMethod().invoke(entity))).orElse(null)));

        query.setParameter("id", ID(entity));

        return query;
    }

    public static <T> Object ID(T entity) {
        validateEntity(entity);

        return Stream.of(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Too many IDs");
                })
                .flatMap(field -> Optional
                        .ofNullable(BeanUtils.getPropertyDescriptor(entity.getClass(), field.getName()))
                        .flatMap(property -> Optional
                                .ofNullable(property.getReadMethod())
                                .map(Function.THROWS(getter -> getter.invoke(entity)))))
                .orElseThrow(() -> new IllegalStateException("Coud Not ID's Value"));
    }

    private static <T> void validateEntity(T entity) {
        Class<?> entityClass = entity.getClass();

        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("Not a entity class");
        }
    }
}
