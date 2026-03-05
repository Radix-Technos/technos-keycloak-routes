package br.com.grupotechnos.keycloak.routes.action_token_handlers;

import br.com.grupotechnos.keycloak.routes.models.BasicResponse;
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

        logger.info("token=" + token);
        logger.info("userId=" + userId);
        logger.info("targetUser=" + targetUser);

        if (targetUser == null)
            return Response.status(Response.Status.NOT_FOUND)
                .header("Access-Control-Allow-Origin", "*")
                .entity(new BasicResponse("User not found"))
                .build();

        return Response
            .ok(new MinorVerificationResponse(targetUser.getEmail()))
            .build();
    }

//    @Override
//    public String getId() {
//        return GuardianEmailConfirmationActionToken.TOKEN_TYPE;
//    }
}