package MeetUpAPI.controller;

import MeetUpAPI.dto.MatchDTO;
import MeetUpAPI.dto.UserResponseDTO;
import MeetUpAPI.errorHandling.CustomException;
import MeetUpAPI.service.JwtTokenService;
import MeetUpAPI.service.UserService;
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
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/{username}")
    public UserResponseDTO search(@PathVariable String username) {
        return modelMapper.map(userService.search(username), UserResponseDTO.class);
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<String> delete(@ApiParam("Username") @PathVariable String username, HttpServletRequest req) {
        if(username.equals(jwtTokenService.getUsername(jwtTokenService.resolveToken(req))) && jwtTokenService.authenticatedRequest(req)) {
            userService.delete(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new CustomException("No/Wrong Authorization header",HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("/me")
    public UserResponseDTO whoami(HttpServletRequest req) {
        return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
    }


    @GetMapping("/me/matches")
    public ResponseEntity<MatchDTO[]> mymatches(HttpServletRequest req) {
        //System.out.println("Getting hobbyset for: "+jwtTokenService.getUsername(jwtTokenService.resolveToken(req)));
        return new ResponseEntity<>(
                new MatchDTO[]{
                        new MatchDTO("Martien Meiland","31612345678","Groningen","Nederland"),
                        new MatchDTO("Hayo de Hond","31612345679","Groningen","Nederland")
                }, HttpStatus.OK);
    }
}
