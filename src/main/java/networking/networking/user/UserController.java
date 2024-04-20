package networking.networking.user;

import jakarta.validation.Valid;
import networking.networking.country.CountryRepository;
import networking.networking.enums.SkillEnum;
import networking.networking.event.Event;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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


    @GetMapping("/add")
    public String addUserUserRole(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("skills", SkillEnum.values());
        return "user-register";
    }

    @GetMapping("/all")
    public String registerNewUser(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("countries", countryRepository.findAll());
        return "all-users";
    }

    @PostMapping("/add")
    public String submitUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model) {
        return userService.saveUser(userDTO, bindingResult, model);
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