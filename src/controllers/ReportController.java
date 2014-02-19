package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Report;
import utils.ScheduleReportWritter;
import views.ReportView;

public class ReportController {
	private ReportView view;
	private Report report;
	
	public ReportController(ReportView view, Report report) {
		this.view = view;
		this.report = report;
		
		this.view.addReportBtnListener(new GenerateReportListener());
	}

	
	class GenerateReportListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			StringBuffer buffer = ScheduleReportWritter.writeReport(new StringBuffer());
			
			report.setReportStringBuffer(buffer);	
			view.getReportLabel().setText(report.toString());
		}
		
	}

}
