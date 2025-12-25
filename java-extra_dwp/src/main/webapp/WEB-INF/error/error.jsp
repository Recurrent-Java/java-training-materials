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
  <main id="error">
    <h2>エラー発生</h2>
    <p>${ errMsg }</p>
    <p class="cntbtn"><button type="button" onclick="location.href='${pageContext.request.contextPath}/FormServlet'">校内利用者登録フォームへ戻る</button></p>
  </main>
</body>
</html>