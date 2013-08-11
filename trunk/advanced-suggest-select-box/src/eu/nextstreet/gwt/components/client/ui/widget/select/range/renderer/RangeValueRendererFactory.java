package eu.nextstreet.gwt.components.client.ui.widget.select.range.renderer;

import java.util.Map;

import eu.nextstreet.gwt.components.client.ui.widget.common.renderer.DefaultValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

public class RangeValueRendererFactory<T> extends DefaultValueRendererFactory<T, RangeValueRenderer<T>> {
	@Override
	public RangeValueRenderer<T> createValueRenderer(T value, String filterText, Map<String, Option<?>> options) {
		return new RangeValueRenderer<T>(value, filterText, false, this);
	}
}
