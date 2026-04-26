package social_network.Service;

import social_network.repository.AdminRepository;

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
