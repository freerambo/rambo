package org.fiap.storage2;

public class TestUpdateCommandApp {

	public static void main(String[] args) throws Exception {

		PostgresDB.postCommand(new PointSetTreeRootTryUpdate("http://gw.hogehoge/room4",true));
		//PostgresDB.postCommand(new PointSetTreeUpdate("http://gw.hogehoge/room3/light1","http://gw.hogehoge/room3",true));
		//PostgresDB.postCommand(new PointValueUpdate("http://gw.hogehoge/room3/light1","2009-11-16T01:00:00.0000000+09:00",null,"true"));
	}

}
