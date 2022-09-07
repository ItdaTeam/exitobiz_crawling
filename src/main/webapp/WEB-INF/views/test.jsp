<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="kr" dir="ltr">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="author" content="스타트업의 성공을 위해 기획, 교육, 컨설팅까지 제공하는 든든한 파트너사">
  <meta name="keywords" content="잇다, 스타트업, 앱개발, 웹개발, IT, ITDA, 창업컨설팅">
  <meta name="description" content="스타트업의 성공을 위해 기획, 교육, 컨설팅까지 제공하는 든든한 파트너사">
  <meta property="og:type" content="website">
  <meta property="og:title" content="ITDA 잇다">
  <meta property="og:description" content="스타트업의 성공을 위해 기획, 교육, 컨설팅까지 제공하는 든든한 파트너사">
  <meta property="og:image" content="/img/ogimg.png">
  <meta property="og:url" content="https://exitobiz.co.kr">
  <link rel="shortcut icon" type="image/x-icon" href="/img/favicon.ico">
  <link rel="apple-touch-icon" href="/img/favicon.ico">
  <link rel="apple-touch-icon-precomposed" href="/img/favicon.ico">
  <link rel="canonical" href="https://exitobiz.co.kr">
  <title>ITDA 잇다</title>

  <!-- 스타일 -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
  <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
  <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
  <link rel="stylesheet" href="/css/id.css">

  <!-- js -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
  <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
  <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
  <script src="/js/id.js"></script>

 <script>
$("#btn").click(function(){
    
    var formData = new FormData($("#fileForm")[0]);
 
    $.ajax({
        type : 'get',
        url : '/file/uploadFile' + "?filePath=/",
        data : formData,
        processData : false,
        dataType : "json",
        contentType : false,
        async    : false,
        success : function(data) {
            alert("파일 업로드 성공.");
        },
        error : function(error) {
            alert("파일 업로드에 실패하였습니다.");
           
        }
    });      
 
});  

$("#delete").click(function(){
     
    param = {
        filePath : "/test",
        fileName : "1.png"
    }

    $.ajax({
        type : 'post',
        url : '/file/deleteFile",
        data : formData,
        processData : false,
        dataType : text,
        contentType : false,
        async    : false,
        success : function(data) {
            alert("파일 삭제 성공.");
        },
        error : function(error) {
            alert("파일 업로드에 실패하였습니다.");
           
        }
    });      
 
}); 
</script>
</head>

<body>
<form id="fileForm" action="/test/file/uploadAjax" method="post" enctype="multipart/form-data">
<input type="file">
<button type="button" id="btn" onclick="test();">전송</button>
<button type="button" id="delete" onclick="delete();">삭제</button>
<form>
</body>

</html>
