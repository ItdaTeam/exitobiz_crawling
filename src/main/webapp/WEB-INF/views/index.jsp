<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="kr" dir="ltr">

 <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="author" content="경남 웹사이트 제작, 앱 제작, 잇다">
  <meta name="keywords" content="경남 웹사이트 제작, 앱 제작, 스타트업, 교육, 컨설팅, 웹, 앱, 홈페이지 제작">
  <meta name="description" content="경남 웹사이트 제작, 앱 제작, 스타트업, 교육, 컨설팅, 웹, 앱, 홈페이지 제작">
  <meta name="robots" content="경남 웹사이트 제작, 앱 제작, 잇다">
  <meta name="title" content="경남 웹사이트 제작, 앱 제작, 잇다">
  <meta name="subject" content="경남 웹사이트 제작, 앱 제작, 잇다">
  <meta property="og:type" content="website">
  <meta property="og:title" content="ITDA 잇다">
  <meta property="og:description" content="경남 웹사이트 제작, 앱 제작, 스타트업, 교육, 컨설팅, 웹, 앱, 홈페이지 제작">
  <meta property="og:image" content="/img/ogimg.png">
  <meta property="og:url" content="https://exitobiz.co.kr">
  <meta name="naver-site-verification" content="1780d2c0ffec38841d949f9db736408a1ca4d3e7" />
  <meta name="google-site-verification" content="L9_TgH674zxuwCS54261AgkbkwtVWAdWs2LWEKjtKeg" />
  <title>ITDA 잇다 - 스타트업의 성공을 위한 기획, 교육, 컨설</title>

  <!-- 스타일 -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
  <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
  <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
  <link rel="stylesheet" href="/css/id.css">

  <!-- js -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
  <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
  <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
  <script src="https://player.vimeo.com/api/player.js"></script>
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
      <a href="/" class="logo"><img src="img/logo.png" alt=""></a>
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
      <div class="video_item">
        <img src="/img/v_img01.png" alt="">
        <a class="video_btn" popup-open="popup-1" href="javascript:void(0)"><i class="fas fa-caret-right"></i></a>
      </div>

      <div class="video_item">
        <img src="/img/v_img02.png" alt="">
        <a class="video_btn" popup-open="popup-2" href="javascript:void(0)"><i class="fas fa-caret-right"></i></a>
      </div>
    </div>

    <div class="popup" popup-name="popup-1">
      <div class="popup-content">
        <iframe src="https://player.vimeo.com/video/513664697" class="iframe01" width="800" height="450" frameborder="0" allow="autoplay; fullscreen; picture-in-picture" allowfullscreen></iframe>
        <a class="close-btn" popup-close="popup-1" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>

    <div class="popup" popup-name="popup-2">
      <div class="popup-content">
        <iframe src="https://player.vimeo.com/video/512854897" class="iframe02" width="800" height="450" frameborder="0" allow="autoplay; fullscreen; picture-in-picture" allowfullscreen></iframe>
        <a class="close-btn" popup-close="popup-2" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>

    <div class="arrowSlider">
      <span class="prev mainslider_prev"><i class="fas fa-caret-left"></i></span>
      <span class="next mainslider_next"><i class="fas fa-caret-right"></i></span>
    </div>

    <div class="main_slider">
      <div class="item bg03">
        <div class="inner">
          <div class="main_txt">
            <h1>스타트업</h1>
            <h1><span>성공을</span> 잇다</h1>
            <p>한 눈에 보는 정부지원사업 어플 제공</p>
            <div class="btn_wrap">
              <%-- <a href="https://play.google.com/store/apps/details?id=com.ctns.itda_user" target="_blank" class="app_btn alert01"><i class="fab fa-google-play"></i> Android</a> <a href="https://apps.apple.com/kr/app/id1555629389" target="_blank"
                class="app_btn"><i class="fab fa-app-store"></i> iOS</a> --%>
            </div>
          </div>
        </div>
      </div>
      <div class="item bg01">
        <div class="inner">
          <div class="main_txt">
            <h1>스타트업</h1>
            <h1><span>희망을</span> 잇다</h1>
            <p>니즈를 충족시키는 웹/앱 제작</p>
            <a href="#section06" class="btn">제작문의</a>
          </div>
        </div>
      </div>
      <div class="item bg02">
        <div class="inner">
          <div class="main_txt">
            <h1>스타트업</h1>
            <h1><span>미래를</span> 잇다</h1>
            <p>창업가를 위한 실전교육</p>
            <a href="https://itdaedu.modoo.at" target="_blank" class="btn">교육신청</a>
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
      <a href="https://itdaedu.modoo.at" target="_blank" class="item"><img src="/img/ico_quick01.png" alt="교육신청"><span>교육신청</span></a>
      <a href="https://itdabiz.com/selections" class="item"><img src="/img/ico_quick02.png" alt="견적계산"><span>견적계산</span></a>
      <a href="#" class="item"><img src="/img/ico_quick03.png" alt="회사소개"><span>회사소개</span></a>
      <a href="http://pf.kakao.com/_PCxokK" target="_blank" class="item"><img src="/img/ico_quick04.png" alt="고객센터"><span>고객센터</span></a>
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
            <a class="item" popup-open="popup-review1" href="javascript:void(0)">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img01.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">라임푸드</p>
                  <p class="cust_name">우유리 대표님</p>
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
                <p>서비스에 대해 간단하게만 설명드렸는데, 미처 생각하지 못한 부분까지 꼼꼼하게 살펴보고 제작해주신 덕에, 복잡했던 시스템이 간편해졌어요. 시스템이 간소화되니 거래처가 증가하여, 매출도 향상되었습니다.</p>
              </div>
            </a>

            <a class="item" popup-open="popup-review2" href="javascript:void(0)">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img02.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">이지모비</p>
                  <p class="cust_name">김효경 대표님</p>
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
                <p>중고거래 앱 제작을 문의했는데, 앱에 대한 지식이 별로 없었어요. 그러다보니 앞서 프로젝트가 엎어진 적도 있었는데, 잇다에서는 처음부터 끝까지 밀착지원해주셔서 앱 런칭을 순탄하게 할 수 있었습니다.</p>
              </div>
            </a>
            <a class="item" popup-open="popup-review3" href="javascript:void(0)">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img04.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">웰린</p>
                  <p class="cust_name">서보형 대표님</p>
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
                <p>반려동물 영양제를 판매하다보니, 회사이미지가 정말 중요해요. 잇다에서 저희 회사가 어떤 회사인지, 사용자가 한 눈에 이해할 수 있게 랜딩페이지를 제작해 주셔서 만족스럽습니다.</p>
              </div>
            </a>
            <a class="item" popup-open="popup-review4" href="javascript:void(0)">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img03.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">CTNS</p>
                  <p class="cust_name">권기정 대표님</p>
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
                <p>전동모빌리티 역경매에 대한 단순 아이디어만 가지고 어플 제작을 의뢰했는데, 기획부터 설계, 시장조사, 원가분석, 운영까지 정말 세밀하게 도와주셨습니다. 덕분에 좀 더 나은 서비스를 만들 수 있었습니다.</p>
              </div>
            </a>
            <a class="item" popup-open="popup-review5" href="javascript:void(0)">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img05.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">커파하우스</p>
                  <p class="cust_name">박승규 대표님</p>
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
                <p>처음엔 완성단계의 앱을 생각하고 방문했는데 저희가 스타트업인 점을 고려하셔서, 작게 만들고 시장반응을 보며 천천히 기능을 늘려나가길 추천하셨어요. 그 덕에 제작 단가, 기간도 줄일 수 있었고, 무엇보다 리스크를 줄일 수 있어 효율적이었습니다.</p>
              </div>
            </a>
          </div>
        </div>
      </div>
    </div>

    <!-- 리뷰 팝업 -->
    <div class="popup review_popup" popup-name="popup-review1">
      <div class="popup-content">
        <div class="profile_wrap">
          <div class="profile_img">
            <img src="/img/profile_img01.png" alt="">
          </div>
          <div class="profile_info">
            <p class="company_name">라임푸드</p>
            <p class="cust_name">우유리 대표님</p>
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
          <p>서비스에 대해 간단하게만 설명드렸는데, 미처 생각하지 못한 부분까지 꼼꼼하게 살펴보고 제작해주신 덕에, 복잡했던 시스템이 간편해졌어요. 시스템이 간소화되니 거래처가 증가하여, 매출도 향상되었습니다.</p>
        </div>
        <a class="close-btn" popup-close="popup-review1" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>

    <div class="popup review_popup" popup-name="popup-review2">
      <div class="popup-content">
        <div class="profile_wrap">
          <div class="profile_img">
            <img src="/img/profile_img02.png" alt="">
          </div>
          <div class="profile_info">
            <p class="company_name">이지모비</p>
            <p class="cust_name">김효경 대표님</p>
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
          <p>중고거래 앱 제작을 문의했는데, 앱에 대한 지식이 별로 없었어요. 그러다보니 앞서 프로젝트가 엎어진 적도 있었는데, 잇다에서는 처음부터 끝까지 밀착지원해주셔서 앱 런칭을 순탄하게 할 수 있었습니다.</p>
        </div>
        <a class="close-btn" popup-close="popup-review2" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>

    <div class="popup review_popup" popup-name="popup-review3">
      <div class="popup-content">
        <div class="profile_wrap">
          <div class="profile_img">
            <img src="/img/profile_img04.png" alt="">
          </div>
          <div class="profile_info">
            <p class="company_name">웰린</p>
            <p class="cust_name">서보형 대표님</p>
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
          <p>반려동물 영양제를 판매하다보니, 회사이미지가 정말 중요해요. 잇다에서 저희 회사가 어떤 회사인지, 사용자가 한 눈에 이해할 수 있게 랜딩페이지를 제작해 주셔서 만족스럽습니다.</p>
        </div>
        <a class="close-btn" popup-close="popup-review3" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>

    <div class="popup review_popup" popup-name="popup-review4">
      <div class="popup-content">
        <div class="profile_wrap">
          <div class="profile_img">
            <img src="/img/profile_img03.png" alt="">
          </div>
          <div class="profile_info">
            <p class="company_name">CTNS</p>
            <p class="cust_name">권기정 대표님</p>
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
          <p>전동모빌리티 역경매에 대한 단순 아이디어만 가지고 어플 제작을 의뢰했는데, 기획부터 설계, 시장조사, 원가분석, 운영까지 정말 세밀하게 도와주셨습니다. 덕분에 좀 더 나은 서비스를 만들 수 있었습니다.</p>
        </div>
        <a class="close-btn" popup-close="popup-review4" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>


    <div class="popup review_popup" popup-name="popup-review5">
      <div class="popup-content">
        <div class="profile_wrap">
          <div class="profile_img">
            <img src="/img/profile_img05.png" alt="">
          </div>
          <div class="profile_info">
            <p class="company_name">커파하우스</p>
            <p class="cust_name">박승규 대표님</p>
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
          <p>처음엔 완성단계의 앱을 생각하고 방문했는데 저희가 스타트업인 점을 고려하셔서, 작게 만들고 시장반응을 보며 천천히 기능을 늘려나가길 추천하셨어요. 그 덕에 제작 단가, 기간도 줄일 수 있었고, 무엇보다 리스크를 줄일 수 있어 효율적이었습니다.</p>
        </div>
        <a class="close-btn" popup-close="popup-review5" href="javascript:void(0)"><i class="fas fa-times"></i></a>
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
            <a class="item" popup-open="popup-review6" href="javascript:void(0)">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img06.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">체크에듀</p>
                  <p class="cust_name">박송묵 대표님</p>
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
                <p>참가자들과 의견을 공유하며, 사업 아이템에 대한 의견을 다양한 관점에서 들을 수 있어 좋았습니다. 사업계획서 작성 능력이 향상되어 기획 역량이 강화된 것 같아 만족스럽습니다.</p>
              </div>
            </a>

            <a class="item" popup-open="popup-review7" href="javascript:void(0)">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img07.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">아르테미스</p>
                  <p class="cust_name">조영현 대표님</p>
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
                <p>노하우를 공유해주신 덕분에, 정부지원사업에 합격할 것 같은 자신감이 생겼습니다. 특히 고객과 투자자입장에서 생각하는 방법은 앞으로도 많은 도움이 될 것 같아요.</p>
              </div>
            </a>
            <a class="item" popup-open="popup-review8" href="javascript:void(0)">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img08.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">휘테커</p>
                  <p class="cust_name">장기원 대표님</p>
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
                <p>사업계획서 작성 시 공통적으로 필요한 요소와 더불어, 개별적으로 맞춤형으로 코칭해 주신 덕분에 숲과 나무를 동시에 볼 수 있었던 것 같습니다. 스스로의 한계라고 생각한 부분을 넘어서라는 말씀이 어려웠지만 많은 도움이 되었습니다.</p>
              </div>
            </a>
            <a class="item" popup-open="popup-review9" href="javascript:void(0)">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img09.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">작은시선</p>
                  <p class="cust_name">조준섭 대표님</p>
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
                <p>실전 사례 위주의 코칭이 직, 간접적으로 많은 도움이 되었습니다. Business Model 도출과 서비스를 직관적으로 표현하는 것이 개인적으로 어려웠지만, 교육과 컨설팅에 전반적으로 만족했습니다.</p>
              </div>
            </a>
            <a class="item" popup-open="popup-review10" href="javascript:void(0)">
              <div class="profile_wrap">
                <div class="profile_img">
                  <img src="/img/profile_img10.png" alt="">
                </div>
                <div class="profile_info">
                  <p class="company_name">트다</p>
                  <p class="cust_name">박수진 대표님</p>
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
                <p>저와 다른 관점에서 생각하지 못한 크리티컬포인트를 찾을 수 있다는 점과 평가자의 관점에서 트리거를 찾을 수 있었다는 점에서 의미있는 시간, 의미있는 교육이었습니다.</p>
              </div>
            </a>
          </div>
        </div>
      </div>
    </div>

    <!-- 리뷰 팝업 -->
    <div class="popup review_popup" popup-name="popup-review6">
      <div class="popup-content">
        <div class="profile_wrap">
          <div class="profile_img">
            <img src="/img/profile_img06.png" alt="">
          </div>
          <div class="profile_info">
            <p class="company_name">체크에듀</p>
            <p class="cust_name">박송묵 대표님</p>
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
          <p>참가자들과 의견을 공유하며, 사업 아이템에 대한 의견을 다양한 관점에서 들을 수 있어 좋았습니다. 사업계획서 작성 능력이 향상되어 기획 역량이 강화된 것 같아 만족스럽습니다.</p>
        </div>
        <a class="close-btn" popup-close="popup-review6" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>

    <div class="popup review_popup" popup-name="popup-review7">
      <div class="popup-content">
        <div class="profile_wrap">
          <div class="profile_img">
            <img src="/img/profile_img07.png" alt="">
          </div>
          <div class="profile_info">
            <p class="company_name">아르테미스</p>
            <p class="cust_name">조영현 대표님</p>
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
          <p>노하우를 공유해주신 덕분에, 정부지원사업에 합격할 것 같은 자신감이 생겼습니다. 특히 고객과 투자자입장에서 생각하는 방법은 앞으로도 많은 도움이 될 것 같아요.</p>
        </div>
        <a class="close-btn" popup-close="popup-review7" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>

    <div class="popup review_popup" popup-name="popup-review8">
      <div class="popup-content">
        <div class="profile_wrap">
          <div class="profile_img">
            <img src="/img/profile_img08.png" alt="">
          </div>
          <div class="profile_info">
            <p class="company_name">휘테커</p>
            <p class="cust_name">장기원 대표님</p>
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
          <p>사업계획서 작성 시 공통적으로 필요한 요소와 더불어, 개별적으로 맞춤형으로 코칭해 주신 덕분에 숲과 나무를 동시에 볼 수 있었던 것 같습니다. 스스로의 한계라고 생각한 부분을 넘어서라는 말씀이 어려웠지만 많은 도움이 되었습니다.</p>
        </div>
        <a class="close-btn" popup-close="popup-review8" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>

    <div class="popup review_popup" popup-name="popup-review9">
      <div class="popup-content">
        <div class="profile_wrap">
          <div class="profile_img">
            <img src="/img/profile_img09.png" alt="">
          </div>
          <div class="profile_info">
            <p class="company_name">작은시선</p>
            <p class="cust_name">조준섭 대표님</p>
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
          <p>실전 사례 위주의 코칭이 직, 간접적으로 많은 도움이 되었습니다. Business Model 도출과 서비스를 직관적으로 표현하는 것이 개인적으로 어려웠지만, 교육과 컨설팅에 전반적으로 만족했습니다.</p>
        </div>
        <a class="close-btn" popup-close="popup-review9" href="javascript:void(0)"><i class="fas fa-times"></i></a>
      </div>
    </div>

    <div class="popup review_popup" popup-name="popup-review10">
      <div class="popup-content">
        <div class="profile_wrap">
          <div class="profile_img">
            <img src="/img/profile_img10.png" alt="">
          </div>
          <div class="profile_info">
            <p class="company_name">트다</p>
            <p class="cust_name">박수진 대표님</p>
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
          <p>저와 다른 관점에서 생각하지 못한 크리티컬포인트를 찾을 수 있다는 점과 평가자의 관점에서 트리거를 찾을 수 있었다는 점에서 의미있는 시간, 의미있는 교육이었습니다.</p>
        </div>
        <a class="close-btn" popup-close="popup-review10" href="javascript:void(0)"><i class="fas fa-times"></i></a>
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

      <div class="pin">
        <span class="shadow"></span>
      </div>
      <div class='pulse'></div>

      <div class="map_wrap m_visible"></div>
    </div>
  </section>


  <section id="section06" class="section06">
    <div class="inner">
      <div class="flex_wrap">
        <div class="flex_box txt_wrap">
          <h2 class="sect_tit"><span>문의하기</span></h2>
          <p class="cust_center_num">070-7733-4747</p>
          <p class="cust_info">전화상담 가능 시간 AM 10:00~19:00<br>(주말, 공휴일 휴무, 점심시간 PM 12:00 ~ 13:00)</p>
          <a href="https://pf.kakao.com/_PCxokK" class="btn">카카오톡 문의하기</a>
        </div>
      </div>
    </div>
  </section>

  <footer class="footer">
    <div class="top">
      <div class="inner">
        <ul>
          <li><a href="/standard">이용약관</a></li>
          <li><a href="/personinfo">개인정보처리방침</a></li>
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
