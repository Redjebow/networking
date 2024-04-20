package networking.networking.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import networking.networking.country.Country;
import networking.networking.education.Education;
import networking.networking.skill.Skill;
import networking.networking.workExperience.WorkExperience;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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
    @Size(max = 250)
    private String description;
    @NotNull
    @ManyToMany
    private Set<Skill> skills;
    @NotNull
    @ManyToMany
    private Set<Skill> interest;

    // SCHOOL
    private String schoolName;
    private LocalDate startDate;
    private LocalDate endDate;

    // WORK
    private String companyName;
    private LocalDate startDateWork;
    private LocalDate endDateWork;
    private String position;
    private String profilePicturePath;

}
