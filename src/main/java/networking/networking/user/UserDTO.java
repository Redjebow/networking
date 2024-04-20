package networking.networking.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import networking.networking.country.Country;
import networking.networking.skill.Skill;

import java.util.Set;

@Getter
@Setter
public class UserDTO {
    @NotNull
    @Size(min = 3, max = 10)
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String rePassword;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 2, max = 15)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 15)
    private String lastName;
    private String phoneNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Country country;
    @NotNull
    private String city;
    @NotNull
    @ManyToMany
    private Set<Skill> skills;
    @NotNull
    @ManyToMany
    private Set<Skill> interest;

}
