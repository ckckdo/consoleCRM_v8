package portfolioCRM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Edit {

	//指定データの削除
	public static List<String[]> editTxt(String[] Act){

		List<String> lines = new ArrayList<>();
		List<String[]> delLines = new ArrayList<>();
		List<String[]> editLines = new ArrayList<>();
		String editLine = "";

		try(BufferedReader reader = new BufferedReader(new FileReader(Act[2]))) {
			String line;

			while ((line = reader.readLine()) !=null) {
				String[] parts = line.split(",");
				//番号を指定
				if (Integer.parseInt(Act[0]) == 0) {
					int targetNumber = Integer.parseInt(Act[1].trim()); // 修正対象の番号
					int lineNumber = Integer.parseInt(parts[0].trim());
					if (lineNumber != targetNumber) {
						lines.add(line);
					} else {
						delLines.add(parts);  //修正する内容を保存。
						editLine = EditData(delLines);  //修正
						lines.add(editLine);
						editLines.add(editLine.split(","));
					}
				} else {
					//名前を指定
					String targetName = Act[1].trim(); //修正対象の名前
					String lineName = parts[1].trim();
					if(lineName != targetName) {
						lines.add(line);
					} else {
						delLines.add(parts);  //修正する内容を保存
						editLine = EditData(delLines);  //修正
						lines.add(editLine);
						editLines.add(editLine.split(","));
					}
				}
			}
		}catch (IOException  e) {
			System.err.println("ファイルの読み取り中にエラーが発生しました。");
			e.printStackTrace();
		}

		//修正後、残ったデータの書き込み
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(Act[2]))) {
					// ファイルに残ったデータを書き込む
					for (String line : lines) {
						writer.write(line);
						writer.newLine();
					}
				} catch (IOException e) {
					System.err.println("ファイルの書き込み中にエラーが発生しました。");
					e.printStackTrace();
				}

		return editLines;
	}


	public static boolean EditMenu(String FilePath) {
		boolean choiceAct = false;
		String[] Act = new String[3]; //{修正条件,対象内容,ファイルパス}
		int inputMenu = -1;

		//修正条件の選択
		while (!choiceAct) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("修正するデータを検索する条件を次から1つ選択し番号を入力してください");
			System.out.println("1：会員番号　2：名前　＞");

			try {
				inputMenu = scanner.nextInt();
				System.out.println();
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


		//修正対象の内容
		switch (inputMenu){
		case 1:     //会員番号
			Act[1] = Message.kaiNumber();
			System.out.println();
			break;
		case 2:     //名前
			Act[1] = Message.name();
			System.out.println();
			break;
		default:
		}

		//ファイルパスを入れる
		Act[2] = FilePath;

		//修正処理
		List<String[]> editList = new ArrayList<>();
		editList = editTxt(Act);
		if (editList.size() < 0 ) {
			System.out.println("修正に失敗しました。");
			return false;
		}

		//結果表示
		System.out.println("以下の内容で修正を完了しました。");
		Message.result(editList);

		return true;

	}


	public static String EditData(List<String[]> delData) {
		String name = "";
		String prif = "";
		String gender = "";
		String birth = "";
		String parts = "";

		System.out.println("以下のデータを修正します。");
		Message.result(delData);

		//名前
		name = Message.name();
		//都道府県
		prif = Message.prefecture();
		//性別
		gender = Message.gender();
		//生年月日
		birth = Message.birth();
		//登録日
		LocalDate now = LocalDate.now();
		String nowDate = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		for (String[] data : delData) {
			String number = data[0]; // 会員番号取り出し
			parts = number + "," + name + "," + prif + ","+ gender +"," + birth + "," + nowDate;
		}

		return parts;
	}


}
