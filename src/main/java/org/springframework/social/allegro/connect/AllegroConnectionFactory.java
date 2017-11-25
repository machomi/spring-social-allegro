package org.springframework.social.allegro.connect;

import org.springframework.social.allegro.api.Allegro;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

public class AllegroConnectionFactory extends OAuth2ConnectionFactory<Allegro> {

  public AllegroConnectionFactory(final String clientId, final String clientSecret, final String apiKey,
      Boolean isSandbox) {
    super("allegro", new AllegroServiceProvider(clientId, clientSecret, apiKey, isSandbox), new AllegroAdapter());
  }

  @Override
  public boolean supportsStateParameter() {
    return false;
  }

  @Override
  protected String extractProviderUserId(AccessGrant accessGrant) {
    String token = accessGrant.getAccessToken();
    // get rid of signature
    if (StringUtils.countOccurrencesOf(token, ".") > 1) {
      token = token.substring(0, token.lastIndexOf(".") + 1);
    }
    Jwt<Header, Claims> jwt = Jwts.parser().parseClaimsJwt(token);
    return jwt.getBody().get("user_name", String.class);
  }

}
