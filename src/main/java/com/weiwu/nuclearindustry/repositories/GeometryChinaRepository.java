package com.weiwu.nuclearindustry.repositories;

import com.weiwu.nuclearindustry.entity.ChinaAdminRegion;
import com.weiwu.nuclearindustry.entity.WorldBoundary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GeometryChinaRepository extends CrudRepository<ChinaAdminRegion, Long> {
    @Query(value = "select ST_AsGeoJSON(geom) as geometry from china_admin_region " +
            "where prov_code = :provCode and city_code = :cityCode and count_code = :countyCode", nativeQuery = true)
    String findByCode(@Param("provCode") Integer provCode, @Param("cityCode") Integer cityCode,
                      @Param("countyCode") Integer countyCode);
}
