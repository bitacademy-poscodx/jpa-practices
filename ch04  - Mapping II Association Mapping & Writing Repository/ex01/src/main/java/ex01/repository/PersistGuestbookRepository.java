package ex01.repository;

import ex01.domain.Guestbook;
import jpa.utils.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Optional;

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
                .map(entity -> JpaUtils.copyFromTransient(entity, argGuestbook)).orElse(null);
//        return em.merge(argGuestbook);
    }

    public void delete(Integer id) {
        Optional.ofNullable(em.find(Guestbook.class, id)).ifPresent(entity -> em.remove(entity));
    }
}