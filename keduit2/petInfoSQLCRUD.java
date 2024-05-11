package com.keduit2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class petInfoSQLCRUD {
	static Connection conn;

	public petInfoSQLCRUD() throws Exception { // sql에 연결. 이미 테이블과 요소들이 삽입되어 있음.
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/sqldb";
		String userid = "root";
		String pwd = "1234";

		Class.forName(driver);
		System.out.println("드라이버 연결 준비...");
		conn = DriverManager.getConnection(url, userid, pwd);
		System.out.println("드라이버 연결 성공");
	}

	private void select(String x) throws SQLException { // 지역 검색
		PreparedStatement pstmt;
		String sql = "SELECT * FROM sqldb.petinfotable WHERE orgNm LIKE ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%" + x + "%");
		ResultSet rs = pstmt.executeQuery();

		if (!rs.isBeforeFirst()) {// 값이 없는 경우

			select2(x);

		} else {
			while (rs.next()) {
				System.out.print(rs.getString(1));
				System.out.print("\t" + rs.getString(2));
				System.out.print("\t" + rs.getString(3));
				System.out.print("\t" + rs.getString(4));
				System.out.print("\t" + rs.getString(5));
				System.out.print("\t" + rs.getString(6));
				System.out.print("\t" + rs.getString(7));
				System.out.print("\t" + rs.getString(8));
				System.out.print("\t" + rs.getString(9));
				System.out.print("\t" + rs.getString(10));
				System.out.print("\t" + rs.getString(11));
				System.out.println();
			}

			String countSql = "SELECT COUNT(*) FROM sqldb.petinfotable WHERE orgNm LIKE ?";
			PreparedStatement countPstmt = conn.prepareStatement(countSql);
			countPstmt.setString(1, "%" + x + "%");
			ResultSet countRs = countPstmt.executeQuery();
			if (countRs.next()) {
				int count = countRs.getInt(1);
				System.out.println("검색된 레코드 수: " + count);
			}
		}
		// countRs 및 countPstmt 관련 리소스 해제

		rs.close();
		pstmt.close();
	}

	private void select2(String y) throws SQLException { // 지역 검색
		PreparedStatement pstmt;
		String sql = "SELECT * FROM sqldb.petinfotable WHERE happenPlace LIKE ?;";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%" + y + "%");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.print(rs.getString(1));
			System.out.print("\t" + rs.getString(2));
			System.out.print("\t" + rs.getString(3));
			System.out.print("\t" + rs.getString(4));
			System.out.print("\t" + rs.getString(5));
			System.out.print("\t" + rs.getString(6));
			System.out.print("\t" + rs.getString(7));
			System.out.print("\t" + rs.getString(8));
			System.out.print("\t" + rs.getString(9));
			System.out.print("\t" + rs.getString(10));
			System.out.print("\t" + rs.getString(11));
			System.out.println();
		}

		String countSql = "SELECT COUNT(*) FROM sqldb.petinfotable WHERE happenPlace LIKE ?";
		PreparedStatement countPstmt = conn.prepareStatement(countSql);
		countPstmt.setString(1, "%" + y + "%");
		ResultSet countRs = countPstmt.executeQuery();
		if (countRs.next()) {
			int count = countRs.getInt(1);
			System.out.println("검색된 레코드 수: " + count);
		}

		// countRs 및 countPstmt 관련 리소스 해제

		rs.close();
		pstmt.close();
	}

	private void insert(petInfoField x) throws SQLException {
		PreparedStatement pstmt;
		String sql = "INSERT INTO sqldb.petinfotable(desertionNo, orgNm, kindCd, sexCd, colorCd, age, "
				+ "happenPlace, careNm, officetel, popfile, processState) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, x.getDesertionNo());
		pstmt.setString(2, x.getOrgNm());
		pstmt.setString(3, x.getKindCd());
		pstmt.setString(4, x.getSexCd());
		pstmt.setString(5, x.getColorCd());
		pstmt.setString(6, x.getAge());
		pstmt.setString(7, x.getHappenPlace());
		pstmt.setString(8, x.getCareNm());
		pstmt.setString(9, x.getOfficetel());
		pstmt.setString(10, x.getpopfile());
		pstmt.setString(11, x.getProcessState());

		int result = pstmt.executeUpdate();

		if (result == 1) {
			System.out.println("레코드 추가 성공");
		} else {
			System.out.println("레코드 추가 실패");
		}

		pstmt.close();
	}

	private void update(String desertionNo, petInfoField petInfo) throws SQLException {
		PreparedStatement pstmt;
		String sql = "UPDATE sqldb.petinfotable " + "SET orgNm = ?," + "kindCd = ?," + "sexCd = ?," + "colorCd = ?,"
				+ "age = ?," + "happenPlace = ?," + "careNm = ?," + "officetel = ?," + "popfile = ?,"
				+ "processState = ? " + "WHERE desertionNo = ?";
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, petInfo.getOrgNm());
		pstmt.setString(2, petInfo.getKindCd());
		pstmt.setString(3, petInfo.getSexCd());
		pstmt.setString(4, petInfo.getColorCd());
		pstmt.setString(5, petInfo.getAge());
		pstmt.setString(6, petInfo.getHappenPlace());
		pstmt.setString(7, petInfo.getCareNm());
		pstmt.setString(8, petInfo.getOfficetel());
		pstmt.setString(9, petInfo.getpopfile());
		pstmt.setString(10, petInfo.getProcessState());
		pstmt.setString(11, desertionNo);

		int result = pstmt.executeUpdate();
		if (result == 1) {
			System.out.println("레코드 수정 성공");
		} else {
			System.out.println("레코드 수정 실패");
		}
		pstmt.close();
	}

	private void deleteNum(String num) throws SQLException { // 번호를 선택해 삭제 메서드.
		String sql = "delete from sqldb.petinfotable where desertionNo = ?";
		PreparedStatement pstmt;
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, num); // 첫번째 ? = n : setint (n, num)
		int result = pstmt.executeUpdate();
		if (result == 1) {
			System.out.println(num + "번 유기번호 삭제 성공");
		} else {
			System.out.println(num + "번 유기번호 삭제 실패");
		}
		pstmt.close();
	}

	public static void main(String[] args) throws Exception {
		petInfoSQLCRUD A = new petInfoSQLCRUD();
		boolean user = true;
		boolean exitProgram = false;

		Scanner in = new Scanner(System.in);
		while (!exitProgram) {
			System.out.println("--------------------------------------------------------");
			System.out.println("1. 등록 | 2. 지역검색 | 3. 수정 | 4. 삭제 | 9. 종료 | 0. 관리자 전환");
			System.out.println("--------------------------------------------------------");

			int choice = in.nextInt();
			switch (choice) {
			case 1:
				if (user) {
					System.out.println("사용자는 목록 조회만 가능");
					break;
				}
				System.out.print("등록할 유기번호 입력: ");
				String desertionNo = in.next();
				System.out.print("등록할 지역 입력: ");
				String orgNm = in.next();
				System.out.print("등록할 품종 입력: ");
				String kindCd = in.next();
				System.out.print("등록할 성별 입력: ");
				String sexCd = in.next();
				System.out.print("등록할 색 입력: ");
				String colorCd = in.next();
				System.out.print("등록할 나이 입력: ");
				String age = in.next();
				System.out.print("등록할 발견장소 입력: ");
				String happenPlace = in.next();
				System.out.print("등록할 보호소명 입력: ");
				String careNm = in.next();
				System.out.print("등록할 보호소연락처 입력: ");
				String officetel = in.next();
				System.out.print("등록할 이미지 입력: ");
				String popfile = in.next();
				System.out.print("상태 입력 :(예: 보호중 혹은 보호종료) ");
				String processState = in.next();

				petInfoField petInfo = new petInfoField(desertionNo, orgNm, kindCd, sexCd, colorCd, age, happenPlace,
						careNm, officetel, popfile, processState);

				try {
					A.insert(petInfo);
					System.out.println("추가 성공");
				} catch (Exception e) {
					System.out.println("레코드 추가 실패: 이미 존재하는 유기번호입니다. 해당 desertionNo 삭제 후 다시 시도하세요.");
				}
				break;

			case 2:

				System.out.println("검색할 지역 입력");
				in.nextLine();
				String S = in.nextLine();
				System.out.println(S);
				A.select(S);
				break;
			case 3:
				if (user) {
					System.out.println("사용자는 목록 조회만 가능");
					break;
				}
				System.out.print("수정할 해당 유기번호 입력: ");
				String e = in.next();
				System.out.print("수정할 지역 입력: ");
				String f = in.next();
				System.out.print("수정할 품종 입력: ");
				String g = in.next();
				System.out.print("수정할 성별 입력: ");
				String h = in.next();
				System.out.print("수정할 색 입력: ");
				String i = in.next();
				System.out.print("수정할 나이 입력: ");
				String j = in.next();
				System.out.print("수정할 발견장소 입력: ");
				String k = in.next();
				System.out.print("수정할 보호소명 입력: ");
				String l = in.next();
				System.out.print("수정할 보호소 연락처 입력: ");
				String m = in.next();
				System.out.print("수정할 이미지 입력: ");
				String n = in.next();
				System.out.print("수정할 상태 입력: (예: 보호중 혹은 보호종료) ");
				String o = in.next();

				petInfoField petInfoToUpdate = new petInfoField(e, f, g, h, i, j, k, l, m, n, o);

				try {
					A.update(e, petInfoToUpdate);
					System.out.println("레코드 수정 성공");
				} catch (Exception ex) {
					System.out.println("레코드 수정 실패: 해당 유기번호가 존재하지 않습니다.");
				}
				break;
			case 4:
				if (user) {
					System.out.println("사용자는 목록 조회만 가능");
					break;
				}

				System.out.println("삭제할 유기번호 입력");
				in.nextLine();
				String s = in.nextLine();
				A.deleteNum(s);
				break;
			case 9:
				exitProgram = true;
				System.out.println("프로그램을 종료합니다.");
				break;
			case 0:
				System.out.println("비밀번호를 입력하시오");
				String pww = in.next();
				String pw1 = "123";
				if (pww.equals(pw1)) {
					user = false;
				} else {
					System.out.println("비밀번호 아님");
					break;
				}
				break;
			default:
				System.out.println("잘못된 선택입니다.");
			}
		}
		in.close();
	}
}
