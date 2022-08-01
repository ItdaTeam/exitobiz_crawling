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
<!-- 테스트 -->
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
            <img class="firstMockup" src="/img/img_mockup.png" alt="목업">
          </div>
        </div>
        <button class="scrolldown">scrolldown<i></i></button>
      </section>
      <section class="landing_sct1" id="intro" data-section="intro">
        <div class="innr">
          <h3>WHAT ABOUT<br> EXITO</h3>
          <dl class="active">
            <dt><span class="icon icon1"></span> <span>한 곳에서 보는<br>다양한 지원사업</span> </dt>
            <dd>수 백개의 사이트에 흩어져 있는 지원사업 공고!<br><br>한 곳에서 볼 수 있게 도와드릴게요!<br><br>하루 10분 투자하고 지원사업 30개 지원하세요~</dd>
          </dl>
          <dl>
            <dt><span class="icon icon2"></span> <span>엑시토 프렌즈와 함께하는 N:1 공동구매</span> </dt>
            <dd>다 같이 구매하면 가격이 내려가요!<br><br>사업계획서 컨설팅,시제품 제작 등<br>기업에 필요한 서비스를<br>저렴하게 구입할 수 있는 기회!</dd>
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
            <img src="/img/img_mck1_2.png" class="img01 aos-init aos-animate" data-aos="fade-up" alt=""> 
            <img src="/img/img_mck1_1.png" class="img02 aos-init aos-animate" data-aos="fade-up" data-aos-delay="500" alt="">
          </div>
          <div class="textbox">
            <h4>전국 지원사업 정보를<br >'한곳'에서 확인!</h4>
            <p>K-Startup,기업마당,테크노파크,<br>창조경제혁신센터,각 지자체..<br><br>100개 이상의 사이트에 방문할 필요 없이<br><br>엑시토 한 곳에서<br>지원사업 정보를 확인하세요!</p>
          </div>
        </div>
      </section>
      <section class="landing_sct3" id="howto" data-section="howto">
        <div class="how how2">
          <div class="innr flex">
            <div class="img_wrap">
              <img src="/img/mockup2_1.png" alt="" class="img01 aos-init aos-animate" data-aos="fade-up">
              <img class="mockup" src="/img/img_mockup2.png" alt="" style="left:-306px;top:0px;"> <!-- /img/img_mockup3.png -->
              <img src="/img/img_how2_2.svg" alt="" class="img02 aos-init aos-animate" data-aos="fade-up" data-aos-delay="300">
              <!-- <img src="/img/img_how4_2.svg" alt="" class="img03 aos-init aos-animate" data-aos="fade-up" data-aos-delay="300"> -->
            </div>
            <div> 
              <h4>정부 지원금으로 결제 가능한<br>N:1공동구매 마켓 운영</h4>
              <p class="txt">-사업계획서 컨설팅<br>-기업인증<br>-APP/WEB 개발<br>-협엽 솔루션(SaaS)<br>-VOD 교육<br><br>공동구매로 보다 저렴하게 제공합니다!</p>
            </div>
          </div>
        </div>
        <div class="how how1" >
          <div class="innr flex">
            <div> 
              <h3>내 주변<br>'지원사업 통계' 확인</h3>
              <h4>전국 지원사업 규모와<br>내 지역 지원사업 규모를 확인하세요</h4>
              <p class="txt">획득 가능한 지원금 규모를 안다면<br>자금계획을 세울 수 있답니다!</p>
            </div>
            <div class="img_wrap">
              <!-- <img src="/img/mockup2_1.png" alt="" class="img01 aos-init aos-animate" data-aos="fade-up"> -->
              <img class="mockup" src="/img/iphone_Mockup_2.png" alt=""> <!-- /img/img_mockup2.png -->
              <!-- <img src="/img/img_how1_2.svg" alt="" class="img02 aos-init aos-animate" data-aos="fade-up" data-aos-delay="300"> -->
              <img src="/img/ai_1.png" alt="" class="img03 aos-init aos-animate" data-aos="fade-up" data-aos-delay="500"> <!-- /img/img_how4_1.svg -->
            </div>
          </div>
        </div>
        <!-- 추가 -->
        <div class="how how6" style="background-color: #287AF6;" >
          <div class="innr flex">
            <div> 
              <h3 style="margin-bottom: 10%;">지원사업도<br>댓글로 소통하세요!</h3>
              <h4 style="color:#fff">나만 아는 사업계획서 작성 꿀팁,<br>지원사업 수행 경험 등</h4>
              <p class="txt">동료 스타트업을 위해<br>아끼지 말고 공유해 주세요!</p>
            </div>
            <div class="img_wrap">
              <!-- <img src="/img/img_how1_1.svg" alt="" class="img01 aos-init aos-animate" data-aos="fade-up"> -->
              <img class="mockup" src="/img/iphone_Mockup_5.png" alt=""> <!-- /img/img_mockup2.png -->
              <!-- <img src="/img/img_how1_2.svg" alt="" class="img02 aos-init aos-animate" data-aos="fade-up" data-aos-delay="300"> -->
              <!-- <img src="/img/ai_1.png" alt="" class="img03 aos-init aos-animate" data-aos="fade-up" data-aos-delay="500"> /img/img_how4_1.svg -->
            </div>
          </div>
        </div>
        <!-- /추가 -->
        
        <div class="how how3">
          <div class="innr flex">
            <div> 
              <h4>요즘 핫한<br>지원사업 '검색 키워드'</h4>
              <p class="txt">TOP 10 키워드 활용하기<br><br>한달동안<br>검색량이 가장 많았던 10가지 키워드로<br><br>인기있는 지원사업을<br>확인해 보아요!</p>
              <!-- <ul>
                <li>#사회적기업</li>
                <li>#사업개발</li>
                <li>#입주</li>
                <li>#공간지원</li>
              </ul>  -->
            </div>
            <div class="img_wrap">
              <img class="mockup" src="/img/img_mockup4_1.png" alt="">
              <img id="mockup4_1_1" src="/img/img_mockup4_1_1.png" alt="">
            </div>
          </div>
        </div>
        <div class="how how4">
          <div class="innr flex">
            <div class="img_wrap">
              <img class="mockup" src="/img/img_mockup5_1.png" alt="">
            </div>
            <div> 
              <h4>'지역+기관' 설정으로<br>정확도를 높여보세요</h4>
              <p class="txt">지원사업 > 카테고리 > 지역 > 주요기관<br>지역별 주요기관의 지원사업을 쉽게 찾을 수 있습니다.</p>
            </div>
          </div>
        </div>
        <!-- <div class="how how5">
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
        </div> -->
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
