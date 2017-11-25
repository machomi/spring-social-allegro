package org.springframework.social.allegro.api.impl;

import org.springframework.social.allegro.api.Allegro;
import org.springframework.social.allegro.api.category.CategoryOperations;
import org.springframework.social.allegro.api.category.CategoryTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.web.client.RestTemplate;

public class AllegroTemplate extends AbstractOAuth2ApiBinding implements Allegro {

    public AllegroTemplate() {
    }

    public AllegroTemplate(String accessToken) {
        super(accessToken);

    }

    @Override
    public CategoryOperations categoryOperations() {
        return new CategoryTemplate(getRestTemplate(), isAuthorized());
    }

    @Override
    protected void configureRestTemplate(RestTemplate restTemplate) {
        super.configureRestTemplate(restTemplate);
    }

}
