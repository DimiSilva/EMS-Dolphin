package model.helpers;

import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import model.exceptions.EncryptionException;

public class Utils {
	public static String hashString(String stringToHash) throws EncryptionException {
		try {
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			
			KeySpec spec = new PBEKeySpec(stringToHash.toCharArray(), salt, 65536, 128);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			
			byte[] hash = factory.generateSecret(spec).getEncoded();
			return hash.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new EncryptionException(e.getMessage());
		}
	}
}
