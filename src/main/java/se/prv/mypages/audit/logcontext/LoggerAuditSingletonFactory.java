package se.prv.mypages.audit.logcontext;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class LoggerAuditSingletonFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAuditFactory.class);
    private static final LoggerContext LC = new LoggerContext();
    private static LoggerAuditSingletonFactory loggerAuditSingletonFactory;

    private static final List<String> DEFAULT_CONFIGS = Collections.unmodifiableList(Arrays.asList(
            "logback-test-audit.xml",
            "logback-audit.xml"
    ));
    private LoggerAuditSingletonFactory() {}

    static LoggerAuditSingletonFactory getInstance() {
        if(loggerAuditSingletonFactory == null) {
            loggerAuditSingletonFactory = new LoggerAuditSingletonFactory();
            loggerAuditSingletonFactory.init();
        }
        return loggerAuditSingletonFactory;
    }

    private void init() {
        Boolean initialized = false;
        for(String path : DEFAULT_CONFIGS) {
            if(!initialized) {
                initialized = configureIf(path);
            }
        }
    }

    Logger getLogger(String name) {
        return LC.getLogger(name);
    }

    private static Boolean configureIf(String config) {
        try {
            URL url = Optional.of(LoggerAuditFactory.class)
                    .map(Class::getClassLoader)
                    .map(cl -> cl.getResource(config))
                    .orElseThrow(IllegalArgumentException::new);

            new ContextInitializer(LC).configureByResource(url);
            LOGGER.info("Found resource [{}]", config);
            return true;
        } catch (IllegalArgumentException | JoranException e) {
            LOGGER.info("Could NOT find resource [{}]", config);
            return false;
        }
    }
}
