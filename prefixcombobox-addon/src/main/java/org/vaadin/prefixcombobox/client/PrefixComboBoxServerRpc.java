package org.vaadin.prefixcombobox.client;

import com.vaadin.shared.communication.ServerRpc;

public interface PrefixComboBoxServerRpc extends ServerRpc {
	
	public void popupOpened();

	public void popupClosed();

}
