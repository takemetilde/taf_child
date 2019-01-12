package com.taf.enums;

import com.taf.auto.StringUtil;
import com.taf.auto.WebDriverUtil;
import com.taf.auto.common.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static java.lang.String.format;

public enum Environment {

    SIT(
            i -> format("sit%d.eportal.taf.com/", i),
            i -> format("https://sit%d.eportal.taf.com/", i),
            "", ""),


    LOCALHOST(
            i -> "localhost:9000",
            i -> "localhost:9000",
            "", "");

    private static final Logger LOG = LoggerFactory.getLogger(Environment.class);

    private final IndexMap baseUrl;
    private final IndexMap loginUrl;
    private final String javaApiUrl;
    private final String soaApiUrl;

    /** Maps a URL template given a runtime index */
    interface IndexMap extends Function<Integer, String> {}

    Environment(IndexMap baseUrl, IndexMap loginUrl, String javaApiUrl, String soaApiUrl) {
        this.baseUrl = baseUrl;
        this.loginUrl = loginUrl;
        this.javaApiUrl = javaApiUrl;
        this.soaApiUrl = soaApiUrl;
    }

    private static final Integer index;
    private static final Environment activeEnv;

    static {
        String env = Configuration.EXECUTION_ENVIRONMENT.toUpperCase();
        String envStrippedOfIndex = StringUtil.stripTrailingIndex(env);
        activeEnv = valueOf(envStrippedOfIndex);

        int resolvedIndex;
        if(envStrippedOfIndex.equals(env)) {
            LOG.warn("Environment is missing index, defaulting to 4");
            resolvedIndex = 4;
        } else {
            String unparsedIndex = env.substring(envStrippedOfIndex.length());
            try {
                resolvedIndex = Integer.parseInt(unparsedIndex);
            } catch (NumberFormatException nfe) {
                throw new Error("Failed to parse resolvedIndex: " + unparsedIndex, nfe);
            }
        }
        index = resolvedIndex;
    }

    private static String resolve(IndexMap im) {
        return im.apply(getIndex());
    }

    public static String getBaseUrl() {
        return resolve(getEnv().baseUrl);
    }

    public static String getLoginUrl() {
        return resolve(getEnv().loginUrl);
    }

    public static String getFullURL(String relativeURL) {
        return WebDriverUtil.assembleFullURL(getBaseUrl(), relativeURL);
    }

    public static String getJavaApiUrl() {
        return getEnv().javaApiUrl;
    }

    public static String getSoaApiUrl() {
        return getEnv().soaApiUrl;
    }

//    public static boolean isDev() {
//        return getEnv() == DEV;
//    }

    /**
     * Gets the active Environment by referencing {@link Configuration#EXECUTION_ENVIRONMENT}.
     *
     * @return the active environment
     */
    public static Environment getEnv() {
        return activeEnv;
    }

    public static Integer getIndex() {
        return index;
    }
}
