package Client;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Login2 extends JFrame implements ActionListener{
	String url ;
	private JPanel contentPane;
	private JTextField textField;
	private JLabel label;
	private JLabel lblDesi;
	private JLabel lblNewLabel_1;
	private JPasswordField passwordField;
	private JLabel lblip;
	String str[] = {"127.0.0.1","192.168.1.106","","","",""};
	JComboBox comboBox = new JComboBox(str);
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login2 frame = new Login2();
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
	public Login2() {
		setResizable(false);
		setTitle("Lan-Connction Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 533, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		lblNewLabel.setBounds(49, 181, 89, 48);
		lblNewLabel.setFont(new Font("宋体",Font.BOLD,20));
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(140, 187, 272, 41);
		contentPane.add(textField);
		textField.setColumns(10);
		
		label = new JLabel("\u5C40\u57DF\u7F51\u901A\u8BAF\u5DE5\u5177");
		label.setBounds(37, 43, 253, 48);
		contentPane.add(label);
		label.setFont(new Font("黑体",Font.BOLD,30));
		
		lblDesi = new JLabel("Designed by:\u54F2");
		lblDesi.setFont(new Font("华文行楷", Font.PLAIN, 18));
		lblDesi.setBounds(362, 49, 112, 48);
		contentPane.add(lblDesi);
		
		lblNewLabel_1 = new JLabel("\u5BC6\u7801\uFF1A");
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_1.setBounds(49, 254, 77, 48);
		contentPane.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(140, 249, 272, 41);
		contentPane.add(passwordField);
		
		JButton button = new JButton("\u767B\u5F55");
		button.setFont(new Font("方正姚体", Font.PLAIN, 15));
		button.setBounds(109, 329, 113, 27);
		button.setBackground(new Color(225,225,225));
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u9000\u51FA");
		button_1.setFont(new Font("方正姚体", Font.PLAIN, 15));
		button_1.setBounds(299, 329, 113, 27);
		button_1.setBackground(new Color(225,225,225));
		contentPane.add(button_1);
		
		lblip = new JLabel("\u4E3B\u673Aip\u5730\u5740");
		lblip.setFont(new Font("方正兰亭超细黑简体", Font.BOLD, 18));
		lblip.setBounds(31, 131, 95, 37);
		contentPane.add(lblip);
		comboBox.setFont(new Font("宋体", Font.PLAIN, 17));
		
		
		comboBox.setBounds(140, 138, 272, 36);
		comboBox.setBorder(null);
		comboBox.setEditable(true);
		contentPane.add(comboBox);
		
		button.addActionListener(this);
		button_1.addActionListener(this);
		
		ImageIcon icon=new ImageIcon(".\\image\\1.png");
	    this.setIconImage(icon.getImage());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().toString().equals("退出")) {
			System.exit(0);
		}
		url = comboBox.getSelectedItem().toString();
		try {
			String ssr = textField.getText()+"%"+passwordField.getText();
			
			
			Socket s =new Socket(url,8001);
			
			OutputStream os = s.getOutputStream();
			OutputStreamWriter osw =new OutputStreamWriter(os);
			PrintWriter pw = new PrintWriter(osw,true);
			pw.println(ssr);
			
			InputStream is = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			if(br.readLine().equals("ok")) {
				MainClient ff =new MainClient();
				ff.setSocket(s);
				ff.setVisible(true);
				this.dispose();
			}else {
				JOptionPane.showMessageDialog(this, "用户名或密码错误");
			}
		}catch(Exception ewe) {}
	}
}
