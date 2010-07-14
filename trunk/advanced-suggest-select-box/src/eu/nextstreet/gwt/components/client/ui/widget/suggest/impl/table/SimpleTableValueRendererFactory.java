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
 * 
 * @author Zied Hamdi founder of http://into-i.fr
 * 
 * @param <T>
 */
public class SimpleTableValueRendererFactory<T, W extends SimpleTableRowItemRenderer<T>>
		implements ValueRendererFactory<T, W> {
	public String tableStyle = "eu-nextstreet-SuggestFieldPopupSimpleTable";

	@Override
	public SimpleTableListRenderer<T, W> createListRenderer() {
		SimpleTableListRenderer<T, W> simpleTableListRenderer = new SimpleTableListRenderer<T, W>();
		simpleTableListRenderer.setStyleName(tableStyle);
		return simpleTableListRenderer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public W createValueRenderer(T value, String filterText,
			boolean caseSensitive) {
		return (W) new SimpleTableRowItemRenderer<T>(value, filterText,
				caseSensitive);
	}

}
