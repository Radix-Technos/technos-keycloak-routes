package br.com.grupotechnos.keycloak.routes;

import br.com.grupotechnos.keycloak.routes.models.BasicResponse;
import br.com.grupotechnos.keycloak.routes.models.SendMinorGuardianVerificationRequest;
import org.keycloak.email.EmailException;
import org.keycloak.email.EmailSenderProvider;
import org.keycloak.forms.login.freemarker.model.UrlBean;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakUriInfo;
import org.keycloak.models.ThemeManager;
import org.keycloak.models.UserModel;
import org.keycloak.theme.Theme;
import org.keycloak.theme.ThemeProvider;
import org.keycloak.theme.freemarker.FreeMarkerProvider;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
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

    @POST
    @Path("send-minor-guardian-verification")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendTokenToUser(SendMinorGuardianVerificationRequest request) {
        UserModel currentUser = this.session.users()
            .getUserByEmail(
                session.getContext().getRealm(),
                request.userEmail
            );

        if (currentUser == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .header("Access-Control-Allow-Origin", "*")
                .entity(new BasicResponse("User not found"))
                .build();
        }

        try {
            sendGuardianVerificationEmail(currentUser, request);
            return Response.status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .entity(new BasicResponse("E-mail sent with success!"))
                .build();
        } catch (EmailException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .header("Access-Control-Allow-Origin", "*")
                .entity(e)
                .build();
        }
    }

    private void sendGuardianVerificationEmail(UserModel user, SendMinorGuardianVerificationRequest request) throws EmailException {
        try {
            ThemeManager themeManager = session.theme();
            Theme theme = themeManager.getTheme(
                session.getContext().getRealm().getEmailTheme(),
                Theme.Type.EMAIL
            );

            KeycloakUriInfo uriInfo = session.getContext().getUri();

            // 2. Initialize the UrlBean
            // This requires: Realm, Theme, Base URI, and the Action URI (usually null for simple emails)
            UrlBean urlBean = new UrlBean(
                session.getContext().getRealm(),
                theme,
                uriInfo.getBaseUri(),
                null
            );

            Map<String, Object> attributes = new HashMap<String, Object>() {{
                put("fullName", request.fullName);
                put("technosEmail", request.technosEmail);
                put("technosPhone", request.technosPhone);
                put("confirmationLink", request.confirmationLink);
                put("url", urlBean);
            }};

            logger.info("url bean: " + urlBean);

            FreeMarkerProvider freeMarker = session.getProvider(FreeMarkerProvider.class);
            String htmlBody = freeMarker.processTemplate(
                attributes,
                "minor-guardian-verification-email.ftl",
                theme
            );

            Properties messages = theme.getMessages(session.getContext().resolveLocale(user));
            String subject = messages.getProperty("minorGuardianVerificatinEmailSubject");

            logger.info("request.guardianEmail=" + request.guardianEmail);

            EmailSenderProvider emailSender = session.getProvider(EmailSenderProvider.class);
            emailSender.send(
                    session.getContext().getRealm().getSmtpConfig(),
                    request.guardianEmail,
                    subject,
                    "",
                    htmlBody
            );
        } catch (Exception e) {
            logger.info("Error: " + e);
            throw new EmailException("Error to sent email");
        }
    }

//    private String generateToken(String userId) {
//        long validityInSecs = session.getContext().getRealm().getActionTokenGeneratedByUserLifespan();
//        long absoluteExpirationInSecs = Instant.now().getEpochSecond() + validityInSecs;
//
//        return new GuardianEmailConfirmationActionToken(
//                userId,
//                (int)absoluteExpirationInSecs,
//                session.getContext().getClient().getId()
//        ).serialize(
//                session,
//                session.getContext().getRealm(),
//                session.getContext().getUri()
//        );
//    }
}
