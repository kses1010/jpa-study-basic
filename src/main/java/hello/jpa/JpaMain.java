package hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // coder
        try {

            Member member = new Member();
            member.setName("abc");
            em.persist(member);

            em.flush();
            em.clear();

            Member m1 = em.getReference(Member.class, member.getId());
            System.out.println("m1 : " +m1.getClass());

            Member findM1 = em.find(Member.class, member.getId());
            System.out.println("m2 : " + findM1.getClass());

            System.out.println("m1 == findByProxy " + (m1 == findM1));

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
