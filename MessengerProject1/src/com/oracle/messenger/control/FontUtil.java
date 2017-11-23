package com.oracle.messenger.control;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * ◊÷ÃÂ…Ë÷√∞Ô÷˙¿‡
 * 
 * @author TengSir
 * 
 */
public class FontUtil {
	public static MutableAttributeSet setFont(JTextPane p, JToggleButton bold,
			JToggleButton italic, JToggleButton underline,
			JComboBox<String> fontNames, JComboBox<String> fontSize, int start,
			int end, Color c) {
		MutableAttributeSet attrset = new SimpleAttributeSet();
		if (bold.isSelected())
			StyleConstants.setBold(attrset, true);
		else
			StyleConstants.setBold(attrset, false);
		if (underline.isSelected())
			StyleConstants.setUnderline(attrset, true);
		else
			StyleConstants.setUnderline(attrset, false);
		if (italic.isSelected())
			StyleConstants.setItalic(attrset, true);
		else
			StyleConstants.setItalic(attrset, false);
		StyleConstants.setFontSize(attrset,
				Integer.parseInt(fontSize.getSelectedItem().toString()));
		StyleConstants.setFontFamily(attrset, fontNames.getSelectedItem()
				.toString());
		StyleConstants.setForeground(attrset, c);
		p.getStyledDocument()
				.setCharacterAttributes(start, end, attrset, false);
		return attrset;
	}

	public static Style getStyle(JToggleButton bold, JToggleButton italic,
			JToggleButton underline, JComboBox<String> fontNames,
			JComboBox<String> fontSize, Color c) {
		Style style = new Style();
		if (bold.isSelected())
			style.setBold(true);
		else
			style.setBold(false);
		if (underline.isSelected())
			style.setUnderline(true);
		else
			style.setUnderline(false);
		if (italic.isSelected())
			style.setItalic(true);
		else
			style.setItalic(false);
		style.setSize(Integer.parseInt(fontSize.getSelectedItem().toString()));
		style.setFontName(fontNames.getSelectedItem()
				.toString());
		style.setColor(c);
		return style;
	}

	public static MutableAttributeSet wrapAttributeSet(Style style) {
		MutableAttributeSet attrset = new SimpleAttributeSet();
			StyleConstants.setBold(attrset, style.isBold());
			StyleConstants.setUnderline(attrset, style.isUnderline());
			StyleConstants.setItalic(attrset, style.isItalic());
			StyleConstants.setFontSize(attrset,style.getSize());
			StyleConstants.setFontFamily(attrset, style.getFontName());
			StyleConstants.setForeground(attrset, style.getColor());
		return attrset;
	}
}
