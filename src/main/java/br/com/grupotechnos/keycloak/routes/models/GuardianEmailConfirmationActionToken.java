package br.com.grupotechnos.keycloak.routes.models;

import org.keycloak.authentication.actiontoken.DefaultActionToken;

public class GuardianEmailConfirmationActionToken extends DefaultActionToken {
    public static final String TOKEN_TYPE = "guardian-email-confirmation";

    public GuardianEmailConfirmationActionToken(
        String keycloakUserId,
        int absoluteExpirationInSecs,
        String authenticationSessionId
    ) {
        super(keycloakUserId, TOKEN_TYPE, absoluteExpirationInSecs, null, authenticationSessionId);
    }
}
