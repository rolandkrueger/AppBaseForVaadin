package org.vaadin.appbase;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import lombok.Getter;
import org.junit.Before;
import org.vaadin.appbase.event.AppEventBus;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.service.templating.impl.TemplatingService;
import org.vaadin.appbase.session.SessionContext;

public abstract class AbstractAppBaseTest
{
  @Getter private UI                 testUI;
  @Getter private SessionContext     sessionContext;
  @Getter private ITemplatingService templatingService;
  @Getter
  private AppEventBus eventBus;

  /**
   * Creates a test {@link UI} object which will act as the current UI during the tests.
   */
  @Before
  public void startUp ()
  {
    sessionContext = new SessionContext ();
    templatingService = new TemplatingService ();
    eventBus = new AppEventBus();

    testUI = new UI ()
    {
      @Override
      protected void init (VaadinRequest request)
      {
      }
    };
    UI.setCurrent (testUI);
  }

  protected void registerOnEventbus () {
    getEventBus().register(this);
  }
}
