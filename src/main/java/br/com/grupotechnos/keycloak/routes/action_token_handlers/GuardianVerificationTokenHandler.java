package br.com.grupotechnos.keycloak.routes.action_token_handlers;

import br.com.grupotechnos.keycloak.routes.models.GuardianEmailConfirmationActionToken;
import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.actiontoken.ActionTokenContext;
import org.keycloak.authentication.actiontoken.ActionTokenHandler;
import org.keycloak.common.VerificationException;
import org.keycloak.events.EventType;
import org.keycloak.sessions.AuthenticationSessionModel;

public class GuardianVerificationTokenHandler implements ActionTokenHandler<GuardianEmailConfirmationActionToken> {
    @Override
    public Response handleToken(GuardianEmailConfirmationActionToken token, ActionTokenContext<GuardianEmailConfirmationActionToken> context) {
        return Response.ok().build();
    }

    @Override
    public Class<GuardianEmailConfirmationActionToken> getTokenClass() {
        return null;
    }

    @Override
    public String getAuthenticationSessionIdFromToken(GuardianEmailConfirmationActionToken guardianEmailConfirmationActionToken, ActionTokenContext<GuardianEmailConfirmationActionToken> actionTokenContext, AuthenticationSessionModel authenticationSessionModel) {
        return "";
    }

    @Override
    public EventType eventType() {
        return null;
    }

    @Override
    public String getDefaultEventError() {
        return "";
    }

    @Override
    public String getDefaultErrorMessage() {
        return "";
    }

    @Override
    public AuthenticationSessionModel startFreshAuthenticationSession(GuardianEmailConfirmationActionToken guardianEmailConfirmationActionToken, ActionTokenContext<GuardianEmailConfirmationActionToken> actionTokenContext) throws VerificationException {
        return null;
    }

    @Override
    public boolean canUseTokenRepeatedly(GuardianEmailConfirmationActionToken guardianEmailConfirmationActionToken, ActionTokenContext<GuardianEmailConfirmationActionToken> actionTokenContext) {
        return false;
    }

    @Override
    public void close() {

    }
}