package org.comppress.comppressbackend.repository;

import org.comppress.comppressbackend.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference,Long> {

    List<Preference> findByType(String type);

    Optional<Preference> findByNameAndAndType(String name, String type);

}
