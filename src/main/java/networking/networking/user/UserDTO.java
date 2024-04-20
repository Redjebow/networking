package networking.networking.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import networking.networking.country.Country;
import networking.networking.skill.Skill;

import java.util.Set;

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
    private int telephone;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Skill> getInterest() {
        return interest;
    }

    public void setInterest(Set<Skill> interest) {
        this.interest = interest;
    }
}
