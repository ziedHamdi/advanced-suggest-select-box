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
package eu.nextstreet.gwt.components.client.ui.widget;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;

/**
 * A text box that handles additional events like the double click and supports
 * the default text feature
 * 
 * NOTE: You should use {@link #getTextValue()} instead of {@link #getText()} to
 * avoid getting the default text instead of an empty one.
 * 
 * <BR/>
 * This class has the following styles:
 * <ul>
 * <li><b>eu-nextstreet-AdvancedTextBoxDefaultText</b> defines the style of the
 * default text</li>
 * </ul>
 * 
 * @author Zied Hamdi founder of http://into-i.fr
 * 
 */
public class AdvancedTextBox extends TextBox implements HasDoubleClickHandlers {
	private static final String SUGGEST_FIELD_DEFAULT_TEXT = "eu-nextstreet-AdvancedTextBoxDefaultText";

	protected String defautText;

	public AdvancedTextBox() {
		this(null);
	}

	public AdvancedTextBox(String defautText) {
		this.defautText = defautText;
		addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (AdvancedTextBox.this.defautText != null) {
					setSelectionRange(0, AdvancedTextBox.super.getText()
							.length());
				}
			}
		});

		addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				handleDefaultText();
			}
		});

		addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				handleDefaultTextStyles();
			}
		});
	}

	@Override
	public HandlerRegistration addDoubleClickHandler(
			final DoubleClickHandler handler) {
		return addDomHandler(handler, DoubleClickEvent.getType());
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		handleDefaultText();
	}

	/**
	 * If the box text is empty fills it with the default value
	 * 
	 * @param text
	 */
	protected void handleDefaultText() {
		String text = getText();
		if (text == null || text.trim().length() == 0) {
			super.setText(defautText);
		}

		handleDefaultTextStyles();
	}

	/**
	 * Adds or removes the default text style depending on the value
	 * 
	 * @param text
	 */
	protected void handleDefaultTextStyles() {
		String text = getText();
		if (defautText != null && text != null && defautText.equals(text))
			addStyleName(SUGGEST_FIELD_DEFAULT_TEXT);
		else
			removeStyleName(SUGGEST_FIELD_DEFAULT_TEXT);
	}

	/**
	 * Returns the current text or an empty text if default value
	 * 
	 * <BR/>
	 * <b>Note:</b> {@link #getText()} cannot be overriden since many other
	 * parent functions use it (like {@link #setSelectionRange(int, int)}).
	 * 
	 * 
	 * @return an empty string if the current text is the default value or the
	 *         contained text
	 */
	public String getTextValue() {
		String text = super.getText();
		if (text.trim().equals(defautText))
			return "";
		return text;
	}

	public void setDefautText(String text) {
		defautText = text;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		String text = getText();
		if (defautText != null && (text == null || text.trim().length() == 0)) {
			setText(defautText);
		}
	}

	public String getDefautText() {
		return defautText;
	}

}
