package toyproject.genshin.teybatguidecrawler.character;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import toyproject.genshin.teybatguidecrawler.common.PropertiesParser;
import toyproject.genshin.teybatguidecrawler.common.domain.value.SiteProperties;

import java.io.IOException;

@Log4j2
class CharactersCrawlServiceTest {

    private ClassPathResource classPathResource;

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
            Connection.Response response = Jsoup.connect(characterUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(6000)
                    .execute();

            // 응답 상태 코드 확인
            if (response.statusCode() == 200) {
                Document document = response.parse();
                log.info("data => {}", document.body().text());

                Elements elements = document.selectXpath("//*[@id=\"app\"]/div/div[2]/article/div[2]/div/div[4]/div/div[2]/div[4]/div[3]/div[4]/table/tbody/tr[2]/td/div/div/div[1]/dl/dd/div/div/table");

                log.info("elements count => {}", elements.size());

                for (Element element : elements) {
                    Elements select = element.select("a");

                    //todo
                    // 링크 가져와 데이터 파싱하기

                    log.info(select.text());
                }

            } else {
                log.error("Http error: {}", response.statusCode());
            }

        } catch (IOException e) {
            log.error("🧨 {}", e.getMessage());
        }
    }

}