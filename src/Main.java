import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTree;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txt_username;
	private JPasswordField txt_password;
	private JLabel lbl_loginTitle;
	
	protected static Account accountLogged; 
	
	protected static CreateAccount createAccount = new CreateAccount();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{	
		//Account.createAccountList();
		Account.txtToArray();
		
		//UI Things:
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Main frame = new Main();
					frame.setVisible(true);
				} 
				
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		lbl_loginTitle = new JLabel("Login");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lbl_loginTitle, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lbl_loginTitle, 181, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lbl_loginTitle, 35, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lbl_loginTitle, -188, SpringLayout.EAST, contentPane);
		lbl_loginTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_loginTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lbl_loginTitle);
		
		JLabel lbl_username = new JLabel("Username:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lbl_username, 68, SpringLayout.NORTH, contentPane);
		contentPane.add(lbl_username);
		
		JLabel lbl_password = new JLabel("Password:");
		sl_contentPane.putConstraint(SpringLayout.EAST, lbl_password, 0, SpringLayout.EAST, lbl_username);
		contentPane.add(lbl_password);
		
		txt_username = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.EAST, lbl_username, -6, SpringLayout.WEST, txt_username);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txt_username, 27, SpringLayout.SOUTH, lbl_loginTitle);
		sl_contentPane.putConstraint(SpringLayout.WEST, txt_username, 68, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txt_username, -230, SpringLayout.EAST, contentPane);
		contentPane.add(txt_username);
		txt_username.setColumns(10);
		
		txt_password = new JPasswordField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, lbl_password, 3, SpringLayout.NORTH, txt_password);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txt_password, 20, SpringLayout.SOUTH, txt_username);
		sl_contentPane.putConstraint(SpringLayout.WEST, txt_password, 0, SpringLayout.WEST, txt_username);
		sl_contentPane.putConstraint(SpringLayout.EAST, txt_password, -230, SpringLayout.EAST, contentPane);
		contentPane.add(txt_password);
		
		JButton btn_createAccount = new JButton("Create Account");
		btn_createAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(!createAccount.isShowing())
				{
					createAccount.setVisible(true);
				}
				else
				{
					System.out.println("Window already open.");
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btn_createAccount, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btn_createAccount, -10, SpringLayout.EAST, contentPane);
		contentPane.add(btn_createAccount);
		
		JButton btn_login = new JButton("Login");
		btn_login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try
				{
					for(int i = 0; i < Bank.accountList.size(); i++)
					{
						Bank.accountList.get(i);
						if(txt_username.getText().equals(Bank.accountList.get(i).username) && txt_password.getText().equals(Bank.accountList.get(i).password))
						{
							System.out.println("Account Found");
							accountLogged = Bank.accountList.get(i);
							Bank bank = new Bank();
							setVisible(false);
							bank.setVisible(true);
							System.out.println("Retrieved username: " + accountLogged.username);
							break;
						}
					}
					if(accountLogged == null)
					{
						Bank.errorPopup("Accout not found.");
						System.out.println("Not found.");
					}
				}
				
				catch(Exception e1)
				{
					Bank.errorPopup("An error occured.");
					e1.getMessage();
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btn_login, 22, SpringLayout.SOUTH, txt_password);
		sl_contentPane.putConstraint(SpringLayout.WEST, btn_login, 101, SpringLayout.WEST, contentPane);
		contentPane.add(btn_login);
	}
}
