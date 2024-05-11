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

public class test111 {
	public static void main(String[] args) throws IOException {
		// @formatter:off
	        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/kind"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=mLbKOYq0dVKPXnuEyyF%2FxtoBsa6xTjrItxaUv%2BWrTre9elwiq%2FB4s1H%2FdCuBAUzb7eUf1wnCHKo9HaOmK9l3cA%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("up_kind_cd","UTF-8") + "=" + URLEncoder.encode("417000", "UTF-8")); /*축종코드 (개 : 417000, 고양이 : 422400, 기타 : 429900) (입력 시 데이터 O, 미입력 시 데이터 X)*/
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
		System.out.println(sb.toString());
		JSONParser parser = new JSONParser();

		try {
			JSONObject jsonObj = (JSONObject) parser.parse(sb.toString());

			JSONObject response = (JSONObject) jsonObj.get("response"); // 배열 형태로 되어있는 데이터에서 배열만 추출하는 과정?
			JSONObject body = (JSONObject) response.get("body");
			JSONObject items = (JSONObject) body.get("items");
			JSONArray item = (JSONArray) items.get("item"); // 필요한 데이터들의 배열만 추출.

			for (int i = 0; i < item.size(); i++) {

				JSONObject petInfo = (JSONObject) item.get(i);

				String kindCd = (String) petInfo.get("kindCd"); // 도시명
				System.out.println(kindCd);
				String knm = (String) petInfo.get("knm"); // 도시 코드
//				Long KNm = Long.parseLong(KNm);
				System.out.println(knm);

			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
