package toyproject.genshin.teybatguidecrawler.common.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum WeaponType {

    SWORDS("한손검"),
    CLAYMORES("양손검"),
    POLEARMS("장병기"),
    CATALYSTS("법구"),
    BOWS("활");

    private final String weaponTypeName;

    private static final Map<String, String> WEAPON_TYPE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(WeaponType::getWeaponTypeName, WeaponType::name)));

    public static WeaponType of(final String name) {
        return WeaponType.valueOf(WEAPON_TYPE_MAP.get(name));
    }
}
