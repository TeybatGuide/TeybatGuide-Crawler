package toyproject.genshin.teybatguidecrawler.common.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Stars {

    FIVE("5성", 1),
    FOUR("4성", 2),
    THREE("3성", 3),
    TWO("2성", 4),
    ONE("1성", 5);

    private final String starsName;
    private final int priority;

}
