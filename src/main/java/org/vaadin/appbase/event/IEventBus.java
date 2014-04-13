package org.vaadin.appbase.event;

import java.io.Serializable;

public interface IEventBus extends Serializable {
  public abstract void post(IEvent event);

  public abstract void register(Object listener);

  public abstract void unregister(Object listener);
}