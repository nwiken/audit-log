package se.audit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import se.audit.aop.AopClassAnnotatedAudit;
import se.audit.aop.AopMethodAnnotadedAudit;
import se.audit.aop.AopTestInterface;
import se.audit.aop.ProfileAspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProfileAspectTest {

    private ProfileAspect profilingAspect = new ProfileAspect();

    @Before
    public void clearAppender() {
        TestAppender.events.clear();
    }

    @Test
    public void testProfileIsMatchingMethod() throws Exception {
        AopTestInterface testInterface = new AopMethodAnnotadedAudit();
        AspectJProxyFactory factory = new AspectJProxyFactory(testInterface);

        factory.addAspect(profilingAspect);
        AopTestInterface proxy = factory.getProxy();
        proxy.aopTestMethod("lisa", "shouldNotBeLogged", 45L);
        assertEquals(1, TestAppender.events.size());
    }

    @Test
    public void testProfilePatternMatchingClass() throws Exception {
        AopTestInterface testInterface = new AopClassAnnotatedAudit();
        AspectJProxyFactory factory = new AspectJProxyFactory(testInterface);

        factory.addAspect(profilingAspect);
        AopTestInterface proxy = factory.getProxy();
        proxy.aopTestMethod("Kalle", "shouldNotBeLogged", 64L);
        assertEquals(1, TestAppender.events.size());
    }
}
