package org.app.dto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Alonso
 */
public class GenericEventDTO {
  
  private String eventType;
  private LocalDateTime timestamp;
  private String source;
  private Map<String, Object> payload;
  
  public void prepareForPublishing() {
    if (this.timestamp == null) {
      this.timestamp = LocalDateTime.now();
    }
    if (this.source == null) {
      this.source = "event-dispatcher-service";
    }
  }
  
  public String getEventType() {
    return eventType;
  }
  
  public void setEventType(String eventType) {
    this.eventType = eventType;
  }
  
  public LocalDateTime getTimestamp() {
    return timestamp;
  }
  
  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
  
  public String getSource() {
    return source;
  }
  
  public void setSource(String source) {
    this.source = source;
  }
  
  public Map<String, Object> getPayload() {
    return payload;
  }
  
  public void setPayload(Map<String, Object> payload) {
    this.payload = payload;
  }
}
