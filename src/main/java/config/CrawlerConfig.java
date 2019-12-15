package config;

import com.ondrejkoula.crawler.messages.CrawlerMessageService;
import service.DefaultMessageService;

public class CrawlerConfig extends com.ondrejkoula.config.CrawlerConfig {

    @Override
    public CrawlerMessageService messageService() {
        return new DefaultMessageService();
    }
}
