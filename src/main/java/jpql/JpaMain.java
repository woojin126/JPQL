package jpql;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Collection;
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

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);


            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);
/*

            Team teamC = new Team();
            teamC.setName("팀C");
            em.persist(teamC);


            Team teamD = new Team();
            teamD.setName("팀D");
            em.persist(teamD);

            Team teamE = new Team();
            teamE.setName("팀E");
            em.persist(teamE);

            Team teamF = new Team();
            teamF.setName("팀F");
            em.persist(teamF);*/

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setAge(10);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member7 = new Member();
            member7.setUsername("회원7");
            member7.setAge(10);
            member7.setTeam(teamB);
            em.persist(member7);


          Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(15);
            member2.setTeam(teamB);
            em.persist(member2);
  /*
            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(15);
            member3.setTeam(teamB);
            em.persist(member3);

            Member member4 = new Member();
            member4.setUsername("회원4");
            member4.setTeam(teamD);
            em.persist(member4);

            Member member5 = new Member();
            member5.setUsername("회원5");
            member5.setTeam(teamE);
            em.persist(member5);

            Member member6 = new Member();
            member6.setUsername("회원6");
            member6.setTeam(teamF);
            em.persist(member6);
*/
            em.flush();
            em.clear();
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
        /*    //맴버에 team을 찾는거라 조인을해서 찾아옴 그런데 아래 sql은 되도록이면 만들어진 sql과 비슷하게 써야한다..
            List<Team> resultTeam = em.createQuery("select t from Member m join m.team t",Team.class)
                    .getResultList();*/


            /**
             * -임베디드 타입 프로젝션-
             */

           /* em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();*/

            /**
             * -스칼라 타입 프로젝션-
             * 타입이 없기때문에 오브젝트로 돌려줌
             */
        /*     em.createQuery("select distinct m.username,m.age from Member m")
                    .getResultList();*/

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

          /*  em.flush();
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
                    .getResultList();*/
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

            /**
             * JPQL 타입표현
             * 문자 'HELLO'
             * 숫자 10L(Long), 10D(Double), 10F(Float)
             * Boolean : TRUE , FALSE
             * ENUM :jpabook.MemberType.Admin(패키지명 포함)
             * 엔티티 타입:TYPE(m) =Member(상속 관계에서 사용
             */

         /*   em.flush();
            em.clear();
            //    "where m.type = jpql.MemberType.ADMIN";
            String query = "select m.username, 'HELLO', true FROM Member m " +
                            "where m.type = :userType";
            List<Object[]> result = em.createQuery(query)
                    .setParameter("userType",MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : result) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
            }*/

            /**
             * 조건식 -기본 CASE 식
             */

