package com.weiwu.nuclearindustry.repositories;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.RadarSatellite;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface RadarSatelliteRepository extends PagingAndSortingRepository<RadarSatellite, Long>,
        QueryByExampleExecutor<RadarSatellite>, JpaSpecificationExecutor<RadarSatellite> {
}
