package social_network.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social_network.repository.AdminRepository;

@Service
@Transactional
public class AdminService {

    private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void blockUser(Integer id) {

        adminRepository.blockUserById(id);
    }

    public void unblockUser(Integer id) {

        adminRepository.unblockUserById(id);
    }

    public void deleteCommunity(Integer id) {

        adminRepository.deleteCommunityById(id);
    }

    public void deletePostInCommunity(Integer communityId, Integer postId) {

        adminRepository.deletePostInCommunityById(communityId, postId);
    }
}
