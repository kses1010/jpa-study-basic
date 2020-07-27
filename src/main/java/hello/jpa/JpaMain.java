package hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // code
        try {
            Team team = new Team();
            team.setTeamName("A");
            em.persist(team);

            Team team2 = new Team();
            team.setTeamName("B");
            em.persist(team2);

            Member member = new Member();
            member.setName("abc");
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member.setName("def");
            member.setTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
