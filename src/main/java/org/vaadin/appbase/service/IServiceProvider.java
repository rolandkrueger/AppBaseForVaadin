package org.vaadin.appbase.service;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.service.impl.ServiceProvider;

import java.io.Serializable;

/**
 * Provider für Dienste der aktuellen Session. Die Dienste, die die Spring-Komponenten in der Produktionsumgebung von
 * der {@link VaadinSession} oder vom aktuellen {@link UI}-Objekt bekommen, werden über dieses Interface gekapselt
 * geliefert. In den Unit-Tests kann damit entsprechend eine Implementierung für dieses Interface verwendet werden, die
 * für die Dienste entsprechende Mocks zurückgibt.
 *
 * @author Roland Krüger
 * @see ServiceProvider
 */
public interface IServiceProvider extends Serializable {
    public IEventBus getEventbus();
}
