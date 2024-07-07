package toyproject.genshin.teybatguidecrawler.character.domain;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import toyproject.genshin.teybatguidecrawler.common.JsonReader;
import toyproject.genshin.teybatguidecrawler.common.domain.value.Country;
import toyproject.genshin.teybatguidecrawler.common.domain.value.JsonProperties;
import toyproject.genshin.teybatguidecrawler.exception.crawlerException.DocumentParseException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public record CharacterAttributes(
        String name, String country, String element, int star, String WeaponType
) {
    private static Map<String, String> map;

    public static CharacterAttributes parseElements(Document document) throws IOException {
        map = new ConcurrentHashMap<>();

        Elements select = getElements(document);
        String name = parseName(document);

        try {
            assert select != null;
            select.stream()
                    .filter(CharacterAttributes::checkCharacterInfo)
                    .forEach(CharacterAttributes::putCharacterInfoMap);

        } catch (NullPointerException | AssertionError e) {
            log.error("parsing error => {}", e.getMessage());
            throw new DocumentParseException(e.getMessage());
        }

        return new CharacterAttributes(name, map.get("소속"), map.get("신의 눈"), 0, map.get("무기"));
    }

    private static Elements getElements(Document document) {
        Element characterInfoTable = parseCharacterInfoTable(document);

        if (characterInfoTable != null) {
            log.debug("characterInfoTable: {}", characterInfoTable.text());
            return characterInfoTable.select("tr");
        }

        return null;
    }

    private static boolean isCharacterInfoTable(Element element) {
        return element.text().contains("본명") && element.text().contains("무기");
    }

    private static String parseName(Document document) {
        return document.select("h1 > a > span").text().replace("(원신)", "");
    }

    private static Element parseCharacterInfoTable(Document document) {
        Elements select = document.select("div > div > div > table > tbody");
        return select.stream()
                .filter(CharacterAttributes::isCharacterInfoTable)
                .findFirst()
                .orElse(null);
    }

    private static void putCharacterInfoMap(Element element) {
        String text = element.text();
        log.debug(text);
        String[] s = text.split(" ");

        if (!text.contains("소속") && !text.contains("신의 눈")) {
            map.put(s[0], s[1]);
        } else if (text.contains("신의 눈")) {
            map.put("신의 눈", text.replace("신의 눈 ", ""));
        } else if (text.contains("고대 용의 대권")) {
            map.put("신의 눈", "물");
        } else if (text.contains("신의 심장")) {
            map.put("신의 눈", text.replace("신의 심장 ", ""));
        } else {
            String attr = element.selectFirst("a").attr("title");
            map.put(s[0], attr);
        }
    }

    private static boolean checkCharacterInfo(Element element) {
        if (!checkExceptKeywords(element)) {
            return false;
        }

        if (checkInfo(element) || checkCountry(element)) {
            return true;
        }

        return false;
    }

    private static boolean checkExceptKeywords(Element element) {
        for (String s : JsonReader.getKeywords(JsonProperties.EXCEPT_KEYWORDS)) {
            if (element.text().contains(s)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkInfo(Element element) {
        for (String s : JsonReader.getKeywords(JsonProperties.PARSING_LIST)) {
            if (element.text().contains(s)) {
                return true;
            }
        }

        return false;
    }

    private static boolean checkCountry(Element element) {
        for (String country : Country.getCountries()) {
            String attr = element.select("a").attr("title");

            if (attr.contains(country)) {
                return true;
            }
        }

        return false;
    }

}
