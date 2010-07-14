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
package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderLabel;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;

/**
 * 
 * @author Zied Hamdi founder of http://into-i.fr
 * 
 * @param <T>
 *            the type of the values
 * @param <W>
 *            the type of the value holder label
 * 
 * @see ValueHolderLabel
 * @see ValueRendererFactory
 */
public class DefaultValueRendererFactory<T, W extends ValueHolderLabel<T>>
		implements ValueRendererFactory<T, W> {

	@SuppressWarnings("unchecked")
	@Override
	public W createValueRenderer(T value, String filterText,
			boolean caseSensitive) {
		return (W) new DefaultValueRenderer<T>(value, filterText, caseSensitive);
	}

	@Override
	public ValueRendererFactory.ListRenderer<T, W> createListRenderer() {
		DefaultListRenderer<T, W> defaultListRenderer = new DefaultListRenderer<T, W>();
		defaultListRenderer.setSpacing(0);
		return defaultListRenderer;
	}

}
