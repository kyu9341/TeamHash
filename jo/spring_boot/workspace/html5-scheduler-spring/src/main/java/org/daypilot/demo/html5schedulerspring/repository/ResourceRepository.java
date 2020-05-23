package org.daypilot.demo.html5schedulerspring.repository;

import org.daypilot.demo.html5schedulerspring.domain.Resource;
import org.springframework.data.repository.CrudRepository;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
}