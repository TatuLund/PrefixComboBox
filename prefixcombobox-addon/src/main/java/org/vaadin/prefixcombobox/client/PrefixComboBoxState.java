package org.vaadin.prefixcombobox.client;

import com.vaadin.shared.ui.combobox.ComboBoxState;

public class PrefixComboBoxState extends ComboBoxState {

    // State can have both public variable and bean properties
    public String prefix = "";
    public boolean prefixHtml = false;
    public int maxLength = -1;
	public boolean selectAllOnFocus = false;

}