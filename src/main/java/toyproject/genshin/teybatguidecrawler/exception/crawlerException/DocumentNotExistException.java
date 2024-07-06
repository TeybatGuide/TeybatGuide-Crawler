package toyproject.genshin.teybatguidecrawler.exception.crawlerException;

import toyproject.genshin.teybatguidecrawler.exception.TeybatException;

public class DocumentNotExistException extends TeybatException {

    public DocumentNotExistException(String message) {
        super(message);
    }

}