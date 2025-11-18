package org.app.authservice.application.dto.command;

public record SendGenericEventCommand(
    String eventType,
    Object payload
) {

}
