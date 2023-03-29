<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/header.jsp" %>
    <link rel="stylesheet" href="../css/banner.css">
    <link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css"/>
    <script src="https://unpkg.com/swiper@7/swiper-bundle.min.js"></script>
</head>
<body>
<div class="main_wrap">
    <%@ include file="../include/nav.jsp" %>
        <div class="main_container">
            <section class="main_section">
                <h2 class="main_title"><span class="material-icons-round"> done_outline</span>배너관리</h2>
                <div class="main_summary">
                    <dl>
                        <dt>배너수</dt>
                        <dd><%=request.getAttribute("totalBanner")%>개</dd>
                    </dl>
                    <dl>
                        <dt>활성화</dt>
                        <dd><%=request.getAttribute("totalActiveBanner")%>개</dd>
                    </dl>
                    <!-- 클릭시 팝업창 띄움 -->
                    <a href="javascript:void(0)" class="popup_trigger"  onclick="showPop('new_banner');"><i></i>배너 추가</a>
                </div>
                <div class="main_utility">
                    <div class="btn_wrap">
                        <button class="btn stroke" onclick="exportExcel();">엑셀다운로드</button>
                    </div>
                </div>
                <div class="tabMenu">
                    <ul id="tabList">
                        <li class="TabM1">웹 리스트</li>
                        <li class="TabM2">메일 리스트</li>
                    </ul>
                </div>
                <div class="main_content">
                    <!-- 보드 영역 main_dashboard-->
                    <div class="main_dashboard">
                        <div class="sub_cont">
                            <div class="btn_wrap">
                                <select name="viewNum" id="bannerGridPageCount">
                                    <option value="100">100개씩</option>
                                    <option value="50">50개씩</option>
                                    <option value="30">30개씩</option>
                                </select>
                                <button type="button" class="btn stroke" onclick="_getUserGridLayout('bannerLayout', bannerGrid);">칼럼위치저장</button>
                                <button type="button" class="btn stroke" onclick="_resetUserGridLayout('bannerLayout', bannerGrid, bannerColumns);">칼럼초기화</button>
                                <button type="button" class="btn stroke" onclick="updateBannerGrid()"> 칼럼저장 </button>
                            </div>
                        </div>
                        <div class="grid_wrap" style="position:relative;">
                            <div id="bannerGrid"></div>
                            <div id="bannerGridPager" class="pager"></div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <!-- 팝업 -->
        <!-- 팝업 : 배너추가 -->
        <div class="popup" id="new_banner">
            <div class="popup_container">
                <div class="popup_head">
                    <p class="popup_title">배너추가</p>
                    <button type="button" class="popup_close" onclick="closePop();">x</button>
                </div>
                <div class="popup_inner">
                    <dfn>필수항목 <i>*</i></dfn>
                    <form action="#" method="post" id="newBannerForm" onsubmit="return false;">
                        <input type="hidden" name="bannerLoc">
                        <input type="hidden" name="bannerCtg">
                        <input type="hidden" name="bannerType">
                        <table>
                            <tbody>
                                <tr>
                                    <th>구분<i>*</i></th>
                                    <td>
                                        <select name="new_banner_type" id="new_banner_type">
                                            <option value="web" selected="selected">웹</option>
                                            <option value="mail">메일</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th>카테고리<i>*</i></th>
                                    <td>
                                        <select name="new_banner_ctg" id="new_banner_ctg" onchange="bannerChange(this);">
                                            <option value="공지사항이동" selected="selected">공지사항이동</option>
                                            <option value="공지세부페이지이동">공지세부페이지이동</option>
                                            <option value="아웃링크">아웃링크</option>
                                            <option value="인앱링크">인앱링크</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th>제목<i>*</i></th>
                                    <td>
                                        <input type="text" id="new_banner_title" name="bannerTitle" style="width:300px;">
                                    </td>
                                </tr>
                                <!-- 배너위치가 홈화면일때 -->
                                <tr id="new_banner_link_tb">
                                    <th>링크<i>*</i></th>
                                    <td>
                                        <input type="text" id="new_banner_link" name="bannerLink" style="width:300px;">
                                    </td>
                                </tr>
                                <!-- 배너위치가 마이페이지일때 -->
                                <tr id="new_banner_noti_tb">
                                    <th>공지/콘텐츠 번호<i>*</i></th>
                                    <td>
                                        <input type="text" id="new_banner_noti_idx" name="bannerNotiIdx">
                                    </td>
                                </tr>
                                <!-- 배너위치가 홈+마이페이지일때 -->
                                <tr>
                                    <th>배너이미지</th>
                                    <td>
                                            <input type="file" id="new_banner_image" name="image" style="width:300px;">
