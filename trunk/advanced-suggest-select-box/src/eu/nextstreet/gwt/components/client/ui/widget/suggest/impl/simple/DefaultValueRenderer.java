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

import com.google.gwt.user.client.ui.HTML;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderLabel;

public class DefaultValueRenderer<T> extends HTML implements ValueHolderLabel<T> {
	private static final String ITEM_HOVER = "eu-nextstreet-SuggestItemHover";
	private static final String MATCHING_STRING = "eu-nextstreet-SuggestMatchingString";
	public static final String SELECTED = "eu-nextstreet-SuggestItemSelected";
	protected T value;
	protected boolean caseSensitive;

	public DefaultValueRenderer(T value, String filterText, boolean caseSensitive) {
		this.value = value;
		this.caseSensitive = caseSensitive;
		fillHtml(value, filterText, caseSensitive);
	}

	protected void fillHtml(T value, String filterText, boolean caseSensitive) {
		String html = value.toString();
		html = highlightMatchingSequence(html, filterText, caseSensitive);
		setHTML(html);
	}

	protected String highlightMatchingSequence(String html, String filterText, boolean caseSensitive) {
		if (caseSensitive) {
			html = html.replace(filterText, "<span class='" + MATCHING_STRING + "'>" + filterText + "</span>");

		} else {
			String startSequence = "###start###";
			String endSequence = "###end###";
			String temp = html.toLowerCase()
				.replace(filterText.toLowerCase(), startSequence + filterText + endSequence);
			int firstIndex = temp.indexOf(startSequence);
			int lastIndex = temp.indexOf(endSequence) - startSequence.length();
			if (firstIndex > -1) {
				html = html.substring(0, firstIndex) + "<span class='" + MATCHING_STRING + "'>"
					+ html.substring(firstIndex, lastIndex) + "</span>"
					+ html.substring(firstIndex + filterText.length());
			}

		}
		return html;
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
