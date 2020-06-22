package MeetUpAPI.controller;

import MeetUpAPI.dto.UserLoginDTO;
import MeetUpAPI.dto.UserRegistrationDTO;
import MeetUpAPI.dbModels.User;
import MeetUpAPI.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class SessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HttpHeaders headers;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO credentials) {
        System.out.println("Login attempt: "+credentials);
        return new ResponseEntity<>(userService.signin(credentials.getUsername(), credentials.getPassword()), headers, HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserRegistrationDTO userDTO) {
        User newUser = modelMapper.map(userDTO, User.class);
        System.out.println("Sign up attempt: "+newUser);
        return new ResponseEntity<>(userService.signup(newUser), headers, HttpStatus.OK);
    }


    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest req) {
        userService.logout(req);
        return new ResponseEntity<>("", headers, HttpStatus.OK);
    }

}