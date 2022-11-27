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
@Table(name="china_admin_region")
public class ChinaAdminRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    private Long gid;

    @Column(name = "prov", length = 50)
    private String province;

    @Column(name = "prov_code", nullable = false)
    private Integer provinceCode;

    @Column(name = "prov_type", length = 50)
    private String provinceType;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "city_code", nullable = false)
    private Integer cityCode;

    @Column(name = "city_type", length = 50)
    private String cityType;

    @Column(name = "county", length = 50)
    private String county;

    @Column(name = "count_code", nullable = false)
    private Integer countCode;

    @Column(name = "county_type", length = 50)
    private String countyType;

    @Column(name = "geom", nullable = false)
    private Geometry geometry;
}
