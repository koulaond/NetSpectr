package service;

import com.ondrejkoula.crawler.messages.MessageService;

public interface ApplicationLogService extends MessageService {

    void generalErrorMessage(String message);

    void generalErrorMessage(String message, Throwable throwable);
}
