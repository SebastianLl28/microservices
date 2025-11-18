package org.app.matriculaservice.application.exception;

import java.time.Instant;

/**
 * @author Alonso
 */
public record ApiError(String message, int statusCode, String path, Instant timestamp) {
}
