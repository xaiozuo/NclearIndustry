package com.weiwu.nuclearindustry.entity;

import lombok.*;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="country_states")
public class CountryStates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    private Long gid;

    @Column(name = "adm1_code", length = 50)
    private String admCode;

    @Column(name = "name", nullable = false)
    private Integer name;

    @Column(name = "admin",nullable = false)
    private String admin;

    @Column(name = "country_ch", length = 50)
    private String countryCh;

    @Column(name = "name_ch", length = 50)
    private Integer nameCh;

    @Column(name = "geom", nullable = false)
    private Geometry geometry;
}
