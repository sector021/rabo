package com.rabo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.service.StatementParserService;

@RestController
@RequestMapping("/rabo")
public class StatementProcessController {
	
	@Autowired 
	 StatementParserService statementParserService;
	
	@RequestMapping(value = "/processStatment", method = RequestMethod.POST)
	public @ResponseBody  void statementHandler
	(@RequestParam("file") MultipartFile statement) throws Exception {
		 if (statement.isEmpty()) {
			 
		 }else {
			 statementParserService.statementParser(statement);
		 }
	}
	
	
	

}
