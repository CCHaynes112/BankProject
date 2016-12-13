import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Bank extends JFrame 
{

	private JPanel contentPane;

	
	protected static ArrayList<Account> accountList = new ArrayList<Account>();
	private static JTextField textField_WithdrawAmt;
	private static JTextField textField_DepositAmt;
	
	private static JLabel lbl_CurrentFundsDeposit, lbl_CurrentFundsWithdraw;
	
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
					Bank frame = new Bank();
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
	public Bank() 
	{
		setResizable(false);
		setBackground(Color.LIGHT_GRAY);
		setTitle("Account Overview");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 463);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 698, 434);
		contentPane.add(tabbedPane);
		
		JPanel panel_Overview = new JPanel();
		panel_Overview.setBackground(SystemColor.control);
		tabbedPane.addTab("Overview", null, panel_Overview, null);
		tabbedPane.setForegroundAt(0, Color.BLACK);
		panel_Overview.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lbl_Id = new JLabel("ID Number: " + Main.accountLogged.id);
		lbl_Id.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Overview.add(lbl_Id);
		
		JLabel lbl_UserName = new JLabel("Username: " + Main.accountLogged.username);
		lbl_UserName.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Overview.add(lbl_UserName);
		
		JLabel lbl_Email = new JLabel("Email: " + Main.accountLogged.email);
		lbl_Email.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Overview.add(lbl_Email);
		
		JLabel lbl_Date = new JLabel("Date Created: " + Main.accountLogged.dateVar);
		lbl_Date.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Overview.add(lbl_Date);
		
		JPanel panel_Withdraw = new JPanel();
		tabbedPane.addTab("Withdraw", null, panel_Withdraw, null);
		panel_Withdraw.setLayout(new MigLayout("", "[270px][330px][270px]", "[94px][60px][30px][50px][50px]"));
		
		lbl_CurrentFundsWithdraw = new JLabel("Current Funds: $" + Main.accountLogged.funds);
		lbl_CurrentFundsWithdraw.setVerticalAlignment(SwingConstants.TOP);
		lbl_CurrentFundsWithdraw.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Withdraw.add(lbl_CurrentFundsWithdraw, "cell 1 0,grow");
		
		JTextPane txtpn_WithdrawPrompt = new JTextPane();
		txtpn_WithdrawPrompt.setBackground(SystemColor.control);
		txtpn_WithdrawPrompt.setEditable(false);
		txtpn_WithdrawPrompt.setText("Please enter the amount below that you would like to withdraw.");
		panel_Withdraw.add(txtpn_WithdrawPrompt, "flowx,cell 1 1,grow");
		
		JLabel lbl_Withdraw = new JLabel("Withdraw: ");
		lbl_Withdraw.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_Withdraw.add(lbl_Withdraw, "cell 0 2,grow");
		
		textField_WithdrawAmt = new JTextField();
		panel_Withdraw.add(textField_WithdrawAmt, "cell 1 2,growx");
		textField_WithdrawAmt.setColumns(10);
		
		JButton btn_Withdraw = new JButton("Withdraw");
		btn_Withdraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try
				{
					int withdrawAmt = Integer.parseInt(textField_WithdrawAmt.getText().toString());
					
					if(withdrawAmt > Main.accountLogged.funds)
					{
						errorPopup("You don't have the requested funds.");
						System.out.println("You don't have the requested funds.");
					}
					
					else if(withdrawAmt <= Main.accountLogged.funds)
					{
						Main.accountLogged.funds = Main.accountLogged.funds - withdrawAmt;
						updateDisplay();
						Account.arrayToTxt();
					}
					
					else
					{
						errorPopup("An error occured.");
						System.out.println("An Error Occured");
					}
				}
				
				catch(Exception e1)
				{
					errorPopup("An error occured.");
					e1.printStackTrace();
				}
			}
		});
		panel_Withdraw.add(btn_Withdraw, "cell 1 4,alignx center,aligny center");
		
		JPanel panel_Deposit = new JPanel();
		tabbedPane.addTab("Deposit", null, panel_Deposit, null);
		panel_Deposit.setLayout(new MigLayout("", "[270px][330px][270px]", "[94px][60px][30px][50px][50px]"));
		
		lbl_CurrentFundsDeposit = new JLabel("Current Funds: $" + Main.accountLogged.funds);
		lbl_CurrentFundsDeposit.setVerticalAlignment(SwingConstants.TOP);
		lbl_CurrentFundsDeposit.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Deposit.add(lbl_CurrentFundsDeposit, "cell 1 0,alignx center,aligny top");
		
		JTextPane txtpn_DepositPrompt = new JTextPane();
		txtpn_DepositPrompt.setText("Pleae enter the amount below that you would like to deposit.");
		txtpn_DepositPrompt.setEditable(false);
		txtpn_DepositPrompt.setBackground(SystemColor.menu);
		panel_Deposit.add(txtpn_DepositPrompt, "cell 1 1,grow");
		
		JLabel lbl_Deposit = new JLabel("Deposit:");
		lbl_Deposit.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_Deposit.add(lbl_Deposit, "cell 0 2,alignx trailing");
		
		textField_DepositAmt = new JTextField();
		textField_DepositAmt.setColumns(10);
		panel_Deposit.add(textField_DepositAmt, "cell 1 2,growx");
		
		JButton btn_Deposit = new JButton("Deposit");
		btn_Deposit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try
				{
					int depositAmt = Integer.parseInt(textField_DepositAmt.getText().toString());

					Main.accountLogged.funds = Main.accountLogged.funds + depositAmt;
					updateDisplay();
					Account.arrayToTxt();
				}
				
				catch(Exception e1)
				{
					errorPopup("An error occured.");
					e1.printStackTrace();
				}
			}
		});
		panel_Deposit.add(btn_Deposit, "cell 1 4,alignx center,aligny center");
		
		JPanel panel_Logout = new JPanel();
		tabbedPane.addTab("Logout", null, panel_Logout, null);
		panel_Logout.setLayout(new MigLayout("", "[50px][50px][50px][50px][50px][200px][50px][50px][50px][50px][50px]", "[50px][50px][50px][50px][50px][50px]"));
		
		JButton btn_Logout = new JButton("Logout");
		btn_Logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Main.accountLogged = null;
				dispose();
				Main main = new Main();
				main.setVisible(true);
				
			}
		});
		
		JTextPane txtpnAreYouSure = new JTextPane();
		txtpnAreYouSure.setBackground(SystemColor.control);
		txtpnAreYouSure.setText("Are you sure you would like to logout?");
		txtpnAreYouSure.setEditable(false);
		panel_Logout.add(txtpnAreYouSure, "cell 5 2,grow");
		panel_Logout.add(btn_Logout, "cell 5 4,growx,aligny center");
	}
	
	private static void updateDisplay()
	{
		lbl_CurrentFundsWithdraw.setText("Current funds: $" + String.valueOf(Main.accountLogged.funds));
		lbl_CurrentFundsDeposit.setText("Current funds: $" + String.valueOf(Main.accountLogged.funds));
		textField_WithdrawAmt.setText("");
		textField_DepositAmt.setText("");
	}
	
	protected static void errorPopup(String s)
	{
		JPanel panel = new JPanel();
		JOptionPane.showMessageDialog(panel, s, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
