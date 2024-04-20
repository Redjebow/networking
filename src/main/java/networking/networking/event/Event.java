package networking.networking.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import networking.networking.user.User;

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

    @NotBlank
    private String city;

    @NotBlank
    private String address;

    @NotBlank
    private LocalDateTime date;

    @NotBlank
    private String topic;

    @NotBlank
    private String organizer;

    @NotBlank
    private int duration; // hours

    @NotBlank
    private String phoneNumber;

    private int capacity; // TODO - use it with tickets or not?
}
