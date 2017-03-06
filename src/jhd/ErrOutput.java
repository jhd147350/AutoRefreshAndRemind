package jhd;

public class ErrOutput {

	public static boolean ENABLE_OUTPUT = true;

	public static void err(String err) {
		if (ENABLE_OUTPUT)
			System.out.println(err);

	}
}
