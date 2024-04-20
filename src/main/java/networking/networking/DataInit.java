package networking.networking;

import lombok.RequiredArgsConstructor;
import networking.networking.country.Country;
import networking.networking.country.CountryRepository;
import networking.networking.enums.CountryEnum;
import networking.networking.enums.SkillEnum;
import networking.networking.skill.Skill;
import networking.networking.skill.SkillRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit implements ApplicationRunner {
    private final CountryRepository countryRepository;
    private final SkillRepository skillRepository;

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

    }
}
