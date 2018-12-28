package com.test.data.repositories;

import com.test.data.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends Neo4jRepository<Movie, Long> {
    Movie findByName(@Param("name") String name);

    @Query("MATCH (m:Movie) WHERE m.name =~ ('(?i).*'+{name}+'.*') RETURN m")
    Movie findByNameLike(@Param("name") String name);
}


