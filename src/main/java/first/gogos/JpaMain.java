package first.gogos;

import first.gogos.domain.Member;

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
        try {

            Member member = new Member();
            member.setEmail("member@naver.com");
            member.setNickname("doolyman");
            member.setJoindate();
            member.setPassword("kk00k0k0");

            em.persist(member);

            em.flush();
            em.clear();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

//페이징 하는거.
//            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(20)
//                    .getResultList();
//            System.out.println("result.size() = " + result.size());
//            for (Member member : result) {
//                System.out.println("member = " + member.getUsername());
//            }

//
//                //여러 값(여러 타입) 한번에 조회하기. MemberDTO를 만들어서 변수를 똑같이 지정해주고, 순서와 타입이 일치하는 생성자 필요하고, 풀패키지 적어줘야함.
//            List<MemberDTO> result = em.createQuery("select new jpabook.jpashop.jpqlpractice.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
//                    .getResultList();
//
//            MemberDTO memberDto = result.get(0);
//            System.out.println("memberDto = " + memberDto.getAge());
//
//
//            em.createQuery("select distinct m.username, m.age from Member m")
//                    .getResultList(); //이렇게 그냥 기본형 가져오는걸 스칼라 프로젝션이라함.
//                              // 아까와 같이 기본형 가져올떈 뒤에 .class (타입) 생략
//            em.createQuery("select o.address from Order o", Address.class)
//                    .getResultList(); //임베디드 타입 가져오는거 임베디드 프로젝션
//
//
//
//            //createQuery로 가져오면 영속성컨텍스트에서 관리함.
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();
//
//            Member findMember = result.get(0);
//            findMember.setAge(20); //이게 수정 되면 영속성컨텍스트에 있다는거
//
//
//                            //이름 파라미터 바인딩으로 find하는방법.
//            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", "member")
//                    .getSingleResult();
//            System.out.println("result = " + result.getAge());
//
//
//            TypedQuery<Member> query3 = em.createQuery("select m from Member m", Member.class);
//
//            //반환값이 무조건 하나일 때는 query3.getSingleResult();. 반환타입은 Member타입그대로.
//            //Spring Data JPA -> 결과가 하나짜리 메서드에서 결과가 없으면, null반환 혹은 Optional반환.
//            List<Member> resultList = query3.getResultList();
//
//            for (Member member8 : resultList) {
//                System.out.println("member1 = " + member8.getAge());
//                System.out.println("member8 = " + member8.getUsername());
//
//            }
//
//            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//            //위는 쿼리결과로 Member라는 타입의 객체를 통으로 가져옴, JPQL뒤에 Member.class라고 명시
//            Query query1 = em.createQuery("select m.username, m.age from Member m");
////            이거는 쿼리의 결과로 m.username(String), m.age(int)를 가져오기 때문에 쿼리 뒤에 타입 명시도 불가능하고
////            받는것도 TypedQuery가 아닌 Query로 받아야 된다.
