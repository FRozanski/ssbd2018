package pl.lodz.p.it.ssbd2018.ssbd01.tools;

import java.util.Objects;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author piotrek
 */
@Interceptor
public class LoggerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        
        String className = context.getTarget().getClass().getCanonicalName();
        String methodName = context.getMethod().getName();
        
        StringBuilder parameters = new StringBuilder();
        if (context.getParameters() != null) {
            for (Object param : context.getParameters()) {
                if (parameters.length() > 0) {
                    parameters.append(", ");
                }
                parameters.append(param);
            }
        }

        logger.info("{}.{}({}) została wywołana przez użytkownika [TODO]",
                className, methodName, parameters
        );

        Object result;
        try {
            result = context.proceed();
        } catch (Exception ex) {
            StringBuilder causes = new StringBuilder();

            Throwable cause = ex.getCause();
            while (cause != null) {
                if (causes.length() > 0) {
                    causes.append(", ");
                }
                causes.append(cause);
                cause = cause.getCause();
            }

            logger.error("{}.{}({}) wywołana przez użytkownika [TODO] rzuciła następujący wyjątek {}: {}. Causes: [{}]",
                    className, methodName, parameters, ex, ex.getLocalizedMessage(), causes
            );

            throw ex;
        }

        String resultString = context.getMethod().getReturnType().equals(Void.TYPE) ? "void" : Objects.toString(result);
        logger.info("{}.{}({}) wywołana przez użytkownika [TODO] zwróciła {}",
                className, methodName, parameters, resultString
        );

        return result;
    }
}
