package org.valandro.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.valandro.entity.AccessLogEntity;
import org.valandro.response.AccessLogResponse;

public class AccessLogMapperTest {

    @Test
    public void shouldReturnNullWhenMapEntityToResponse() {
        //given
        final AccessLogEntity entity = null;

        //when
        final AccessLogResponse result = AccessLogMapper.mapToResponse(entity);

        //then
        assertNull(result);
    }

    @Test
    public void shouldMapEntityToResponse() {
        //given
        final AccessLogEntity entity = new AccessLogEntity();
        entity.setIpAddress("ip");
        entity.setEmail("email");

        //when
        final AccessLogResponse result = AccessLogMapper.mapToResponse(entity);

        //then
        assertNotNull(result);
        assertEquals("ip", result.getIp());
        assertEquals("email", result.getEmail());
    }
}
