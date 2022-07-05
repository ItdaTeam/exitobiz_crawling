<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
response.setDateHeader("Expires", 0L); // Do not cache in proxy server
%>
<!DOCTYPE html>
<html lang="kr" dir="ltr">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="author" content="">
  <meta name="keywords" content="">
  <meta name="description" content="">
  <meta property="og:type" content="website">
  <meta property="og:title" content="ITDA 잇다">
  <meta property="og:description" content="">
  <meta property="og:image" content="">
  <meta property="og:url" content="">
  <link rel="shortcut icon" type="image/x-icon" href="/img/favicon.ico">
  <link rel="apple-touch-icon" href="/img/favicon.ico">
  <link rel="apple-touch-icon-precomposed" href="/img/favicon.ico">
  <link rel="canonical" href="">
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

</head>
<script>
function addQuestion(){
  var hpRule = /^\d{3}\d{3,4}\d{4}$/;
  if($("#companyname").val() == ''){
    alert('회사명을 입력하시기 바랍니다.');
    return false;
  }else if($("#username").val() ==''){
    alert('성함을 입력하시기 바랍니다.');
    return false;
  }else if($("#userhp").val() ==''){
    alert('연락처를 입력하시기 바랍니다.');
    return false;
  } else if(!hpRule.test($("#userhp").val())){
    alert("연락처를 바르게 입력하시기 바랍니다.(ex. 01012341234)");
    return false;
  }else if($("#app").is(":checked") == false && $("#web").is(":checked") == false && $("#other").is(":checked") == false){
    alert('문의 분야를 선택하시기 바랍니다.');
    return false;
  }else if($("#body").val() ==''){
    alert('문의 내용을 입력하시기 바랍니다.');
    return false;
  }else if($("#agree").is(":checked") == false){
    alert('개인정보처리방침 동의를 확인하시기 바랍니다.');
    return false;
  }

 
   var params={
    appFlag : $("#app").is(":checked") == true? 'Y' : 'N',
    webFlag : $("#web").is(":checked") == true? 'Y' : 'N',
    etcFlag : $("#other").is(":checked") == true? 'Y' : 'N',
    body : $("#body").val(),
    companyName : $("#companyname").val(),
    userName : $("#username").val(),
    userHp : $("#userhp").val()
  }
  $.ajax({
        url : "<%=request.getContextPath()%>/addQuestion",
        async : false, // 비동기모드 : true, 동기식모드 : false
        type : 'POST',
        cache : false,
        data :  params,
        success : function() {
          alert("등록되었습니다.");
          $("#companyname").val('');
          $("#username").val('');
          $("#userhp").val('');
          $("#app").attr("checked", false);
          $("#web").attr("checked", false);
          $("#other").attr("checked", false);
          $("#agree").attr("checked", false);
          $("#body").val('');
        },
          error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
          }
      });

}
</script>
<body>

  <header class="header">
    <div class="inner">
      <a href="/" class="logo"><img src="/img/logo.png" alt=""></a>
      <ul class="gnb">
        <li class="active"><a href="#section02">잇다소개</a></li>
        <li><a href="#section04">이용후기</a></li>
        <li><a href="#section05">오시는길</a></li>
        <li><a href="#section06">제작문의</a></li>
      </ul>

      <div class="toggle_btn">
        <span></span>
        <span></span>
        <span></span>
      </div>

      <div class="toggle_menu_wrap">
        <ul class="toggle_menu">
          <li><a href="#">1</a></li>
          <li><a href="#">1</a></li>
          <li><a href="#">1</a></li>
          <li><a href="#">1</a></li>
        </ul>
      </div>
    </div>
  </header>

  <section class="section01">
    <div class="video_wrap m_hidden">
      <div class="video_item popup-trigger">
        <img src="/img/v_img01.png" alt="">
        <a class="video_btn" popup-open="popup-1" href="javascript:void(0)"><i class="fas fa-caret-right"></i></a>
      </div>

      <div class="video_item popup-trigger">
        <img src="/img/v_img02.png" alt="">
        <a class="video_btn" popup-open="popup-2" href="javascript:void(0)"><i class="fas fa-caret-right"></i></a>
      </div>
    </div>

    <div class="popup" popup-name="popup-1">
      <div class="popup-content">
        <iframe src="https://player.vimeo.com/video/513664697" width="800" height="450" frameborder="0" allow="autoplay; fullscreen; picture-in-picture" allowfullscreen></iframe>
        <a class="close-btn" popup-close="popup-1" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>

    <div class="popup" popup-name="popup-2">
      <div class="popup-content">
        <iframe src="https://player.vimeo.com/video/512854897" width="800" height="450" frameborder="0" allow="autoplay; fullscreen; picture-in-picture" allowfullscreen></iframe>
        <a class="close-btn" popup-close="popup-2" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>

    <div class="arrowSlider">
      <span class="prev mainslider_prev"><i class="fas fa-caret-left"></i></span>
      <span class="next mainslider_next"><i class="fas fa-caret-right"></i></span>
    </div>

    <div class="main_slider">
      <div class="item bg01">
        <div class="inner">
          <div class="main_txt">
            <h1>스타트업</h1>
            <h1><span>희망을</span> 잇다</h1>
            <p>니즈를 충족시키는 웹/앱 제작</p>
            <a href="#" class="btn">제작문의</a>
          </div>
        </div>
      </div>
      <div class="item bg02">
        <div class="inner">
          <div class="main_txt">
            <h1>스타트업</h1>
            <h1><span>미래을</span> 잇다</h1>
            <p>창업가를 위한 실전교육</p>
            <a href="#" class="btn">교육신청</a>
          </div>
        </div>
      </div>
      <div class="item bg03">
        <div class="inner">
          <div class="main_txt">
            <h1>스타트업</h1>
            <h1><span>성공을</span> 잇다</h1>
            <p>지원사업 통합 플랫폼 제공</p>
            <div class="btn_wrap">
              <a href="#" class="app_btn"><i class="fab fa-google-play"></i> Android</a> <a href="#" class="app_btn"><i class="fab fa-app-store"></i> iOS</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <div class="quick_menu_wrap">
    <div class="quick_trigger">
      <span></span>
      <span></span>
    </div>
    <div class="quick_menu">
      <a href="https://raynorsmakers.com/itda " class="item"><img src="/img/ico_quick01.png" alt="교육신청"><span>교육신청</span></a>
      <a href="https://itdabiz.com/selections" class="item"><img src="/img/ico_quick02.png" alt="견적계산"><span>견적계산</span></a>
      <a href="#" class="item"><img src="/img/ico_quick03.png" alt="회사소개"><span>회사소개</span></a>
      <a href="http://pf.kakao.com/_PCxokK" class="item"><img src="/img/ico_quick04.png" alt="고객센터"><span>고객센터</span></a>
    </div>
  </div>

  <section class="section07 m_visible">
    <h2 class="sect_tit">잇다 <span>영상</span></h2>
    <div class="video_wrap">
      <div class="video_item popup-trigger">
        <img src="/img/v_img01.png" alt="">
        <a class="video_btn" popup-open="popup-1" href="javascript:void(0)"><i class="fas fa-caret-right"></i></a>
      </div>

      <div class="video_item popup-trigger">
        <img src="/img/v_img02.png" alt="">
        <a class="video_btn" popup-open="popup-2" href="javascript:void(0)"><i class="fas fa-caret-right"></i></a>
      </div>
    </div>
  </section>

  <section id="section02" class="section02">
    <div class="inner">
      <div class="flex_wrap">
        <img src="/img/section02_img01.png" alt="">
        <h2 class="sect_tit"><span>잇다 서비스</span>는?</h2>
      </div>
      <p>잇다는 단순한 웹/앱 서비스 제작사를 벗어나 스타트업의 성공을 위해<br class="none">기획, 교육, 컨설팅까지 제공하는 든든한 파트너사가 되어드립니다.</p>
      <a href="https://raynorsmakers.com/itdaquestion" class="btn">자주 묻는 질문</a>
    </div>
  </section>

  <section id="section03" class="section03">
    <div class="inner">
      <h2 class="sect_tit">잇다 <span>강점</span></h2>
      <p class="sect_txt">잇다를 믿고 맡길 수 있는 이유, 잇다에서 성공과 더욱 가까워지세요!</p>
      <div class="flex_wrap">
        <div class="flex_box">
          <h4>PM 전담제</h4>
          <p>고객과 제작자 사이의 중간 역할을 하는 PM (Project Manager)이 있어<br class="none">원활한 의사소통이 가능합니다.</p>
          <h4>교육/컨설팅 제공</h4>
          <p>예비/초기/도약 창업가를 위해 기업대표가 직접 알려주는 꿀 강의를 제공합니다.</p>
          <h4>크로스플랫폼</h4>
          <p>하이브리드 가격으로 네이티브에 준하는 앱 제작이 가능합니다.</p>
        </div>
        <div class="flex_box">
          <div class="phone_img_wrap posi01">
            <img src="/img/phone_img01.png" alt="" class="sect03_img">
          </div>
          <div class="phone_img_wrap posi02">
            <img src="/img/phone_img03.png" alt="" class="sect03_img">
            <img src="/img/phone_item01.png" alt="" class="phone_item posi01" data-aos="zoom-in-up" data-aos-duration="1000">
            <img src="/img/phone_item02.png" alt="" class="phone_item posi02" data-aos="zoom-in-up" data-aos-duration="1000" data-aos-delay="200">
            <img src="/img/phone_item03.png" alt="" class="phone_item posi03" data-aos="zoom-in-up" data-aos-duration="1000" data-aos-delay="400">
            <img src="/img/phone_item04.png" alt="" class="phone_item posi04" data-aos="zoom-in-up" data-aos-duration="1000" data-aos-delay="600">
            <img src="/img/phone_item05.png" alt="" class="phone_item posi05" data-aos="zoom-in-up" data-aos-duration="1000" data-aos-delay="800">
          </div>
        </div>
      </div>
    </div>
  </section>

  <section id="section04" class="section04 fir">
    <div class="inner">
      <div class="flex_wrap review_wrap">
        <div class="flex_box">
          <h2 class="sect_tit"><span>서비스제작</span> 후기</h2>
          <div class="arrowSlider">
            <span class="prev sect04_prev"><i class="fas fa-caret-left"></i></span>
            <span class="next sect04_next"><i class="fas fa-caret-right"></i></span>
          </div>
        </div>
        <div class="flex_box">

          <div class="section04_slider slider1">
            <div class="item">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img01.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">라임푸*</p>
                  <p class="cust_name">우유리</p>
                </div>
              </div>
              <div class="star_wrap">
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <p class="star_rating">5</p>
              </div>
              <div class="review">
                <p>잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다.</p>
              </div>
            </div>
            <div class="item">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img01.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">라임푸*</p>
                  <p class="cust_name">우유리</p>
                </div>
              </div>
              <div class="star_wrap">
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <p class="star_rating">5</p>
              </div>
              <div class="review">
                <p>잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다.</p>
              </div>
            </div>
            <div class="item">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img01.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">라임푸*</p>
                  <p class="cust_name">우유리</p>
                </div>
              </div>
              <div class="star_wrap">
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <p class="star_rating">5</p>
              </div>
              <div class="review">
                <p>잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다.</p>
              </div>
            </div>
            <div class="item">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img01.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">라임푸*</p>
                  <p class="cust_name">우유리</p>
                </div>
              </div>
              <div class="star_wrap">
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <p class="star_rating">5</p>
              </div>
              <div class="review">
                <p>잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다.</p>
              </div>
            </div>
            <div class="item">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img01.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">라임푸*</p>
                  <p class="cust_name">우유리</p>
                </div>
              </div>
              <div class="star_wrap">
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <p class="star_rating">5</p>
              </div>
              <div class="review">
                <p>잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다.</p>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </section>

  <section id="section04" class="section04 sec">
    <div class="inner">
      <div class="flex_wrap review_wrap">
        <div class="flex_box">
          <h2 class="sect_tit"><span>교육/컨설팅</span> 후기</h2>
          <div class="arrowSlider">
            <span class="prev sect04_prev2"><i class="fas fa-caret-left"></i></span>
            <span class="next sect04_next2"><i class="fas fa-caret-right"></i></span>
          </div>
        </div>
        <div class="flex_box">
          <div class="section04_slider slider2">
            <div class="item">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img01.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">잇다*</p>
                  <p class="cust_name">우유리</p>
                </div>
              </div>
              <div class="star_wrap">
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <p class="star_rating">5</p>
              </div>
              <div class="review">
                <p>잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다.</p>
              </div>
            </div>
            <div class="item">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img01.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">잇다*</p>
                  <p class="cust_name">우유리</p>
                </div>
              </div>
              <div class="star_wrap">
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <p class="star_rating">5</p>
              </div>
              <div class="review">
                <p>잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다.</p>
              </div>
            </div>
            <div class="item">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img01.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">잇다*</p>
                  <p class="cust_name">우유리</p>
                </div>
              </div>
              <div class="star_wrap">
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <p class="star_rating">5</p>
              </div>
              <div class="review">
                <p>잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다.</p>
              </div>
            </div>
            <div class="item">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img01.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">잇다*</p>
                  <p class="cust_name">우유리</p>
                </div>
              </div>
              <div class="star_wrap">
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <p class="star_rating">5</p>
              </div>
              <div class="review">
                <p>잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다.</p>
              </div>
            </div>
            <div class="item">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img01.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">잇다*</p>
                  <p class="cust_name">우유리</p>
                </div>
              </div>
              <div class="star_wrap">
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <i class="fas fa-star"></i>
                <p class="star_rating">5</p>
              </div>
              <div class="review">
                <p>잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다. 잇다에 제작문의를 하면서 많은 시행착오를 줄이고 빠르게 제작을 진행할 수 있었습니다.</p>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </section>


  <section id="section05" class="section05">
    <div class="inner">
      <div class="txt_wrap">
        <h5>잇다 오시는길</h5>
        <h2 class="sect_tit"><span>우리 여기 ITDA<i>!</i></span></h2>
        <a href="https://kko.to/-LoUTDeDH" target="_blank">경상남도 창원시 의창구 평산로 23<br>신화테크노벨리 6층 639</a>
        <a href="https://kko.to/-LoUTDeDH" target="_blank" class="map_btn">지도보기</a>
      </div>

      <div class='pin'></div>
      <div class='pulse'></div>
    </div>
  </section>


  <section id="section06" class="section06">
    <div class="inner">
      <div class="flex_wrap">
        <div class="flex_box txt_wrap">
          <h2 class="sect_tit"><span>문의하기</span></h2>
          <p class="cust_center_num">070-7733-4747</p>
          <p class="cust_info">전화상담 가능 시간 AM 10:00~19:00<br>(주말, 공휴일 휴무, 점심시간 PM 12:00 ~ 13:00)</p>
        </div>
        <div class="flex_box inquiry_form_wrap">
          <table>
            <tr>
              <th>회사명</th>
              <td><input type="text" name="companyname" id="companyname" value="" placeholder="잇다"></td>
            </tr>
            <tr>
              <th>성함</th>
              <td><input type="text" name="username" id="username" value="" placeholder="홍길동"></td>
            </tr>
            <tr>
              <th>연락처</th>
              <td><input type="text" name="userhp" id="userhp" value="" placeholder="-없이 작성"></td>
            </tr>
            <tr>
              <th>문의분야</th>
              <td>
                <input type="checkbox" name="app" value="" id="app" class="chk">
                <label for="app"><span class="check_span"></span>앱</label>
                <input type="checkbox" name="web" value="" id="web" class="chk">
                <label for="web"><span class="check_span"></span>웹</label>
                <input type="checkbox" name="other" value="" id="other" class="chk">
                <label for="other"><span class="check_span"></span>기타</label>
              </td>
            </tr>
            <tr>
              <th style="vertical-align:top;">문의내용</th>
              <td><textarea name="body" id="body" cols="20" rows="3"></textarea> </td>
            </tr>
            <tr>
              <th>개인정보처리방침</th>
              <td>
                <input type="checkbox" name="agree" value="on" id="agree" class="chk chk_style02" checked>
                <label for="agree"><span class="check_span"></span>동의함</label>
                <a href="#" clas="policy_cont_btn">[내용확인]</a>
              </td>
            </tr>
          </table>
          <a href="javascript:addQuestion()" class="btn">문의하기</a>
        </div>
      </div>
    </div>
  </section>

  <footer class="footer">
    <div class="top">
      <div class="inner">
        <ul>
          <li><a href="">이용약관</a></li>
          <li><a href="">개인정보처리방침</a></li>
        </ul>
      </div>
    </div>
    <div class="bottom">
      <div class="inner">
        <ul>
          <li><span>회사명 : CTNS </span> <span>대표자명 : 권기정</span> <span>사업자등록번호 : 307-81-50055</span></li>
          <li><span><i class="fas fa-map-marker-alt"></i> 경상남도 창원시 의창구 평산로 23 신화테크노벨리 6층 639</span></li>
          <li><span><i class="fas fa-headset"></i> 070-7733-4747</span> <span><i class="fas fa-envelope"></i> itda.korea@gmail.com</span></li>
        </ul>
        <p class="copyright">ⓒ Copyright 2021 ITDA. All rights reserved.</p>

        <a href="#" class="top_btn"><i class="fas fa-chevron-up"></i></a>
      </div>
    </div>
  </footer>


</body>

</html>
