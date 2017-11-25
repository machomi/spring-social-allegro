package org.springframework.social.allegro.connect;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.social.oauth2.AccessGrant;

@RunWith(MockitoJUnitRunner.class)
public class AllegroConnectionFactoryTest {

    @Mock
    AccessGrant accessGrant;

    private final String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTE2NjIyOTcsInVzZXJfbmFtZSI6IjEyMzQ1NiIsImp0aSI6IjExMTExMTExLTIyMi0zMzMtNDQ0NC01NTU1NTU1NTU1NTUiLCJjbGllbnRfaWQiOiJhYmNkZWZnaGkiLCJzY29wZSI6WyJhbGxlZ3JvX2FwaSJdfQ.c2HLfcHl1AdZEm7TBQXB9aBHvKIihxo28CAE2vCZIgM";

    @Test
    public void testExtractProviderUserId() throws Exception {
        when(accessGrant.getAccessToken()).thenReturn(accessToken);
        AllegroConnectionFactory allegroConnectionFactory = new AllegroConnectionFactory("clientId", "clientSecret",
                "apiKey", false);
        String userId = allegroConnectionFactory.extractProviderUserId(accessGrant);
        assertThat(userId, is(equalTo("123456")));
    }
}
