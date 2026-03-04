package br.com.grupotechnos.keycloak.routes.models;

public class SendMinorGuardianVerificationRequest {
    public String userEmail = "";
    public String guardianEmail = "";
    public String fullName = "";
    public String technosEmail = "";
    public String technosPhone = "";
    public String confirmationLink = "";

    public SendMinorGuardianVerificationRequest(
        String userEmail,
        String guardianEmail,
        String fullName,
        String technosEmail,
        String technosPhone,
        String confirmationLink
    ) {
        this.userEmail = userEmail;
        this.guardianEmail = guardianEmail;
        this.fullName = fullName;
        this.technosEmail = technosEmail;
        this.technosPhone = technosPhone;
        this.confirmationLink = confirmationLink;
    }

    public SendMinorGuardianVerificationRequest() {

    }
}
