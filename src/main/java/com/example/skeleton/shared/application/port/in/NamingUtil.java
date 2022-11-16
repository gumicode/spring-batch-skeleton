package com.example.skeleton.shared.application.port.in;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class NamingUtil {

	private static final List<String> FOOD_PREFIXES = Arrays.asList("맛있는", "싱싱한", "달콤한", "짜릿한", "부드러운", "감칠맛나는",
			"시큼한", "향기로운", "딱딱한", "시린", "매운", "화끈한", "얼큰한", "시원한", "촉촉한", "감미로운", "사르르녹는", "달달한", "고소한",
			"딸기향", "바바나향", "초코향", "초코맛", "바나나맛", "바닐라맛", "다양한", "신기한", "알수없는", "레몬향", "레몬맛", "두꺼운", "얇은",
			"가느다란", "치즈맛", "치즈향", "고추맛", "새콤한", "신비한", "맛잇는", "맛없는", "독특한", "사과향", "사과맛", "모락모락");

	private static final List<String> FOODS = Arrays.asList("콜라", "사이다", "곱창", "두부", "돈까스", "가츠동", "탕수육", "김밥",
			"라면", "국밥", "피자", "치킨", "족발", "막국수", "주스", "떡볶이", "튀김", "가래떡", "연두부", "아이스크림", "초코칩", "포카칩",
			"감자칩", "쿠키", "오레오", "포키", "양념감자", "감자", "고구마", "레몬", "쌀밥", "공기밥", "젤리", "포도", "사과", "귤", "감",
			"감귤", "바나나", "치즈", "도토리", "묵", "가라아케", "자장면", "짬뽕", "백반", "고사리", "시금치", "콩나물", "멸치", "미역국",
			"덮밥", "소불고기", "된장", "제육볶음", "생선회", "연어", "광어", "초밥", "새우", "딱새우", "짜파게티", "진라면", "펩시", "전",
			"비엔나소시지", "햄", "소시지", "닦볶음탕", "계란", "감자전", "아몬드", "고량주", "소주", "맥주", "와인", "위스키", "데낄라");

	public static String food() {
		SecureRandom secureRandom = new SecureRandom();
		int foodPrefixIndex = secureRandom.nextInt(FOOD_PREFIXES.size() - 1);
		int foodsIndex = secureRandom.nextInt(FOODS.size() - 1);
		return FOOD_PREFIXES.get(foodPrefixIndex) + " " + FOODS.get(foodsIndex);
	}
}
