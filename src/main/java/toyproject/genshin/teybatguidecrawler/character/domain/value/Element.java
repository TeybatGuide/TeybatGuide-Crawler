package toyproject.genshin.teybatguidecrawler.character.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.genshin.teybatguidecrawler.common.domain.value.Stars;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Element {

    ANEMO("바람", "https://wiki.hoyolab.com/_nuxt/img/anemo.e0f1804.png"),
    GEO("바위", "https://wiki.hoyolab.com/_nuxt/img/geo.2498e06.png"),
    ELECTRO("전기", "https://wiki.hoyolab.com/_nuxt/img/electro.be07020.png"),
    DENDRO("풀", "https://wiki.hoyolab.com/_nuxt/img/dendro.88f5bfa.png"),
    HYDRO("물", "https://wiki.hoyolab.com/_nuxt/img/hydro.3e969aa.png"),
    PYRO("불", "https://wiki.hoyolab.com/_nuxt/img/pyro.2267e27.png"),
    CRYO("얼음", "https://wiki.hoyolab.com/_nuxt/img/cryo.b810caa.png");

    private final String elementName;
    private final String elementImage;

    private static final Map<String, String> ELEMENT_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Element::getElementName, Element::name)));

    public static Element of(final String name) {
        return Element.valueOf(ELEMENT_MAP.get(name));
    }

}
