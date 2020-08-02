package hello.jpa;

import hello.jpa.domain.Member;
import hello.jpa.dto.MemberDto;

import javax.persistence.*;
import java.awt.print.Pageable;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // code
        try {
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<Member> result = em.createQuery(
                    "select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(20)
                    .getResultList();

            System.out.println(result.size());

            for (Member m : result) {
                System.out.println("member : " + m);
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
