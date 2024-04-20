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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.json.JSONObject;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public static final String UPLOAD_DIRECTORY = "C:/app_data";

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


    public String saveUser(UserDTO userDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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

        User user = userMapper.toEntity(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
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
        redirectAttributes.addFlashAttribute("success_account_created", "Account created successfully!");
        return "redirect:/login";
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

    public ResponseEntity<?> uploadImage(MultipartFile file) throws IOException {
        StringBuilder nameOfUploadedFile = new StringBuilder();

        if (!Files.exists(Path.of(UPLOAD_DIRECTORY))) {
            Files.createDirectories(Path.of(UPLOAD_DIRECTORY));
        }

        String originalFileName = file.getOriginalFilename();
        nameOfUploadedFile.append(originalFileName);

        String nameOfFileToSave = generateUUIDFileName(originalFileName); // will keep the same extension

        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, nameOfFileToSave);

        Files.write(fileNameAndPath, file.getBytes());

        JSONObject response = new JSONObject();
        response.put("img_name", nameOfFileToSave);

        return ResponseEntity.ok(response.toString());
    }

    private String generateUUIDFileName(String originalFileName) {
        String extension = getFileExtension(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";  // TODO raise new error - pass it back to the frontend - no extension found...
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
