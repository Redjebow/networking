package networking.networking.event;

import networking.networking.user.MyUserDetails;
import networking.networking.user.User;
import networking.networking.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
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
        return "redirect:/index";
    }

    private boolean isFutureDate(LocalDateTime localDateTime) {
        LocalDate now = LocalDate.now();
        LocalDate dateToCheck = localDateTime.toLocalDate();
        return dateToCheck.isAfter(now);
    }

    public String showEventsSortedByDate(Model model){
        model.addAttribute("events", eventRepository.findAllByOrderByDateAsc());
        return "events-all";
    }
    public boolean checkForExistingCapacity(Event event){
        return true;
    }

    public boolean isUserAlreadySubscribed(Event event, User user){
        Set<User> users = event.getUsers();
        for(User u : users){
            if(Objects.equals(u.getId(), user.getId())){
                return true;
            }
        }
        return false;
    }

}
