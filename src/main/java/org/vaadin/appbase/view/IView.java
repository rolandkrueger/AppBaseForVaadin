package org.vaadin.appbase.view;

import com.vaadin.ui.Component;

public interface IView
{

  public void buildLayout ();

  public Component getContent ();

}
