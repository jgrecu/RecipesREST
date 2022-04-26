package recipes.service;

import org.springframework.stereotype.Service;
import recipes.dao.UserRequest;
import recipes.model.User;
import recipes.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addUser(UserRequest userRequest) {
        Optional<User> userByUsername = userRepository.findUserByUsername(userRequest.getEmail());
        if (userByUsername.isPresent()) {
            return false;
        }
        User user = User.builder()
                .username(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
        userRepository.save(user);
        return true;
    }
}
