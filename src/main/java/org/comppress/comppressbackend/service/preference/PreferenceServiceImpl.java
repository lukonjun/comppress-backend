package org.comppress.comppressbackend.service.preference;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.PreferenceDto;
import org.comppress.comppressbackend.entity.Preference;
import org.comppress.comppressbackend.mapper.MapstructMapper;
import org.comppress.comppressbackend.repository.PreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PreferenceServiceImpl implements PreferenceService {

    private final MapstructMapper mapstructMapper;
    private final PreferenceRepository preferenceRepository;

    public PreferenceServiceImpl(MapstructMapper mapstructMapper, PreferenceRepository preferenceRepository) {
        this.mapstructMapper = mapstructMapper;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public Long create(PreferenceDto preferenceDto) {
        Preference preference = mapstructMapper.preferenceDtoToPreference(preferenceDto);
        Preference saved = preferenceRepository.save(preference);
        log.info("Created Preference with id {}", saved.getId());
        return saved.getId();
    }

    @Override
    public Optional<PreferenceDto> findById(Long id) {
        Optional<Preference> found = preferenceRepository.findById(id);
        if(found.isPresent()){
            log.info("Found Preference with id {}", id);
            return Optional.of(mapstructMapper.preferenceToPreferenceDto(found.get()));
        }
        log.error("No Preference found with id {}", id);
        return Optional.empty();
    }

    @Override
    public Optional<PreferenceDto> findByNameAndType(String name, String type) {
        Optional<Preference> found = preferenceRepository.findByNameAndAndType(name,type);
        if(found.isPresent()){
            log.info("Found Preference with name {} and type {}", name, type);
            return Optional.of(mapstructMapper.preferenceToPreferenceDto(found.get()));
        }
        log.error("Found no Preference with name {} and type {}", name, type);
        return Optional.empty();
    }

    @Override
    public List<PreferenceDto> findByType(String type) {
        List<Preference> preferenceList = preferenceRepository.findByType(type);
        log.info("Found preference list of size {}", preferenceList.size());
        return mapstructMapper.preferenceListToPreferenceDtoList(preferenceList);
    }

    @Override
    public List<PreferenceDto> findAll() {
        List<Preference> preferenceList = preferenceRepository.findAll();
        log.info("Found preference list of size {}", preferenceList.size());
        return mapstructMapper.preferenceListToPreferenceDtoList(preferenceList);
    }
}
