package org.vaadin.appbase;

import lombok.Getter;

import org.junit.Before;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.service.templating.impl.TemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public abstract class AbstractAppBaseTest
{
  @Getter private UI                 testUI;
  @Getter private SessionContext     sessionContext;
  @Getter private ITemplatingService templatingService;

  /**
   * Creates a test {@link UI} object which will act as the current UI during the tests.
   */
  @Before
  public void startUp ()
  {
    sessionContext = new SessionContext ();
    templatingService = new TemplatingService ();

    testUI = new UI ()
    {
      @Override
      protected void init (VaadinRequest request)
      {
      }
    };
    UI.setCurrent (testUI);
  }

  /**
   * <p>
   * Starts up a {@link VaadinUIServices} object that can be configured with specialized mock
   * objects for the Spring injected services.
   * <p>
   * To provide your own mock objects, overwrite the corresponding getter-methods of this test base
   * ({@link #getSessionContext()}, ...).
   */
  protected void startVaadinUIServices ()
  {
    VaadinUIServices.startUp ();
    VaadinUIServices.get ().setContext (getSessionContext ());
    VaadinUIServices.get ().setTemplatingService (getTemplatingService ());
  }

  protected void registerOnEventbus ()
  {
    VaadinUIServices.get ().getEventbus ().register (this);
  }
}
