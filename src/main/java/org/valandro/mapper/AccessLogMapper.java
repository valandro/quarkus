package org.valandro.mapper;

import org.valandro.entity.AccessLogEntity;
import org.valandro.response.AccessLogResponse;

public class AccessLogMapper {
    public static AccessLogResponse mapTo(final AccessLogEntity entity) {
        return AccessLogResponse.builder()
                .email(entity.getEmail())
                .ip(entity.getIpAddress())
                .build();
    }
}
