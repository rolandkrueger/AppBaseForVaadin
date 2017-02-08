package org.vaadin.appbase.uriactions;

import org.roklib.urifragmentrouting.UriActionMapperTree;
import org.vaadin.appbase.event.IEventBus;

public class RoutingContextData {
  private IEventBus eventBus;
  private UriActionMapperTree uriActionMapperTree;

  public RoutingContextData(IEventBus eventBus, UriActionMapperTree uriActionMapperTree) {
    this.eventBus = eventBus;
    this.uriActionMapperTree = uriActionMapperTree;
  }

  public IEventBus getEventBus() {
    return eventBus;
  }

  public UriActionMapperTree getUriActionMapperTree() {
    return uriActionMapperTree;
  }
}
