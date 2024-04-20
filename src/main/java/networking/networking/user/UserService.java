package networking.networking.user;

import networking.networking.country.CountryRepository;
import networking.networking.education.Education;
import networking.networking.education.EducationRepository;
import networking.networking.enums.SkillEnum;
import networking.networking.exceptions.UserNotFoundException;
import networking.networking.skill.Skill;
import networking.networking.skill.SkillRepository;
import networking.networking.workExperience.WorkExperience;
import networking.networking.workExperience.WorkExperienceRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private CountryRepository countryRepository;
    private UserMapper userMapper;
    private final WorkExperienceRepository workExperienceRepository;
    private final EducationRepository educationRepository;
    private final SkillRepository skillRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, UserMapper userMapper, CountryRepository countryRepository,
                       WorkExperienceRepository workExperienceRepository,
                       EducationRepository educationRepository,
                       SkillRepository skillRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.countryRepository = countryRepository;
        this.workExperienceRepository = workExperienceRepository;
        this.educationRepository = educationRepository;
        this.skillRepository = skillRepository;
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

    private List<User> getAllUsersExceptCurrent(User currentuser) {
        List<User> allUsers = (List<User>) userRepository.findAll();
        allUsers.remove(currentuser);
        return allUsers;
    }

    public String showAllUsersSortedByInterests(Model model, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User currentUser = userRepository.getUserByUsername(myUserDetails.getUsername());
        List<User> allUsersExceptCurrent = getAllUsersExceptCurrent(currentUser);

        List<User> sortedUsersByCurrentUserInterests = sortUsersByInterest(currentUser, allUsersExceptCurrent);

        model.addAttribute("users", sortedUsersByCurrentUserInterests);
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        return "all-users";
    }

    public List<User> sortUsersByInterest(User currentUser, List<User> allUsers) {
        Set<Skill> currentUserInterests = currentUser.getInterests();
        Map<User, Integer> userSkillsMatches = new HashMap<>();
        for (User user : allUsers) {
            int matches = countMatches(currentUserInterests, user.getSkills());
            userSkillsMatches.put(user, matches);

        }
        List<User> sortedUsers = userSkillsMatches.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return sortedUsers;

    }

    private int countMatches(Set<Skill> interests, Set<Skill> skills) {
        return (int) skills.stream()
                .filter(interests::contains)
                .count();
    }
    public List<User> getSortedList(Long id) {
        List<User> sortedUsers = new ArrayList<>();
        List<User> usersList = (List<User>) userRepository.findAll();
        for (User user : usersList) {
            for (Skill skill : user.getSkills()) {
                if (skill.getId()==id) {
                    sortedUsers.add(user);
                    break; // Намерили сме съвпадение, прекратяваме цикъла за уменията на този потребител
                }
            }
        }
        return sortedUsers;
    }
}
