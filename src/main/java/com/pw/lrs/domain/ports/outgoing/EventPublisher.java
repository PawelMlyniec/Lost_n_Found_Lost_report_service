package com.pw.lrs.domain.ports.outgoing;

import com.google.protobuf.Message;

/**
 * Abstraction over event publishing
 */
public interface EventPublisher {

    /**
     * Publish domain event which is meant to be handled by external services
     *
     * @param userId user whose action triggered the event
     * @param event the event
     * @param <T> type of the event, should extend Protobuf {@link Message}
     */
    <T extends Message> void publishDomainEvent(String userId, T event);
}
