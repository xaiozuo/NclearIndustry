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
@Table(name="world_bounary_national")
public class WorldBoundary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    private Long gid;

    @Column(name = "name_0", nullable = false, length = 80)
    private String englishName;

    @Column(name = "Chi_name", nullable = false, length = 50)
    private String chinaName;

    @Column(name = "geom", nullable = false)
    private Geometry geometry;
}
