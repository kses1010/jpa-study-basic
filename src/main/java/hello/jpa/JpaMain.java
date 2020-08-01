package hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // code
        try {
            Member member = new Member();
            member.setName("abc");
            member.setHomeAddress(new Address("HomeCity1", "street1", "1010"));

            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("라면");

            member.getAddressHistory().add(new Address("old1", "street1", "1010"));
            member.getAddressHistory().add(new Address("old2", "street2", "1222"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("###########################");
            Member findMember = em.find(Member.class, member.getId());

            // homeCity -> newCity
//            findMember.getHomeAddress().setCity("newCity");
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            //
            findMember.getAddressHistory().remove(new Address("old1", "street1", "1010"));
            findMember.getAddressHistory().add(new Address("newCity1", "street1", "1010"));

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
