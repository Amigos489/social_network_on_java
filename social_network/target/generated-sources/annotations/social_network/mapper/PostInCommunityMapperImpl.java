package social_network.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import social_network.dto.PostInCommunityDto;
import social_network.entity.PostInCommunity;
import social_network.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-13T23:07:54+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class PostInCommunityMapperImpl implements PostInCommunityMapper {

    @Override
    public PostInCommunityDto doPostInCommunityDto(PostInCommunity postInCommunity) {
        if ( postInCommunity == null ) {
            return null;
        }

        String authorName = null;
        String authorSurname = null;
        Integer id = null;
        String content = null;
        LocalDateTime dateTimePublication = null;

        authorName = postInCommunityAuthorName( postInCommunity );
        authorSurname = postInCommunityAuthorSurname( postInCommunity );
        id = postInCommunity.getId();
        content = postInCommunity.getContent();
        dateTimePublication = postInCommunity.getDateTimePublication();

        PostInCommunityDto postInCommunityDto = new PostInCommunityDto( id, content, authorName, authorSurname, dateTimePublication );

        return postInCommunityDto;
    }

    @Override
    public List<PostInCommunityDto> doPostDtoList(List<PostInCommunityDto> postsInCommunity) {
        if ( postsInCommunity == null ) {
            return null;
        }

        List<PostInCommunityDto> list = new ArrayList<PostInCommunityDto>( postsInCommunity.size() );
        for ( PostInCommunityDto postInCommunityDto : postsInCommunity ) {
            list.add( postInCommunityDto );
        }

        return list;
    }

    private String postInCommunityAuthorName(PostInCommunity postInCommunity) {
        User author = postInCommunity.getAuthor();
        if ( author == null ) {
            return null;
        }
        return author.getName();
    }

    private String postInCommunityAuthorSurname(PostInCommunity postInCommunity) {
        User author = postInCommunity.getAuthor();
        if ( author == null ) {
            return null;
        }
        return author.getSurname();
    }
}
