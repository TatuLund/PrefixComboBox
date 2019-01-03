package org.vaadin.prefixcombobox.client;

import com.google.gwt.event.dom.client.FocusEvent;
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
}
