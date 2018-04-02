package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JLabel;

public class MainClient extends JFrame implements ActionListener,Runnable,WindowListener{
	private Socket s;
	JTextArea textArea = new JTextArea();
	JComboBox comboBox = new JComboBox();
	public void setSocket(Socket value) {
		s = value;
		Thread t = new Thread(this);
		t.start();
	}
	private JPanel contentPane;
	private JTextField textField;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MainClient frame = new MainClient();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public MainClient() {
		this.addWindowListener(this);
		setTitle("Lan-Connction Main");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		textField.setBounds(14, 448, 484, 37);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("\u53D1\u9001");
		button.setBackground(Color.LIGHT_GRAY);
		button.setBounds(540, 453, 113, 27);
		contentPane.add(button);
		button.addActionListener(this);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 17));
		
		textArea.setBounds(14, 7, 484, 406);
		contentPane.add(textArea);
		
		
		comboBox.setBounds(529, 54, 113, 37);
		contentPane.add(comboBox);
		
		label = new JLabel("\u5728\u7EBF\uFF1A");
		label.setFont(new Font("宋体", Font.PLAIN, 18));
		label.setBounds(529, 23, 83, 27);
		contentPane.add(label);
		ImageIcon icon=new ImageIcon(".\\image\\1.png");
	    this.setIconImage(icon.getImage());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			OutputStream os = s.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			PrintWriter pw1 =new PrintWriter(osw,true);
			pw1.println(comboBox.getSelectedItem()+"%"+textField.getText());
		}catch(Exception wee) {}
		textArea.append("我说："+textField.getText()+"\n");
		textField.setText("");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		InputStream is;
		try {
			is = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			while(true) {
				String message = br.readLine();
				String type = message.split("%")[0];
				String mess = message.split("%")[1];
				if(type.equals("add"))
				comboBox.addItem(mess);
				if(type.equals("exit")) {
					comboBox.removeItem(mess);
				}
				if(type.equals("mess")) {
					textArea.append(comboBox.getSelectedItem()+"说: "+mess+"\n");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println(66);
			try {
				OutputStream os = s.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				PrintWriter pw = new PrintWriter(osw,true);
				pw.println("{exit}");
				System.exit(0);
			} catch (Exception e1) {
				
			}
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}


