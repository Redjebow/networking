package networking.networking.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private
    UserRepository userRepository;
 @Override
    public UserDetails loadUserByUsername(String userNameOrEmail)
            throws UsernameNotFoundException {
        User userByUsername = userRepository.getUserByUsername(userNameOrEmail);
        User userByEmail = userRepository.getUserByEmail(userNameOrEmail);

        if (userByUsername != null) {
            return new MyUserDetails(userByUsername);
        } else if (userByEmail != null) {
            return new MyUserDetails(userByEmail);
        }
        throw new UsernameNotFoundException("Could not find user");
    }
}
