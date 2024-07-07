package toyproject.genshin.teybatguidecrawler.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import toyproject.genshin.teybatguidecrawler.common.domain.value.JsonProperties;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class JsonReader {

    private static final Gson gson = new Gson();
    private static final Resource resource = new ClassPathResource("setting.json");
    private final ResourceLoader resourceLoader;

    public static List<String> getKeywords(JsonProperties properties) {
        try (Reader reader = new InputStreamReader(resource.getInputStream())) {

            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            // "except-keywords" 키의 값을 List<String>으로 변환
            Type listType = new TypeToken<List<String>>() {}.getType();

            return gson.fromJson(jsonObject.get(properties.getKey()), listType);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
