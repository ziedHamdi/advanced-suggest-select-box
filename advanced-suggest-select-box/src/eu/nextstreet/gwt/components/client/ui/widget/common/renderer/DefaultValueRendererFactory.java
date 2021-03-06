/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.nextstreet.gwt.components.client.ui.widget.common.renderer;

import java.util.Map;

import eu.nextstreet.gwt.components.client.ui.widget.common.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.common.StringFormulator;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultOptions;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.BooleanOption;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

/**
 * 
 * @author Zied Hamdi founder of http://1vu.fr founder of http://into-i.fr
 * 
 * @param <T>
 *          the type of the values
 * @param <W>
 *          the type of the value holder label
 * 
 * @see EventHandlingValueHolderItem
 * @see ValueRendererFactory
 */
public class DefaultValueRendererFactory<T, W extends EventHandlingValueHolderItem<T>> extends AbstractValueRendererFactory<T, W> {
	private static final String LIST_DEFAULT_STYLE = "ListRenderer";
	protected String listStyle = LIST_DEFAULT_STYLE;
	protected String itemStyle = EventHandlingValueHolderItem.ITEM_DEFAULT_STYLE;

	public DefaultValueRendererFactory() {
		super();
	}

	public DefaultValueRendererFactory(StringFormulator<T> stringFormulator) {
		super(stringFormulator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public W createValueRenderer(T value, String filterText, Map<String, Option<?>> options) {
		/** FIXME options should be read only at the lowest level (remove BooleanOption.isEnabled(DefaultOptions.CASE_SENSITIVE.name()) */
		W toReturn = (W) new DefaultValueRenderer<T>(value, filterText, BooleanOption.isEnabled(DefaultOptions.CASE_SENSITIVE.name(), options), options, this);
		toReturn.initWidget();
		toReturn.setStyleName(itemStyle);
		return toReturn;
	}

	@Override
	public ValueRendererFactory.ListRenderer<T, W> createListRenderer() {
		DefaultListRenderer<T, W> defaultListRenderer = new DefaultListRenderer<T, W>(this);
		defaultListRenderer.setStyleName(listStyle);
		return defaultListRenderer;
	}

	public String getListStyle() {
		return listStyle;
	}

	public void setListStyle(String listStyle) {
		this.listStyle = listStyle;
	}

	public String getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(String itemStyle) {
		this.itemStyle = itemStyle;
	}

}
