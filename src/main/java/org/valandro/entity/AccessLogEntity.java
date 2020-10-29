package org.valandro.entity;


import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccessLogEntity {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String ipAddress;

    public static AccessLogEntity from(final Row row) {
        return new AccessLogEntity(row.getLong("id"),
                row.getString("first_name"),
                row.getString("last_name"),
                row.getString("email"),
                row.getString("gender"),
                row.getString("ip_address"));
    }
}
