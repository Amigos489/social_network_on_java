package social_network.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import social_network.dto.ProfileInfoDto;
import social_network.entity.Profile;
import social_network.entity.User;
import social_network.enums.Gender;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-13T23:07:54+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public ProfileInfoDto toProfileInfoDto(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String surname = null;
        Integer age = null;
        String status = null;
        Gender gender = null;

        id = profileUserId( profile );
        name = profileUserName( profile );
        surname = profileUserSurname( profile );
        age = profile.getAge();
        status = profile.getStatus();
        gender = profile.getGender();

        LocalDate birthday = null;

        ProfileInfoDto profileInfoDto = new ProfileInfoDto( id, name, surname, age, birthday, status, gender );

        return profileInfoDto;
    }

    @Override
    public List<ProfileInfoDto> toProfileInfoDtoList(List<Profile> profiles) {
        if ( profiles == null ) {
            return null;
        }

        List<ProfileInfoDto> list = new ArrayList<ProfileInfoDto>( profiles.size() );
        for ( Profile profile : profiles ) {
            list.add( toProfileInfoDto( profile ) );
        }

        return list;
    }

    private Integer profileUserId(Profile profile) {
        User user = profile.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String profileUserName(Profile profile) {
        User user = profile.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getName();
    }

    private String profileUserSurname(Profile profile) {
        User user = profile.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getSurname();
    }
}
