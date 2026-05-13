package social_network.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import social_network.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/block-user")
    public ResponseEntity<?> blockUser(@RequestParam("id") Integer id) {
        adminService.blockUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/unblock-user")
    public ResponseEntity<?> unblockUser(@RequestParam("id") Integer id) {
        adminService.unblockUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/delete-community")
    public ResponseEntity<?> deleteCommunity(@RequestParam("id") Integer id) {
        adminService.deleteCommunity(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/delete-post-community")
    public ResponseEntity<?> deletePostInCommunity(@RequestParam("communityId") Integer communityId,
                                                   @RequestParam("postId") Integer postId) {
        adminService.deletePostInCommunity(communityId, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
