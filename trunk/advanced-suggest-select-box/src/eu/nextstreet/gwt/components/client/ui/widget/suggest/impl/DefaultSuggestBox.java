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
package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl;

import java.util.ArrayList;
import java.util.List;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractSuggestBox;

/**
 * Implements a simple value holding suggest box, more advanced implementations
 * can have a type safe model to hold and manage items.
 * 
 * @author Zied Hamdi
 * 
 * @param <T>
 */
public class DefaultSuggestBox<T> extends AbstractSuggestBox<T> {
	protected List<T> possiblilities = new ArrayList<T>();

	public DefaultSuggestBox() {
	}

	public void add(T t) {
		possiblilities.add(t);
	}

	public void clear() {
		possiblilities.clear();
	}

	@Override
	protected List<T> getFiltredPossibilities(String text) {
		List<T> toReturn = new ArrayList<T>();
		for (T t : possiblilities) {
			if (accept(text, t))
				toReturn.add(t);
		}
		return toReturn;
	}

	/**
	 * used to define the filtering strategy, override and check in the inner
	 * list if this element should appear
	 * 
	 * @param text
	 * @param t
	 * @return
	 */
	protected boolean accept(String text, T t) {
		if (toString(t).toUpperCase().startsWith(text.toUpperCase())) {
			return true;
		}
		return false;
	}

}
