package org.scerrn;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
 
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.JSpinner;

public class pricing_screen {
	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private JTextField textField;
	private JTextField textField_1;
	private JSpinner spinner;
	private JSpinner spinner_1;
 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pricing_screen window = new pricing_screen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 
	/**
	 * Create the application.
	 */
	public pricing_screen() {
		initialize();
	}
 
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame("销售定价");
		int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width*2/3;
		int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height*2/3;	
		frame.setBounds(screenWidth/6, screenHeight/6, screenWidth, screenHeight);
		frame.getContentPane().setLayout(null);
 
		// 依据model创建一个Jtable
		String[] header = { "品名","总价值","库存数量","单位","进货单价","销售单价" };// 设置表头
		Object[][] item = new Object[0][6];// 设置单身
		model = new DefaultTableModel(item, header) {
			public boolean isCellEditable(int row, int column) {
				if (column == 5)
					return true;
				else
					return false;
			}
		};// 创建一个model，并设置model数据不可编辑
		table = new JTable(model);// 根据model创建一个Jtable
		table.setFocusable(false);// 关闭鼠标选中单个坐标
 
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds((screenWidth-780)/2+170, (screenHeight-440)/2-20, 600, 400);
		frame.getContentPane().add(scrollPane);		
 
		JLabel label_4 = new JLabel("起始日期");
		label_4.setFont(new Font("仿宋", Font.BOLD, 14));
		label_4.setBounds((screenWidth-780)/2, (screenHeight-440)/2-20, 60, 20);
		frame.getContentPane().add(label_4);
 
		JLabel label_5 = new JLabel("结束日期");
		label_5.setFont(new Font("仿宋", Font.BOLD, 14));
		label_5.setBounds((screenWidth-780)/2, (screenHeight-440)/2-20+30, 60, 20);
		frame.getContentPane().add(label_5);
 
		//定义两个spinner用于日期的调整
		GregorianCalendar gc=new GregorianCalendar(); //当前日期的前一个月
		gc.setTime(new Date());
		gc.add(2, -1);		
		SpinnerModel dateModel=null;
		dateModel = new SpinnerDateModel(gc.getTime(), null, null,Calendar.DAY_OF_MONTH);
		spinner = new JSpinner();
		spinner.setBounds((screenWidth-780)/2+65, (screenHeight-440)/2-20, 90, 20);
		frame.getContentPane().add(spinner);
		spinner.setModel(dateModel);  
		spinner.setEditor(new JSpinner.DateEditor(spinner,"yyyy-MM-dd"));
 
		SpinnerModel dateModel_1=null;
		dateModel_1 = new SpinnerDateModel(new Date(), null, null,Calendar.DAY_OF_MONTH);		
		spinner_1 = new JSpinner();
		spinner_1.setBounds((screenWidth-780)/2+65, (screenHeight-440)/2-20+30, 90, 20);
		frame.getContentPane().add(spinner_1);
		spinner_1.setModel(dateModel_1); 
		spinner_1.setEditor(new JSpinner.DateEditor(spinner_1,"yyyy-MM-dd"));			
		
		JButton button_1 = new JButton("查找");
		button_1.setFont(new Font("仿宋", Font.BOLD, 16));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//点击查找按钮，根据起止日期，查询库存信息
				int r = model.getRowCount();
				while(r>0)
				{
					model.removeRow(r-1);
					r--;
				}
 
				Object[] obj = new Object[4];
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 创建指定格式的当前时间
				ArrayList<Object[]> list = new OperationSqlData().getStockRecordByDate(df.format(spinner.getValue())+" "+"00:00:00", df.format(spinner_1.getValue())+" "+"23:59:59",1 , true);
				int row = list.size();
				float cost = 0;
				//显示到table
				for(int i=0; i<row; i++)
				{
					obj = list.get(i);
					model.addRow(new Object[5]);
					int j = model.getRowCount();
					model.setValueAt(obj[0], j-1, 0);
					model.setValueAt(obj[3], j-1, 1);
					model.setValueAt(obj[1], j-1, 2);
					model.setValueAt(obj[2], j-1, 3);
					cost = Float.parseFloat(obj[3].toString())/Float.parseFloat(obj[1].toString());
					cost =  (float)(Math.round(cost*100))/100;
					model.setValueAt(cost, j-1, 4);
				}
			}
		});
		button_1.setBounds((screenWidth-780)/2+30, (screenHeight-440)/2-20+70, 90, 20);
		frame.getContentPane().add(button_1);		
	   
