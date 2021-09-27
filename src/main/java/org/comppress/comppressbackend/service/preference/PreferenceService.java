package org.comppress.comppressbackend.service.preference;

import org.comppress.comppressbackend.dto.PreferenceDto;

import java.util.List;
import java.util.Optional;

public interface PreferenceService {

    Long create(PreferenceDto preferenceDto);
    Optional<PreferenceDto> findById(Long id);
    Optional<PreferenceDto> findByNameAndType(String name, String type);
    List<PreferenceDto> findByType(String type);
    List<PreferenceDto> findAll();

}
