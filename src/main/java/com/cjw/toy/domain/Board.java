package com.cjw.toy.domain;

import com.cjw.toy.domain.enums.BoardType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table
@Getter
public class Board implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.TABLE)    //기본키 자동 할당 어노테이션
    private Long idx;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private String content;

    @Column
    @Enumerated(EnumType.STRING)    //Enum 타입 매핑용 어노테이션. 자바enum형과 DB 데이터 변환을 지원
    private BoardType boardType;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    @OneToOne(fetch = FetchType.LAZY)   //User 인스턴스의 PK값을 저장
    // FetchType.EAGER: 조회시 User 인스턴스도 같이 조회
    // FetchType.LAZY: 실제 인스턴스 사용시 조회.
    private User user;

    @Builder
    public Board(String title, String subTitle, String content, BoardType boardType, LocalDateTime createdDate, LocalDateTime updatedDate, User user) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.boardType = boardType;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.user = user;
    }
}
