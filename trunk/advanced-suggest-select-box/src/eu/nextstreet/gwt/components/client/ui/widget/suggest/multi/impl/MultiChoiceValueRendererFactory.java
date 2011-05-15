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

package eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.impl;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.MultiChoiceListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.MultiChoiceValueHolderItem;

/**
 * 
 * @author Zied Hamdi
 * 
 * @param <T>
 *          item value type
 * @param <C>
 *          concrete ValueHolderItem
 */
public class MultiChoiceValueRendererFactory<T, C extends MultiChoiceValueHolderItem<T, C>>
		implements ValueRendererFactory<T, C> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory
	 * #createValueRenderer(java.lang.Object, java.lang.String, boolean)
	 */
	@Override
	public C createValueRenderer(T value, String filterText, boolean caseSensitive) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory
	 * #createListRenderer()
	 */
	@Override
	public eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer<T, C> createListRenderer() {
		return new MultiChoiceListRenderer<T, C>();
	}

}
