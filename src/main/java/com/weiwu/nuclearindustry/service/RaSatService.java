package com.weiwu.nuclearindustry.service;

import com.weiwu.nuclearindustry.entity.RadarSatellite;
import org.springframework.stereotype.Component;

@Component
public interface RaSatService {
    public void create(RadarSatellite radarSatellite);
    public void delete();
    public void update(RadarSatellite radarSatellite);
    public void search();
}
