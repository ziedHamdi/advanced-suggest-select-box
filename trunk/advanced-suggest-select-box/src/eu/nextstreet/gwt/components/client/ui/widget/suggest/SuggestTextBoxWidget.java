package eu.nextstreet.gwt.components.client.ui.widget.suggest;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.GestureChangeHandler;
import com.google.gwt.event.dom.client.GestureEndHandler;
import com.google.gwt.event.dom.client.GestureStartHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;

import eu.nextstreet.gwt.components.client.ui.widget.AdvancedTextBox;
import eu.nextstreet.gwt.components.shared.Validator;

/**
 * This class is the ui representer of the "inactive" suggest box: the text box
 * handles all events and is surrounded by panels which you can fill with you
 * own representation of the selected item
 * 
 * @param <T>
 *          the type of items
 * 
 * @author Zied Hamdi
 * 
 * 
 */
public class SuggestTextBoxWidget<T, W extends ValueHolderLabel<T>> extends
		Composite implements MouseDownHandler, MouseMoveHandler, MouseOutHandler {
	/** the text field style name */
	private static final String SUGGEST_FIELD = "eu-nextstreet-SuggestField";

	/** the main panel */
	protected DockPanel panel = new DockPanel();
	/** the main component */
	protected AdvancedTextBox advancedTextBox = new AdvancedTextBox();

	/** the suggest box that contains this widget */
	protected AbstractSuggestBox<T, W> representer;

	/**
	 * the button is a background image so that the focus is not lost when it is
	 * clicked, this value corresponds to the image width
	 */
	protected int buttonWidth = 16;

	/** any value change is notified to this list of listeners */
	protected ChangeEventHandlerHolder<T, ChangeEvent> valueChangeEventHandlerHolder = new ChangeEventHandlerHolder<T, ChangeEvent>() {

		@Override
		protected ChangeEvent changedValue(T param) {
			return new SuggestChangeEvent<T, W>(representer, param);
		}

	};

	/** any value change is notified to this list of listeners */
	protected ChangeEventHandlerHolder<String, ChangeEvent> textChangeEventHandlerHolder = new ChangeEventHandlerHolder<String, ChangeEvent>() {

		@Override
		protected ChangeEvent changedValue(String param) {
			return new SuggestChangeEvent<T, W>(representer, param);
		}

	};

	/** left of the text box */
	protected SimplePanel left = new SimplePanel();
	/** right of the text box */
	protected SimplePanel right = new SimplePanel();
	/** top of the text box */
	protected SimplePanel top = new SimplePanel();
	/** bottom of the text box */
	protected SimplePanel bottom = new SimplePanel();

	public SuggestTextBoxWidget() {
		initWidget(panel);
		initPanels();
		addMouseDownHandler(this);
		addMouseMoveHandler(this);
		addMouseOutHandler(this);
	}

	/**
	 * Sets the current value and notifies listeners
	 * 
	 * @param value
	 *          the new value
	 */
	protected void setValue(T value) {
		advancedTextBox.setText(representer.toString(value));
		valueChangeEventHandlerHolder.fireChangeOccured(value);
	}

	/**
	 * Sets the current value as a string and notifies listeners
	 * 
	 * @param value
	 *          the new value
	 */
	protected void setText(String value) {
		advancedTextBox.setText(value);
		textChangeEventHandlerHolder.fireChangeOccured(value);
	}

	private void initPanels() {
		panel.add(advancedTextBox, DockPanel.CENTER);
		panel.add(top, DockPanel.NORTH);
		top.setHeight("0px");
		panel.add(bottom, DockPanel.SOUTH);
		bottom.setHeight("0px");
		advancedTextBox.setStyleName(SUGGEST_FIELD);
		panel.add(left, DockPanel.WEST);
		panel.add(right, DockPanel.EAST);
	}

	/**
	 * Sets the widget on the left part of the text box
	 * 
	 * @param left
	 *          a widget
	 */
	public void setLeftWidget(IsWidget left) {
		this.left.setWidget(left);
	}

	/**
	 * Sets the widget on the right part of the text box
	 * 
	 * @param right
	 *          a widget
	 */
	public void setRightWidget(IsWidget right) {
		this.right.setWidget(right);
	}

	/**
	 * Sets the widget on top of the text box
	 * 
	 * @param top
	 *          a widget
	 */
	public void setTopWidget(IsWidget top) {
		this.top.setWidget(top);
	}

	/**
	 * Sets the widget under the text box
	 * 
	 * @param bottom
	 *          a widget
	 */
	public void setBottomWidget(IsWidget bottom) {
		this.bottom.setWidget(bottom);
	}

	public AbstractSuggestBox<T, W> getRepresenter() {
		return representer;
	}

	public void setRepresenter(AbstractSuggestBox<T, W> abstractSuggestBox) {
		this.representer = abstractSuggestBox;
	}

	/** Sets the default text */
	public void setDefaultText(String defaultText) {
		advancedTextBox.setDefaultText(defaultText);
	}

	/** delegates the focus to the text field */
	public void setFocus(boolean b) {
		advancedTextBox.setFocus(b);
	}

	/** gets the text value */
	public String getTextValue() {
		return advancedTextBox.getTextValue();
	}

	/** Enables or disables the edition */
	public void setEnabled(boolean enabled) {
		advancedTextBox.setEnabled(enabled);
	}

	/** returns the default text */
	public String getDefaultText() {
		return advancedTextBox.getDefaultText();
	}

	/** delegates to the text box */
	public void setSelectionRange(int i, int length) {
		advancedTextBox.setSelectionRange(i, length);
	}

	public Validator<String> getValidator() {
		return advancedTextBox.getValidator();
	}

	public void setValidator(Validator<String> validator) {
		advancedTextBox.setValidator(validator);
	}

	public String getDefaultTextStyle() {
		return advancedTextBox.getDefaultTextStyle();
	}

	public boolean isMandatory() {
		return advancedTextBox.isMandatory();
	}

	public void setMandatory(boolean mandatory) {
		advancedTextBox.setMandatory(mandatory);
	}

	public String getMandatoryTextStyle() {
		return advancedTextBox.getMandatoryTextStyle();
	}

	public void setMandatoryTextStyle(String defaultMandatoryTextStyle) {
		advancedTextBox.setMandatoryTextStyle(defaultMandatoryTextStyle);
	}

	public void setDefaultTextStyle(String defaultTextStyle) {
		advancedTextBox.setDefaultTextStyle(defaultTextStyle);
	}

	public boolean isReadOnly() {
		return advancedTextBox.isReadOnly();
	}

	public String getErrorTextStyle() {
		return advancedTextBox.getErrorTextStyle();
	}

	public void setErrorTextStyle(String errorTextStyle) {
		advancedTextBox.setErrorTextStyle(errorTextStyle);
	}

	public String getReadOnlyTextStyle() {
		return advancedTextBox.getReadOnlyTextStyle();
	}

	public void setReadOnlyTextStyle(String readOnlyTextStyle) {
		advancedTextBox.setReadOnlyTextStyle(readOnlyTextStyle);
	}

	public void setReadOnly(boolean readOnly) {
		advancedTextBox.setReadOnly(readOnly);
	}

	public String getText() {
		return advancedTextBox.getText();
	}

	// -------------------------- delegate events -----------------------
	public HandlerRegistration addAttachHandler(Handler handler) {
		return advancedTextBox.addAttachHandler(handler);
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return advancedTextBox.addChangeHandler(handler);
	}

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return advancedTextBox.addValueChangeHandler(handler);
	}

	public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
		return advancedTextBox.addDoubleClickHandler(handler);
	}

	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return advancedTextBox.addBlurHandler(handler);
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return advancedTextBox.addClickHandler(handler);
	}

	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return advancedTextBox.addFocusHandler(handler);
	}

	public HandlerRegistration addGestureChangeHandler(
			GestureChangeHandler handler) {
		return advancedTextBox.addGestureChangeHandler(handler);
	}

	public HandlerRegistration addGestureEndHandler(GestureEndHandler handler) {
		return advancedTextBox.addGestureEndHandler(handler);
	}

	public HandlerRegistration addGestureStartHandler(GestureStartHandler handler) {
		return advancedTextBox.addGestureStartHandler(handler);
	}

	public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
		return advancedTextBox.addKeyDownHandler(handler);
	}

	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return advancedTextBox.addKeyPressHandler(handler);
	}

	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return advancedTextBox.addKeyUpHandler(handler);
	}

	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return advancedTextBox.addMouseDownHandler(handler);
	}

	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return advancedTextBox.addMouseMoveHandler(handler);
	}

	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return advancedTextBox.addMouseOutHandler(handler);
	}

	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return advancedTextBox.addMouseOverHandler(handler);
	}

	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		return advancedTextBox.addMouseUpHandler(handler);
	}

	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		return advancedTextBox.addMouseWheelHandler(handler);
	}

	// --------------------- other features ----------------------

	public int getButtonWidth() {
		return buttonWidth;
	}

	public void setButtonWidth(int buttonWidth) {
		this.buttonWidth = buttonWidth;
	}

	// -------------------- self handling ---------------------
	/**
	 * On a click on the button, we recompute the possibilities list and we show
	 * them
	 */
	@UiHandler("textField")
	public void onMouseDown(MouseDownEvent event) {
		int interval = advancedTextBox.getAbsoluteLeft()
				+ advancedTextBox.getOffsetWidth() - event.getClientX();
		if (interval < buttonWidth) {
			if (representer.isShowingSuggestList()) {
				representer.hideSuggestList(false);
			} else {
				representer.recomputePopupContent(KeyCodes.KEY_DOWN);
				representer.highlightSelectedValue();
			}
		}
	}

	/**
	 * The style adding and removing is handled by {@link AbstractSuggestBox} here
	 * we just analyse our own state
	 * 
	 * @param event
	 *          mouse event
	 */
	@UiHandler("textField")
	public void onMouseMove(MouseMoveEvent event) {
		int mousePosition = event.getX();
		representer
				.mouseOnButton(mousePosition > (advancedTextBox.getOffsetWidth() - buttonWidth));
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		representer.mouseOnButton(false);
	}

}
