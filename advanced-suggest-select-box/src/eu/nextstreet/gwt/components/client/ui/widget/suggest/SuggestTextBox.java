package eu.nextstreet.gwt.components.client.ui.widget.suggest;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;

public class SuggestTextBox extends TextBox implements HasDoubleClickHandlers {

	public SuggestTextBox() {
		super();
	}

	@Override
	public HandlerRegistration addDoubleClickHandler(
			final DoubleClickHandler handler) {
		return addDomHandler(handler, DoubleClickEvent.getType());
	}
}
