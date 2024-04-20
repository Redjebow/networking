package networking.networking.event;

import lombok.RequiredArgsConstructor;
import networking.networking.user.UserDTO;
import networking.networking.user.UserMapper;
import networking.networking.user.UserRepository;
import networking.networking.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    public final EventRepository eventRepository;
    public final EventService eventService;

    @GetMapping("/add")
    public String addNewEvent(Model model){
        model.addAttribute("event", new Event());
        return "event-register";
    }

    @GetMapping("/all")
    public String getAllCommunityRoleUser(Model model){
        model.addAttribute("events", eventRepository.findAll());
        return "all-events";
    }
}
