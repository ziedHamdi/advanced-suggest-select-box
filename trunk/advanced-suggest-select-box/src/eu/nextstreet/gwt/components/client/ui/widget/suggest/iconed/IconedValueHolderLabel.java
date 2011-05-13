package eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed;

import com.google.gwt.user.client.ui.Image;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderLabel;

/**
 * Adds the parsing for icon values
 * 
 * @author Zied Hamdi
 * 
 * @param <T>
 *          a value that can be represented by an icon
 */
public interface IconedValueHolderLabel<T> extends ValueHolderLabel<T> {

	/**
	 * Returns the icon representing the current value
	 * 
	 * @return icon
	 */
	Image getIcon();
}
