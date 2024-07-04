package toyproject.genshin.teybatguidecrawler.character.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

}
