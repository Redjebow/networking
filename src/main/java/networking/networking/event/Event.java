package networking.networking.event;

import jakarta.persistence.*;
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

    private String city;
    private String address;
    private LocalDateTime date;
    private String topic;

    private String organizer;
    private int duration; // hours
    private String phoneNumber;
    private int capacity; // TODO - use it with tickets or not?
}
