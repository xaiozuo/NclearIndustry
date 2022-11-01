package com.weiwu.nuclearindustry.repositories;

import com.weiwu.nuclearindustry.entity.WorldBoundary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GeometryRepository extends CrudRepository<WorldBoundary, Long> {
    @Query(value = "select ST_AsGeoJSON(ST_SimplifyPreserveTopology(geom, 0.05)) " +
            "as geometry from world_bounary_national " +
            "where chi_name = :nationName", nativeQuery = true)
    String findByNationName(@Param("nationName") String chinaName);

    @Query(value = "select ST_AsGeoJSON(geom) as geometry from china_admin_region " +
            "where prov_code = :provCode", nativeQuery = true)
    String findByProvCode(@Param("provCode") String provCode);

    @Query(value = "select ST_AsGeoJSON(geom) as geometry from china_admin_region " +
            "where city_code = :cityCode", nativeQuery = true)
    String findByCityCode(@Param("cityCode") String cityCode);

    @Query(value = "select ST_AsGeoJSON(geom) as geometry from china_admin_region " +
            "where count_code = :countyCode", nativeQuery = true)
    String findByCountyCode(@Param("countyCode") String countyCode);
}
