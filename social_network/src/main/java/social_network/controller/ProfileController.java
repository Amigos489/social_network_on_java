package social_network.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import social_network.dto.FindProfileCriteriaDto;
import social_network.enums.Gender;
import social_network.service.CurrentUserService;
import social_network.service.ServiceFacade;

@Valid
@RestController
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

    private final CurrentUserService currentUserService;

    public ProfileController(ServiceFacade serviceFacade, CurrentUserService currentUserService) {
        super(serviceFacade);
        this.currentUserService = currentUserService;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        return new ResponseEntity<>(serviceFacade.findProfile(id), HttpStatus.OK);
    }

    @PutMapping("/set-age")
    public ResponseEntity<?> setAgeInProfile(@RequestParam("age")
                                                 @NotNull @Min(value = 14) @Max(value = 100) Integer age) {
        serviceFacade.setAgeInProfile(currentUserService.getCurrentUserId(), age);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/set-status")
    public ResponseEntity<?> setStatusInProfile(@RequestParam("status") @NotBlank String status) {
        serviceFacade.setStatusInProfile(currentUserService.getCurrentUserId(), status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/set-gender")
    public ResponseEntity<?> setAgeInProfile(@RequestParam("gender") Gender gender) {
        serviceFacade.setGenderInProfile(currentUserService.getCurrentUserId(), gender);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/find-by-criteria")
    public ResponseEntity<?> findProfileByCriteria(@RequestBody @Validated FindProfileCriteriaDto findProfileCriteriaDto){
        return new ResponseEntity<>(serviceFacade.findProfilesByCriteria(findProfileCriteriaDto), HttpStatus.OK);
    }
}
