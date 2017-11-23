package com.oracle.messenger.control.colorUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

import com.oracle.messenger.view.ChatFrame;

public class ColorPickerCombobox extends JComboBox {
	public static final String SELECTEDCOLOR = "selectedcolor";
	private ChatFrame c;
	private JFrame picker;
	public ColorPickerCombobox(ChatFrame  c,JFrame parent) {
		this.c=c;
		this.picker=parent;
		this.setEditable(false);
		this.setRenderer(new ListCellRenderer() {
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				ZHTColorTextField textField = new ZHTColorTextField();
				textField.setColor(getSelectedColor());
				return textField;
			}
		});
		this.setPreferredSize(new Dimension(150, 22));
	}

	public void updateUI() {
		this.setUI(new MetalDateComboBoxUI());
	}

	class MetalDateComboBoxUI extends MetalComboBoxUI {
		protected ComboPopup createPopup() {
			return new ColorPopup(comboBox);
		}
	}

	class ColorPopup extends BasicComboPopup implements PropertyChangeListener {
		private ZHTColorPicker picker;

		public ColorPopup(JComboBox box) {
			super(box);
			picker = new ZHTColorPicker();
			picker.addPropertyChangeListener(this);
			this.setLayout(new BorderLayout());
			this.add(picker, BorderLayout.CENTER);
			this.setBorder(BorderFactory.createEmptyBorder());
		}

		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName() == ZHTColorPicker.SELECTEDCOLORCHANGE) {
				comboBox.putClientProperty(SELECTEDCOLOR, picker.getSelectedColor());
				comboBox.setPopupVisible(false);
				c.getEditMessage().setForeground(picker.getSelectedColor());
				ColorPickerCombobox.this.picker.dispose();
			}
		}
	}

	public Color getSelectedColor() {
		Object obj = getClientProperty(SELECTEDCOLOR);
		if (obj != null && obj instanceof Color) {
			return (Color) obj;
		} else {
			return null;
		}
	}

	public void setSelectedColor(Color selectedColor) {
		putClientProperty(SELECTEDCOLOR, selectedColor);
	}
}
