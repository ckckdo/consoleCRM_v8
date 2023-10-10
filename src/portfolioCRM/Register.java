package portfolioCRM;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Register {

	//テキストファイルに登録されている最大番号を取得
	public static int getMaxNumber(String FilePath) {
		try {
			FileReader fileReader = new FileReader(FilePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			int maxNumber = 1;

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				// カンマで分割して番号を取得
				String[] parts = line.split(",");
				if (parts.length >= 1) {
					int number = Integer.parseInt(parts[0]);
					if (number > maxNumber) {
						maxNumber = number;
					}
				}
			}
			// リソースをクローズ
			bufferedReader.close();
			fileReader.close();

			return maxNumber;

		} catch (IOException e) {
			System.out.println("エラー: ファイルの読み取りに失敗しました。");
			e.printStackTrace();
			return 0;
		} catch (NumberFormatException e) {
			System.out.println("エラー: 番号を整数に変換できませんでした。");
			e.printStackTrace();
			return 0;
		}
	}


	//テキストファイルへの書き込み
	public  static boolean writeTxt(String FilePath){

		try {
			FileWriter fileWriter = new FileWriter(FilePath, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			// 最大番号を取得し、採番
			int maxNum = getMaxNumber(FilePath);
			int number = -1;
			String strNumber = "";
			if (maxNum > 0 ) {
				number = maxNum + 1;
				strNumber = String.format("%03d",number);
			}

			//名前の入力
			String name = Message.name();

			//都道府県の入力
			String prefecture = Message.prefecture();

			//生年月日の入力
			String birth = Message.birth();

			//性別の入力
			String gender = Message.gender();

			//登録日
			LocalDate now = LocalDate.now();
			String nowDate = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

			// テキストファイルにデータを書き込む
			String data = strNumber + "," + name + "," + prefecture + ","+ gender +"," + birth + "," + nowDate;
			printWriter.println(data);

			//結果メッセージの表示
			List<String[]> dataList = new ArrayList<>();
			dataList.add(data.split(","));
			System.out.println("以下の内容で、登録が完了しました。");
			Message.result(dataList);

			// リソースをクローズ
			printWriter.close();
			fileWriter.close();

			return true;

		} catch (IOException e) {
			System.out.println("ファイルへの書き込みに失敗しました。");
			e.printStackTrace();
			return  false;
		}
	}

}
