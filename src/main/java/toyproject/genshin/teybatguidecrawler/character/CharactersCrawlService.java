package toyproject.genshin.teybatguidecrawler.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import toyproject.genshin.teybatguidecrawler.character.domain.CharacterAttributes;
import toyproject.genshin.teybatguidecrawler.common.JsoupManager;
import toyproject.genshin.teybatguidecrawler.common.domain.YmlProperty;
import toyproject.genshin.teybatguidecrawler.common.domain.value.SiteProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharactersCrawlService {

    private final CharactersRepository charactersRepository;
    private final YmlProperty property;

    public List<CharacterAttributes> crawl() {
        String url = property.getProperty(SiteProperties.CHARACTER);
        log.info("character url: {}", url);

        List<CharacterAttributes> list = new LinkedList<>();

        try {
            Connection.Response response = JsoupManager.ConnectJsoupExecute(url);

            if (JsoupManager.isStatusOK(response)) {
                Elements elements = getElements(response.parse());
                log.debug("elements count => {}", elements.size());

                List<String> fullUrlList = makeFullUrlList(elements);

                return fullUrlList.stream()
                        .map(this::createCharacterAttributes)
                        .filter(Objects::nonNull)
                        .toList();
            } else {
                log.error("Http error: {}", response.statusCode());
            }

        } catch (IOException e) {
            log.error("🧨 {}", e.getMessage());
        }

        return list;
    }

    private CharacterAttributes createCharacterAttributes(String characterUrl) {

        try {
            Connection.Response res = JsoupManager.ConnectJsoupExecute(characterUrl);

            if (JsoupManager.isStatusOK(res)) {
                CharacterAttributes attributes = CharacterAttributes.parseElements(res.parse());
                log.debug("character => {}", attributes);
                return attributes;
            }
        } catch (IOException e) {
            log.error("Http error: {}", e.getMessage());
        }

        return null;
    }

    private Elements getElements(Document document) {
        return document
                .selectXpath("//*[@id=\"app\"]/div/div[2]/article/div[2]/div/div[4]/div/div[2]/div[4]/div[3]/div[4]/table/tbody/tr[2]/td/div/div/div[1]/dl/dd/div/div/table")
                .select("a");
    }

    private List<String> makeFullUrlList(Elements elements) {
        List<String> list = new ArrayList<>();

        for (Element element : elements) {

            String title = element.attr("title");
            String href = element.attr("href");

            if (!title.equals("원신/시스템/원소")) {
                log.debug("{} : {}", title, href);
                String fullUrl = property.makeUrl(href);
                list.add(fullUrl);
            }
        }

        return list;
    }

}
