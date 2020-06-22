package MeetUpAPI.controller;

import MeetUpAPI.dto.HobbyDTO;
import MeetUpAPI.service.JwtTokenService;
import MeetUpAPI.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hobbies")
public class HobbyController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HttpHeaders headers;

    @Autowired
    private JwtTokenService jwtTokenService;


    @GetMapping("/myhobbies")
    public ResponseEntity<HobbyDTO[]> myhobbies(HttpServletRequest req) {
        System.out.println("Getting hobbies for: "+jwtTokenService.resolveToken(req));
        return new ResponseEntity<>(new HobbyDTO[]{new HobbyDTO("Stargazing"), new HobbyDTO("Gazing stars")}, headers, HttpStatus.OK);
    }

}
