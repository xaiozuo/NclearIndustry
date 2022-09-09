package com.weiwu.nuclearindustry.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OpticalSatellite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    private Long id;

    @Column(length = 64)
    private String satelliteID;

    @Column(length = 64)
    private String sensorID;

    @Column(length = 64)
    private String SensorMode;

    @Column(length = 64)
    private String receiveStationID;

    @Column(length = 64)
    private String receiveTime;

    @Column(length = 64)
    private String produceTime;

    @Column(length = 64)
    private String orbitID;

    @Column(length = 64)
    private String pOrbitID;

    @Column(length = 64)
    private String orbitType;

    @Column(length = 64)
    private String attType;

    @Column(length = 64)
    private String stripID;

    @Column(length = 64)
    private String produceType;

    @Column(length = 64)
    private String sceneID;

    @Column(length = 64)
    private String productID;

    @Column(length = 64)
    private String productLevel;

    @Column(length = 64)
    private String productQuality;

    @Column(length = 64)
    private String productFormat;

    @Column(length = 64)
    private String bands;

    @Column(length = 64)
    private String bandsCount;

    @Column(length = 64)
    private String bandsID;

    @Column(length = 64)
    private String spectralRangeStart;

    @Column(length = 64)
    private String spectralRangeEnd;

    @Column(length = 64)
    private String centralWaveLength;

    @Column(length = 64)
    private String scenePath;

    @Column(length = 64)
    private String sceneRow;

    @Column(length = 64)
    private String satPath;

    @Column(length = 64)
    private String satRow;

    @Column(length = 64)
    private String sceneCount;

    @Column(length = 64)
    private String sceneShift;

    @Column(length = 64)
    private String startTime;

    @Column(length = 64)
    private String endTime;

    @Column(length = 64)
    private String centerTime;

    @Column(length = 64)
    private String pixelByte;

    @Column(length = 64)
    private String imageGSDLine;

    @Column(length = 64)
    private String imageGSDSample;

    @Column(length = 64)
    private String imageGSD;

    @Column(length = 64)
    private String widthInMeters;

    @Column(length = 64)
    private String heightInMeters;

    @Column(length = 64)
    private String cloudPercent;

    @Column(length = 64)
    private String snowPercent;

    @Column(length = 64)
    private String dataSize;

    @Column(length = 64)
    private String rollViewingAngle;

    @Column(length = 64)
    private String pitchViewingAngle;

    @Column(length = 64)
    private String rollSatelliteAngle;

    @Column(length = 64)
    private String pitchSatelliteAngle;

    @Column(length = 64)
    private String yawSatelliteAngle;

    @Column(length = 64)
    private String solarAzimuth;

    @Column(length = 64)
    private String sunAzimuth;

    @Column(length = 64)
    private String solarZenith;

    @Column(length = 64)
    private String satelliteAzimuth;

    @Column(length = 64)
    private String satAzimuth;

    @Column(length = 64)
    private String satelliteZenith;

    @Column(length = 64)
    private String gainMode;

    @Column(length = 64)
    private String gain;

    @Column(length = 64)
    private String GAINS;

    @Column(length = 64)
    private String OFFSETS;

    @Column(length = 64)
    private String bias;

    @Column(length = 64)
    private String integrationTime;

    @Column(length = 64)
    private String integrationLevel;

    @Column(length = 64)
    private String mapProjection;

    @Column(length = 64)
    private String earthEllipsoid;

    @Column(length = 64)
    private String zoneNo;

    @Column(length = 64)
    private String resamplingKernel;

    @Column(length = 64)
    private String heightMode;

    @Column(length = 64)
    private String ephemerisData;

    @Column(length = 64)
    private String attitudeData;

    @Column(length = 64)
    private String radiometricMethod;

    @Column(length = 64)
    private String calParameterVersion;

    @Column(length = 64)
    private String calibrationParam;

    @Column(length = 64)
    private String calibrationParamVersion;

    @Column(length = 64)
    private String scaleFactors;

    @Column(length = 64)
    private String mtfCorrection;

    @Column(length = 64)
    private String denoise;

    @Column(length = 64)
    private String rayleighCorrection;

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
    private String bottomRightLatitude;

    @Column(length = 64)
    private String bottomRightLongitude;

    @Column(length = 64)
    private String bottomLeftLatitude;

    @Column(length = 64)
    private String bottomLeftLongitude;

}
