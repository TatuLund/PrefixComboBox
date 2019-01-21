package org.vaadin.prefixcombobox;

import com.vaadin.ui.CustomComponent;

/**
 * The PopupClosedEvent is triggered when ComboBox suggestion popup is closed
 * 
 * @author Tatu Lund
 */
@SuppressWarnings("serial")
public class PopupClosedEvent<T> extends CustomComponent.Event {

	/**
	 * The PopupClosedEvent is triggered when ComboBox suggestion popup is closed
	 * 
	 * @param source The component where event happened
	 */
	public PopupClosedEvent(PrefixComboBox<T> source) {
		super(source);
	}

}
