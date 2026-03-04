package br.com.grupotechnos.keycloak.routes;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.services.resources.admin.AdminEventBuilder;
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProvider;
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator;

public class AdminEmailProvider implements AdminRealmResourceProvider {

    private final KeycloakSession session;

    public AdminEmailProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void close() { }

    @Override
    public Object getResource(KeycloakSession keycloakSession, RealmModel realmModel, AdminPermissionEvaluator adminPermissionEvaluator, AdminEventBuilder adminEventBuilder) {
        return new AdminEmailResource(session);
    }
}
