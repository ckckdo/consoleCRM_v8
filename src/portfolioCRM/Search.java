package portfolioCRM;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Search {


	//テキストファイルの読み込み
	public static boolean readTxt (String FilePath, String[] jokenSort) {
		List<String[]> data = new ArrayList<>();
		FileReader fr = null;
		BufferedReader br =null;
		String line;

		try{
			//読み込むファイルの指定
			fr = new FileReader(FilePath);
			//ファイルの読み込み
			br = new BufferedReader(fr);

			//1行ずつ読み込み
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (!jokenSort[0].equals("zenken")) {
					//検索対象の項目を取得
					String key = parts[Integer.parseInt(jokenSort[2])].trim();
					//検索、ヒットしたデータをリストに追加
					if (key.equals((jokenSort[0]))) {
						data.add(parts);
					}
				} else {
					data.add(parts);
				}
			}
			//検索結果が1件もない場合はメッセージを表示
			if (data.isEmpty()){
				System.out.println("入力された条件に該当するデータが存在しません。");
				return false;
			}

			//ソート
			if (Integer.parseInt((jokenSort[1])) == 1) {
				//会員番号順
				Collections.sort(data, new Comparator<String[]>() {
					@Override
					public int compare(String[] o1, String[] o2) {
						return Integer.compare(Integer.parseInt(o1[0]), Integer.parseInt(o2[0]));
					}
				});
			} else {
				//登録日時順
				Collections.sort(data, new Comparator<String[]>() {
					@Override
					public int compare(String[] o1, String[] o2) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
						try {
							Date date1 = dateFormat.parse(o1[5].trim());
							Date date2 = dateFormat.parse(o2[5].trim());
							return date1.compareTo(date2);
						} catch (ParseException e) {
							e.printStackTrace();
							return 0;
						}
					}
				});
			}

			//データの表示
			System.out.println("―――――――――   検索結果  ――――――――――");

			for (String[] row : data) {
				String number = row[0];
				String name = row[1];
				String prefecture = row[2];
				String gender = row[3];
				String birthdate = row[4];
				String issueDate = row[5];

				//表示
				System.out.println("番号: " + number);
				System.out.println("名前: " + name);
				System.out.println("都道府県: " + prefecture);
				System.out.println("性別: " + (Integer.parseInt(gender) == 0 ? "女性" : "男性"));
				System.out.println("生年月日: " + birthdate);
				System.out.println("登録日: " + issueDate);
				System.out.println();
			}
			System.out.println("――――――――――――――――――――――――――");

		} catch ( IOException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				fr.close();
				br.close();
			} catch(IOException ignored) {

			}
		}
		//正常終了
		return true;
	}

	//検索条件の入力
	public static String[] searchMenu() {

		boolean choiceJoken = false;
		boolean choiceSort = false;
		int jokenNumber = -1;
		int sort = -1;
		String[] jokenSort = new String[3]; //{検索条件,ソート順,検索項目}

		//検索条件の選択
		while (!choiceJoken) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("検索する条件を次から1つ選択し番号を入力してください");
			System.out.println("1：会員番号　2：名前　3：都道府県　4：性別　5:生年月日　 6：全件　＞");

			try {
				jokenNumber = scanner.nextInt();
				if (jokenNumber < 0 || jokenNumber > 6) {
					System.out.println("入力された番号の条件はありません。");
				} else {
					jokenSort[2] = String.valueOf(jokenNumber - 1) ;
					choiceJoken = true;
				}
			} catch (java.util.InputMismatchException e) {
				System.out.println("無効な入力です。整数を入力してください。");
				scanner.next(); // 不正な入力をクリア
			}
		}

		//ソート順の選択
		while (!choiceSort) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("表示する順番を次から1つ選択し番号を入力してください");
			System.out.println("1：会員番号順　2：登録日順　＞");

			try {
				sort = scanner.nextInt();
				if (sort != 1 && sort != 2) {
					System.out.println("入力された番号の順番はありません。");
				} else {
					jokenSort[1] = String.valueOf(sort);
					choiceSort = true;
				}
			} catch (java.util.InputMismatchException e) {
				System.out.println("無効な入力です。整数を入力してください。");
				scanner.next(); // 不正な入力をクリア
			}
		}

		switch (jokenNumber){
		case 1:     //会員番号
			jokenSort[0] = Message.kaiNumber();
			break;
		case 2:     //名前
			jokenSort[0] = Message.name();
			break;
		case 3:     //都道府県
			jokenSort[0] = Message.prefecture();
			break;
		case 4:     //性別
			jokenSort[0] = Message.gender();
			break;
		case 5:     //生年月日
			jokenSort[0] = Message.birth();
			break;
		case 6:     //全件
			jokenSort[0] = "zenken";
			break;
		default:
			;
		}

		return jokenSort;

	}
}
