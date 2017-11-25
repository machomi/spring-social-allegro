package org.springframework.social.allegro.connect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.Base64Utils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class AllegroOAuth2Template extends OAuth2Template {

    private String apiKey;

    private String clientId;

    private String clientSecret;

    public AllegroOAuth2Template(String clientId, String clientSecret, String apiKey, Boolean isSandbox) {
        super(clientId, clientSecret, getAuthorizeUrl(isSandbox, apiKey), getAccessTokenUrl(isSandbox));
        setUseParametersForClientAuthentication(true);
        this.apiKey = apiKey;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    private static String getAuthorizeUrl(Boolean isSandbox, String apiKey) {
        return isSandbox ? "https://ssl.allegro.pl.webapisandbox.pl/auth/oauth/authorize"
                : "https://ssl.allegro.pl/auth/oauth/authorize";
    }

    private static String getAccessTokenUrl(Boolean isSandbox) {
        return isSandbox ? "https://ssl.allegro.pl.webapisandbox.pl/auth/oauth/token"
                : "https://ssl.allegro.pl/auth/oauth/token";
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        parameters.add("api-key", apiKey);
        return super.buildAuthorizeUrl(parameters);
    }

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
        parameters.add("api-key", apiKey);
        return super.buildAuthorizeUrl(grantType, parameters);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64Utils.encodeToString((clientId + ":" + clientSecret).getBytes()));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);
        return extractAccessGrant(getRestTemplate().postForObject(accessTokenUrl, request, Map.class));
    }

    private AccessGrant extractAccessGrant(Map<String, Object> result) {
        return createAccessGrant((String) result.get("access_token"), (String) result.get("scope"),
                (String) result.get("refresh_token"), getIntegerValue(result, "expires_in"), result);
    }

    private Long getIntegerValue(Map<String, Object> map, String key) {
        try {
            return Long.valueOf(String.valueOf(map.get(key))); // normalize to String before creating integer value;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (interceptors == null) {
            interceptors = new ArrayList<>();
            restTemplate.setInterceptors(interceptors);
        }
        interceptors.add(new ApiKeyRequestInterceptor());
        return restTemplate;
    }

    private class ApiKeyRequestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {
            HttpRequestWrapper wrapper = new HttpRequestWrapper(request);
            wrapper.getHeaders().add("Api-Key", apiKey);
            return execution.execute(request, body);
        }
    }

}
