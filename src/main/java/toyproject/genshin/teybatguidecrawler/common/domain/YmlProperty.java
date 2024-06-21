package toyproject.genshin.teybatguidecrawler.common.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import toyproject.genshin.teybatguidecrawler.common.domain.value.SiteProperties;

@Component
@RequiredArgsConstructor
public class YmlProperty {

    private final Environment environment;

    public String getProperty(SiteProperties name) {
        return environment.getProperty(name.getPropertyLocation());
    }

}
