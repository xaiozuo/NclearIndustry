package com.weiwu.nuclearindustry.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Data
public class SystemConfig {
    @Value("${system.datasource}")
    private String[] DATA_SOURCE;
    @Value("${system.images}")
    private String IMAGE_PATH;
    @Value("${system.untargz}")
    private String UNTARGZ_PATH;

    public static final String GF3[] = new String[]{
            //product
            "product",
            "satellite", "orbitType", "attiType", "Station", "ReceiveTime", "sceneID", "productID", "orbitID",
            //sensor
            //"sensorID", "imagingMode",  "waveCode",
            "sensor", "sensorID", "imagingMode", "waveParams", "wave", "waveCode",
            //platform
            "platform", "CenterTime",
            //product information
            "productinfo",
            "NominalResolution", "WidthInMeters", "productLevel", "productType", "productFormat",
            //image information
            "imageinfo",
            "imagingTime", "start", "end",
            "center", "corner", "topLeft", "topRight", "bottomLeft", "bottomRight", "latitude", "longitude",
            "sceneShift",
            //process information
            "processinfo",
            "EarthModel", "ProjectModel", "DEMModel",
    };

    public static final Map<String, String> GF3Filter;

    static {
        GF3Filter = new HashMap<>();
        GF3Filter.put("imagingTime start", "imagingTime ImagingStart");
        GF3Filter.put("imagingTime end", "imagingTime ImagingEnd");
        GF3Filter.put("center latitude", "center CenterLatitude");
        GF3Filter.put("center longitude", "center CenterLongitude");
        GF3Filter.put("topLeft latitude", "topLeft TopLeftLatitude");
        GF3Filter.put("topLeft longitude", "topLeft TopLeftLongitude");
        GF3Filter.put("topRight latitude", "topRight TopRightLatitude");
        GF3Filter.put("topRight longitude", "topRight TopRightLongitude");
        GF3Filter.put("bottomLeft latitude", "bottomLeft BottomLeftLatitude");
        GF3Filter.put("bottomLeft longitude", "bottomLeft BottomLeftLongitude");
        GF3Filter.put("bottomRight latitude", "bottomRight BottomRightLatitude");
        GF3Filter.put("bottomRight longitude", "bottomRight BottomRightLongitude");
    }

    public static final String GF124567[] = new String[]{
            "ProductMetaData", //entry
            "SatelliteID", "SensorID", "SensorMode", "ReceiveStationID", "ReceiveTime", "ProduceTime", "OrbitID",
            "POrbitID", "OrbitType", "AttType", "StripID", "ProduceType", "SceneID", "ProductID", "ProductLevel",
            "ProductQuality", "ProductFormat", "Bands", "BandsCount", "BandsID", "SpectralRangeStart",
            "SpectralRangeEnd", "CentralWavelength", "ScenePath", "SceneRow", "SatPath", "SatRow", "SceneCount",
            "SceneShift", "StartTime", "EndTime", "CenterTime", "PixelByte", "ImageGSDLine", "ImageGSDSample",
            "ImageGSD", "WidthInMeters", "HeightInMeters", "CloudPercent", "SnowPercent", "DataSize",
            "RollViewingAngle", "PitchViewingAngle", "RollSatelliteAngle", "PitchSatelliteAngle",
            "YawSatelliteAngle", "SolarAzimuth", "SunAzimuth", "SolarZenith", "SatelliteAzimuth", "SatAzimuth",
            "SatelliteZenith", "GainMode", "Gain", "GAINS", "OFFSETS", "Bias", "IntegrationTime", "IntegrationLevel",
            "MapProjection", "EarthEllipsoid", "ZoneNo", "ResamplingKernel", "HeightMode", "EphemerisData",
            "AttitudeData", "RadiometricMethod", "CalParameterVersion", "CalibrationParam", "CalibrationParamVersion",
            "ScaleFactor", "MtfCorrection", "Denoise", "RayleighCorrection", "CenterLatitude", "CenterLongitude",
            "TopLeftLatitude", "TopLeftLongitude", "TopRightLatitude", "TopRightLongitude", "BottomRightLatitude",
            "BottomRightLongitude", "BottomLeftLatitude", "BottomLeftLongitude"
    };
}
