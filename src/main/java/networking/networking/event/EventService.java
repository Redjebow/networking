package networking.networking.event;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class EventService {

    EventRepository eventRepository;

    public String showAddEventForm(Model model){
        model.addAttribute("event", new Event());
        return "event-register";
    }

    public String saveEvent(Event event, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("event", event);
            return "event-register";
        }
        eventRepository.save(event);
        return "event-register";
    }
}
