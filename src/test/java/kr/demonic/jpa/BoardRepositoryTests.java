package kr.demonic.jpa;

import kr.demonic.jpa.entity.Board;
import kr.demonic.jpa.persistence.BoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * JPA 기능 테스트
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    // 테이블 생성 테스트
    /*
2018-07-02 15:31:06.062  INFO 11228 --- [           main] jdbc.sqlonly                             : create table tb_boards (id bigint not null, content varchar(255), regdate datetime, title varchar(255),
updatedate datetime, writer varchar(255), primary key (id)) engine=InnoDB
     */
    @Test
    public void inspect(){
        // 실제 객체의 클래스 이름
        Class<?> clz = boardRepository.getClass();

        System.out.println(clz.getName());

        // 클래스가 구현하고 있는 인터페이스 목록
        Class<?>[] interfaces = clz.getInterfaces();

        Stream.of(interfaces).forEach(inter -> System.out.println(inter.getName()));

        // 클래스의 부모 클래스
        Class<?> superClasses = clz.getSuperclass();

        System.out.println(superClasses.getName());
    }

    // 삽입
/*
2018-07-02 15:32:03.808  INFO 3472 --- [           main] jdbc.sqlonly                             : insert into tb_boards (content, regdate, title, updatedate, writer, id) values ('내용', '07/02/2018
15:32:03.794', '제목', '07/02/2018 15:32:03.794', 'user01', 1)
 */
    @Test
    public void testInsert(){
        Board board = new Board();
        board.setTitle("제목");
        board.setContent("내용");
        board.setWriter("user01");

        boardRepository.save(board);
    }

    // 조회
/*
2018-07-02 15:32:45.075  INFO 10308 --- [           main] jdbc.sqlonly                             : select board0_.id as id1_0_0_, board0_.content as content2_0_0_, board0_.regdate as regdate3_0_0_,
board0_.title as title4_0_0_, board0_.updatedate as updateda5_0_0_, board0_.writer as writer6_0_0_
from tb_boards board0_ where board0_.id=1
 */
    @Test
    public void testRead(){
        Optional<Board> board = boardRepository.findById(1L);

        System.out.println(board);
    }


    // 수정
/*
조회를 하고 난 후 수정하는 것을 확인할 수 있다.
2018-07-02 15:34:53.788  INFO 10764 --- [           main] jdbc.sqlonly                             : select board0_.id as id1_0_0_, board0_.content as content2_0_0_, board0_.regdate as regdate3_0_0_,
board0_.title as title4_0_0_, board0_.updatedate as updateda5_0_0_, board0_.writer as writer6_0_0_
from tb_boards board0_ where board0_.id=1
2018-07-02 15:34:53.818  INFO 10764 --- [           main] jdbc.sqlonly                             : update tb_boards set content='내용', regdate='07/02/2018 15:34:36.000', title='제목3 로 수정', updatedate='07/02/2018
15:34:53.811', writer='user01' where id=1

JPA는 데이터베이스에서 바로 작업하는 JDBC와는 달리 엔터티 객체들을 메모리상에서 관리하고, 필요한 경우 데이터베이스에 작업을 하게 됩니다.
수정과 삭제 작업을 직접 데이터베이스에서 SQL을 실행하는 것이 아니라, 엔티티 객체가 우선적으로 메모리상에 존재하고 있어야 하기 때문에 select가 동작하게 됩니다.
*/
    @Test
    public void testUpdate(){
        Optional<Board> boardOpt = boardRepository.findById(1L);

        boardOpt.get().setTitle("제목3 로 수정");

        boardRepository.save(boardOpt.get());

    }


    // 삭제
    // 해당 식별키를 가진 엔터티가 있으면 삭제합니다.
/*
2018-07-02 15:35:41.575  INFO 9576 --- [           main] jdbc.sqlonly                             : select board0_.id as id1_0_0_, board0_.content as content2_0_0_, board0_.regdate as regdate3_0_0_,
board0_.title as title4_0_0_, board0_.updatedate as updateda5_0_0_, board0_.writer as writer6_0_0_
from tb_boards board0_ where board0_.id=1
2018-07-02 15:35:41.635  INFO 9576 --- [           main] jdbc.sqlonly                             : delete from tb_boards where id=1

수정부분과 마찬가지로 우선 select를 통해 조회한 후에 엔터티 객체를 보관하고 이후 delete로 실행하여 삭제합니다.
 */
    @Test
    public void testDelete(){
        boardRepository.deleteById(1L);
    }
}
