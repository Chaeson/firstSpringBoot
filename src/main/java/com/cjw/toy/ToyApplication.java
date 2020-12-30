package com.cjw.toy;

import com.cjw.toy.domain.Board;
import com.cjw.toy.domain.User;
import com.cjw.toy.domain.enums.BoardType;
import com.cjw.toy.repository.BoardRepository;
import com.cjw.toy.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class ToyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToyApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) throws Exception{
        return (args) -> {
            User user = userRepository.save(User.builder()
                                                .name("chae")
                                                .password("1234")
                                                .email("chae@gmail.com")
                                                .createdDate(LocalDateTime.now())
                                                .build());

            IntStream.rangeClosed(1,200).forEach(index->
                    boardRepository.save(Board.builder()
                            .title("게시글"+index)
                            .subTitle("순서"+index)
                            .content("컨텐츠")
                            .boardType(BoardType.free)
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .user(user).build()));
        };
    }
}
