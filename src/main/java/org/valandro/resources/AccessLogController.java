package org.valandro.resources;

import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import org.valandro.exception.HttpErrorException;
import org.valandro.mapper.AccessLogMapper;
import org.valandro.repository.AccessLogRepository;
import org.valandro.request.AccessLogRequest;
import org.valandro.response.AccessLogResponse;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@ApplicationScoped
public class AccessLogController {

    @Inject
    AccessLogRepository repository;

    @Route(path = "/log/:id", methods = HttpMethod.GET, produces = MediaType.APPLICATION_JSON)
    public Uni<AccessLogResponse> getAccessLog(@Param("id") String id) {
        return repository.findById(id)
                .onItem().ifNotNull().transform(AccessLogMapper::mapToResponse)
                .onItem().ifNull().failWith(() -> new HttpErrorException("Log not found.", NOT_FOUND));
    }

    @Route(path = "/log", methods = HttpMethod.GET, produces = MediaType.APPLICATION_JSON)
    public Multi<AccessLogResponse> getAccessLogs() {
        return repository.findAll()
                    .filter(Objects::nonNull)
                    .map(AccessLogMapper::mapToResponse);
    }

    @Route(path = "/log", methods = HttpMethod.POST, produces = MediaType.APPLICATION_JSON)
    public Uni<Void> register(@Body AccessLogRequest request) throws Exception {
        return repository.save(AccessLogMapper.mapToEntity(request))
                .onItem().ifNull().failWith(() -> new HttpErrorException("Log not registered", NOT_ACCEPTABLE))
                .onItem().ifNotNull().transform(e -> null);
    }
}
