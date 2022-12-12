package com.weiwu.nuclearindustry.repositories;

import com.weiwu.nuclearindustry.entity.ChinaAdminRegion;
import com.weiwu.nuclearindustry.entity.CountryStates;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CountryStatesRepository extends CrudRepository<CountryStates, Long> {
    @Query(value = "select ST_AsGeoJSON(geom) as geometry from country_states " +
            "where admin = :admin and name = :name", nativeQuery = true)
    String findByAdminAndName(@Param("admin") String admin, @Param("name") String name);
}
