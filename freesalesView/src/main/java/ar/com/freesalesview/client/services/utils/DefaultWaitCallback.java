package ar.com.freesalesview.client.services.utils;

import ar.com.freesalesview.client.utils.widgets.WaitBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class DefaultWaitCallback<T> implements AsyncCallback<T> {

	public void onSuccess(T result){
		success(result);
		WaitBox.getInstance().hide();
	}

	public final void onFailure(Throwable caught) {
		WaitBox.getInstance().hide();
		failure(caught);
	}

	public abstract void success(T result);

	public void failure(Throwable caught) {
		try {
			throw caught;
		} catch (Throwable e1) {
			GWT.log(caught.getMessage(), caught);
		}
	}
}
