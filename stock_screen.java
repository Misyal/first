package org.scerrn;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class stock_screen {
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton btnNewButton;
 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					stock_screen window = new stock_screen();  //创建进货窗口界面
					window.frame.setVisible(true);  //设置窗口可见
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 
	public stock_screen() {
		initialize();
	}
 
	private void initialize() {
		frame = new JFrame("进货管理");  //new一个Jfram容器
		int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width/2;;  //根据用户屏幕，自动调整控件的显示位置
		int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/2;;
		
		frame.setBounds(screenWidth/2, screenHeight/2, screenWidth, screenHeight);
		frame.getContentPane().setLayout(null);	
		
		JLabel lblNewLabel = new JLabel("品名");  //设置label控件属性，位置
		lblNewLabel.setFont(new Font("仿宋", Font.BOLD, 20));
		lblNewLabel.setBounds((screenWidth-180)/2, (screenHeight-180)/2, 50, 20);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("数量");  //设置label控件属性，位置
		lblNewLabel_1.setFont(new Font("仿宋", Font.BOLD, 20));
		lblNewLabel_1.setBounds((screenWidth-180)/2, (screenHeight-180)/2+30*1, 50, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("单位");  //设置label控件属性，位置
		lblNewLabel_2.setFont(new Font("仿宋", Font.BOLD, 20));
		lblNewLabel_2.setBounds((screenWidth-180)/2, (screenHeight-180)/2+30*2, 50, 20);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("总价");  //设置label控件属性，位置
		lblNewLabel_3.setFont(new Font("仿宋", Font.BOLD, 20));
		lblNewLabel_3.setBounds((screenWidth-180)/2, (screenHeight-180)/2+30*3, 50, 20);
		frame.getContentPane().add(lblNewLabel_3);
		
		textField = new JTextField();  //设置输入Field控件属性，位置
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_2.setText(new OperationSqlData().getUnitByGoodsName(textField.getText()));
			}
		});
		textField.setBounds((screenWidth-180)/2+60, (screenHeight-180)/2, 180, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField(); //设置输入Field控件属性，位置
		textField_1.setBounds((screenWidth-180)/2+60, (screenHeight-180)/2+30*1, 120, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField(); //设置输入ComboBox控件属性，位置 ，默认下拉框两个选项
		textField_2.setBounds((screenWidth-180)/2+60, (screenHeight-180)/2+30*2, 120, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setText(new OperationSqlData().getUnitByGoodsName(textField.getText()));
		
		textField_3 = new JTextField();  //设置输入Field控件属性，位置
		textField_3.setBounds((screenWidth-180)/2+60, (screenHeight-180)/2+30*3, 120, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		btnNewButton = new JButton("提交");
		btnNewButton.setMnemonic(KeyEvent.VK_ENTER);
		btnNewButton.setFont(new Font("仿宋", Font.BOLD, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().isEmpty()||textField_1.getText().isEmpty()||textField_3.getText().isEmpty()||textField_2.getText().isEmpty()) //检查商品名字段是否为空
				{
				   JOptionPane.showMessageDialog(null, "输入栏位不能为空！");
				   return;
				}
					int ret = new OperationSqlData().StockIn(textField.getText(), textField_1.getText(), (String) textField_2.getText(), textField_3.getText());
					if  (ret == 1)  //根据SQL返回值判断执行结果，并显示对话框
					{
						JOptionPane.showMessageDialog(null, "存储成功");
						textField.setText(null);
						textField_1.setText(null);
						textField_2.setText(null);;
						textField_3.setText(null);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "存储失败！");
					}			
			}
 				
		});
		btnNewButton.setBounds((screenWidth-180)/2+60, (screenHeight-180)/2+30*3+40, 120, 20);
		frame.getContentPane().add(btnNewButton);
	}

}
