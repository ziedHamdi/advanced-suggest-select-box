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
import com.google.gwt.user.client.ui.RootPanel;

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

		DefaultSuggestBox<Value> box = new DefaultSuggestBox<Value>();
		box.add(new Value("01 - ABCD"));
		box.add(new Value("02 - CDEF"));
		box.add(new Value("03 - GHIJ"));
		RootPanel.get("nameFieldContainer").add(box);
		// RootPanel.get("sendButtonContainer").add(sendButton);
	}
}
