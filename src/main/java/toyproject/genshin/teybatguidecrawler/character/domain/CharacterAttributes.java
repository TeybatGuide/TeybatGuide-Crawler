package toyproject.genshin.teybatguidecrawler.character.domain;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import toyproject.genshin.teybatguidecrawler.common.JsonReader;
import toyproject.genshin.teybatguidecrawler.common.domain.value.Country;

import java.io.IOException;
import java.util.List;

@Slf4j
public record CharacterAttributes(
        String name, String country, String element, int star, String WeaponType
) {

    private static List<String> checkList = List.of("신의 눈", "무기");

    public static CharacterAttributes parseElements(Document document) throws IOException {
        Elements select = getElements(document);

        for (Element element : select) {
            if (checkCharacterInfo(element)) {

            }

        }

        return new CharacterAttributes(null, null, null, 0, null);
    }

    private static Elements getElements(Document document) {
        return document
                .selectXpath("//*[@id=\"app\"]/div/div[2]/article/div[2]/div/div[4]/div/div[2]/div[4]/div[3]/div[3]/table/tbody")
                .select("tr");
    }

    private static boolean checkCharacterInfo(Element element) {
        if(!checkExceptKeywords(element)) {
            return false;
        }

        if(checkInfo(element) || checkCountry(element)) {
            return true;
        }

        return false;
    }

    private static boolean checkExceptKeywords(Element element) {
        for(String s : JsonReader.getExceptKeywords()) {
            if(element.text().contains(s)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkInfo(Element element) {
        for (String s : checkList) {
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