/*
            em.flush();
            em.clear();

            String query =
                    "select " +
                            " case when m.age <= 10 then '학생요금' " +
                            "      when m.age >= 10 then '경로요금' " +
                            "      else  '일반요금' " +
                            " end " +
                    "from Member m";

            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
            }
*/

            /**
             * COALESCE : 하나씩 조회해서 NULL이 아니면 반환
             * NULLIF: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
             */

 /*           em.flush();
            em.clear();

            //사용자 이름이 없으면 이름없는 회원 반환
            //String query = "select coalesce(m.username,'이름없는 회원') as username from Member m";
            //사용자 이름이 '관리자'면 null을 반환하고 나머지는 본인 이름반환
            String query = "select nullif(m.username, '관리자') as username from Member m ";

            List<String> result = em.createQuery(query, String.class).getResultList();
            for (String s : result) {
                System.out.println("s = " + s);
            }*/

            /**
             * JPQL 기본 함수
             */
      /*      em.flush();
            em.clear();

            String query = "select concat('a','b') FROM Member m";
            String query1 = "select substring(m.username,2,3) FROM Member m";
            String query2 = "select locate('de','abcdefg') FROM Member m";//de위치번호 4번쨰
            String query3 = "select size(t.members) from Team t"; //t.members의 크기
            List<Integer> result = em.createQuery(query3,Integer.class)
                    .getResultList();

            for (Integer s : result) {
                System.out.println("s = " + s);
            }*/

            /**
             * 경로 표현식 특징
             * 상태필드(state field): 경로 탐색의 끝, 탐색x
             * 단일 값 연관 경로: 묵시적 내부조인(inner join)발생, 탐색O
             * 컬렉션 값 연관 경로 : 묵시적 내부조인 발생, 탐색X
             * from절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색가능
             *
             * 실무에서는 그냥 묵시적 조인 사용하지말아라!!!!!!!!!!!!!!!!!!!!!
             * 다 명시적으로 써라 (select m from Member m join m.team t) 이런식
             */

            /**
             * 경로 탐색을 사용한 묵시적 조인 시 주의사항
             * 1.항상 내부조인
             * 2.컬렉션은 경로탐색의 끝, 명시적 조인을 통해 별칭을 얻어야함
             * 3.경로 탐색은 주로 SELECT, WHERE절에서 사용하지만 묵시적 조인으로 인해
             * SQL의 FROM(JOIN)절에 영향을줌
             */


           /* //m.username은 사태필드, 더이상 타고들어갈 필드가없다 경로탐색의 끝
            String query = "select m.username from Member m ";

            //단일값 연관 경로 m.team은 묵시적 내부조인발생, 탐색 더가능 m.team -> m.team.name //여기까지와서는 다시 상태필드가됨 (탐색 끝)
            //m.team 객체에서는 이렇게 타고들어가면 되지만 디비에서는 JOIN을 발생시켜야함 그것임 묵시적 조인
            //주의:묵시적조인이 발생하지않도록 작성하는게좋음 실무에서는 수백개의 쿼리가잇음 (직관적으로 만들자는 뜻 운영이쉽게)
            String query1 = "select m.team from Member m ";
            List<Team> result = em.createQuery(query1,Team.class).getResultList();
            for (Team team3 : result) {

                System.out.println("team3 = " + team3);
            }*/

        /*    //컬렉션값 연관경로는 묵시적 내부조인 발생하지만 탐색은 x  -> m.members.username 이안됨 -> 해결책 (from절에 명시적인 조인사용
            // 컬렉션 자체값을 조회하는거기떄문에 더 이상 타고들어가기 불사
            String query2 = "select t.members from Team t";
            Collection result2 = em.createQuery(query2,Collection.class).getResultList();
            for (Object o : result2) {
                System.out.println("o = " + o);
            }*/
/*
            // m.member.username 까지 탐색하는 방법. from절에 명시적인 조인사용
            String query3 = "select m.username from Team t join t.members m"; //m.member.username 까지 탐색하는 방법. from절에 명시적인 조인사용
            List<String> resultList = em.createQuery(query3, String.class).getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }*/


            /**
             * 페치 조인(fetch join)
             * SQL 조인 종류 X
             * JPQL 에서 성능 최적화를 위해 제공하는 기능
             * ****연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회하는 기능***
             * join fetch 명령어 사용
             * 페치 조인 ::= [LEFT[OUTER] | INNER ] JOIN FETCH 조연깅로
             */

            /**
             * 엔티티 페치 조인의 예제
             * 회원을 조회하면서 연관된 팀도 함께 조회(SQL 한번에)
             * SQL을 보면 회원 뿐만 아니라 팀(T.*)도 함께 SELECT
             *
             * [JPQL]
             * select m from Member m join fetch m.team
             *
             * [SQL]
             * select m.*,t.* member m inner join team t on m.team_id = t.id
             */


            /**
             * @ManyToOne  or ManyToMany case
             **/

