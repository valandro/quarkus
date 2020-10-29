package org.valandro;

import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import org.valandro.mapper.AccessLogMapper;
import org.valandro.repository.AccessLogRepository;
import org.valandro.response.AccessLogResponse;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class AccessLogController {

    @Inject
    AccessLogRepository repository;

    @Route(path = "/log/:id", methods = HttpMethod.GET, produces = MediaType.APPLICATION_JSON)
    public Uni<AccessLogResponse> getAccessLog(@Param("id") String id) {
        return repository.findById(id)
                .map(AccessLogMapper::mapTo);
    }

    @Route(path = "/log", methods = HttpMethod.GET, produces = MediaType.APPLICATION_JSON)
    public Multi<AccessLogResponse> getAccessLogs() {
        return repository.findAll()
                    .map(AccessLogMapper::mapTo);
    }
}
