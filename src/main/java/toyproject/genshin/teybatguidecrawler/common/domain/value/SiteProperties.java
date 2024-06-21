package toyproject.genshin.teybatguidecrawler.common.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SiteProperties {

    CHARACTER("site.genshin.character"),
    WEAPON("");

    private final String propertyLocation;

}
