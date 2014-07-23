package ar.com.freesalesview.client.utils;

import com.google.gwt.dom.client.Element;

public class JSUtils {

	public static native void close(Element idPopup) /*-{
		$wnd.closePopupInfo(idPopup);
	}-*/;
	
	
	public static native void resetCalendar()/*-{
		$wnd.resetCalendar();
	}-*/;
	
	
	public static native void deselectAll(Element element)/*-{
		$wnd.deselectAll(element);
	}-*/;
}
