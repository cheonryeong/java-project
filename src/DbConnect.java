import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	public static Connection makeConnection() {
		String url = "jdbc:mysql://localhost/m?characterEncoding=UTF-8&serverTimezone=UTC";
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, "root", "k1234");
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
		return con;
	}
}