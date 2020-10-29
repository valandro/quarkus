package org.valandro.repository;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import org.valandro.entity.AccessLogEntity;

public class AccessLogRepository {
    public static Multi<AccessLogEntity> findAll(final PgPool client) {
        return client.query("SELECT * FROM ACCESS_LOG").execute()
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(AccessLogEntity::from);
    }
}
