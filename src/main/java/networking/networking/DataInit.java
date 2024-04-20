package networking.networking;

import lombok.RequiredArgsConstructor;
import networking.networking.country.Country;
import networking.networking.country.CountryRepository;
import networking.networking.education.Education;
import networking.networking.education.EducationRepository;
import networking.networking.enums.CountryEnum;
import networking.networking.enums.SkillEnum;
import networking.networking.skill.Skill;
import networking.networking.skill.SkillRepository;
import networking.networking.user.User;
import networking.networking.user.UserRepository;
import networking.networking.workExperience.WorkExperience;
import networking.networking.workExperience.WorkExperienceRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInit implements ApplicationRunner {
    private final CountryRepository countryRepository;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EducationRepository educationRepository;
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (countryRepository.count() == 0) {
            for (CountryEnum countryEnum : CountryEnum.values()) {
                Country country = Country.builder().id(countryEnum.getId()).name(countryEnum.getLabel()).build();
                countryRepository.save(country);
            }
        }

        if (skillRepository.count() == 0) {
            for (SkillEnum skillEnum : SkillEnum.values()) {
                Skill skill = Skill.builder().id(skillEnum.getId()).name(skillEnum.getName()).build();
                skillRepository.save(skill);
            }
        }


        if (userRepository.count() == 0) {
            addUser1();
            addUser2();
        }
    }

    private void addUser1() {
        CountryEnum bgEnum = CountryEnum.BULGARIA;
        Country BG = Country.builder().id(bgEnum.getId()).name(bgEnum.getLabel()).build();

        List<SkillEnum> skillEnumList = List.of(SkillEnum.Java, SkillEnum.Python, SkillEnum.MongoDB);
        Set<Skill> skills = getSkillSet(skillEnumList);

        List<SkillEnum> interestsEnumList = List.of(SkillEnum.HTML, SkillEnum.CSS, SkillEnum.JavaScript);
        Set<Skill> interests = getSkillSet(interestsEnumList);

        User user = User.builder()
                .username("user")
                .password(bCryptPasswordEncoder.encode("123"))
                .role("Tester")
                .email("user@abv.bg")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("0888123456")
                .country(BG)
                .city("Razgrad")
                .skills(skills)
                .interests(interests)
                .build();
        userRepository.save(user);

        Education education1 = Education.builder()
                .schoolName("Vasil Levski - Razgrad")
                .startDate(LocalDate.of(2010, 9, 15))
                .endDate(LocalDate.of(2015, 6, 1))
                .user(user)
                .build();
        educationRepository.save(education1);

        Education education2 = Education.builder()
                .schoolName("Mehano - Razgrad")
                .startDate(LocalDate.of(2015, 9, 15))
                .endDate(LocalDate.of(2019, 6, 1))
                .user(user)
                .build();
        educationRepository.save(education2);

        WorkExperience workExperience1 = WorkExperience.builder()
                .companyName("Mlin - Razgrad")
                .position("Cleaner")
                .startDate(LocalDate.of(2022, 10, 1))
                .endDate(LocalDate.of(2022, 12, 15))
                .user(user)
                .build();

        WorkExperience workExperience2 = WorkExperience.builder()
                .companyName("Speedy - Razgrad")
                .position("IT support")
                .startDate(LocalDate.of(2023, 1, 1))
                .endDate(LocalDate.of(2023, 6, 30))
                .user(user)
                .build();

        workExperienceRepository.save(workExperience1);
        workExperienceRepository.save(workExperience2);
    }

    private void addUser2() {
        CountryEnum bgEnum = CountryEnum.BULGARIA;
        Country BG = Country.builder().id(bgEnum.getId()).name(bgEnum.getLabel()).build();

        List<SkillEnum> skillEnumList = List.of(SkillEnum.Java, SkillEnum.CSS, SkillEnum.HTML, SkillEnum.JavaScript);
        Set<Skill> skills = getSkillSet(skillEnumList);

        List<SkillEnum> interestsEnumList = List.of(SkillEnum.BusinessAnalysis, SkillEnum.MongoDB, SkillEnum.Python);
        Set<Skill> interests = getSkillSet(interestsEnumList);

        User user = User.builder()
                .username("user2")
                .password(bCryptPasswordEncoder.encode("123"))
                .role("Tester")
                .email("user2@abv.bg")
                .firstName("Test")
                .lastName("User2")
                .phoneNumber("0888123123")
                .country(BG)
                .city("Razgrad")
                .skills(skills)
                .interests(interests)
                .build();
        userRepository.save(user);

        Education education1 = Education.builder()
                .schoolName("Ikonomov - Razgrad")
                .startDate(LocalDate.of(2010, 9, 15))
                .endDate(LocalDate.of(2015, 6, 1))
                .user(user)
                .build();
        educationRepository.save(education1);

        Education education2 = Education.builder()
                .schoolName("Mehano - Razgrad")
                .startDate(LocalDate.of(2015, 9, 15))
                .endDate(LocalDate.of(2019, 6, 1))
                .user(user)
                .build();
        educationRepository.save(education2);

        WorkExperience workExperience1 = WorkExperience.builder()
                .companyName("Rompetrol")
                .position("Cashier")
                .startDate(LocalDate.of(2020, 10, 1))
                .endDate(LocalDate.of(2022, 12, 15))
                .user(user)
                .build();

        WorkExperience workExperience2 = WorkExperience.builder()
                .companyName("Speedy - Razgrad")
                .position("IT support")
                .startDate(LocalDate.of(2023, 2, 1))
                .endDate(LocalDate.of(2023, 8, 30))
                .user(user)
                .build();

        workExperienceRepository.save(workExperience1);
        workExperienceRepository.save(workExperience2);
    }

    private Set<Skill> getSkillSet(List<SkillEnum> skillEnumList) {
        Set<Skill> skills = new HashSet<>();
        for (SkillEnum skillEnum : skillEnumList) {
            Skill skill = new Skill(skillEnum.getId(), skillEnum.getName());
            skills.add(skill);
        }
        return skills;
    }
}
