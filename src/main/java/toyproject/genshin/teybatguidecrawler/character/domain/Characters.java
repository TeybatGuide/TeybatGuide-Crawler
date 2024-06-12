package toyproject.genshin.teybatguidecrawler.character.domain;

import jakarta.persistence.*;
import lombok.Getter;
import toyproject.genshin.teybatguidecrawler.character.domain.value.Element;
import toyproject.genshin.teybatguidecrawler.common.BaseEntity;
import toyproject.genshin.teybatguidecrawler.common.value.Country;
import toyproject.genshin.teybatguidecrawler.common.value.Domain;
import toyproject.genshin.teybatguidecrawler.common.value.Stars;
import toyproject.genshin.teybatguidecrawler.common.value.WeaponType;

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

}
