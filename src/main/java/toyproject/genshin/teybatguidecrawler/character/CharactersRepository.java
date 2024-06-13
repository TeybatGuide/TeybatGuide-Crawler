package toyproject.genshin.teybatguidecrawler.character;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.genshin.teybatguidecrawler.character.domain.Characters;

@Repository
public interface CharactersRepository extends JpaRepository<Characters, String> {
}
