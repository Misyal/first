package org.scerrn;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.KeyEvent;
 
public class sales_screen {
 
	// 定义SWT控件
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JComboBox<String> comboBox;
	private JTable table_1;
	private DefaultTableModel model;
	private int m = 0;
 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sales_screen window = new sales_screen(); // 新建销售窗口
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 
	public sales_screen() {
		initialize();
	}

	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame("销售管理"); // new一个fram容器
		// 自动设置销售管理窗口大小为用户屏幕的一半，切位于屏幕中央
		int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width*2/3;
		int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height*2/3;
		frame.setBounds(screenWidth/6, screenHeight/6, screenWidth, screenHeight);
		frame.getContentPane().setLayout(null);
 
		JLabel lblNewLabel = new JLabel("品名");// 设置品名label属性
		lblNewLabel.setFont(new Font("仿宋", Font.BOLD, 20));
		lblNewLabel.setBounds((screenWidth - 880) / 2, (screenHeight - 400) / 2+100, 50, 20);
		frame.getContentPane().add(lblNewLabel);
 
		JLabel lblNewLabel_1 = new JLabel("数量");// 设置单价label属性
		lblNewLabel_1.setFont(new Font("仿宋", Font.BOLD, 20));
		lblNewLabel_1.setBounds((screenWidth - 880) / 2, (screenHeight - 400) / 2+140 , 50, 20);
		frame.getContentPane().add(lblNewLabel_1);
 
		JLabel lblNewLabel_2 = new JLabel("单价");// 设置数量label属性
		lblNewLabel_2.setFont(new Font("仿宋", Font.BOLD, 20));
		lblNewLabel_2.setBounds((screenWidth - 880) / 2, (screenHeight - 400) / 2+180 , 50, 20);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setEditable(false);
		textField_3.setBounds((screenWidth - 880) / 2 + 170, (screenHeight - 400) / 2 + 140, 60, 20);;
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);		
 
		comboBox = new JComboBox<String>();// 设置combox品名输入属性
		new OperationSqlData().getGoodsNameToCombox(comboBox);
		comboBox.setBounds((screenWidth - 880) / 2 + 55, (screenHeight - 400) / 2 + 100, 180, 20);
		frame.getContentPane().add(comboBox);
		comboBox.setSelectedItem(null);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String unit = new OperationSqlData().getUnitByGoodsName(comboBox.getSelectedItem().toString());
				String sales_price = new OperationSqlData().getSalesPriceByGoodsName(comboBox.getSelectedItem().toString());	
				textField.setText(sales_price);
				textField_3.setText(unit);
				textField_1.setText(null);
			}
		});
 
		textField_1 = new JTextField();
		textField_1.setBounds((screenWidth - 880) / 2 + 55, (screenHeight - 400) / 2 + 140, 110, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
 
		textField = new JTextField();// 设置(数量)Field输入属性
		textField.setBounds((screenWidth - 880) / 2 + 55, (screenHeight - 400) / 2 + 180, 110, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
 
		JButton btnNewButton_2 = new JButton(">>");// 设置>>按钮属性及事件处理
		btnNewButton_2.setFont(new Font("仿宋", Font.BOLD, 16));
		btnNewButton_2.setBounds((screenWidth - 880) / 2 + 55, (screenHeight - 400) / 2 + 220, 60, 20);
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			// 将用户输入的数据，写入Jtable中，计算总金额
			public void actionPerformed(ActionEvent arg0) {
				m = model.getRowCount();
				for(int q=0; q<m; q++)
				{
					if(model.getValueAt(q, 0).toString().equals(comboBox.getSelectedItem().toString()))
					{
						JOptionPane.showMessageDialog(null, "重复加入购物车！");
					     return;
					}
				}
				if (comboBox.getSelectedItem().toString().isEmpty()||textField.getText().isEmpty()||textField_1.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "输入栏位不能为空！");
					return;
				}
				
				model.addRow(new Object[4]);
				model.setValueAt(comboBox.getSelectedItem(), m, 0);
				model.setValueAt(textField_1.getText(), m, 1);
				model.setValueAt(textField_3.getText(), m, 2);
				model.setValueAt(textField.getText(), m, 3);
				model.setValueAt((float)(Math.round(Float.parseFloat(textField.getText()) * Float.parseFloat(textField_1.getText())*100))/100, m, 4);
			}
		});		
		
		JButton btnNewButton = new JButton("提交");// 设置提交按钮属性及事件处理
		btnNewButton.setFont(new Font("仿宋", Font.BOLD, 16));
		btnNewButton.setMnemonic(KeyEvent.VK_ENTER);
		btnNewButton.setBounds((screenWidth - 880) / 2 + 450, (screenHeight - 400) / 2 + 390, 80, 20);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new OperationSqlData().CommitSalesToSql(model, table_1);
				int j = model.getRowCount();
				while(j>0)
				{
					model.removeRow(j-1);
					j--;
				}
			}
		});
 
		JButton btnNewButton_1 = new JButton("删除");// 设置删除按钮属性及事件处理
		btnNewButton_1.setFont(new Font("仿宋", Font.BOLD, 16));
		btnNewButton_1.setBounds((screenWidth - 880) / 2 + 580, (screenHeight - 400) / 2 + 390, 80, 20);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_1.getSelectedRow();
				if (row >= 0) {
					model.removeRow(row);
				}
			}
		});
 
		// 依据model创建一个Jtable
		String[] header = { "品名", "数量", "单位", "单价", "总价" };// 设置表头
		Object[][] item = new Object[0][5];// 设置单身
		model = new DefaultTableModel(item, header) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};// 创建一个model，并设置model数据不可编辑
		table_1 = new JTable(model);// 根据model创建一个Jtable
		table_1.setFocusable(false);// 关闭鼠标选中单个坐标
 
 
		JScrollPane scrollPane = new JScrollPane(table_1);// 设置JScrollPane容器属性，将Jtable放入JScrollPane中，实现数据超出上下左右滚动条的功能
		scrollPane.setLocation((screenWidth - 880) / 2 + 250, (screenHeight - 440) / 2);
		scrollPane.setSize(600, 400);
		frame.getContentPane().add(scrollPane);
		
	}
}