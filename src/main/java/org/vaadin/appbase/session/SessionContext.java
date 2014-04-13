package org.vaadin.appbase.session;

import java.io.Serializable;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class SessionContext implements Serializable {

  private static final long serialVersionUID = -1178538977327157062L;

  private Locale locale;

  public void setLocale(Locale locale) {
    log.debug("Setting locale: " + locale);
    this.locale = new Locale(locale.getLanguage());
  }

  public Locale getLocale() {
    return locale;
  }

  public Locale getDefaultLocale() {
    return Locale.ENGLISH;
  }

}
