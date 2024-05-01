package com.example.myboard.entity;


import com.example.myboard.constant.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
//@EnableJpaAuditing :MyboardApplication에 선언
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //findById
    private String name; //findByName
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "like_color")
    private String likeColor; //findByColor
    @Column(name = "created_At", updatable = false)
    @CreatedDate //insert 가 생길 때 자동으로
    private LocalDateTime createdAt; //findByCreatedAt
    @Column(name = "updated_At" , insertable = false)
    @LastModifiedDate //마지막
    private LocalDateTime updatedAt; //findByUpdatedAt
}
