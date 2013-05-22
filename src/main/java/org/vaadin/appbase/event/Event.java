package org.vaadin.appbase.event;

import lombok.Getter;

public class Event implements IEvent
{
  @Getter private Object source;

  public Event (Object source)
  {
    this.source = source;
  }

  protected String formatLogMessageImpl ()
  {
    return "(Overwrite Event.formatLogMessageImpl() to provide additional data)";
  }

  @Override
  public String toString ()
  {
    return getClass () + ": [source=" + source + ", " + formatLogMessageImpl () + "]";
  }
}
