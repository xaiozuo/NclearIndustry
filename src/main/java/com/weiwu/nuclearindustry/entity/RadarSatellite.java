package com.weiwu.nuclearindustry.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RadarSatellite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    private Long id;

    @Column(length = 128)
    private String satellite;

    @Column(length = 128)
    private String sensorID;

    @Column(length = 128)
    private String imagingMode;

    @Column(length = 128)
    private String orbitID;

    @Column(length = 128)
    private String orbitType;

    @Column(length = 128)
    private String attiType;

    @Column(length = 128)
    private String station;

    @Column(length = 128)
    private String receiveTime;

    @Column(length = 128)
    private String sceneID;

    @Column(length = 128)
    private String productID;

    @Column(length = 128)
    private String waveCode;

    @Column(length = 128)
    private String nominalResolution;

    @Column(length = 128)
    private String widthInMeters;

    @Column(length = 128)
    private String productLevel;

    @Column(length = 128)
    private String productType;

    @Column(length = 128)
    private String productFormat;

    @Column(length = 128)
    private String sceneShift;

    @Column(length = 128)
    private String earthModel;

    @Column(length = 128)
    private String projectModel;

    @Column(length = 128)
    private String dEMModel;

    @Column(length = 128)
    private String centerTime;

    @Column(length = 128)
    private String imagingStart;

    @Column(length = 128)
    private String imagingEnd;

    @Column(length = 128)
    private String centerLatitude;

    @Column(length = 128)
    private String centerLongitude;

    @Column(length = 128)
    private String topLeftLatitude;

    @Column(length = 128)
    private String topLeftLongitude;

    @Column(length = 128)
    private String topRightLatitude;

    @Column(length = 128)
    private String topRightLongitude;

    @Column(length = 128)
    private String bottomLeftLatitude;

    @Column(length = 128)
    private String bottomLeftLongitude;

    @Column(length = 128)
    private String bottomRightLatitude;

    @Column(length = 128)
    private String bottomRightLongitude;

    @Column(length = 128)
    private String imageUrl;

    @Column(length = 128)
    private String thumbUrl;

    @Column(length = 128)
    private String directory;
}
