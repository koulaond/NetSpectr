package service;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class DefaultMessageService implements ApplicationLogService {

    @Override
    public void crawlerError(UUID uuid, String s) {
        log.error("An error occurred for crawler {}. Message: {}", uuid, s);
    }

    @Override
    public void crawlerError(UUID uuid, String s, Throwable throwable) {
        log.error("An error occurred for crawler {}. Message: {}, Cause: {}", uuid, s, throwable);
    }

    @Override
    public void crawlerWarning(UUID uuid, String s) {
        log.warn("Warning: Crawler UUID: {} Message: {}", uuid, s);
    }

    @Override
    public void crawlerInfo(UUID uuid) {
        // TODO
    }

    @Override
    public void generalErrorMessage(String message) {
        log.error(message);
    }

    @Override
    public void generalErrorMessage(String message, Throwable throwable) {
        log.error(message, throwable);
    }
}
