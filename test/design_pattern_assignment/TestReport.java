package design_pattern_assignment;
import utils.ScheduleReportWritter;

public class TestReport extends BaseTest {
	
	public void testEmptyReport() {
		beforeCase();

		StringBuffer buffer = new StringBuffer();
		buffer = ScheduleReportWritter.writeReport(buffer);
		assertEquals("Number of scheduled offerings: 0\n", buffer.toString());
	}
	
	public void testReport() {
		beforeCase(true);
		
		StringBuffer buffer = new StringBuffer();
		buffer = ScheduleReportWritter.writeReport(buffer);
		String result = buffer.toString();
		String valid1 = "CS101 M10\n\tAlice\n\tBob\n" + "CS101 T9\n\tBob\n" + "Number of scheduled offerings: 2\n";
		String valid2 = "CS101 T9\n\tBob\n" + "CS101 M10\n\tBob\n\tAlice\n" + "Number of scheduled offerings: 2\n";
		assertTrue(result.equals(valid1) || result.equals(valid2));
	}

}
