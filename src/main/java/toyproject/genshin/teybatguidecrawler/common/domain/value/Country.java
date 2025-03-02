package toyproject.genshin.teybatguidecrawler.common.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Country {

    MONDSTADT("몬드"),
    LIYUE("리월"),
    INAZUMA("이나즈마"),
    SUMERU("수메르"),
    FONTAINE("폰타인"),
    SNEZHNAYA("스네즈나야"),
    NATLAN("나타"),
    OTHER("기타");

    private final String countryName;

    public static List<String> getCountries() {
        return Arrays.stream(Country.values()).map(Country::getCountryName).toList();
    }

}
