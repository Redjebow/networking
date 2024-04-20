package networking.networking.event;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    public final EventRepository eventRepository;
    public final EventService eventService;

    @GetMapping("/add")
    public String addNewEvent(Model model){
        return eventService.showAddEventForm(model);
    }

    @PostMapping("/add")
    public String saveEvent(@ModelAttribute @Valid Event event, BindingResult bindingResult, Model model){
        return eventService.saveEvent(event, bindingResult, model);
    }

    @GetMapping("/all")
    public String getAllCommunityRoleUser(Model model){
        model.addAttribute("events", eventRepository.findAll());
        return "all-events";
    }
}
