package org.vaadin.prefixcombobox.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.vaadin.client.ui.VComboBox;

public class VPrefixComboBox extends VComboBox {

	private boolean scheduled = false;
	private boolean keypressed = false;
	private int updateInterval = -1;
	
	@Override
    public void onFocus(FocusEvent event) {
		super.onFocus(event);
		
		PrefixComboBoxState state = (PrefixComboBoxState) connector.getState();
		if (state.selectAllOnFocus) {
			tb.setSelectionRange(0, tb.getValue().length());
		}
	}

	@Override
    public void filterOptions(int page) {
		if (updateInterval <= 0) {
	    	doFilterOptions(page);			
		} else {
			keypressed = true;
			if (!scheduled) {
				scheduled = true;
				Scheduler.get().scheduleFixedDelay(() -> {
					if (!keypressed) {
						doFilterOptions(page);
						scheduled = false;
						return false;
					} else {
						keypressed = false;
						return true;
					}
				}, updateInterval);				
			}
		}
    }

    private void doFilterOptions(int page) {
    	super.filterOptions(page);
    }
    	
	@Override
    public void onKeyUp(KeyUpEvent event) {
		if (enabled && !readonly) {
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

	public void setUpdateInterval(int updateInterval) {
		this.updateInterval = updateInterval;
	}

}
