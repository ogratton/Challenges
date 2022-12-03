package day05;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * See task description file for explanation
 * @author Oliver Gratton
 *
 */
public class MD5Password 
{	
	private int passlength = 8;
	
	protected String crack1(String key)
	{
		String password = "";
		
		for (int i = 0; password.length() < passlength; i++)
		{
			String cand = md5(key + i);
			if (cand.substring(0, 5).equals("00000"))
			{
				password += cand.charAt(5);
				System.out.println(password);
			}
			
			if (i%1000000==0)
			{
				System.out.println(">> " + i);
			}
		}
		
		return password;
	}
	
	protected char[] crack2(String key)
	{
		char[] password = new char[passlength];
		int progress = 0;
		
		for (int i = 0; progress < passlength; i++)
		{
			String cand = md5(key + i);
			if (cand.substring(0, 5).equals("00000"))
			{
				try
				{
					int index = Character.getNumericValue(cand.charAt(5));
					if (password[index]==0 && index < passlength) // only take first valid result
					{
						password[index] = cand.charAt(6);
						progress++;
						System.out.println(cand);
					}
					
				}
				catch (Exception e)
				{
					// Ignore
				}
			}
			
			if (i%1000000==0)
			{
//				System.out.println(">> " + i);
			}
		}
		
		return password;
	}
	
	protected static String md5(String passwordToHash)
	{
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Add password bytes to digest
			md.update(passwordToHash.getBytes());
			//Get the hash's bytes 
			byte[] bytes = md.digest();
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			//Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}

		return generatedPassword;
		
	}
	
	public static void main(String[] args) 
	{		
//		System.out.println(md5("abc3231929"));
		MD5Password m = new MD5Password();
//		System.out.println(m.crack1("uqwqemis"));
		char[] password = m.crack2("uqwqemis");
		
		for (int i = 0; i < password.length; i++)
		{
			System.out.print(password[i]);
		}
		
		
	}
}
