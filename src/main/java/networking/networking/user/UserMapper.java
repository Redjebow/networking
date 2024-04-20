package networking.networking.user;

import networking.networking.education.Education;
import networking.networking.workExperience.WorkExperience;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole("ROLE_USER");
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setCountry(userDTO.getCountry());
        user.setCity(userDTO.getCity());
        user.setSkills(userDTO.getSkills());
        user.setInterests(userDTO.getInterest());
        user.setDescription(userDTO.getDescription());
        user.setProfilePicturePath(userDTO.getProfilePicturePath());
        return user;
    }
}
