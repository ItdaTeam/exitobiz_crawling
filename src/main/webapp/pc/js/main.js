$(document).ready(function() {
  $('#fullpage').fullpage({
    anchors: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10'],
    navigation: true,
    navigationPosition: 'right',
    navigationTooltips: [],
    slidesNavigation: true,
    easing: 'linear',
    afterLoad:function(anchorLink, index){
      if(anchorLink == '01'){
        $('.main-visual__mobile').addClass('mockup_fade');
        $('.main-visual__title').addClass('vtit_fadeup');
      }else{
        $('.main-visual__mobile').removeClass('mockup_fade'); 
        $('.main-visual__title').removeClass('vtit_fadeup'); 
      }
      if(anchorLink == '02'){
        $('.main__title').addClass('tit_fadeup');
      }else{
        $('.main__title').removeClass('tit_fadeup');        
      }
      if(anchorLink == '03' ){
        $('.main-intro--sec1 h3').addClass('intro_fadeup');
      }else{
        $('.main-intro--sec1 h3').removeClass('intro_fadeup');        
      }
      if(anchorLink == '04'){
        $('.main-intro--sec2 h3').addClass('intro_fadeup');
      }else{
        $('.main-intro--sec2 h3').removeClass('intro_fadeup');        
      }
      if(anchorLink == '05'){
        $('.main-intro--sec3 h3').addClass('intro_fadeup');
      }else{
        $('.main-intro--sec3 h3').removeClass('intro_fadeup');        
      }
      if (anchorLink == '06'){
        $('.use1 .text').addClass('usebox_fadeup');
        $('.use1 .text').css('animation-delay','0.5s');
        $('.main-use__title').addClass('use_fadeup');
      }else{
        $('.use1 .text').removeClass('usebox_fadeup');  
        $('.main-use__title').removeClass('use_fadeup'); 
      }
      if (anchorLink == '07'){
        $('.use2 .text').addClass('usebox_fadeup');
      }else{
        $('.use2 .text').removeClass('usebox_fadeup');  
      }
      if (anchorLink == '08'){
        $('.use3 .text').addClass('usebox_fadeup');
      }else{
        $('.use3 .text').removeClass('usebox_fadeup');  
      }

      // 로고 및 인스타그램 아이콘, 네비게이션 색상변경
      if (anchorLink == '02' || anchorLink == '10'){
        $('#logo').attr('src','images/ic_logo_wh.png');
        $('#insta').attr('src','images/ic_insta_wh.png');
        $('#fp-nav ul li a span').css('background','#fff');
       
      }else{
        $('#logo').attr('src','images/ic_logo.png');
        $('#insta').attr('src','images/ic_insta.png');
        $('#fp-nav ul li a span').css('background','#333');
      }
    }
  });
});

// tip silde
$(document).ready(function() {
  $('.main-tip__slider').slick({
    autoplay: true,
    autoplaySpeed:2000,
    arrows:false,
    dots: true,
    infinite: true,
    speed:800,
    slidesToShow: 1,
    centerMode: true,
    variableWidth: true
  });
});