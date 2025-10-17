package fileupload;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import common.JDBConnect;
import model1.board.BoardDTO;

public class MyFileDAO extends JDBConnect {
	
	public int insertFile(MyFileDTO dto) {
		int applyResult = 0;
		PreparedStatement psmt = null;
		
		try {
			String query = "INSERT INTO myfile ( "
					+ " title, cate, ofile, sfile) "
					+ " VALUES (?, ?, ?, ?)";
			psmt = getCon().prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getCate());
			psmt.setString(3, dto.getOfile());
			psmt.setString(4, dto.getSfile());

			applyResult = psmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("파일 등록 중 예외 발생:");
			e.printStackTrace();
		}
		return applyResult;
	}
	
	//파일 목록을 반환
	public List<MyFileDTO> myFileList(){
		
		List<MyFileDTO> fileList = new Vector<MyFileDTO>();
		String query = "SELECT * FROM myfile ORDER BY idx DESC";
		
		try {
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();
							
			while(rs.next()) {
				MyFileDTO dto = new MyFileDTO();
				
				dto.setIdx(rs.getInt("idx"));
				dto.setTitle(rs.getString("title"));
				dto.setCate(rs.getString("cate"));
				dto.setOfile(rs.getString("ofile"));
				dto.setSfile(rs.getString("sfile"));
				dto.setPostdate(rs.getString("postdate"));
				
				fileList.add(dto);
			}

		}catch (Exception e) {
			System.out.println("SELECT 시 예외 발생");
			e.printStackTrace();
		}
	
	
		return fileList; //목록 반환
		
	}

}
