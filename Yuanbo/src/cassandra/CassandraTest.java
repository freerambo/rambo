package cassandra;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CassandraTest {

	static {
		
	}
	static Connection con;


	public static void main(String[] args) {

		try {
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con = DriverManager
					.getConnection("jdbc:cassandra://ict.eri.ntu.edu.sg:9042/data_collection","zhuyuanbo","zhuyuanbo");

			String query = "select id,date_time, energy_consumption from spms_hru limit ?";
			PreparedStatement statement = con.prepareStatement(query);

			statement.setLong(1, 10);

			ResultSet results = statement.executeQuery();

			while(results.next()){
				System.out.format("%s-%s-%d \n", results.getString(1),results.getString(2),results.getDouble(3));
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updatetest(){
		try {
			con = DriverManager
					.getConnection("jdbc:cassandra://host1--host2--host3:9160/keyspace1?primarydc=DC1&backupdc=DC2&consistency=QUORUM");

			String query = "UPDATE Test SET a=?, b=? WHERE KEY=?";
			PreparedStatement statement = con.prepareStatement(query);

			statement.setLong(1, 100);
			statement.setLong(2, 1000);
			statement.setString(3, "key0");

			statement.executeUpdate();

			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}