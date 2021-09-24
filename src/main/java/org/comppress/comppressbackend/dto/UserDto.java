package org.comppress.comppressbackend.dto;
import lombok.Data;

import java.util.Collection;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String username;
    private String password;
    private RoleDto roleDto;
    private Collection<PreferenceDto> preferences;

}
