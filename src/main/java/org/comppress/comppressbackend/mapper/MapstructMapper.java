package org.comppress.comppressbackend.mapper;

import org.comppress.comppressbackend.dto.PreferenceDto;
import org.comppress.comppressbackend.dto.RoleDto;
import org.comppress.comppressbackend.dto.UserDto;
import org.comppress.comppressbackend.entity.Preference;
import org.comppress.comppressbackend.entity.Role;
import org.comppress.comppressbackend.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface MapstructMapper {

    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
    PreferenceDto preferenceToPreferenceDto(Preference preference);
    Preference preferenceDtoToPreference(PreferenceDto PreferenceDto);
    RoleDto roleToRoleDto(Role role);
    Role roleDtoToRole(RoleDto roleDto);
    List<RoleDto> roleListToRoleDtoList(List<Role> roleList);
    List<PreferenceDto> preferenceListToPreferenceDtoList(List<Preference> preferencesList);
    List<UserDto> userListToUserListDto(List<User> userList);

}
