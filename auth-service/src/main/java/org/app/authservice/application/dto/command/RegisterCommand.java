package org.app.authservice.application.dto.command;

/**
 * @author Alonso
 */
public record RegisterCommand(
    String username,
    String password
) {

}
