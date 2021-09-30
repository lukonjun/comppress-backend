package org.comppress.comppressbackend.repository;

import org.comppress.comppressbackend.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SourceRepository extends JpaRepository<Source,Long> {

    Optional<Source> findByName(String name);

}
