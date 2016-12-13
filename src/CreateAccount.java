import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CreateAccount extends JFrame {

	private JPanel contentPane;
	private JTextField txt_username;
	private JTextField txt_email;
	private JPasswordField txt_password;

	private static String id, username, password, email;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					CreateAccount frame = new CreateAccount();
					frame.setVisible(true);
				} 
				
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateAccount() {
		setResizable(false);
		setTitle("Create Account");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 281, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_createAccountTitle = new JLabel("Create Account");
		lbl_createAccountTitle.setBounds(56, 11, 154, 25);
		lbl_createAccountTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lbl_createAccountTitle);
		
		JLabel lbl_username = new JLabel("Username:");
		lbl_username.setBounds(15, 79, 66, 14);
		contentPane.add(lbl_username);
		
		JLabel lbl_password = new JLabel("Password:");
		lbl_password.setBounds(17, 110, 64, 14);
		contentPane.add(lbl_password);
		
		JLabel lbl_email = new JLabel("E-Mail:");
		lbl_email.setBounds(35, 141, 46, 14);
		contentPane.add(lbl_email);
		
		txt_username = new JTextField();
		txt_username.setBounds(91, 76, 113, 20);
		contentPane.add(txt_username);
		txt_username.setColumns(10);
		
		txt_password = new JPasswordField();
		txt_password.setBounds(91, 107, 113, 20);
		contentPane.add(txt_password);
		
		txt_email = new JTextField();
		txt_email.setBounds(91, 138, 113, 20);
		contentPane.add(txt_email);
		txt_email.setColumns(10);
		
		JButton btn_submit = new JButton("Submit");
		btn_submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!txt_username.getText().equals("") && !txt_password.getText().equals("") && !txt_email.getText().equals(""))
				{
					username = txt_username.getText();
					password = txt_password.getText();
					email = txt_email.getText();
					if(containsInfo(Bank.accountList, username, email))
					{
						Bank.errorPopup("Username or Email already exist.");
						System.out.println("Username or Email already exists.");
					}
					else
					{
						String lastId = "";
						for(int i = 0; i < Bank.accountList.size(); i++)
						{
							lastId = Bank.accountList.get(i).id;
						}
						
						lastId = String.valueOf(Integer.parseInt(lastId) + 1);
						lastId = Account.makeToId(lastId);
						Account obj = new Account(lastId, username, password, email, 0);
						Bank.accountList.add(obj);
						Account.arrayToTxt();
						
						setVisible(false);
					}
				}
				
				else
				{
					Bank.errorPopup("Please fill out all fields.");
					System.out.println("Please fill out all fields.");
				}
			}
		});
		btn_submit.setBounds(91, 192, 89, 23);
		contentPane.add(btn_submit);
	}
	
	public static boolean containsInfo(ArrayList<Account> accObj, String un, String em) {
	    for(Account o : accObj) {
	        if(o != null && o.username.equals(un) || o.email.equals(em)) {
	            return true;
	        }
	    }
	    return false;
	}
}
