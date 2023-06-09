package com.weiwu.nuclearindustry.repositories;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface OpticalSatelliteRepository extends PagingAndSortingRepository<OpticalSatellite, Long>,
        QueryByExampleExecutor<OpticalSatellite>, JpaSpecificationExecutor<OpticalSatellite> {
}
