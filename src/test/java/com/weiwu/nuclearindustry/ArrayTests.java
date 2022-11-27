package com.weiwu.nuclearindustry;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.WorldBoundary;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootTest
public class ArrayTests {

    @Test
    public void testPostGIS(){
        List<OpticalSatellite> collect = StreamSupport
                .stream(new Iterable<OpticalSatellite>() {
                    @Override
                    public Iterator<OpticalSatellite> iterator() {
                        return null;
                    }
                }.spliterator(), false).collect(Collectors.toList());
    }
}
