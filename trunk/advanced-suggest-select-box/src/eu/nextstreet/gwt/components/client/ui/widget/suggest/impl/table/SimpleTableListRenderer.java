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
package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer;

/**
 * Designed to create a simlpe table around the items creating
 * <tr>
 * tags around each value. You can only add items in the form of
 * <td>col1</td>
 * <td>col2</td>... etc. You can also add a first set with
 * <th>tags
 * 
 * @author Zied Hamdi founder of http://into-i.fr
 * 
 * @param <T>
 */
public class SimpleTableListRenderer<T> extends HTML implements ListRenderer<T> {
	protected List<Widget> rows = new ArrayList<Widget>();
	protected String tableStyle = "eu-nextstreet-SuggestFieldPopupSimpleTable";

	@Override
	public void add(Widget item) {
		rows.add(item);
		setHTML(getHTML());
	}

	@Override
	public void clear() {
		rows.clear();
	}

	@Override
	public Widget getWidget(int index) {
		return rows.get(index);
	}

	@Override
	public int getWidgetCount() {
		return rows.size();
	}

	@Override
	public String getHTML() {
		StringBuffer buffer = new StringBuffer("<table class='" + getTableStyle() + "'>");
		for (Widget widget : rows) {
			buffer.append("<tr>");
			buffer.append(widget.toString());
			buffer.append("</tr>");
		}
		buffer.append("</table>");
		return buffer.toString();
	}

	@Override
	public String toString() {
		return getHTML();
	}

	public String getTableStyle() {
		return tableStyle;
	}

	public void setTableStyle(String tableStyle) {
		this.tableStyle = tableStyle;
	}

}
