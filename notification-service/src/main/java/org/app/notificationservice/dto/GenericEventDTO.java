package org.app.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Alonso
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericEventDTO {
  
  private String eventType;
  private Object payload;
  
  public String getEventType() { return eventType; }
  public void setEventType(String eventType) { this.eventType = eventType; }
  public Object getPayload() { return payload; }
  public void setPayload(Object payload) { this.payload = payload; }
  
}
