package toyproject.genshin.teybatguidecrawler.common.domain;

import jakarta.el.PropertyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import toyproject.genshin.teybatguidecrawler.common.domain.value.SiteProperties;

import java.util.List;

@Component
@RequiredArgsConstructor
public class YmlProperty {

    private final Environment environment;

    public String getProperty(SiteProperties name) {
        return environment.getProperty(name.getPropertyLocation());
    }

    public List<String> getProperties(SiteProperties name) {
        Binder binder = Binder.get(environment);
        return binder.bind(name.getPropertyLocation(), List.class).orElseThrow(PropertyNotFoundException::new);
    }

    public String makeUrl(String url) {
        return environment.getProperty(SiteProperties.BASE.getPropertyLocation()) + url;
    }

}
