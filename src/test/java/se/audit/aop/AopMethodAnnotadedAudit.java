package se.audit.aop;

public class AopMethodAnnotadedAudit implements AopTestInterface {

    @Audit
    @Override
    public Integer aopTestMethod(String firstArg, String doNotLog, Long secondArg) {
        return secondArg.intValue();
    }
}
