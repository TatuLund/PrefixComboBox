package org.vaadin.prefixcombobox;

import java.util.Collection;

import org.vaadin.prefixcombobox.client.PrefixComboBoxState;

import com.vaadin.data.provider.DataCommunicator;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.ComboBox;

/**
 * A filtering dropdown single-select. Items are filtered based on user input.
 * Supports the creation of new items when a handler is set by the user. PrefixComboBox
 * is subclass of {@link com.vaadin.ui.ComboBox}. It has additional feature of
 * prefix.
 * 
 * @see PrefixComboBox#setPrefix(String)
 *
 * @param <T>
 *            item (bean) type in PrefixComboBox
 */
public class PrefixComboBox<T> extends ComboBox<T> {

    /**
     * Constructs an empty prefix combo box without a caption. The content of the combo
     * box can be set with {@link #setDataProvider(DataProvider)} or
     * {@link #setItems(Collection)}
     */
    public PrefixComboBox() {
    	super();
    }

    /**
     * Constructs an empty prefix combo box, whose content can be set with
     * {@link #setDataProvider(DataProvider)} or {@link #setItems(Collection)}.
     *
     * @param caption
     *            the caption to show in the containing layout, null for no
     *            caption
     */
    public PrefixComboBox(String caption) {
        this();
        setCaption(caption);
    }

    /**
     * Constructs a prefix combo box with a static in-memory data provider with the
     * given options.
     *
     * @param caption
     *            the caption to show in the containing layout, null for no
     *            caption
     * @param options
     *            collection of options, not null
     */
    public PrefixComboBox(String caption, Collection<T> options) {
        this(caption);

        setItems(options);
    }

    /**
     * Constructs and initializes an empty prefix combo box.
     *
     * @param dataCommunicator
     *            the data comnunicator to use with this ComboBox
     */
    protected PrefixComboBox(DataCommunicator<T> dataCommunicator) {
        super(dataCommunicator);
    }

    // We must override getState() to cast the state to PrefixComboBoxState
    @Override
    protected PrefixComboBoxState getState() {
        return (PrefixComboBoxState) super.getState();
    }

    /**
     * Set the prefix used in the prefix combo box
     * 
     * @param prefix Prefix shown before option
     */
    public void setPrefix(String prefix) {
    	setPrefix(prefix,false);    	
    }
    
    /**
     * Set the prefix used in the prefix combo box
     * 
     * @param prefix Prefix shown before option
     * @param htmlAllowed If true prefix is allowed to contain html otherwise escaped
     */
    public void setPrefix(String prefix, boolean htmlAllowed) {
    	getState().prefix = prefix;
    	getState().prefixHtml = htmlAllowed;
    }
    
    
    /**
     * Get the current predix used in the predix combo box
     * 
     * @return The current prefix
     */
    public String getPrefix() {
    	return getState().prefix;
    }
}