/*

            //String query = "select m from Member m"; //프록시
            String query = "select m from Member m join fetch m.team"; //프록시가아닌 진짜데이터 (한방쿼리) :: 페치 조인 엄청중요 (LAZY 로 설정을해놨어도 fetch join이 우선순위)
            List<Member> resultList = em.createQuery(query, Member.class).getResultList();

            for (Member members : resultList) {
                System.out.println("member==" + members.getUsername() + ", " + members.getTeam().getName());
                //LAZY 전략 이라
                //회원1,팀A(SQL)
                //회원2,팀A(1차캐시)
                //회원3,팀B (sql)

                //회원 100명 -> N + 1 문제
            }
*/



            /**
             * 컬렉션 페치 조인
             * 일대다 관계, 컬렉션 폐치 조인
             *
             * [JPQL]
             * select t from Team t join fetch t.members where t.name='팀A';
             *
             * [SQL]
             * SELECT T.*,M.* FROM TEAM T
             * INNER JOIN MEMBER M ON T.ID = M.TEAM_ID
             * WHERE T.NAME = '팀A'
             *
             * 일대 : 다 관계는 (뻥튀기 값이 나옴)
             * 팀 A는 한개지만 , 팀A에 속한 맴버는 2개이기 때문에
             * 팀 A를 조회하면 행이 2줄이나옴. 알아두자
             */

       /*    // String query = "select t FROM Team t";//사이즈 2개가 나옴
            String query = "select t FROM Team t join fetch t.members";//결과는 같지만 사이즈가 3
            List<Team> resultList = em.createQuery(query, Team.class).getResultList();

            System.out.println("resultList.size() = " + resultList.size());
            for (Team team : resultList) {
                System.out.println("team = " + team.getName() + " || " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("ㅡㅡ-> member = " + member);
                }
            }*/

            /**
             * 페치 조인과 DISTINCT 
             * 1.SQL의 DISTINCT는 중복된 결과를 제거하는 명령
             * 2.JPQL의 DISTINCT 2가지 기능 제공
             *    -SQL에 DISTINCT를 추가
             *    -애플리케이션에서 엔티티 중복 제거
             */
          /*  String query = "select t from Team t join t.members m"; //그냥 일반조인은 이시점에 로딩이안되있음 , 데이터가 없음
            //String query = "select distinct t FROM Team t join fetch t.members ";//결과는 같지만 사이즈가 3 // 중복이있음
            List<Team> resultList = em.createQuery(query, Team.class).getResultList();

            System.out.println("resultList.size() = " + resultList.size());
            for (Team team : resultList) {
                System.out.println("team = " + team.getName() + " || "  + team.getMembers().size());
                for(Member member : team.getMembers()) {
                    System.out.println("ㅡㅡ-> member = " + member);
                }
            }*/

            /**
             * 페치 조인과, 일반 조인의 차이
             * 1.일반 조인 실행시 연관된 엔티티를 함께 조회하지 않는다.
             * 2.JPQL은 결과를 반환할 때 연관관계 고려 X (딱 SELECT절에있는것만 가져옴)
             * 3.여기서는 TEAM 엔티티만 조회하고, 회원 엔티티는 조회 X 
             * 4.페치 조인을 사용할 때만 연관된 엔티티도 함께 조회가능(즉시 로딩)
             * 5.페치 조인은 객체 그래프를 SQL 한번에 조회하는 개념
             */


            /**
             * 패치 조인의 한계
             * 1.패치 조인 대상에는 별칭을 줄 수 없다.
             * 나랑 연관된것을 다 끌고오기 때문에 , 중간에 몇개를 걸러서 가져올수가 없다.
             * EX1) 팀과 연관된 회원을 5명인데 한명만 불러온다치자. 잘못조작해서
             * 나머지 4명이 누락될수있다. (이상하게 동작할 수 있다)
             * EX2)총 5개인데 , 이쿼리를날려 결과가 3개만 나왔다 치자. team.member 를하면 3명만 조회가된다 나머지는 누락이됨.
             * JPA의 의도는 이것이 아니다. 객체그래프는 모든 데이터를 조회 하는것이 기본이다.
             * TEAM.MEMBER를 하는게아니라 애초에 MEMBER에서 5개를 조회해야한다.
             * EX3)똑같은 TeamA를 두명이 조회했다고 하자 한명은 100개중 5개만 걸러서 조회하고
             * 한명은 100개중 100개를 조회했다고 치자. 그러면 영속성 컨텍스트입장에선 이런 입장이
             * 불문명하다.  웬만하면 alias(별칭) 를 쓰지말자.
             * 객체그래프의 사상에 맞지않는다.!
             String query = "select t From Team t join fetch t.members m where m.age > 10";

             * 2.둘이상의 컬렉션은 패치조인 할 수 없다.
             * 일대다 도 데이터 뻥튀기가 되는데 일대 다대 다 는 얼마나 뻥튀기가 되겠는가?
             * TIP) 컬렉션의 패치조인은 딱하나만 지정 할 수있다 라고 기억하자!!! ****
             *
             * 3.컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults)를 사용할 수 없다.
             *    3-1.일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징가능
             *    why? 데이터 뻥튀기가 안되기때문.
             *    ex)팀을 조회하는데 맴버 2개를 가지고있다고 해보자.
             *    페이지 사이즈를 1에 데이터 한건만 가져와! 하면 맴버 반이잘려나가고 1개만 조회해버린다
             *    (페이징은 철저히 데이터베이스 중심)
             *    ex2)패치조인은 한방쿼리로 모든 관련데이터를 다끌고오기떄문에 100만건 데이터면
             *    100만건을 메모리에 다올려버림 페이징할떄. 매우심각
             *    3-2.하이버네이트는 경고 로그를 남기고 메모리에서 페이징은 매우 위험하다.
             * 
             * 페치조인의 특징
             * 1.연관된 엔티티들을 SQL 한 번으로 조회 -성능 최적화
             * 2.엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선함
             *    2-1. @OneToMany(fetch = FetchType.LAZY) //글로벌 로딩 전략
             * 3.실무에서 글로벌 로딩 전략은 모두 지연 로딩
             * 4.최적화가 필요한 곳은 페치 조인 적용 (N+1이 터지는곳에서 페치조인 적용하면 좋다?)
             */
        /*    //일대다 중심 쿼리인 아래쿼리로는 페이징이 매우 위험.
            String query = "select t from Team t join fetch t.members";

            //대안1
            //맴버를 중심으로 페이징 시작 회원 -> 팀은 다대일 (이러면 페이징 위험 x)
            String query1 = "select m From Member m join fetch m.team t";

            //대안2
            //XML에 BATCH 사이즈 설정 이러면 N+1로안나가고 테이블 수만큼 맞춰서나가게 할 수있다.
            //Team 엔티티에서 BatchSize 설정
            //LAZY로딩이고, 조회가 여러번되기때문에 성능은 최악
            //팀을한 10개 조회하면,, 연관된 맴버쿼리가 추가로 10번이 더나감...
            String query2 = "select t From Team t";
            List<Team> result = em.createQuery(query2,Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();
*//*
            System.out.println("result.size() = " + result.size());

            for (Member member : result) {
                System.out.println("------------> member.getUsername() = " + member.getTeam());

            }*//*
            for (Team team : result) {
                System.out.println("team = " + team.getName() + " || " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("ㅡㅡ-> member = " + member);
                }
            }*/
