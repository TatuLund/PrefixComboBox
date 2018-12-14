package org.vaadin.prefixcombobox;

import com.vaadin.ui.CustomComponent;

/**
 * The PopupOpenedEvent is triggered when ComboBox suggestion popup is opened
 * 
 * @author Tatu Lund
 */
@SuppressWarnings("serial")
public class PopupOpenedEvent<T> extends CustomComponent.Event {

	/**
	 * The PopupOpenedEvent is triggered when ComboBox suggestion popup is opened
	 * 
	 * @param source The component where event happened
	 */
	public PopupOpenedEvent(PrefixComboBox<T> source) {
		super(source);
	}

}
