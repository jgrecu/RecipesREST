package recipes.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.dao.UserRequest;
import recipes.model.User;
import recipes.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean addUser(UserRequest userRequest) {
        Optional<User> userByUsername = userRepository.findUserByUsername(userRequest.getEmail());
        if (userByUsername.isPresent()) {
            return false;
        }

        User user = User.builder()
                .username(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role("ROLE_USER")
                .build();
        userRepository.save(user);
        return true;
    }
}
