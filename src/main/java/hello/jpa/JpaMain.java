package hello.jpa;

import hello.jpa.domain.Member;
import hello.jpa.domain.MemberType;
import hello.jpa.domain.Team;

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
            team.setName("A");
            em.persist(team);

            Member member = new Member();
//            member.setUsername("member");
            member.setAge(10);
            member.changeTeam(team);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();

            String query  = "select coalesce(m.username, '이름 없는 회원') from Member m";
            List<String> list = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : list) {
                System.out.println("s = " + s);
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
