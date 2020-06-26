package MeetUpAPI.controller;

import MeetUpAPI.dbModels.Hobby;
import MeetUpAPI.dbModels.User;
import MeetUpAPI.dto.MatchDTO;
import MeetUpAPI.dto.UserRegistrationDTO;
import MeetUpAPI.dto.UserResponseDTO;
import MeetUpAPI.errorHandling.CustomException;
import MeetUpAPI.service.JwtTokenService;
import MeetUpAPI.service.DBService;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


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

    @PutMapping("/me")
    public ResponseEntity<String> updateAccountDetails(@Valid @RequestBody UserRegistrationDTO newDetails, HttpServletRequest req) {
        return new ResponseEntity<>(dbService.updateUser(newDetails, req),HttpStatus.OK);
    }


    @GetMapping("/me/matches")
    public ResponseEntity<ArrayList<MatchDTO>> myMatches(HttpServletRequest req) {
        ArrayList<MatchDTO> arr = new ArrayList<>();

        //unpack jwt
        String[] split_string = jwtTokenService.resolveToken(req).split("\\.");
        Base64 base64Url = new Base64(true);
        JSONObject jsonObj = new JSONObject(new String(base64Url.decode(split_string[1])));
        String user = jsonObj.getString("sub");
        User userObject = dbService.search(user);
        Set<Hobby> userHobbies = userObject.getHobbySet();
        if (userObject.getActive() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (User users : dbService.getAllUsers()) {
            if (!users.getHobbySet().isEmpty() && !(user.equals(users.getUsername())) && users.getActive() == 1) {
                Set<Hobby> h = new HashSet<>(userHobbies);
                h.retainAll(users.getHobbySet());
                if (h.size() > 0) {
                    arr.add(new MatchDTO(users.getFirstname(), users.getPhone(), users.getCity(), users.getCountry(), users.getHobbySet(),h));
                }
            }
        }
        return new ResponseEntity<>( arr, HttpStatus.OK);
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
