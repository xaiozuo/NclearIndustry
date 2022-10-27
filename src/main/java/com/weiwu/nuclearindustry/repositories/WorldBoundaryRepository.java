package com.weiwu.nuclearindustry.repositories;

import com.weiwu.nuclearindustry.entity.WorldBoundary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorldBoundaryRepository extends CrudRepository<WorldBoundary, Long> {
    @Query(value = "select ST_AsGeoJSON(ST_SimplifyPreserveTopology(geom, 0.05)) as geometry from world_bounary_national " +
            "where chi_name = :chinaName",
            nativeQuery = true)
    String findByChinaName(@Param("chinaName") String chinaName);

    Optional<WorldBoundary> findWorldBoundaryByChinaName(String chinaName);
}
