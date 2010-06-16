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

import com.google.gwt.user.client.ui.HTML;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderLabel;

public class DefaultValueRenderer<T> extends HTML implements
		ValueHolderLabel<T> {
	private static final String ITEM_HOVER = "eu-nextstreet-SuggestItemHover";
	private static final String MATCHING_STRING = "eu-nextstreet-SuggestMatchingString";
	public static final String SELECTED = "eu-nextstreet-SuggestItemSelected";
	protected T value;
	protected boolean caseSensitive;

	public DefaultValueRenderer(T value, String filterText,
			boolean caseSensitive) {
		this.value = value;
		this.caseSensitive = caseSensitive;
		String html = value.toString();
		if (caseSensitive) {
			html = html.replace(filterText, "<span class='" + MATCHING_STRING
					+ "'>" + filterText + "</span>");

		} else {
			String temp = html.toLowerCase().replace(
					filterText.toLowerCase(),
					"<span class='" + MATCHING_STRING + "'>" + filterText
							+ "</span>");
			int firstIndex = temp.indexOf("<span");
			int lastIndex = temp.indexOf("</span") + "</span>".length();
			if (firstIndex > -1) {
				html = html.substring(0, firstIndex)
						+ temp.substring(firstIndex, lastIndex)
						+ html.substring(firstIndex + filterText.length());
			}

		}
		setHTML(html);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public void hover(boolean hover) {
		if (hover)
			addStyleName(ITEM_HOVER);
		else
			removeStyleName(ITEM_HOVER);
	}

	@Override
	public void setFocused(boolean focused) {
		if (focused)
			addStyleName(SELECTED);
		else
			removeStyleName(SELECTED);
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
}
