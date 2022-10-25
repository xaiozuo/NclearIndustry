package com.weiwu.nuclearindustry.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
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

    public static final String ZY[] = new String[]{
            "sensor_corrected_metadata", //entry
            "productInfo",
            "ProductLevel", "SatelliteID", "ReceiveStationID", "SensorID", "OrbitID", "SceneID", "ScenePath",
            "OrbitType", "AttitudeType", "SunAltitude", "SunAzimuth", "SatAzimuth", "SatAltitude",
            "RollViewingAngle", "PitchViewingAngle", "RollSatelliteAngle", "PitchSatelliteAngle",
            "TimeStamp", "StartTime", "EndTime", "CenterTime", "ImageGSD", "Line", "Sample", "WidthInMeters",
            "HeightInMeters", "RegionName", "CloudPercent", "ProductQuality", "Bands", "FUSMethod",
            "ProductGeographicRange", "CenterPoint", "LeftTopPoint", "RightTopPoint", "RightBottomPoint",
            "LeftBottomPoint", "Longtitude", "Latitude", "Altitude",
            "processInfo",
            "ProductTime", "ProduceID", "DataSource", "ProduceType", "CalibrationInfo", "Geometric", "Radiometric",
            "RadiometricMethod", "MtfCompensation", "Denoise", "RayleighCorrection", "GeometryMethod",
            "HeightMode", "DEMAverage", "DEMPrecision", "ResamplingKernel"
    };

    public static final Map<String, String> ZYFilter;

    static {
        ZYFilter = new HashMap<>();
        ZYFilter.put("CenterPoint Latitude", "CenterPoint CenterLatitude");
        ZYFilter.put("CenterPoint Longtitude", "CenterPoint CenterLongitude");
        ZYFilter.put("LeftTopPoint Latitude", "LeftTopPoint TopLeftLatitude");
        ZYFilter.put("LeftTopPoint Longtitude", "LeftTopPoint TopLeftLongitude");
        ZYFilter.put("RightTopPoint Latitude", "RightTopPoint TopRightLatitude");
        ZYFilter.put("RightTopPoint Longtitude", "RightTopPoint TopRightLongitude");
        ZYFilter.put("LeftBottomPoint Latitude", "LeftBottomPoint BottomLeftLatitude");
        ZYFilter.put("LeftBottomPoint Longtitude", "LeftBottomPoint BottomLeftLongitude");
        ZYFilter.put("RightBottomPoint Latitude", "RightBottomPoint BottomRightLatitude");
        ZYFilter.put("RightBottomPoint Longtitude", "RightBottomPoint BottomRightLongitude");
        ZYFilter.put("ImageGSD Line", "ImageGSD ImageGSDLine");
        ZYFilter.put("ImageGSD Sample", "ImageGSD ImageGSDSample");
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
