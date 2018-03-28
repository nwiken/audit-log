package se.prv.mypages.audit.aop;

import net.logstash.logback.marker.Markers;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import se.prv.mypages.audit.autoconfigure.AuditProperties;
import se.prv.mypages.audit.logcontext.LoggerAuditFactory;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Aspect
public class AuditAspect {

    private static final Logger LOGGER = LoggerAuditFactory.getLogger("audit-log");
    private static final String SEPARATOR = ", ";
    private static final String MARKER_CLASS = "audit-class";
    private static final String TRUNCATED_SUFFIX = "-truncated...";

    private final AuditProperties auditProperties;

    public AuditAspect(AuditProperties auditProperties) {
        this.auditProperties = auditProperties;
    }

    @Before(value = "@within(se.prv.mypages.audit.aop.Audit) || @annotation(se.prv.mypages.audit.aop.Audit)")
    public void auditBeforeMessagesServerMethod(JoinPoint joinPoint) {
        List<String> loggableParameters = getLoggableParameters(joinPoint);
        String parametersAsString = StringUtils.join(loggableParameters, SEPARATOR);
        String methodName = joinPoint.getSignature().getName();

        LOGGER.info(Markers.append(MARKER_CLASS, joinPoint.getTarget().getClass().getCanonicalName()),
                ">{}(args: {})", methodName, parametersAsString);
    }

    @AfterReturning(value = "@within(se.prv.mypages.audit.aop.Audit) || @annotation(se.prv.mypages.audit.aop.Audit)",
            returning = "returnValue")
    public Object auditAfterMessageServerMethod(JoinPoint joinPoint, Object returnValue) {
        String returnValueAsString = returnValue == null ? "null" : truncateString(returnValue.toString());

        LOGGER.info(Markers.append(MARKER_CLASS, joinPoint.getTarget().getClass().getCanonicalName()),
                "<{}({})", joinPoint.getSignature().getName(), returnValueAsString);
        return returnValue;
    }

    private List<String> getLoggableParameters(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Parameter[] parameters = methodSignature.getMethod().getParameters();

        return parameters.length == 0 ? Collections.emptyList() : IntStream.range(0, parameters.length)
                .filter(i -> isLoggable(parameters[i]))
                .mapToObj(i -> joinPoint.getArgs()[i])
                .map(Objects::toString)
                .map(this::truncateString)
                .collect(Collectors.toList());
    }

    private boolean isLoggable(Parameter parameter) {
        return parameter.getAnnotation(AuditSkip.class) == null;
    }

    private String truncateString(String longString) {
        String limitedString = longString.substring(0, Math.min(longString.length(), auditProperties.getMaxOutputLength()));
        return limitedString.equals(longString) ? limitedString : limitedString + TRUNCATED_SUFFIX;
    }
}