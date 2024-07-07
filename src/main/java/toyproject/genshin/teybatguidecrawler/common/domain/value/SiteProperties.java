package toyproject.genshin.teybatguidecrawler.common.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SiteProperties {

    BASE("site.base-url"),
    CHARACTER("site.genshin.character"),
    WEAPON("site.genshin.weapon");

    private final String propertyLocation;

}
