package hello.jpa;

import hello.jpa.domain.Member;
import hello.jpa.dto.MemberDto;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // code
        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);

            em.persist(member);

            List<MemberDto> result = em.createQuery(
                    "select new hello.jpa.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                    .getResultList();

            MemberDto memberDto = result.get(0);

            System.out.println("name : " + memberDto.getUsername());
            System.out.println("age : " + memberDto.getAge());

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
