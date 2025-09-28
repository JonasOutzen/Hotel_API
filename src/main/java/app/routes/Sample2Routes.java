package app.routes;

import app.controllers.HotelController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;


public class Sample2Routes {
    HotelController controller = new HotelController();

    public EndpointGroup getRoutes(){
        return () -> {
            get("/", controller::getAllSamples);
            get("/{id}", controller::getSampleById);
            post("/", controller::createSample);
        };
    }
}
