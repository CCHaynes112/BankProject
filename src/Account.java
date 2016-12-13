import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.*;

public class Account 
{
	protected String id, username, password, email, dateVar;
	protected int funds;
	
	static Path file = Paths.get("assets\\Account List.txt");
	Date currentDate = new Date();
	
	protected Account(String id, String u, String p, String e, int f)
	{
		this.id = id;
		this.username = u;
		this.password = p;
		this.email = e;
		dateVar = String.valueOf(currentDate);
		this.funds = f;
	}
	
	protected static void createAccountList()
	{
		String s = "000,NullName,NullPass,NullEmail,NullDate,0                                        " + System.getProperty("line.separator");
		byte[] data = s.getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(data);
		final int NUMRECS = 1000;
		
		try
		{
			OutputStream output = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
			
			for(int count = 0; count < NUMRECS; ++count)
				writer.write(s, 0, s.length());
				writer.close();
		}
		catch(Exception e)
		{
			System.out.println("Error message: " + e);
		}
	}
	
	protected static void arrayToTxt()
	{
		int linePos = 0;
		
		for(int i = 0; i < Bank.accountList.size(); i++)
		{
			String s = Bank.accountList.get(i).id + "," + Bank.accountList.get(i).username + "," + Bank.accountList.get(i).password +
					"," + Bank.accountList.get(i).email + "," + Bank.accountList.get(i).dateVar + "," + Bank.accountList.get(i).funds;
			
			if(s.length() < 82)
			{
				for(int j = s.length(); j < 82; j++)
				{
					s = s + ' ';
				}
			}
			
			if(s.length() > 82)
			{
				for(int k = 0; k > 82; k--)
				{
					s = s.substring(0, s.length()-1);
				}
			}
			
			s = s + System.getProperty("line.separator");
			
			
			final int RECSIZE = s.length();
			byte[] data = s.getBytes();
			ByteBuffer buffer = ByteBuffer.wrap(data);
			FileChannel fc = null;
			
			
			try
			{
				fc = (FileChannel)Files.newByteChannel(file, READ, WRITE);
				fc.position(linePos * RECSIZE);
				fc.write(buffer);
				fc.close();
			}
			
			catch(Exception e)
			{
				e.getMessage();
				e.printStackTrace();
			}
			
			linePos = linePos + 1;
		}
	}
	
	protected static void txtToArray()
	{
		String[] array = new String[5];
		String s = "";
		String delimiter = ",";
		
		String idLocal;
		String name;
		String pass;
		String email;
		String date;
		int fund;
		String fundsStr;
		
		try
		{
			InputStream input = new BufferedInputStream(Files.newInputStream(file));
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			s = reader.readLine();
			while(s != null)
			{
				array = s.split(delimiter);
				idLocal = array[0];
				
				if(!idLocal.equals("000"))
				{
					name = array[1];
					pass = array[2];
					email = array[3];
					date = array[4];
					fundsStr = array[5];
					
					StringBuilder sb = new StringBuilder(fundsStr);

					for(int i = fundsStr.length()-1; i > 0; i--)
					{
						if(fundsStr.charAt(i) == ' ')
						{
							sb.deleteCharAt(i);
						}
					}
					
					fundsStr = sb.toString();
					
					fund = Integer.parseInt(fundsStr);
					
					if(!checkArrayContents(idLocal))
					{
						Account acc = new Account(idLocal, name, pass, email, fund);
						Bank.accountList.add(acc);
						System.out.println("Read account from txt, Added to array: " + idLocal + " " + name + " " + pass + " " + email + " " + date + " " + fund);
					}
				}
				s = reader.readLine();
			}
			reader.close();
		}
		
		catch(Exception e)
		{
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	protected static String makeToId(String s)
	{
		if(s.length() == 1)
		{
			s = "00" + s;
		}
		else if(s.length() == 2)
		{
			s = "0" + s;
		}
		return s;
	}
	
	protected static boolean checkArrayContents(String id)
	{
		
		for(int i = 0; i < Bank.accountList.size(); i++)
		{
			if(Bank.accountList.get(i).id == id)
			{
				return true;
			}
		}
		return false;
	}
}
