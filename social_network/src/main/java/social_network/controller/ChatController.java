package social_network.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import social_network.dto.CreateGroupChatDto;
import social_network.service.CurrentUserService;
import social_network.service.ServiceFacade;

@RestController
@RequestMapping("/chat")
public class ChatController extends AbstractController {

    private final CurrentUserService currentUserService;

    public ChatController(ServiceFacade serviceFacade, CurrentUserService currentUserService) {
        super(serviceFacade);
        this.currentUserService = currentUserService;
    }

    @PostMapping("/create-group-chat")
    public ResponseEntity<?> createGroupChat(@RequestBody @Validated CreateGroupChatDto createGroupChatDto) {
        return new ResponseEntity<>(serviceFacade.createGroupChat(createGroupChatDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete-group-chat")
    public ResponseEntity<?> deleteGroupChat(@RequestParam("groupChatId") Integer groupChatId) {

        serviceFacade.deleteGroupChat(groupChatId, currentUserService.getCurrentUserId());

        return new ResponseEntity<>(String.format("group chat with id = %d deleted",
                groupChatId),
                HttpStatus.OK);
    }

    @PutMapping("/add-user-group-chat")
    public ResponseEntity<?> addUserInGroupChat(@RequestParam("groupChatId") Integer groupChatId,
                                                @RequestParam("userIdForAdd") Integer userIdForAdd) {

        serviceFacade.addUserInGroupChat(userIdForAdd,
                groupChatId,
                currentUserService.getCurrentUserId());

        return new ResponseEntity<>(String.format("user with id = %d add in group chat with id = %d",
                userIdForAdd, groupChatId),
                HttpStatus.OK);
    }

    @PutMapping("/leave-group-chat")
    public ResponseEntity<?> leaveFromGroupChat(@RequestParam("groupChatId") Integer groupChatId) {

        serviceFacade.leaveFromGroupChat(groupChatId,
                currentUserService.getCurrentUserId());

        return new ResponseEntity<>(String.format("user with id = %d leave from group chat with id = %d",
                currentUserService.getCurrentUserId(), groupChatId),
                HttpStatus.OK);
    }

    @PutMapping("/kick-user-group-chat")
    public ResponseEntity<?> kickUserFromGroupChat(@RequestParam("groupChatId") Integer groupChatId,
                                      @RequestParam("userIdForAdd") Integer userIdForKick) {

        serviceFacade.kickUserFromGroupChat(userIdForKick,
                groupChatId,
                currentUserService.getCurrentUserId());

        return new ResponseEntity<>(String.format("user with id kicked from group chat with id = %d",
                userIdForKick, groupChatId),
                HttpStatus.OK);
    }
}
