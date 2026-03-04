package br.com.grupotechnos.keycloak.routes;

import br.com.grupotechnos.keycloak.routes.models.BasicResponse;
import br.com.grupotechnos.keycloak.routes.models.GuardianEmailConfirmationActionToken;
import br.com.grupotechnos.keycloak.routes.models.GuardianVerificationTokenResponse;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;

import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

import java.time.Instant;
import java.util.logging.Logger;

public class AdminEmailResource {
    private static final Logger logger = Logger.getLogger(AdminEmailResource.class.getName());

    KeycloakSession session;

    public AdminEmailResource(KeycloakSession session) {
        this.session = session;
    }

    @GET
    @Path("ping")
    public Response ping() {
        return Response.ok("pong").build();
    }

    @GET
    @Path("guardian-verification-token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendTokenToUser(
        @QueryParam("userEmail") String userEmail
    ) {
        UserModel currentUser = this.session.users()
            .getUserByEmail(
                session.getContext().getRealm(),
                userEmail
            );

        if (currentUser == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .header("Access-Control-Allow-Origin", "*")
                .entity(new BasicResponse("User not found"))
                .build();
        }

        try {
            String token = generateToken(currentUser.getId());
            logger.info("token " + token);
            return Response.ok(new GuardianVerificationTokenResponse(token)).build();
        } catch (Exception e) {
            logger.info("error " + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .header("Access-Control-Allow-Origin", "*")
                .entity(e)
                .build();
        }
    }

    private String generateToken(String userId) {
        long validityInSecs = session.getContext().getRealm().getActionTokenGeneratedByUserLifespan();
        long absoluteExpirationInSecs = Instant.now().getEpochSecond() + validityInSecs;

        return new GuardianEmailConfirmationActionToken(
            userId,
            (int)absoluteExpirationInSecs,
            "guardian-verification-token-" + userId
        ).serialize(
            session,
            session.getContext().getRealm(),
            session.getContext().getUri()
        );
    }
}
