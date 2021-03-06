package org.vaadin.prefixcombobox.client;

import java.util.ArrayList;

import org.vaadin.prefixcombobox.PrefixComboBox;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.client.VConsole;
import com.vaadin.client.WidgetUtil;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.VComboBox;
import com.vaadin.client.ui.combobox.ComboBoxConnector;
import com.vaadin.shared.ui.Connect;

@Connect(PrefixComboBox.class)
public class PrefixComboBoxConnector extends ComboBoxConnector {

    PrefixComboBoxServerRpc rpc = RpcProxy.create(PrefixComboBoxServerRpc.class, this);

	private String prefix;
	HTML html = null;
	private static final String STYLE_ADJUSTMENTS = "padding-left: 1px; border-left: none; border-top-left-radius: 0px; border-bottom-left-radius: 0px";
	
	public PrefixComboBoxConnector() {
		super();		
	
		registerRpc(PrefixComboBoxClientRpc.class, new PrefixComboBoxClientRpc() {
			@Override
			public void showPopup(int currentPage) {
				if (currentPage == -1) {					
					getWidget().suggestionPopup.showSuggestions(getWidget().currentPage);
				} else {
					getWidget().suggestionPopup.showSuggestions(currentPage);	
				}
			}

			@Override
			public void selectText() {
				getWidget().tb.setSelectionRange(0, getWidget().tb.getValue().length());
			}

			@Override
			public void setCursorPos(int position) {
				getWidget().tb.setCursorPos(position);				
			}
		});
		
		getWidget().setStyleName("prefix-combobox", true);
		getWidget().tb.setStyleName("prefix-combobox-input",true);
		
		getWidget().suggestionPopup.addAttachHandler(event -> {
			if (getWidget().suggestionPopup.isShowing()) {
				getRpc().popupOpened();
			} else {
				getRpc().popupClosed();
			}
		});
		
	}
	
    @Override
    public VPrefixComboBox getWidget() {
        return (VPrefixComboBox) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        
        if (stateChangeEvent.hasPropertyChanged("maxLength")) {
        	int maxLength = getState().maxLength;
        	if (maxLength > 0) {
        		getWidget().tb.setMaxLength(maxLength);        		
        	}
        }

        if (stateChangeEvent.hasPropertyChanged("textUpdateInterval")) {
        	int updateInterval = getState().textUpdateInterval;
        	if (updateInterval > 0) {
        		getWidget().setUpdateInterval(updateInterval);        		
        	}
        }
        
        if (stateChangeEvent.hasPropertyChanged("prefix")) {
        	if (getState().prefix.equals("")) return;
        	
        	// Store content 
        	ArrayList<Node> childNodes = new ArrayList<Node>();
        	Element el = getWidget().getElement();
			NodeList<Node> children = el.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				childNodes.add(children.getItem(i));
			}
			// Create prefix
        	prefix = getState().prefixHtml ? getState().prefix+":" : WidgetUtil.escapeHTML(getState().prefix)+":";
			html = new HTML(prefix);
			html.setStyleName("prefix-combobox-prefix", true);
			// Get width of the prefix
			int width = getWidget().getOffsetWidth();
        	el.removeAllChildren();
        	// Insert prefix as first and restore content
        	int i=0;
			for (Node node : childNodes) {
				if (i==0) {
					el.appendChild(html.getElement());
				}
				el.appendChild(node);
				i++;
			}       
			// Adjust width and styles fit with prefix
			int newWidth = width-html.getOffsetWidth();
			getWidget().tb.getElement().setAttribute("style", "width: "+newWidth+"px !important; "+STYLE_ADJUSTMENTS);
        }
    }
    
    @Override
    protected void updateWidgetSize(String newWidth, String newHeight) {
    	super.updateWidgetSize(newWidth,newHeight);
    	if (getState().prefix.equals("") || html == null) return;

    	int width = getWidget().getOffsetWidth();
		int adjustedWidth = width-html.getOffsetWidth();
		getWidget().tb.getElement().setAttribute("style", "width: "+adjustedWidth+"px !important; "+STYLE_ADJUSTMENTS);        	    	
    	
    }
    
    @Override
	public PrefixComboBoxState getState() {
    	return (PrefixComboBoxState) super.getState(); 
    }

	private PrefixComboBoxServerRpc getRpc() {
		return getRpcProxy(PrefixComboBoxServerRpc.class);
	}


}
