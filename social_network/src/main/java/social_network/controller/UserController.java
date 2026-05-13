package social_network.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import social_network.dto.JwtResponse;
import social_network.dto.AuthenticationDto;
import social_network.service.JwtService;
import social_network.service.ServiceFacade;
import social_network.dto.UserRegisterDto;
import social_network.service.UserDetailsServiceImpl;

@RestController
@RequestMapping(path = "/user")
public class UserController extends AbstractController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailsServiceImpl userDetailsService;

    public UserController(ServiceFacade serviceFacade,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserDetailsServiceImpl userDetailsService) {
        super(serviceFacade);
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerUser(@RequestBody @Validated UserRegisterDto userRegisterDto) {
        serviceFacade.registerUser(userRegisterDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody @Validated AuthenticationDto authenticationDto) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationDto.login(),
                            authenticationDto.password()
                    ));

            String token = jwtService.generateToken(userDetailsService.loadUserByUsername(authenticationDto.login()));

            return new ResponseEntity<>(new JwtResponse(token),
                    HttpStatus.OK);

        } catch (AuthenticationException exception) {

            return new ResponseEntity<>("Authentication error! Invalid username or password",
                    HttpStatus.UNAUTHORIZED);
        }

    }
}
