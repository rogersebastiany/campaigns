package br.com.uol.campaigns.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.uol.campaigns.service.TokenService;
import br.com.uol.campaigns.user.IUserRepository;
import br.com.uol.campaigns.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("")
    public ResponseEntity login(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        } else {
            if (BCrypt.verifyer().verify(userModel.getPassword().toCharArray(), user.getPassword()).verified) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                        tokenService.generateToken(userModel)
                );
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong password");
            }
        }
    }
}
