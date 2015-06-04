
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
/**
 * @author rambo
 */
public class App {

	public static void main(String[] args) {
		Cluster cluster = Cluster.builder()
				.withCredentials("zhuyuanbo", "zhuyuanbo")
				.addContactPoint("155.69.214.102")
				.addContactPoint("172.21.77.197").addContactPoint("155.69.218.214").build();
		create(cluster);
	}

	public static void create(Cluster cluster) {

		Session session = cluster.connect();
		String cqlStatement = "CREATE KEYSPACE myfirstcassandradb WITH "
				+ "replication = {'class':'SimpleStrategy','replication_factor':1}";
		session.execute(cqlStatement);

		String cqlStatement2 = "CREATE TABLE myfirstcassandradb.users ("
				+ " user_name varchar PRIMARY KEY," + " password varchar "
				+ ");";
		session.execute(cqlStatement2);

		System.out.println("Done");
		System.exit(0);
	}

	static void read(Cluster cluster) {
		Session session = cluster.connect();
		String cqlStatement = "SELECT * FROM local";
		for (Row row : session.execute(cqlStatement)) {
			System.out.println(row.toString());
		}
	}

	static void crud(Cluster cluster) {

		Session session = cluster.connect();
		// for all three it works the same way (as a note the 'system' keyspace
		// cant
		// be modified by users so below im using a keyspace name
		// 'exampkeyspace' and
		// a table (or columnfamily) called users

		String cqlStatementC = "INSERT INTO exampkeyspace.users (username, password) "
				+ "VALUES ('Serenity', 'fa3dfQefx')";

		String cqlStatementU = "UPDATE exampkeyspace.users"
				+ "SET password = 'zzaEcvAf32hla',"
				+ "WHERE username = 'Serenity';";

		String cqlStatementD = "DELETE FROM exampkeyspace.users "
				+ "WHERE username = 'Serenity';";

		session.execute(cqlStatementC); // interchangeable, put any of the
										// statements u wish.
	}
}