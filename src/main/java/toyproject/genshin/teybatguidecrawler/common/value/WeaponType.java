package toyproject.genshin.teybatguidecrawler.common.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeaponType {

    SWORDS("한손검"),
    CLAYMORES("양손검"),
    POLEARMS("장병기"),
    CATALYSTS("법구"),
    BOWS("활");

    private final String weaponTypeName;

}
