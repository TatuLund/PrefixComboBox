package org.vaadin.prefixcombobox.client;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.vaadin.client.ui.VComboBox;

public class VPrefixComboBox extends VComboBox {

	@Override
    public void onFocus(FocusEvent event) {
		super.onFocus(event);
		
		PrefixComboBoxState state = (PrefixComboBoxState) connector.getState();
		if (state.selectAllOnFocus) {
			tb.setSelectionRange(0, tb.getValue().length());
		}
	}
	
	@Override
    public void onKeyUp(KeyUpEvent event) {

		if (event.isControlKeyDown() && (event.getNativeKeyCode() == KeyCodes.KEY_A)) {
			tb.setCursorPos(0);
			tb.setSelectionRange(0, tb.getValue().length());			
		} else if (event.isControlKeyDown() && (event.getNativeKeyCode() == KeyCodes.KEY_C)) {
			// NOP
		} else {
			super.onKeyUp(event);			
		}
	}

}
