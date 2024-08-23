package toyproject.genshin.teybatguidecrawler.common.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Stars {

    FIVE("5성", 1, 5),
    FOUR("4성", 2, 4),
    THREE("3성", 3, 3),
    TWO("2성", 4, 2),
    ONE("1성", 5, 1),
    OTHER("미정", 0, 0);

    private final String starsName;
    private final int priority;
    private final int num;

    private static final Map<Integer, String> STAR_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Stars::getNum, Stars::name)));

    public static Stars of(final int num) {
        return Stars.valueOf(STAR_MAP.get(num));
    }

}
