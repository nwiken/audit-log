package se.audit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import se.audit.aop.AopClassAnnotatedAudit;
import se.audit.aop.AopMethodAnnotadedAudit;
import se.audit.aop.AopTestInterface;
import se.audit.aop.AuditAspect;
import se.audit.aop.SingleInputInterface;
import se.audit.aop.SingleInputInterfaceImpl;
import se.audit.autoconfigure.AuditProperties;

import static org.junit.Assert.assertEquals;

public class AuditAspectTest {

    private AuditAspect auditAspect = new AuditAspect(new AuditProperties());

    @Before
    public void clearAppender() {
        TestAppender.events.clear();
    }

    @Test
    public void testAuditMethod() throws Exception {
        AopTestInterface testInterface = new AopMethodAnnotadedAudit();
        AspectJProxyFactory factory = new AspectJProxyFactory(testInterface);

        factory.addAspect(auditAspect);
        AopTestInterface proxy = factory.getProxy();
        proxy.aopTestMethod("lisa", "shouldNotBeLogged", 45L);
        assertEquals(2, TestAppender.events.size());
        assertEquals(">aopTestMethod(args: lisa, 45)", TestAppender.events.get(0).getFormattedMessage());
        assertEquals("<aopTestMethod(45)", TestAppender.events.get(1).getFormattedMessage()); }

    @Test
    public void testAuditClass() throws Exception {
        AopTestInterface testInterface = new AopClassAnnotatedAudit();
        AspectJProxyFactory factory = new AspectJProxyFactory(testInterface);

        factory.addAspect(auditAspect);
        AopTestInterface proxy = factory.getProxy();
        proxy.aopTestMethod("Kalle", "shouldNotBeLogged", 64L);
        assertEquals(2, TestAppender.events.size());
        assertEquals(">aopTestMethod(args: Kalle, 64)", TestAppender.events.get(0).getFormattedMessage());
        assertEquals("<aopTestMethod(64)", TestAppender.events.get(1).getFormattedMessage());
    }

    @Test
    public void testSingleInputInterface() throws Exception {
        SingleInputInterface testInterface = new SingleInputInterfaceImpl();
        AspectJProxyFactory factory = new AspectJProxyFactory(testInterface);

        factory.addAspect(auditAspect);
        SingleInputInterface proxy = factory.getProxy();
        proxy.singleInput("Batman");
        assertEquals(3, TestAppender.events.size());
        assertEquals(">singleInput(args: Batman)", TestAppender.events.get(0).getFormattedMessage());
        assertEquals("default context", TestAppender.events.get(1).getFormattedMessage());
        assertEquals("<singleInput(null)", TestAppender.events.get(2).getFormattedMessage());
    }

}