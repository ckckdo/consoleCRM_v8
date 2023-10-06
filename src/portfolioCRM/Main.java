package portfolioCRM;
import java.io.File;
import java.util.Scanner;


public class Main {

	public static  void main (String[] args){
		Scanner scanner = new Scanner(System.in);
		boolean StartMenu = true;
		boolean InFile = true;
		String FilePath = "";

		//起動メッセージ
		System.out.println("会員管理システムを起動しました。");

		//パスの入力
		while (InFile) {
			System.out.println("データを保存するテキストファイルのパスを入力してください。＞");
			String inputFilePath = scanner.nextLine();
			File file = new File(inputFilePath);
			if (!file.exists() || !file.isFile()) {
				System.out.println("指定したファイルが存在しません。");
			} else{
				//ファイルパス
				FilePath = inputFilePath;
				InFile = false;
			}
		}

		//メニューの表示
		while (StartMenu) {
			System.out.println("実行するメニューの番号を入力してください ");
			System.out.println("1：登録　2：検索　0:終了　＞");
			try {
				int ChoiceMenu ;
				ChoiceMenu = scanner.nextInt();

				if (ChoiceMenu < 0 || ChoiceMenu > 2) {
					System.out.println("入力された番号のメニューはありません。");
				} else {
					switch (ChoiceMenu) {
					case 1:  //登録
						System.out.println("登録します。");
						if (!Register.writeTxt(FilePath)){
							System.out.println("はじめからやり直してください。");
							System.out.println(" ");
						}
						break;
					case 2:  //検索
						System.out.println("検索します。");
						String[] data ;
						data = Search.searchMenu() ;
						if(!Search.readTxt(FilePath,data)){
							System.out.println("はじめからやり直してください。");
							System.out.println(" ");
						}
						break;
					case 0:  //終了
						System.out.println("終了します");
						StartMenu = false;
						break;
					default:  //その他
						System.out.println("入力された番号のメニューはありません。");
					}
				}

			} catch (java.util.InputMismatchException e) {
				System.out.println("無効な入力です。整数を入力してください。＞");
			}
		}
	}

}
