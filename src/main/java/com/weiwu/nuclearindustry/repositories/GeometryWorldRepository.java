package com.weiwu.nuclearindustry.repositories;

import com.weiwu.nuclearindustry.entity.WorldBoundary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GeometryWorldRepository extends CrudRepository<WorldBoundary, Long> {
    @Query(value = "select ST_AsGeoJSON(ST_SimplifyPreserveTopology(geom, 0.01)) " +
            "as geometry from world_bounary_national " +
            "where chi_name = :nationName", nativeQuery = true)
    String findByNationName(@Param("nationName") String chinaName);
}
