package org.vaadin.prefixcombobox;

import java.util.Collection;

import org.vaadin.prefixcombobox.client.PrefixComboBoxClientRpc;
import org.vaadin.prefixcombobox.client.PrefixComboBoxServerRpc;
import org.vaadin.prefixcombobox.client.PrefixComboBoxState;

import com.vaadin.data.provider.DataCommunicator;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.shared.Registration;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ItemCaptionGenerator;

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
    	
    	setupServerRpc();
    }

	private void setupServerRpc() {
		PrefixComboBox<T> component = this;
    	
        registerRpc(new PrefixComboBoxServerRpc() {

			@Override
			public void popupOpened() {
				fireEvent(new PopupOpenedEvent<T>(component));
			}

			@Override
			public void popupClosed() {
				fireEvent(new PopupClosedEvent<T>(component));				
			}
        	
        });
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
    	setupServerRpc();
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

    /**
     * Set maximum input length in the text box of the PrefixComboBox

     * @param maxLength Maximum length, int
     */
    public void setMaxInputLength(int maxLength) {
    	getState().maxLength = maxLength;
    }

    /**
     * Get current maximum input length
     * 
     * @return Get current max input length
     */
    public int getMaxInputLenght() {
    	return getState().maxLength;
    }

    /**
     * Add a new PopupOpenedListener
     * The PopupOpenedEvent is fired when the suggestion popup of the ComboBox is opened
     * 
     * @param listener A new PopupOpenedListener
     * @return Registration
     */
	public Registration addPopupOpenedListener(PopupOpenedListener<T> listener) {
		return addListener(PopupOpenedEvent.class, listener, PopupOpenedListener.POPUP_OPENED_METHOD);
	}

    /**
     * Add a new PopupClosedListener
     * The PopupClosedEvent is fired when the suggestion popup of the ComboBox is closed
     * 
     * @param listener A new PopupClosedListener
     * @return Registration
     */
	public Registration addPopupClosedListener(PopupClosedListener<T> listener) {
		return addListener(PopupClosedEvent.class, listener, PopupClosedListener.POPUP_CLOSED_METHOD);
	}

	/**
	 * Programmatically open the suggestion popup at the current page
	 */
	public void openPopup() {
		openPopup(-1);
	}
	
	/**
	 * Programmatically open the suggestion popup at the given page
	 * 
	 * @param page The page where to open the popup 
	 */
	public void openPopup(int page) {
		getRpc().showPopup(page);
	}
	
	/**
	 * Set focus to this ComboBox and set the text in the text box selected
	 */
	public void selectText() {
		this.focus();
		getRpc().selectText();
	}

	/**
	 * Set a delay for text box input. This helps with lazy loading ComboBoxes with
	 * slow backend. The filtering is done if no new keypresses are done after the
	 * delay.
	 * 
	 * Setting the delay to 0 or negative will disable the feature (default)
	 * 
	 * @param updateInterval The delay in millis
	 */
	public void setTextUpdateInterval(int updateInterval) {
		getState().textUpdateInterval = updateInterval;
	}
	
	/**
	 * Configure whether to mark text in the text box automatically selected on mouse focus click
	 * 
	 * @param selectOnClick true = The text in text box is automatically set selected
	 */
	public void setSelectTextOnClick(boolean selectOnClick) {
		getState().selectAllOnFocus = selectOnClick;
	}
	
	/**
	 * Check if text is automatically selected or not
	 * 
	 * @return Boolean value
	 */
	public boolean isSelectTextOnClick() {
		return getState().selectAllOnFocus;
	}
	
	/**
	 * Set the cursor position in the text box of the PrefixComboBox
	 * 
	 * @param position The position to be set
	 */
	public void setCursorPos(int position) {
		int maxLength = getSelectedItemCaption().length();
		if (position < 0) throw new IllegalArgumentException("Cursor position can't be negative");
		if (position > maxLength) position = maxLength;
		getRpc().setCursorPos(position);
	}

	/**
	 * Get the item caption of currently selected item
	 * 
	 * @return The selected item caption
	 */
	public String getSelectedItemCaption() {
		if (getValue() == null || getValue() == this.getEmptyValue()) return this.getEmptySelectionCaption();
		ItemCaptionGenerator<T> generator = this.getItemCaptionGenerator();
		return generator.apply(getValue());
	}

	/**
	 * Change setting of cursor on focus
	 * 
	 * @param cursorOnFocus If true cursor is set in position 0 on focus, else unset
	 */
	public void setCursorOnFocus(boolean cursorOnFocus) {
		getState().setCursorOnFocus = cursorOnFocus;
	}

	/**
	 * Check if cursor is automatically set or not
	 * 
	 * @return Boolean value
	 */
	public boolean isCursorOnFocus() {
		return getState().setCursorOnFocus;
	}
	
	/**
	 * Set the focus to this component
	 */
	@Override
	public void focus() {
		super.focus();
		if (getState().setCursorOnFocus) setCursorPos(0);
	}
	
	private PrefixComboBoxClientRpc getRpc() {
		return getRpcProxy(PrefixComboBoxClientRpc.class);
	}

}
