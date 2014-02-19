package design_pattern_assignment;

import models.Report;
import views.ReportView;
import controllers.ReportController;

public class TestMvcReport extends BaseTest {

	public void testMvcFullReport() {
		beforeCase(true);

		ReportView view = new ReportView();
		Report report = new Report(new StringBuffer());
		new ReportController(view, report);

		assertEquals("", view.getReportLabel().getText());

		view.getGenerateReportBtn().doClick();

		String text = view.getReportLabel().getText();
		String valid = "CS101 T9\n\tBob\n" + "CS101 M10\n\tBob\n\tAlice\n"
				+ "Number of scheduled offerings: 2\n";
		assertTrue(text.equals(valid));
	}

	public void testMvcEmptyReport() {
		beforeCase();

		ReportView view = new ReportView();
		Report report = new Report(new StringBuffer());
		new ReportController(view, report);

		assertEquals("", view.getReportLabel().getText());

		view.getGenerateReportBtn().doClick();
		String text = view.getReportLabel().getText();
		String valid = "Number of scheduled offerings: 0\n";
		assertTrue(text.equals(valid) || text.equals(valid));
	}

}
