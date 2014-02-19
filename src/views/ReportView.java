package views;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class ReportView {
	private JButton generateReportBtn = new JButton();
	private JLabel reportLabel = new JLabel();
	
	public ReportView(){}
	
	public JButton getGenerateReportBtn() {
		return generateReportBtn;
	}

	public void setGenerateReportBtn(JButton generateReportBtn) {
		this.generateReportBtn = generateReportBtn;
	}

	public JLabel getReportLabel() {
		return reportLabel;
	}

	public void setReportLabel(JLabel reportLabel) {
		this.reportLabel = reportLabel;
	}

	public void addReportBtnListener(ActionListener listenForReportButton){
		generateReportBtn.addActionListener(listenForReportButton);
	}
}
