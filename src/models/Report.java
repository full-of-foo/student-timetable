package models;

public class Report {
	private StringBuffer reportStringBuffer;
	
	public Report(StringBuffer reportStringBuffer) {
		this.setReportStringBuffer(reportStringBuffer);
	}

	public StringBuffer getReportStringBuffer() {
		return reportStringBuffer;
	}

	public void setReportStringBuffer(StringBuffer reportStringBuffer) {
		this.reportStringBuffer = reportStringBuffer;
	}
}
