package org.springframework.social.allegro.api;

import org.springframework.social.ApiBinding;
import org.springframework.social.allegro.api.category.CategoryOperations;

public interface Allegro extends ApiBinding {
  
  CategoryOperations categoryOperations();

}
