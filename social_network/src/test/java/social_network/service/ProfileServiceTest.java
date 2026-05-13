package social_network.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import social_network.dto.ProfileInfoDto;
import social_network.entity.Profile;
import social_network.entity.User;
import social_network.exception.NotFoundException;
import social_network.mapper.ProfileMapper;
import social_network.repository.ProfileRepository;
import social_network.TestData;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
    @Mock
    ProfileMapper profileMapper;
    @Mock
    ProfileRepository profileRepository;
    @InjectMocks
    ProfileService profileService;

    @Test
    void findProfile_shouldReturnDto_whenProfileExists() {
        User user = TestData.user(1);
        Profile profile = new Profile(user);
        ProfileInfoDto dto = new ProfileInfoDto(1, "Name1", "Surname1", null, null, null, null);
        when(profileRepository.findById(1)).thenReturn(profile);
        when(profileMapper.toProfileInfoDto(profile)).thenReturn(dto);

        ProfileInfoDto result = profileService.findProfile(1);

        assertSame(dto, result);
    }

    @Test
    void findProfileById_shouldThrow_whenProfileNotFound() {
        when(profileRepository.findById(404)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> profileService.findProfileById(404));
    }
}
