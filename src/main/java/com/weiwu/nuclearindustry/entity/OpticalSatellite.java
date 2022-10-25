package com.weiwu.nuclearindustry.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OpticalSatellite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    private Long id;

    private Boolean done = false;

    @Column(length = 128)
    private String satelliteID;

    @Column(length = 128)
    private String sensorID;

    @Column(length = 128)
    private String sensorMode;

    @Column(length = 128)
    private String receiveStationID;

    @Column(length = 128)
    private String receiveTime;

    @Column(length = 128)
    private String produceTime;

    @Column(length = 128)
    private String orbitID;

    @Column(length = 128)
    private String pOrbitID;

    @Column(length = 128)
    private String orbitType;

    @Column(length = 128)
    private String attType;

    @Column(length = 128)
    private String attitudeType;

    @Column(length = 128)
    private String stripID;

    @Column(length = 128)
    private String produceType;

    @Column(length = 128)
    private String sceneID;

    @Column(length = 128)
    private String productID;

    @Column(length = 128)
    private String productLevel;

    @Column(length = 128)
    private String productQuality;

    @Column(length = 128)
    private String productFormat;

    @Column(length = 128)
    private String bands;

    @Column(length = 128)
    private String bandsCount;

    @Column(length = 128)
    private String bandsID;

    @Column(length = 128)
    private String spectralRangeStart;

    @Column(length = 128)
    private String spectralRangeEnd;

    @Column(length = 128)
    private String centralWaveLength;

    @Column(length = 128)
    private String scenePath;

    @Column(length = 128)
    private String sceneRow;

    @Column(length = 128)
    private String satPath;

    @Column(length = 128)
    private String satRow;

    @Column(length = 128)
    private String sceneCount;

    @Column(length = 128)
    private String sceneShift;

    @Column(length = 128)
    private String startTime;

    @Column(length = 128)
    private String endTime;

    @Column(length = 128)
    private String centerTime;

    @Column(length = 128)
    private String pixelByte;

    @Column(length = 128)
    private String imageGSDLine;

    @Column(length = 128)
    private String imageGSDSample;

    @Column(length = 128)
    private String imageGSD;

    @Column(length = 128)
    private String widthInMeters;

    @Column(length = 128)
    private String heightInMeters;

    @Column(length = 128)
    private String cloudPercent;

    @Column(length = 128)
    private String snowPercent;

    @Column(length = 128)
    private String dataSize;

    @Column(length = 128)
    private String rollViewingAngle;

    @Column(length = 128)
    private String pitchViewingAngle;

    @Column(length = 128)
    private String rollSatelliteAngle;

    @Column(length = 128)
    private String pitchSatelliteAngle;

    @Column(length = 128)
    private String yawSatelliteAngle;

    @Column(length = 128)
    private String solarAzimuth;

    @Column(length = 128)
    private String sunAzimuth;

    @Column(length = 128)
    private String solarZenith;

    @Column(length = 128)
    private String satelliteAzimuth;

    @Column(length = 128)
    private String satAzimuth;

    @Column(length = 128)
    private String satelliteZenith;

    @Column(length = 128)
    private String gainMode;

    @Column(length = 128)
    private String gain;

    @Column(length = 128)
    private String GAINS;

    @Column(length = 128)
    private String OFFSETS;

    @Column(length = 128)
    private String bias;

    @Column(length = 128)
    private String integrationTime;

    @Column(length = 128)
    private String integrationLevel;

    @Column(length = 128)
    private String mapProjection;

    @Column(length = 128)
    private String earthEllipsoid;

    @Column(length = 128)
    private String zoneNo;

    @Column(length = 128)
    private String resamplingKernel;

    @Column(length = 128)
    private String heightMode;

    @Column(length = 128)
    private String ephemerisData;

    @Column(length = 128)
    private String attitudeData;

    @Column(length = 128)
    private String radiometricMethod;

    @Column(length = 128)
    private String calParameterVersion;

    @Column(length = 128)
    private String calibrationParam;

    @Column(length = 128)
    private String calibrationParamVersion;

    @Column(length = 128)
    private String scaleFactors;

    @Column(length = 128)
    private String mtfCorrection;

    @Column(length = 128)
    private String mtfCompensation;

    @Column(length = 128)
    private String denoise;

    @Column(length = 128)
    private String rayleighCorrection;

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
    private String bottomRightLatitude;

    @Column(length = 128)
    private String bottomRightLongitude;

    @Column(length = 128)
    private String bottomLeftLatitude;

    @Column(length = 128)
    private String bottomLeftLongitude;

    @Column(length = 128)
    private String qualityInfo;

    @Column(length = 128)
    private String relativeCorrectionData;

    @Column(length = 128)
    private String usedGCPNo;

    @Column(length = 128)
    private String validPixelBits;

    @Column(length = 128)
    private String imageUrl;

    @Column(length = 128)
    private String thumbUrl;

    @Column(length = 128)
    private String directory;

    @Column(length = 20)
    private Long tarGzSize;

    @Column(length = 64)
    private String tgLastModified;

}
