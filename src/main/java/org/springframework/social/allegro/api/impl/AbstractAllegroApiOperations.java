/**
 * Copyright 2011-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.allegro.api.impl;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractAllegroApiOperations {
  private static final Log logger = LogFactory.getLog(AbstractAllegroApiOperations.class);

  protected final RestTemplate restTemplate;
  protected final boolean isAuthorized;

  protected AbstractAllegroApiOperations(final RestTemplate restTemplate, final boolean isAuthorized) {
    this.restTemplate = restTemplate;
    this.isAuthorized = isAuthorized;

    restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
      @Override
      public void handleError(final ClientHttpResponse response) throws IOException {
        if (logger.isWarnEnabled()) {
          final String bodyText = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
          logger.warn("Error: " + bodyText);
        }
      }
    });
  }

  protected void requireAuthorization() {
    if (!isAuthorized) {
      throw new MissingAuthorizationException("allegro");
    }
  }

  protected <T> T getEntity(final String url, final Class<T> type) {
    return restTemplate.getForObject(url, type);
  }

  @SuppressWarnings("unchecked")
  protected <T> T saveEntity(final String url, final T entity) {
    return (T) restTemplate.postForObject(url, entity, entity.getClass());
  }

}
