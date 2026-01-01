package com.really.good.sir.jpa;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

public class UserCriteriaExample {

    public static void main(String[] args) {
        EntityManager em = Persistence
                .createEntityManagerFactory("myPU-xml")
                .createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);

        Root<UserEntity> user = cq.from(UserEntity.class);

        cq.select(user)
                .where(cb.equal(user.get("email"), "alice@gmail.com"));

        List<UserEntity> users = em.createQuery(cq).getResultList();

        users.forEach(u -> System.out.println(u.getUserName()));

        em.close();
    }
}
