$(document).ready(function() {
  $('#fullpage').fullpage({
    anchors: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10','11','12' ],
    navigation: true,
    navigationPosition: 'right',
    navigationTooltips: [],
    slidesNavigation: true,
    easing: 'linear',
    afterLoad:function(anchorLink, index){
      if(anchorLink == '01'){
        $('.main-visual__title').addClass('tit_fadeup');
        $('.delay').css('animation-delay','0.3s');
      }else{
        $('.main-visual__title').removeClass('tit_fadeup'); 
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

      if (anchorLink == '09'){
        $('.tip1 .text').addClass('usebox_fadeup');
      }else{
        $('.tip1 .text').removeClass('usebox_fadeup');  
      }
      if (anchorLink == '10'){
        $('.tip2 .text').addClass('usebox_fadeup');
      }else{
        $('.tip2 .text').removeClass('usebox_fadeup');  
      }
      if (anchorLink == '11'){
        $('.tip3 .text').addClass('usebox_fadeup');
      }else{
        $('.tip3 .text').removeClass('usebox_fadeup');  
      }
      
      // 로고 및 인스타그램 아이콘, 네비게이션 색상변경
      if (anchorLink == '02' || anchorLink == '12'){
        $('#fp-nav ul li a span').css('background','#fff');
      }else{
        $('#fp-nav ul li a span').css('background','#333');
      }
      if (anchorLink == '02' || anchorLink == '09' || anchorLink == '10' || anchorLink == '11' || anchorLink == '12'){
        $('#logo').attr('src','images/ic_logo_wh.png');
        $('#insta').attr('src','images/ic_insta_wh.png');
      }else{
        $('#logo').attr('src','images/ic_logo.png');
        $('#insta').attr('src','images/ic_insta.png');
      }
    }
  });
});

