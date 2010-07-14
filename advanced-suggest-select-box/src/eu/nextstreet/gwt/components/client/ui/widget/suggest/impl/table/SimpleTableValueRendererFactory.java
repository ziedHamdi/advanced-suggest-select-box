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
package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.table;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;

/**
 * The factory for the inclueded table popup content builder. You should
 * override {@link #newInstance(Object, String, boolean)} to give your instance
 * to the factory and have it handle the rest of the work
 * 
 * @author Zied Hamdi founder of http://into-i.fr
 * 
 * @param <T>
 *            the value type (for each item)
 * @param <W>
 *            the view object representing the value row
 */
public class SimpleTableValueRendererFactory<T, W extends SimpleTableRowItemRenderer<T>>
		implements ValueRendererFactory<T, W> {
	private String tableStyle = "eu-nextstreet-SuggestFieldPopupSimpleTable";
	private String cellStyle = "eu-nextstreet-SuggestFieldPopupSimpleTableCell";

	@Override
	public SimpleTableListRenderer<T, W> createListRenderer() {
		SimpleTableListRenderer<T, W> simpleTableListRenderer = new SimpleTableListRenderer<T, W>();
		simpleTableListRenderer.setStyleName(tableStyle);
		return simpleTableListRenderer;
	}

	@Override
	public W createValueRenderer(T value, String filterText,
			boolean caseSensitive) {
		W toReturn = newInstance(value, filterText, caseSensitive);
		toReturn.setStyleName(cellStyle);
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	protected W newInstance(T value, String filterText, boolean caseSensitive) {
		W toReturn = (W) new SimpleTableRowItemRenderer<T>(value, filterText,
				caseSensitive);
		return toReturn;
	}

	public String getTableStyle() {
		return tableStyle;
	}

	public void setTableStyle(String tableStyle) {
		this.tableStyle = tableStyle;
	}

	public String getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(String cellStyle) {
		this.cellStyle = cellStyle;
	}
}
