<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="kr" dir="ltr">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="yes" name="apple-mobile-web-app-capable" />
  <meta content="minimum-scale=1.0, width=device-width, maximum-scale=1, user-scalable=no" name="viewport" />
  <meta name="author" content="성공의 문을 열고 나가다, 엑시토">
  <meta name="keywords" content="엑시토,스타트업,지원사업,공급기업, 공급업체">
  <meta name="description" content="전국 지원사업 정보, 시제품 제작 기업 정보 스타트업 정보의 바다, 엑시토">
  <meta name="robots" content="엑시토">
  <meta name="title" content="엑시토">
  <meta name="subject" content="스타트업,성공의 문을 열고 나가다. EXITO">
  <meta property="og:type" content="website">
  <meta property="og:title" content="스타트업,성공의 문을 열고 나가다.EXITO">
  <meta property="og:description" content="전국 지원사업 정보, 시제품 제작 기업 정보 스타트업 정보의 바다, 엑시토">
  <meta property="og:image" content="/img/ogimg.png">
  <meta property="og:url" content="https://exitobiz.co.kr/">
  <%-- 페이스북 --%>
  <meta name="facebook-domain-verification" content="82ofka826hrn1lygtasbnkowydg8i6" />
  <%-- 네이버 --%>
  <meta name="naver-site-verification" content="2020d64d024cfbaedd37bd131b11843f21b94b4b" />
  <title> EXITO 엑시토</title>

  <!-- 스타일 -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
  <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
  <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
  <link rel="stylesheet" href="css/id.css">
  <link rel="stylesheet" href="css/landing.css">

  <!-- js -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
  <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
  <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
  <script src="js/id.js"></script>

  <!-- Global site tag (gtag.js) - Google Analytics -->
  <script async src="https://www.googletagmanager.com/gtag/js?id=UA-221415003-1"> </script>

  <script> window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', 'UA-221415003-1');
  </script>
  <!-- Global site tag (gtag.js) - Google Analytics -->
  <script async src="https://www.googletagmanager.com/gtag/js?id=G-RLDS2JPV4N"></script>
  <script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', 'G-RLDS2JPV4N');
  </script>
</head>

<body>

  <header class="header policy_header">
    <div class="inner">
      <a href="/" class="logo"><img src="/img/logo2.png" alt=""></a>

      <p class="menu">위치기반서비스이용약관</p>
    </div>
  </header>


  <div class="policy_wrap">
    <div class="inner">
      <ul class="policy_menu">
        <li><a href="/standard">이용약관</a></li>
        <li><a href="/personinfo">개인정보처리약관</a></li>
        <li><a href="/locationinfo" class="current">위치기반서비스이용약관</a></li>
        <li><a href="/marketingInfo">마케팅정보수신동의</a></li>
      </ul>

      <div class="policy_cont">
        <%=request.getAttribute("termLocate")%>
      </div>
    </div>
  </div>

  <footer class="landing_footer" >
    <div class="innr">
      <div class="finfo">
        <p>
          <strong>엑시토</strong>&nbsp; &nbsp;
          대표 : 권기정&nbsp; &nbsp; 개인정보 책임자 : (주)CTNS&nbsp; &nbsp; <span>사업자등록번호 : 307-81-50055</span> 
        </p>
        <p>고객센터 : <a href="tel:031-784-8443">031-784-8443</a></p>
        <p>본사 : 경상남도 창원시 의창구 평산로 23 신화테크노밸리 6층 639~641호 / <a href="tel:010-3901-7981">010-3901-7981</a></p>
        <p>지사 : 경기도 화성시 영천동 283-1 금강펜테리움 IX타워 / <a href="tel:010-3901-7981">010-3901-7981</a> </p>
        <p>Copyright © EXITO. All rights reserved</p>
      </div>
    </div>
  </footer>


</body>

</html>
