package eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.impl;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.UIObject;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.IconedValueHolderLabel;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultValueRenderer;

public class IconedValueRenderer<T> extends Composite implements
		IconedValueHolderLabel<T> {
	private static final String ITEM_HOVER = "eu-nextstreet-SuggestItemHover";
	public static final String SELECTED = "eu-nextstreet-SuggestItemSelected";

	protected HorizontalPanel horizontalPanel = new HorizontalPanel();
	protected Image icon;
	protected DefaultValueRenderer<T> label;

	public IconedValueRenderer(T value, Image icon, String filterText,
			boolean caseSensitive) {
		initWidget(horizontalPanel);
		label = new DefaultValueRenderer<T>(value, filterText, caseSensitive);
		this.icon = icon;

		horizontalPanel.setSpacing(5);
		horizontalPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		horizontalPanel.add(icon);
		horizontalPanel.setCellWidth(icon, "20px");

		horizontalPanel.add(label);
	}

	@Override
	public T getValue() {
		return label.getValue();
	}

	@Override
	public void setValue(T value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSelected(boolean selected) {
		// label.setSelected(selected);
		if (selected)
			addStyleName(SELECTED);
		else
			removeStyleName(SELECTED);
	}

	@Override
	public void hover(boolean hover) {
		// label.hover(hover);
		if (hover)
			addStyleName(ITEM_HOVER);
		else
			removeStyleName(ITEM_HOVER);

	}

	@Override
	public UIObject getUiObject() {
		return horizontalPanel;
	}

	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		icon.addMouseDownHandler(handler);
		return label.addMouseDownHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		icon.addMouseUpHandler(handler);
		return label.addMouseUpHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		icon.addMouseOutHandler(handler);
		return label.addMouseOutHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		icon.addMouseOverHandler(handler);
		return label.addMouseOverHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		icon.addMouseMoveHandler(handler);
		return label.addMouseMoveHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		icon.addMouseWheelHandler(handler);
		return label.addMouseWheelHandler(handler);
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		icon.addClickHandler(handler);
		return label.addClickHandler(handler);
	}

	@Override
	public Image getIcon() {
		return icon;
	}

}
