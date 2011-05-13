package eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.impl;

import com.google.gwt.user.client.ui.Image;

import eu.nextstreet.gwt.components.client.ui.common.data.ValueRepresentationTransformer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.IconedValueHolderLabel;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.DefaultSuggestBox;

/**
 * A suggest box with Icon decorated values
 * 
 * @author Zied Hamdi
 * 
 * @param <T>
 *          items type
 * @param <W>
 *          renderer
 */
public class DefaultIconedSuggestBox<T, W extends IconedValueHolderLabel<T>>
		extends DefaultSuggestBox<T, W> {
	/** Gives an image for each value */
	@SuppressWarnings("unchecked")
	protected ValueRepresentationTransformer<T, Image> iconLinker = ValueRepresentationTransformer.NULL_TRANSFORMER;

	public DefaultIconedSuggestBox(String defaultText) {
		super(defaultText);
	}

	@Override
	protected void init(String defaultText) {
		super.init(defaultText);
		setValueRendererFactory(new IconedValueRendererFactory<T, W>(iconLinker));
	}

	/**
	 * Value changes trigger icon changes
	 */
	public void valueSelected(T value) {
		super.valueSelected(value);
		textField.setLeftWidget(iconLinker.transform(value));
	};

	/**
	 * Typed values trigger icon removal
	 */
	@Override
	public void valueTyped(String value) {
		super.valueTyped(value);
		textField.setLeftWidget(null);
	}

	public ValueRepresentationTransformer<T, Image> getIconLinker() {
		return iconLinker;
	}

	public void setIconLinker(ValueRepresentationTransformer<T, Image> iconLinker) {
		this.iconLinker = iconLinker;
		((IconedValueRendererFactory<T, W>) valueRendererFactory)
				.setIconLinker(iconLinker);
	}

}
