package com.cjw.toy.repository;

import com.cjw.toy.domain.Board;
import com.cjw.toy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);
}
