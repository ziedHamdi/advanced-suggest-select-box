package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.table;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderLabel;

public class SimpleTableRowItemRenderer<T> extends HorizontalPanel implements ValueHolderLabel<T> {
	protected T value;
	protected boolean caseSensitive;

	public SimpleTableRowItemRenderer(T value, String filterText, boolean caseSensitive) {
		this.value = value;
		this.caseSensitive = caseSensitive;
		fillHtml(value, filterText, caseSensitive);
	}

	protected void fillHtml(T value, String filterText, boolean caseSensitive) {
		String[] valueInColumns = explodeValueInColumns(value, filterText, caseSensitive);
		for (int i = 0; i < valueInColumns.length; i++) {
			String colText = valueInColumns[i];
			add(new HTML(highlightColumnText(i) ? highlightMatchingSequence(colText, filterText, caseSensitive)
				: colText));
		}
	}

	private String highlightMatchingSequence(String colText, String filterText, boolean caseSensitive) {
		return colText;
	}

	/**
	 * Specifies whether you want the highlight function to be called on teh column with index <code>i</code>
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
	protected String[] explodeValueInColumns(T value, String filterText, boolean caseSensitive) {
		return new String[] { value.toString() };
	}

	@Override
	public T getValue() {
		return null;
	}

	@Override
	public void hover(boolean hover) {
	}

	@Override
	public void setFocused(boolean focused) {
	}

	@Override
	public void setValue(T value) {
	}

	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return null;
	}

	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		return null;
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return null;
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return null;
	}

	@Override
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return null;
	}

	@Override
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		return null;
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return null;
	}

}