<%--                                        <input type="file" id="new_banner_image" name="new_banner_image" style="width:300px;" onchange="uploadImgPreview('new_banner');">--%>
                                    </td>
<%--                                    <td><span class="opt_img" id="new_banner_img"></span></td>--%>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                    <div class="popup_btn_area">
                        <button type="button" class="btn confirm" onclick="saveNewBanner();">추가</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 배너추가 팝업 영역 끝-->
        <!-- 팝업 : 정보 수정 -->
        <div class="popup" id="modify_banner">
            <div class="popup_container">
                <div class="popup_head">
                    <p class="popup_title">정보 수정</p>
                    <button type="button" class="popup_close" onclick="closePop();">x</button>
                </div>
                <div class="popup_inner">
                    <dfn>필수항목 <i>*</i></dfn>
                    <form action="#" method="post" id="updateBannerForm" onsubmit="return false;">
                        <input type="hidden" name="bannerLoc">
                        <input type="hidden" name="bannerCtg">
                        <input type="hidden" name="bannerType">
                        <input type="hidden" name="activeYn">
                        <table>
                            <tbody>
                                <tr>
                                    <th>활성화<i>*</i></th>
                                    <td>
                                        <input type="hidden" id="modify_banner_index" name="index">
                                        <input type="checkbox" id="modify_banner_active" name="modify_banner_active" checked>
                                        <label for="modify_banner_active">체크 시, 활성화</label>
                                    </td>
                                </tr>
                                <tr>
                                    <th>구분<i>*</i></th>
                                    <td>
                                        <select name="modify_banner_type" id="modify_banner_type">
                                            <option value="web">웹</option>
                                            <option value="mail">메일</option>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <th>카테고리<i>*</i></th>
                                    <td>
                                        <select name="modify_banner_ctg" id="modify_banner_ctg">
                                            <option value="공지사항이동">공지사항이동</option>
                                            <option value="공지세부페이지이동">공지세부페이지이동</option>
                                            <option value="아웃링크">아웃링크</option>
                                            <option value="인앱링크">인앱링크</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th>제목<i>*</i></th>
                                    <td>
                                        <input type="text" id="modify_banner_title" name="bannerTitle" style="width:300px;">
                                    </td>
                                </tr>
                                <!-- 배너위치가 홈화면일때 -->
                                <tr id="modify_banner_link_tb">
                                    <th>링크<i>*</i></th>
                                    <td>
                                        <input type="text" id="modify_banner_link" name="bannerLink" style="width:300px;">
                                    </td>
                                </tr>
                                <!-- 배너위치가 마이페이지일때 -->
                                <tr id="modify_banner_noti_tb">
                                    <th>공지번호<i>*</i></th>
                                    <td>
                                        <input type="text" id="modify_banner_noti_idx" name="bannerNotiIdx">
                                    </td>
                                </tr>
                                <!-- 배너위치가 홈+마이페이지일때 -->
                                <tr>
                                    <th>배너이미지</th>
                                    <td>
                                        <input type="file" id="modify_banner_image" name="image" style="width:300px;">
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                    <div class="popup_btn_area">
                        <button type="button" class="btn fill" onclick="updateBanner();">수정</button>
                        <button type="button" class="btn stroke" onclick="deleteBanner();">삭제</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="popup" id="image">
            <div id="bannerImg" onmousedown="closePop();">
            </div>
        </div>
        <!-- 정보수정 팝업 영역 끝-->
    <!-- 팝업 : 배너이미지 팝업 -->
    <div class="popup" id="view-img">
        <div class="popup_container" style="width:800px !important; overflow:hidden;">
            <div class="popup_head">
                <p class="popup_title">배너이미지 보기</p>
                <button type="button" class="popup_close" onclick="closePop();">x</button>
            </div>
            <!-- swiper -->
            <div class="popup_inner">
                <div class="swiper-container gallery-top">
                    <div id="swiperTop" class="swiper-wrapper">
                        <div class="swiper-slide"><div class="swiper-slide-container">Slide 1</div></div>
                        <div class="swiper-slide"><div class="swiper-slide-container">Slide 2</div></div>
                        <div class="swiper-slide"><div class="swiper-slide-container">Slide 3</div></div>
                        <div class="swiper-slide"><div class="swiper-slide-container">Slide 4</div></div>
                        <div class="swiper-slide"><div class="swiper-slide-container">Slide 5</div></div>
                    </div>
                    <!-- Add Arrows -->
                    <div class="swiper-button-next"></div>
                    <div class="swiper-button-prev"></div>
                </div>
                <div class="swiper-container gallery-thumbs">
                    <div id="swiperBottom" class="swiper-wrapper">
                        <div class="swiper-slide"><div class="swiper-slide-container">Slide 1</div></div>
                        <div class="swiper-slide"><div class="swiper-slide-container">Slide 2</div></div>
                        <div class="swiper-slide"><div class="swiper-slide-container">Slide 3</div></div>
                        <div class="swiper-slide"><div class="swiper-slide-container">Slide 4</div></div>
                        <div class="swiper-slide"><div class="swiper-slide-container">Slide 5</div></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 배너이미지 팝업 영역 끝-->

    <style>
        .custom-button button {
            background-color: transparent !important;
        }

        .swiper-button-prev, .swiper-rtl .swiper-button-next {
            left: 50px;
            right: auto;
        }

        .swiper-button-next, .swiper-rtl .swiper-button-prev {
            right: 50px;
            left: auto;
        }
        .swiper-slide {
            height: 240px;
        }
    </style>
    </div>
