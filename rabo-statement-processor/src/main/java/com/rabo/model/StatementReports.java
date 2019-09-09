package com.rabo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatementReports {

	
	

		public StatementReports(List<StatementReport> StatementReport) {
			super();
			this.record = StatementReport;
		}

		private List<StatementReport> record;

		@XmlElement(name="record")
		public List<StatementReport> getRecord() {
			return record;
		}

		public void setRecord(List<StatementReport> record) {
			this.record = record;
		}
}
