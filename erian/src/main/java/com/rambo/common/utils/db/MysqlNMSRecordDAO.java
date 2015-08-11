package com.rambo.common.utils.db;

import java.sql.*;
import java.util.*;

import com.google.common.collect.Sets;
import com.rambo.erian.entity.NMSRecord;
import com.rambo.erian.entity.NmsKwhImpDay;
import com.rambo.erian.entity.NmsKwhImpHour;

public class MysqlNMSRecordDAO implements NMSRecordDAO {

	private final String INSERT_AN_NMS_DAY = "INSERT ignore INTO nms_kwh_imp_day (id, date_time, value, unit,meter_id, std,max,dt_max,min,dt_min) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final String INSERT_AN_NMS_HOUR = "INSERT ignore INTO nms_kwh_imp_hour (id, date_time, value, unit,meter_id) VALUES (?, ?, ?, ?, ?)";

	private final String INSERT_AN_NMSRecord = "INSERT ignore INTO nms_hour_kwh_imp_aft_26_mar_2015 (provider, meter_id, unit, start_time, end_time, tz, spi, no, value, st,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	// private final String INSERT_AN_NMSRecord =
	// "INSERT INTO nms_raw_01 (provider, meter_id, unit, start_time, end_time, tz, spi, no, value, st) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// private final String QUERY_DAY_NOT_CORRECT
	// ="SELECT * FROM ict_data_tran.nms_day_results   where date_time = '2014-12-12%'";

	private final String QUERY_DAY_NOT_CORRECT = "SELECT * FROM ict_data_tran.nms_day_results   where date_time not like '%00:00:00'";

	private final String QUERY_DAY_CORRECT = "SELECT date_time,sum(nanyang),sum(nieo) FROM ict_data_tran.nms_day_results  where date_time  like ?";

	private final String DELETE_DAY_NOT_CORRECT = "delete FROM ict_data_tran.nms_day_results   where date_time not like '%00:00:00'";

	// private final String INSERT_AN_NMSRecord =
	// "INSERT INTO nms_raw_dec (provider, meter_id, unit, start_time, end_time, tz, spi, no, value, st) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// private final String INSERT_AN_NMSRecord =
	// "INSERT INTO nms_raw (provider, meter_id, unit, start_time, end_time, tz, spi, no, value, st) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String GET_AN_NMSRecord = "select id from nms_raw where meter_id= ? and unit =? and start_time = ? and no = ?";
	private final String DEL_AN_NMSRecord = "delete from NMSRecord where id = ?";
	private final String UPDATE_AN_NMSRecord = "update NMSRecord set name=?,course=? where id=?";
	private final String GET_ALL_NMSRecordS = "select distinct(meter_it) from NMSRecord order by id";

	private final String SYN_ALL_HOUR = "INSERT IGNORE INTO nms_hour_results SELECT date_time, nanyang, nieo  FROM v_export_nms_hour_view  WHERE date_time > ?";

	private final String SYN_ALL_DAY = "INSERT IGNORE INTO nms_day_results SELECT date_time, nanyang, nieo  FROM v_export_nms_day_view  WHERE date_time > ?";

	public boolean createNMSRecord(NMSRecord record) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean tmp = true;

		try {
			conn = MysqlDAOFactory.getConnection();
			ps = conn.prepareStatement(INSERT_AN_NMSRecord);

			// System.out.println(record);
			ps.setString(1, record.getProvider());
			ps.setString(2, record.getMeterId());
			ps.setString(3, record.getUnit());
			ps.setString(4, record.getStartTime());
			ps.setString(5, record.getEndTime());
			ps.setString(6, record.getTz());
			ps.setInt(7, record.getInterval());
			ps.setInt(8, record.getNo());
			ps.setDouble(9, record.getValue());
			ps.setString(10, record.getSt());
			ps.setString(11, record.getId());

			int result = ps.executeUpdate();
			// System.out.println(result);
			if (result != 1) {
				tmp = false;
			}
		} catch (SQLException e) {
			tmp = false;
			e.printStackTrace();
		} finally {
			MysqlDAOFactory.closeStatement(ps);
			MysqlDAOFactory.closeConnection(conn);
		}

