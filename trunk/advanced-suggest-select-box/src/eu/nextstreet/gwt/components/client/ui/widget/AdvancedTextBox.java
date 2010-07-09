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

import eu.nextstreet.gwt.components.shared.ValidationException;
import eu.nextstreet.gwt.components.shared.ValidatorList;

/**
 * A text box that handles additional events like the double click and supports the default text feature
 * 
 * NOTE: You should use {@link #getTextValue()} instead of {@link #getText()} to avoid getting the default text instead
 * of an empty one.
 * 
 * <BR/>
 * This class has the following styles:
 * <ul>
 * <li><b>eu-nextstreet-AdvancedTextBoxDefaultText</b> defines the style of the default text</li>
 * </ul>
 * 
 * @author Zied Hamdi founder of http://into-i.fr
 * 
 */
public class AdvancedTextBox extends TextBox implements HasDoubleClickHandlers {
	private static final String DEFAULT_TEXT_STYLE = "eu-nextstreet-AdvancedTextBoxDefaultText";
	private static final String MANDATORY_DEFAULT_TEXT_STYLE = "eu-nextstreet-AdvancedTextBoxMandatoryText";
	private static final String ERROR_TEXT_STYLE = "eu-nextstreet-AdvancedTextBoxErrorText";
	protected ValidatorList<String> validator = null;
	protected String defautText;
	protected String defaultTextStyle;
	protected String errorTextStyle;
	protected String mandatoryTextStyle;
	protected boolean mandatory;

	public AdvancedTextBox() {
		this(null);
	}

	public AdvancedTextBox(final String defautText) {
		this.defautText = defautText;
		addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				String text = AdvancedTextBox.this.getText();
				if (AdvancedTextBox.this.defautText == null || !AdvancedTextBox.this.defautText.equals(text)) {
					setSelectionRange(0, text.length());
				} else {
					AdvancedTextBox.super.setText("");
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
				handleTextStyles();
			}
		});
	}

	@Override
	public HandlerRegistration addDoubleClickHandler(final DoubleClickHandler handler) {
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
		boolean empty = isEmptyTextField();
		if (empty) {
			super.setText(defautText);
		}
		handleTextStyles();
	}

	/**
	 * true if and only if the value is empty after trimming (if the value is the default text, returns false)
	 * 
	 * @return true if and only if the value is empty after trimming (if the value is the default text, returns false)
	 * @see #isEmpty() to integrate the test of having the default text in.
	 */
	protected boolean isEmptyTextField() {
		String text = getText();
		return text == null || text.trim().length() == 0;
	}

	/**
	 * returns true if the current text is empty or equal to the default text
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return isEmptyTextField() || (defautText == null ? true : defautText.equals(getText()));
	}

	/**
	 * Adds or removes the default text style depending on the value
	 * 
	 * @param empty
	 * 
	 * @param text
	 */
	protected void handleTextStyles() {
		String text = getText();
		ValidationException error = null;
		if (validator != null) {
			try {
				validator.validate(getText());
			} catch (ValidationException ex) {
				error = ex;
			}
		}
		if (error == null) {
			if (isEmptyTextField() || getTextValue().trim().length() == 0) {
				addStyleName(getTextStyle());
			} else {
				removeStyleName(getTextStyle());
			}
		} else {
			addStyleName(getErrorTextStyle());
		}
	}

	protected String getTextStyle() {
		return mandatory ? getMandatoryTextStyle() : getDefaultTextStyle();
	}

	protected String getDefaultTextStyle() {
		if (defaultTextStyle == null)
			return DEFAULT_TEXT_STYLE;
		return defaultTextStyle;
	}

	/**
	 * Returns the current text or an empty text if default value
	 * 
	 * <BR/>
	 * <b>Note:</b> {@link #getText()} cannot be overriden since many other parent functions use it (like
	 * {@link #setSelectionRange(int, int)}).
	 * 
	 * 
	 * @return an empty string if the current text is the default value or the contained text
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
		if (defautText != null && isEmptyTextField()) {
			setText(defautText);
		}
	}

	public String getDefautText() {
		return defautText;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		removeStyleName(getTextStyle());
		this.mandatory = mandatory;
		handleTextStyles();
	}

	public String getMandatoryTextStyle() {
		if (mandatoryTextStyle == null)
			return MANDATORY_DEFAULT_TEXT_STYLE;
		return mandatoryTextStyle;
	}

	public void setMandatoryTextStyle(String defaultMandatoryTextStyle) {
		this.mandatoryTextStyle = defaultMandatoryTextStyle;
	}

	public void setDefaultTextStyle(String defaultTextStyle) {
		removeStyleName(getTextStyle());
		this.defaultTextStyle = defaultTextStyle;
		handleTextStyles();
	}

	public String getErrorTextStyle() {
		if (errorTextStyle == null)
			return ERROR_TEXT_STYLE;
		return errorTextStyle;
	}

	public void setErrorTextStyle(String errorTextStyle) {
		removeStyleName(getErrorTextStyle());
		this.errorTextStyle = errorTextStyle;
		handleTextStyles();
	}

}
