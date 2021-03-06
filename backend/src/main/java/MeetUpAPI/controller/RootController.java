package MeetUpAPI.controller;

import MeetUpAPI.dbModels.Hobby;
import MeetUpAPI.dto.UserLoginDTO;
import MeetUpAPI.dto.UserRegistrationDTO;
import MeetUpAPI.dbModels.User;
import MeetUpAPI.errorHandling.CustomException;
import MeetUpAPI.service.DBService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class RootController {

    @Autowired
    private DBService dbService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HttpHeaders headers;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO credentials) {
        return new ResponseEntity<>(dbService.signin(credentials.getUsername(), credentials.getPassword()), headers, HttpStatus.OK);
    }

    @PostMapping("/hobbies")
    public ResponseEntity<String> addGlobalHobby(@RequestBody String name){
        int id = dbService.addtoAllHobbies(name);
        return new ResponseEntity<>("{\"id\":\"" + id + "\"}", headers, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserRegistrationDTO userDTO) {
        User newUser = modelMapper.map(userDTO, User.class);
        return new ResponseEntity<>(dbService.signup(newUser), headers, HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest req) {
        dbService.logout(req);
        System.out.println("Logout");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest req) {
        return new ResponseEntity<>(dbService.refreshToken(req), headers, HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String test() {
        throw new CustomException("Custom error, bad request", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/hobbies")
    public ResponseEntity<List<Hobby>> getGlobalHobbies(){
        return new ResponseEntity<>(dbService.getAllHobbies(), headers, HttpStatus.OK);
    }



}
