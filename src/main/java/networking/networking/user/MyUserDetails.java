package networking.networking.user;

import lombok.Getter;
import networking.networking.country.Country;
import networking.networking.education.Education;
import networking.networking.messages.Message;
import networking.networking.skill.Skill;
import networking.networking.workExperience.WorkExperience;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
public class MyUserDetails implements UserDetails {

    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getFirstName(){return  user.getFirstName();}

    public String getLastName(){return user.getLastName();}
    public String getRole(){return user.getRole();}
    public String getEmail(){return user.getEmail();}
    public String getPhoneNumber(){return user.getPhoneNumber();}
    public Country getCountry(){return  user.getCountry();}
    public  String getCity(){return  user.getCity();}
    public Set<Skill>getSkills(){return user.getSkills();}
    public Set<Skill>getInterests(){return user.getInterests();}
    public Set<Education> getEducations(){
        return user.getEducations();
    }
    public Set<WorkExperience> getWorkExperiences(){
        return user.getWorkExperiences();
    }
    public List<Message> getSentMessages(){
        return user.getSentMessages();
    }
    public List<Message> getReceivedMessages(){
        return user.getReceivedMessages();
    }
    public String getDescription(){
        return user.getDescription();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public Long getId() { return user.getId();  }

}
