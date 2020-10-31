package org.valandro.repository;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.valandro.entity.AccessLogEntity.from;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import org.valandro.entity.AccessLogEntity;
import org.valandro.exception.HttpErrorException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccessLogRepository {

    @Inject
    PgPool client;

    public Multi<AccessLogEntity> findAll() {
        return client.query("SELECT * FROM ACCESS_LOG").execute()
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(AccessLogEntity::from)
                .onFailure().transform(t -> new HttpErrorException(t.getMessage(), INTERNAL_SERVER_ERROR));
    }

    public Uni<AccessLogEntity> findById(final String id) {
        return client.preparedQuery("SELECT * FROM ACCESS_LOG WHERE ID = $1").execute(Tuple.of(Long.valueOf(id)))
                     .onItem().transform(RowSet::iterator)
                     .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null)
                     .onFailure().transform(t -> new HttpErrorException(t.getMessage(), INTERNAL_SERVER_ERROR));
    }

    public Uni<Long> save(final AccessLogEntity e) {
        return client.preparedQuery("INSERT INTO ACCESS_LOG(ID, FIRST_NAME, LAST_NAME, EMAIL, GENDER, IP_ADDRESS) VALUES (nextval('ACCESS_LOG_SEQUENCE'), $1, $2, $3, $4, $5) RETURNING  (id)")
              .execute(Tuple.of(e.getFirstName(), e.getLastName(), e.getEmail(), e.getGender(), e.getIpAddress()))
              .onItem().transform(pgRowSet -> pgRowSet.iterator().next().getLong("id"))
              .onFailure().transform(t -> new HttpErrorException(t.getMessage(), INTERNAL_SERVER_ERROR));
    }
}
