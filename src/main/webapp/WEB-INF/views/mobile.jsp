<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko" class="fzoom">
<head>
    <title>ITDA 잇다</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="한눈에 보는 정부지원사업 어플">
    <meta name="keywords" content="잇다, 스타트업, 창업, 정부지원사업">
    <meta name="description" content="한눈에 보는 정부지원사업 어플">
    <meta property="og:type" content="website">
    <meta property="og:title" content="ITDA 잇다">
    <meta property="og:description" content="한눈에 보는 정부지원사업 어플">
    <meta property="og:image" content="/mobile/images/og_m_img.png">
    <meta property="og:url" content="https://exitobiz.co.kr/landingMobile">
    <link rel="shortcut icon" type="image/x-icon" href="/mobile/images/f_m_icon.ico">
    <link rel="apple-touch-icon" href="/mobile/images/f_m_icon.ico">
    <link rel="apple-touch-icon-precomposed" href="/mobile/images/f_m_icon.ico">
    <link rel="canonical" href="https://exitobiz.co.kr/landingMobile">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <link rel="stylesheet" href="mobile/css/landing_m.css">
    <link rel="stylesheet" href="mobile/css/reset_m.css">
    <link rel="stylesheet" href="mobile/css/jquery.fullPage.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="/mobile/js/landing_m.js"></script>
    <script src="/mobile/js/jquery.fullPage.js "></script>
