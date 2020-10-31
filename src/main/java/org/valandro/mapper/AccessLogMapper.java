package org.valandro.mapper;

import static java.util.Objects.isNull;

import org.valandro.entity.AccessLogEntity;
import org.valandro.request.AccessLogRequest;
import org.valandro.response.AccessLogResponse;

public class AccessLogMapper {
    public static AccessLogResponse mapToResponse(final AccessLogEntity entity) {
        if (isNull(entity)) return null;
        return AccessLogResponse.builder()
                .email(entity.getEmail())
                .ip(entity.getIpAddress())
                .build();
    }

    public static AccessLogEntity mapToEntity(final AccessLogRequest request) {
        final AccessLogEntity entity = new AccessLogEntity();
        entity.setEmail(request.getEmail());
        entity.setFirstName(request.getFirstName());
        entity.setGender(request.getGender());
        entity.setIpAddress(request.getIpAddress());
        entity.setLastName(request.getLastName());

        return entity;
    }
}
