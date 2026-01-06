package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import beans.Member;
import dao.MemberDAO;

/**
 * Servlet implementation class FormServlet
 */
@WebServlet("/FormServlet")
public class FormServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 送信されたデータのエンコーディングを指定（文字化け対策）
    request.setCharacterEncoding("UTF-8");

    // セッションスコープの破棄
    HttpSession session = request.getSession();
    session.invalidate();

    // 画面遷移 (form.jsp)
    String path = "/WEB-INF/register/form.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(path);
    dispatcher.forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // 送信されたデータのエンコーディングを指定（文字化け対策）
    request.setCharacterEncoding("UTF-8");
    // 送信種別
    String sendKind = request.getParameter("sendKind");
    if (sendKind == null || sendKind.isEmpty() || sendKind.equals("cancel")) {
      /* 送信種別なし or 取り消し */
      // form.jspへ画面遷移
      response.sendRedirect("/java-extra_dwp/FormServlet");
    }
    /* 送信種別あり */
    else {
      // セッションスコープ取得
      HttpSession session = request.getSession();
      // 画面遷移先
      String path = null;
      // Memberオブジェクト格納用
      Member member = null;
      // 送信種別チェック
      switch (sendKind) {
        // 送信ボタン押下時
        case "confirm":
          /** 入力情報取得 */
          // 名前
          String name = request.getParameter("name");
          // 年齢
          String age = request.getParameter("age");
          // 性別
          String gender = request.getParameter("gender");
          // 区分
          String role = request.getParameter("role");
          // 利用サービス
          String[] services = request.getParameterValues("services");
          // メール配信希望
          String mailFlg = request.getParameter("mailFlg");
          // Memberオブジェクトのインスタンス化
          member = new Member(name, age, gender, role, services, mailFlg);

          /** 入力エラーチェック */
          List<String> errList = checkInputData(member);
          if (errList.size() != 0) {
            // リクエストスコープに入力情報セット
            request.setAttribute("member", member);
            // リクエストスコープにエラーリストセット
            request.setAttribute("errList", errList);
            path = "/WEB-INF/register/form.jsp";
          } else {
            // セッションスコープにMemberオブジェクトセット
            session.setAttribute("member", member);
            // 画面遷移先設定
            path = "/WEB-INF/register/confirm.jsp";
          }
          break;
        // 登録ボタン押下時
        case "register":
          try {
            // セッションスコープよりユーザ情報取得
            member = (Member) session.getAttribute("member");
            // MemberDAOのインスタンス化
            MemberDAO memberDao = new MemberDAO();
            // 登録
            memberDao.insertAll(
                member.getName(),
                Integer.parseInt(member.getAge()),
                member.getGender(),
                member.getRole(),
                member.getServices(),
                member.getMailFlg());
            // セッションスコープの破棄
            session.invalidate();
            // 画面遷移先設定
            path = "/WEB-INF/register/completed.jsp";
          } catch (Exception e) {
            // エラーメッセージをリクエストスコープへセット
            request.setAttribute("errMsg", e.getMessage());
            // 画面遷移先設定
            path = "/WEB-INF/error/error.jsp";
          }
          break;
        // 修正ボタン押下時
        case "edit":
          // 画面遷移 (form.jsp)
          path = "/WEB-INF/register/form.jsp";
          break;
      }

      // 画面遷移
      RequestDispatcher dispatcher = request.getRequestDispatcher(path);
      dispatcher.forward(request, response);
    }
  }

  /** 内部メソッド */
  // 入力エラーチェック
  private List<String> checkInputData(Member member) {
    // エラーリスト
    List<String> errList = new ArrayList<>();

    // 名前
    String name = member.getName();
    if (name == null || name.isEmpty()) {
      errList.add("名前を入力してください。");
    }

    // 年齢  
    try {
      String strAge = member.getAge();
      // 年齢の変換（文字列 -> 数値)
      int age = Integer.parseInt(strAge);
      // 年齢チェック（18歳以上を許可）
      if (age < 18) {
        errList.add("18歳未満の方はご利用できません。");
      }
    } catch (NumberFormatException e) {
      errList.add("年齢を正しく入力してください。");
    }
    // 性別
    String gender = member.getGender();
    if (gender == null || gender.isEmpty()) {
      errList.add("性別を入力してください。");
    }
    // 区分
    String role = member.getRole();
    if (role.equals("0")) {
      errList.add("区分を選択してください");
    }

    /** リターン */
    return errList;
  }
}
