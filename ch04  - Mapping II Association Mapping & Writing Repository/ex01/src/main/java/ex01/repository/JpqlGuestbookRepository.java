package ex01.repository;

import ex01.domain.Guestbook;
import ex01.domain.dto.GuestbookDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpqlGuestbookRepository {
    @PersistenceContext
    private EntityManager em;



    public List<Guestbook> findAll() {
        String jpql = "select gb from Guestbook gb";
        TypedQuery<Guestbook> query = em.createQuery(jpql, Guestbook.class);

        return query.getResultList();
    }

    public List<Guestbook> findAllOrderedByRegDateDesc() {
        String jpql = "select gb from Guestbook gb order by gb.regDate desc";
        TypedQuery<Guestbook> query = em.createQuery(jpql, Guestbook.class);

        return query.getResultList();
    }

    public List<GuestbookDto> findAllWithProjection() {
        String jpql = "select new ex01.domain.dto.GuestbookDto(gb.id, gb.name, gb.contents, gb.regDate) from Guestbook gb";
        TypedQuery<GuestbookDto> query = em.createQuery(jpql, GuestbookDto.class);

        return query.getResultList();
    }

    public Integer deleteByIdAndPassword(int id, String password) {
        String jpql = "delete from Guestbook gb where gb.id=:id and gb.password=:password";
        Query query = em.createQuery(jpql);

        query.setParameter("id", id);
        query.setParameter("password", password);
        return query.executeUpdate();
    }

    public Long count() {
        String jpql = "select count(*) from Guestbook";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);

        return query.getSingleResult();
    }
}