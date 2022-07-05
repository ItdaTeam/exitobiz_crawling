$(document).ready(function() {
    $('#fullpage').fullpage({
        anchors: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11'],
        navigation: true,
        navigationPosition: 'right',
        navigationTooltips: [],
        slidesNavigation: true,
        easing: 'linear',
        afterLoad: function(anchorLink, index) {
            if (anchorLink == '01') {
                $('.landing-visual__title').addClass('vtit_fadeup');
                $('.bubble--fir').addClass('vtit_fadeup');
                $('.bubble--sec').addClass('vtit_fadeup');
                $('.bubble--thr').addClass('vtit_fadeup');
            } else {
                $('.landing-visual__title').removeClass('vtit_fadeup');
                $('.bubble--fir').removeClass('vtit_fadeup');
                $('.bubble--sec').removeClass('vtit_fadeup');
                $('.bubble--thr').removeClass('vtit_fadeup');
            }
            if (anchorLink == '02') {
                $('.landing__title').addClass('tit_fadeup');
            } else {
                $('.landing__title').removeClass('tit_fadeup');
            }
            if (anchorLink == '03') {
                $('.landing-intro--sec1 h3').addClass('intro_fadeup');
            } else {
                $('.landing-intro--sec1 h3').removeClass('intro_fadeup');
            }
            if (anchorLink == '04') {
                $('.landing-intro--sec2 h3').addClass('intro_fadeup');
            } else {
                $('.landing-intro--sec2 h3').removeClass('intro_fadeup');
            }
            if (anchorLink == '05') {
                $('.landing-intro--sec3 h3').addClass('intro_fadeup');
            } else {
                $('.landing-intro--sec3 h3').removeClass('intro_fadeup');
            }
            if (anchorLink == '06') {
                $('.use1 .text').addClass('usebox_fadeup');
                $('.use1 .text').css('animation-delay', '0.5s');
                $('.landing-use__title').addClass('use_fadeup');
            } else {
                $('.use1 .text').removeClass('usebox_fadeup');
                $('.landing-use__title').removeClass('use_fadeup');
            }
            if (anchorLink == '07') {
                $('.use2 .text').addClass('usebox_fadeup');
            } else {
                $('.use2 .text').removeClass('usebox_fadeup');
            }
            if (anchorLink == '08') {
                $('.use3 .text').addClass('usebox_fadeup');
            } else {
                $('.use3 .text').removeClass('usebox_fadeup');
            }

            // 로고 및 인스타그램 아이콘, 네비게이션 색상변경
            if (anchorLink == '02' || anchorLink == '10' || anchorLink == '11') {
                $('#landing_logo').attr('src', 'pc/images/ic_logo_wh.png');
                $('#langding_insta').attr('src', 'pc/images/ic_insta_wh.png');
                $('#fp-nav ul li a span').css('background', '#fff');

            } else {
                $('#landing_logo').attr('src', 'pc/images/ic_logo.png');
                $('#langding_insta').attr('src', 'pc/images/ic_insta.png');
                $('#fp-nav ul li a span').css('background', '#333');
            }
        }
    });
});

// tip silde
$(document).ready(function() {
    $('.landing-tip__slider').slick({
        autoplay: true,
        autoplaySpeed: 2000,
        arrows: false,
        dots: true,
        infinite: true,
        speed: 800,
        slidesToShow: 1,
        centerMode: true,
        variableWidth: true
    });
});