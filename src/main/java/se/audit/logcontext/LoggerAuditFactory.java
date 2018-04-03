package se.audit.logcontext;

import org.slf4j.Logger;

public class LoggerAuditFactory {

    private static LoggerAuditSingletonFactory loggerAuditSingletonFactory = LoggerAuditSingletonFactory.getInstance();

    private LoggerAuditFactory() {
    }

    public static Logger getLogger(String name) {
        return loggerAuditSingletonFactory.getLogger(name);
    }
}
