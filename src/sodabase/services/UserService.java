package sodabase.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class UserService {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnectionService dbService = null;

	public UserService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean useApplicationLogins() {
		return true;
	}
	
	public boolean login(String username, String password) {
		//TODO: Complete this method.
		Connection con = this.dbService.getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "SELECT PasswordSalt FROM User WHERE PASSWORD = " + password;
			ResultSet rs = stmt.executeQuery(query);
			String currentHash = hashPassword(rs.getBytes(0), password);
			Statement stmt2 = con.createStatement();
			String query2 = "SELECT PasswordHash FROM USER";
			ResultSet rs2 = stmt.executeQuery(query2);
			for(int i=0;i<rs2.getFetchSize();i++){
			if(rs2.next()){
				if(rs2.getString(i) == currentHash){
					return true;
				}
			}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}

	public boolean register(String username, String password) {
		//TODO: Task 6
		byte[] salt = getNewSalt();
		String pHash = hashPassword(salt, password);
		CallableStatement cs = null;
		try {
			cs = dbService.getConnection().prepareCall("{call Register(?,?,?)}");
			
			if(username!=null){
				cs.setString(1, username);
				cs.setString(2,salt.toString());
				cs.setString(3, password);
			
			}else {
				return false;
			}
			
			return cs.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

}
