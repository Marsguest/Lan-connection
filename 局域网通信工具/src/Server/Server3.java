package Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;

public class Server3 extends JFrame{
	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server3 frame = new Server3();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

		try {
			HashMap<String,Socket> hm= new HashMap<String,Socket>();
			ServerSocket ss = new ServerSocket(8001);
			while(true) {
				System.out.println("服务器正在8001端口监听---");
				Socket s = ss.accept();
				MyService t = new MyService();
				t.setHashMap(hm);
				t.setSocket(s);
				t.start();
			}
			
			
		}catch(Exception errr) {}
	}

	/**
	 * Create the frame.
	 */
	public Server3() {
		setResizable(false);
		setTitle("Lan-Connection Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(">>\u670D\u52A1\u5668\u6B63\u57288001\u7AEF\u53E3\u76D1\u542C---");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("微软雅黑 Light", Font.BOLD, 19));
		lblNewLabel.setBounds(14, 13, 285, 50);
		contentPane.add(lblNewLabel);

		ImageIcon icon=new ImageIcon(".\\image\\2.jpg");
	    this.setIconImage(icon.getImage());
	   
	}
}

class MyService extends Thread {
	private Socket s;
	private HashMap<String,Socket> hm;
	Connection cn = null;
	ResultSet rs = null;
	PreparedStatement ps =null;
	public void setHashMap(HashMap hm) {
		this.hm = hm;
	}
	public void setSocket(Socket s) {
		this.s = s;
	}
	public void run() {
		try {
			InputStream is = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			
			String clientInformation = br.readLine();
			System.out.println(clientInformation);
			//check point
			
			OutputStream os = s.getOutputStream();
			OutputStreamWriter osr = new OutputStreamWriter(os);
			PrintWriter pw = new PrintWriter(osr,true);
			
			String user = "";
			String passwd = "";
			try {
				user = clientInformation.split("%")[0];
				passwd = clientInformation.split("%")[1];
			}catch(Exception eyt) {}
			
			Class.forName("org.gjt.mm.mysql.Driver");//启动插件
			cn  = DriverManager.getConnection(数据库基本信息);
			ps = cn.prepareStatement("select * from user where username=? and password =?");
			ps.setString(1, user);
			ps.setString(2, passwd);
			rs = ps.executeQuery();//启动查询语句
			if(rs.next()) {
				pw.println("ok");
				
				for(Socket ts:hm.values()) {
					OutputStream tos = ts.getOutputStream();
					OutputStreamWriter tosr = new OutputStreamWriter(tos);
					PrintWriter tpw = new PrintWriter(tosr,true);
					tpw.println("add%"+user);
				}
				for(String tu:hm.keySet()) {
					pw.println("add%"+tu);
				}
				hm.put(user, s);
				while(true){
					String message = br.readLine();
					if(message.equals("{exit}")) {
						System.out.println("exit");
						hm.remove(user);
						for(Socket ts:hm.values()) {
							OutputStream tos = ts.getOutputStream();
							OutputStreamWriter tosr = new OutputStreamWriter(tos);
							PrintWriter tpw = new PrintWriter(tosr,true);
							tpw.println("exit%"+user);
						}
						return ;
					}
					String to = message.split("%")[0];
					String msge = message.split("%")[1];
					Socket ts = hm.get(to);
					OutputStream tos = ts.getOutputStream();
					OutputStreamWriter tosw = new OutputStreamWriter(tos);
					PrintWriter tpw = new PrintWriter(tosw,true);
					
					tpw.println("mess%"+msge);
					System.out.println(message);
				}
			}else {
				pw.println("err");
			}
		}catch(Exception e) {}
		finally {
			try {
				cn.close();
				ps.close();
				rs.close();
			}catch(Exception e) {}
		}
	}
}

