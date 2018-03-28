package se.prv.mypages.audit.aop;

import net.logstash.logback.marker.Markers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import se.prv.mypages.audit.logcontext.LoggerAuditFactory;

@Aspect
public class ProfileAspect {

    private static final Logger LOGGER = LoggerAuditFactory.getLogger("profile-log");
    private static final String DURATION = "duration";

    @Around("@within(se.prv.mypages.audit.aop.Audit) || @annotation(se.prv.mypages.audit.aop.Audit)")
    public Object profileMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object retVal = proceedingJoinPoint.proceed();
        Long elapsedTime = System.currentTimeMillis() - startTime;
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info(Markers.append(DURATION, elapsedTime.toString()), "Profiling method: {}.{}",
                    proceedingJoinPoint.getTarget().getClass().getCanonicalName(), proceedingJoinPoint.getSignature().getName());
        }
        return retVal;
    }
}
