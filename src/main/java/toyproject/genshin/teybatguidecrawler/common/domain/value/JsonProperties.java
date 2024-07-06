package toyproject.genshin.teybatguidecrawler.common.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JsonProperties {

    EXCEPT_KEYWORDS("except-keywords"),
    PARSING_LIST("parsing-list"),;

    private final String key;

}
