package se.audit.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "audit")
public class AuditProperties {

    private Integer maxOutputLength = 200;

    public Integer getMaxOutputLength() {
        return maxOutputLength;
    }

    public void setMaxOutputLength(Integer maxOutputLength) {
        this.maxOutputLength = maxOutputLength;
    }
}
