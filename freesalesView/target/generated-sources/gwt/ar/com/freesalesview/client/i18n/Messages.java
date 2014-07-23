package ar.com.freesalesview.client.i18n;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/dgattesco/workspace_freesale/freesales-2/freesalesView/src/main/resources/ar/com/freesalesview/client/i18n/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Welcome {0}, here are some recent updates.".
   * 
   * @return translated "Welcome {0}, here are some recent updates."
   */
  @DefaultMessage("Welcome {0}, here are some recent updates.")
  @Key("dashboard.main.content.welcome")
  String dashboard_main_content_welcome(String arg0);

  /**
   * Translated "Detail".
   * 
   * @return translated "Detail"
   */
  @DefaultMessage("Detail")
  @Key("dashboard.notification.single.detail")
  String dashboard_notification_single_detail();

  /**
   * Translated "Notifications".
   * 
   * @return translated "Notifications"
   */
  @DefaultMessage("Notifications")
  @Key("dashboard.notification.title")
  String dashboard_notification_title();
}
