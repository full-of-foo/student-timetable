package models;

/**
 * Report --- POJO that represents a 
 * non-persistent Report entity.
 * 
 * @author    Anthony Troy
 */
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
	
	/**
	   * String representation of a report
	   * @exception Any exception
	   * @return buffer.toString()
	   */
	public String toString(){
		return reportStringBuffer.toString();
	}
}
