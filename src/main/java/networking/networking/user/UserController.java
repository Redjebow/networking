package networking.networking.user;

import jakarta.validation.Valid;
import networking.networking.country.Country;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserController(UserService userService, UserMapper userMapper){

        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Autowired
    UserRepository userRepository;

    @GetMapping("/add")
    public String addUserUserRole(Model model){
        model.addAttribute("user", new UserDTO());
        return "user-register";
    }

    @GetMapping("/users")
    public String getAllCommunityRoleUser(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }
    @PostMapping("/submitUser")
    public ModelAndView submitUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model ){
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            return new ModelAndView("user-register");
        }
        if(userService.cherForExistUserName(userDTO)) {
            model.addAttribute("not_unique_name", "Username already exist");
            model.addAttribute("user", userDTO);
            return new ModelAndView("user-register");
        }
        if(!userDTO.getPassword().equals(userDTO.getRePassword())){
            model.addAttribute("PasswordDoNotMatch", "Password Do Not Match");
            model.addAttribute("user", userDTO);
            return new ModelAndView("user-register");
        }

        return new ModelAndView("result");
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
        model.addAttribute("country", Country.values());
        return "editUser";
    }
}