package jhd.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Constant {
	public static final String URL = "https://chinabluemix.itsm.unisysedge.cn/arsys/shared/loggedout.jsp";

	public static final String CONFIGPATH = "config.properties";
	public static final String USERNAME_STRING = "username";
	public static final String PASSWORD_STRING = "password";
	public static final String FIRSTRUN_STRING = "firstrun";
	public static String USERNAME = "";
	public static String PASSWORD = "";
	public static boolean FIRSTRUN = false;

	public static void readProperties() {

		File file = new File(CONFIGPATH);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Properties p = new Properties();
		try {
			FileInputStream fileInputStream = new FileInputStream(file);

			p.load(fileInputStream);
			FIRSTRUN = Boolean.parseBoolean(p.getProperty(FIRSTRUN_STRING, "true"));
			if (FIRSTRUN) {
				System.out.println("It's your first run! Pls set your Remedy username & password!");
				reSetConfig();
				// System.exit(0);
				fileInputStream.close();
				readProperties();
			} else {
				USERNAME = p.getProperty(USERNAME_STRING);
				PASSWORD = p.getProperty(PASSWORD_STRING);
				// System.out.println(FIRSTRUN);
				// System.out.println(USERNAME);
				// System.out.println(PASSWORD);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeProperties() {

		File file = new File(CONFIGPATH);

		Properties p = new Properties();
		try {
			FileInputStream input = new FileInputStream(file);
			p.load(input);
			input.close();

			FileOutputStream output = new FileOutputStream(file);
			p.put(FIRSTRUN_STRING, "false");
			// p.setProperty("phone22", "123456");
			p.put(USERNAME_STRING, USERNAME);
			p.put(PASSWORD_STRING, PASSWORD);

			p.store(output, "latest uodate");
			output.close();
			System.out.println("Your info has been saved!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reSetConfig() {

		System.out.println("Username:");
		Scanner s = new Scanner(System.in);
		USERNAME = s.next();
		System.out.println("Password:");
		PASSWORD = s.next();
		writeProperties();
	}

}
