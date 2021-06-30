package jpql;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 배치조인 장점
 * batch size는 일대다 관계도 해결하고, 다대일 관계 등등 수 많은 N+1 문제를 해결해줍니다.
 * 예를 들어서
 * member -> team 관계가 지연로딩 관계입니다.
 * member를 10개 조회했으면, N+1이 최악의 경우 N이 10번 발생하겠지요.
 * 첫 쿼리 select m from Member m -> member 엔티티 10개 조회
 * while 루프 10개 실행
 *   m.getTeam().getName()
 * 이렇게 하면 지연로딩으로 select * from team where id = 1...10 쿼리가 총 10번 실행됩니다.
 * 그런데 batchSize를 10으로 지정해두면 다음과 같이 동작합니다.
 * 첫 쿼리 select m from Member m -> member 엔티티 10개 조회
 *
 * while 루프 10개 실행
 * m.getTeam().getName()
 * 지연 로딩시 실행 쿼리
 *
 * 루프 1
 * select * from team where member_id in (1,2,3,4,5)
 *
 * 루프 6
 * select * from team where member_id in (6,7,8,9,10)
 * 이렇게 batchSize를 5로 변경하면 in 쿼리로 바뀌면서 루프시 총 10번 실행되는 쿼리가 2번만 실행됩니다.
 */
@Entity
@Getter
@Setter
public class Team {

    @Id @GeneratedValue
    private Long id;

    private String name;

    //@BatchSize(size = 100) // 일대다 관계에서 페이징의 문제점.. N+1을 해결하기위해
    //팀을 가져올때 이 members는 Lazy 로딩사상태 ,
    //main에서 레이지 로딩을 끌고올때 내팀뿐만아니라,
    //List<Team> result = em.createQuery 에 인쿼리를 100개씩넘겨 지금은 두개밖에없으면 두개씩만넘김
    @OneToMany(mappedBy = "team",fetch = FetchType.EAGER)
    private List<Member> members = new ArrayList<>();


}
