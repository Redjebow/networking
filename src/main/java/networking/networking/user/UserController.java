package networking.networking.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import networking.networking.country.CountryRepository;
import networking.networking.education.EducationRepository;
import networking.networking.enums.RequestStatus;
import networking.networking.enums.SkillEnum;
import networking.networking.friendRequest.FriendRequest;
import networking.networking.friendRequest.FriendRequestRepository;
import networking.networking.skill.SkillRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;
    public final UserMapper userMapper;
    public final UserRepository userRepository;
    public final CountryRepository countryRepository;
    private final EducationRepository educationRepository;
    private final SkillRepository skillRepository;
    private final FriendRequestRepository friendRequestRepository;

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

    @GetMapping("/{id}/delete")  // TODO - move ID after delete --> /delete/{id} + fix thymeleaf
    public ModelAndView deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ModelAndView("result");
    }

    @GetMapping("/{id}/edit") // TODO - move ID after delete --> /delete/{id} + fix thymeleaf
    public String editUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("country", countryRepository.findAll());
        return "editUser";
    }

    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.getUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/bySkill")
    public String getFilmsByGenre(@RequestParam("skill") List<Long> id, Model model) {
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("users", userService.getSortedList(id));
        return "sorted-users";
    }

    @GetMapping("/{id}/profile") // TODO - move ID after delete --> /delete/{id} + fix thymeleaf
    public String getUserProfile(@PathVariable Long id, Model model, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User currentUser = myUserDetails.getUser();
        if(userRepository.findById(id).isPresent()) {
            if (userService.isInFriendsList(userRepository.findById(id).get(), currentUser.getFriends()))
                model.addAttribute("friends","Friends");
        }
        model.addAttribute("user", userRepository.findById(id).orElseThrow());
        return "user-profile";
    }

    @PostMapping("/sendFriendRequest/{id}")
    public String sendFriendRequest(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        return userService.sendFriendRequest(id, authentication, redirectAttributes);
    }

    @GetMapping("/showFriendRequests")
    public String showFriendRequests(Model model, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User currentUser = userRepository.getUserByUsername(myUserDetails.getUsername());
        Set<FriendRequest> requesters = friendRequestRepository.findByRecipientAndStatus(currentUser, RequestStatus.PENDING);
//        Set<FriendRequest> friends = friendRequestRepository.findByRecipientAndStatus(currentUser, RequestStatus.ACCEPTED);
        model.addAttribute("requesters", requesters);
        FriendRequest friendRequest = new FriendRequest();
        // for each - <hr> <button -> ACCEPTED / REJECTED -- @PathVariable Long id
        // currentUser.setFriends(currentUser.getFriends().add(id)); // will save in a DB table users-friends
        model.addAttribute("friends", currentUser.getFriends());
        // for each - // START CHAT -> @Get (form + msg - if any) text field + button -> post method
        // set interval ( javascript ) seconds refresh
        return "friends-and-requests";
    }


    @PostMapping("/acceptNewFriend/{id}")
    public String acceptNewFriend(@PathVariable Long id, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User currentUser = userRepository.getUserByUsername(myUserDetails.getUsername());
        User requester;
        if (userRepository.findById(id).isPresent()) {
            requester = userRepository.findById(id).get();
            if (friendRequestRepository.findByRecipientAndSenderAndStatus(currentUser, requester, RequestStatus.PENDING).isPresent()) {
                FriendRequest currentFriendRequest = friendRequestRepository.findByRecipientAndSenderAndStatus(currentUser, requester, RequestStatus.PENDING).get();
                currentFriendRequest.setStatus(RequestStatus.ACCEPTED);
                friendRequestRepository.save(currentFriendRequest);
            }
            Set<User> currentUserFriends = currentUser.getFriends();
            Set<User> requesterFriends = requester.getFriends();
            currentUserFriends.add(requester);
            currentUser.setFriends(currentUserFriends);
            requesterFriends.add(currentUser);
            requester.setFriends(requesterFriends);
            userRepository.save(currentUser);
            userRepository.save(requester);
        }
        return "redirect:/users/showFriendRequests";
    }

    @PostMapping("/rejectNewFriend/{id}")
    public String rejectNewFriend(@PathVariable Long id, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User currentUser = userRepository.getUserByUsername(myUserDetails.getUsername());
        User requester;
        if (userRepository.findById(id).isPresent()) {
            requester = userRepository.findById(id).get();
            if (friendRequestRepository.findByRecipientAndSenderAndStatus(currentUser, requester, RequestStatus.PENDING).isPresent()) {
                FriendRequest currentFriendRequest = friendRequestRepository.findByRecipientAndSenderAndStatus(currentUser, requester, RequestStatus.PENDING).get();
                currentFriendRequest.setStatus(RequestStatus.REJECTED);
                friendRequestRepository.save(currentFriendRequest);
            }
        }
        return "redirect:/users/showFriendRequests";
    }

    @GetMapping("/chat/{id}")
    public String openChatForm(@PathVariable Long id, Authentication authentication){
        return "message";
    }

}