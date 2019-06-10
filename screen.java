package org.scerrn;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JButton;
 
import java.awt.Font;
 
public class screen {
 
	private JFrame frame;
	private int screenWidth;
	private int screenHeight;
 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					screen window = new screen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public screen() {
		initialize();
	}
 
	private void initialize() {
		screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width; //根据用户屏幕，自动调整屏幕元素位置
		screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;		
		frame = new JFrame("销售管理系统");
		frame.setBounds(0, 0, screenWidth, screenHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 55, screenWidth, screenHeight-55);
		frame.add(lblNewLabel);
 
		JMenuBar mb = new JMenuBar();
		mb.setBounds(0, 0, screenWidth, 25);
		frame.getContentPane().add(mb);
 
		JMenu m0 = new JMenu("系统");
		m0.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		mb.add(m0);	
		JMenuItem mt02 = new JMenuItem("退出系统");
		mt02.setEnabled(true);
		m0.add(mt02);
		mt02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				System.exit(0);
			}
		});		
 
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 26, screenWidth, 30);
		frame.getContentPane().add(toolBar);
 
		JButton bt1 = new JButton("仓库管理");
		bt1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stock_screen.main(null);
			}
		});
		bt1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		bt1.setBorder(null);
		toolBar.add(bt1);
		toolBar.addSeparator();
		toolBar.addSeparator();
		
		
		JButton bt2 = new JButton("商品定价");
		bt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pricing_screen.main(null);
			}
		});
		bt2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		bt2.setBorder(null);
		toolBar.add(bt2);
		toolBar.addSeparator();
		toolBar.addSeparator();
 
		JButton bt3 = new JButton("销售商品");
		bt3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sales_screen.main(null);
			}
		});
		bt3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		bt3.setBorder(null);
		toolBar.add(bt3);
		toolBar.addSeparator();
		toolBar.addSeparator();	
	
	}
}	