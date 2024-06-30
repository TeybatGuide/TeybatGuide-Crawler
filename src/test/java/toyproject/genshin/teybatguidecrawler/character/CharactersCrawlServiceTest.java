package toyproject.genshin.teybatguidecrawler.character;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

                //todo
                // 데이터 파싱하기

            } else {
                log.error("Http error: {}", response.statusCode());
            }

        } catch (IOException e) {
            log.error("🧨 {}", e.getMessage());
        }
    }

}