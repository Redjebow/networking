package networking.networking.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public UserDTO makeCryptedPassword(UserDTO userDTO) {
        String hashsetPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(hashsetPassword);
        return userDTO;
    }

    public void saveUserRoleUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean cherForExistUserName(UserDTO userDTO) {
        List<User> optionalUser = (List<User>) userRepository.findAll();
        for (User currentUser : optionalUser) {
            if (currentUser.getUsername().equalsIgnoreCase(userDTO.getUsername())) {
                return true;
            }
        }
        return false;

    }
}
