package com.rambo.common.utils.excel;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import com.rambo.common.utils.DateUtils;

public class JxlsUtil {
	
	/**
	 * 导出excel
	 * @param templateFile - excel模版名称
	 * @param beans - 模版中填充的数据
	 * @param os - 生成模版输出流
	 */
	public static void exportExcel(String templateFile,Map<String,Object> beans,OutputStream os) {
	     XLSTransformer transformer = new XLSTransformer();
	     InputStream is=JxlsUtil.class.getClassLoader().getResourceAsStream(templateFile);
	     try {
			Workbook workbook=transformer.transformXLS(is,beans);
			workbook.write(os);
		} catch (Exception e) {
//			throw new RuntimeException("导出excel错误!");
			e.printStackTrace();
		} 
	}
	
	
	public static String export(List<ExportModel> ls){
		
		String fName = System.currentTimeMillis()+".xls";
		OutputStream os = null;
		try {
			os = new FileOutputStream("export/"+fName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String templateFile="templates/energy_export_template.xlsx";
		Map<String, Object> beans=new HashMap<String, Object>();
		beans.put("results",ls);
		exportExcel(templateFile, beans, os);
		return fName;
	}
	
public static  void export(List<ExportModel> ls, OutputStream os){
		
		
		String templateFile="templates/energy_export_template.xlsx";
		Map<String, Object> beans=new HashMap<String, Object>();
		beans.put("results",ls);
		exportExcel(templateFile, beans, os);
	}
	
	public static void main(String[] args) throws ParsePropertyException, InvalidFormatException, IOException {
		OutputStream os=new FileOutputStream("newExcel.xls");
		String templateFile="templates/energy_export_template.xlsx";
		Map<String, Object> beans=new HashMap<String, Object>();
		
		// fruits
		List<ExportModel> fruitList=new ArrayList<ExportModel>();
		
		ExportModel em = new ExportModel("2014-01-04",12.3,23.4,34.8);
		
		fruitList.add(em);

		ExportModel em1 = new ExportModel("2014-01-05",14.3,23.4,3.4);
		fruitList.add(em1);		
		beans.put("results",fruitList);
		exportExcel(templateFile, beans, os);
	}
}
