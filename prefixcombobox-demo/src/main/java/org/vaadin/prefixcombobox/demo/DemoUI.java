package org.vaadin.prefixcombobox.demo;

import org.vaadin.prefixcombobox.PrefixComboBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("PrefixComboBox Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        // Initialize our new UI component
        VerticalLayout mainLayout = new VerticalLayout();

        List<String> items = new ArrayList<>(Arrays.asList("blue", "red", "green", "purple", "grey", "orange"));

        PrefixComboBox<String> comboBox = new PrefixComboBox<>("",items);
        comboBox.setValue("red");
        comboBox.setPageLength(0);
        comboBox.setItems(items);
        comboBox.setPrefix("Color");
        comboBox.setWidth("200px");
        
        Label label = new Label("Value: ");
        comboBox.setTextInputAllowed(true);
        
        comboBox.setNewItemProvider(value -> {
            items.add(value);
            comboBox.getDataProvider().refreshAll(); //I don't know if it's required but it doesn't impact the bug
            return Optional.ofNullable(value);
        });
        mainLayout.addComponents(comboBox, label);
        comboBox.addValueChangeListener(event -> {
        	label.setValue("Value: "+comboBox.getValue());
        });
        
        setContent(mainLayout);
    }
}
