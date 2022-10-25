package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.entity.WorldBoundary;
import com.weiwu.nuclearindustry.repositories.OpticalSatelliteRepository;
import com.weiwu.nuclearindustry.repositories.RadarSatelliteRepository;
import com.weiwu.nuclearindustry.repositories.WorldBoundaryRepository;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@ResponseBody
@RequestMapping(value = "/query")
public class QueryController {

    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
    @Autowired
    private WorldBoundaryRepository worldBoundaryRepository;

    @Autowired
    private OpticalSatelliteRepository opticalSatelliteRepository;

    @Autowired
    private RadarSatelliteRepository radarSatelliteRepository;

    private boolean checkOpticalSatellite(OpticalSatellite op){
        if(op.getTopLeftLongitude() != null && op.getTopLeftLatitude() != null &&
                op.getTopRightLongitude() != null && op.getTopRightLatitude() != null &&
                op.getBottomRightLongitude() != null && op.getBottomRightLatitude() != null &&
                op.getBottomLeftLongitude() != null && op.getBottomLeftLatitude() != null){
            return true;
        }
        return false;
    }

    private boolean checkRadarSatellite(RadarSatellite ra){
        if(ra.getTopLeftLongitude() != null && ra.getTopLeftLatitude() != null &&
                ra.getTopRightLongitude() != null && ra.getTopRightLatitude() != null &&
                ra.getBottomRightLongitude() != null && ra.getBottomRightLatitude() != null &&
                ra.getBottomLeftLongitude() != null && ra.getBottomLeftLatitude() != null){
            return true;
        }
        return false;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/optical_satellite/{nation}")
    public List<OpticalSatellite> queryOpticalSatellite(@PathVariable String nation){
        System.out.println(nation);
        Optional<WorldBoundary> worldBoundary = worldBoundaryRepository.findWorldBoundaryByChinaName(nation);
        List<OpticalSatellite> res = new ArrayList<>();
        if(worldBoundary.isPresent()){
            WorldBoundary wb = worldBoundary.get();
            Geometry geometry = wb.getGeometry();
            Iterable<OpticalSatellite> opticalSatellites = opticalSatelliteRepository.findAll();
            opticalSatellites.forEach(opticalSatellite -> {
                if(opticalSatellite.getDone() && opticalSatellite.getSatelliteID() != null
                        && checkOpticalSatellite(opticalSatellite)){
                    Polygon polygon = geometryFactory.createPolygon(new Coordinate[]{
                            new Coordinate(Float.parseFloat(opticalSatellite.getTopLeftLongitude()),
                                    Float.parseFloat(opticalSatellite.getTopLeftLatitude())),
                            new Coordinate(Float.parseFloat(opticalSatellite.getTopRightLongitude()),
                                    Float.parseFloat(opticalSatellite.getTopRightLatitude())),
                            new Coordinate(Float.parseFloat(opticalSatellite.getBottomRightLongitude()),
                                    Float.parseFloat(opticalSatellite.getBottomRightLatitude())),
                            new Coordinate(Float.parseFloat(opticalSatellite.getBottomLeftLongitude()),
                                    Float.parseFloat(opticalSatellite.getBottomLeftLatitude())),
                            new Coordinate(Float.parseFloat(opticalSatellite.getTopLeftLongitude()),
                                    Float.parseFloat(opticalSatellite.getTopLeftLatitude())),
                    });
                    if(geometry.intersects(polygon)){
                        res.add(opticalSatellite);
                    }
                }
            });
        }
        return res;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/radar_satellite/{nation}")
    public List<RadarSatellite> queryRadarSatellite(@PathVariable String nation){
        Optional<WorldBoundary> worldBoundary = worldBoundaryRepository.findWorldBoundaryByChinaName(nation);
        ArrayList<RadarSatellite> res = new ArrayList<>();
        if(worldBoundary.isPresent()){
            WorldBoundary wb = worldBoundary.get();
            Geometry geometry = wb.getGeometry();
            Iterable<RadarSatellite> radarSatellites = radarSatelliteRepository.findAll();
            radarSatellites.forEach(radarSatellite -> {
                if(radarSatellite.getDone() && radarSatellite.getSatellite() != null
                        && checkRadarSatellite(radarSatellite)){
                    Polygon polygon = geometryFactory.createPolygon(new Coordinate[]{
                            new Coordinate(Float.parseFloat(radarSatellite.getTopLeftLongitude()),
                                    Float.parseFloat(radarSatellite.getTopLeftLatitude())),
                            new Coordinate(Float.parseFloat(radarSatellite.getTopRightLongitude()),
                                    Float.parseFloat(radarSatellite.getTopRightLatitude())),
                            new Coordinate(Float.parseFloat(radarSatellite.getBottomRightLongitude()),
                                    Float.parseFloat(radarSatellite.getBottomRightLatitude())),
                            new Coordinate(Float.parseFloat(radarSatellite.getBottomLeftLongitude()),
                                    Float.parseFloat(radarSatellite.getBottomLeftLatitude())),
                            new Coordinate(Float.parseFloat(radarSatellite.getTopLeftLongitude()),
                                    Float.parseFloat(radarSatellite.getTopLeftLatitude())),
                    });
                    if(geometry.intersects(polygon)){
                        res.add(radarSatellite);
                    }
                }
            });
        }
        return res;
    }
}
