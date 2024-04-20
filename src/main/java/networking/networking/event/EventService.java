package networking.networking.event;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public String showAddEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event-register";
    }

    public String saveEvent(Event event, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("event", event);
            return "event-register";
        }
        if(!isFutureDate(event.getDate())){
            model.addAttribute("incorrectDate","You should pick a future date!");
            model.addAttribute("event", event);
            return "event-register";
        }
        eventRepository.save(event);
        return "event-register";
    }

    private boolean isFutureDate(LocalDateTime localDateTime) {
        LocalDate now = LocalDate.now();
        LocalDate dateToCheck = localDateTime.toLocalDate();
        return dateToCheck.isAfter(now);
    }
}
