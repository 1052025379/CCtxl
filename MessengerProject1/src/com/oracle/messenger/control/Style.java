package com.oracle.messenger.control;

import java.awt.Color;
import java.io.Serializable;

public class Style  implements Serializable{
	private Color color;
	private int size;
	private String fontName;
	private boolean bold;
	private boolean italic;
	private boolean underline;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isItalic() {
		return italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	public boolean isUnderline() {
		return underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public Style(Color color, int size, String fontName, boolean bold,
			boolean italic, boolean underline) {
		super();
		this.color = color;
		this.size = size;
		this.fontName = fontName;
		this.bold = bold;
		this.italic = italic;
		this.underline = underline;
	}

	public Style() {
		super();
		this.setColor(Color.black);
		this.setSize(12);
		this.setFontName("ËÎÌו");
		this.setBold(false);
		this.setItalic(false);
		this.setUnderline(false);
	}

	@Override
	public String toString() {
		return "Style [color=" + color + ", size=" + size + ", fontName="
				+ fontName + ", bold=" + bold + ", italic=" + italic
				+ ", underline=" + underline + "]";
	}
}