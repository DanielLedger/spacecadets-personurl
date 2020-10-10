package com.github.DanL.UsernameGet;

import java.io.Console;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		Console cmdInst = System.console(); //This returns null if you aren't using system console (thanks Eclipse).
		if (cmdInst == null) {
			System.out.println("This code will only work when run from system console! Please use that instead. Thanks!");
			System.exit(1);
		}
		System.out.println("Hello! Please enter the user ID of who you wish to look up. For example, I'm 'dcl'.");
		String resp = cmdInst.readLine();
		String url = "https://www.ecs.soton.ac.uk/people/" + resp;
		System.out.println("Fetching " + url + "...");
		String page = "err";
		try {
			page = NetUtils.getPage(url);
		}
		catch (IOException ex){
			//Something went wrong...
			ex.printStackTrace();
			System.out.println("Whoops! That didn't work. The error above should say why in a bit more detail.");
		}
		String name = Main.getNameFromPage(page);
		if (name.contentEquals("People")) {
			//The ID we were given is invalid.
			System.out.println("The ID you gave was invalid! This may also be because the person does not wish to be publically searchable. Goodbye.");
			System.exit(1); //Use a non-zero exit just in case we were getting parsed by something (not that that is overly likely)
		}
		System.out.println("This person is called " + name + ". Do you want me to try and find out as much about them as I can? (Y/N)");
		String strResp = cmdInst.readLine();
		if (strResp.equalsIgnoreCase("y")) {
			//TODO: Add features.
		}
		else if (strResp.equalsIgnoreCase("n")){
			System.out.println("Goodbye!");
			System.exit(0);
		}
		else {
			System.out.println("Interpreting invalid answer as 'no'. Goodbye!");
			System.exit(0);
		}
		//Since the other two branches end in system.exit, I can put code down here.
	}
	
	/**
	 * Attempts to get a user's name from the page we've been given.
	 * 
	 * This can work on one of two formats:
	 * 1) https://www.ecs.soton.ac.uk/people
	 * 2) (TODO) https://secure.ecs.soton.ac.uk/people
	 * @param pageContent - The contents of the webpage.
	 * @return - The requested name.
	 */
	static String getNameFromPage(String pageContent) {
		//Format 1: look between "<title>" and "|". In a sickly one-liner too!
		//Requires \\ because the | symbol is a valid regex, but \| is an invalid escape sequence.
		return pageContent.split("<title>")[1].split("\\|")[0].trim();
	}

}