/*
            String query = "select t from Team t join fetch t.members";
            List<Team> result = em.createQuery(query,Team.class)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getResultList();

            for (Team team : result) {
                System.out.println("팀이름 = " + team.getName());
                for (Member member  : team.getMembers()){
                    System.out.println("------------> member = " + member);
                }
            }*/

     /*       String query = "select t from Team t join fetch t.members";
            List<Team> resultList = em.createQuery(query, Team.class).getResultList();

            for (Team team : resultList) {
                System.out.println("team = " + team.getName());
                for(Member member : team.getMembers()){
                    System.out.println("------------>member = " + member);
                }
            }*/

            /**
             * 페치 조인 -정리
             * 1.모든 것을 페치 조인으로 해결할 수 는 없다.
             * 2.페치 조인은 객체 그래프를 유지할 때 사용하면 효과적
             * 3.여러 테이블을 조인해서 엔티티가 가진 모양이 아닌 전혀 다른 결과를
             * 내야 하면, 페치 조인 보다는 일반 조인을 사용하고 필요한 데이터만 조회해서
             * DTO로 반환하는 것이 효과적*/

 /*           //jpql은 식별자를 엔티티자체, 아이디 를사용해도 똑같이 db에 primary key 사용
            String query = "select m from Member m where m = :member";
            String query1 = "select m from Member m where m.id = :memberId";

            //연관된 외래키값값
           //m.team 은    @JoinColumn(name = "TEAM_ID") TEAM_ID를 말하는것
            String foreignKey = "select m from Member m where m.team = :team";
            String foreignKeyId = "select m from Member m where m.team.id = :teamId";
            List<Member> memberId = em.createQuery(foreignKeyId, Member.class)
                    .setParameter("teamId", teamB.getId())
                    .getResultList();


            for (Member member : memberId) {
                System.out.println("member = " + member);
            }
*/

            /**
             * Named 쿼리 -정적 쿼리
             * 1.미리 정의해서 이름을 부여해두고 사용하는 JPQL
             * 2.정적 쿼리
             * 3.어노테이션 ,XML에 정의
             * 4.애플리케이션 로딩 시점에 초기화 후 재사용 (한마디로 로딩시점에 캐쉬에 넣어놓음) 비용 감소
             * 5.애플리케이션 로딩 시점에 쿼리를 검증
             */
