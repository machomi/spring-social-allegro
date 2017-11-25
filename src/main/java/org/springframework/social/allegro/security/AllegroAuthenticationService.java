package org.springframework.social.allegro.security;

import org.springframework.social.allegro.api.Allegro;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

public class AllegroAuthenticationService extends OAuth2AuthenticationService<Allegro>  {

  public AllegroAuthenticationService(OAuth2ConnectionFactory<Allegro> connectionFactory) {
    super(connectionFactory);
  }

}
