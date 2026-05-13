package social_network.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import social_network.dto.CommunityInfoDto;
import social_network.entity.Community;
import social_network.entity.User;
import social_network.exception.CommunityException;
import social_network.exception.NotFoundException;
import social_network.mapper.CommunityMapper;
import social_network.mapper.PostInCommunityMapper;
import social_network.repository.CommunityRepository;
import social_network.repository.PostInCommunityRepository;
import social_network.TestData;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommunityServiceTest {
    @Mock
    PostInCommunityMapper postInCommunityMapper;
    @Mock
    CommunityMapper communityMapper;
    @Mock
    CommunityRepository communityRepository;
    @Mock
    PostInCommunityRepository postInCommunityRepository;
    @InjectMocks
    CommunityService communityService;

    @Test
    void userJoiningCommunity_shouldAddUser_whenUserIsNotCreatorAndNotMember() {
        User user = TestData.user(2);
        Community community = TestData.community(10, TestData.user(1));
        when(communityRepository.isUserCreatorCommunityById(2, 10)).thenReturn(false);
        when(communityRepository.isUserInCommunity(2, 10)).thenReturn(false);

        communityService.userJoiningCommunity(user, community);

        verify(communityRepository).addUserById(user, 10);
    }

    @Test
    void userJoiningCommunity_shouldThrow_whenUserAlreadyJoined() {
        User user = TestData.user(2);
        Community community = TestData.community(10, TestData.user(1));
        when(communityRepository.isUserCreatorCommunityById(2, 10)).thenReturn(false);
        when(communityRepository.isUserInCommunity(2, 10)).thenReturn(true);

        assertThrows(CommunityException.class, () -> communityService.userJoiningCommunity(user, community));
    }

    @Test
    void createCommunity_shouldReturnDto_whenSaved() {
        User creator = TestData.user(1);
        Community saved = TestData.community(10, creator);
        CommunityInfoDto dto = new CommunityInfoDto("Java", "About Java");
        when(communityRepository.create(any(Community.class))).thenReturn(saved);
        when(communityMapper.doCommunityInfoDto(saved)).thenReturn(dto);

        CommunityInfoDto result = communityService.createCommunity("Java", "About Java", creator);

        assertSame(dto, result);
    }

    @Test
    void findCommunityById_shouldThrow_whenMissing() {
        when(communityRepository.findById(404)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> communityService.findCommunityById(404));
    }
}
