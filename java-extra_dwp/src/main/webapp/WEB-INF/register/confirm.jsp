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
	<main id="confirm">
		<h2>&lt;登録内容のご確認&gt;</h2>
		<table>
			<tr>
				<th>項　　目</th>
				<th>内　　容</th>
			</tr>
			<tr>
				<th>名　　前</th>
				<td>${member.name}</td>
			</tr>
			<tr>
				<th>年　　齢</th>
				<td>${member.age}歳</td>
			</tr>
			<tr>
				<th>性　　別</th>
				<td>${member.showGenderText()}</td>
			</tr>
      <tr>
        <th>区　　分</th>
        <td>${member.showRoleText()}</td>
      </tr>
			<tr>
				<th>ご利用のサービス</th>
				<td>${member.showCheckedServicesText()}</td>
			</tr>
			<tr>
				<th>メール配信希望</th>
				<td>${member.showMailFlgText()}</td>
			</tr>
		</table>
		<form  class="cntbtn" action="${pageContext.request.contextPath}/FormServlet" method="post">
      <button type="submit" name="sendKind" value="edit">修正</button>
			<button type="submit" name="sendKind" value="register">登録</button>
		</form>
	</main>
</body>
</html>