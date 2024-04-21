package networking.networking.user;

import jakarta.persistence.*;
import lombok.*;
import networking.networking.country.Country;
import networking.networking.education.Education;
import networking.networking.messages.Message;
import networking.networking.skill.Skill;
import networking.networking.workExperience.WorkExperience;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
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
    private String profilePicturePath;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    private String city;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Education> educations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<WorkExperience> workExperiences;

    @ManyToMany
    private Set<Skill> skills;

    @ManyToMany
    private Set<Skill> interests;

    @ManyToMany(fetch = FetchType.EAGER)
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
