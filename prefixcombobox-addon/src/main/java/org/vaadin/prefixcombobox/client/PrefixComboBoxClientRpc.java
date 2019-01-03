package org.vaadin.prefixcombobox.client;

import com.vaadin.shared.communication.ClientRpc;

public interface PrefixComboBoxClientRpc extends ClientRpc {
	
	public void showPopup(int currentPage);

	public void selectText();
	
}
