package org.vaadin.appbase.service;

import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.service.impl.ServiceProvider;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

/**
 * Provider für Dienste der aktuellen Session. Die Dienste, die die Spring-Komponenten in der
 * Produktionsumgebung von der {@link VaadinSession} oder vom aktuellen {@link UI}-Objekt bekommen,
 * werden über dieses Interface gekapselt geliefert. In den Unit-Tests kann damit entsprechend eine
 * Implementierung für dieses Interface verwendet werden, die für die Dienste entsprechende Mocks
 * zurückgibt.
 * 
 * @see ServiceProvider
 * @author Roland Krüger
 */
public interface IServiceProvider
{
  public IEventBus getEventbus ();

}
