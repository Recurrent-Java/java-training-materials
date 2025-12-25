<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>Javaプログラミング応用　確認テスト</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/html5reset-1.6.1.css" media="all">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/java-extra_99.css" media="all">
</head>
<body>
  <header><h1>校内利用者登録フォーム</h1></header>
  <main id="regist">
    <form action="${pageContext.request.contextPath}/FormServlet" method="post">
      <c:if test="${ not empty errList }">
        <ul>
        <c:forEach var="errMsg" items="${ errList }">
          <li>${ errMsg }</li>
        </c:forEach>
        </ul>
      </c:if>
      <p><label for="name">名前<span class="badge">必須</span>：</label><input type="text" name="name" value="${ member.name }" id="name"></p>
      <p><label for="age">年齢<span class="badge">必須</span>：</label><input type="tel" name="age" value="${ member.age }" id="age"></p>
      <p>性別<span class="badge">必須</span>：
        <input type="radio" name="gender" value="male" id="male" ${ member.gender eq "male" ? "checked" : "" }><label for="male">男性</label>
        <input type="radio" name="gender" value="female" id="female" ${ member.gender eq "female" ? "checked" : "" }><label for="female">女性</label>
        <input type="radio" name="gender" value="other" id="other" ${ member.gender eq "other" ? "checked" : "" }><label for="other">その他</label>
      </p>
      <p><label>区分<span class="badge">必須</span>：</label>
        <select name="role">
          <option value="0" ${ member.role eq "0" ? "selected" : "" } hidden>---- 選択 ----</option>
          <option value="1" ${ member.role eq "1" ? "selected" : "" }>管理者</option>
          <option value="2" ${ member.role eq "2" ? "selected" : "" }>講師</option>
          <option value="3" ${ member.role eq "3" ? "selected" : "" }>受講生</option>
        </select>
      </p>
      <p>ご利用のサービス：
        <input type="checkbox" name="services" value="1" id="mail" ${ member.checkServices("1") ? "checked" : "" }><label for="mail">メール</label>
        <input type="checkbox" name="services" value="2" id="chat" ${ member.checkServices("2") ? "checked" : "" }><label for="chat">チャット</label>
        <input type="checkbox" name="services" value="3" id="portal" ${ member.checkServices("3") ? "checked" : "" }><label for="portal">ポータル</label>
      </p>
      <p>メール配信希望：
        <input type="radio" name="mailFlg" value="yes" id="yes" checked><label for="yes">希望する</label>
        <input type="radio" name="mailFlg" value="no" id="no" ${ member.mailFlg eq "no" ? "checked" : "" }><label for="no">希望しない</label>
      </p>
      <p class="cntbtn">
        <button type="submit" name="sendKind" value="cancel">取り消し</button>
        <button type="submit" name="sendKind" value="confirm">確認</button>
      </p>
    </form>
  </main>
</body>
</html>
