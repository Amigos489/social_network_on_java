package social_network.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import social_network.dto.ProfileInfoDto;
import social_network.entity.Profile;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.surname", target = "surname")
    ProfileInfoDto toProfileInfoDto(Profile profile);

    List<ProfileInfoDto> toProfileInfoDtoList(List<Profile> profiles);
}
