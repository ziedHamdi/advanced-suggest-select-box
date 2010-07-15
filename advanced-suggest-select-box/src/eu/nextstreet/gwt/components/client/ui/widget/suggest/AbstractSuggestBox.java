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
package eu.nextstreet.gwt.components.client.ui.widget.suggest;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.common.event.UIHandler;
import eu.nextstreet.gwt.components.client.ui.widget.AdvancedTextBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.DefaultSuggestList;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultValueRendererFactory;
import eu.nextstreet.gwt.components.shared.Validator;

/**
 * Suggest box (or select box) with many possibilities either in behavior and in presentation.
 * 
 * @author Zied Hamdi
 * 
 *         bugs: when a selection is directly replaced by characters, the enter button doesn't fire the event (it's
 *         postponed to the blur event).
 * 
 * @param <T>
 */
public abstract class AbstractSuggestBox<T, W extends ValueHolderLabel<T>> extends
		ChangeEventHandlerHolder<Boolean, SuggestChangeEvent<T, W>> {

	private static final String SUGGEST_FIELD_COMP = "eu-nextstreet-SuggestFieldComp";
	private static final String SUGGEST_FIELD = "eu-nextstreet-SuggestField";
	private static final String SUGGEST_FIELD_HOVER = "eu-nextstreet-SuggestFieldHover";
	private static SuggestBoxUiBinder uiBinder = GWT.create(SuggestBoxUiBinder.class);
	protected T selected;
	protected String typed;
	protected int buttonWidth = 16;
	protected boolean caseSensitive;
	protected SuggestWidget<T> suggestWidget = new DefaultSuggestList<T>();
	protected ScrollPanel scrollPanel = new ScrollPanel();
	protected ListRenderer<T, W> listRenderer;
	protected boolean strictMode;

	protected int selectedIndex = -1;
	private boolean recomputePopupContent = true;
	/**
	 * Specifies if enter is hit multiple times with same value, whether it generates a change event for each
	 */
	private boolean multipleChangeEvent;
	private boolean fireChangeOnBlur;

	protected ValueRendererFactory<T, W> valueRendererFactory;

	@SuppressWarnings("unchecked")
	interface SuggestBoxUiBinder extends UiBinder<Widget, AbstractSuggestBox> {
	}

	protected @UiField
	AdvancedTextBox textField;

	public AbstractSuggestBox() {
		this(null);
	}

	public AbstractSuggestBox(String defaultText) {
		initWidget(uiBinder.createAndBindUi(this));
		setStyleName(SUGGEST_FIELD_COMP);
		textField.setRepresenter(this);
		textField.setStyleName(SUGGEST_FIELD);
		textField.setDefaultText(defaultText);
		suggestWidget.setWidget(scrollPanel);
		setValueRendererFactory(new DefaultValueRendererFactory<T, W>());
	}

	// unused
	// @UiHandler("text")
	// public void onMouseOver(MouseOverEvent event) {
	// }
	//	
	// @UiHandler("text")
	// public void onMouseOut(MouseOutEvent event) {
	// }

	@UiHandler("textField")
	public void onMouseMove(MouseMoveEvent event) {
		int mousePosition = event.getX();
		mouseOnButton(mousePosition > (textField.getOffsetWidth() - buttonWidth));
	}

	@UiHandler("textField")
	public void onDoubleClick(DoubleClickEvent event) {
		doubleClicked(event);
	}

	@UiHandler("textField")
	public void onMouseDown(MouseDownEvent event) {
		int interval = textField.getAbsoluteLeft() + textField.getOffsetWidth() - event.getClientX();
		if (interval < buttonWidth) {
			if (suggestWidget.isShowing()) {
				suggestWidget.hide();
			} else {
				recomputePopupContent(KeyCodes.KEY_DOWN);
				highlightSelectedValue();
			}
		}
	}

	@UiHandler("textField")
	public void onBlur(BlurEvent event) {
		new Timer() {
			@Override
			public void run() {
				String currentText = getText();
				if (typed == null || !typed.equals(currentText)) {
					if (strictMode) {
						setText("");
						valueTyped("");
					} else {
						valueTyped(currentText);
					}
				} else if (fireChangeOnBlur) {
					valueSelected(selected);
					fireChangeOnBlur = false;
				}
				if (currentText.trim().length() == 0)
					setText("");
			}
		}.schedule(200);
		if (suggestWidget.isShowing()) {
			new Timer() {
				@Override
				public void run() {
					suggestWidget.hide();
				}
			}.schedule(300);
		}
	}

	@UiHandler("textField")
	public void onKeyUp(KeyUpEvent keyUpEvent) {
		int keyCode = keyUpEvent.getNativeKeyCode();

		if (keyCode == KeyCodes.KEY_TAB || keyCode == KeyCodes.KEY_ALT || keyCode == KeyCodes.KEY_CTRL
			|| keyCode == KeyCodes.KEY_SHIFT || keyCode == KeyCodes.KEY_HOME || keyCode == KeyCodes.KEY_END)
			return;

		if (keyCode == KeyCodes.KEY_DOWN || keyCode == KeyCodes.KEY_UP || keyCode == KeyCodes.KEY_LEFT
			|| keyCode == KeyCodes.KEY_RIGHT) {
			recomputePopupContent = !suggestWidget.isShowing();
		}
		if (keyCode == KeyCodes.KEY_DELETE || keyCode == KeyCodes.KEY_BACKSPACE)
			recomputePopupContent = true;

		if (keyCode == KeyCodes.KEY_DELETE || keyCode == KeyCodes.KEY_BACKSPACE)
			selectedIndex = -1;

		if (recomputePopupContent) {
			if (recomputePopupContent(keyCode).size() == 1)
				return;

		}

		ValueHolderLabel<T> popupWidget = getSelectedItem();
		if (popupWidget != null && selectedIndex != -1)
			popupWidget.setSelected(false);
		int widgetCount = listRenderer.getWidgetCount();

		if (widgetCount == 0)
			return;

		if (keyCode == KeyCodes.KEY_DOWN) {
			selectedIndex = ++selectedIndex % widgetCount;
			highlightSelectedValue();
		} else if (keyCode == KeyCodes.KEY_UP) {
			selectedIndex = --selectedIndex % widgetCount;
			if (selectedIndex < 0)
				selectedIndex += widgetCount;
			highlightSelectedValue();
		} else if (keyCode == KeyCodes.KEY_ENTER) {
			if (suggestWidget.isShowing()) {
				if (popupWidget != null) {
					T t = popupWidget.getValue();
					fillValue(t, true);
				} else {
					// value not in list, this enter means OK.
					valueTyped(getText());
				}
				hideSuggestList();
			} else {
				if (multipleChangeEvent) {
					// popup is not visible, this enter means OK (check if the
					// value was entered from the list or from text).
					if (selected == null)
						valueTyped(getText());
					else
						fillValue(selected, multipleChangeEvent);
				}
			}
		} else if (keyCode == KeyCodes.KEY_ESCAPE) {
			hideSuggestList();
		} else if (keyCode == KeyCodes.KEY_TAB) {

		} else {
			recomputePopupContent = true;
			if (strictMode) {
				StringBuffer reducingText = new StringBuffer(getText());
				while (reducingText.length() > 1) {
					List<T> inserted = recomputePopupContent(keyCode);
					if (inserted.size() > 0) {
						break;
					} else {
						reducingText.setLength(reducingText.length() - 1);
						setText(reducingText.toString());
					}
				}
			}
		}

	}

	/**
	 * 
	 */
	protected void highlightSelectedValue() {
		ValueHolderLabel<T> popupWidget = getSelectedItem();
		if (popupWidget != null) {
			popupWidget.setSelected(true);
			UIObject uiObject = popupWidget.getUiObject();
			assert (uiObject != null);
			scrollPanel.ensureVisible(uiObject);
		}
	}

	/**
	 * Orders the suggest widget to be hidden
	 */
	protected void hideSuggestList() {
		suggestWidget.hide();
		selectedIndex = -1;
	}

	/**
	 * Recomputes the content of the popup. Returns true to show there's no need to do more processing. Special cases
	 * are when one of the keys {@link KeyCodes#KEY_DOWN}, {@link KeyCodes#KEY_UP} is pressed: all possible values are
	 * presented
	 * 
	 * @param keyCode
	 * @return true if there is no more need for computing (when only one value remains, the value is inserted into the
	 *         text box and the processing is informed that the popup is now hidden)
	 */
	protected List<T> recomputePopupContent(int keyCode) {
		if (isReadOnly())
			return null;
		List<T> possibilities;
		String textValue = getText();
		// to show all possible values if a value is already selected and a up
		// or down key is pressed
		if (keyCode == KeyCodes.KEY_DOWN || keyCode == KeyCodes.KEY_UP)
			textValue = "";

		possibilities = getFiltredPossibilities(textValue);
		if (possibilities.size() > 0) {
			listRenderer.clear();
			if (possibilities.size() == 1) {
				// laisse l'utilisateur effacer les valeurs
				if (keyCode != KeyCodes.KEY_BACKSPACE && keyCode != KeyCodes.KEY_LEFT && keyCode != KeyCodes.KEY_RIGHT) {
					fillValue(possibilities.get(0), false);
				}
			}

			constructPopupContent(possibilities);

			showSuggestList();
		} else {
			hideSuggestList();
		}
		return possibilities;
	}

	protected void showSuggestList() {
		suggestWidget.adjustPosition(textField.getAbsoluteLeft(), textField.getAbsoluteTop()
			+ textField.getOffsetHeight());
		highlightSelectedValue();
		suggestWidget.show();
	}

	protected void constructPopupContent(List<T> possibilities) {
		int size = possibilities.size();
		for (int i = 0; i < size; i++) {
			final T t = possibilities.get(i);
			String currentText = getText();
			if (checkSelected(t, currentText))
				selectedIndex = i;

			final W currentLabel = createValueRenderer(t, currentText);
			listRenderer.add(currentLabel);
			currentLabel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent clickEvent) {
					itemClicked(t);
				}
			});

			class MouseHoverhandler implements MouseOverHandler, MouseOutHandler {

				@Override
				public void onMouseOver(MouseOverEvent event) {
					currentLabel.hover(true);
				}

				@Override
				public void onMouseOut(MouseOutEvent event) {
					currentLabel.hover(false);
				}
			}

			MouseHoverhandler hoverhandler = new MouseHoverhandler();
			currentLabel.addMouseOverHandler(hoverhandler);
			currentLabel.addMouseOutHandler(hoverhandler);
		}
	}

	/**
	 * Override to use another strategy to determine if an item is selected
	 * 
	 * @param item
	 *            item to test
	 * @param currentText
	 *            the current field text
	 * @return true ifaoif <code>currentText</code> matches the item <code>t</code>
	 */
	protected boolean checkSelected(final T item, String currentText) {
		String value = toString(item);
		boolean found = value.equals(currentText);
		return found;
	}

	private W createValueRenderer(final T t, String value) {
		final W currentLabel = valueRendererFactory.createValueRenderer(t, value, caseSensitive);
		return currentLabel;
	}

	/**
	 * Returns the string representation of a value, this method is very important since it determines the equality of
	 * elements typed by hand with the ones in the list.
	 * 
	 * @param t
	 *            the value
	 * @return the string representation of a value
	 */
	protected String toString(final T t) {
		return t.toString();
	}

	private ValueHolderLabel<T> getSelectedItem() {
		if (selectedIndex != -1 && listRenderer.getWidgetCount() > selectedIndex)
			return (ValueHolderLabel<T>) listRenderer.getRow(selectedIndex);
		return null;
	}

	/**
	 * Fills a "type safe" value (one of the available values in the list). override to check existing values change
	 * 
	 * @param t
	 *            the selected value
	 * @param commit
	 *            consider a value changed only if commit is true (otherwise you can have duplicate events)
	 * @return true if the suggest box has to be hidden. Subclasses can override this method and return false to force
	 *         the suggest widget to remain open even if there's only one element remaining in the list of choices
	 */
	protected boolean fillValue(final T t, boolean commit) {
		textField.setText(toString(t));
		hideSuggestList();
		textField.setFocus(true);
		selected = t;
		typed = toString(t);
		if (commit) {
			valueSelected(t);
		} else {
			// the change is not notified immediately since we just suggest the
			// value t. We keep a flag to know there was no notification
			fireChangeOnBlur = true;
		}
		return true;
	}

	/**
	 * Called when a value is selected from the list, if the value is typed on the keyboard and only one possible
	 * element corresponds, this method will be called immediately only if <code>multipleChangeEvent</code> is true.
	 * Otherwise it will wait until a blur event occurs Notice that if <code>multipleChangeEvent</code> is true, this
	 * method will be called also each time the enter key is typed
	 * 
	 * @param value
	 */
	public void valueSelected(T value) {
		fireChangeOccured(true);
	}

	/**
	 * Called when a typed value is confirmed whether by pressing the key enter, or on blur (losing the focus) of the
	 * element. Notice that this method behavior also can be changed thanks to the property
	 * <code>multipleChangeEvent</code> which specifies if the method has to be called on each enter key press or only
	 * on the first one.
	 * 
	 * @param value
	 */
	public void valueTyped(String value) {
		selected = null;
		// if (defautText != null && defautText.equals(value))
		// value = "";
		typed = value;
		fireChangeOccured(false);
	}

	/**
	 * returns the value selected from list, if a text was typed then returns null
	 * 
	 * @return the value selected from list, if a text was typed then returns null
	 */
	public T getSelected() {
		return selected;
	}

	/**
	 * 
	 * @return the typed value, this can also be the selected one from list: to check if the value belongs to the list
	 *         check if {@link #getSelected()} returns null
	 */
	public String getTyped() {
		return typed;
	}

	@Override
	protected SuggestChangeEvent<T, W> changedValue(Boolean selected) {
		if (selected)
			return new SuggestChangeEvent<T, W>(this, getSelected());
		return new SuggestChangeEvent<T, W>(this, getText());
	}

	/**
	 * Returns the text currently in the text field, this method can have different results before and after the call of
	 * {@link #recomputePopupContent(int)} which auto completes the text automatically if only one result remains.
	 * 
	 * @return the text fiel
	 */
	public String getText() {
		return textField.getTextValue();
	}

	public void setText(String str) {
		textField.setText(str);
		typed = str;
	}

	/**
	 * Not used in the the library: utility method to get the typed item corresponding to a given text Warning! this
	 * methods calls {@link #getFiltredPossibilities(String)} that could make a call to the server, you should avoid
	 * calling it.
	 * 
	 * @param text
	 * @return
	 */
	public T computeSelected(String text) {
		List<T> possibilities = getFiltredPossibilities(text);
		if (possibilities.size() == 1) {
			selected = possibilities.get(0);
		} else {
			selected = null;
		}
		return selected;
	}

	public boolean isEmpty() {
		return getText().length() == 0;
	}

	public void setEnabled(boolean enabled) {
		textField.setEnabled(enabled);
	}

	public void setFocus(boolean focus) {
		textField.setFocus(focus);
	}

	protected abstract List<T> getFiltredPossibilities(String text);

	/**
	 * returns the curent suggest renderer items factory
	 * 
	 * @return
	 */
	public ValueRendererFactory<T, ? extends ValueHolderLabel<T>> getValueRendererFactory() {
		return valueRendererFactory;
	}

	/**
	 * Sets the items renderer factory: you can define your own item factory to control the way items are shown in the
	 * suggest list
	 * 
	 * @param valueRendererFactory
	 */
	public void setValueRendererFactory(ValueRendererFactory<T, W> valueRendererFactory) {
		this.valueRendererFactory = valueRendererFactory;
		if (listRenderer != null) {
			listRenderer.clear();
			scrollPanel.clear();
		}
		listRenderer = valueRendererFactory.createListRenderer();
		scrollPanel.add((Widget) listRenderer);
		System.out.println();
	}

	public SuggestWidget<T> getSuggestWidget() {
		return suggestWidget;
	}

	/**
	 * Sets the suggesting list holder widget
	 * 
	 * @param suggestWidget
	 */
	public void setSuggestWidget(SuggestWidget<T> suggestWidget) {
		this.suggestWidget = suggestWidget;
	}

	public int getButtonWidth() {
		return buttonWidth;
	}

	public void setButtonWidth(int buttonWidth) {
		this.buttonWidth = buttonWidth;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	/**
	 * Controls whether the method {@link #valueSelected(Object)} expressing a change event will be called each time a
	 * value is selected or if it has to wait until a blur occurs.
	 * 
	 * @return
	 */
	public boolean isMultipleChangeEvent() {
		return multipleChangeEvent;
	}

	public void setMultipleChangeEvent(boolean multipleChangeEvent) {
		this.multipleChangeEvent = multipleChangeEvent;
	}

	public boolean isStrictMode() {
		return strictMode;
	}

	public void setStrictMode(boolean strictMode) {
		this.strictMode = strictMode;
	}

	public String getDefaultText() {
		return textField.getDefaultText();
	}

	public void setDefaultText(String text) {
		this.textField.setDefaultText(text);
	}

	public AdvancedTextBox getTextField() {
		return textField;
	}

	public void setTextField(AdvancedTextBox textField) {
		this.textField = textField;
	}

	/**
	 * action when the mouse hovers the arrow button
	 * 
	 * @param onButton
	 */
	protected void mouseOnButton(boolean onButton) {
		if (onButton)
			textField.addStyleName(SUGGEST_FIELD_HOVER);
		else
			textField.removeStyleName(SUGGEST_FIELD_HOVER);
	}

	/**
	 * action when the suggest box is double clicked
	 * 
	 * @param event
	 */
	protected void doubleClicked(DoubleClickEvent event) {
		this.textField.setSelectionRange(0, getText().length());
		recomputePopupContent(KeyCodes.KEY_RIGHT);
	}

	/**
	 * action on an item click
	 * 
	 * @param t
	 */
	protected void itemClicked(T t) {
		fillValue(t, true);
	}

	public Validator<String> getValidator() {
		return textField.getValidator();
	}

	public void setValidator(Validator<String> validator) {
		textField.setValidator(validator);
	}

	public String getErrorTextStyle() {
		return textField.getErrorTextStyle();
	}

	public String getMandatoryTextStyle() {
		return textField.getMandatoryTextStyle();
	}

	public boolean isMandatory() {
		return textField.isMandatory();
	}

	public void setDefaultTextStyle(String defaultTextStyle) {
		textField.setDefaultTextStyle(defaultTextStyle);
	}

	public void setErrorTextStyle(String errorTextStyle) {
		textField.setErrorTextStyle(errorTextStyle);
	}

	public void setMandatory(boolean mandatory) {
		textField.setMandatory(mandatory);
	}

	public void setMandatoryTextStyle(String defaultMandatoryTextStyle) {
		textField.setMandatoryTextStyle(defaultMandatoryTextStyle);
	}

	public String getDefaultTextStyle() {
		return textField.getDefaultTextStyle();
	}

	public String getReadOnlyTextStyle() {
		return textField.getReadOnlyTextStyle();
	}

	public boolean isReadOnly() {
		return textField.isReadOnly();
	}

	public void setReadOnly(boolean readOnly) {
		textField.setReadOnly(readOnly);
	}

	public void setReadOnlyTextStyle(String readOnlyTextStyle) {
		textField.setReadOnlyTextStyle(readOnlyTextStyle);
	}

	public UIHandler getUiHandler() {
		return textField.getUiHandler();
	}

	public void setUiHandler(UIHandler uiHandler) {
		textField.setUiHandler(uiHandler);
	}

	public void setSelected(T selected) {
		this.selected = selected;
		setText(toString(selected));
	}
}
