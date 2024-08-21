package toyproject.genshin.teybatguidecrawler.character.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import toyproject.genshin.teybatguidecrawler.character.domain.value.Element;
import toyproject.genshin.teybatguidecrawler.common.domain.BaseEntity;
import toyproject.genshin.teybatguidecrawler.common.domain.value.Country;
import toyproject.genshin.teybatguidecrawler.common.domain.value.Domain;
import toyproject.genshin.teybatguidecrawler.common.domain.value.Stars;
import toyproject.genshin.teybatguidecrawler.common.domain.value.WeaponType;

@Getter
@Entity
@Table(name = "characters")
public class Characters extends BaseEntity {

    @Column(nullable = false, length = 30)
    private String characterName;

    @Column(length = 100)
    private String characterImage;

    @Column(length = 100)
    private String characterImage2;

    @Enumerated(EnumType.STRING)
    private Stars stars;

    @Enumerated(EnumType.STRING)
    private Element element;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    private WeaponType weaponType;

    protected Characters() {
        super(Domain.CHARACTER);
    }

    @Builder
    public Characters(String characterName, String characterImage, String characterImage2, Stars stars, Element element, Country country, WeaponType weaponType) {
        this();
        this.characterName = characterName;
        this.characterImage = characterImage;
        this.characterImage2 = characterImage2;
        this.stars = stars;
        this.element = element;
        this.country = country;
        this.weaponType = weaponType;
    }

    public static Characters of(@NotNull CharacterAttributes attributes) {
        return Characters.builder()
                .characterName(attributes.name())
                .country(Country.valueOf(attributes.country()))
                .stars(Stars.of(attributes.star()))
                .element(Element.of(attributes.element()))
                .weaponType(WeaponType.of(attributes.WeaponType()))
                .build();
    }

}