//		//定义两种加价方式按比例和固定值，基于日期区间库存成本定义销售价格
//		JLabel lblNewLabel = new JLabel("统一按比例加价");
//		lblNewLabel.setFont(new Font("仿宋", Font.BOLD, 14));
//		lblNewLabel.setBounds((screenWidth-780)/2-20, (screenHeight-440)/2-20+190, 110, 20);
//		frame.getContentPane().add(lblNewLabel);
// 
//		JLabel label_1 = new JLabel("%");
//		label_1.setBounds((screenWidth-780)/2+150, (screenHeight-440)/2-20+190, 20, 20);
//		frame.getContentPane().add(label_1);
// 
//		JLabel label_2 = new JLabel("统一按固定值加价");
//		label_2.setFont(new Font("仿宋", Font.BOLD, 14));
//		label_2.setBounds((screenWidth-780)/2-20, (screenHeight-440)/2-20+220, 120, 20);
//		frame.getContentPane().add(label_2);
// 
//		JLabel label_3 = new JLabel("￥");
//		label_3.setBounds((screenWidth-780)/2+150, (screenHeight-440)/2-20+220, 20, 20);
//		frame.getContentPane().add(label_3);		
 
//		textField = new JTextField();
//		textField.setBounds((screenWidth-780)/2+105, (screenHeight-440)/2-20+190, 40, 20);
//		frame.getContentPane().add(textField);
//		textField.setColumns(10);		
// 
//		textField_1 = new JTextField();
//		textField_1.setBounds((screenWidth-780)/2+105, (screenHeight-440)/2-20+220, 40, 20);
//		frame.getContentPane().add(textField_1);
//		textField_1.setColumns(10);
 
		JButton btnNewButton = new JButton("确定");
		btnNewButton.setFont(new Font("仿宋", Font.BOLD, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//两种加价方式选其一
				if (!textField.getText().isEmpty() && !textField_1.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "加价方式（按比例/按固定值）二选一");
				    return;
				}
				
				else if(!textField.getText().isEmpty())
				{
					int row = 0;
					row = model.getRowCount();
                    for(int i=0; i<row; i++)
                    {
                    	//计算销售价格：成本*比例 保留两位小数的另一种方法
                    	float sales = Float.parseFloat(model.getValueAt(i, 4).toString())*(1+Float.parseFloat(textField.getText())/100);
                    	sales =  (float)(Math.round(sales*100))/100;
                    	model.setValueAt(sales, i, 5);
                    }
                         
				}
				
				else if(!textField_1.getText().isEmpty())
				{
                    int row;
                    row = model.getRowCount();
					for(int i=0; i<row; i++)
                    {
						//计算销售价格：成本+定值
                    	float sales = Float.parseFloat(model.getValueAt(i, 4).toString())+Float.parseFloat(textField_1.getText());
                    	sales =  (float)(Math.round(sales*100))/100;
                    	model.setValueAt(sales, i, 5);
                    }					
				}
				      
			}
		});
		btnNewButton.setBounds((screenWidth-780)/2+30, (screenHeight-440)/2-20+260, 90, 20);
		frame.getContentPane().add(btnNewButton);	
 
		JButton button = new JButton("提交");
		button.setFont(new Font("仿宋", Font.BOLD, 16));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new OperationSqlData().CommitSalesStockPricingToSql(model, table);
			}
		});
		button.setBounds((screenWidth-780)/2+400, (screenHeight-440)/2+400, 90, 20);
		frame.getContentPane().add(button);
	}
}
