package cctxl;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Cctxl extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cctxl frame = new Cctxl();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cctxl() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(98, 194, 114, 23);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(150, 62, 160, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(152, 113, 160, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label = new JLabel("账号");
		label.setBounds(65, 65, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("密码");
		label_1.setBounds(65, 116, 54, 15);
		contentPane.add(label_1);
		
		JButton btnNewButton_1 = new JButton("注册账号");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_1.setBounds(260, 194, 114, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("image\\2.jpg"));
		lblNewLabel.setBounds(326, 10, 106, 146);
		contentPane.add(lblNewLabel);
	}
}
