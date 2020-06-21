package MeetUpAPI.controller;

import MeetUpAPI.dto.UserLoginDTO;
import MeetUpAPI.dto.UserRegistrationDTO;
import MeetUpAPI.model.User;
import MeetUpAPI.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class SessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO credentials) {
        System.out.println("Login attempt: "+credentials);
        return new ResponseEntity<>(userService.signin(credentials.getUsername(), credentials.getPassword()), new HttpHeaders(), HttpStatus.OK);
    }


    @PostMapping("/signup")
    public String signup(@RequestBody UserRegistrationDTO userDTO) {
        User newUser = modelMapper.map(userDTO, User.class);
        System.out.println("Sign up attempt: "+newUser);
        return userService.signup(newUser);
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse res) {
        userService.logout(req);
        res.setStatus(200);
        return "";
    }

}