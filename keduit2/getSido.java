package com.keduit2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Map<T,map<T,T>>
//울산광역시=6310000, 대전광역시=6300000, 광주광역시=6290000, 전라남도=6460000, 
//전북특별자치도=6540000, 강원특별자치도=6530000, 부산광역시=6260000, 경기도=6410000, 
//경상남도=6480000, 대구광역시=6270000, 서울특별시=6110000, 충청북도=6430000, 충청남도=6440000,
//제주특별자치도=6500000, 경상북도=6470000, 인천광역시=6280000, 세종특별자치시=5690000
public class getSido {
	static Map<String, String> sidoMap = new HashMap<String, String>();
	static Map<String, String> Sigungumap = new HashMap<String, String>();
	static Map<String, String> aniCode = Map.of("개", "417000", "고양이", "422400", "기타", "429900");
	static Map<String, String> dogCodemap = new HashMap<String, String>();
//	- 개 : 417000
//	 - 고양이 : 422400
//	 - 기타 : 429900

	public static void main(String[] args) throws IOException, ParseException {
//		String a = "20230601";
//		String b = "20240201";
//		String c = "6410000";
//		String d = "3940000";
//		String e = "000114";
//
//		Final(a, b, c, d, e);
//		Final();
//		getSido A = new getSido();
//		System.out.println(aniCode.get("개"));

//		A.getSido1();
//		System.out.println(sidoMap);
//		sidoMap.values();

//		A.getSigungu();
//		getdogcode();
//		System.out.println(dogCodemap);
	}

//    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+ serviceKey); /*Service Key*/
//    urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
//    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수 (1,000 이하)*/
//    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	protected static void getSido1() throws IOException {
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sido"); /* URL */
		String serviceKey = "mLbKOYq0dVKPXnuEyyF%2FxtoBsa6xTjrItxaUv%2BWrTre9elwiq%2FB4s1H%2FdCuBAUzb7eUf1wnCHKo9HaOmK9l3cA%3D%3D";
		// @formatter:off
	      urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+ serviceKey); /*Service Key*/
	      urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
	      urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수 (1,000 이하)*/
	      urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	        
	     // @formatter:on
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		JSONParser parser = new JSONParser();

		try {
			JSONObject jsonObj = (JSONObject) parser.parse(sb.toString());

			JSONObject response = (JSONObject) jsonObj.get("response"); // 배열 형태로 되어있는 데이터에서 배열만 추출하는 과정?
			JSONObject body = (JSONObject) response.get("body");
			JSONObject items = (JSONObject) body.get("items");
			JSONArray item = (JSONArray) items.get("item"); // 필요한 데이터들의 배열만 추출.

			for (int i = 0; i < item.size(); i++) {

				JSONObject petInfo = (JSONObject) item.get(i);
				String orgdownNm = (String) petInfo.get("orgdownNm"); // 도시명
//				System.out.println(orgdownNm);
				String orgCdString = (String) petInfo.get("orgCd"); // 도시 코드
//				Long orgCd = Long.parseLong(orgCdString);
//				System.out.println(orgCd);
				sidoMap.put(orgdownNm, orgCdString);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected static void getSigungu(String a) throws IOException {

		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sigungu"); /* URL */
		String serviceKey = "mLbKOYq0dVKPXnuEyyF%2FxtoBsa6xTjrItxaUv%2BWrTre9elwiq%2FB4s1H%2FdCuBAUzb7eUf1wnCHKo9HaOmK9l3cA%3D%3D";
		// @formatter:off
	 urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+ serviceKey); /*Service Key*/
     urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
     urlBuilder.append("&" + URLEncoder.encode("upr_cd","UTF-8") + "=" + URLEncoder.encode( a, "UTF-8"));
	        
	     // @formatter:on
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		JSONParser parser = new JSONParser();

		try {
			JSONObject jsonObj = (JSONObject) parser.parse(sb.toString());

			JSONObject response = (JSONObject) jsonObj.get("response"); // 배열 형태로 되어있는 데이터에서 배열만 추출하는 과정?
			JSONObject body = (JSONObject) response.get("body");
			JSONObject items = (JSONObject) body.get("items");
			JSONArray item = (JSONArray) items.get("item"); // 필요한 데이터들의 배열만 추출.

			for (int i = 0; i < item.size(); i++) {

				JSONObject petInfo = (JSONObject) item.get(i);
				String orgdownNm = (String) petInfo.get("orgdownNm"); // 도시명
				String orgCdString = (String) petInfo.get("orgCd"); // 도시 코드
//				Long orgCd = Long.parseLong(orgCdString);
				Sigungumap.put(orgdownNm, orgCdString);

			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static void getdogcode(String a) throws IOException {

		// @formatter:off
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/kind"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=mLbKOYq0dVKPXnuEyyF%2FxtoBsa6xTjrItxaUv%2BWrTre9elwiq%2FB4s1H%2FdCuBAUzb7eUf1wnCHKo9HaOmK9l3cA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("up_kind_cd","UTF-8") + "=" + URLEncoder.encode(a, "UTF-8")); /*축종코드 (개 : 417000, 고양이 : 422400, 기타 : 429900) (입력 시 데이터 O, 미입력 시 데이터 X)*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
     // @formatter:on
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
//		System.out.println(sb.toString());

		JSONParser parser = new JSONParser();

		try {
			JSONObject jsonObj = (JSONObject) parser.parse(sb.toString());

			JSONObject response = (JSONObject) jsonObj.get("response"); // 배열 형태로 되어있는 데이터에서 배열만 추출하는 과정?
			JSONObject body = (JSONObject) response.get("body");
			JSONObject items = (JSONObject) body.get("items");
			JSONArray item = (JSONArray) items.get("item"); // 필요한 데이터들의 배열만 추출.

			for (int i = 0; i < item.size(); i++) {

				JSONObject petInfo = (JSONObject) item.get(i);

//				System.out.println(petInfo.toString());

				// 수정: "kindCd" 대신 "kindCd"이 맞는지 확인

				String knm = (String) petInfo.get("knm"); //
//				System.out.println(kindCd);
				String kindCdString = (String) petInfo.get("kindCd"); // 도시 코드
//				Long kindCd = Long.parseLong(kindCdString);
//				System.out.println(knm);

				dogCodemap.put(knm, kindCdString);
//				System.out.println(dogCodemap);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected static void Final(String a, String b, String c, String d, String e) throws IOException, ParseException {
		// @formatter:off
//		 Final(Long a, Long b, Long c, Long d, Long e)
//		Long a = 20230601L;
//		 Long b = 20240201L;
//		 Long c = 6410000L;
//		 Long d = 3940000L;
//		 Long e = 000114L;
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=mLbKOYq0dVKPXnuEyyF%2FxtoBsa6xTjrItxaUv%2BWrTre9elwiq%2FB4s1H%2FdCuBAUzb7eUf1wnCHKo9HaOmK9l3cA%3D%3D"); /*Service Key*/
		
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수(1,000 이하)*/
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
		urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
		
		urlBuilder.append("&" + URLEncoder.encode("bgnde","UTF-8") + "=" + URLEncoder.encode(a, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("endde","UTF-8") + "=" + URLEncoder.encode(b, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("upr_cd","UTF-8") + "=" + URLEncoder.encode(c, "UTF-8")); // 시도코드
		if(d!=null) {
		urlBuilder.append("&" + URLEncoder.encode("org_cd","UTF-8") + "=" + URLEncoder.encode(d, "UTF-8"));} // 시군구코드
		if(e!=null) {
		urlBuilder.append("&" + URLEncoder.encode("kind","UTF-8") + "=" + URLEncoder.encode(e, "UTF-8"));}
		
	// @formatter:on
		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		String result = sb.toString();
		// System.out.println(sb.toString());

		JSONParser parser = new JSONParser(); // 읽어온 data를 json에서 java버전으로 파싱하는 과정.
		JSONObject jsonObj = (JSONObject) parser.parse(result);

		JSONObject response = (JSONObject) jsonObj.get("response"); // 배열 형태로 되어있는 데이터에서 배열만 추출하는 과정?
		JSONObject body = (JSONObject) response.get("body");
		JSONObject items = (JSONObject) body.get("items");
		JSONArray item = (JSONArray) items.get("item");

		for (int i = 0; i < item.size(); i++) {

			JSONObject petInfo = (JSONObject) item.get(i);
			String desertionNo = (String) petInfo.get("desertionNo");
			String kindCd = (String) petInfo.get("kindCd");
			System.out.println(i + " ");
			System.out.println("유기번호 : " + desertionNo);
			String orgNm = (String) petInfo.get("orgNm");
			System.out.println(orgNm);
			System.out.print(" 품종 : " + kindCd);
			String sexCd = (String) petInfo.get("sexCd");
			System.out.print(" 성별 : " + sexCd);
			String colorCd = (String) petInfo.get("colorCd");
			System.out.print(" 색상 : " + colorCd);
			String age = (String) petInfo.get("age");
			System.out.print(" 나이 : " + age);
			String happenPlace = (String) petInfo.get("happenPlace");
			System.out.println(" 발견장소: " + happenPlace);
			String careNm = (String) petInfo.get("careNm");
			String officetel = (String) petInfo.get("officetel");
			System.out.println("보호소: " + careNm + " " + officetel);
			String processState = (String) petInfo.get("processState");
			System.out.println("상태" + processState);
			String popfile = (String) petInfo.get("popfile");
			System.out.println("사진 : " + popfile);
//			String totalCount = (String) petInfo.get("totalCount");
//			System.out.println("총 검색 수 : " + totalCount);

		}
	}

}
