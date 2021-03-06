/*
 * Copyright 2012 Zied Hamdi.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple;

import eu.nextstreet.gwt.components.client.ui.widget.common.StringFormulator;

/**
 * @author Zied Hamdi founder of http://1vu.fr
 * 
 */
public class DefaultStringFormulator<T> implements StringFormulator<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.StringFormulator#
	 * toString(java.lang.Object)
	 */
	@Override
	public String toString(T t) {
		return t.toString();
	}

}
