package org.valandro.resources;

import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.logging.Logger;
import org.valandro.domain.ErrorResponse;
import org.valandro.exception.HttpErrorException;
import org.valandro.mapper.AccessLogMapper;
import org.valandro.repository.AccessLogRepository;
import org.valandro.request.AccessLogRequest;
import org.valandro.response.AccessLogResponse;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

@ApplicationScoped
public class AccessLogResource {
    private static final Logger LOG = Logger.getLogger(AccessLogResource.class);

    @Inject
    AccessLogRepository repository;

    @Operation(summary = "Get access log by id", description = "This method gets a single access log by id")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Get specific access log information.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccessLogResponse.class))),
            @APIResponse(responseCode = "404",
                    description = "Log not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Route(path = "/log/:id", methods = HttpMethod.GET, produces = MediaType.APPLICATION_JSON)
    public Uni<AccessLogResponse> getAccessLog(@Param("id") String id) {
        LOG.info("Getting access log information.");
        return repository.findById(id)
                .onItem().ifNotNull().transform(AccessLogMapper::mapToResponse)
                .onItem().ifNull().failWith(() -> new HttpErrorException("Log not found", NOT_FOUND));
    }

    @Operation(summary = "Get all access logs", description = "This method gets all access logs")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Get specific access log information.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccessLogResponse.class))),
            @APIResponse(responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Route(path = "/log", methods = HttpMethod.GET, produces = MediaType.APPLICATION_JSON)
    public Multi<AccessLogResponse> getAccessLogs() {
        LOG.info("Getting all access log information.");
        return repository.findAll()
                    .filter(Objects::nonNull)
                    .map(AccessLogMapper::mapToResponse);
    }

    @Operation(summary = "Save new log information.", description = "This method save new log info")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccessLogResponse.class))),
            @APIResponse(responseCode = "406",
                    description = "Log not registered",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Route(path = "/log", methods = HttpMethod.POST, produces = MediaType.APPLICATION_JSON)
    public Uni<Void> register(@Body AccessLogRequest request) {
        LOG.info("Save access log information.");
        return repository.save(AccessLogMapper.mapToEntity(request))
                .onItem().ifNull().failWith(() -> new HttpErrorException("Log not registered", NOT_ACCEPTABLE))
                .onItem().ifNotNull().transform(e -> null);
    }
}
