package org.vaadin.appbase.event.impl.error;

import lombok.Getter;

import org.vaadin.appbase.enums.ErrorSeverity;
import org.vaadin.appbase.event.Event;

public class ErrorEvent extends Event
{
  @Getter private ErrorSeverity severity;
  @Getter private String        message;
  @Getter private Throwable     exception;

  public ErrorEvent (Object source, ErrorSeverity severity, String message, Throwable exception)
  {
    this (source, severity, message);
    this.exception = exception;
  }

  public ErrorEvent (Object source, ErrorSeverity severity, String message)
  {
    super (source);
    this.severity = severity;
    this.message = message;
  }
}
