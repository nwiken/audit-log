package se.prv.mypages.audit.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.prv.mypages.audit.aop.AuditAspect;
import se.prv.mypages.audit.aop.ProfileAspect;

@Configuration
@EnableConfigurationProperties(AuditProperties.class)
public class AuditConfiguration {

    @Autowired
    private AuditProperties auditProperties;

    @Bean
    @ConditionalOnProperty(prefix = "mypages.audit.trace", value = "enabled", havingValue = "true", matchIfMissing = true)
    public AuditAspect createAuditAspect() {
        return new AuditAspect(auditProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "mypages.audit.profile", value = "enabled", havingValue = "true")
    public ProfileAspect createProfilingAspect() {
        return new ProfileAspect();
    }
}
