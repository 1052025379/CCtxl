/*
 *
 * @author zhangtao
 *
 * Msn & Mail: zht_dream@hotmail.com
 */
package com.oracle.messenger.control.colorUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.SimplisticSoftBorderPainter;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.painter.ClassicGradientPainter;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;
import org.jvnet.substance.theme.SubstanceBottleGreenTheme;
import org.jvnet.substance.title.ArcHeaderPainter;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

import com.oracle.messenger.view.ChatFrame;
import com.sun.awt.AWTUtilities;

/**
 * 
 * @author zhangtao
 *
 * Msn & Mail: zht_dream@hotmail.com
 */
public class ColorPickerDemo extends JFrame {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setContentPane(new ColorPickerDemo(null));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private ZHTColorPicker picker = new ZHTColorPicker();
	private JPopupMenu menu = new JPopupMenu();

	public ColorPickerDemo(final ChatFrame parent) {
		this.setSize(205, 255);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		this.setTitle("Ñ¡Ôñ×ÖÌåÑÕÉ«");
		this.setLocationRelativeTo(null);
		menu.setLayout(new BorderLayout());
		menu.add(picker);
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					menu.show(ColorPickerDemo.this, e.getX(), e.getY());
				}
			}
		});
		picker.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName() == ZHTColorPicker.SELECTEDCOLORCHANGE) {
					menu.setVisible(false);
					Color color = picker.getSelectedColor();
					setBackground(color);
				}
				if (evt.getPropertyName() == ZHTColorPicker.MORECOLORSELECTION) {
					menu.setVisible(false);
				}
			}
		});
		JComboBox comboBox = new ColorPickerCombobox(parent,this);
		this.add(comboBox,BorderLayout.NORTH);
	}
}
