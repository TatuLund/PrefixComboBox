package org.vaadin.prefixcombobox.demo;

import org.jsoup.nodes.Comment;
import org.vaadin.prefixcombobox.PrefixComboBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("PrefixComboBox Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

//	
//	public class TestLayout extends VerticalLayout {
//
//	    public class KeyValueBean {
//
//	        private Object key;
//	        private String value;
//
//	        public KeyValueBean(Object inKey, String inValue) {
//	            key = inKey;
//	            value = inValue;
//	        }
//
//	        public Object getKey() {
//	            return key;
//	        }
//
//	        public void setKey(Object inKey) {
//	            key = inKey;
//	        }
//
//	        public String getValue() {
//	            return value;
//	        }
//
//	        public void setValue(String inValue) {
//	            value = inValue;
//	        }
//	    }
//
//	    public TestLayout() {
//	        KeyValueBean parent = null;
//	        TreeGrid<KeyValueBean> tree = new TreeGrid<>(KeyValueBean.class);
//	        tree.setColumns("key", "value");
//	        tree.setHierarchyColumn("key");
//	        TreeData<KeyValueBean> treeData = new TreeData<>();
//
//	        for (int i = 0; i < 10; i++) {
//	        	KeyValueBean val = new KeyValueBean("key" + i, "value" + i);
//	            treeData.addItem(null, val);
//	        }
//	        
//	        for (int i = 0; i < 1; i++) {
//	        	KeyValueBean val = new KeyValueBean("1key" + i, "1value" + i);
//	            treeData.addItem(parent, val);
//		        for (int j = 0; j < 10; j++) {
//		        	KeyValueBean v = new KeyValueBean("2key" + j, "2value" + j);
//		            treeData.addItem(val, v);
//			        for (int k = 0; k < 10; k++) {
//			        	KeyValueBean v1 = new KeyValueBean("3key" + k, "3value" + k);
//			            treeData.addItem(v, v1);
//				        for (int m = 0; m < 10; k++) {
//				        	KeyValueBean v2 = new KeyValueBean("4key" + k, "4value" + k);
//				            treeData.addItem(v, v2);
//				        }
//			        }
//		        }
//	        }
//	        TreeDataProvider<KeyValueBean> dataProvider = new TreeDataProvider<>(treeData);
//	        tree.setDataProvider(dataProvider);
//	        
//	        addComponent(tree);
//	    }
//	}	
	
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        // Initialize our new UI component
        VerticalLayout mainLayout = new VerticalLayout();

        List<String> items = new ArrayList<>(Arrays.asList("blue", "cyan", "pinke", "red", "green", "brown", "magenta", "purple", "deep purple", "grey", "orange", "black", "yellow"));

        PrefixComboBox<String> comboBox = new PrefixComboBox<>("",items);
        comboBox.setPageLength(0);
        comboBox.setPopupWidth(null);
        comboBox.setEmptySelectionAllowed(false);
        comboBox.setMaxInputLength(10);
        comboBox.setItems(items);
        comboBox.setPrefix("Color");
        comboBox.setWidth("200px");
        Label label = new Label("Value: ");
        comboBox.setTextInputAllowed(true);
        comboBox.openPopup();
        comboBox.setSelectTextOnClick(true);
        comboBox.setCursorOnFocus(true);
        comboBox.setPlaceholder("color");
        comboBox.setTextUpdateInterval(500);
        comboBox.setNewItemProvider(value -> {
            items.add(value);
            comboBox.getDataProvider().refreshAll();
            return Optional.ofNullable(value);
        });
        Button selectButton = new Button("Selext text");
        selectButton.addClickListener(event -> {
        	comboBox.selectText();
        });
        Button focusButton = new Button("Focus");
        focusButton.addClickListener(event -> {
        	comboBox.focus();
        });
        Button selectOnFocusButton = new Button("Toggle select on click");
        selectOnFocusButton.addClickListener(event -> {
        	comboBox.setSelectTextOnClick(!comboBox.isSelectTextOnClick());
        });
        Button cursorOnFocusButton = new Button("Toggle cursor set");
        cursorOnFocusButton.addClickListener(event -> {
        	comboBox.setCursorOnFocus(!comboBox.isCursorOnFocus());
        });
        mainLayout.addComponents(comboBox, selectButton, focusButton, selectOnFocusButton, cursorOnFocusButton, label);
        comboBox.addValueChangeListener(event -> {
        	label.setValue("Value: "+comboBox.getValue()+" Caption: "+comboBox.getSelectedItemCaption());
        });
        comboBox.addPopupOpenedListener(event -> {
        	mainLayout.addComponent(new Label("ComboBox opened"));
        });
//        mainLayout.setComponentAlignment(comboBox, Alignment.TOP_RIGHT);
        mainLayout.setMargin(false);

//        mainLayout.addComponent(new TestLayout());
//        
        setContent(mainLayout);
    }
}
