package org.vaadin.appbase.event;

import lombok.extern.slf4j.Slf4j;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

@Slf4j
public class AppEventBus implements IEventBus {

  private static final long serialVersionUID = 1225711706929234463L;

  private EventBus eventbus;

  public AppEventBus() {
    eventbus = new EventBus(new SubscriberExceptionHandler() {
      @Override
      public void handleException(Throwable exception, SubscriberExceptionContext context) {
        log.error("Could not dispatch event: " + context.getSubscriber() + " to " + context.getSubscriberMethod());
        exception.printStackTrace();
      }
    });
  }

  @Override
  public void post(IEvent event) {
    if (log.isTraceEnabled()) {
      log.trace(event.getSource() + " posted event: " + event);
    }
    eventbus.post(event);
  }

  @Override
  public void register(Object listener) {
    if (log.isTraceEnabled()) {
      log.trace("Registering event bus listener: " + listener);
    }
    eventbus.register(listener);
  }

  @Override
  public void unregister(Object listener) {
    if (log.isTraceEnabled()) {
      log.trace("Removing listener from event bus: " + listener);
    }
    eventbus.unregister(listener);
  }
}
