package MeetUpAPI.service;

import javax.servlet.http.HttpServletRequest;

import MeetUpAPI.dbModels.Hobby;
import MeetUpAPI.dto.HobbyDTO;
import MeetUpAPI.dto.UserRegistrationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import MeetUpAPI.errorHandling.CustomException;
import MeetUpAPI.dbModels.User;
import MeetUpAPI.dbModels.UserRepository;


@Service
public class DBService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    public ModelMapper modelMapper;

    public String signin(String username, String password) {
        try {
            if (passwordEncoder.matches(password, userRepository.findByUsername(username).getPassword()))
                return tokenToJson(jwtTokenService.createToken(username));
            else{
                throw new CustomException("Password incorrect", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex){
            if(ex instanceof CustomException){
                throw ex;
            }
            throw new CustomException("Invalid username", HttpStatus.UNAUTHORIZED);
        }
    }

    public String signup(User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);

        if (userRepository.existsByPhone(user.getPhone()))
            throw new CustomException("Phone number already in use", HttpStatus.UNPROCESSABLE_ENTITY);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return tokenToJson(jwtTokenService.createToken(user.getUsername()));
    }

    public void logout(HttpServletRequest req) {
        jwtTokenService.logout(jwtTokenService.resolveToken(req));
    }


    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public User search(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public String updateUser(UserRegistrationDTO newDetails, HttpServletRequest req){
        User user = resolveUser(req);
        user.setUsername(newDetails.getUsername());
        user.setPassword(passwordEncoder.encode(newDetails.getPassword()));
        user.setPhone(newDetails.getPhone());
        user.setFirstname(newDetails.getFirstname());
        user.setLastname(newDetails.getLastname());
        user.setCity(newDetails.getCity());
        user.setCountry(newDetails.getCountry());
        userRepository.save(user);

        return tokenToJson(jwtTokenService.createToken(newDetails.getUsername()));

    }

    public void addHobby(int hobbyId, HttpServletRequest req) {
        User user = resolveUser(req);
        user.getHobbySet().add(modelMapper.map(new HobbyDTO(hobbyId),Hobby.class));
        userRepository.save(user);
    }

    public void removeHobby(int hobbyId, HttpServletRequest req) {
        User user = resolveUser(req);
        for (Hobby elem : user.getHobbySet()) {
            if (elem.getId() == hobbyId) {
                user.getHobbySet().remove(elem);
                break;
            }
        }
        userRepository.save(user);
    }

    public User whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenService.getUsername(jwtTokenService.resolveToken(req)));
    }

    public String refreshToken(HttpServletRequest req) {
        String username = jwtTokenService.getUsername(jwtTokenService.resolveToken(req));
        return tokenToJson(jwtTokenService.createToken(username));
    }

    public User resolveUser(HttpServletRequest req){
        return search(jwtTokenService.getUsername(jwtTokenService.resolveToken(req)));
    }

    private String tokenToJson(String token) {
        return "{\"token\":\"" + token + "\"}";
    }

}
