package org.vaadin.appbase;

import static org.junit.Assert.assertSame;
import static org.vaadin.appbase.VaadinUIServices.UIServices;

import org.junit.Test;

import com.vaadin.ui.UI;

public class VaadinUIServicesTest extends AbstractAppBaseTest
{
  @Test
  public void testGet ()
  {
    VaadinUIServices.startUp ();
    VaadinUIServices vaadinUIServices1 = UIServices ();
    VaadinUIServices vaadinUIServices2 = UIServices ();
    assertSame (vaadinUIServices1, vaadinUIServices2);
  }

  @Test (expected = IllegalStateException.class)
  public void testGet_Fails ()
  {
    UIServices ();
  }

  @Test (expected = IllegalStateException.class)
  public void testGet_FailsWithoutPriorCallToStartUpAndAGivenUIDataObject ()
  {
    UI.getCurrent ().setData (new Object ());
    UIServices ();
  }

  @Test (expected = IllegalStateException.class)
  public void testStartUp_FailsIfDataObjectIsAlreadyDefinedOnUI ()
  {
    UI.getCurrent ().setData (new Object ());
    VaadinUIServices.startUp ();
  }

  @Test (expected = IllegalStateException.class)
  public void testStartUp_FailsIfCalledTwice ()
  {
    VaadinUIServices.startUp ();
    VaadinUIServices.startUp ();
  }
}
