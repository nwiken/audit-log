package se.prv.mypages.audit.aop;

@Audit
public class AopClassAnnotatedAudit implements AopTestInterface {

    @Override
    public Integer aopTestMethod(String firstArg, String doNotLog, Long secondArg) {
        return secondArg.intValue();
    }
}
