package com.picon.utils.providers;

import androidx.annotation.NonNull;

public class UrlProvider {

    @NonNull
    public static String createByBase(@NonNull String baseUrl, @NonNull String path) {
        return baseUrl + "/" + path;
    }

    @NonNull
    public static String createByCustom(@NonNull String protocol, @NonNull String domain, @NonNull String path) {
        return new Builder(protocol, domain).create(path);
    }

    @NonNull
    public static String createByHttp(@NonNull String domain, @NonNull String path) {
        return new Builder(Builder.PROTOCOL_HTTP, domain).create(path);
    }

    @NonNull
    public static String createByHttps(@NonNull String domain, @NonNull String path) {
        return new Builder(Builder.PROTOCOL_HTTPS, domain).create(path);
    }

    public static class Builder {

        public static final String PROTOCOL_HTTP = "http";
        public static final String PROTOCOL_HTTPS = "https";

        @NonNull
        private final String protocol;
        @NonNull
        private final String domain;

        public Builder(@NonNull String protocol, @NonNull String domain) {
            this.protocol = protocol;
            this.domain = domain;
        }

        @NonNull
        public String getDomain() {
            return domain;
        }

        @NonNull
        public String getProtocol() {
            return protocol;
        }

        public String getBaseUrl() {
            return String.format("%s://%s", getProtocol(), getDomain());
        }

        @NonNull
        public String create(@NonNull String path) {
            return getBaseUrl() + "/" + path;
        }
    }
}
