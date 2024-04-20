package networking.networking.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import networking.networking.country.Country;
import networking.networking.education.Education;
import networking.networking.messages.Message;
import networking.networking.skill.Skill;
import networking.networking.workExperience.WorkExperience;

import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String password;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    private String city;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Education> educations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<WorkExperience> workExperiences;

    @ManyToMany
    private Set<Skill> skills;

    @ManyToMany
    private Set<Skill> interests;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends;

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages;
}
