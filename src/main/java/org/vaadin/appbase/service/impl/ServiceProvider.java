package org.vaadin.appbase.service.impl;

import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.service.IServiceProvider;
import org.vaadin.spring.UIScope;

/**
 * Service Provider Implementierung für den Produktionsbetrieb. Damit die von Spring verwalteten
 * Komponenten auf Dienste zugreifen können, die vom {@link UI}/von der Session zur Verfügung
 * gestellt werden, müssten diese eine direkte Abhängigkeit zur {@link UI} bekommen, um auf die
 * statische Methode {@link UI#getCurrent()} zugreifen zu können. Dadurch würden aber die Objekte zu
 * stark gekoppelt werden, und das Testen der Komponenten wäre erschwert. Um das zu vermeiden,
 * greifen die Spring-Komponenten nicht direkt auf die UI-Dienste zu, sondern tun dies über eine
 * Zwischenschicht, bereitgestellt über das Interface {@link IServiceProvider}. Die Implementierung
 * {@link ServiceProvider} des Interfaces wird von Spring für die Dependency Injection verwendet.
 * Die Interface-Methoden greifen in dieser Implementierung intern direkt auf das aktuelle
 * UI/Session-Objekt zu. Damit beschränkt sich die Kopplung ausschließlich auf diese Klasse.
 * Unit-Tests können das Interface entsprechend auf ihre Weise implementieren und den
 * Spring-Komponenten über deren jeweilige Setter-Methoden übergeben.
 * 
 */
@Component
@UIScope
public class ServiceProvider implements IServiceProvider
{
  @Autowired
  private IEventBus eventBus;

  @Override
  public IEventBus getEventbus () {
    return eventBus;
  }

}
