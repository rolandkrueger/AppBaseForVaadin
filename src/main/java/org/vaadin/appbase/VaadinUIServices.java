package org.vaadin.appbase;

import lombok.AccessLevel;
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
  @Autowired
  @Getter
  private IEventBus eventbus;
  @Autowired
  @Getter
  private PlaceManager placeManager;
  @Autowired
  @Getter
  private URIActionManager uriActionManager;
  @Autowired
  @Getter
  @Setter(AccessLevel.PROTECTED)
  private SessionContext context;
  @Autowired
  @Getter
  @Setter(AccessLevel.PROTECTED)
  private ITemplatingService templatingService;
  @Autowired
  @Getter
  @Setter(AccessLevel.PROTECTED)
  private IMessageProvider messageProvider;

  public void init() {
    uriActionManager.initialize();
  }
}