/*
            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "회원1")
                    .getResultList();

            for (Member member : resultList) {
               log.info("member={}",member);
            }*/

            /**
             * 벌크 연산  (여러 값을 한번에 처리하는것) (일반적인 크루드랑 같은데 이건 다량을 목적)
             * 1.재고가 10개 미만인 모든 상품의 가격을 10% 상스하려면?
             * 2.JPA 변경 감지 기능으로 실행하려면 너무 많은 SQL 실행
             *  2-1.재고가 10개 미만인 상품을 리스트로 조회한다.
             *  2.2. 상품 엔티티의 가격을 10% 증가한다
             *  2.3. 트랜잭션 커밋 시점에 변경감지가 동작한다.
             * 3.변경된 데이터가 100건이라면 100번의 update SQL 실행
             */

            /**
             * 벌크 연산 예제
             * 1.쿼리 한 번으로 여러 테이블 로우 변경(엔티티)
             * 2.executeUpdate()의 결과는 영향받은 엔티티 수 반환
             * UPDATE, DELETE 지원
             * INERT(insert into ... select, 하이버네이트 지원)
             */

            //모든회원의 나이를 20살로 바꿔보자, 아래 update 벌크연산 처리전에 FLUSH 자동호출
            //에는 영속성 컨텍스트랑 상관없이 그냥 db에 값을넣어버림;;
            //영속 컨텍스트에는 age = 20 이 반영 x
            int resultCount = em.createQuery("update Member m set m.age =20")
                    .executeUpdate();

            System.out.println("resultCount = " + resultCount);
            System.out.println("member1.getAge() === " + member1.getAge());//안바뀜
            System.out.println("member2.getAge() === " + member2.getAge());
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());//영속컨텍스트 클리어후 다시가져옴

            System.out.println("findMember.getAge() = " + findMember.getAge());






            /**
             * 벌크 연산 주의
             * 1.벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리 날려버림(flush() 발생)
             *    해결을위한 방법
             *      1.먼저 벌크 연산을 먼저 실행
             *      2.벌크 연산 수행 후 영속성 컨텍스트 초기화
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
