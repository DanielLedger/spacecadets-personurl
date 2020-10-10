package com.github.DanL.UsernameGet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class NetUtils {
	
	/**
	 * Stores authentication credentials (if required) for basic authentication.
	 * @param user - Username
	 * @param password - Password
	 */
	public static class AuthCreds{
		
		private String u;
		private String p;
		
		public AuthCreds(String user, String password) {
			u = user; p = password;
		}
		
		/**
		 * Gets the username.
		 * @return - The username.
		 */
		public String getUser() {
			return u;
		}
		
		/**
		 * Gets the password. Obviously don't do anything like
		 * System.out.println(AuthCreds{@link #getPass()}.
		 * @return The password.
		 */
		public String getPass() {
			return p;
		}
	}
	
	public class NeedsAuthentication extends Throwable{
		//Eclipse complained the class didn't have one, who am I to argue?
		private static final long serialVersionUID = -4894811097427033744L;
	}
	/**
	 * Gets a page using no authentication. Throws any encountered exceptions.
	 * 
	 * See {@link NetUtils#getPage(String, AuthCreds)} for more detail.
	 * 
	 * @param url - The url to get
	 * @return The page fetched.
	 * @throws IOException 
	 */
	public static String getPage(String url) throws IOException {
		return NetUtils.getPage(url, null);
	}
	
	/**
	 * Gets a page, attempting to authenticate with the given credentials (if not null).
	 * 
	 * Throws any found exceptions.
	 * 
	 * @param url - The url to get.
	 * @param userPass - Optional credentials to log in to secured pages.
	 * @return - The page fetched.
	 * @throws IOException 
	 */
	public static String getPage(String url, AuthCreds userPass) throws IOException {
		URL target = new URL(url);
		//URLConnection httpCon = target.openConnection();
		InputStream dataSource = target.openStream(); //Apparently this works (?)
		//Get the number of bytes.
		int len = dataSource.available();
		byte[] readInto = new byte[len];
		dataSource.read(readInto);
		//If we get weird decoding errors, that's probably the fault of this line.
		String result = new String(readInto);
		return result;
	}
}
