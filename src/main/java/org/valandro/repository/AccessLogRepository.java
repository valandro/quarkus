package org.valandro.repository;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
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
}
