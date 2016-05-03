package com.test.data.repositories;

import com.test.data.domain.Movie;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends GraphRepository<Movie> {
    Movie findByName(@Param("name") String name);

    @Query("MATCH (m:Movie) WHERE m.name =~ ('(?i).*'+{name}+'.*') RETURN m")
    Page<Movie> findByName(@Param("name") String name, Pageable pageable);//not support yet
}


