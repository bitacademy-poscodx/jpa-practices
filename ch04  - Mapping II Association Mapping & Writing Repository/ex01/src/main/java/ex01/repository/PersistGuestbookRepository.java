package ex01.repository;

import ex01.domain.Guestbook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Repository
public class PersistGuestbookRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Guestbook guestbook) {
        em.persist(guestbook);
    }

    public Optional<Guestbook> find(Integer id) {
        return Optional.ofNullable(em.find(Guestbook.class, id));
    }

    public Guestbook update(Guestbook argGuestbook) {
        return Optional.ofNullable(em.find(Guestbook.class, argGuestbook.getId()))
                .map(entity -> copyFromTransient(entity, argGuestbook)).orElse(null);
//        return em.merge(guestbook);
    }

    public void delete(Integer id) {
        Optional.ofNullable(em.find(Guestbook.class, id)).ifPresent(entity -> em.remove(entity));
    }

    private Guestbook copyFromTransient(Guestbook entity, Guestbook transientEntity) {
        Stream.of(transientEntity.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(Id.class) == null)
                .filter(field -> field.getAnnotation(Transient.class) == null)
                .forEach(field -> Optional.ofNullable(BeanUtils.getPropertyDescriptor(Guestbook.class, field.getName()))
                        .ifPresent(propertyDescriptor -> {
                            Optional<Object> value = Optional.ofNullable(propertyDescriptor.getReadMethod()).map(getter -> {
                                try {
                                    log.info("<<<{}@{}: invoke:{}()>>>", entity.getClass().getSimpleName(), entity.hashCode(), getter.getName());
                                    return getter.invoke(transientEntity);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            if (value.isEmpty() && Optional.ofNullable(field.getAnnotation(Column.class)).map(column -> column.nullable() ? null : false).isPresent()) {
                                return;
                            }

                            Optional.ofNullable(propertyDescriptor.getWriteMethod()).ifPresent(setter -> {
                                try {
                                    log.info("<<<{}@{}: invoke:{}({})>>>", entity.getClass().getSimpleName(), entity.hashCode(), setter.getName(), value.orElse(null));
                                    setter.invoke(entity, value.orElse(null));
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }));

        return entity;
    }
}
