package com.taf.utils.Wait;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by AF41814 on 7/25/2017.
 */
public class Wait {
    private static final Logger LOG = LoggerFactory.getLogger(Wait.class);
    public final static double LONG_TIMEOUT = 60;
    public final static double DEFAULT_TIMEOUT = 20;
    public final static double SHORT_TIMEOUT = 3;

    public static boolean For(Conditions conditions) {
        return For(conditions, Wait.DEFAULT_TIMEOUT, false);
    }

    public static boolean For(Conditions conditions, boolean throwsTimeoutException) {
        return For(conditions, Wait.DEFAULT_TIMEOUT, throwsTimeoutException);
    }

    public static boolean For(Conditions conditions, double timeout) {
        return For(conditions, timeout, false);
    }

    public static boolean For(Conditions conditions, double timeout, boolean throwsTimeoutException) {
        Runnable runnable = () -> {
            switch (conditions.chainType) {
                case And: {
                    while (conditions.size() > 0) {
                        for (int i = 0; i < conditions.size(); i++) {
                            if (conditions.get(i).apply(new Object())) {
                                conditions.remove(i);
                            }
                        }
                    }
                    break;
                }
                case Or: {
                    while(true) {
                        for (Condition condition : conditions) {
                            if (condition.apply(new Object())) {
                                return;
                            }
                        }
                    }
                }
            }
        };
        LOG.debug("Starting chained condition '" + conditions.toString() + "' with parameters 'timeout: " + timeout + "', throwsTimeoutException: " + throwsTimeoutException + "'");
        boolean result = dynamicWait(runnable, timeout);
        if(throwsTimeoutException) {
            if(!result) {
                throw new RuntimeException("Conditions '" + conditions + "' failed within '" + timeout + "'");
            }
        }
        LOG.debug("Chained condition finished with result '" + result + "'");
        return result;
    }

    public static boolean For(Condition condition) {
        return For(condition, Wait.DEFAULT_TIMEOUT, false);
    }

    public static boolean For(Condition condition, boolean throwsTimeoutException) {
        return For(condition, Wait.DEFAULT_TIMEOUT, throwsTimeoutException);
    }

    public static boolean For(Condition condition, double timeout) {
        return For(condition, timeout, false);
    }

    public static boolean For(Condition condition, double timeout, boolean throwsTimeoutException) {
        Runnable runnable = () -> {
            while (!condition.apply(new Object())) try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
        };
        LOG.debug("Starting condition '" + condition.toString() + "' with parameters 'timeout: " + timeout + "', throwsTimeoutException: " + throwsTimeoutException + "'");
        boolean result = dynamicWait(runnable, timeout);
        if(throwsTimeoutException) {
            if(!result) {
                throw new RuntimeException("Condition '" + condition + "' failed within '" + timeout + "'");
            }
        }
        LOG.debug("Condition finished with result '" + result + "'");
        return result;
    }

    private static boolean dynamicWait(Runnable condition, double timeout) {
        try {
            Thread condThread = new Thread(condition);
            condThread.start();
            while (condThread.isAlive()) {
                Thread.sleep(250);
                timeout -= .25;
                if (timeout <= 0) {
                    condThread.interrupt();
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public enum ConditionChainType {
        And,
        Or
    }


}
