package br.com.grupotechnos.keycloak.routes;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProvider;
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProviderFactory;

public class AdminEmailProviderFactory implements AdminRealmResourceProviderFactory {

    public static final String ID = "technos-email";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public AdminRealmResourceProvider create(KeycloakSession session) {
        return new AdminEmailProvider(session);
    }

    @Override
    public void init(Scope config) { }

    @Override
    public void postInit(KeycloakSessionFactory factory) { }

    @Override
    public void close() { }

}