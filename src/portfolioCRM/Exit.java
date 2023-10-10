package portfolioCRM;

public class Exit {

	public static void ExitMsg() {
		System.out.println();
		System.out.println("―――――――――――――――――――――――――――――――――――――――");
		System.out.println("※入力時に「9999」を入力すると、入力を中止してアプリを終了することができます。");
		System.out.println("―――――――――――――――――――――――――――――――――――――――");
		System.out.println();
	}

	public static void ExitApp() {
		System.out.println("アプリを終了しました");
		System.exit(0);
	}
}
