package com.ronilsonalves.msusers.config.feign;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class FeignConfig {

    private static final String KEYCLOAK_REGISTRATION_ID = "keycloak-registration";

    private final OAuth2AuthorizedClientService clientService;
    private final ClientRegistrationRepository registrationRepository;

    @Autowired
    public FeignConfig(OAuth2AuthorizedClientService clientService, ClientRegistrationRepository registrationRepository) {
        this.clientService = clientService;
        this.registrationRepository = registrationRepository;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        ClientRegistration clientRegistration = registrationRepository.findByRegistrationId(KEYCLOAK_REGISTRATION_ID);
        OAuth2ClientCredentialsFeignMananger credentialsFeignMananger =
                new OAuth2ClientCredentialsFeignMananger(authorizedClientManager(),clientRegistration);
        return requestInterceptor -> {
          requestInterceptor.header("Authorization", "Bearer " + credentialsFeignMananger.getAccessToken());
        };
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager () {
        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder
                .builder()
                .clientCredentials()
                .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(registrationRepository, clientService);

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }
}
