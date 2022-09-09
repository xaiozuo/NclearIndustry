package com.weiwu.nuclearindustry.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    private Long id;

    @Column(nullable = false, length = 48)
    private String username;

    @Column(length = 48)
    private String nickname;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(length = 48)
    private String account;

    @Column(length = 16)
    private String sex;

    @Column(length = 8)
    private Integer age;

    @Column(length = 48)
    private String phoneNumber;

    @Column(length = 64)
    private String email;

    @Column(length = 64)
    private String birthPlace;

    @Column(length = 64)
    private String address;

    @Column(length = 64)
    private String portrait;

    @Column(length = 64)
    private String authority;

}
