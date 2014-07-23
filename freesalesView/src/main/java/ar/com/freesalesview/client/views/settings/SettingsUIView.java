package ar.com.freesalesview.client.views.settings;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SettingsUIView extends Composite implements SettingsView {

	private static SettingsUIViewUiBinder uiBinder = GWT
			.create(SettingsUIViewUiBinder.class);

	interface SettingsUIViewUiBinder extends UiBinder<Widget, SettingsUIView> {
	}
	
	private Presenter presenter;

	public SettingsUIView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
