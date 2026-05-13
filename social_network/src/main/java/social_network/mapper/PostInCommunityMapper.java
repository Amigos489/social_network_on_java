package social_network.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import social_network.dto.PostInCommunityDto;
import social_network.entity.PostInCommunity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostInCommunityMapper {

    @Mapping(source = "author.name", target = "authorName")
    @Mapping(source = "author.surname", target = "authorSurname")
    public PostInCommunityDto doPostInCommunityDto(PostInCommunity postInCommunity);

    public List<PostInCommunityDto> doPostDtoList(List<PostInCommunityDto> postsInCommunity);
}
