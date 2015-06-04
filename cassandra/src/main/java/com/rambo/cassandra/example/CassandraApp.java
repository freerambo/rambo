package com.rambo.cassandra.example;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.CassandraRepository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.rambo.cassandra.entity.Person;

public class CassandraApp {
//	CassandraRepository cassandra =null;
	private static final Logger LOG = LoggerFactory
			.getLogger(CassandraApp.class);

	private static Cluster cluster;
	private static Session session;

	public static void main(String[] args) {

		cluster = Cluster.builder().withCredentials("zhuyuanbo", "zhuyuanbo")
				.addContactPoint("155.69.214.102")
				//.addContactPoint("172.21.77.197")
//				.addContactPoint("155.69.218.214")
				.build();
		session = cluster.connect("demo1");

		CassandraOperations cassandraOps = new CassandraTemplate(session);
		
		System.out.println("---------------start--------------");
		List<Person> p =  cassandraOps.selectAll(Person.class);
		LOG.info("output: "+p.toString());
		System.out.println(p.toString());

		System.out.println("---------------end--------------");

//		cassandraOps.insert(new Person("1234567890", "hello", 27));
//		for(int i = 100; i > 0; i--){
//			cassandraOps.insert(new Person("Name" + new Random().nextInt() , "hello" +  new Random().nextInt(),  10 + new Random().nextInt(50)));
//
//		}
//		Person p = cassandraOps.selectOne("111111111111", Person.class);
//		List<Person> p =  cassandraOps.selectAll(Person.class);
//		cassandraOps.selectOne(arg0, arg1)
		
//		cassandraOps.select(QueryBuilder
//		        .select()
//		        .all().from("person")
//		        , Person.class);
//		.from("person").where().and(eq("name","hello"))
//		LOG.info(p.toString());
//		cassandraOps.queryForObject(arg0, arg1)
//		p = cassandraOps.select(QueryBuilder
//		        .select()
//		        .all().from("person")
//		        , Person.class);
//		LOG.info(p.toString());
//		Person p1 = cassandraOps.selectOneById(Person.class, "1234567890");
//		LOG.info(p1.toString());
//		 p1 = cassandraOps.selectOne("select * from person where name = 'hello'", Person.class);
//		 LOG.info(p1.toString());
		 
//		 p = cassandraOps.select("select * from person where name = 'Name713334756'", Person.class);
//		 LOG.info(p.toString());
//		 cassandraOps.delete(p1);
//		 p = cassandraOps.select("select * from person", Person.class);
//		 LOG.info(p.toString());

//		 session.closeAsync();
//		 cassandraOps.exec
//		Select s = QueryBuilder.select().from("person");
//		s.where(QueryBuilder.eq("id", "1234567890"));
//
//		LOG.info(cassandraOps.queryForObject(s, Person.class).getId());

//		cassandraOps.truncate("person");

	}
	
	/**
	 * ongoing
	 * beginning of each month
	 * what tasks you are working on 
	 * single line diagram 
	 * documentation confidential 
	 * 
	 * distribute information non disclosure
	 * transimition logs 
	 * 
	 * 480 kw peak power  dc ac 
	 * 
	 * 50 kw  middle need battery --- 400kwh
	 *
	 * July 20th 
	 * com.datastax.driver.core.TransportException: [/172.21.77.197:9042] Cannot connect
	 * 
	 * */
}