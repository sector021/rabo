package com.rabo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.rabo.model.StatementReport;
import com.rabo.model.StatementReports;

@Component
public class StatementValidatorService {

	public List<StatementReport> statmentExtractorfromCsv(File csvStatement) {
		HeaderColumnNameTranslateMappingStrategy<StatementReport> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<StatementReport>();
		beanStrategy.setType(StatementReport.class);

		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("Reference", "transactionRef");
		columnMapping.put("AccountNumber", "accountNumber");
		columnMapping.put("Description", "description");
		columnMapping.put("Start Balance", "startBalance");
		columnMapping.put("Mutation", "mutation");
		columnMapping.put("End Balance", "endBalance");

		beanStrategy.setColumnMapping(columnMapping);

		CsvToBean<StatementReport> csvToBean = new CsvToBean<StatementReport>();
		CSVReader reader;
		List<StatementReport> records = null;
		try {
			reader = new CSVReader(new FileReader(csvStatement));
			records = csvToBean.parse(beanStrategy, reader);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return records;
	}
	
	public List<StatementReport> statmentExtractorfromXML(File file) throws Exception {
		  
        JAXBContext jaxbContext = JAXBContext.newInstance(StatementReports.class);  
   
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
        StatementReports rootRecord= (StatementReports) jaxbUnmarshaller.unmarshal(file);  

		return rootRecord.getRecord();
	}
	
	public List<StatementReport> getEndBalanceRecords(List<StatementReport> reports) {
		List<StatementReport> endBalanceErrorRecords = new  ArrayList<StatementReport>();
		int counter = 0;
		for (StatementReport report : reports) {
			if (Math.round((report.getStartBalance() - report.getMutation()) - Math.round(report.getEndBalance())) == 0) {
				counter++;
			}else {
				endBalanceErrorRecords.add(report);
			}
		}
		return endBalanceErrorRecords;
	}
	public List<StatementReport> checkForDuplicateRecords(List<StatementReport> records) {
		//List<Integer> transRecords =  records.stream().map(StatementReport::getTransRef)
			// 			    .collect(Collectors.toList());
		//records.stream().map(pm ->pm.getTransRef).forEach(); 
		Map<Integer, StatementReport> uniqeRecords = new HashMap<Integer, StatementReport>();
		List<StatementReport> duplicateRecords = new ArrayList<StatementReport>();
		for (StatementReport record : records) {
			if (uniqeRecords.containsKey(record.getTransRef())) {
				duplicateRecords.add(record);
			} else {
				uniqeRecords.put(record.getTransRef(), record);
			}
		}
		List<StatementReport> finalDuplicateRecords = new ArrayList<StatementReport>();
		finalDuplicateRecords.addAll(duplicateRecords);
		for (StatementReport record : duplicateRecords) {
			if (null != uniqeRecords.get(record.getTransRef())) {
				finalDuplicateRecords.add(uniqeRecords.get(record.getTransRef()));
				uniqeRecords.remove(record.getTransRef());
			}
		}
		return finalDuplicateRecords;
		/* List<Integer> transRecords = null;
		for (final StatementReport record : records) {
			String TransRef =(record.getTransRef()).toString();			
			transRecords.add(record.getTransRef());  
	        }
		
		
		
		return records.stream().collect(Collectors.groupingBy(Function.identity(),     
	              Collectors.counting()))                                             // perform group by count
	          .entrySet().stream()
	          .filter(e -> e.getValue() > 1L)                                        // using take only those element whose count is greater than 1
	          .map(e -> e.getKey())                                                  // using map take only value   
	          .collect(Collectors.toList())  ;                                      // convert group by result to list      
	          //.forEach(System.out::println);
	//	return null; */
	}
}
