package com.weiwu.nuclearindustry.service;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import org.springframework.stereotype.Component;

@Component
public interface OpSatService {
    public void create(OpticalSatellite opticalSatellite);
    public void delete();
    public void update(OpticalSatellite opticalSatellite);
    public void search();
}
