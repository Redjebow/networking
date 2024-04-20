package networking.networking.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import networking.networking.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    private Set<User> users;

    @NotBlank(message = "Please enter a city for your event")
    private String city;

    @NotBlank(message = "Please enter an address for your event")
    private String address;

    @NotNull(message = "Please choose a date of the event")
    private LocalDateTime date;

    @NotBlank(message = "Please enter the topic of the event")
    private String topic;

    @NotBlank(message = "Please enter the organizer's names")
    private String organizer;

    @Min(value = 10, message = "Duration must be 10 minutes or above!")
    private int duration; // minutes

    @Size(min = 6, message = "You must enter a phone number with length of 6 or above!")
    private String phoneNumber;

    @Min(value = 10, message = "Your event must have a capacity of 10 or above!")
    private int capacity; // TODO - use it with tickets or not?
}
