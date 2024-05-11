package com.keduit2;

import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

public class aipTest extends getSido {
	public static void main(String[] args) throws IOException, ParseException {
		Scanner in = new Scanner(System.in);
		int condition = 1;
		String sidoCode = null;
		String SigunguCode = null;
		String dogcode = null;
		String dogkcode = null;

		try {
			getSido1();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("공고시작일 입력 (yyyymmdd)");
		String noticeSdt = in.next();

		System.out.println("공고 종료일 입력 (yyyymmdd)");
		String noticeEdt = in.next();
		while (true) {
			switch (condition) {
			case 1:
				sidoMap.keySet().forEach(key -> System.out.print(key + " "));
				System.out.println();
				System.out.println("보기 중 시,도 입력");
				String sido = in.next();
				if (sidoMap.containsKey(sido)) {
					sidoCode = sidoMap.get(sido);
					System.out.println(sidoCode);
					condition = 2; // condition을 2로 변경합니다.
					break; // switch 문을 빠져나가도록 합니다.
				} else {
					System.out.println("다시입력");
					condition = 1;
					break;
				}
			case 2:
				System.out.println("더 상세한 지역 검색원할 시 1 입력 아님 0");
				int q = in.nextInt();
				if (q == 1) {
					getSigungu(sidoCode);
//					System.out.println(Sigungumap);
					Sigungumap.keySet().forEach(key -> System.out.print(key + " "));
					System.out.println();
					System.out.println("보기 중 시,군,구 입력");
					String Sigungu = in.next();
					if (Sigungumap.containsKey(Sigungu)) {
						SigunguCode = Sigungumap.get(Sigungu);
//						System.out.println(SigunguCode);
						condition = 3; // condition을 2로 변경합니다.
						break; // switch 문을 빠져나가도록 합니다.
					} else {
						System.out.println("다시입력");
						condition = 2;
						break;
					}
				}
			case 3:
				System.out.println("개, 고양이, 기타 중 입력 0 입력시 전체검색");
				String ani = in.next();

				if (ani.equals("개")) {
					dogcode = aniCode.get("개");
					condition = 4;
					break;
				} else if (ani.equals("고양이")) {
					dogcode = aniCode.get("고양이");
					condition = 4;
				} else if (ani.equals("기타")) {
					dogcode = aniCode.get("기타");
					condition = 4;
				} else
					condition = 5;
				break;
			case 4:
				System.out.println("품종 선택 시 1, 전체 검색 시 0");
				int S = in.nextInt();
				if (S == 1) {
//					System.out.println(dogcode);
					getdogcode(dogcode);
					dogCodemap.keySet().forEach(key -> System.out.print(key + ", "));
					System.out.println();
					System.out.println("보기 중 품종 입력");
					String D = in.next();
					if (dogCodemap.containsKey(D)) {
						dogkcode = dogCodemap.get(D);
//						System.out.println(dogkcode + "응애");

//						System.out.println(SigunguCode);
						condition = 5; // condition을 2로 변경합니다.
						break; // switch 문을 빠져나가도록 합니다.
					} else {
						System.out.println("다시입력. 품종 선택 시 1, 전체 검색 시 0");
						condition = 4;
						break;
					}
				}
			case 5:
				Final(noticeSdt, noticeEdt, sidoCode, SigunguCode, dogkcode);
				condition = 0;
			default:
				break;
			}
//			in.close();
		}
	}
}