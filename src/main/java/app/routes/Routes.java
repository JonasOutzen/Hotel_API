package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

private HotelRoutes hotelRoutes = new HotelRoutes();
private Sample2Routes sample2Routes = new Sample2Routes();

public EndpointGroup getRoutes() {
    return () -> {
        get("/", ctx -> ctx.result("Hello World"));
        path("/sample1", hotelRoutes.getRoutes());
        path("/sample2", sample2Routes.getRoutes());
    };
}
}
