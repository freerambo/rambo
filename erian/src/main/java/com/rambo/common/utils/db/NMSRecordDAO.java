package com.rambo.common.utils.db;
import java.util.*;

import com.rambo.erian.entity.NMSRecord;
import com.rambo.erian.entity.NmsKwhImpDay;
import com.rambo.erian.entity.NmsKwhImpHour;


public interface NMSRecordDAO {

		public boolean isExists(NMSRecord record);
		public boolean deleteNMSRecord(NMSRecord record);
		public boolean createNMSRecord(NMSRecord record);
		public boolean createNMSRecords(List<NMSRecord> records,String file);
		public boolean updateNMSRecord(NMSRecord record);
		public  Iterator getAllNMSRecord();
		
		public Iterator dualDailyRecord();
		public boolean createNMSDays(List<NmsKwhImpDay> records,String file);
		
		public boolean createNMSHours(List<NmsKwhImpHour> records,String file);

		
		public  List<String> getMetersByArea(String area);
		
		public void hourlyAndDailySynchronized(String dateTime);
		
}
