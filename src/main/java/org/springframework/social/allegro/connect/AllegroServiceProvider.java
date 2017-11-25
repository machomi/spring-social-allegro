package org.springframework.social.allegro.connect;

import org.springframework.social.allegro.api.Allegro;
import org.springframework.social.allegro.api.impl.AllegroTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;

public class AllegroServiceProvider extends AbstractOAuth2ServiceProvider<Allegro> {

  public AllegroServiceProvider(OAuth2Operations oauth2Operations) {
    super(oauth2Operations);
  }
  
  public AllegroServiceProvider(final String clientId, final String clientSecret, final String apiKey, Boolean isSandbox) {
    super(new AllegroOAuth2Template(clientId, clientSecret, apiKey, isSandbox));
  }
  

  @Override
  public Allegro getApi(String accessToken) {
    return new AllegroTemplate(accessToken);
  }

}
