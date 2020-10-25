package org.valandro;

import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import org.valandro.response.ExampleResponse;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class Controller {

    @Route(path = "/example", methods = HttpMethod.GET, produces = MediaType.APPLICATION_JSON)
    public Uni<ExampleResponse> examples() {
        return Uni.createFrom()
                  .item(() -> ExampleResponse.builder()
                          .text("example")
                          .build());
    }
}
