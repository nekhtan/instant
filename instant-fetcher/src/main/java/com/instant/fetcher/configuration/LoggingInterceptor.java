package com.instant.fetcher.configuration;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Interceptor that handles logging of external HTTP requests and responses.
 */
@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(@NonNull HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        BufferedClientHttpResponse bufferedResponse = new BufferedClientHttpResponse(response);

        log.info("Requested: {}; Response [{}]: {}", request.getURI(), bufferedResponse.getStatusCode(), new String(bufferedResponse.getBody().readAllBytes()));

        return bufferedResponse;
    }

    /**
     * Buffered response to allow multiple reads without consuming the actual response.
     */
    private static class BufferedClientHttpResponse implements ClientHttpResponse {
        private final ClientHttpResponse original;
        private final byte[] body;

        public BufferedClientHttpResponse(ClientHttpResponse original) throws IOException {
            this.original = original;
            body = original.getBody().readAllBytes();
        }

        @Override
        public InputStream getBody() {
            return new ByteArrayInputStream(body);
        }

        @Override
        public HttpStatusCode getStatusCode() throws IOException {
            return original.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return original.getStatusText();
        }

        @Override
        public void close() {
            original.close();
        }

        @Override
        public HttpHeaders getHeaders() {
            return original.getHeaders();
        }
    }
}
