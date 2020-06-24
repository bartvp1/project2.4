package MeetUpAPI.controller;

import MeetUpAPI.dto.MatchDTO;
import MeetUpAPI.dto.UserResponseDTO;
import MeetUpAPI.errorHandling.CustomException;
import MeetUpAPI.service.JwtTokenService;
import MeetUpAPI.service.DBService;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private DBService dbService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/{username}")
    public UserResponseDTO searchUser(@PathVariable String username) {
        return modelMapper.map(dbService.search(username), UserResponseDTO.class);
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@ApiParam("Username") @PathVariable String username, HttpServletRequest req) {
        if(username.equals(jwtTokenService.getUsername(jwtTokenService.resolveToken(req))) && jwtTokenService.authenticatedRequest(req)) {
            dbService.delete(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new CustomException("No/Wrong Authorization header",HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("/me")
    public UserResponseDTO whoami(HttpServletRequest req) {
        return modelMapper.map(dbService.whoami(req), UserResponseDTO.class);
    }


    @GetMapping("/me/matches")
    public ResponseEntity<MatchDTO[]> myMatches(HttpServletRequest req) {
        //System.out.println("Getting hobbyset for: "+jwtTokenService.getUsername(jwtTokenService.resolveToken(req)));
        return new ResponseEntity<>(
                new MatchDTO[]{
                        new MatchDTO("Martien Meiland","31612345678","Groningen","Nederland"),
                        new MatchDTO("Bart Meiland","31612345678","Groningen","Nederland"),
                        new MatchDTO("Hayo Meiland","31612345678","Groningen","Nederland"),
                        new MatchDTO("Maurice Meiland","31612345678","Groningen","Nederland"),
                        new MatchDTO("Singh Meiland","31612345678","Groningen","Nederland"),
                        new MatchDTO("Je moeders Meiland","31612345678","Groningen","Nederland"),
                        new MatchDTO("Hayo de Hond","31612345679","Groningen","Nederland")
                }, HttpStatus.OK);
    }

    @PostMapping("/me/hobbies/{id}")
    public ResponseEntity<String> addHobby(@PathVariable String id, HttpServletRequest req) {
        dbService.addHobby(Integer.parseInt(id),req);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/me/hobbies/{id}")
    public ResponseEntity<String> removeHobby(@PathVariable String id, HttpServletRequest req) {
        dbService.removeHobby(Integer.parseInt(id), req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
