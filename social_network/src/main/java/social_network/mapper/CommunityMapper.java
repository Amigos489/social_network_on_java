package social_network.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import social_network.dto.CommunityDto;
import social_network.dto.CommunityInfoDto;
import social_network.entity.Community;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PostInCommunityMapper.class, UserMapper.class})
public interface CommunityMapper {

    public CommunityDto doCommunityDto(Community community);

    public List<CommunityDto> doCommunityDtoList(List<Community> communities);

    public CommunityInfoDto doCommunityInfoDto(Community community);

    public List<CommunityInfoDto> doCommunityInfoDtoList(List<Community> communities);
}