</body>
</html>
<script>
    var bannerView;
    var bannerGrid;
    var bannerGridPager;
    var bannerColumns;
    var staffId = "<%=session.getAttribute("staffId")%>";

    var text = 'web';

    function pageOnLoad() {
        $("#banner").addClass("current");
        loadGridBannerList('init');
        getBannerList();
        // sessionCheck(staffId);
    }

    // 탭메뉴
    $(".TabM1").click(function(){
        $(".TabM1").css("background-color","#37f1aa").css("border","1px solid #37f1aa");
        $(".TabM2").css("background-color","#fff").css("border","1px solid #ddd");
        $("#supportDiv").css("display","block");
        text = 'web';
        getBannerList();
    });

    $(".TabM2").click(function(){
        $(".TabM2").css("background-color","#37f1aa").css("border","1px solid #37f1aa");
        $(".TabM1").css("background-color","#fff").css("border","1px solid #ddd");
        $("#supportDiv").css("display","block");
        text = 'mail';
        getBannerList();
    });

    var galleryTop = new Swiper('.gallery-top', {
        // spaceBetween: 10,
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },

    });

    var galleryThumbs = new Swiper('.gallery-thumbs', {
        spaceBetween: 10,
        centeredSlides: true,
        slidesPerView: 'auto',
        touchRatio: 0.2,
        slideToClickedSlide: true,
        loopedSlides: 4,
    });



    galleryTop.controller.control = galleryThumbs;
    galleryThumbs.controller.control = galleryTop;

    function imageModalPop(ctx) {

        var item = ctx.item

        var param = {
            banner_type : text,
            item : ctx.item.index
        }

        $.ajax({
            type : 'GET',
            url : '/cms/getBannerList',
            async : false, // 비동기모드 : true, 동기식모드 : false
            data : param,
            success : function(result) {
                $("#swiperTop .swiper-slide").remove();
                $("#swiperBottom .swiper-slide").remove();

                var slideTo;

                result.forEach((obj, i) => {
                    if(obj.bannerImg == item.bannerImg){
                        var img = "<img src='"+ obj.bannerImg +"'>";
                        $("#swiperTop").append('<div class="swiper-slide" data-id="'+ obj.index +'"><div class="swiper-slide-container">'+ img +'</div></div>')
                    }  
                        
                  //  
                    // if (item.id == obj.index) {
                    //     slideTo = Number(i)
                    // }
                   // $("#swiperTop").append('<div class="swiper-slide" data-id="'+ obj.index +'"><div class="swiper-slide-container">'+ img +'</div></div>')
                })
                console.log(slideTo);
                galleryTop.slideTo(slideTo)
                galleryThumbs.slideTo(slideTo)
                var transform = -288 * slideTo;
                console.log(transform);
                $("#swiperBottom").css("transform", "translate3d(" + transform +", 0, 0)")
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });

        galleryTop.update();
        galleryThumbs.update();


        $('#view-img').addClass("is_on")

    }

    function loadGridBannerList(type, result) {
        if(type == "init"){
            bannerView = new wijmo.collections.CollectionView(result, {
                pageSize: 100
            });

            //페이지 이동
            bannerGridPager = new wijmo.input.CollectionViewNavigator('#bannerGridPager', {
                byPage : true,
                headerFormat: '{currentPage:n0} / {pageCount:n0}',
                cv: bannerView
            });

            bannerColumns = [
                { binding: 'index', header: '배너번호', isReadOnly: true, width: 100, align:"center"},
                { binding: 'bannerTitle', header: '제목', isReadOnly: true, width: 300, align:"center"},
                { binding: 'bannerCtgNm', header: '카테고리', isReadOnly: true, width: 120, align:"center"},
                { binding : 'bannerType', header: '구분', visible : false},
                { binding: 'cretDt', header: '작성날짜', isReadOnly: true, width: 100, align:"center"},
                { binding: 'updtDt', header: '수정날짜', isReadOnly: true, width: 100, align:"center"},
                { binding: 'bannerImg', header: '배너이미지', isReadOnly: true, width: 200, align:"center",
                            cellTemplate: wijmo.grid.cellmaker.CellMaker.makeImage({
                                click:(e,ctx) =>imageModalPop(ctx)
                            })
                },
                { binding: 'bannerNotiIdx', header: '공지/콘텐츠번호', isReadOnly: true, width: 100, align:"center"},
                { binding: 'bannerLink', header: '링크', isReadOnly: true, width: 200, align:"center"},
                { binding: 'activeYn', header: '활성화', isReadOnly: true, width: 100, align:"center"},
                { binding : 'sort', header : '정렬', align: 'center', isReadOnly: false, dataType:"Number" },
                { binding: 'edit', header: '배너수정', width: 100, align:"center",
                    cellTemplate: wijmo.grid.cellmaker.CellMaker.makeButton({
                        text: '<b>수정</b>',
                        click: (e, ctx) => {
                            showPop("modify_banner")
                        }
                    })
                },
                { binding: 'content', header: '내용', isReadOnly: true, width: 200, align:"center", visible: false}
            ]

            bannerGrid = new wijmo.grid.FlexGrid('#bannerGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns : bannerColumns,
                itemsSource: bannerView,
                loadedRows: function(s, e) {
                    s.autoSizeColumns();
                },
                cellEditEnded: function(s, e) {
                    s.autoSizeColumn(e.col);
                },
                rowEditEnded: function(s, e) {
                    s.autoSizeColumns();
                }
            });

            _setUserGridLayout('bannerLayout', bannerGrid, bannerColumns);

            //행번호 표시하기
            bannerGrid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                    cell.textContent = (r + 1).toString();
                }
            };
        }else {
            bannerView = new wijmo.collections.CollectionView(result, {
                pageSize: Number($('#bannerGridPageCount').val()),
                trackChanges: true,
            });
            bannerGridPager.cv = bannerView;
            bannerGrid.itemsSource = bannerView;
        }
        refreshPaging(bannerGrid.collectionView.totalItemCount, 1, bannerGrid, 'bannerGrid');
    }

    function exportExcel(){
        var gridView = bannerGrid.collectionView;
        var oldPgSize = gridView.pageSize;
        var oldPgIndex = gridView.pageIndex;

        //전체 데이터를 엑셀다운받기 위해서는 페이징 제거 > 엑셀 다운 > 페이징 재적용 하여야 함.
        bannerGrid.beginUpdate();
        bannerView.pageSize = 0;

        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(bannerGrid, {includeCellStyles: true, includeColumnHeaders: true}, 'BannerList.xlsx',
            saved => {
                gridView.pageSize = oldPgSize;
                gridView.moveToPage(oldPgIndex);
                bannerGrid.endUpdate();
            }, null
        );
    }

    //팝업 오픈
    function showPop(pop){
        if(pop == "new_banner"){

            newBannerForm.bannerTitle.value = "";
            newBannerForm.bannerLink.value = "";
            newBannerForm.bannerNotiIdx.value = "";
            newBannerForm.image.value = "";
            newBannerForm.new_banner_ctg.value = "공지사항이동";
            newBannerForm.new_banner_type.value = 'web';
            $('#new_banner_noti_tb').hide();
            $('#new_banner_link_tb').hide();
            $('#new_banner_img').empty();

        }else if(pop == "modify_banner"){

            $('#modify_exist_img').empty();
            $('#modify_banner_img').empty();
            var imgPath = bannerGrid.collectionView.currentItem["bannerImg"];
            // if(imgPath != null){
            //     var img = '<img class="banner_img" id="banner_exist_img" src="'+imgPath+'"art="이미지">';
            //     $('#modify_exist_img').show();
            //     $('#modify_exist_img').append(img)
            //     $('#modify_exist_img').append('<button type="button" class="img_button custom_button" onclick="deleteImage(`modify_banner`)" style="background:#fff; position: absolute;">x</button>');
            //     $('#modify_banner_img').hide();
            // }else {
            //     $('#modify_banner_img').show();
            //     $('#modify_exist_img').hide();
            //     $('#modify_exist_img').empty();
            // }

            updateBannerForm.index.value = bannerGrid.collectionView.currentItem["index"];
            updateBannerForm.modify_banner_active.checked = (bannerGrid.collectionView.currentItem["activeYn"] == 'Y' ? true : false );
            updateBannerForm.bannerTitle.value = bannerGrid.collectionView.currentItem["bannerTitle"];
            updateBannerForm.modify_banner_ctg.value = bannerGrid.collectionView.currentItem["bannerCtgNm"];
            updateBannerForm.modify_banner_type.value = bannerGrid.collectionView.currentItem["bannerType"];
            updateBannerForm.bannerLink.value = bannerGrid.collectionView.currentItem["bannerLink"];
            updateBannerForm.bannerNotiIdx.value = bannerGrid.collectionView.currentItem["bannerNotiIdx"];
            updateBannerForm.image.value = "";

            if(bannerGrid.collectionView.currentItem["bannerCtgNm"] == '인앱링크' || bannerGrid.collectionView.currentItem["bannerCtgNm"] == '아웃링크'){
                $('#modify_banner_noti_tb').hide();
                $('#modify_banner_link_tb').show();
            }else if(bannerGrid.collectionView.currentItem["bannerCtgNm"] == '공지사항이동'){
                $('#modify_banner_noti_tb').hide();
                $('#modify_banner_link_tb').hide();
            }else if(bannerGrid.collectionView.currentItem["bannerCtgNm"] == '공지세부페이지이동'){
                $('#modify_banner_noti_tb').show();
                $('#modify_banner_link_tb').hide();
            }

            $('#modify_banner_ctg').attr('disabled', true);
        } else if(pop == "image"){
            var imgPath = bannerGrid.collectionView.currentItem["bannerImg"];
            var img = '<img class="banner_img" src="'+imgPath+'"art="이미지">';
            $('#bannerImg')
                .append(img)
        }


        $('#'+pop).addClass('is_on');
    }

    //팝업 종료
    function closePop(){
        $('.popup').removeClass('is_on');
        $('#bannerImg').empty();
    }

    function bannerChange(e) {
        if(e.value == '아웃링크' || e.value == '인앱링크'){
            $('#new_banner_noti_tb').hide();
            $('#new_banner_link_tb').show();
            newBannerForm.bannerNotiIdx.value = "";
        }else if(e.value == '공지사항이동'){
            $('#new_banner_noti_tb').hide();
            $('#new_banner_link_tb').hide();
            newBannerForm.bannerNotiIdx.value = "";
            newBannerForm.bannerLink.value = "";
        }else if(e.value == '공지세부페이지이동'){
            $('#new_banner_noti_tb').show();
            $('#new_banner_link_tb').hide();
            newBannerForm.bannerLink.value = "";
        }
    }

    function uploadImgPreview(mode) {
        if(mode == "new_banner"){
            $('#new_banner_image').empty();
            $('#new_banner_img').empty();

            var fileList = document.getElementById("new_banner_image").files;
            var reg = /(.*?)\/(jpg|jpeg|png)$/;
            console.log(fileList);
            if(!fileList[0].type.match(reg)){
                alert("확장자는 이미지 확장자만 가능합니다");
                newBannerForm.image.value = "";
                $('#new_banner_img').empty();
                return false;
            }
            if (fileList) {
                [].forEach.call(fileList, readAndPreview);
            }

            function readAndPreview(fileList) {
                if (/\.(jpe?g|png|gif)$/i.test(fileList.name)) {
                    var reader = new FileReader();

                    reader.addEventListener("load", function () {
                        var image = new Image();

                        image.style = "width:300px; height:300px;";
                        image.title = fileList.name;
                        image.src = this.result;
                        image.className = "modiImg";

                        document.getElementById("new_banner_img").appendChild(image);
                        $("#new_banner_img").append('<button type="button" class="img_button custom_button" onclick="deleteImage(`new_banner`)" style="background:#fff; position: absolute; top: 3px; right: 3px;">x</button>');

                    }, false);

                    if (fileList) {
                        reader.readAsDataURL(fileList);
                    }
                }
            }
        }else if(mode == "modify_banner"){
            $('#modify_banner_image').empty();
            $('#modify_banner_img').empty();

            var fileList = document.getElementById("modify_banner_image").files;
            var reg = /(.*?)\/(jpg|jpeg|png)$/;
            console.log(fileList);
            if(!fileList[0].type.match(reg)){
                alert("확장자는 이미지 확장자만 가능합니다");
                updateBannerForm.image.value = "";
                $('#modify_banner_img').empty();
                return false;
            }
            if (fileList) {
                [].forEach.call(fileList, readAndPreview);
            }

            function readAndPreview(fileList) {
                if (/\.(jpe?g|png|gif)$/i.test(fileList.name)) {
                    var reader = new FileReader();

                    reader.addEventListener("load", function () {
                        var image = new Image();

                        image.style = "width:300px; height:300px;";
                        image.title = fileList.name;
                        image.src = this.result;
                        image.className = "modiImg";
                        image.id = "modiImg";

                        document.getElementById("modify_banner_img").appendChild(image);
                        $("#modify_banner_img").append('<button type="button" class="img_button custom_button" onclick="deleteImage(`modify_exist_banner`)" style="background:#fff; position: absolute; top: 3px; right: 3px;">x</button>');
                        $('#modify_exist_img').hide();
                        $('#modify_banner_img').show();

                    }, false);

                    if (fileList) {
                        reader.readAsDataURL(fileList);
                    }
                }
            }
        }
    }

    function deleteImage(mode){
        if(mode == "new_banner") {
            newBannerForm.image.value = "";
            $('#new_banner_img').empty();
        }else if(mode == "modify_banner") {
            if(confirm("기존 사진을 삭제하시겠습니까?")){
                $('#modify_exist_img').empty();
            }
        }else if(mode == "modify_exist_banner") {
            updateBannerForm.image.value = "";
            $('#modify_banner_img').empty();
            var imgPath = bannerGrid.collectionView.currentItem["bannerImg"];
            if(imgPath != null){
                var img = '<img class="banner_img" id="banner_exist_img" src="'+imgPath+'"art="이미지">';
                $('#modify_exist_img').append(img)
                $('#modify_exist_img').append('<button type="button" class="img_button custom_button" onclick="deleteImage(`modify_banner`)" style="background:#fff; position: absolute; top: 3px; right: 3px;">x</button>');
                $('#modify_banner_img').hide();
                $('#modify_exist_img').show();
            }
        }
    }

    function saveNewBanner() {
        if(newBannerForm.bannerTitle.value == ""){
            alert("제목을 입력해주세요.");
            newBannerForm.bannerTitle.focus();
            return false;
        }else if (newBannerForm.image.value == ""){
            alert("배너이미지를 등록해주세요.");
            newBannerForm.image.focus();
            return false;
        }else if (newBannerForm.new_banner_ctg.value == "공지세부페이지이동"){
            if (newBannerForm.bannerNotiIdx.value == ""){
                alert("공지번호를 입력해주세요.");
                newBannerForm.bannerNotiIdx.focus();
                return false;
            }else if (!$.isNumeric(newBannerForm.bannerNotiIdx.value)){
                alert("공지번호는 숫자를 입력해주세요.");
                newBannerForm.bannerNotiIdx.focus();
                return false;
            }
        }else if (newBannerForm.new_banner_ctg.value == "아웃링크"){
            if (newBannerForm.bannerLink.value == ""){
                alert("링크를 입력해주세요.");
                newBannerForm.bannerLink.focus();
                return false;
            }
        }

        newBannerForm.bannerCtg.value = newBannerForm.new_banner_ctg.value;
        newBannerForm.bannerType.value = newBannerForm.new_banner_type.value;

        var form = new FormData(newBannerForm);

        $.ajax({
            url : "/cms/banner",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'POST',
            cache : false,
            contentType : false,
            processData: false,
            enctype : 'multipart/form-data',
            data : form,
            success : function(data) {
                alert('저장되었습니다.');
                closePop();
                getBannerList();
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }

    function updateBannerGrid(){
        var editItem = bannerView.itemsEdited;
        var rows = [];

        var item = bannerView._src.filter(v => v.sort != null && v.sort != 'null' && v.sort != "").map(v => parseInt(v.sort));
        const set = new Set(item);

        if (editItem.length ==0) {
            alert("수정된 행이 없습니다.");
            return false;
        }

        if(set.size != item.length){
            alert("정렬 중복값은 입력할 수 없습니다.");
            return false;
        }

        if (!confirm("저장하시겠습니까?")) return false;

        editItem.forEach((obj) => {
            obj.notice_idx = obj.notice_idx;
            rows.push(obj);
        })

        axios.put("/cms/updateGrid", rows).then((res) => {
            console.log(res);
            if (res.status == 200) {
                alert("변경사항을 저장했습니다.");
                getBannerList();
            } else {
                alert("오류가 발생했습니다. 다시 시도해 주세요.");
            }
        })
    }

    function updateBanner(){
        if(updateBannerForm.bannerTitle.value == ""){
            alert("제목을 입력해주세요.");
            updateBannerForm.bannerTitle.focus();
            return false;
        }else if (updateBannerForm.modify_banner_ctg.value == "공지세부페이지이동"){
            if (updateBannerForm.bannerNotiIdx.value == ""){
                alert("공지번호를 입력해주세요.");
                updateBannerForm.bannerNotiIdx.focus();
                return false;
            }else if (!$.isNumeric(updateBannerForm.bannerNotiIdx.value)){
                alert("공지번호는 숫자를 입력해주세요.");
                updateBannerForm.bannerNotiIdx.focus();
                return false;
            }
        }else if (updateBannerForm.modify_banner_ctg.value == "아웃링크"){
            if (updateBannerForm.bannerLink.value == ""){
                alert("링크를 입력해주세요.");
                updateBannerForm.bannerLink.focus();
                return false;
            }
        }

        updateBannerForm.activeYn.value = (updateBannerForm.modify_banner_active.checked ? 'Y' : 'N' );
        updateBannerForm.bannerCtg.value = updateBannerForm.modify_banner_ctg.value;
        updateBannerForm.bannerType.value = updateBannerForm.modify_banner_type.value;

        var form = new FormData(updateBannerForm);

        $.ajax({
            url : "/cms/banner",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'PUT',
            cache : false,
            contentType : false,
            processData: false,
            enctype : 'multipart/form-data',
            data : form,
            success : function(data) {
                alert('수정되었습니다.');
                closePop();
                getBannerList();
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }

    function deleteBanner(){
        if(confirm("삭제하시겠습니까?")){
            var imgUrl = bannerGrid.collectionView.currentItem["bannerImg"];

            var params = {
                index : updateBannerForm.index.value
                ,filePath : "/banner"
                ,fileName : imgUrl.substring(33)
            }

            $.ajax({
                url : '/cms/banner',
                async : false, // 비동기모드 : true, 동기식모드 : false
                type : 'DELETE',
                data : params,
                cache : false,
                dataType : null,
                success : function(data) {
                    alert('정상적으로 삭제되었습니다.');
                    closePop();
                    getBannerList();
                },
                error : function(request,status,error) {
                    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                }
            });
        }
    }

    function getBannerList(){
        var param = {
            // con 	: $('#con').val()
            // , inq 	: $('#inq').val()
            // , searchDtTo : wijmo.Globalize.format(edat.value,'yyyy-MM-dd')
            // , searchDtFr : wijmo.Globalize.format(sdat.value,'yyyy-MM-dd')
            banner_type : text
        }

        $.ajax({
            type : 'GET',
            url : '/cms/getBannerList',
            dataType : null,
            data : param,
            success : function(result) {
                console.log("getBannerList success");
                loadGridBannerList('search', result);
            },
            error: function(request, status, error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

            }
        });
    }

    $(document).ready(function() {
        pageOnLoad();
    });





    // 수정수정
    
</script>
