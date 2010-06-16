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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.DefaultSuggestBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IntoGwt implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
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

		final DefaultSuggestBox<Value> box = new DefaultSuggestBox<Value>();
		box.add(new Value("01 - ABCD"));
		box.add(new Value("02 - CDEF"));
		box.add(new Value("03 - CFGHIJ"));
		RootPanel.get("suggestBoxContainer").add(box);

		CheckBox startOnly = new CheckBox("Start Only");
		startOnly.setValue(box.isStartsWith());
		startOnly.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

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

		VerticalPanel options = new VerticalPanel();
		options.add(startOnly);
		options.add(caseSensitive);

		RootPanel.get("options").add(options);

	}
}
