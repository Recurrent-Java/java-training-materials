package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberDAO {
  public void insertAll(String name, int age, String gender, String role, String[] services, String mailFlg)
      throws Exception {
    try (Connection conn = DBManager.getConnection()) {
      // Auto-commit OFF
      conn.setAutoCommit(false);
      // SQL文
      String sql = """
            INSERT INTO member (
              name
            , age
            , gender
            , role
            , dm
            )
            VALUES (
              ?
            , ?
            , ?
            , ?
            , ?)
          """;
      PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      // Member情報を設定
      pst.setString(1, name);
      pst.setInt(2, age);
      pst.setString(3, gender);
      pst.setString(4, role);
      pst.setString(5, mailFlg);
      // クエリの発行
      int result = pst.executeUpdate();

      if (result != 0) {
        // 自動採番されたキーを取得
        ResultSet rs = pst.getGeneratedKeys();
        long generatedId = 0L;
        if (rs.next()) {
          generatedId = rs.getLong(1);
          System.out.println("登録されたID: " + generatedId);
        }
        if (generatedId == 0) {
          // PreparedStatementをクローズ
          pst.close();
          // ロールバック
          conn.rollback();
          // Auto-commit ON
          conn.setAutoCommit(true);
          throw new Exception("登録に失敗しました。");
        }
        /** 利用しているサービスの登録 */
        sql = "INSERT INTO service(member_id, service_cd) VALUES (?, ?)";
        pst = conn.prepareStatement(sql);
        // 発行
        for (String srv : services) {
          pst.setLong(1, generatedId);
          pst.setString(2, srv);
          result = pst.executeUpdate();
          if (result == 0) {
            // PreparedStatementをクローズ
            pst.close();
            // ロールバック
            conn.rollback();
            // Auto-commit ON
            conn.setAutoCommit(true);
            throw new Exception("登録に失敗しました。");
          }
        }
        // 登録処理完了
        conn.commit();
        // Auto-commit ON
        conn.setAutoCommit(true);
        // PreparedStatementをクローズ
        pst.close();
      }
    } catch (SQLException sqe) {
      throw new Exception("データベースエラー：<br>" + sqe.getMessage());
    }
  }
}
