$(document).ready(function() {

  // 스크롤 애니메이션
  AOS.init();

  // 퀵메뉴
  $('.quick_trigger').click(function() {
    $('.quick_menu_wrap').toggleClass('close');
  });

  // 헤더
  $(window).scroll(function() {
    if ($(window).scrollTop() > 30) {
      $('header.header').addClass('active');
    } else {
      $('header.header').removeClass('active');
    }
  });

  // 헤더 gnb
  $('.gnb li a').click(function() {
    $('html, body').animate({
      scrollTop: ($($.attr(this, 'href')).offset().top - $(window).height() / 5)
    }, 500);
    return false;
  });

  // top 버튼
  $('.top_btn').click(function() {
    $('html, body').animate({
      scrollTop: 0
    }, 400);
    return false;
  });


  // 메인 슬라이더
  $('.main_slider').slick({
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 5000,
    infinite: true,
    arrows: true,
    prevArrow: $('.mainslider_prev'),
    nextArrow: $('.mainslider_next'),
  });

  // section04 이용 후기 슬라이더
  $('.section04_slider.slider1').slick({
    infinite: true,
    autoplay: true,
    autoplaySpeed: 3000,
    slidesToShow: 3,
    slidesToScroll: 1,
    arrows: true,
    prevArrow: $('.sect04_prev'),
    nextArrow: $('.sect04_next'),
    responsive: [{
      breakpoint: 768,
      settings: {
        slidesToShow: 1
      }
    }]
  });

  $('.section04_slider.slider2').slick({
    infinite: true,
    autoplay: true,
    autoplaySpeed: 3000,
    slidesToShow: 3,
    slidesToScroll: 1,
    arrows: true,
    prevArrow: $('.sect04_prev2'),
    nextArrow: $('.sect04_next2'),
    responsive: [{
      breakpoint: 768,
      settings: {
        slidesToShow: 1
      }
    }]
  });


  // 비디오 팝업x
  var iframe01 = document.querySelector('.iframe01');
  var player01 = new Vimeo.Player(iframe01);
  var iframe02 = document.querySelector('.iframe02');
  var player02 = new Vimeo.Player(iframe02);
  player01.on('ended', function() {
    $('.popup').fadeOut(300);
    player01.pause();
  });
  player02.on('ended', function() {
    $('.popup').fadeOut(300);
    player02.pause();
  });

  $(function() {
    $('[popup-open]').on('click', function() {
      var popup_name = $(this).attr('popup-open');
      $('[popup-name="' + popup_name + '"]').fadeIn(300);
    });

    $('[popup-close]').on('click', function() {
      var popup_name = $(this).attr('popup-close');
      $('[popup-name="' + popup_name + '"]').fadeOut(300);
      player01.pause();
      player02.pause();
    });

    $('.popup').on('click', function() {
      var popup_name = $(this).find('[popup-close]').attr('popup-close');
      $('[popup-name="' + popup_name + '"]').fadeOut(300);
      player01.pause();
      player02.pause();
    }).children().click(function() {
      return false;
    });

  });


  // section04 탭
  $(function() {
    $(".tab li").click(function() {
      $(".tab li").removeClass('on');
      $(".conBox").removeClass('on');
      $(this).addClass('on');
      $("#" + $(this).data('id')).addClass('on');
    });
  });

});