		return tmp;
	}

	public boolean deleteNMSRecord(NMSRecord NMSRecord) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean tmp = true;

		try {
			conn = MysqlDAOFactory.getConnection();
			stmt = conn.prepareStatement(DEL_AN_NMSRecord);
			int result = stmt.executeUpdate();
			if (result != 1) {
				tmp = false;
			}
		} catch (SQLException e) {
			tmp = false;
		} finally {
			MysqlDAOFactory.closeStatement(stmt);
			MysqlDAOFactory.closeConnection(conn);
		}

		return tmp;
	}

	//

	public static void main(String[] args) {

		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		NMSRecordDAO dao = df.getNMSRecordDAO();
//		dao.dualDailyRecord();
		dao.hourlyAndDailySynchronized("2015-04-13");
	}

	public Iterator dualDailyRecord() {
		Connection conn = null;
		PreparedStatement stmt = null;

		Statement st = null;

		try {
			conn = MysqlDAOFactory.getConnection();
			stmt = conn.prepareStatement(QUERY_DAY_NOT_CORRECT);
			NMSRecord NMSRecord = null;
			ResultSet rs = stmt.executeQuery();

			// delete
			// stmt = conn.prepareStatement(DELETE_DAY_NOT_CORRECT);
			// stmt.executeQuery();
			Set<String> s = Sets.newConcurrentHashSet();

			while (rs.next()) {

				String date = rs.getString(1).substring(0, 10);

				System.out.println(date + rs.getString(1) + rs.getDouble(2)
						+ "-" + rs.getDouble(3));

				s.add(date);

			}
			for (String d : s) {

				st = conn.createStatement();
				ResultSet trs = st
						.executeQuery("SELECT date_time,sum(nanyang),sum(nieo) FROM ict_data_tran.nms_day_results  where date_time  like '"
								+ d + "%'");
				// stmt.setString(1, d+"%");

				while (trs.next()) {
					System.out.println("updated - " + d + " 00:00:00 - "
							+ trs.getDouble(2) + "-" + trs.getDouble(3));

				}
				// update nms_day_results set nanyang = 82810.3 , nieo =
				// 51627.51 where date_time = '2014-12-12 00:00:00';
				// int i =
				// conn.createStatement().executeUpdate("update ict_data_tran.nms_day_results set nanyang = "+trs.getDouble(2)+",  nieo = "+trs.getDouble(3)+"where date_time = '"+d+" 00:00:00'");
				// System.out.println("update "+ i);
			}
			// 2015-01-052015-01-05 00:30:000.0-3653.81
			// 2015-01-052015-01-05 00:00:0054027.62-54102.67

			// conn.createStatement().executeUpdate("delete FROM ict_data_tran.nms_day_results  where date_time  not like '% 00:00:00'");

			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			MysqlDAOFactory.closeStatement(stmt);
			MysqlDAOFactory.closeConnection(conn);
		}
	}

	public Iterator getAllNMSRecord() {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = MysqlDAOFactory.getConnection();
			stmt = conn.prepareStatement(GET_ALL_NMSRecordS);
			NMSRecord NMSRecord = null;
			ResultSet rs = stmt.executeQuery();
			ArrayList NMSRecords = new ArrayList();
			while (rs.next()) {
				NMSRecord = new NMSRecord();
				// NMSRecord.setId(rs.getInt("id"));
				// NMSRecord.setName(rs.getString("name"));
				// NMSRecord.setCourse(rs.getString("Course"));
				NMSRecords.add(NMSRecord);
			}
			return NMSRecords.iterator();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			MysqlDAOFactory.closeStatement(stmt);
			MysqlDAOFactory.closeConnection(conn);
		}
	}

	public boolean isExists(NMSRecord record) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean tmp = true;

		try {
			conn = MysqlDAOFactory.getConnection();
			stmt = conn.prepareStatement(GET_AN_NMSRecord);
			stmt.setString(1, record.getMeterId());
			stmt.setString(2, record.getUnit());
			stmt.setString(3, record.getStartTime());
			stmt.setInt(4, record.getNo());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				tmp = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			tmp = false;
		} finally {
			MysqlDAOFactory.closeStatement(stmt);
			MysqlDAOFactory.closeConnection(conn);
		}

		return tmp;
	}

	public boolean updateNMSRecord(NMSRecord NMSRecord) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean tmp = true;

		try {
			conn = MysqlDAOFactory.getConnection();
			stmt = conn.prepareStatement(UPDATE_AN_NMSRecord);
			// stmt.setString(1, NMSRecord.getName());
			// stmt.setString(2, NMSRecord.getCourse());
			int result = stmt.executeUpdate();
			if (result != 1) {
				tmp = false;
			}
		} catch (SQLException e) {
			tmp = false;
		} finally {
			MysqlDAOFactory.closeStatement(stmt);
			MysqlDAOFactory.closeConnection(conn);
		}

		return tmp;
	}

	public boolean createNMSDays(List<NmsKwhImpDay> records, String file) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean tmp = true;
		// int count = 0 ;

		try {
			conn = MysqlDAOFactory.getConnection();
			ps = conn.prepareStatement(INSERT_AN_NMS_DAY);

			// id, date_time, value, unit,meter_id, std,max,dt_max,min,dt_min
			for (NmsKwhImpDay record : records) {
				ps.setString(1, record.getId());
				ps.setString(2, record.getDateTime());
				ps.setDouble(3, record.getValue());
				ps.setString(4, record.getUnit());
				ps.setString(5, record.getMeterId());
				ps.setDouble(6, record.getStd());
				ps.setDouble(7, record.getMax());
				ps.setString(8, record.getDtMax());
				ps.setDouble(9, record.getMin());
				ps.setString(10, record.getDtMin());
				ps.addBatch();
			}

			ps.executeBatch();

		} catch (SQLException e) {
			tmp = false;
			System.err.println("error : " + file);
			e.printStackTrace();
		} finally {
			MysqlDAOFactory.closeStatement(ps);
			MysqlDAOFactory.closeConnection(conn);
		}

		return tmp;
	}

	// private final String INSERT_AN_NMS_DAY =
	// "INSERT ignore INTO nms_kwh_imp_day (id, date_time, value, unit,meter_id, std,max,dt_max,min,dt_min) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	//
	//
	// private final String INSERT_AN_NMS_HOUR =
	// "INSERT ignore INTO nms_kwh_imp_hour (id, date_time, value, unit,meter_id) VALUES (?, ?, ?, ?, ?)";

	@Override
	public boolean createNMSRecords(List<NMSRecord> records, String file) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean tmp = true;
		// int count = 0 ;

		try {
			conn = MysqlDAOFactory.getConnection();
			ps = conn.prepareStatement(INSERT_AN_NMSRecord);

			for (NMSRecord record : records) {
				// System.out.println(record);
				// if(record.getUnit().equals("KWH_IMP_INT")/*&&!this.isExists(record)*/){

				ps.setString(1, record.getProvider());
				ps.setString(2, record.getMeterId());
				ps.setString(3, record.getUnit());
				// ps.setDate(4, new
				// java.sql.Date(record.getStartTime().getTime()));
				// ps.setDate(5, new
				// java.sql.Date(record.getEndTime().getTime()));
				ps.setString(4, record.getStartTime());
				ps.setString(5, record.getEndTime());
				ps.setString(6, record.getTz());
				ps.setInt(7, record.getInterval());
				ps.setInt(8, record.getNo());
				ps.setDouble(9, record.getValue());
				ps.setString(10, record.getSt());
				ps.setString(11, record.getId());
				// count++;
				ps.addBatch();
			}
			// }

			int[] results = ps.executeBatch();
			// for(int i : results){
			// System.out.println(i);
			// }
			// System.out.println(records.size());

		} catch (SQLException e) {
			tmp = false;
			System.err.println("error : " + file);
			e.printStackTrace();
		} finally {
			MysqlDAOFactory.closeStatement(ps);
			MysqlDAOFactory.closeConnection(conn);
			// System.out.println("inserted rows " + count);
		}

		return tmp;
	}

	@Override
	public List<String> getMetersByArea(String area) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = MysqlDAOFactory.getConnection();
			stmt = conn.prepareStatement(GET_ALL_NMSRecordS);
			ResultSet rs = stmt.executeQuery();
			List<String> meters = new ArrayList<String>();
			while (rs.next()) {
				// NMSRecord.setId(rs.getInt("id"));
				// NMSRecord.setName(rs.getString("name"));
				// NMSRecord.setCourse(rs.getString("Course"));
			}
			return meters;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			MysqlDAOFactory.closeStatement(stmt);
			MysqlDAOFactory.closeConnection(conn);
		}
	}

	@Override
	public boolean createNMSHours(List<NmsKwhImpHour> records, String file) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean tmp = true;

		try {
			conn = MysqlDAOFactory.getConnection();
			ps = conn.prepareStatement(INSERT_AN_NMS_HOUR);

			for (NmsKwhImpHour record : records) {
				ps.setString(1, record.getId());
				ps.setString(2, record.getDateTime());
				ps.setDouble(3, record.getValue());
				ps.setString(4, record.getUnit());
				ps.setString(5, record.getMeterId());

				ps.addBatch();
			}

			ps.executeBatch();

		} catch (SQLException e) {
			tmp = false;
			System.err.println("error : " + file);
			e.printStackTrace();
		} finally {
			MysqlDAOFactory.closeStatement(ps);
			MysqlDAOFactory.closeConnection(conn);
		}

		return tmp;
	}

	@SuppressWarnings("resource")
	@Override
	public void hourlyAndDailySynchronized(String dateTime) {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement ps = null;
		boolean tmp = true;

		try {
			conn = MysqlDAOFactory.getConnection();
			ps = conn.prepareStatement(SYN_ALL_HOUR);

			// System.out.println(record);
			ps.setString(1, dateTime);

			int result = ps.executeUpdate();
			System.out.println("houly update " + result);

			ps = conn.prepareStatement(SYN_ALL_DAY);

			// System.out.println(record);
			ps.setString(1, dateTime);

			result = ps.executeUpdate();
			System.out.println("Daily update " + result);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MysqlDAOFactory.closeStatement(ps);
			MysqlDAOFactory.closeConnection(conn);
		}

	}

}
