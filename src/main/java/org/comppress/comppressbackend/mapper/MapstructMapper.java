package org.comppress.comppressbackend.mapper;

import org.comppress.comppressbackend.dto.*;
import org.comppress.comppressbackend.entity.*;
import org.comppress.comppressbackend.jsonmodel.ArticleJsonModel;
import org.comppress.comppressbackend.jsonmodel.SourceJsonModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapstructMapper {

    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
    PreferenceDto preferenceToPreferenceDto(Preference preference);
    Preference preferenceDtoToPreference(PreferenceDto PreferenceDto);
    RoleDto roleToRoleDto(Role role);
    Role roleDtoToRole(RoleDto roleDto);
    ArticleDto articleToArticleDto(Article article);
    Article articleDtoToArticle(ArticleDto articleDto);
    SourceDto sourceToSourceDto(Source source);
    Source sourceDtoToSource(SourceDto sourceDto);
    RatingDto ratingToRatingDto(Rating rating);
    Rating ratingDtoToRating(RatingDto ratingDto);

    ArticleDto articleJsonModelToArticleDto(ArticleJsonModel articleJsonModel);
    SourceDto sourceJsonModelToSourceDto(SourceJsonModel sourceJsonModel);

}
