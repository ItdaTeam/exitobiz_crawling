// jQuery - fullpage
$(document).ready(function() {
    $('#fullpage').fullpage({
        anchors: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15'],
        navigation: true,
        navigationPosition: 'right',
        navigationTooltips: [],
        slidesNavigation: true,
        easing: 'linear',
        afterLoad: function(anchorLink, index) {
            if (anchorLink == '01') {
                $('.landing-visual__title').addClass('tit_fadeup');
                $('.delay').css('animation-delay', '0.3s');
                $('.landing-down').css('height', '0');
            } else {
                $('.landing-visual__title').removeClass('tit_fadeup');
                $('.landing-down').css('height', '52px');
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
            if (anchorLink == '09') {
                $('.add1 .text').addClass('usebox_fadeup');
            } else {
                $('.add1 .text').removeClass('usebox_fadeup');
            }
            if (anchorLink == '10') {
                $('.add2 .text').addClass('usebox_fadeup');
            } else {
                $('.add2 .text').removeClass('usebox_fadeup');
            }
            if (anchorLink == '11') {
                $('.add3 .text').addClass('usebox_fadeup');
            } else {
                $('.add3 .text').removeClass('usebox_fadeup');
            }

            if (anchorLink == '12') {
                $('.tip1 .text').addClass('usebox_fadeup');
            } else {
                $('.tip1 .text').removeClass('usebox_fadeup');
            }
            if (anchorLink == '13') {
                $('.tip2 .text').addClass('usebox_fadeup');
            } else {
                $('.tip2 .text').removeClass('usebox_fadeup');
            }
            if (anchorLink == '14') {
                $('.tip3 .text').addClass('usebox_fadeup');
            } else {
                $('.tip3 .text').removeClass('usebox_fadeup');
            }
            if (anchorLink == '15') {
                $('.landing-down').css('display', 'none');
            } else {
                $('.landing-down').css('display', 'block');
            }
            // 로고 및 인스타그램 아이콘, 네비게이션 색상변경
            if (anchorLink == '02' || anchorLink == '15') {
                $('#fp-nav ul li a span').css('background', '#fff');
            } else {
                $('#fp-nav ul li a span').css('background', '#333');
            }
            if (anchorLink == '02' || anchorLink == '12' || anchorLink == '13' || anchorLink == '14' || anchorLink == '15') {
                $('.landing-header .logo img').attr('src', 'mobile/images/ic_m_logo_wh.png');
                $('.landing-header .insta img').attr('src', 'mobile/images/ic_m_insta_wh.png');
            } else {
                $('.landing-header .logo img').attr('src', 'mobile/images/ic_m_logo.png');
                $('.landing-header .insta img').attr('src', 'mobile/images/ic_m_insta.png');
            }
        }
    });
});

// 앱다운로드버튼 클릭시 os별 앱스토어 링크 이동
var varUA = navigator.userAgent.toLowerCase();

function appdown() {
    if (varUA.match('android') != null) {
        window.location.href = 'https://play.google.com/store/apps/details?id=com.ctns.itda_user';
    } else if (varUA.indexOf("iphone") > -1 || varUA.indexOf("ipad") > -1 || varUA.indexOf("ipod") > -1) {
        window.location.href = 'https://apps.apple.com/us/app/id1555629389';
    } else {
        window.location.href = 'https://play.google.com/store/apps/details?id=com.ctns.itda_user';
    }
};