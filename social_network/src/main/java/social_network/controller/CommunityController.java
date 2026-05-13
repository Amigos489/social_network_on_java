package social_network.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import social_network.dto.CreatePostInCommunityDto;
import social_network.service.CurrentUserService;
import social_network.service.ServiceFacade;
import social_network.dto.CommunityInfoDto;

@RestController
@RequestMapping("/community")
public class CommunityController extends AbstractController {

    private final CurrentUserService currentUserService;

    public CommunityController(ServiceFacade serviceFacade, CurrentUserService currentUserService) {
        super(serviceFacade);
        this.currentUserService = currentUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCommunity(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(serviceFacade.findCommunity(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCommunity(@RequestBody @Validated CommunityInfoDto communityInfoDto) {
        return new ResponseEntity<>(serviceFacade.createCommunity(communityInfoDto, currentUserService.getCurrentUserId()), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCommunity(@RequestParam("communityId") Integer communityId) {
        serviceFacade.deleteCommunity(communityId, currentUserService.getCurrentUserId());
        return new ResponseEntity<>(String.format("community with id = %d deleted",
                currentUserService.getCurrentUserId(),
                communityId),
                HttpStatus.OK);
    }

    @PutMapping("/join")
    public ResponseEntity<?> joinCommunity(@RequestParam("communityId") Integer communityId) {
        serviceFacade.joinCommunity(communityId, currentUserService.getCurrentUserId());
        return new ResponseEntity<>(String.format("user with id = %d joined community with id = %d",
                currentUserService.getCurrentUserId(),
                communityId),
                HttpStatus.OK);
    }

    @PutMapping("/leave")
    public ResponseEntity<?> leaveCommunity(@RequestParam("communityId") Integer communityId) {
        serviceFacade.leaveCommunity(communityId, currentUserService.getCurrentUserId());
        return new ResponseEntity<>(String.format("user with id = %d left community with id = %d",
                currentUserService.getCurrentUserId(),
                communityId),
                HttpStatus.OK);
    }

    @PostMapping("/publish-post")
    public ResponseEntity<?> publishPostInCommunity(@RequestBody @Valid CreatePostInCommunityDto createPostInCommunityDto) {
        return new ResponseEntity<>(serviceFacade.publishPostInCommunity(createPostInCommunityDto, currentUserService.getCurrentUserId()), HttpStatus.OK);
    }

    @DeleteMapping("/delete-post")
    public ResponseEntity<?> deletePost(@RequestParam("communityId") Integer communityId, @RequestParam("postId") Integer postId) {
        serviceFacade.deletePostinCommunity(postId, communityId,
                currentUserService.getCurrentUserId());
        return new ResponseEntity<>(String.format("post with id = %d deleted in community with id = %d. the user who deleted with id = %d",
                postId,
                communityId,
                currentUserService.getCurrentUserId()),
                HttpStatus.OK);
    }

    @GetMapping("/joined-by-user")
    public ResponseEntity<?> getCommunitiesUserJoined(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(serviceFacade.getCommunitiesUserJoined(id), HttpStatus.OK);
    }

    @GetMapping("/users-who-joined")
    public ResponseEntity<?> getUsersWhoJoinedCommunity(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(serviceFacade.getUsersWhoJoinedCommunity(id), HttpStatus.OK);
    }

    @PutMapping("/kick-user")
    public ResponseEntity<?> kickUserFromCommunity(@RequestParam("userIdForDelete") int userIdForDelete, @RequestParam("communityId") int communityId) {
        serviceFacade.kickUserFromCommunity(userIdForDelete,
                communityId,
                currentUserService.getCurrentUserId());
        return new ResponseEntity<>(String.format("user with id = %d kicked from community with id = %d", userIdForDelete, communityId), HttpStatus.OK);
    }
}
