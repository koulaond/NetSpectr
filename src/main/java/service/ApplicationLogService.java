package service;

import com.ondrejkoula.crawler.messages.CrawlerMessageService;

public interface ApplicationLogService extends CrawlerMessageService {

    void generalErrorMessage(String message);

    void generalErrorMessage(String message, Throwable throwable);
}
