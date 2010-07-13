package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.table;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;

public class SimpleTableRowItemRenderer<T> extends Widget {

	public SimpleTableRowItemRenderer(T value, String filterText, boolean caseSensitive) {
		super();
		//		super(value, filterText, caseSensitive);
	}

	protected void fillHtml(T value, String filterText, boolean caseSensitive) {
		setElement(Document.get().createTDElement());
		String[] valueInColumns = explodeValueInColumns(value, filterText, caseSensitive);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < valueInColumns.length; i++) {
			String colText = valueInColumns[i];
			//			buffer.append("<td>");
			buffer.append(highlightColumnText(i) ? highlightMatchingSequence(colText, filterText, caseSensitive)
				: colText);
			//			buffer.append("</td>");
		}
		getElement().setInnerHTML(buffer.toString());
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

}
