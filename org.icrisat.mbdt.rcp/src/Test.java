import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Test {

	public static void main(String[] args) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(
				"C:\\IBWorkflowSystem\\tools\\mbdt\\MBDT.exe", "");
		Map<String, String> env = pb.environment();
		// env.put("VAR1", "myValue");
		// env.remove("OTHERVAR");
		// env.put("VAR2", env.get("VAR1") + "suffix");
		 pb.directory(new File("C:\\IBWorkflowSystem\\tools\\mbdt"));
		Process p = pb.start();
	}
}
