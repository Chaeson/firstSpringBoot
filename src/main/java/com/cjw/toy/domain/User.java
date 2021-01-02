package com.cjw.toy.domain;

import com.cjw.toy.domain.enums.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table
@Getter
public class User implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long idx;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    // OAuth 2 Start
    @Column
    private String principal;

    @Column
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    // -- Oauth2 End
    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    @Builder
    public User(String name, String password, String email,
                String principal, SocialType socialType,
                LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.principal = principal;
        this.socialType = socialType;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
