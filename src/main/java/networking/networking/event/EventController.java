package networking.networking.event;

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
public class EventController {
    public EventRepository eventRepository;
    public EventService eventService;
    public EventController(EventRepository eventRepository, EventService eventService){
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }
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
