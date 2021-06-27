package jpql;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Slf4j
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            //공통 으로 사용

      /*      Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team team1 = new Team();
            team1.setName("teamB");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("teamC");
            em.persist(team2);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.changeTeam(team);

            em.persist(member);
            Member member1 = new Member();
            member1.setUsername("member2");
            member1.setAge(20);
            member1.changeTeam(team1);
            em.persist(member1);
*/
            /**
             * 반환 타입이 명확하면 TypeQuery
             * 반환 타입이 불명확하면 Query
             */


            /**
             * 반환이 여러개일경우
             * query.getResultList();
             * 
             * 결과가 없으면 빈 List반환
             */
           /* //타입정보가 있다면  타입쿼리를반환 제네릭<>
            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);//2번째인자는 타입(엔티티)
            List<Member> resultList = query.getResultList();*/

            /**
             * 반환이 하나일경우
             * query.getSingleResult();
             *
             * 주의점 !
             * 결과가 정확히 하나,단일 객체 반환해야함
             * 결과가 없으면 NoResultException 발생
             * 결과가 둘 이상이면 NonUniqueResultException 발생
             */
           /* //타입정보가 있다면  타입쿼리를반환 제네릭<>
            TypedQuery<Member> query = em.createQuery("select m from Member m where id = 1", Member.class);//2번째인자는 타입(엔티티)
            Member singleResult = query.getSingleResult();*/

            /**
             * 파라미터 바인딩 :
             */

     /*        Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username","member1")
                    .getSingleResult();

*/
          /* // username은 String ,age는 int 타입정보가 명확하지않음
            TypedQuery<Member> select_m_from_member = em.createQuery("select m.username,m.age from Member m");//2번째인자는 타입(엔티티)

            //값이 String 하나라면 String으로 타입 명시가능
            TypedQuery<String> query = em.createQuery("select m.username from Member m", String.class);
      */
            /**
             * 프로젝션이란
             * SELECT 절에 조회할 대상을 지정하는것.
             * 프로젝션 대상:엔티티, 임베디드 타입,스칼라 타입(숫자,문자 기본데이터 타입)
             */

            /**
             * -엔티티 타입 프로젝션-
             *  과연  List<Member>는 영속성 컨텍스트에 속해있을까? yes
             *  엔티티 프로젝션한 모든 셀렉트절  select m from Member m
             *  한개는 수십개든 영속성 컨텍스에 관리가 된다!
             */
           /* List<Member> result = em.createQuery("select m from Member m",Member.class)
                    .getResultList();
            Member findMember = result.get(0);
            findMember.setAge(120);
*/
            /**
             * -엔티티 타입 프로젝션-
             * 작성한 sql은 결과 sql과 비슷하게 쓰는게좋다.
             * 튜닝요소도 많고, 한눈에 보이는게 좋다
             */
            //맴버에 team을 찾는거라 조인을해서 찾아옴 그런데 아래 sql은 되도록이면 만들어진 sql과 비슷하게 써야한다..
            List<Team> resultTeam = em.createQuery("select t from Member m join m.team t",Team.class)
                    .getResultList();


            /**
             * -임베디드 타입 프로젝션-
             */

            em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            /**
             * -스칼라 타입 프로젝션-
             * 타입이 없기때문에 오브젝트로 돌려줌
             */
             em.createQuery("select distinct m.username,m.age from Member m")
                    .getResultList();

            /**
             * 프로젝션 -여러 값 조회
             */

            /**
             * 첫방법
             */
           /* List resultList = em.createQuery("select distinct m.username,m.age from Member m")
                    .getResultList();

            Object o = resultList.get(0);
            Object[] resultObject = (Object[]) o;
            System.out.println("username[0] = " + resultObject[0]);
            System.out.println("age[1] = " + resultObject[1]);*/
            /**
             * 두번째 방법 
             * 타입쿼리처럼 타입선언가능 스칼라 여러개라도
             */
           /* List<Object[]> resultList = em.createQuery("select distinct m.username,m.age from Member m")
                    .getResultList();

            Object[] resultOb = resultList.get(0);
            System.out.println("resultOb[0] = " + resultOb[0]);
            System.out.println("resultOb[1] = " + resultOb[1]);*/

            /**
             * 세번째방법 가장 깔끔
             * new 명령어로 조회
             *   -단순 값을 DTO로 바로 조회
             *    select new jpabook.spql.MemberDTO(m.username,m.age) From Member m
             *   -패키지 명을 포함한 전체 클래스명 입력 (문자기떄문에 패키지경로를 다적어줘야함)
             *   -순서와 타입이 일치하는 생성자 필요
             *
             */

           /* List<MemberDTO> resultDTO = em.createQuery("select new jpql.MemberDTO(m.username,m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = resultDTO.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());*/


            /**
             * 페이징
             */

            em.flush();
            em.clear();
            for(int i=0; i<100;i++){
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }
            List<Member> resultPaging = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();
/*
            log.info("result.size={}",result.size());
            for (Member member1 : resultPaging) {
                log.info("member1={}",member1);
            }*/
            /**
             * 조인
             */


            /**
             * INNER JOIN
             */
      /*
            em.flush();
            em.clear();
            String query = "select m from Member m inner join m.team t";
            List<Member> resultInnerJoin = em.createQuery(query, Member.class)
                    .getResultList();

            resultInnerJoin.stream().forEach(System.out::println);*/

            /**
             * OUTER LEFT JOIN
             */

      /*      em.flush();
            em.clear();
            String query = "select m from Member m LEFT join m.team t";
            List<Member> resultInnerJoin = em.createQuery(query, Member.class)
                    .getResultList();

            resultInnerJoin.stream().forEach(System.out::println);*/

            /**
             * 세타조인
             */
         /*   em.flush();
            em.clear();
            String query = "select m from Member m,Team t where m.username = t.name";
            List<Member> resultInnerJoin = em.createQuery(query, Member.class)
                    .getResultList();

            resultInnerJoin.stream().forEach(System.out::println);*/

            /**
             * 조인 -ON 절
             * 1.조인 대상 필터링
             * 2.연관관계 없는 엔티티 외부 조인
             */

            /**
             *  1.조인 대상 필터링
             */
       /*     em.flush();
            em.clear();
            String query = "select m from Member m LEFT JOIN m.team t ON t.id = 1";
            List resultList = em.createQuery(query).getResultList();

            resultList.stream().forEach(System.out::println);
            System.out.println("resultSize==" + resultList.size());
*/
            /**
             * * 2.연관관계 없는 엔티티 외부 조인
             */
/*

            em.flush();
            em.clear();
            String query = "select m from Member m LEFT JOIN Team t ON m.username =t.name";
            List<Member> resultList = em.createQuery(query, Member.class).getResultList();

            resultList.stream().forEach(System.out::println);
            System.out.println(resultList.size()+"사이즈");

*/


            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
    }


}
