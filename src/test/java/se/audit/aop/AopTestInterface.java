package se.audit.aop;

public interface AopTestInterface {

    Integer aopTestMethod(String firstArg, @AuditSkip String doNotLog, Long secondArg);
}
