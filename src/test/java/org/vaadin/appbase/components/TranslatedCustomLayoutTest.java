package org.vaadin.appbase.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.vaadin.appbase.VaadinUIServices.UIServices;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.junit.Test;
import org.vaadin.appbase.AbstractAppBaseTest;
import org.vaadin.appbase.enums.ErrorSeverity;
import org.vaadin.appbase.event.impl.error.ErrorEvent;
import org.vaadin.appbase.service.templating.ITemplatingService;

import com.google.common.eventbus.Subscribe;

public class TranslatedCustomLayoutTest extends AbstractAppBaseTest
{
  private ErrorEvent event;

  @Test
  public void testBuildLayout_FailsWithIOException ()
  {
    startVaadinUIServices ();
    registerOnEventbus ();
    UIServices ().getEventbus ().register (this);

    TranslatedCustomLayout view = new TranslatedCustomLayout ("");
    view.buildLayout ();

    assertNotNull (event);
    assertSame (event.getSource (), view);
    assertEquals (ErrorSeverity.FATAL, event.getSeverity ());
  }

  @Subscribe
  public void onErrorEvent (ErrorEvent event)
  {
    this.event = event;
  }

  /**
   * Return an InputStream that throws an IOException when read.
   */
  @Override
  public ITemplatingService getTemplatingService ()
  {
    return new ITemplatingService ()
    {
      @Override
      public InputStream getLayoutTemplate (Locale forLocale, String templatePath)
      {
        return new InputStream ()
        {
          @Override
          public int read () throws IOException
          {
            throw new IOException ();
          }
        };
      }
    };
  }

}
