package org.vaadin.appbase.view;

import com.vaadin.ui.Component;

public interface IView
{

  public IView buildLayout ();

  public Component getContent ();

}
