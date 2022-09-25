package com.ronilsonalves.msusers.config.feign;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import java.util.Collection;
import java.util.logging.Logger;


public class OAuth2ClientCredentialsFeignMananger {

    private static Logger log = Logger.getLogger(OAuth2ClientCredentialsFeignMananger.class.getName());

    private final OAuth2AuthorizedClientManager manager;
    private final ClientRegistration clientRegistration;
    private final Authentication principal;

    public OAuth2ClientCredentialsFeignMananger(OAuth2AuthorizedClientManager mananger,
                                                ClientRegistration clientRegistration) {
        this.manager = mananger;
        this.clientRegistration = clientRegistration;
        this.principal = createPrincipal();
    }

    private Authentication createPrincipal() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return this;
            }

            @Override
            public boolean isAuthenticated() {
                return principal.isAuthenticated();
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return clientRegistration.getClientId();
            }
        };
    }

    public String getAccessToken() {
        try {
            OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId(clientRegistration.getRegistrationId()) // a implementação feita em
                    // aula utilizou clientRegistration.getClientId(), alterei para o atual e rodou.
                    .principal(principal)
                    .build();

            OAuth2AuthorizedClient client = manager.authorize(oAuth2AuthorizeRequest);

            log.info("Status do ClientRegistrastion: "+ clientRegistration);

            if (client == null) {
                throw new IllegalStateException(String.format("Client Crendentials flow para registro %s falhou! ",
                        clientRegistration.getClientId()));
            }
            return client.getAccessToken().getTokenValue();
        } catch (Exception exception) {
            log.info(exception.getMessage());
            log.info(String.valueOf(clientRegistration));
            System.out.println("ERRO: "+exception.getMessage());
            return null;
        }
    }
}
