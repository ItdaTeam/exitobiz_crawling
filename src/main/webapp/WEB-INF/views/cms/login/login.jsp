<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="exito, 엑시토">
    <meta name="description" content="exito, 엑시토">
    <meta property="og:type" content="website">
    <meta property="og:title" content="exito, 엑시토">
    <meta property="og:description" content="엑시토 컨텐츠 관리 시스템">
    <meta property="og:image" content="">
    <title>엑시토 - 콘텐츠 관리 솔루션</title>
    <link rel="stylesheet" href="../css/reset.css">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="icon" type="image/x-icon" href="../image/favicon.ico" >
    <link rel="short icon" type="image/x-icon" href="../image/favicon.ico" >
    <link rel="stylesheet"href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp">
    <script src="https://code.jquery.com/jquery-2.2.4.js" integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI=" crossorigin="anonymous"></script>
    <script src="../js/common.js"></script>
    <script>
        <%
            Cookie[] cookies = request.getCookies();
            if(cookies !=null){
                for(Cookie tempCookie : cookies){
                    if(tempCookie.getName().equals("staff_id")){
                        //쿠키값으로 대신 로그인 처리함
                        %>
                    $.ajax({
                        url : '<%=request.getContextPath()%>/login/autoLogin',
                        async : false, // 비동기모드 : true, 동기식모드 : false
                        type : 'POST',
                        cache : false,
                        dataType : 'text',
                        data : {id : "<%=tempCookie.getValue()%>"},
                        success : function(data) {
                            location.href="/cms/staff";
                        },
                        error : function(request,status,error) {
                            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                        }
                    });
                    <%
                }
            }
            }
        %>

        function autoLoginCheck(){
            if($('#auto-login').val() =='off'){
                $('#auto-login').val('on');
            }else{
                $('#auto-login').val('off');
            }
        }

        function login()
        {
            if(!login_form.identity.value){
                alert("아이디를 입력하세요");
                login_form.identity.focus();
                return false;

            }else if(!login_form.password.value){
                alert("비밀번호를 입력하세요");
                login_form.password.focus();
                return false;
            }

            var params = {
                id 	: login_form.identity.value
                ,password	: login_form.password.value
                ,autoLogin : $('#auto-login').val()
            }

            var response = $.ajax({
                            url : "/login/login",
                            async : true, // 비동기모드(화면전환 X) : true, 동기식모드 : false
                            type : 'POST',
                            cache : false,
                            dataType : 'text',
                            data : params,
                            success : function(data) {
                                if(data == "/cms/main"){
                                    var jwt = response.getResponseHeader('X-AUTH-TOKEN');
                                    localStorage.setItem('jwt', jwt);
                                    location.href = data;
                                }else {
                                    alert(data);
                                }
                            },
                            error : function(request,status,error) {
                                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                            }
                        });
        }

        function enterkey() {
            if (window.event.keyCode == 13) {
                login();
            }
        }
    </script>
</head>
<body>
    <div class="login_wrap">
        <!--로그인 영역-->
        <div class="login_box">
            <div class="login_box-lt">
                <h2>Welcome to CMS</h2>
            </div>
            <div class="login_box-rt">
                <p>원활한 사용을 위해 <br> 로그인을 해주세요 </p>
                <form action="#" method="post" name="login_form">
                    <fieldset class="login_fld">
                        <legend class="blind"> 로그인</legend>
                        <label for="identity">아이디</label>
                        <input type="text" class="icon" id="identity" name="identity" placeholder="아이디를 입력하세요" onkeyup="enterkey();"><span class="material-icons-round">person_outline </span>
                        <label for="password">비밀번호</label>
                        <input type="password" class="icon" id="password" name="password" placeholder="비밀번호를 입력하세요" onkeyup="enterkey();"><span class="material-icons-round icon">lock</span>
                        <div class="login_check">
                            <input type="checkbox" id="auto-login" name="auto-login" value="off" onchange="autoLoginCheck()">
                            <label for="auto-login">자동 로그인</label>
                        </div>
                        <button type="button" class="login_btn" onClick="login()"><span>LOGIN</span></button>
                    </fieldset>
                </form>
            </div>
        </div>
        <div class="terms_area">
            <a href="/standard">이용약관</a>
            <a href="/personinfo">개인정보처리약관</a>
            <a href="/locationinfo">위치기반서비스이용약관</a>
            <a href="/marketingInfo">마케팅정보수신동의</a>
        </div>
    </div>
</body>
</html>
