package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Report;
import utils.ScheduleReportWriter;
import views.ReportView;

/**
 * ReportController --- Controller that
 * mediates actions between reportViews
 * and reportModels
 * 
 * @author       Anthony Troy
 */
public class ReportController {
	private ReportView view;
	private Report report;
	
	public ReportController(ReportView view, Report report) {
		this.view = view;
		this.report = report;
		
		this.view.addReportBtnListener(new GenerateReportListener());
	}

	
	/**
	 * GenerateReportListener --- ActionListener 
	 * implementation which handles the view's
	 * response to button actions
	 * 
	 * @author       Anthony Troy
	 */
	class GenerateReportListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			StringBuffer buffer = ScheduleReportWriter.writeReport(new StringBuffer());
			
			report.setReportStringBuffer(buffer);	
			view.getReportLabel().setText(report.toString());
		}
		
	}

}