</head>
<body style="max-width:100%; overflow:hidden; height: 100%;" class="fp-viewing-12">
    <div class="landing-wrap">
        <header class="landing-header">
            <h1 class="logo"><a href="#01"><img src="/mobile/images/ic_m_logo.png" alt="잇다"></a></h1>
            <span class="insta"><a href="https://www.instagram.com/itda.korea/"><img src="/mobile/images/ic_m_insta.png" alt="인스타그램"></a></span>
        </header>
        <div id="fullpage">
            <div class="section landing-visual" data-anchor="01">
                <div class="landing__inner">
                    <p class="landing-visual__sub"><strong>잇다</strong>에서 한번에 볼 수 있으니까</p>
                    <p class="landing-visual__title">정부지원사업,</p>
                    <p class="landing-visual__title delay">그만 찾으세요</p>
                    <div class="landing-store">
                        <a href="#" onclick="appdown()">스토어 바로가기</a>
                    </div>
                </div>
                <p class="landing-bg"></p>
            </div>
            <div class="section landing-blue" data-anchor="02">
                <h2 class="landing__title" style="opacity:0;">스타트업에게<br>잇다가 왜 필요한가?</h2>
            </div>
            <div class="section landing-intro landing-intro--sec1" data-anchor="03" >
                <div class="landing__inner">
                    <h3><i>01</i>하루 5분이면 충분 !<br>한눈에 보는 지원사업</h3>
                    <div class="text">
                        <p>매일 지원사업 찾느라 전담직원<br>배치하고 몇 시간씩 투자하지 마세요</p>
                    </div>
                </div>
            </div>
            <div class="section landing-intro landing-intro--sec2" data-anchor="04">
                <div class="landing__inner">
                    <h3><i>02</i>  교과서같은 교육은 그만!<br>실전 창업 교육</h3>
                    <div class="text">
                        <p>스타트업 대표님들의 실전강의를 통해 <br>실무 꿀팁을 전수받아보세요</p>
                    </div>
                </div>
            </div>
            <div class="section landing-intro landing-intro--sec3" data-anchor="05">
                <div class="landing__inner">
                    <h3><i>03</i>사업파트너도 잇다에서!<br> 팀원 모집 가능</h3>
                    <div class="text">
                        <p>혼자서는 힘든 창업, 동업자가 필요할 때<br>잇다에서 모집할 수 있습니다.</p>
                    </div>
                </div>
            </div>
            <div class="section landing-use use1" data-anchor="06">
                <h2 class="landing-use__title">잇다 사용방법</h2>
                <div class="text" style="opacity: 0;">
                    <h4><span>STEP 01</span>지역 설정하기</h4>
                    <p>지원사업을 보고 싶은 지역을 설정해주세요!<br>
                        해당 지역과 전국만 볼 수 있도록 설정됩니다.</p>
                </div>
                <div class="thumb">
                    <img src="/mobile/images/use01.png" alt="사용법1">
                </div>
            </div>
            <div class="section landing-use use2" data-anchor="07">
                <div class="text">
                    <h4><span>STEP 02</span>필터 설정하기</h4>
                    <p>분야, 기관, 지역별로 필터 설정할 수 있어요!<br>
                        필터 설정하고, 원하는 정보만 받아보세요 :D</p>
                </div>
                <div class="thumb"><img src="/mobile/images/use02.png" alt="사용법2"></div>
            </div>
            <div class="section landing-use use3" data-anchor="08">
                <div class="text">
                    <h4><span>STEP 03</span>지원 사업 확인</h4>
                    <p>실시간 인기, 최신 지원사업을 확인할 수 있어요<br>
                        꿀 정보에는 좋아요, 스크랩, 공유까지 꾹!</p>
                </div>
                <div class="thumb"><img src="/mobile/images/use03.png" alt="사용법3"></div>
            </div>
            <div class="section landing-use landing-use--box add1" data-anchor="09">
                <div class="text">
                    <h4><span>STEP 04</span>푸쉬 알람 현황</h4>
                    <p>알람 종을 클릭하여 알람 현황을 확인해보세요</p>
                </div>
                <div class="thumb"><img src="/mobile/images/use04.png" alt="사용법4"></div>
            </div>
            <div class="section landing-use landing-use--box add2" data-anchor="10">
                <div class="text">
                    <h4><span>STEP 05</span>정렬 필터</h4>
                    <p>필터를 적용해서 원하는 지원사업순으로 보세요</p>
                </div>
                <div class="thumb"><img src="/mobile/images/use05.png" alt="사용법5"></div>
            </div>
            <div class="section landing-use landing-use--box add3" data-anchor="11">
                <div class="text">
                    <h4><span>STEP 06</span>일괄 공유</h4>
                    <p>공유하기를 클릭하고 일괄 공유해보세요</p>
                </div>
                <div class="thumb"><img src="/mobile/images/use06.png" alt="사용법6"></div>
            </div>
            <div class="section landing-tip" data-anchor="12">
                <div class="landing-tip__slide tip1">
                    <div class="text">
                        <h4>카테고리 활용하기</h4>
                        <p>특정 분야, 기관, 지역 딱 하나만 보고 싶다면 <br>카테고리를 활용해보세요! 
                        </p>
                    </div>
                    <span class="thumb">
                        <img src="/mobile/images/tip01.png" alt="tip1">
                    </span>
                </div>
            </div>
            <div class="section landing-tip" data-anchor="13">
                <div class="landing-tip__slide tip2">
                    <div class="text">
                        <h4>팀원 모집하기</h4>
                        <p>간략한 회사 소개와 연락처 남기고,<br>든든한 사업파트너를 구해보세요.</p>
                    </div>
                    <span class="thumb">
                        <img src="/mobile/images/tip02.png" alt="tip2">
                    </span>
                </div>
            </div>
            <div class="section landing-tip" data-anchor="14">
                <div class="landing-tip__slide tip3">
                    <div class="text">
                        <h4>실전창업교육 듣기</h4>
                        <p>스타트업 대표님들에게 직접 듣는 실제 사례<br>창업교육까지 들으면 잇다 활용 Master!</p>
                    </div>
                    <span class="thumb">
                        <img src="/mobile/images/tip03.png" alt="tip3">
                    </span>
                </div>
            </div>
            <div class="section landing-footer" data-anchor="15">
                <div class="landing-banner">
                    <h2><span>잇다 다운로드</span> 하고<br> 언제 어디서나 <span>편리하게</span><br><span>정부지원사업</span>확인하세요</h2>
                    <div class="landing-store">
                        <a href="#" onclick="appdown()">스토어 바로가기</a>
                    </div>
                </div>
                <footer>
                   <div class="landing__inner">
                        <div class="fright">
                            <span class="fcscenter">잇다 고객센터</span>
                            <a href="tel:070-7733-4747" class="num">070-7733-4747</a>
                            <p>평일 오전 10시~오후 7시</p>
                            <p>토요일/일요일/공휴일 OFF</p>
                        </div>
                        <div class="fmenu">
                            <a href="/personinfo">개인정보처리방침 |</a>
                            <a href="/standard">이용약관</a>
                        </div>
                        <div class="finfo">
                            <dl>
                                <dt>상호명</dt>
                                <dd>잇다</dd>
                                <dt>대표</dt>
                                <dd>권기정</dd>
                                <dt>개인정보 책임자</dt>
                                <dd>(주)CTNS</dd>
                            </dl>
                            <dl>
                                <dt>사업자등록번호 </dt>
                                <dd>307-81-50055</dd>
                            </dl>
                            <dl>
                                <dt>고객센터 메일 주소</dt>
                                <dd>itda.korea@gmail.com</dd>
                            </dl>
                            <dl>
                                <dt>Copyright © ITDA. All rights reserved</dt>
                                <dd></dd>
                            </dl>
                        </div>
                        <a href="#01" class="landing-top">
                            <i class="fal fa-arrow-up"></i>
                        </a>
                    </div>
                </footer>
            </div>
        </div>
        <a href="#" class="landing-down" onclick="appdown()">앱 다운로드</a>
    </div>
</body>
</html>