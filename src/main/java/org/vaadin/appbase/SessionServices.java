package org.vaadin.appbase;

import lombok.Getter;
import lombok.Setter;

import org.vaadin.appbase.event.AppEventBus;
import org.vaadin.appbase.event.IEventBus;

import com.vaadin.ui.UI;

public class SessionServices
{
  @Getter @Setter private Object data;

  @Getter private IEventBus      eventbus;

  public void startUp ()
  {

    if (UI.getCurrent ().getData () != null) { throw new IllegalStateException (
        "Given UI object already provides a data object through UI.getData(). Please use this object as container for this data instead."); }

    eventbus = new AppEventBus ();

    UI.getCurrent ().setData (this);
  }

}
