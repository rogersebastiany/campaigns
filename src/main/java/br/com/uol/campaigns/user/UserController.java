package br.com.uol.campaigns.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("")
    public ResponseEntity create(@RequestBody UserModel userModel) {

        if (this.userRepository.findByUsername(userModel.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.userRepository.save(userModel)
        );

    }

}
