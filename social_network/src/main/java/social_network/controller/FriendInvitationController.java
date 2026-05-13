package social_network.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_network.service.CurrentUserService;
import social_network.service.ServiceFacade;

@RestController
@RequestMapping("/friend-invitation")
public class FriendInvitationController extends AbstractController {

    private final CurrentUserService currentUserService;

    public FriendInvitationController(ServiceFacade serviceFacade, CurrentUserService currentUserService) {
        super(serviceFacade);
        this.currentUserService = currentUserService;
    }

    @GetMapping(path = "/send")
    public ResponseEntity<?> sendFriendInvitation(@RequestParam("recipientId") Integer recipientId) {
        serviceFacade.sendFriendInvitation(currentUserService.getCurrentUserId(),
                recipientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/received")
    public ResponseEntity<?> getReceivedFriendInvitation() {
        return new ResponseEntity<>(serviceFacade.findReceivedFriendInvitation(currentUserService.getCurrentUserId()), HttpStatus.OK);
    }

    @GetMapping(path = "/sent")
    public ResponseEntity<?> getSentFriendInvitation() {
        return new ResponseEntity<>(serviceFacade.findSentFriendInvitation(currentUserService.getCurrentUserId()), HttpStatus.OK);
    }

    @GetMapping(path = "/accept/{senderId}")
    public ResponseEntity<?> acceptFriendInvitation(@PathVariable("senderId") Integer senderId) {

        Integer currentUserId = currentUserService.getCurrentUserId();

        serviceFacade.acceptFriendInvitation(senderId, currentUserId, currentUserId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //отклонить приглашение в друзья
    @GetMapping(path = "/decline/{senderId}")
    public ResponseEntity<?> declineFriendInvitation(@PathVariable("senderId") Integer senderId) {

        Integer currentUserId = currentUserService.getCurrentUserId();

        serviceFacade.declineFriendInvitation(senderId, currentUserId, currentUserId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/delete-friend/{friendId}")
    public ResponseEntity<?> deleteFromFriend(@PathVariable("friendId") Integer friendId) {
        serviceFacade.deleteUserFromFriend(currentUserService.getCurrentUserId(), friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/friends")
    public ResponseEntity<?> deleteFromFriend() {
        return new ResponseEntity<>(serviceFacade.getFriends(currentUserService.getCurrentUserId()), HttpStatus.OK);
    }
}
