package org.springframework.social.allegro.api.category;

import org.springframework.social.allegro.api.impl.AbstractAllegroApiOperations;
import org.springframework.web.client.RestTemplate;

public class CategoryTemplate extends AbstractAllegroApiOperations implements CategoryOperations {
  
  private static final String CATEGORIES_URL = "https://allegroapi.io/categories";

  public CategoryTemplate(final RestTemplate restTemplate, final boolean isAuthorized) {
    super(restTemplate, isAuthorized);
  }

  @Override
  public Categories getCategories() {
    return getEntity(CATEGORIES_URL, Categories.class);
  }

}
