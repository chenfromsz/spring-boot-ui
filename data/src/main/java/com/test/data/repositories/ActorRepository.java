package com.test.data.repositories;

import com.test.data.domain.Actor;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "actors", path = "actors")
public interface ActorRepository extends GraphRepository<Actor> {
}
