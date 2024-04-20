package networking.networking.user;

import jakarta.validation.Valid;
import networking.networking.country.CountryRepository;
import networking.networking.enums.SkillEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/users")
public class UserController {
    public UserService userService;
    public UserMapper userMapper;
    public UserRepository userRepository;
    public CountryRepository countryRepository;

    public UserController(UserService userService, UserMapper userMapper, UserRepository userRepository, CountryRepository countryRepository) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return userService.uploadImage(file);
    }

    @GetMapping("/add")
    public String registerNewUser(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("skills", SkillEnum.values());
        return "user-register";
    }

    @GetMapping("/all")
    public String showAllUsers(Model model, Authentication authentication) {
        return userService.showAllUsersSortedByInterests(model, authentication);
    }

    @PostMapping("/add")
    public String submitUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        return userService.saveUser(userDTO, bindingResult, model, redirectAttributes);
    }


    @GetMapping("/details")
    public String getUserDetails(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        model.addAttribute("user", user);
        return "user-profile";
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ModelAndView("result");
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("country", countryRepository.findAll());
        return "editUser";
    }
}