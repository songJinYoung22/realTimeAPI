package com.keduit2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class petInfo {
	public static void main(String[] args) throws IOException {

		// @formatter:off
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=mLbKOYq0dVKPXnuEyyF%2FxtoBsa6xTjrItxaUv%2BWrTre9elwiq%2FB4s1H%2FdCuBAUzb7eUf1wnCHKo9HaOmK9l3cA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수 (1,000 이하)*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        
        urlBuilder.append("&" + URLEncoder.encode("state","UTF-8") + "=" + URLEncoder.encode("protect", "UTF-8"));
//        urlBuilder.append("&" + URLEncoder.encode("upr_cd","UTF-8") + "=" + URLEncoder.encode("6530000", "UTF-8")); // 시도코드
//        urlBuilder.append("&" + URLEncoder.encode("org_cd","UTF-8") + "=" + URLEncoder.encode("4060000", "UTF-8")); // 시군구코드
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
				String desertionNo = (String) petInfo.get("desertionNo");
				String kindCd = (String) petInfo.get("kindCd");
				System.out.println(i + " ");
				System.out.println("유기번호 : " + desertionNo);
				String orgNm = (String) petInfo.get("orgNm");
				System.out.println(orgNm);

				String weight = (String) petInfo.get("weight");
				System.out.print(" 품종 : " + kindCd);
				String sexCd = (String) petInfo.get("sexCd");
				System.out.print(" 무게 : " + weight);

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
				String filename = (String) petInfo.get("filename");
				System.out.println("사진 : " + filename);
				String processState = (String) petInfo.get("processState");
				System.out.println("상태" + processState);
//				String totalCount = (String) petInfo.get("totalCount");
//				System.out.println("총 검색 수 : " + totalCount);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}