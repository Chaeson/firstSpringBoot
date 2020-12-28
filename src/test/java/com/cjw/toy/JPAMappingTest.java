package com.cjw.toy;

import com.cjw.toy.domain.Board;
import com.cjw.toy.domain.User;
import com.cjw.toy.domain.enums.BoardType;
import com.cjw.toy.repository.BoardRepository;
import com.cjw.toy.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest                // JPA테스트를 위한 전용 어노테이션, 테스트 끝나면 자동 롤백!
public class JPAMappingTest {

    private final String boardTestTitle="테스트";
    private final String email="test@gmail.com";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    public void init(){
        User user = userRepository.save(User.builder()
                    .name("havi")
                    .password("testPass")
                    .email(email)
                    .createdDate(LocalDateTime.now())
                    .build());

        boardRepository.save(Board.builder()
                            .title(boardTestTitle)
                            .subTitle("서브 타이틀")
                            .content("콘텐츠 내용")
                            .boardType(BoardType.free)
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .user(user).build());
    }

    @Test
    public void 생성테스트(){
        User user = userRepository.findByEmail(email);
        assertThat(user.getName(),is("havi"));
        assertThat(user.getPassword(), is("testPass"));
        assertThat(user.getEmail(),is(email));

        Board board = boardRepository.findByUser(user);
        assertThat(board.getTitle(),is(boardTestTitle));
        assertThat(board.getSubTitle(), is("서브 타이틀"));
        assertThat(board.getContent(), is("콘텐츠 내용"));
        assertThat(board.getBoardType(), is(BoardType.free));
    }

}
