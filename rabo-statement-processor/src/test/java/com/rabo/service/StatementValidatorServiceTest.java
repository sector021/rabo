package com.rabo.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.rabo.model.StatementReport;

public class StatementValidatorServiceTest {

	@Test
	public void checkForDuplicateRecordsTest() {
		List<StatementReport> inputList = Arrays.asList(
				new StatementReport(172833, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 24.98),
				new StatementReport(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 59.61));
		StatementValidatorService statementValidatorService = new StatementValidatorService();
		List<StatementReport> duplicateRecords = statementValidatorService.checkForDuplicateRecords(inputList);
		assertEquals(inputList.size(), duplicateRecords.size());

	}
	
	@Test
	public void checkForDuplicateRecordsTest1() {
		List<StatementReport> inputList = Arrays.asList(
				new StatementReport(172823, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 24.98),
				new StatementReport(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 59.61));
		StatementValidatorService statementValidatorService = new StatementValidatorService();
		List<StatementReport> duplicateRecords = statementValidatorService.checkForDuplicateRecords(inputList);
		assertEquals(0, duplicateRecords.size());

	}
	
	@Test
	public void getEndBalanceRecordsTest() {
		List<StatementReport> inputList = Arrays.asList(
				new StatementReport(172833, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 24.98),
				new StatementReport(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 59.80));
		StatementValidatorService statementValidatorService = new StatementValidatorService();
		List<StatementReport> endBalanceErrorRecords = statementValidatorService.getEndBalanceRecords(inputList);
		assertEquals(inputList.size(), endBalanceErrorRecords.size());

	}
	
	@Test
	public void getEndBalanceRecordsTest1() {
		List<StatementReport> inputList = Arrays.asList(
				new StatementReport(172833, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 108.46),
				new StatementReport(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", -26.57));
		StatementValidatorService statementValidatorService = new StatementValidatorService();
		List<StatementReport> endBalanceErrorRecords = statementValidatorService.getEndBalanceRecords(inputList);
		assertEquals(0, endBalanceErrorRecords.size());
	}
	
}
