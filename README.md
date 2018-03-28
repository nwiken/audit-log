# Audit log for My Pages
Spring boot application for audit logging a class or a method. Audit is enabled by annotating a method or class with the 
annotation **@Audit**

Sensible parameters can be omitted from the audit log if annotated with **@AuditSkip**

### Properties
- **mypages.audit.trace.enabled** - If set to true, all trace logging will be enabled. Default value is true
- **mypages.audit.profile.enabled** - If set to true, all profile logging will be enabled. Default value is false
- **mypages.audit.maxOutputLength** - Defines the maximum character lenght allowed for a variable before it's truncated. Default is 200

### Configuration
The audit-log can be configured with a configuration script expressed in XML, in the same way as for a normal logback configuration.

1. Audit-log tries to find a file called *logback-test-audit.xml* in the resource folder.
2. Audit-log tries to find a file called *logback-audit.xml* in the resource folder.

If you are using Maven and if you place the logback-test.xml under the src/test/resources folder, Maven will ensure that 
it won't be included in the artifact produced. Thus, you can use a different configuration file, namely logback-test-audit.xml 
during testing, and another file, namely, logback-audit.xml, in production.

For more information on how to configure the audit-log, please see [logback documentation](https://logback.qos.ch/manual/configuration.html)
