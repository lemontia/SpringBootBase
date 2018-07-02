package kr.demonic.jpa.persistence;

import kr.demonic.jpa.entity.Board;
import org.springframework.data.repository.CrudRepository;

/**
 * BoardRepository 클래스를 동적으로 생성
 */
public interface BoardRepository extends CrudRepository<Board, Long> {
}
