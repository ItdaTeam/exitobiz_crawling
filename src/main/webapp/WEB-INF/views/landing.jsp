<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="minimum-scale=1.0, width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no" />
  <meta name="author" content="성공의 문을 열고 나가다, 엑시토">
  <meta name="keywords" content="엑시토,스타트업,지원사업,공급기업, 공급업체">
  <meta name="description" content="전국 지원사업 정보, 시제품 제작 기업 정보 스타트업 정보의 바다, 엑시토">
  <meta name="robots" content="엑시토">
  <meta name="title" content="엑시토">
  <meta name="subject" content="스타트업,성공의 문을 열고 나가다. EXITO">
  <meta property="og:type" content="website">
  <meta property="og:title" content="스타트업,성공의 문을 열고 나가다. EXITO">
  <meta property="og:description" content="전국 지원사업 정보, 시제품 제작 기업 정보 스타트업 정보의 바다, 엑시토">
  <meta property="og:image" content="/img/ogimg.png">
  <meta property="og:url" content="https://exitobiz.co.kr/">
  <%-- 페이스북 --%>
  <meta name="facebook-domain-verification" content="82ofka826hrn1lygtasbnkowydg8i6" />
  <%-- 네이버 --%>
  <meta name="naver-site-verification" content="2020d64d024cfbaedd37bd131b11843f21b94b4b" />
  <title> EXITO 엑시토</title>
  <link rel="stylesheet" href="css/reset.css">
  <link rel="stylesheet" href="css/landing.css">
  <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
  <link rel="icon" type="image/x-icon" href="/img/favicon.ico">
  <link rel="shortcut icon" type="image/x-icon" href="/img/favicon.ico">
  <script src="https://code.jquery.com/jquery-1.11.3.min.js"></script> 
  <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
  <script src="js/landing.js"></script>
  <!-- Global site tag (gtag.js) - Google Analytics -->
  <script async src="https://www.googletagmanager.com/gtag/js?id=UA-221415003-1"> </script>
  <!-- 구글 애널리틱스 시작 --> 
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
    <div class="landing_wrap">
      <header class="landing_header">
        <div class="innr">
            <h1><a href="/" class="logo">EXITO</a></h1>
            <div class="nav" id="nav">
                <ul>
                    <li data-section="intro" class="mn"><a href="#intro">엑시토 소개</a></li>
                    <li data-section="howto" class="mn"><a href="#howto">주요기능</a></li>
                </ul>
            </div>
        </div>
      </header>
      <section class="landing_visual">
        <div class="universe">
            <div class="noite"></div>
            <div class="constelacao"></div>
            <div class="lua">
              <div class="textura"></div>
            </div>
            <div class="chuvaMeteoro"></div>
        </div>
        <div class="innr flex">
          <div>
            <h2><span>EXITO</span>성공의 문을 열고 나가다</h2>
            <a href="#" class="appstore" onclick="appdown()"><span>앱 다운로드</span></a>
          </div>
          <div class="mockup">
            <img src="/img/img_mockup.png" alt="목업">
          </div>
        </div>
        <button class="scrolldown">scrolldown<i></i></button>
      </section>
      <section class="landing_sct1" id="intro" data-section="intro">
        <div class="innr">
          <h3>WHAT ABOUT<br> EXITO</h3>
          <dl class="active">
            <dt><span class="icon icon1"></span> <span>한 곳에서 보는<br>다양한 지원사업</span> </dt>
            <dd>지원사업 찾는라 여러 기관 찾아다니지 마세요.<br><br> 엑시토의 100개 이상의 기관에서 금액순, 인기순, 사업 항목 등 나에게 필요한 지원사업만 확인!</dd>
          </dl>
          <dl>
            <dt><span class="icon icon2"></span> <span>엑시토<br>프렌즈 리스팅</span> </dt>
            <dd>엑시토에서 획득한 정부 지원금으로 N:1 공동구매를 통해 싸게 구매하세요!<br>VOD 교육, 컨설팅, 팀 / 문서 협업 툴, 시제품 제작 등 내가 필요한 상품 공동구매 신청!<br></dd>
          </dl>
          <dl>
            <dt><span class="icon icon3"></span><span>카카오톡 스타트업<br>네트워킹</span></dt>
            <dd>외로운 스타트업 대표님을 위한 수도권 및 지방 전국 창업자 네트워킹!<br><br> 스터디, 정보 공유, 업체 홍보 등 활발한 창업라이프!</dd>
          </dl>
        </div>
      </section>
      <section class="landing_sct2" id="exito" data-section="exito">
        <div class="innr flex">
          <div class="img_wrap">
            <img src="/img/iphone_Mockup_1.png" alt=""> <!--/img/img_mockup1.png-->
            <img src="/img/img_mck1_1.png" class="img01 aos-init aos-animate" data-aos="fade-up" alt=""> 
            <img src="/img/img_mck1_2.png" class="img02 aos-init aos-animate" data-aos="fade-up" data-aos-delay="500" alt="">
          </div>
          <div class="textbox">
            <h4>전국 지원사업 정보를<br>입맛대로 한곳에서 확인!</h4>
            <p>지원사업 찾느라 여러 기관 찾아다니지 마세요!<br>100개의 이상 사이트에 방문할 필요없이<br>엑시토 한 곳에서 지원사업 정보를 확인하세요!</p>
          </div>
        </div>
      </section>
      <section class="landing_sct3" id="howto" data-section="howto">
        <div class="how how1" >
          <div class="innr flex">
            <div> 
              <h3>전국 지원사업 정보를<br>입맛대로 한 곳에서 확인!</h3>
              <h4>엑시토에서 회사 정보를 기입하고<br>회사에 맞는 지원사업을 추천 받아보세요.</h4>
              <p class="txt">엑시토에서 인기 많은 지원사업부터<br>숨은 지원사업까지 똑똑하게 추천해드려요!</p>
            </div>
            <div class="img_wrap">
              <img src="/img/img_how1_1.svg" alt="" class="img01 aos-init aos-animate" data-aos="fade-up">
              <img class="mockup" src="/img/iphone_Mockup_2.png" alt=""> <!-- /img/img_mockup2.png -->
              <!-- <img src="/img/img_how1_2.svg" alt="" class="img02 aos-init aos-animate" data-aos="fade-up" data-aos-delay="300"> -->
              <img src="/img/ai_1.png" alt="" class="img03 aos-init aos-animate" data-aos="fade-up" data-aos-delay="500"> <!-- /img/img_how4_1.svg -->
            </div>
          </div>
        </div>
        <div class="how how2">
          <div class="innr flex">
            <div class="img_wrap">
              <img src="/img/img_how1_1.svg" alt="" class="img01 aos-init aos-animate" data-aos="fade-up">
              <img class="mockup" src="/img/iphone_Mockup_4.png" alt=""> <!-- /img/img_mockup3.png -->
              <img src="/img/img_how2_2.svg" alt="" class="img02 aos-init aos-animate" data-aos="fade-up" data-aos-delay="300">
              <img src="/img/img_how4_2.svg" alt="" class="img03 aos-init aos-animate" data-aos="fade-up" data-aos-delay="300">
            </div>
            <div> 
              <h4>정부 지원금으로 결제 가능한<br>N:1공동구매 마켓 운영</h4>
              <p class="txt">VOD 교육, 컨설팅, CS, 협업툴, 기업복지<br> APP/WEB 제작 등 서비스 상품을 공동구매 마켓을 통해 보다 저렴하게 이용해보세요.</p>
            </div>
          </div>
        </div>
        <div class="how how3">
          <div class="innr flex">
            <div> 
              <h4>키워드로 원하는 지원사업을 검색도 하고,<br>원하는 키워드 알림도 받아보세요</h4>
              <p class="txt">지원사업 > 검색 아이콘<br> 핵심 키워드로 검색하면 관련 지원사업을 찾아드립니다.</p>
              <ul>
                <li>#사회적기업</li>
                <li>#사업개발</li>
                <li>#입주</li>
                <li>#공간지원</li>
              </ul> 
            </div>
            <div class="img_wrap">
              <img class="mockup" src="/img/img_mockup4.png" alt="">
            </div>
          </div>
        </div>
        <div class="how how4">
          <div class="innr flex">
            <div class="img_wrap">
              <img class="mockup" src="/img/img_mockup5.png" alt="">
            </div>
            <div> 
              <h4>'지역+기관' 설정으로<br>정확도를 높여보세요</h4>
              <p class="txt">지원사업 > 카테고리 > 지역 > 주요기관<br>지역별 주요기관의 지원사업을 쉽게 찾을 수 있습니다.</p>
            </div>
          </div>
        </div>
        <div class="how how5">
          <div class="innr flex">
            <div>
              <img src="/img/img_how4_1.svg" alt="image">
              <h4>지원사업 공유/저장 </h4>
              <p>차년도 공고 시기, 사업계획서 제출 마감일같이 기억하기 어려운 정보는 지원사업을 저장하여 다음 년도에도 확인할 수 있어요!</p>
            </div>
            <div>
              <img src="/img/img_how4_2.svg" alt="image">
              <h4>스타트업 네트워킹</h4>
              <p>혼자서 외롭게 창업하지 말고 엑시토 온-오프라인 모임을 통해<br>창업에 미친 사람들을 만나보세요</p>
            </div>
          </div>
        </div>
      </section>
      <section class="landing_sct4">
        <div class="bg"></div>
      </section>
      <footer class="landing_footer">
          <div class="fmenu">
            <div class="innr">
              <a href="/standard">이용약관</a> 
              <a href="/personinfo">개인정보처리약관</a>
              <a href="/locationinfo">위치기반서비스이용약관</a>
              <a href="#" onclick="appdown()">앱 다운로드</a>
            </div>
        </div>
          <div class="innr"> 
            <div class="finfo">
              <p>
                <strong>엑시토</strong>&nbsp; &nbsp;
                대표 : 권기정&nbsp; &nbsp; 개인정보 책임자 : (주)CTNS&nbsp; &nbsp; <span>사업자등록번호 : 307-81-50055</span> 
              </p>
              <p>고객센터 : <a href="tel:031-784-8443">031-784-8443</a></p>
              <p>본사 : 경상남도 창원시 의창구 평산로 23 신화테크노밸리 6층 639~641호</p>
              <p>지사 : 경기도 화성시 영천동 283-1 금강펜테리움 IX타워 </p>
              <p>Copyright © EXITO. All rights reserved</p>
            </div>
          </div>
      </footer>
      <a href="#" class="landing_store" onclick="appdown()">앱 다운로드</a>
    </div>
</body>
</html>
