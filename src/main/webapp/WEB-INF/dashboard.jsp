<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<div class="container">
<p>Welcome ${currentUser.firstName}</p>
<hr>
<h3>Upload An Image</h3>
<p>${message}</p>
<form method="POST" action="/dashboard/upload" enctype="multipart/form-data">
<div class="form-group">
	<input type="file" name="pic" class="form-control-file" id="inlineFormInput">
</div>
<div class="col-auto">
	<textarea name="description" class="form-control" id="exampleFormControlTextarea1" placeholder="Enter a Description"></textarea>
</div>
<button class="btn btn-primary">Upload Pic!</button>
</form>
<hr>
<p>Pics Uploaded By User</p>
<c:forEach items="${currentUser.pics}" var="pic">
<p>${pic.user.firstName} posted:</p>
<img src="${pic.image_url}" height="400" width="300"/>
<p>${pic.description}</p>
<b>Posted <fmt:formatDate type="date" value="${pic.createdAt}" />  </b>
<hr>
</c:forEach>
</div>
</body>
</html>