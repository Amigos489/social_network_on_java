package social_network.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_network.service.CurrentUserService;
import social_network.service.ServiceFacade;
import social_network.dto.MessageToGroupChatDto;
import social_network.dto.MessageToUserDto;

@RestController
@RequestMapping("/message")
public class MessageController extends AbstractController {

    private CurrentUserService currentUserService;

    public MessageController(ServiceFacade serviceFacade, CurrentUserService currentUserService) {
        super(serviceFacade);
        this.currentUserService = currentUserService;
    }

    @PostMapping("/send-user")
    public ResponseEntity<?> sendMessageUser(@RequestBody MessageToUserDto messageToUserDto) {
        serviceFacade.sendMessageToUser(messageToUserDto, currentUserService.getCurrentUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/send-group-chat")
    public ResponseEntity<?> sendMessageInGroupChat(@RequestBody MessageToGroupChatDto messageToGroupChatDto) {
        serviceFacade.sendMessageInGroupChat(messageToGroupChatDto, currentUserService.getCurrentUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-from-personal-chat")
    public ResponseEntity<?> getMessageFromPersonalChat(@RequestParam("secondUserId") Integer secondUserId) {
        return new ResponseEntity<>(serviceFacade.readAllMessagesFromPersonalChat(currentUserService.getCurrentUserId(),
                secondUserId),
                HttpStatus.OK);
    }

    @GetMapping("/get-from-group-chat")
    public ResponseEntity<?> getMessageFromGroupChat(@RequestParam Integer chatId) {
        return new ResponseEntity<>(serviceFacade.readAllMessagesFromGroupChat(chatId,
                currentUserService.getCurrentUserId()), HttpStatus.OK);
    }
}
