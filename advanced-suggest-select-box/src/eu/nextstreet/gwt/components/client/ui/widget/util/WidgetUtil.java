package eu.nextstreet.gwt.components.client.ui.widget.util;

import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.UIObject;

public class WidgetUtil {

	@SuppressWarnings("rawtypes")
	public static boolean inBounds(MouseEvent clickEvent, UIObject widget) {
		int sourceXstart = clickEvent.getClientX();
		int sourceYstart = clickEvent.getClientY() + Window.getScrollTop();
		return inBounds(widget, sourceXstart, sourceYstart);
	}

	public static boolean inBounds(UIObject widget, int sourceXstart, int sourceYstart) {
		return inBounds(0, widget, sourceXstart, sourceYstart);
	}

	public static boolean inBounds(int precision, UIObject widget, int sourceXstart, int sourceYstart) {
		int absoluteLeft = widget.getAbsoluteLeft();
		int absoluteTop = widget.getAbsoluteTop();
		boolean xOk = absoluteLeft < (sourceXstart - precision) && (sourceXstart + precision) < absoluteLeft + widget.getOffsetWidth();
		boolean yOk = absoluteTop < (sourceYstart - precision) && (sourceYstart + precision) < absoluteTop + widget.getOffsetHeight();
		return xOk && yOk;
	}

}
