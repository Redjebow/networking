package networking.networking.event;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String showAllEvents(Model model){
        return eventService.showEventsSortedByDate(model);
    }
    @GetMapping("/{id}/events")
    public String getUserProfile(@PathVariable Long id, Model model){
        model.addAttribute("selectedEvent", eventRepository.findById(id).orElse(null));
        return "events";
    }
}
