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
            Team team = new Team();
            team.setTeamName("A");
            em.persist(team);

            Member member = new Member();
            member.setName("abc");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member findM1 = em.find(Member.class, member.getId());
            System.out.println(findM1.getTeam().getClass());

            System.out.println("==========================");
            System.out.println(findM1.getTeam());
            System.out.println("==========================");

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
