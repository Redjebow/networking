package networking.networking.event;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import networking.networking.skill.SkillRepository;
import networking.networking.user.MyUserDetails;
import networking.networking.user.User;
import networking.networking.user.UserRepository;
import networking.networking.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    public final EventRepository eventRepository;
    public final EventService eventService;
    public final UserRepository userRepository;
    public  final UserService userService;
    public final SkillRepository skillRepository;

    @GetMapping("/add")
    public String addNewEvent(Model model){
        return eventService.showAddEventForm(model);
    }

    @PostMapping("/add")
    public String saveEvent(@ModelAttribute @Valid Event event, BindingResult bindingResult, Model model){
        return eventService.saveEvent(event, bindingResult, model);
    }

    @GetMapping("/all")
    public String showAllEvents(Model model){
        return eventService.showEventsSortedByDate(model);
    }
    @GetMapping("/{id}/events")
    public String getUserProfile(@PathVariable Long id, Model model){
        Event event = eventRepository.findById(id).orElseThrow();
        model.addAttribute("selectedEvent", event);
        model.addAttribute("users",event.getUsers());
        return "events";
    }
    @PostMapping("/{id}/events")
    public String addUserInEvent(@PathVariable Long id, Model model, Authentication authentication){
        Event event = eventRepository.findById(id).orElseThrow();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        event.getUsers().add(user);
        model.addAttribute("selectedEvent",event);
        model.addAttribute("users", event.getUsers());
        eventRepository.save(event);
        return "events";
    }
    @GetMapping("/{id}/users")
    public String showAllUsersInEvent(@PathVariable Long id,Model model, Authentication authentication) {
        Event event = eventRepository.findById(id).orElseThrow();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        List<User> userList = new ArrayList<>(event.getUsers());
        model.addAttribute("selectedEvent",event);
        model.addAttribute("users",userService.sortUsersByInterest(user, userList));
        model.addAttribute("skills", skillRepository.findAll());
        return "all-event-users";
    }
    @PostMapping("/{id}/bySkill")
    public String getFilmsByGenre(@RequestParam("skill") List<Long> idSkill, @PathVariable Long id,Authentication authentication, Model model) {
        Event event = eventRepository.findById(id).orElseThrow();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        List<User> userList = new ArrayList<>(event.getUsers());
        List<User> userByEvent = userService.getSortedList(idSkill,userList);
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("users", userByEvent);
        model.addAttribute("selectedEvent", event);
        return "all-event-users";
    }
}
