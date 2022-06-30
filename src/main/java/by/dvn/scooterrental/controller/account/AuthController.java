package by.dvn.scooterrental.controller.account;

import by.dvn.scooterrental.dto.authorization.AuthRequest;
import by.dvn.scooterrental.dto.authorization.AuthResponse;
import by.dvn.scooterrental.handlerexception.*;
import by.dvn.scooterrental.model.account.User;
import by.dvn.scooterrental.security.SynteticUserDetailsService;
import by.dvn.scooterrental.security.jwt.JwtProvider;
import by.dvn.scooterrental.service.account.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    private SynteticUserDetailsService userDetailsService;

    private JwtProvider jwtProvider;

    private ServiceUser serviceUser;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          SynteticUserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          JwtProvider jwtProvider, ServiceUser serviceUser) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.serviceUser = serviceUser;
    }

    @PostMapping(value = "/login")
    public ResponseEntity getAuthorization(@RequestBody AuthRequest request) throws HandleNotAuthorized {
        try {
            String userName = request.getUserName();
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if (userDetails == null) {
                throw new UsernameNotFoundException("User with user name " + userName + " not found.");
            }
            if (passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

                String token = jwtProvider.generateToken(userName);
                AuthResponse response = new AuthResponse(userName, token);
                return ResponseEntity.ok(response);
            }
            throw new HandleNotAuthorized("Not authorized.");
        } catch (AuthenticationException e) {
            throw new HandleNotAuthorized("Not authorized.");
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity getRegistration(@RequestBody User user)
            throws HandleNotModified, HandleBadRequestPath, HandleBadCondition, HandleNotFoundExeption {

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (serviceUser.create(user)) {
            return new ResponseEntity<>("You must login.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }


}
