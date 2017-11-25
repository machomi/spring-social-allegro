package org.springframework.social.allegro.connect;

import org.springframework.social.allegro.api.Allegro;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.connect.support.OAuth2Connection;

public class AllegroAdapter implements ApiAdapter<Allegro> {

  private String userId;

  @Override
  public boolean test(Allegro api) {
    return true;
  }

  @Override
  public void setConnectionValues(Allegro api, ConnectionValues values) {
    if (values instanceof OAuth2Connection) {
      this.userId = ((OAuth2Connection) values).getKey().getProviderUserId();
    }
  }

  @Override
  public UserProfile fetchUserProfile(Allegro api) {
    if (userId != null) {
      return new UserProfileBuilder().setId(userId).setUsername(userId).build();
    }
    return UserProfile.EMPTY;
  }

  @Override
  public void updateStatus(Allegro api, String message) {

  }

}
