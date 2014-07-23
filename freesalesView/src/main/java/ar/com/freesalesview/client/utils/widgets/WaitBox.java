package ar.com.freesalesview.client.utils.widgets;

import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RootPanel;

public class WaitBox {

	private static WaitBox instance = new WaitBox();
	private static String HIDE_STYLE = "hide";
	private RootPanel waitbox;

	private WaitBox() {
		waitbox = RootPanel.get("wait-box");
	}

	public static WaitBox getInstance() {
		return instance;
	}

	public void show() {
		InlineHTML labelWidget = new InlineHTML("Loading");
		labelWidget.addStyleName("wait-box-item");
		waitbox.add(labelWidget);
		waitbox.removeStyleName(HIDE_STYLE);
	}

	public void hide() {
		waitbox.clear();
		waitbox.addStyleName(HIDE_STYLE);
	}

}
