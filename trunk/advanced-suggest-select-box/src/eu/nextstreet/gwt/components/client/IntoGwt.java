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
package eu.nextstreet.gwt.components.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.nextstreet.gwt.components.client.ui.widget.AdvancedTextBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestChangeEvent;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.DefaultSuggestBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IntoGwt implements EntryPoint {
	public static native void changeCss(String styleFile) /*-{
		$doc.getElementById("cssFile").href=styleFile;
	}-*/;

	private boolean test = true;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		if (test)
			testWidget();
	}

	private void testWidget() {
		class Value {
			String str;

			public Value(String str) {
				this.str = str;
			}

			@Override
			public String toString() {
				return str;
			}

		}

		final DefaultSuggestBox<Value> box = new DefaultSuggestBox<Value>("select or type value");
		box.add(new Value("01 - ABCD"));
		box.add(new Value("02 - CDEF"));
		box.add(new Value("03 - CFGHIJ"));
		RootPanel.get("suggestBoxContainer").add(box);

		CheckBox startsWith = new CheckBox("Starts With");
		startsWith.setValue(box.isStartsWith());
		startsWith.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				box.setStartsWith(event.getValue());
			}
		});

		CheckBox caseSensitive = new CheckBox("Case Sensitive");
		caseSensitive.setValue(box.isCaseSensitive());
		caseSensitive.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				box.setCaseSensitive(event.getValue());
			}
		});

		final CheckBox style = new CheckBox("Rounded");
		style.setValue(box.isStartsWith());
		style.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				changeCss(style.getValue() ? "IntoGwt.css" : "IntoGwtClassic.css");
			}

		});

		final CheckBox strict = new CheckBox("Strict mode");
		strict.setValue(false);
		strict.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				box.setStrictMode(event.getValue());
			}

		});

		final CheckBox mandatory = new CheckBox("Mandatory");
		mandatory.setValue(box.getTextField().isMandatory());
		mandatory.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				box.getTextField().setMandatory(event.getValue());
			}

		});

		VerticalPanel options = new VerticalPanel();
		options.add(startsWith);
		options.add(caseSensitive);
		options.add(style);
		options.add(strict);
		options.add(mandatory);

		RootPanel.get("options").add(options);

		final VerticalPanel infoContainer = new VerticalPanel();
		RootPanel.get("infoContainer").add(infoContainer);
		final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("HH:mm:ss");
		box.addHandler(new ChangeHandler() {

			@SuppressWarnings("unchecked")
			@Override
			public void onChange(ChangeEvent event) {
				if (infoContainer.getWidgetCount() > 3)
					infoContainer.remove(3);
				infoContainer.insert(new Label("At " + dateTimeFormat.format(new Date()) + " you "
					+ (((SuggestChangeEvent<String>) event).isSelected() ? "selected " : "typed ")
					+ ((AbstractSuggestBox<String>) event.getSource()).getText()), 0);
			}
		});

		AdvancedTextBox advancedTextBox = new AdvancedTextBox("Please type a value");
		advancedTextBox.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				Window.alert("Double clicked");
			}
		});
		RootPanel.get("advancedTextBox").add(advancedTextBox);
	}
}
