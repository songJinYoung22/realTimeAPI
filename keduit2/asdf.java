package com.keduit2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class asdf {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/sqldb";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1234";

	public static void main(String[] args) throws IOException, ParseException {
		try {
			// MySQL 연결
			Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

//			// 테이블 생성
//			createTable(connection);

			// 데이터 삽입
			insertCampsiteData(connection);

			// 연결 닫기
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void insertCampsiteData(Connection connection) throws SQLException, IOException, ParseException {
// json 자료의 배열만 사용하면 되니 배열을 꺼내는 과정.
// 마무리로 반복문으로 배열 자료의 필요한 요소들만 추출해 테이블에 입력하는 과정.
// 이로서 api의 주소로부터 필요한 자료만 sql의 테이블(생성도 함.)에 넣는 과정의 클레스.
		// @formatter:off
					StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic"); /*URL*/
			        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=mLbKOYq0dVKPXnuEyyF%2FxtoBsa6xTjrItxaUv%2BWrTre9elwiq%2FB4s1H%2FdCuBAUzb7eUf1wnCHKo9HaOmK9l3cA%3D%3D"); /*Service Key*/
			        urlBuilder.append("&" + URLEncoder.encode("noticeSdt","UTF-8") + "=" + URLEncoder.encode("20240101", "UTF-8"));
			        urlBuilder.append("&" + URLEncoder.encode("noticeEdt","UTF-8") + "=" + URLEncoder.encode("20240130", "UTF-8"));
			        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수(1,000 이하)*/
			        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("3", "UTF-8")); /*페이지 번호*/
			        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
			        urlBuilder.append("&" + URLEncoder.encode("state","UTF-8") + "=" + URLEncoder.encode("protect", "UTF-8"));
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
//				System.out.println(sb.toString());

		JSONParser parser = new JSONParser(); // 읽어온 data를 json에서 java버전으로 파싱하는 과정.
		JSONObject jsonObj = (JSONObject) parser.parse(result);

		JSONObject response = (JSONObject) jsonObj.get("response"); // 배열 형태로 되어있는 데이터에서 배열만 추출하는 과정?
		JSONObject body = (JSONObject) response.get("body");
		JSONObject items = (JSONObject) body.get("items");
		JSONArray item = (JSONArray) items.get("item"); // 필요한 데이터들의 배열만 추출.

// 데이터 삽입
		String insertSQL = "INSERT IGNORE INTO PetInfoTable (desertionNo, kindCd, weight, sexCd, colorCd, age, happenPlace, careNm, officetel, filename, processState) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			for (int i = 0; i < item.size(); i++) {
				JSONObject petInfo = (JSONObject) item.get(i);
				preparedStatement.setString(1, (String) petInfo.get("desertionNo"));
				preparedStatement.setString(2, (String) petInfo.get("kindCd"));
				preparedStatement.setString(3, (String) petInfo.get("weight"));
				preparedStatement.setString(4, (String) petInfo.get("sexCd"));
				preparedStatement.setString(5, (String) petInfo.get("colorCd"));
				preparedStatement.setString(6, (String) petInfo.get("age"));
				preparedStatement.setString(7, (String) petInfo.get("happenPlace"));
				preparedStatement.setString(8, (String) petInfo.get("careNm"));
				preparedStatement.setString(9, (String) petInfo.get("officetel"));
				preparedStatement.setString(10, (String) petInfo.get("filename"));
				preparedStatement.setString(11, (String) petInfo.get("processState"));
				preparedStatement.executeUpdate();
			}
		}

		System.out.println("데이터가 MySQL 테이블에 성공적으로 삽입되었습니다.");

	}
}
