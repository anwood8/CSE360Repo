package LoginPage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import com.opencsv.CSVReader;


public class Logic {
	
	// simple array of usernames/passwords for prototype
	String[] username = {"anwood8", "alphaMale"};
	String[] password = {"123", "password"};	
	
	
	public int verify(String uName, String pWord){
		String line = "";
		String splitBy = ",";
		boolean bool = false;
		
		// logic for login entry
		try {
			BufferedReader br = new BufferedReader(new FileReader("UserData/users.csv"));
			
		
		while((line = br.readLine()) != null) {
			String[] userInfo = line.split(splitBy);
			userInfo[0] = userInfo[0].strip();
			userInfo[1] = userInfo[1].strip();

			if(uName.equals(userInfo[0]) && pWord.equals(userInfo[1])) {
				bool = true;
				break;
			}
			
		}
		
		}catch(IOException e) {
			e.printStackTrace();
		}
		if(bool == true) {
			System.out.println("Login Successful");
			return 1;
		}
		else {
			System.out.println("Login attempt unsuccessful");
			return 0;
		}
		
	}

}
