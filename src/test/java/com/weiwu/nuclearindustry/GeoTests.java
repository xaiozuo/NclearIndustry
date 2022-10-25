package com.weiwu.nuclearindustry;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.entity.WorldBoundary;
import com.weiwu.nuclearindustry.repositories.OpticalSatelliteRepository;
import com.weiwu.nuclearindustry.repositories.UserRepository;
import com.weiwu.nuclearindustry.repositories.WorldBoundaryRepository;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class GeoTests {

    @Autowired
    private WorldBoundaryRepository worldBoundaryRepository;
    @Autowired
    private OpticalSatelliteRepository opticalSatelliteRepository;

    @Test
    public void testPostGIS(){
        Optional<WorldBoundary> byId = worldBoundaryRepository.findById(1L);
        System.out.println(byId.isPresent() + ":" + byId.toString());
        if(byId.isPresent()){
            WorldBoundary worldBoundary = byId.get();
            System.out.println(worldBoundary.getChinaName());
            Geometry geometry = worldBoundary.getGeometry();
            if(geometry != null){
                System.out.println(geometry.toString());
            }
        }
    }

    private boolean checkOp(OpticalSatellite op){
        if(op.getTopLeftLongitude() != null &&
        op.getTopLeftLatitude() != null &&
        op.getTopRightLongitude() != null &&
        op.getTopRightLatitude() != null &&
        op.getBottomRightLongitude() != null &&
        op.getBottomRightLatitude() != null &&
        op.getBottomLeftLongitude() != null &&
        op.getBottomLeftLatitude() != null){
            return true;
        }
        return false;
    }

//    @Test
//    public void testFind(){
//        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
//        Optional<WorldBoundary> worldBoundary = worldBoundaryRepository.findWorldBoundaryByChinaName("英国");
//        if(worldBoundary.isPresent()){
//            WorldBoundary wb = worldBoundary.get();
//            Geometry geometry = wb.getGeometry();
//            Iterable<OpticalSatellite> opticalSatellites = opticalSatelliteRepository.findAll();
//            opticalSatellites.forEach(opticalSatellite -> {
//                if(opticalSatellite.getDone() && opticalSatellite.getSatelliteID() != null && checkOp(opticalSatellite)){
//                    Polygon polygon = geometryFactory.createPolygon(new Coordinate[]{
//                            new Coordinate(Float.parseFloat(opticalSatellite.getTopLeftLongitude()),
//                                    Float.parseFloat(opticalSatellite.getTopLeftLatitude())),
//                            new Coordinate(Float.parseFloat(opticalSatellite.getTopRightLongitude()),
//                                    Float.parseFloat(opticalSatellite.getTopRightLatitude())),
//                            new Coordinate(Float.parseFloat(opticalSatellite.getBottomRightLongitude()),
//                                    Float.parseFloat(opticalSatellite.getBottomRightLatitude())),
//                            new Coordinate(Float.parseFloat(opticalSatellite.getBottomLeftLongitude()),
//                                    Float.parseFloat(opticalSatellite.getBottomLeftLatitude())),
//                            new Coordinate(Float.parseFloat(opticalSatellite.getTopLeftLongitude()),
//                                    Float.parseFloat(opticalSatellite.getTopLeftLatitude())),
//                    });
//                    System.out.println(geometry.intersects(polygon));
//                }
//            });
//        }
//    }

    @Test
    public void testGeometry(){
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        Polygon polygon = geometryFactory.createPolygon(new Coordinate[]{
                new Coordinate(1, 2),
                new Coordinate(1, 2),
                new Coordinate(1, 2),
                new Coordinate(1, 2),
                new Coordinate(1, 2),
        });
        Geometry geometry = polygon;
        System.out.println(polygon.toString() + ":" + geometry.toString());
    }
}
