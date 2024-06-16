package toyproject.genshin.teybatguidecrawler.character;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharactersCrawlService {

    private final CharactersRepository charactersRepository;

}
