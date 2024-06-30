package toyproject.genshin.teybatguidecrawler.common;

import org.springframework.core.io.ClassPathResource;
import toyproject.genshin.teybatguidecrawler.common.domain.value.SiteProperties;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class PropertiesParser {

    public static String getProperty(ClassPathResource classPathResource, SiteProperties siteProperties) throws IOException {
        String content = classPathResource.getContentAsString(Charset.defaultCharset());
        return parsePropertiesString(content).get(siteProperties.getPropertyLocation());
    }

    public static Map<String, String> parsePropertiesString(String propertiesContent) {
        Map<String, String> properties = new HashMap<>();

        String[] lines = propertiesContent.split("\n");

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue; // 빈 줄 또는 주석은 건너뜁니다.
            }

            int equalsIndex = line.indexOf('=');
            if (equalsIndex == -1) {
                // = 기호가 없는 경우 무시합니다.
                continue;
            }

            String key = line.substring(0, equalsIndex).trim();
            String value = line.substring(equalsIndex + 1).trim();

            properties.put(key, value);
        }

        return properties;
    }
}
