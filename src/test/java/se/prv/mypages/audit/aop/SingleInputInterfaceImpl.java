package se.prv.mypages.audit.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Audit
public class SingleInputInterfaceImpl implements SingleInputInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleInputInterface.class);

    @Override
    public void singleInput(String single) {
        LOGGER.info("default context");
    }
}
