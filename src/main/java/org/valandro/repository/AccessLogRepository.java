package org.valandro.repository;

import static org.valandro.entity.AccessLogEntity.from;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import org.valandro.entity.AccessLogEntity;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccessLogRepository {

    @Inject
    PgPool client;

    public Multi<AccessLogEntity> findAll() {
        return client.query("SELECT * FROM ACCESS_LOG").execute()
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(AccessLogEntity::from);
    }

    public Uni<AccessLogEntity> findById(final String id) {
        return client.preparedQuery("SELECT * FROM ACCESS_LOG WHERE ID = $1").execute(Tuple.of(Long.valueOf(id)))
                     .onItem().transform(RowSet::iterator)
                     .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }
}
