package com.rabo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.constant.raboStatementConstant;
import com.rabo.model.StatementReport;


@Service
public class StatementParserService {
	
	@Autowired 
	StatementValidatorService statementValidatorService;

	public void statementParser(MultipartFile statement) {
		if(statement.getContentType().equalsIgnoreCase(raboStatementConstant.CONTENT_TYPE_CSV)) {
			File statementInCsv = new File(statement.getOriginalFilename());
			try {
				statement.transferTo(statementInCsv);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<StatementReport> Statement = statementValidatorService.statmentExtractorfromCsv(statementInCsv);
		    if(Statement.isEmpty()) {
		    	
		    }else {
		    	List duplicateStatement = statementValidatorService.checkForDuplicateRecords(Statement);
		    	if (duplicateStatement.isEmpty()) {		    		
		    		statementValidatorService.getEndBalanceRecords(Statement);
		    	}
		    }
		}else if (statement.getContentType().equalsIgnoreCase(raboStatementConstant.CONTENT_TYPE_XML)) {
			File statementInXml = new File(statement.getOriginalFilename());
			List<StatementReport> Statement = null;
			try {
				statement.transferTo(statementInXml);			
			
				Statement = statementValidatorService.statmentExtractorfromXML(statementInXml);
			}  catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}
        if(Statement.isEmpty()) {
		    	
		    }else {
		    	List duplicateStatement = statementValidatorService.checkForDuplicateRecords(Statement);
		    	if (duplicateStatement.isEmpty()) {		    		
		    		statementValidatorService.getEndBalanceRecords(Statement);
		    	}
		}
	}
	}
}
