package br.com.grupotechnos.keycloak.routes.models;

public class MinorVerificationResponse {
    public String email;
    public boolean validated;

    public MinorVerificationResponse(String email, boolean validated) {
        this.email = email;
        this.validated = validated;
    }
}
