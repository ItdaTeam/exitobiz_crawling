
// 움직이는 별 배경
  function init(){
    //estrelas
    var style = ["style1", "style2", "style3", "style4"];
    var tam = ["tam1", "tam1", "tam1", "tam2", "tam3"];
    var opacity = ["opacity1", "opacity1", "opacity1", "opacity2", "opacity2", "opacity3"];
    function getRandomArbitrary(min, max) {
      return Math.floor(Math.random() * (max - min)) + min;
    }
    var estrela = "";
    var qtdeEstrelas = 280;
    var noite = document.querySelector(".constelacao");
    var widthWindow = window.innerWidth;
    var heightWindow = window.innerHeight;
    for (var i = 0; i < qtdeEstrelas; i++) {
      estrela += "<span class='estrela " + style[getRandomArbitrary(0, 4)] + " " + opacity[getRandomArbitrary(0, 6)] + " "
      + tam[getRandomArbitrary(0, 5)] + "' style='animation-delay: ." +getRandomArbitrary(0, 7)+ "s; left: "
      + getRandomArbitrary(0, widthWindow) + "px; top: " + getRandomArbitrary(0, heightWindow) + "px;'></span>";
    }
    noite.innerHTML = estrela;
    //meteoros
    var numeroAleatorio = 1200;
    setTimeout(function(){
      carregarMeteoro();
    }, numeroAleatorio);
    function carregarMeteoro(){
      setTimeout(carregarMeteoro, numeroAleatorio);
      numeroAleatorio = getRandomArbitrary(5000, 10000);
      var meteoro = "<div class='meteoro "+ style[getRandomArbitrary(0, 4)] +"'></div>";
      document.getElementsByClassName('chuvaMeteoro')[0].innerHTML = meteoro;
      setTimeout(function(){
        document.getElementsByClassName('chuvaMeteoro')[0].innerHTML = "";
      }, 500);
    }
  }
  window.onload = init;

// AOS animation 실행
$(document).ready(function() {
  AOS.init();
} );

// 헤더 스크롤 이벤트 
$(window).scroll(function(event) {
  if($(window).scrollTop() > 110) {
    $(".landing_header").addClass("is_on");
  } else {
    $(".landing_header").removeClass("is_on");
  }
  var offsets = new Array();
  var sections = new Array();
  var i = 0;
    $("section").each(function() {
      offsets[i] = Math.floor($(this).offset().top);
      sections[i] = $(this).attr("data-section");
      i++;
    });
    for (var i = 0; i < offsets.length; i++) {
      if (window.pageYOffset >= offsets[i] && window.pageYOffset < offsets[i + 1]) {
        $(".nav li[data-section='" + sections[i] + "']").addClass("active");
        $(".nav li:not([data-section='" + sections[i] + "'])").removeClass("active");
        i = offsets.length;
      } else if (window.pageYOffset >= offsets[offsets.length - 1]) {
        $(".nav li[data-section='" + sections[sections.length - 1] + "']").addClass("active");
        $(".nav li:not([data-section='" + sections[sections.length - 1] + "'])").removeClass("active");
      }
    }
});

// landing_visual scrolldown
$(function(){
  $(".scrolldown").click(function(){
    $("html, body").animate({ scrollTop: $(".landing_visual").height() },500);
  });
});

// landing_intro hover event
$(function(){
  $("dl").hover(function(){ 
    $(this).addClass("active"); 
    $(this).siblings().removeClass("active"); 
  });
});

//  앱다운로드버튼 .landing_store 스크롤 이벤트
$(function(){
  var docHeight = $(document).height();
  var winHeight = $(window).height();
  buffer = 200
  $('.landing_store').hide();
  $(window).on("scroll", function() {
      if($(window).scrollTop() < 200 || ($(window).scrollTop() + winHeight + buffer >= docHeight )){   
          $('.landing_store').hide();
      } else  {
          $('.landing_store').show();
      }
  });
});


// 앱다운로드버튼 .landing_store 클릭시 os별 앱스토어 링크 이동
var varUA = navigator.userAgent.toLowerCase(); 
function appdown() {
  if (varUA.match('android') != null) { 
    window.location.href='https://play.google.com/store/apps/details?id=com.ctns.itda_user';
  } else if (varUA.indexOf("iphone")>-1||varUA.indexOf("ipad")>-1||varUA.indexOf("ipod")>-1 ||varUA.indexOf("mac")>-1 ) { 
    window.location.href='https://apps.apple.com/kr/app/id1555629389';
  } else {
    window.location.href='https://play.google.com/store/apps/details?id=com.ctns.itda_user';
  }
};
