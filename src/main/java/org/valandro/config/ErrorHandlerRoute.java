package org.valandro.config;

import static io.quarkus.vertx.web.Route.HandlerType.FAILURE;

import io.quarkus.vertx.web.Route;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.valandro.exception.HttpErrorException;
import java.util.Optional;


/*
    Defines an failure route that intercept the Exception and convert to an JSON object.
 */
public class ErrorHandlerRoute {

    @Route(path = "/*", type = FAILURE)
    public void error(RoutingContext context) {
        Optional.ofNullable(context.failure())
                .ifPresentOrElse(throwable -> {
                    if (throwable instanceof HttpErrorException) {
                        final HttpErrorException exception = (HttpErrorException) throwable;
                        final int status = exception.getHttpStatus().getStatusCode();
                        context.response()
                               .setStatusCode(status)
                               .end(new JsonObject().put("code", status)
                                       .put("exceptionType", exception.getClass().getSimpleName())
                                       .put("message", exception.getMessage())
                                       .encode());
                    } else {
                        int status = context.statusCode();
                        context.response()
                               .setStatusCode(status)
                               .end(new JsonObject().put("code", status)
                                       .put("exceptionType", throwable.getClass().getSimpleName())
                                       .put("message", throwable.getMessage())
                                       .encode());
                    }
                }, context::next);
    }
}
