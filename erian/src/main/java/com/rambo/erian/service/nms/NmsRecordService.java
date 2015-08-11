package com.rambo.erian.service.nms;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.common.collect.Lists;
import com.rambo.common.params.MeterConstants;
import com.rambo.common.params.TimeConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.common.utils.Statistics;
import com.rambo.common.utils.StatisticsDouble;
import com.rambo.common.utils.excel.ExportModel;
import com.rambo.common.utils.xml.NMSRecordHandler;
import com.rambo.erian.entity.NMSRecord;
import com.rambo.erian.entity.NmsDay;
import com.rambo.erian.entity.NmsDayResult;
import com.rambo.erian.entity.NmsHour;
import com.rambo.erian.entity.NmsMonthResult;
import com.rambo.erian.entity.NmsWeekResult;
import com.rambo.erian.entity.PmWlgDay;
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.repository.NMSRecordKWHDao;
import com.rambo.erian.repository.NmsDayDao;
import com.rambo.erian.repository.NmsDayResultDao;
import com.rambo.erian.repository.NmsHourDao;
import com.rambo.erian.repository.NmsMonthResultDao;
import com.rambo.erian.repository.NmsWeekResultDao;
import com.rambo.erian.repository.PmWlgMonthDao;
import com.rambo.erian.repository.PmWlgWeekDao;
import com.rambo.erian.service.PmWlgDayService;
import com.rambo.infrustructure.entity.NmsMeter;
import com.rambo.infrustructure.repository.NmsMeterDao;

@Service("nmsRecordService")
// all the public function will be transaction associated
@Transactional(value = "defaultEM")
public class NmsRecordService {

	private NMSRecordKWHDao dao;

	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(NmsRecordService.class); // 日志打印

	@Autowired
	public void setDao(NMSRecordKWHDao dao) {
		this.dao = dao;
	}

	/**
	 * 
	 * @function: visit all raw file and insterinto database
	 * @author: zhuyuanbo 3 Feb, 2015 1:27:49 pm
	 */
	public void showAllFile(String path) {

		File f = new File(path);
		String[] files = f.list();
		
		XMLReader parser = null;
		try {
			parser = XMLReaderFactory.createXMLReader();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		NMSRecordHandler nMSRecordHandler = new NMSRecordHandler();
		parser.setContentHandler(nMSRecordHandler);
		
		
		for(String s : files){
//			log.warn();
			String p = path+"\\"+s;
			try {
				parser.parse(p);
			} catch (IOException | SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.save(nMSRecordHandler.records);	
		}
	}
	
	
	public void createNMSDailyData(String start, String end){
		
//		dao.findByMeterIdAndStartTime(meterId, startTime);
		
	}

}



