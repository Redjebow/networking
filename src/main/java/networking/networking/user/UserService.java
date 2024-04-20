package networking.networking.user;

import networking.networking.country.CountryRepository;
import networking.networking.education.Education;
import networking.networking.education.EducationRepository;
import networking.networking.enums.SkillEnum;
import networking.networking.exceptions.UserNotFoundException;
import networking.networking.workExperience.WorkExperience;
import networking.networking.workExperience.WorkExperienceRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private CountryRepository countryRepository;
    private UserMapper userMapper;
    private final WorkExperienceRepository workExperienceRepository;
    private final EducationRepository educationRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, UserMapper userMapper, CountryRepository countryRepository,
                       WorkExperienceRepository workExperienceRepository,
                       EducationRepository educationRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.countryRepository = countryRepository;
        this.workExperienceRepository = workExperienceRepository;
        this.educationRepository = educationRepository;
    }

    public UserDTO makeCryptedPassword(UserDTO userDTO) {
        String hashsetPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(hashsetPassword);
        return userDTO;
    }

    public String saveUser(UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            model.addAttribute("countries", countryRepository.findAll());
            model.addAttribute("skills", SkillEnum.values());
            return "user-register";
        }
        if (cherForExistUserName(userDTO)) {
            model.addAttribute("not_unique_name", "Username already exist");
            model.addAttribute("user", userDTO);
            model.addAttribute("countries", countryRepository.findAll());
            model.addAttribute("skills", SkillEnum.values());
            return "user-register";
        }
        if (!userDTO.getPassword().equals(userDTO.getRePassword())) {
            model.addAttribute("PasswordDoNotMatch", "Password Do Not Match");
            model.addAttribute("user", userDTO);
            model.addAttribute("countries", countryRepository.findAll());
            model.addAttribute("skills", SkillEnum.values());
            return "user-register";
        }
        User user = userMapper.toEntity(makeCryptedPassword(userDTO));
        userRepository.save(user);

        WorkExperience workExperience = new WorkExperience();
        workExperience.setCompanyName(userDTO.getCompanyName());
        workExperience.setStartDate(userDTO.getStartDateWork());
        workExperience.setEndDate(userDTO.getEndDateWork());
        workExperience.setUser(user);
        workExperienceRepository.save(workExperience);

        Education education = new Education();
        education.setSchoolName(userDTO.getSchoolName());
        education.setStartDate(userDTO.getStartDate());
        education.setEndDate(userDTO.getEndDate());
        education.setUser(user);
        educationRepository.save(education);

//        user.getWorkExperiences().add(workExperience);
//        user.getEducations().add(education);

//        return "index";
        return "redirect:/index";
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

    public void validateUserExist(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
    }
}
