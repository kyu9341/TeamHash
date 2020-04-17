package hellojpa;

import javax.persistence.*;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        // 트랜잭션 얻기
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Member member = new Member();

            member.setId(2L);
            member.setName("HelloB");
            // 저장
            em.persist(member);
            // 커밋
            tx.commit();
        } catch (Exception e){ // 문제가 생긴경우
            tx.rollback(); // 롤백
        } finally { // 작업이 끝나면
            em.close();
        }
        emf.close();

    }
}
