package MeetUpAPI.controller;

import MeetUpAPI.dto.HobbyDTO;
import MeetUpAPI.dto.MatchDTO;
import MeetUpAPI.service.JwtTokenService;
import MeetUpAPI.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class MatchesController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HttpHeaders headers;

    @Autowired
    private JwtTokenService jwtTokenService;


    @GetMapping("/matches")
    public ResponseEntity<MatchDTO[]> mymatches(HttpServletRequest req) {
        //System.out.println("Getting hobbies for: "+jwtTokenService.getUsername(jwtTokenService.resolveToken(req)));
        return new ResponseEntity<>(
                new MatchDTO[]{
                new MatchDTO("Martien Meiland","31612345678","Groningen","Nederland"),
                new MatchDTO("Hayo de Hond","31612345679","Groningen","Nederland")
                },
                headers, HttpStatus.OK);
    }
}
