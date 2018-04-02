package Client;
import javax.swing.*;
import java.awt.event.*;

public class Login_closeListener implements ActionListener{

	private JButton btn;
	private JFrame jf;
	public Login_closeListener(JFrame jf){
		this.jf = jf;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		jf.dispose();
	}

}