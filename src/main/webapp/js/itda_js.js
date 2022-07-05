(() => {
  $(window).scroll(function() {

    // 메인
    var $window = $(window),
      $body = $('body'),
      $panel = $('.panel');

    var scroll = $window.scrollTop() + ($window.height() / 1.3);

    $panel.each(function() {
      var $this = $(this);

      // if position is within range of this panel.
      // So position of (position of top of div <= scroll position) && (position of bottom of div > scroll position).
      // Remember we set the scroll to 33% earlier in scroll var.
      if ($this.position().top <= scroll && $this.position().top + $this.height() > scroll) {

        // Remove all classes on body with color-
        $body.removeClass(function(index, css) {
          return (css.match(/(^|\s)color-\S+/g) || []).join(' ');
        });

        // Add class of currently active div
        $body.addClass('color-' + $(this).data('color'));
      }
    });

    $('.panel04 .img_box').css('width', scroll);
  }).scroll();

  // panel03 tab
  $('.panel03 .tab_menu li').click(function() {
    $('.panel03 .tab_menu li').removeClass('active');
    $('.panel03 .tab_cont').removeClass('active');
    $(this).addClass('active');
    $('.' + $(this).data('tab')).addClass('active');
  });



  // 서브
  // 서브헤더
  $(window).scroll(function(){
    if($(window).scrollTop() > 90 && $(window).scrollTop() < 399){
      $('#sub .header').addClass('active');
    }else if($(window).scrollTop() > 400){
      $('#sub .header').removeClass('active');
      $('#sub .header').addClass('active02');
    }else {
      $('#sub .header').removeClass('active02');
    }
  });

  $('.est_check_items:nth-child(1)').addClass('open');
  $('.est_check_items:nth-child(2)').addClass('open');
  $('.est_check_items:nth-child(3)').addClass('open');
  $('.est_check_items_tit').click(function(){
    $(this).parent().toggleClass('open');
  });

  /* 동적생성해야해서 주석처리 전승희 */
  // $('.est_check_items .items_wrap .item').click(function(){
  //   $(this).toggleClass('select');
  // });

  $('.est_check_items .items_wrap .item').mouseenter(function(){
    var previewImg = $(this).data('preview');
    $('.est_preview .img_wrap img').attr('src', '');
  });

  if($(window).width() > 768 ){
    $('.est_cont .est_preview .btn_close').click(function(){
      $('.est_cont').removeClass('preview');
      $('.est_cont_info').css('top', '16rem');
    });

    $('.est_cont .est_calc .btn_preview').click(function(){
      $('.est_cont').addClass('preview');
      $('.est_cont_info').css('top', '12rem');
    });
  }



})();
