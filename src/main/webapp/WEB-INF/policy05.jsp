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
  <script> window.dataLayer = window.dataLayer || []; function gtag(){dataLayer.push(arguments);} gtag('js', new Date()); gtag('config', 'UA-221415003-1'); </script>
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
      <p class="menu">마케팅정보수신동의</p>
    </div>
  </header>


  <div class="policy_wrap">
    <div class="inner">
      <ul class="policy_menu">
        <li><a href="/standard">이용약관</a></li>
        <li><a href="/personinfo">개인정보처리약관</a></li>
        <li><a href="/locationinfo">위치기반서비스이용약관</a></li>
        <li><a href="/marketingInfo" class="current">마케팅정보수신동의</a></li>
      </ul>

      <div class="policy_cont">
        <!-- <%=request.getAttribute("termService")%> -->
        <h1>마케팅 정보 수신 동의</h1>
        <h2>서비스 제공 및 이용과 관련하여 ㈜씨티엔에스가 취득한 개인정보는 "개인정보보호법" 및 "정보통신망 이용촉진 및 정보보호 등에 관한 법률" 등 정보통신서비스제공자가 준수하여야 할 관련 법령상의 개인정보 보호 규정을 준수합니다.<h2><br>
        <p><span>1. 고객이 수집 및 이용에 동의한 개인정보를 활용하여, 전자적 전송 매체(App Push, 핸드폰 문자메시지(SMS,MMS), E-mail 등 다양한 전송 매체)를 통하여 발송됩니다. </span></p><br>
        <p><span>2. 발송되는 마케팅 정보는 수신자에게 ㈜씨티엔에스 및 제 3자의 상품 또는 서비스에 대한 혜택 정보, 각종 이벤트 정보, 개인 맞춤형 광고 정보 등 광고성 정보로 관련 법의 규정을 준수하여 발송됩니다.  
                 단, 광고성 정보 이외에 의무적으로 안내되어야 하는 정보성 내용은 수신동의 여부와 무관하게 제공됩니다. </span></p><br>
        <p><span>3. 마케팅 정보 수신 여부 및 마케팅을 위한 개인정보 수집이용을 거부하실 수 있으며, 거부 시에도 엑시토 서비스를 이용하실 수 있으나, 동의를 거부한 경우 각종 소식 및 이벤트 참여에 제한이 있을 수 있습니다. </span></p><br>
        <p><span>4. ㈜씨티엔에스 고객센터를 통해 수신동의를 철회할 수 있습니다. </span></p><br>
        <p><span>※ 문의 및 동의철회 : 031-784-8443 </span></p>
        <section class="tableWrap">
          <div class="tableWrap1 tables">
            <p>개인정보 수집 항목</p>
            <ul>
              <li>개인정보 1등급 : 위치정보</li>
              <li>개인정보 2등급 : 이름, 휴대폰번호, 이메일, 주소</li>
            </ul>
          </div>
          <div class="tableWrap2 tables">
            <p>개인정보 수집 이용 목적</p>
            <ul>
              <li>마케팅 광고에 활용</li>
              <li>- 이벤트 운영 및 광고성 정보 전송</li>
              <li>- 서비스 관련 정보 전송</li>
            </ul>
          </div>
          <div class="tableWrap3 tables">
            <p>보유 및 이용기간</p>
            <ul>
              <li>- 이용자가 동의를 철회하거나, 탈퇴 시 까지</li>
              <li>- 단, 관련 법령의 규정에 따라 보존할 필요가 있을 경우,해당 기간<br>까지 보존함</li>
            </ul>
          </div>
        </section>
        <br>
        <p class="marketingbold">이 마케팅약관은 2022년 09월 01일부터 적용됩니다.</p>
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
