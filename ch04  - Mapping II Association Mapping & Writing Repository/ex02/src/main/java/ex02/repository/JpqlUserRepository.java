package ex02.repository;

import ex02.domain.User;
import ex02.domain.dto.UserDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@RequiredArgsConstructor
@Repository
public class JpqlUserRepository {

    @NonNull
    private EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findById(Integer id) {
        return em.find(User.class, id);
    }

    public User findByIdInJpql(Integer id) {
        String jpql = "select u from User u where u.id=:id";
        TypedQuery<User> query = em.createQuery(jpql, User.class);

        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public UserDto findByEmailAndPassword(String email, String password) {
        String jpql = "select new ex02.domain.dto.UserDto(u.id, u.name) from User u where u.email=:email and u.password=:password";
        TypedQuery<UserDto> query = em.createQuery(jpql, UserDto.class);

        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getSingleResult();
    }

    public User update(User argUser) {
        User user = em.find(User.class, argUser.getId());

        if (user != null) {
            user.setRole(argUser.getRole());
            user.setGender(argUser.getGender());
            user.setEmail(argUser.getEmail());
            user.setName(argUser.getName());
            user.setPassword(argUser.getPassword());
        }

        return user;
    }

    public Integer update02(User user) {
        String jpql = "update User u set u.role=:role, u.gender=:gender, u.email=:email, u.name=:name, u.password=:password where u.id=:id";
        Query query = em.createQuery(jpql);

        query.setParameter("id", user.getId());
        query.setParameter("role", user.getRole());
        query.setParameter("gender", user.getGender());
        query.setParameter("email", user.getEmail());
        query.setParameter("name", user.getName());
        query.setParameter("password", user.getPassword());
        return query.executeUpdate();
    }

    public void deleteById(Integer id) {
        User user = em.find(User.class, id);
        em.remove(user);
    }

    public Integer deleteByIdInJpql(Integer id) {
        String jpql = "delete from User u where u.id=:id";
        Query query = em.createQuery(jpql);

        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public Long count() {
        String jpql = "select count(u) from User u";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);

        return query.getSingleResult();
    }
}
