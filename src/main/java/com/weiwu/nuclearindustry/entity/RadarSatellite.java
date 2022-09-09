package com.weiwu.nuclearindustry.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class RadarSatellite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    private Long id;

    @Column(length = 64)
    private String satellite;

    @Column(length = 64)
    private String sensorID;

    @Column(length = 64)
    private String imagingMode;

    @Column(length = 64)
    private String orbitID;

    @Column(length = 64)
    private String orbitType;

    @Column(length = 64)
    private String attiType;

    @Column(length = 64)
    private String station;

    @Column(length = 64)
    private String receiveTime;

    @Column(length = 64)
    private String sceneID;

    @Column(length = 64)
    private String productID;

    @Column(length = 64)
    private String waveCode;

    @Column(length = 64)
    private String nominalResolution;

    @Column(length = 64)
    private String widthInMeters;

    @Column(length = 64)
    private String productLevel;

    @Column(length = 64)
    private String productType;

    @Column(length = 64)
    private String productFormat;

    @Column(length = 64)
    private String sceneShift;

    @Column(length = 64)
    private String earthModel;

    @Column(length = 64)
    private String projectModel;

    @Column(length = 64)
    private String dEMModel;

    @Column(length = 64)
    private String centerTime;

    @Column(length = 64)
    private String imagingStart;

    @Column(length = 64)
    private String imagingEnd;

    @Column(length = 64)
    private String centerLatitude;

    @Column(length = 64)
    private String centerLongitude;

    @Column(length = 64)
    private String topLeftLatitude;

    @Column(length = 64)
    private String topLeftLongitude;

    @Column(length = 64)
    private String topRightLatitude;

    @Column(length = 64)
    private String topRightLongitude;

    @Column(length = 64)
    private String bottomLeftLatitude;

    @Column(length = 64)
    private String bottomLeftLongitude;

    @Column(length = 64)
    private String bottomRightLatitude;

    @Column(length = 64)
    private String bottomRightLongitude;
}
