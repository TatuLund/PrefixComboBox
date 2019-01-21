package org.vaadin.prefixcombobox;

import java.lang.reflect.Method;

import com.vaadin.event.ConnectorEventListener;
import com.vaadin.util.ReflectTools;

/**
 * Listener for {@link PopupClosedEvent}
 * 
 * @see PrefixComboBox#addPopupClosedListener(PopupClosedListener)
 * 
 * @param <T> Type of the bean in PrefixComboBox

 * @author Tatu Lund
 */
public interface PopupClosedListener<T> extends ConnectorEventListener {
	Method POPUP_CLOSED_METHOD = ReflectTools.findMethod(
			PopupClosedListener.class, "popupClosed", PopupClosedEvent.class);
	public void popupClosed(PopupClosedEvent<T> event);
}
