package portfolioCRM;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Message {


	//メッセージを取得する
	public static String getMessage (int Act){
		String inf ;
		String ex ;

		switch (Act){
		case 0 :
			inf = "名前を";
			ex = "例：福沢諭吉";
			break;
		case 1 :
			inf = "住んでいる都道府県を";
			ex = "例：大阪府";
			break;
		case 2 :
			inf = "生年月日を半角数字で";
			ex = "例：19970113";
			break;
		case 3 :
			inf = "性別を次から選んで番号を";
			ex = "例：1";
			break;
		case 4 :
			inf = "会員番号を半角数字3桁で";
			ex = "例：001";
			break;
		default :
			inf = "入力内容が正しくありません。もう一度";
			ex ="";
		}
		return inf + "入力してください。" + ex + "　＞　" ;
	}


	//会員番号の入力
	public static String kaiNumber(){
		Scanner scanner = new Scanner(System.in);
		boolean kaiNumberFlg = false;
		int kaiNumber ;
		String strNumber ="";

		while (!kaiNumberFlg) {
			System.out.print(Message.getMessage(4));
			try {
				strNumber = scanner.nextLine();
				kaiNumber = Integer.parseInt(strNumber);

				if (kaiNumber < 0 || kaiNumber > 999) {
					System.out.println("会員番号の範囲を超えています。3桁の半角数字で入力してください。");
				} else if(strNumber.length() < 3 || strNumber.length() > 4 ){
					System.out.println("3桁の半角数字で入力してください。");
				}else {
					kaiNumberFlg = true;
				}
			} catch (java.util.InputMismatchException | NumberFormatException e ){
				System.out.println("無効な入力です。整数を入力してください。＞");
			}
		}
		return strNumber;
	}
	//名前の入力
	public static String name (){
		Scanner scanner = new Scanner(System.in);
		System.out.print(Message.getMessage(0));
		String name = scanner.nextLine();
		name = name.replace("　",""); //空欄削除
		name = name.trim(); //空欄削除
		return  name;
	}

	//都道府県の入力
	public static String prefecture (){
		Scanner scanner = new Scanner(System.in);
		System.out.print(Message.getMessage(1));
		String pref = scanner.nextLine();
		pref = pref.replace("　",""); //空欄削除
		pref = pref.trim(); //空欄削除
		return  pref;
	}

	//生年月日の入力
	public static String birth() {
		Scanner scanner = new Scanner(System.in);
		DateTimeFormatter frm = DateTimeFormatter.ofPattern("yyyyMMdd");

		boolean birthFlg = false;
		String birth = "";
		while (!birthFlg) {
			System.out.print(Message.getMessage(2));
			String birthdate = scanner.nextLine();
			if (!birthdate.matches("[0-9]{8}")) {
				System.out.println("生年月日が正しく入力されていません。");
				continue;
			}
			try {
				LocalDate date = LocalDate.parse(birthdate, frm);
				birth = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
				birthFlg = true;

			} catch (DateTimeParseException e) {
				System.out.println("生年月日が正しく入力されていません。");
				scanner.next(); // 不正な入力をクリア
			}
		}
		return birth;
	}

	//性別の入力
	public static String gender(){
		Scanner scanner = new Scanner(System.in);
		boolean genderFlg = false;
		int gender = -1;

		while (!genderFlg) {
			System.out.println("性別を次から選んで番号を入力してください。");
			System.out.println("0：女性　1：男性　＞");
			gender = scanner.nextInt();
			try {
				if (gender < 0 || gender > 1) {
					System.out.println("入力された内容が正しくありません。0か1で入力してください。");
				} else {
					genderFlg = true;
				}
			} catch (java.util.InputMismatchException e){
				System.out.println("無効な入力です。整数を入力してください。");
				scanner.next(); // 不正な入力をクリア
			}
		}
		return String.valueOf(gender);
	}
}
