package br.com.grupotechnos.keycloak.routes.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.keycloak.authentication.actiontoken.DefaultActionToken;

public class GuardianEmailConfirmationActionToken extends DefaultActionToken {
    public static final String TOKEN_TYPE = "guardian-email-confirmation";

    @JsonProperty(value = "email")
    private String email;

    public GuardianEmailConfirmationActionToken(
        String keycloakUserId,
        int absoluteExpirationInSecs,
        String email
    ) {
        super(keycloakUserId, TOKEN_TYPE, absoluteExpirationInSecs, null, null);
        this.email = email;
    }

    private GuardianEmailConfirmationActionToken() {}

    public String getEmail() {
        return email;
    }
}
