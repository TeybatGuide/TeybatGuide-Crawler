package toyproject.genshin.teybatguidecrawler.character;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import toyproject.genshin.teybatguidecrawler.character.domain.CharacterAttributes;
import toyproject.genshin.teybatguidecrawler.common.JsoupManager;
import toyproject.genshin.teybatguidecrawler.common.PropertiesParser;
import toyproject.genshin.teybatguidecrawler.common.domain.value.Country;
import toyproject.genshin.teybatguidecrawler.common.domain.value.SiteProperties;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Log4j2
class CharactersCrawlServiceTest {

    private ClassPathResource classPathResource;
    private List<String> checkList = List.of("신의 눈", "무기");

    @BeforeEach
    public void setUp() {
        classPathResource = new ClassPathResource("./site.properties");
    }

    @Test
    void crawlWithJsoup() throws IOException {
        String characterUrl = PropertiesParser.getProperty(classPathResource, SiteProperties.CHARACTER);

        log.info("crawling with Jsoup: {}", characterUrl);

        if (characterUrl == null) {
            log.error("character url is null");
            return;
        }

        try {
            // 연결 설정 및 HTML 문서 가져오기
            Connection.Response response = JsoupManager.ConnectJsoupExecute(characterUrl);

            // 응답 상태 코드 확인
            if (isStatusOK(response)) {
                Document document = response.parse();

                Element tbody = document.select("tbody").get(2).select("tbody").get(1);

                log.info("tbody: {}", tbody.text());

                Elements elements = tbody.select("a");

                Queue<String> q = new LinkedList<>();

                for (Element element : elements) {

                    String title = element.attr("title");
                    String href = element.attr("href");

                    if (!title.equals("원신/시스템/원소")) {
                        log.debug("{} : {}", title, href);
                        String fullUrl = PropertiesParser.makeUrl(classPathResource, href);
                        q.add(fullUrl);
                    }
                }

                while (!q.isEmpty()) {
                    String url = q.poll();

                    Connection.Response characterConnection = JsoupManager.ConnectJsoupExecute(url);

                    if (isStatusOK(characterConnection)) {
                        Document parse = characterConnection.parse();
//                        log.info("data => {}", parse.body().text());


                        CharacterAttributes attributes = CharacterAttributes.parseElements(parse);
                        log.info("character attributes => {}", attributes);

//                        Elements select = parse.selectXpath("//*[@id=\"app\"]/div/div[2]/article/div[2]/div/div[4]/div/div[2]/div[4]/div[3]/div[3]/table/tbody")
//                                .select("tr");
//
//                        for (Element element : select) {
//
//                            if (checkCharacterInfo(element)) {
//                                log.info("character info => {}", element.text());
//                            }
//                        }
                    }
                }

            } else {
                log.error("Http error: {}", response.statusCode());
            }

        } catch (IOException e) {
            log.error("🧨 {}", e.getMessage());
        }
    }

    private boolean checkCharacterInfo(Element element) {

        for (String s : checkList) {
            if (element.text().contains(s) && !element.text().contains("언어별 표기")) {
                return true;
            }
        }

        for (String country : Country.getCountries()) {
            String attr = element.select("a").attr("title");

            if (attr.contains(country)) {
                return true;
            }
        }

        return false;
    }

    private boolean isStatusOK(Connection.Response response) {
        return response != null && response.statusCode() == 200;
    }

    private boolean isDocumentEmpty(Document document) {
        return document == null || document.title().isEmpty();
    }

}