package app.security.rest;

import app.security.ISecurityController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class SecurityRoutes {
    ISecurityController securityController = new SecurityController();
    public EndpointGroup getSecurityRoute = () -> {
            path("/auth", () -> {
//          before(securityController::authenticate);
//          get("/", personEntityController.getAll(), Role.ANYONE);
//                get("/", personEntityController.getAll());
//                get("/resetdata", personEntityController.resetData());
//                get("/{id}", personEntityController.getById());

            post("/login", securityController.login());
//                put("/{id}", personEntityController.update());
//                delete("/{id}", personEntityController.delete());
        });
    };
}
