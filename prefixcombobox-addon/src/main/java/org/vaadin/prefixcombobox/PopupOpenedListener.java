package org.vaadin.prefixcombobox;

import java.lang.reflect.Method;

import com.vaadin.event.ConnectorEventListener;
import com.vaadin.util.ReflectTools;

/**
 * Listener for {@link PopupOpenedEvent}
 * 
 * @see PrefixComboBox#addPopupOpenedListener(PopupOpenedListener)
 * 
 * @param <T> Type of the bean in PrefixComboBox

 * @author Tatu Lund
 */
public interface PopupOpenedListener<T> extends ConnectorEventListener {
	Method POPUP_OPENED_METHOD = ReflectTools.findMethod(
			PopupOpenedListener.class, "popupOpened", PopupOpenedEvent.class);
	public void popupOpened(PopupOpenedEvent<T> event);
}
