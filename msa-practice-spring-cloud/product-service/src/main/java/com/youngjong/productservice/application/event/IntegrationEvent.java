package com.youngjong.productservice.application.event;

import lombok.Getter;

@Getter
public abstract class IntegrationEvent<T> {
    private final String eventType;
    private final String eventVersion;
    private final String traceId;
    private final T payload;

    protected IntegrationEvent(String eventType, String eventVersion, String traceId, T payload) {
        this.eventType = eventType;
        this.eventVersion = eventVersion;
        this.traceId = traceId;
        this.payload = payload;
    }


}
