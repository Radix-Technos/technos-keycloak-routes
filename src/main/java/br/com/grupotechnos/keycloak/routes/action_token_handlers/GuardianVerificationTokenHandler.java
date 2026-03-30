package br.com.grupotechnos.keycloak.routes.action_token_handlers;

import br.com.grupotechnos.keycloak.routes.models.GuardianEmailConfirmationActionToken;
import br.com.grupotechnos.keycloak.routes.models.MinorVerificationResponse;
import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.actiontoken.AbstractActionTokenHandler;
import org.keycloak.authentication.actiontoken.ActionTokenContext;
import org.keycloak.events.Errors;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.messages.Messages;

import java.util.logging.Logger;

import static br.com.grupotechnos.keycloak.routes.AdminEmailResource.CURRENT_GUARDIAN_VERIFICATION_TOKEN;

public class GuardianVerificationTokenHandler extends AbstractActionTokenHandler<GuardianEmailConfirmationActionToken> {
    private static final Logger logger = Logger.getLogger(GuardianVerificationTokenHandler.class.getName());

    public GuardianVerificationTokenHandler() {
        super(
            GuardianEmailConfirmationActionToken.TOKEN_TYPE,
            GuardianEmailConfirmationActionToken.class,
            Messages.INVALID_REQUEST,
            EventType.EXECUTE_ACTION_TOKEN,
            Errors.INVALID_REQUEST
        );
    }

    @Override
    public Response handleToken(GuardianEmailConfirmationActionToken token, ActionTokenContext<GuardianEmailConfirmationActionToken> context) {
        KeycloakSession session = context.getSession();
        RealmModel realm = context.getRealm();
        String userId = token.getSubject();

        UserModel targetUser = session.users().getUserById(realm, userId);

        if (targetUser == null)
            return getResponse("", false);

        String savedToken = targetUser.getFirstAttribute(CURRENT_GUARDIAN_VERIFICATION_TOKEN);
        String providedToken = context.getSession()
                .getContext()
                .getHttpRequest()
                .getUri()
                .getQueryParameters()
                .getFirst("key");

        if (savedToken == null || !savedToken.equals(providedToken))
            return getResponse(targetUser.getEmail(), false);

        targetUser.removeAttribute(CURRENT_GUARDIAN_VERIFICATION_TOKEN);

        return getResponse(targetUser.getEmail(), true);
    }

    public Response getResponse(String email, boolean validated) {
        return Response
            .ok(new MinorVerificationResponse(email, validated))
            .build();
    }
}