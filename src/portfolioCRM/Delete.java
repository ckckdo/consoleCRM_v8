package portfolioCRM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Delete {


	//指定データの削除
	public static List<String[]> deleteTxt(String[] Act) {

		List<String> lines = new ArrayList<>();
		List<String[]> delLines = new ArrayList<>();

		//削除対象のデータを取り除く
		try (BufferedReader reader = new BufferedReader(new FileReader(Act[2]))) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");

				//番号を指定
				if (Integer.parseInt(Act[0]) == 0) {
					int targetNumber = Integer.parseInt(Act[1].trim()); // 削除対象の番号
					int lineNumber = Integer.parseInt(parts[0].trim());
					if (lineNumber != targetNumber) {
						lines.add(line);
					} else {
						delLines.add(parts);  //削除した内容を保存。
					}
				} else {
					//名前を指定
					String targetName = Act[1].trim(); //削除対象の名前
					String lineName = parts[1].trim();
					if(lineName != targetName) {
						lines.add(line);
					} else {
						delLines.add(parts);  //削除した内容を保存
					}
				}

			}
		} catch (IOException e) {
			System.err.println("ファイルの読み取り中にエラーが発生しました。");
			e.printStackTrace();
			return delLines;
		}
		//削除後、残ったデータの書き込み
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Act[2]))) {
			// ファイルに残ったデータを書き込む
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("ファイルの書き込み中にエラーが発生しました。");
			e.printStackTrace();
			return delLines;
		}

		return delLines;

	}


	//削除条件の入力
	public static Boolean deleteMenu(String FilePath) {

		boolean choiceAct = false;
		String[] Act = new String[3]; //{削除条件,対象内容}
		int inputMenu = -1;

		//削除条件の選択
		while (!choiceAct) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("削除する条件を次から1つ選択し番号を入力してください");
			System.out.println(Message.getMessage(5));
			System.out.println("1：会員番号　2：名前　＞");

			try {
				inputMenu = scanner.nextInt();
				if (inputMenu == 9999) {
					//アプリの終了
					Exit.ExitApp();
				}
				if (inputMenu < 0 || inputMenu > 3) {
					System.out.println("入力された番号の条件はありません。");
				} else {
					Act[0] = String.valueOf(inputMenu - 1) ;
					choiceAct = true;
				}
			} catch (java.util.InputMismatchException e) {
				System.out.println("無効な入力です。整数を入力してください。");
				scanner.next(); // 不正な入力をクリア
			}
		}

		//削除対象の内容
		switch (inputMenu){
		case 1:     //会員番号
			Act[1] = Message.kaiNumber();
			break;
		case 2:     //名前
			Act[1] = Message.name();
			break;
		default:
		}

		//ファイルパスを入れる
		Act[2] = FilePath;

		//削除処理
		List<String[]> deleteList = new ArrayList<>();
		deleteList = deleteTxt(Act);
		if (deleteList.size() < 0 ) {
			System.out.println("削除に失敗しました。");
			return false;
		}

		//結果表示
		System.out.println("以下のデータの削除を完了しました。");
		Message.result(deleteList);

		return true;
	}


}
