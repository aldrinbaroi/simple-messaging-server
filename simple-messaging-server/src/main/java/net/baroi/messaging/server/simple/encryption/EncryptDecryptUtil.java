package net.baroi.messaging.server.simple.encryption;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * 
 * @author Aldrin Baroi
 *
 */
public class EncryptDecryptUtil {

	private static final String ENCRYPT = "encrypt";
	private static final String DECRYPT = "decrypt";

	public static void main(String[] args) {
		if ((args.length == 3) && (args[0].equalsIgnoreCase(ENCRYPT) || args[0].equalsIgnoreCase(DECRYPT))) {
			String command = args[0];
			String password = args[1];
			String text = args[2];
			StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
			enc.setPassword(password);
			if (command.equals(ENCRYPT)) {
				System.out.println();
				System.out.println("Command            : " + command);
				System.out.println("Password           : " + password);
				System.out.println("Provided plain text: " + text);
				try {
					System.out.println("Encrypted text     : " + enc.encrypt(text));
				} catch (Exception e) {
					System.out.println("ERROR: " + e.getMessage());
				}
				System.out.println();
			} else if (command.equals(DECRYPT)) {
				System.out.println();
				System.out.println("Command                : " + command);
				System.out.println("Password               : " + password);
				System.out.println("Provided encrypted text: " + text);
				try {
					System.out.println("Decrypted plain text   : " + enc.decrypt(text));
				} catch (Exception e) {
					System.out.println("ERROR: " + e.getMessage());
				}
				System.out.println();
			}
		} else {
			System.out.println();
			System.out.println("Usage: encryptor COMMAND PASSWORD TEXT");
			System.out.println("");
			System.out.println("    COMMAND : [encrypt|decrypt]");
			System.out.println("    PASSWORD: Password to be used for encryption & decryption");
			System.out.println("    TEXT    : Text to be encrypted or decrypted");
			System.out.println();
		}
	}
}
