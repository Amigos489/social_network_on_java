package social_network.mapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import social_network.dto.CommunityDto;
import social_network.dto.CommunityInfoDto;
import social_network.dto.PostInCommunityDto;
import social_network.dto.UserDto;
import social_network.entity.Community;
import social_network.entity.PostInCommunity;
import social_network.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-13T23:07:54+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CommunityMapperImpl implements CommunityMapper {

    @Autowired
    private PostInCommunityMapper postInCommunityMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public CommunityDto doCommunityDto(Community community) {
        if ( community == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String description = null;
        Set<UserDto> users = null;
        Set<PostInCommunityDto> posts = null;

        id = community.getId();
        name = community.getName();
        description = community.getDescription();
        users = userSetToUserDtoSet( community.getUsers() );
        posts = postInCommunitySetToPostInCommunityDtoSet( community.getPosts() );

        Integer creatorId = null;

        CommunityDto communityDto = new CommunityDto( id, name, description, creatorId, users, posts );

        return communityDto;
    }

    @Override
    public List<CommunityDto> doCommunityDtoList(List<Community> communities) {
        if ( communities == null ) {
            return null;
        }

        List<CommunityDto> list = new ArrayList<CommunityDto>( communities.size() );
        for ( Community community : communities ) {
            list.add( doCommunityDto( community ) );
        }

        return list;
    }

    @Override
    public CommunityInfoDto doCommunityInfoDto(Community community) {
        if ( community == null ) {
            return null;
        }

        String name = null;
        String description = null;

        name = community.getName();
        description = community.getDescription();

        CommunityInfoDto communityInfoDto = new CommunityInfoDto( name, description );

        return communityInfoDto;
    }

    @Override
    public List<CommunityInfoDto> doCommunityInfoDtoList(List<Community> communities) {
        if ( communities == null ) {
            return null;
        }

        List<CommunityInfoDto> list = new ArrayList<CommunityInfoDto>( communities.size() );
        for ( Community community : communities ) {
            list.add( doCommunityInfoDto( community ) );
        }

        return list;
    }

    protected Set<UserDto> userSetToUserDtoSet(Set<User> set) {
        if ( set == null ) {
            return null;
        }

        Set<UserDto> set1 = new LinkedHashSet<UserDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( User user : set ) {
            set1.add( userMapper.doUserDto( user ) );
        }

        return set1;
    }

    protected Set<PostInCommunityDto> postInCommunitySetToPostInCommunityDtoSet(Set<PostInCommunity> set) {
        if ( set == null ) {
            return null;
        }

        Set<PostInCommunityDto> set1 = new LinkedHashSet<PostInCommunityDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PostInCommunity postInCommunity : set ) {
            set1.add( postInCommunityMapper.doPostInCommunityDto( postInCommunity ) );
        }

        return set1;
    }
}
