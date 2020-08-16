package hitNBlow.main;

import java.util.Random;
import java.util.Scanner;

public class Main {

	public static final int MIN_LV = 1;
	public static final int MAX_LV = 5;
	public static final int MAX_ATTEMPTS = 10;
	static int gameLevel = -1;

	//ヒットアンドブロー
	public static void main(String[] args) {

		setGameLevel();

		System.out.println("敵のレベルは[" + gameLevel + "]に設定されました。");

		int[] enemyNum = new int[gameLevel];
		getEnemyNum(enemyNum);

		boolean gameOver = false;
		boolean playerWon = false;
		int timesAttempted = 0;

		System.out.println(gameLevel + "桁の敵の数字を予想して入力ください。");
		while ( !gameOver ) {

			int[] userNum = new int[gameLevel];

			getUserNum(userNum);
			timesAttempted++;

			if (judgeWinner(userNum, enemyNum)) {
				playerWon = true;
				gameOver  = true;
			}
			if (timesAttempted == MAX_ATTEMPTS) {
				gameOver  = true;
			}
		}

		if ( playerWon ) {
			System.out.println("おめでとう！あなたの勝ちです。");
			System.out.println("挑戦回数は" + timesAttempted + "回でした！");
		} else {
			System.out.println("残念！挑戦回数が" + MAX_ATTEMPTS + "に達したのであなたの負けです。");
			System.out.print("敵の数字は");
			for ( int eachNum: enemyNum ) System.out.print(eachNum);
			System.out.println("でした。");
		}
	}

	//勝敗を判定する関数
	private static boolean judgeWinner(int[] userNum, int[] enemyNum) {

		int hit  = 0;
		int blow = 0;

		for (int i = 0; i < gameLevel; i++) {
			if (userNum[i] == enemyNum[i]) {
				hit++;
			}else {
				for (int j = 0; j < gameLevel; j++) {
					if (userNum[i] == enemyNum[j]) {
						blow++;
					}
				}
			}
		}
		if (hit == gameLevel) {
			return true;
		} else {
			System.out.println("Hit = " + hit + "; Blow = " + blow );
			return false;
		}
	}

	//敵のレベル（数字の桁数）をユーザーに設定させる関数
	private static void setGameLevel() {
		boolean isValidLevel = (gameLevel >= MIN_LV && gameLevel <= MAX_LV);

		System.out.println("ゲームレベルを" + MIN_LV + "～" + MAX_LV + "の間で設定してください。");

		while( !isValidLevel ) {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String strNum = scanner.nextLine();

	        try
	        {
	        	gameLevel = Integer.parseInt(strNum);
	        }catch(NumberFormatException e) {
		        System.out.println("無効です。数字を入力してください。");
		        continue;
	        }
	        isValidLevel = (gameLevel >= 1 && gameLevel <= 5);
	        if ( !isValidLevel )
	        	System.out.println("無効です。" + MIN_LV + "～" + MAX_LV + "の数字を入力してください。");
		}
	}

	//敵の数値をランダムで取得する関数
	public static boolean getEnemyNum (int[] enemyNum)
	{
		if (enemyNum.length > 10 || enemyNum.length < 1)
			return false;

		enemyNum[0] = (int)(new Random().nextInt(9)+1);

		for (int i = 1; i < enemyNum.length;) {
			boolean duplicated = false;
			int eachDigit = (int)(new Random().nextInt(10));
			for (int j = 0; j < i - 1 ; j++) {
				if (eachDigit == enemyNum[j]) duplicated = true;
			}
			if ( !duplicated ) {
				enemyNum[i] = eachDigit;
				i++;
			}
		}
		return true;
	}

	//ユーザーに敵に挑戦する数値を入力させる関数
	public static void getUserNum (int[] userNum)
	{
		while(true) {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
	        String strNum = scanner.nextLine();

	        if (strNum.length() != gameLevel) {
	        	System.out.println("桁数が違います。" + gameLevel + "桁で数字を入力してください");
	        	continue;
	        }

	        try
	        {
	        	for (int i = 0; i < userNum.length; i++) {
		        	userNum[i] = Integer.parseInt(String.valueOf(strNum.charAt(i)));
	        	}
	        }catch(NumberFormatException e) {
		        System.out.println("無効です。" + gameLevel + "桁の数字を入力してください。");
		        continue;
	        }

	        boolean duplicated = false;
	        for (int i = 0; i < userNum.length; i++) {
	        	 for (int j = i + 1 ; j < userNum.length; j++) {
	        		 if (userNum[i] == userNum[j]) {
	        			 System.out.println("数字が重複しています。やり直してください。");
	        			 duplicated = true;
	        		}
	        	}
	        }
	        if ( !duplicated )	 break;
		}
        return;
	}
}
