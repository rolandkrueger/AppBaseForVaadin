package org.vaadin.appbase;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.places.PlaceManager;
import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;
import org.vaadin.appbase.uriactions.URIActionManager;
import org.vaadin.spring.UIScope;

import java.io.Serializable;

@UIScope
@Component
public class VaadinUIServices implements Serializable {

  private static final long serialVersionUID = -6765728153503813392L;

  @Getter
  @Setter
  private Object data;
  @Getter
  private final IEventBus eventbus;
  @Getter
  private final PlaceManager placeManager;
  @Getter
  private final URIActionManager uriActionManager;
  @Getter
  private final SessionContext context;
  @Getter
  private final ITemplatingService templatingService;
  @Getter
  private final IMessageProvider messageProvider;

  @Autowired
  public VaadinUIServices(final IEventBus eventbus, final PlaceManager placeManager, final URIActionManager uriActionManager, final SessionContext context, final ITemplatingService templatingService, final IMessageProvider messageProvider) {
    this.eventbus = eventbus;
    this.placeManager = placeManager;
    this.uriActionManager = uriActionManager;
    this.context = context;
    this.templatingService = templatingService;
    this.messageProvider = messageProvider;
  }
}
