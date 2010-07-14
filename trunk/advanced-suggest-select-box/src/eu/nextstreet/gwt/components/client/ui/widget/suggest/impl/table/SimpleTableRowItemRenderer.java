package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.table;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.UIObject;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderLabel;

public class SimpleTableRowItemRenderer<T> extends BasicWidgetListHolder
		implements ValueHolderLabel<T> {
	private static final long serialVersionUID = 1L;
	protected T value;
	protected boolean caseSensitive;

	public SimpleTableRowItemRenderer(T value, String filterText,
			boolean caseSensitive) {
		this.value = value;
		this.caseSensitive = caseSensitive;
		fillHtml(value, filterText, caseSensitive);
	}

	protected void fillHtml(T value, String filterText, boolean caseSensitive) {
		String[] valueInColumns = explodeValueInColumns(value, filterText,
				caseSensitive);
		for (int i = 0; i < valueInColumns.length; i++) {
			String colText = valueInColumns[i];
			add(new HTML(highlightColumnText(i) ? highlightMatchingSequence(
					colText, filterText, caseSensitive) : colText));
		}
	}

	private String highlightMatchingSequence(String colText, String filterText,
			boolean caseSensitive) {
		return colText;
	}

	/**
	 * Specifies whether you want the highlight function to be called on teh
	 * column with index <code>i</code>
	 * 
	 * @param i
	 *            the column index starting from 0
	 * @return
	 */
	protected boolean highlightColumnText(int i) {
		return true;
	}

	/**
	 * Override to have <td>tags generated for you
	 * 
	 * @param filterText
	 * @param caseSensitive
	 * 
	 * @return
	 */
	protected String[] explodeValueInColumns(T value, String filterText,
			boolean caseSensitive) {
		return new String[] { value.toString() };
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void hover(boolean hover) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFocused(boolean focused) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStyleName(String item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValue(T value) {
		// TODO Auto-generated method stub

	}

	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		// TODO Auto-generated method stub

	}

	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UIObject getUiObject() {
		if (size() > 0)
			return get(0);
		return null;
	}
}
