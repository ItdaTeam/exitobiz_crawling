<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/header.jsp" %>
</head>
<body>
  <div class="modal_bg">

  </div>
  <div class="loading_bar_wrap">
      <div class="loader"></div>
  </div>
<div class="main_wrap">
    <%@ include file="../include/nav.jsp" %>
    <div class="main_container">
        <section class="main_section">
            <h2 class="main_title"><span class="material-icons-round"> done_outline</span> 지원사업 관리</h2>
            <span id="PUSH" class="push push1">지원사업 PUSH발송<i class="fa-solid fa-share-from-square"></i></span>
            <span id="PUSH" class="push push2">공지 PUSH발송<i class="fa-solid fa-share-from-square"></i></span>       
            <div class="main_summary">
                <dl>
                    <dt>전체 수</dt>
                    <dd id="totalCnt"><%=request.getAttribute("totalSupportCnt")%>개</dd>
                </dl>
                <dl>
                    <dt>지원사업(Y)</dt>
                    <dd id="activeYCnt"><%=request.getAttribute("activeYCnt")%>개</dd>
                </dl>
                <dl>
                    <dt>지원사업(N)</dt>
                    <dd id="activeNCnt"><%=request.getAttribute("activeNCnt")%>개</dd>
                </dl>
                <dl>
                    <dt>전체 조회수</dt>
                    <dd id="viewCnt"><%=request.getAttribute("allViewCnt")%>개</dd>
                </dl>
                <dl>
                    <dt>조회수 TOP 지역</dt>
                    <dd><%=request.getAttribute("viewCntLoc")%>
                    </dd>
                </dl>
                <dl>
                    <dt>공고개수 TOP 지역</dt>
                    <dd><%=request.getAttribute("supportCntLoc")%>
                    </dd>
                </dl>
            </div>
            <div class="main_utility">
                <label for="fromDate">조회일</label>
                <input type="date" id="fromDate" name="from">
                -
                <input type="date" id="toDate" name="to">
                <div class="btn_wrap support_btn_wrap">
                    <input type="file" class="form-control" style="display:none" id="importFile"
                           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>
                    <button class="btn stroke" onclick="downTemplateExito()"><span class="material-icons">attach_file</span>엑시토 엑셀
                        템플릿
                    </button>
                    <button class="btn stroke" onclick="downTemplateCorp()"><span class="material-icons">attach_file</span>고객용 엑셀
                        템플릿
                    </button>
                    <button class="btn stroke uploadExcel" style="display:none" onClick="findFile();"><span class="material-icons"> upload</span>엑셀 업로드
                    </button>
                    <button class="btn stroke" onclick="exportExcel()"><span class="material-icons-outlined">file_download</span>엑셀
                        다운로드
                    </button>
                    <button type="button" class="btn stroke" onClick="saveGrid();" id="excelBtn"><span
                            class="material-icons-outlined" style="width:28px;">save_grid</span>엑셀저장
                    </button>
                </div>
            </div>
            <div class="tabMenu">
                <ul id="tabList">
                    <li class="TabM1">크롤링 리스트</li>
                    <li class="TabM2">엑시토 리스트</li>
                    <li class="TabM3">고객용 리스트</li>
                </ul>
            </div>
            <div class="main_content">
                <!-- 필터 영역 main_filter-->
                <div class="main_filter">
                    <form action="#" onsubmit="return false;">
                        <label for="con">검색조건</label>
                        <select name="con" id="con">
                            <option value="all" selected="selected">전체</option>
                            <option value="target">기관</option>
                            <option value="title">제목</option>
                        </select>
                        <label for="inq" onkeyup="enterkey()">조회</label>
                        <input id="inq" name="inq" type="text" placeholder=",로 다중검색 가능" onkeyup="enterkey()">
                        <button type="button" class="main_filter_btn" onclick="getList()"><i>조회</i></button>
                    </form>
                </div>
                <!-- 보드 영역 main_dashboard-->
                <div class="main_dashboard">
                    <div class="sub_cont">
                        <div class="btn_wrap">
                            <select name="viewType" id="viewType" onchange="getList()">
                                <option value="opt1">전체</option>
                                <option value="opt2">비활성화</option>
                                <option value="opt3">활성화</option>
                                <option value="opt4">마감임박순</option>
                                <option value="opt5">실시간인기순</option>
                                <option value="opt6">금액높은순</option>
                            </select>
                            <select name="viewNum" id="viewNum" onchange="getList()">
                                <option value="100">100개씩</option>
                                <option value="50">50개씩</option>
                                <option value="30">30개씩</option>
                            </select>
                            <button type="button" class="btn stroke"
                                    onClick="getGrid();"><span
                                    class="material-icons-outlined">view_list</span>칼럼위치저장
                            </button>
                            <button type="button" class="btn stroke"
                                    onClick="resetGrid();"><span
                                    class="material-icons-outlined">restart_alt</span>칼럼초기화
                            </button>
<%--                            <button type="button" class="btn stroke addGrid" style="display:none;"--%>
<%--                                    onClick="addGrid();"><span--%>
<%--                                    class="material-icons-outlined">add_circle</span>그리드추가--%>
<%--                            </button>--%>
                            <button type="button" class="btn stroke delGrid"  style="display:none;"
                                    onClick="delGrid();"><span
                                    class="material-icons-outlined">do_not_disturb_on</span>그리드삭제
                            </button>
                            <button type="button" class="btn stroke editGrid" onClick="editGrid();" style="display:none;" id="gridBtn"><span
                                    class="material-icons-outlined" style="width:28px;">save_grid</span>그리드저장
                            </button>

                        </div>
                    </div>
                    <div class="grid_wrap" id="supportDiv" style="position:relative;">
                        <div id="supportGrid"></div>
                        <div id="supportGridPager" class="pager"></div>
                    </div>
                    <div class="grid_wrap" id="supportDiv2" style="position:relative;">
                        <div id="corpGrid"></div>
                        <div id="corpGridPager" class="pager"></div>
                    </div>

                    <div class="grid_wrap" id="excelDiv" style="position:relative;">
                        <div id="excelGrid"></div>
                    </div>
                    <div class="grid_wrap" id="corpExcelDiv" style="position:relative;">
                        <div id="corpExcelGrid"></div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <div class="exitoModal">
        <section class="exitoModal1">
            <h1>지원사업 푸쉬 발송<span onclick="closeModal()">X</span></h1>
            <p><span class="dot">*</span>필수항목</p>
            <!-- <hr class="boldHR"> -->
        </section>

        <form name="push_form" id="push_form" onsubmit="return false;">
        <section class="exitoModal2">
            <div class="exitoModal1Left">
                <p><span class="dot">*</span>대상선택</p>
            </div>
            <div class="exitoModal1Right">
                <p>
                <li>
                    <input type="checkbox" id="point1" onclick="selectAll(this);">
                    <label for="point1">대상전체선택</label>
                </li>
                </p>
                <ul class="areaList">
                    <li>
                        <input type="checkbox" id="point2" name="location" value="C02">
                        <label for="point2">서울</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point3" name="location" value="C031">
                        <label for="point3">경기</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point4" name="location" value="C032">
                        <label for="point4">인천</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point5" name="location" value="C033">
                        <label for="point5">강원</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point6" name="location" value="C041">
                        <label for="point6">충남</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point7" name="location" value="C042">
                        <label for="point7">대전</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point8" name="location" value="C043">
                        <label for="point8">충북</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point9" name="location" value="C044">
                        <label for="point9">세종</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point10" name="location" value="C051">
                        <label for="point10">부산</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point11" name="location" value="C052">
                        <label for="point11">울산</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point12" name="location" value="C053">
                        <label for="point12">대구</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point13" name="location" value="C054">
                        <label for="point13">경북</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point14" name="location" value="C055">
                        <label for="point14">경남</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point15" name="location" value="C061">
                        <label for="point15">전남</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point16" name="location" value="C063">
                        <label for="point16">전북</label>
                    </li>
                    <li>
                        <input type="checkbox" id="point17" name="location" value="C062">
                        <label for="point17">광주</label>
                    </li>
                    <li>
                        <input type="checkbox" id="like1" name="location" value="C064">
                        <label for="like1">제주</label>
                    </li>
                </ul>
            </div>
        </section>
        <section class="exitoModal3">
            <label for="idx"><span class="dot">*</span>게시판 번호</label>
            <input type="text" name="idx" id="idx">
        </section>
        <section class="exitoModal4">
            <label for="title"><span class="dot">*</span>제목</label>
            <input type="text" name="titel" id="title">
        </section>
        <section class="exitoModal5">
            <label for="body"><span class="dot">*</span>본문</label>
            <textarea name="body" id="body" rows="8" cols="80"></textarea>
        </section>
        </form>

        <p id="send"><a href="#" onclick="sendPush()">발송</a></p>

    </div>

    <div class="NewExitoModal">
        <section class="NewExitoModal1">
            <h1>공지 푸쉬 알람 발송<span onclick="closeModal()">X</span></h1>
            <p><span class="dot">*</span>필수항목</p>
            <!-- <hr class="boldHR"> -->
        </section>

        <form name="push_form1" id="push_form1" onsubmit="return false;">
        <section class="NewExitoModal2">
            <div class="NewExitoModalleft">
                <p><span class="dot">*</span>대상선택</p>
            </div>
            <div class="NewExitoModalRight">
                <ul class="areaList NewAreaList">
                    <li>
                        <input type="radio" id="Newpoint2" name="category" value="noti">
                        <label for="Newpoint2">공지사항</label>
                    </li>
                    <li>
                        <input type="radio" id="Newpoint3" name="category" value="bann">
                        <label for="Newpoint3">배너</label>
                    </li>
                    <li>
                        <input type="radio" id="Newpoint4" name="category" value="cus1">
                        <label for="Newpoint4">커뮤니티</label>
                    </li>
                </ul>
            </div>
        </section>
        <section class="NewExitoModal3">
            <label for="idx"><span class="dot">*</span>게시판 번호</label>
            <input type="text" name="idx" id="idx">
        </section>
        <section class="NewExitoModal4">
            <label for="title"><span class="dot">*</span>제목</label>
            <input type="text" name="title" id="title">
        </section>
        <section class="NewExitoModal5">
            <label for="body"><span class="dot">*</span>본문</label>
            <textarea name="body" id="body" rows="8" cols="80"></textarea>
        </section>
        </form>

        <p id="send"><a href="javascript:void(0);" onclick="sendNoticePush()">발송</a></p>
    </div>
</div>
</body>
</html>
<script>
    //고객용 리스트는 컬럼이 달라 따로 구분
    var supportGrid;
    var supportView;
    var supportGridPager;
    var supportColumns;

    var excelGrid;
    var excelView;
    var excelSelector;
    var excelColumns;

    var corpGrid;
    var corpView;
    var corpGridPager;
    var corpColumns;
    var corpSelector;

    var corpExcelGrid;
    var corpExcelView;
    var corpExcelSelector;
    var corpExcelColumns;

    var text = 'crawling'; // 탭 텍스트

    $(document).ready(function () {
        //조회일 기본값 설정
        const month = new Date().getMonth();
        const day = new Date().getDate();


        $("#fromDate").val(new Date(new Date().setDate(day - 1)).toISOString().slice(0,10));
        $('#toDate').val( new Date().toISOString().slice(0,10));
        pageOnLoad();
        getList();



        //  지원사업관리 탭메뉴
        $(".TabM1").click(function(){
            $(".TabM1").css("background-color","#37f1aa").css("border","1px solid #37f1aa");
            $(".TabM2, .TabM3").css("background-color","#fff").css("border","1px solid #ddd");
            $("#supportDiv").css("display","block");
            $('.uploadExcel, .addGrid, .delGrid, .editGrid, #supportDiv2').css('display', 'none');

            $("#fromDate").val(new Date(new Date().setDate(day - 1)).toISOString().slice(0,10));
            text = 'crawling';

            getList();
        });

        $(".TabM2").click(function(){
            $(".TabM2").css("background-color","#37f1aa").css("border","1px solid #37f1aa");
            $(".TabM1, .TabM3").css("background-color","#fff").css("border","1px solid #ddd");
            $("#supportDiv2, .addGrid, .delGrid, .editGrid").css("display","none");
            $("#supportDiv").css("display","block");
            $('.uploadExcel').css('display', 'inline');

            $("#fromDate").val(new Date(new Date().setMonth(month - 1)).toISOString().slice(0,10));
            text = 'support';
            getList();
        });

        $(".TabM3").click(function(){
            $(".TabM3").css("background-color","#37f1aa").css("border","1px solid #37f1aa");
            $(".TabM1, .TabM2").css("background-color","#fff").css("border","1px solid #ddd");
            $("#supportDiv").css("display","none");
            $("#supportDiv2").css("display","block");
            $('.uploadExcel, .addGrid, .delGrid, .editGrid').css('display', 'inline');

            $("#fromDate").val(new Date(new Date().setMonth(month - 1)).toISOString().slice(0,10));
            text = 'corp';
            getList();
        });

    });

    function pageOnLoad() {
        loadGridSupportList('init');
        loadGridCorpList('init');
        document.getElementById("support").classList.add("active");
        $("#excelDiv").hide();
        $("#corpExcelDiv").hide();
        $("#importFile").on('change', function (params) {
            importExcel();
        });
    }



    function selectAll(selectAll)  {
        const checkboxes = document.getElementsByName("location");
        checkboxes.forEach((checkbox) => {
            checkbox.checked = selectAll.checked;
        })
    }

    function getList(){
        getAPIList(text);
    }


    $(".push1").click(function(){
        $('#push_form')[0].reset();

        $(".modal_bg").addClass("on");
        $(".exitoModal").addClass("on");

    });

    function closeModal(){
      $(".modal_bg").removeClass("on");
      $(".exitoModal").removeClass('on');
      $(".modal_bg").removeClass("on");
      $('.NewExitoModal').removeClass('on');
    }

    // 지원사업관리 push화면 보이기
    $(".push2").click(function(){
        $('#push_form1')[0].reset();

        $(".modal_bg").addClass("on");
        $(".NewExitoModal").addClass("on");

    });

    function sendPush(){
        const query = 'input[name="location"]:checked';
        const selectedEls = document.querySelectorAll(query);

        if(selectedEls.length == 0){
            alert("대상을 선택해주세요.")
            return false;
        }
        if(push_form.idx.value == ""){
            alert("게시판번호를 입력해주세요.")
            return false;
        }
        if(push_form.title.value == ""){
            alert("제목을 입력해주세요.")
            return false;
        }
        if(push_form.body.value == ""){
            alert("본문을 입력해주세요.")
            return false;
        }

        let result = '';
        selectedEls.forEach((el) => {
            result += el.value + ',';
        });


        axios.post("/push/sendSplit", null, {
            params : {
                userloc : result.slice(0,-1),
                title : encodeURIComponent(push_form.title.value),
                body : encodeURIComponent(push_form.body.value),
                idx : encodeURIComponent(push_form.idx.value),
                keyId : 'cont'
            }
        });

        alert("전송을 완료했습니다.");
        closeModal();

    }

    //공지Push발송
    function sendNoticePush(){
        const query = 'input[name="category"]:checked';
        const selectedEl = document.querySelector(query);

        if(selectedEl == null){
            alert("대상을 선택해주세요.")
            return false;
        }
        if(push_form1.idx.value == ""){
            alert("게시판번호를 입력해주세요.")
            return false;
        }
        if(push_form1.title.value == ""){
            alert("제목을 입력해주세요.")
            return false;
        }
        if(push_form1.body.value == ""){
            alert("본문을 입력해주세요.")
            return false;
        }
        axios.post("/push/sendNotice", null, {
            params : {
                title : encodeURIComponent(push_form1.title.value),
                body : encodeURIComponent(push_form1.body.value),
                idx : push_form1.idx.value,
                keyId : push_form1.category.value
            }
        });

        alert("전송을 완료했습니다.");
        closeModal();
    }

    function enterkey() {
        if (window.event.keyCode == 13) {
            getList();
        }
    }

    //그리드 초기 셋팅
    function loadGridSupportList(type, result) {

        if (type == "init") {
            supportView = new wijmo.collections.CollectionView(result, {
                pageSize: 100,
                trackChanges: true
            });

            supportGridPager = new wijmo.input.CollectionViewNavigator('#supportGridPager', {
                byPage: true,
                headerFormat: '{currentPage:n0} / {pageCount:n0}',
                cv: supportView
            });

            var onoffYnMap = "N,Y".split(",");	//온/오프라인 콤보박스

            supportColumns = [
                {binding: 'siIdx', header: 'INDEX', isReadOnly: true, width: 100, align: "center"},
                {binding: 'targetName', header: '기관', isReadOnly: true, width: 100, align: "center"},
                {binding: 'targetCatName', header: '분류', isReadOnly: true, width: 100, align: "center"},
                {binding: 'targetCatCd', header: '분류코드', isReadOnly: true, width: 200, align: "center"},
                {binding: 'targetCostValue', header: '금액', isReadOnly: true, width: 120, align: "center"},
                {binding: 'locCode', header: '지역코드', isReadOnly: true, width: 100, align: "center"},
                {binding: 'siTitle', header: '제목', isReadOnly: true, width: 100, align: "center"},
                {binding: 'mobileUrl', header: '모바일주소', isReadOnly: true, width: 200, align: "center", maxWidth: 200},
                {binding: 'pcUrl', header: '피시주소', isReadOnly: true, width: 100, align: "center"},
                {
                    binding: 'siEndDt', header: '마감일', isReadOnly: false, width: 100, align: "center",
                    editor: new wijmo.input.InputDate(document.createElement('div'), {
                        isRequired: false,
                        value: null
                    })
                },
                {binding: 'siCretDt', header: '생성일', isReadOnly: true, width: 200, align: "center"},
                {
                    binding: 'siActiveYn',
                    header: '활성화여부',
                    isReadOnly: true,
                    width: 200,
                    align: "center",
                    dataMap: onoffYnMap
                },
                {binding: 'businessCtg', header: '사업분야', isReadOnly: true, width: 100, align: "center"},
                {binding: 'techCtg', header: '기술분야', isReadOnly: true, width: 100, align: "center"},
                {binding: 'businessType', header: '사업자형태', isReadOnly: true, width: 200, align: "center"},
                {binding: 'companyType', header: '기업형태', isReadOnly: true, width: 100, align: "center"},
                {binding: 'startPeriod', header: '창업기간', isReadOnly: true, width: 100, align: "center"},
                {binding: 'viewCnt', header: '조회수', isReadOnly: true, width: 100, align: "center"},
                {binding: 'shareCnt', header: '공유수', isReadOnly: true, width: 100, align: "center"},
                {binding: 'saveCnt', header: '저장수', isReadOnly: true, width: 100, align: "center"}
            ];

            supportGrid = new wijmo.grid.FlexGrid('#supportGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: supportColumns,
                itemsSource: supportView,
                //컬럼 길이 자동정렬
                loadedRows: function (s, e) {
                    s.autoSizeColumns();
                    for (var i = 0; i < s.rows.length; i++) {
                        var row = s.rows[i];
                        var item = row.dataItem;
                        if (item.activeFlag == 'N') {
                            row.cssClass = 'change_dup';
                        }
                        if (item.expectCloseFlag == 'Y') {
                            row.cssClass = 'change_close';
                        }
                    }
                }
            });

            supportGrid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                    cell.textContent = (r + 1).toString();
                }
            };
            _setUserGridLayout(text + 'Layout', supportGrid, supportColumns);

            excelColumns = [
                {binding: 'siIdx', header: 'Index', isReadOnly: true, width: 100, align: "center", dataType: "Number"},
                {binding: 'targetName', header: '기관', isReadOnly: true, width: 100, align: "center"},
                {binding: 'targetCatName', header: '분류', isReadOnly: true, width: 100, align: "center"},
                {binding: 'targetCatCd', header: '분류코드', isReadOnly: true, width: 200, align: "center"},
                {binding: 'targetCostValue', header: '금액', isReadOnly: true, width: 120, align: "center"},
                {binding: 'locCode', header: '지역코드', isReadOnly: true, width: 100, align: "center"},
                {binding: 'siTitle', header: '제목', isReadOnly: true, width: 100, align: "center"},
                {binding: 'mobileUrl', header: '모바일주소', isReadOnly: true, width: 200, align: "center"},
                {binding: 'pcUrl', header: '피시주소', isReadOnly: true, width: 100, align: "center"},
                {binding: 'siEndDt', header: '마감일', isReadOnly: true, width: 100, align: "center"},
                {binding: 'siCretDt', header: '생성일', isReadOnly: true, width: 200, align: "center"},
                {
                    binding: 'siActiveYn',
                    header: '활성화여부',
                    isReadOnly: true,
                    width: 200,
                    align: "center",
                    dataMap: onoffYnMap
                },
                {binding: 'businessCtg', header: '사업분야', isReadOnly: true, width: 100, align: "center"},
                {binding: 'techCtg', header: '기술분야', isReadOnly: true, width: 100, align: "center"},
                {binding: 'businessType', header: '사업자형태', isReadOnly: true, width: 200, align: "center"},
                {binding: 'companyType', header: '기업형태', isReadOnly: true, width: 100, align: "center"},
                {binding: 'startPeriod', header: '창업기간', isReadOnly: true, width: 100, align: "center"}
            ];
            excelGrid = new wijmo.grid.FlexGrid('#excelGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: supportColumns,
                itemsSource: excelView,
                //컬럼 길이 자동정렬
                loadedRows: function (s, e) {
                    s.autoSizeColumns();
                    for (var i = 0; i < s.rows.length; i++) {
                        var row = s.rows[i];
                        var item = row.dataItem;
                        if (item.activeFlag == 'N') {
                            row.cssClass = 'change_dup';
                        }
                        if (item.expectCloseFlag == 'Y') {
                            row.cssClass = 'change_close';
                        }
                    }
                }
            });

            //행번호 표시하기
            excelGrid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                    cell.textContent = (r + 1).toString();
                }
            };

        } else {
            supportView = new wijmo.collections.CollectionView(result, {
                pageSize: Number($('#viewNum').val()),
                trackChanges: true
            });

            supportGridPager.cv = supportView;
            supportGrid.itemsSource = supportView;

            _setUserGridLayout(text+'Layout', supportGrid, supportColumns);
        }

        refreshPaging(supportGrid.collectionView.totalItemCount, 1, supportGrid, 'supportGrid');  // 페이징 초기 셋팅

    }

    //그리드 초기 셋팅
    function loadGridCorpList(type, result) {
        if (type == "init") {
            corpView = new wijmo.collections.CollectionView(result, {
                pageSize: 100,
                trackChanges: true
            });

            corpGridPager = new wijmo.input.CollectionViewNavigator('#corpGridPager', {
                byPage: true,
                headerFormat: '{currentPage:n0} / {pageCount:n0}',
                cv: corpView
            });

            var onoffYnMap = "N,Y".split(",");	//온/오프라인 콤보박스

            corpColumns = [
                {binding: 'check', header:'선택',isReadOnly: true, width: 50, align: "center"},
                {binding: 'siIdx', header: 'Index', isReadOnly: true, width: 100, align: "center", dataType: "Number"},
                {binding: 'targetName', header: '기관',  width: 100, align: "center"},
                {binding: 'targetCatName', header: '분류', width: 100, align: "center"},
                {binding: 'targetCatCd', header: '분류코드', width: 200, align: "center"},
                {binding: 'targetCostValue', header: '금액', width: 120, align: "center"},
                {binding: 'locCode', header: '지역코드', width: 100, align: "center"},
                {binding: 'siTitle', header: '제목', width: 100, align: "center"},
                {binding: 'mobileUrl', header: '모바일주소', width: 200, align: "center"},
                {binding: 'pcUrl', header: '피시주소', width: 100, align: "center"},
                {binding: 'siEndDt', header: '마감일', width: 100, align: "center"},
                {binding: 'siCretDt', header: '생성일', width: 200, align: "center"},
                {
                    binding: 'siActiveYn',
                    header: '활성화여부',
                    width: 200,
                    align: "center",
                    dataMap: onoffYnMap
                },
                {binding: 'businessCtg', header: '사업분야', width: 100, align: "center"},
                {binding: 'techCtg', header: '기술분야', width: 100, align: "center"},
                {binding: 'businessType', header: '사업자형태', width: 200, align: "center"},
                {binding: 'companyType', header: '기업형태', width: 100, align: "center"},
                {binding: 'startPeriod', header: '창업기간', width: 100, align: "center"},
                {binding: 'corpCd', header: '고객사코드', width: 100, align: "center"},
                {binding: 'detail', header: '세부내용', isReadOnly: true, width: 100, align: "center"}
            ];
            corpGrid = new wijmo.grid.FlexGrid('#corpGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: corpColumns,
                itemsSource: corpView,
                //컬럼 길이 자동정렬
                loadedRows: function (s, e) {
                    s.autoSizeColumns();
                    for (var i = 0; i < s.rows.length; i++) {
                        var row = s.rows[i];
                        var item = row.dataItem;
                        if (item.activeFlag == 'N') {
                            row.cssClass = 'change_dup';
                        }
                        if (item.expectCloseFlag == 'Y') {
                            row.cssClass = 'change_close';
                        }
                    }
                },
                itemFormatter : function (panel, r, c, cell) {
                    if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                        cell.textContent = (r + 1).toString();
                    }
                }
            });
            /* 체크박스 생성 */
            corpSelector = new wijmo.grid.selector.Selector(corpGrid);
            corpSelector.column = corpGrid.columns[0];

            _setUserGridLayout('corpLayout', corpGrid, corpColumns, corpSelector);

            corpExcelColumns = [
                {binding: 'siIdx', header: 'Index', isReadOnly: true, width: 100, align: "center", dataType: "Number"},
                {binding: 'targetName', header: '기관', isReadOnly: true, width: 100, align: "center"},
                {binding: 'targetCatName', header: '분류', isReadOnly: true, width: 100, align: "center"},
                {binding: 'targetCatCd', header: '분류코드', isReadOnly: true, width: 200, align: "center"},
                {binding: 'targetCostValue', header: '금액', isReadOnly: true, width: 120, align: "center"},
                {binding: 'locCode', header: '지역코드', isReadOnly: true, width: 100, align: "center"},
                {binding: 'siTitle', header: '제목', isReadOnly: true, width: 100, align: "center"},
                {binding: 'mobileUrl', header: '모바일주소', isReadOnly: true, width: 200, align: "center"},
                {binding: 'pcUrl', header: '피시주소', isReadOnly: true, width: 100, align: "center"},
                {binding: 'siEndDt', header: '마감일', isReadOnly: true, width: 100, align: "center"},
                {binding: 'siCretDt', header: '생성일', isReadOnly: true, width: 200, align: "center"},
                {
                    binding: 'siActiveYn',
                    header: '활성화여부',
                    isReadOnly: true,
                    width: 200,
                    align: "center",
                    dataMap: onoffYnMap
                },
                {binding: 'businessCtg', header: '사업분야', isReadOnly: true, width: 100, align: "center"},
                {binding: 'techCtg', header: '기술분야', isReadOnly: true, width: 100, align: "center"},
                {binding: 'businessType', header: '사업자형태', isReadOnly: true, width: 200, align: "center"},
                {binding: 'companyType', header: '기업형태', isReadOnly: true, width: 100, align: "center"},
                {binding: 'startPeriod', header: '창업기간', isReadOnly: true, width: 100, align: "center"},
                {binding: 'corpCd', header: '고객사코드', isReadOnly: true, width: 100, align: "center"}
            ];
            corpExcelGrid = new wijmo.grid.FlexGrid('#corpExcelGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: corpColumns,
                itemsSource: corpExcelView,
                //컬럼 길이 자동정렬
                loadedRows: function (s, e) {
                    s.autoSizeColumns();
                    for (var i = 0; i < s.rows.length; i++) {
                        var row = s.rows[i];
                        var item = row.dataItem;
                        if (item.activeFlag == 'N') {
                            row.cssClass = 'change_dup';
                        }
                        if (item.expectCloseFlag == 'Y') {
                            row.cssClass = 'change_close';
                        }
                    }
                }
            });

            //행번호 표시하기
            corpExcelGrid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                    cell.textContent = (r + 1).toString();
                }
            };

        } else {
            corpView = new wijmo.collections.CollectionView(result, {
                pageSize: Number($('#viewNum').val()),
                trackChanges: true
            });

            console.log(corpView);

            corpGridPager.cv = corpView;
            corpGrid.itemsSource = corpView;

            _setUserGridLayout('corpLayout', corpGrid, corpColumns);

        }

        refreshPaging(corpGrid.collectionView.totalItemCount, 1, corpGrid, 'corpGrid');  // 페이징 초기 셋팅

    }

    function getAPIList(type) {

        //db데이터에는 시간까지 출력되기 때문에 날짜 데이터만 가져오면 범위에 포함되지 않기 때문에 하루 더해서 범위값 설정한다.
        let to = new Date($('#toDate').val());
        to = new Date(to.setDate(to.getDate() + 1));
        to = to.getFullYear() + "-" + (to.getMonth()+1) + "-" + to.getDate();
        var param = {
            con: $('#con').val()
            , inq: $('#inq').val()
            , viewType: $('#viewType').val()
            ,from : $('#fromDate').val()
            , to : to
        };

        let url;
        type == 'crawling' ? url = '/cms/crawling/allCrawling' : type == 'support' ? url = '/cms/allSupport' : url = '/cms/corp/allSupport';

        $.ajax({
            type: 'GET',
            url: url,
            dataType: null,
            data: param,
            success: function (result) {
                if(type == 'corp') loadGridCorpList('search', result);
                else loadGridSupportList('search', result);

                document.getElementById('totalCnt').innerText = result.length + "개";
                document.getElementById('activeYCnt').innerText = result.filter(v => v.siActiveYn == 'Y').length + "개";
                document.getElementById('activeNCnt').innerText = result.filter(v => v.siActiveYn == 'N').length + "개";
                document.getElementById('viewCnt').innerText = result.map(v => v.viewCnt).reduce((acc, cur) => acc + cur, 0) + "개";

            },
            error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);

            }
        });
    }



    //엑시토 템플릿 다운로드
    function downTemplateExito() {
        window.location.assign("<%=request.getContextPath()%>" + "/template/지원사업관리양식.xlsx");
    }

    //고객용 템플릿 다운로드
    function downTemplateCorp() {
        window.location.assign("<%=request.getContextPath()%>" + "/template/사업공고관리양식.xlsx");
    }

    //엑셀 다운로드
    function exportExcel() {

        var gridView ;

        text == 'corp' ? gridView = corpGrid.collectionView : gridView = supportGrid.collectionView;

        var oldPgSize = gridView.pageSize;
        var oldPgIndex = gridView.pageIndex;

        //부분 데이터를 엑셀다운받기 위해서는 페이징 재설정 > 엑셀 다운 > 페이징 재적용 하여야 함.
        if(gridView._src.length > 50000){
            alert("조회일을 줄여주세요.");
            return false;
        }

        //로딩 시작
        loadingBarStart();

        // 로딩 이미지가 활성화 된 상태에서 실행하기 위해 지연시간을 둠
        setTimeout(function(){

            if(text =='corp'){
                corpGrid.beginUpdate();
                corpView.pageSize = gridView._src.length;

                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(corpGrid, {includeCellStyles: true, includeColumnHeaders: true}, '고객용지원사업리스트.xlsx',
                    saved => {
                        loadingBarEnd();
                        gridView.pageSize = oldPgSize;
                        gridView.moveToPage(oldPgIndex);
                        corpGrid.endUpdate();
                    }, null
                );
            }else{
                supportGrid.beginUpdate();
                supportView.pageSize = gridView._src.length;

                let listTxt = text == 'support' ? '엑시토지원사업리스트.xlsx' : '크롤링지원사업리스트.xlsx';

                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(supportGrid, {includeCellStyles: true, includeColumnHeaders: true}, listTxt,
                    saved => {
                        loadingBarEnd();
                        gridView.pageSize = oldPgSize;
                        gridView.moveToPage(oldPgIndex);
                        supportGrid.endUpdate();
                    }, null
                );
            }
        }, 100);
    }

    //업로드 파일 찾기
    function findFile() {
        $("#importFile").val("");
        document.all.importFile.click();
    }

    //엑셀 업로드
    function importExcel() {

        var inputEle = document.querySelector('#importFile');

        if(text == 'support'){

            $("#supportDiv").hide();
            $('#supportDiv2').hide();
            $('#corpExcelDiv').hide();
            $("#excelDiv").show();

            supportView = new wijmo.collections.CollectionView(null, {
                pageSize: 100
            });

            if (inputEle.files[0]) {
                wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(excelGrid, inputEle.files[0], {includeColumnHeaders: true}, (w) => {
                    // 데이터 바인딩할 함수 호출
                    bindImportedDataIntoModel(excelGrid);
                    excelGrid.columns.forEach(col => {
                        col.width = 160,
                            col.align = "center"
                    })
                });
            }
        }else{

            $("#supportDiv2").hide();
            $('#supportDiv').hide();
            $('#excelDiv').hide();
            $("#corpExcelDiv").show();

            corpView = new wijmo.collections.CollectionView(null, {
                pageSize: 100
            });

            if (inputEle.files[0]) {
                wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(corpExcelGrid, inputEle.files[0], {includeColumnHeaders: true}, (w) => {
                    // 데이터 바인딩할 함수 호출
                    bindImportedDataIntoModel(corpExcelGrid);
                    corpExcelGrid.columns.forEach(col => {
                        col.width = 160,
                            col.align = "center"
                    })
                });
            }
        }
    }

    const editGrid = async () => {
        //엑시토 리스트는 추후 기능 추가 예정
        const editItem = text == 'support' ? supportView.itemsEdited : corpView.itemsEdited;
        const addItem = text == 'support' ? supportView.itemsAdded : corpView.itemsAdded;

        let arr = [...editItem, ...addItem];

        console.log("arr >>>" , arr);

        if (arr.length < 1) {
            alert("수정 및 추가된 내역이 없습니다.");
            return false;
        }

        for(let i=0; i<arr.length; i++){
            if (arr[i].siActiveYn == null || wijmo.isEmpty(arr[i].siActiveYn) || arr[i].siActiveYn == "") {
                alert("활성화여부를 입력 해주세요.");
                return false;
            }

            if(arr[i].siActiveYn == 'Y'){
                if (arr[i].targetName == null || wijmo.isEmpty(arr[i].targetName) || arr[i].targetName == "") {
                    alert("기관을 입력 해주세요.");
                    return false;
                }

                if (arr[i].targetCatName == null || arr[i].targetCatName == "") {
                    alert("분류를 입력 해주세요.");
                    return false;
                }

                if (arr[i].targetCatCd == null || wijmo.isEmpty(arr[i].targetCatCd) || arr[i].targetCatCd == "") {
                    alert("분류코드를 입력 해주세요.");
                    return false;
                }

                if (arr[i].locCode == null || wijmo.isEmpty(arr[i].locCode) || arr[i].locCode == "") {
                    alert("지역코드를 입력 해주세요.");
                    return false;
                }

                if (arr[i].siTitle == null || wijmo.isEmpty(arr[i].siTitle) || arr[i].siTitle == "") {
                    alert("제목을 입력 해주세요.");
                    return false;
                }

                if (arr[i].mobileUrl == null || wijmo.isEmpty(arr[i].mobileUrl) || arr[i].mobileUrl == "") {
                    alert("모바일주소를 입력 해주세요.");
                    return false;
                }

                if (arr[i].pcUrl == null || wijmo.isEmpty(arr[i].pcUrl) || arr[i].pcUrl == "") {
                    alert("피시주소를 입력 해주세요.");
                    return false;
                }

                if (arr[i].siEndDt == null || wijmo.isEmpty(arr[i].siEndDt) || arr[i].siEndDt == "") {
                    alert("마감일을 입력 해주세요.");
                    return false;
                }

                if (arr[i].siCretDt == null || wijmo.isEmpty(arr[i].siCretDt) || arr[i].siCretDt == "") {
                    alert("생성일을 입력 해주세요.");
                    return false;
                }

                if (arr[i].businessCtg == null || wijmo.isEmpty(arr[i].businessCtg) || arr[i].businessCtg == "") {
                    alert("사업분야를 입력 해주세요.");
                    return false;
                }

                if (arr[i].techCtg == null || wijmo.isEmpty(arr[i].techCtg) || arr[i].techCtg == "") {
                    alert("기술분야를 입력 해주세요.");
                    return false;
                }

                if (arr[i].businessType == null || wijmo.isEmpty(arr[i].businessType) || arr[i].businessType == "") {
                    alert("사업자형태를 입력 해주세요.");
                    return false;
                }

                if (arr[i].companyType == null || wijmo.isEmpty(arr[i].companyType) || arr[i].companyType == "") {
                    alert("기업형태를 입력 해주세요.");
                    return false;
                }

                if (arr[i].startPeriod == null || wijmo.isEmpty(arr[i].startPeriod) || arr[i].startPeriod == "") {
                    alert("창업기간을 입력 해주세요.");
                    return false;
                }

                if(text =='corp'){
                    if (arr[i].corpCd == null || wijmo.isEmpty(arr[i].corpCd) || arr[i].corpCd == "") {
                        alert("고객사코드를 입력 해주세요.");
                        return false;
                    }
                }
            }
        }

        if (!confirm(arr.length + "건의 상태를 변경하시겠습니까?")) return false;

        /* observer배열에서 기본배열로 옮겨담는다 */
        let rows = arr.map(obj => obj);

        let url = text == 'support' ? "/cms/support/uploadExcel" : "/cms/corp/support/uploadExcel";

        await axios.post(url, rows).then((res) => {
            if (res.status == 200) {
                alert("저장했습니다.");
                getList();
            } else {
                alert("오류가 발생했습니다. 다시 시도해 주세요.");
            }
        })
    }

    //컬럼위치저장
    function getGrid(){

        if(text == 'corp') _getUserGridLayout(text+'Layout', corpGrid, corpSelector);
        else _getUserGridLayout(text+'Layout', supportGrid);
    }

    //컬럼초기화
    function resetGrid(){
        if(text =='corp')
            _resetUserGridLayout(text+'Layout', corpGrid,corpColumns, corpSelector);
        else
            _resetUserGridLayout(text+'Layout', supportGrid, supportColumns);
    }

    //컬럼추가
    function addGrid(){

        corpView.addNew();
        var dataPerPage = corpGrid.collectionView.pageSize;
        var totalData = corpGrid.collectionView.totalItemCount;
        var totalPage = Math.ceil(totalData / dataPerPage);
        clickPager(totalPage,corpGrid,'corpGrid');
        corpView.commitNew();
        corpGrid.select(new wijmo.grid.CellRange(corpGrid.rows.length - 1,0), true);  //셀선택 추가된행으로 선택되게
    }

    //컬럼삭제
    function delGrid(){
        const item = corpGrid.rows.filter(r => r.isSelected);
        let rows = [];
        if (item.length == 0) {
            alert("선택된 행이 없습니다.");
            return false;
        } else {
            item.map(v => {  rows.push(v.dataItem); })

            if (confirm("선택한 행들을 삭제 하시겠습니까?")) {
                $.ajax({
                    url: "/cms/corp/support/delete",
                    async: false, // 비동기모드 : true, 동기식모드 : false
                    type: 'post',
                    contentType: 'application/json',
                    data: JSON.stringify(rows),
                    success: function (result) {
                        alert("총 " + result + "건이 삭제되었습니다.");
                        getList();
                    },
                    error: function (request, status, error) {
                        alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                    }
                });
            }
        }
    }

    // 엑셀그리드 저장
    function saveGrid() {
        // 엑셀 업로드 저장하기
        var item = text == 'support' ? excelGrid.rows : corpExcelGrid.rows;
        var rows = [];
        var params;
        var grid = text == 'support' ? excelGrid : corpExcelGrid;
        for (var i = 0; i < item.length; i++) {
            var value;
            // if(!wijmo.isNumber(value)){
            //     alert("INDEX는 숫자만 입력 가능합니다.");
            //     return false;
            // }
            // value = wijmo.changeType(excelGrid.collectionView.items[i].금액, wijmo.DataType.Number, null);
            // if(!wijmo.isNumber(value)){
            //     alert("금액은 숫자만 입력 가능합니다.");
            //     return false;
            // }
            var flag = wijmo.changeType(grid.collectionView.items[i].활성화여부, wijmo.DataType.String, null);
            if (flag != 'Y' && flag != 'N') {
                alert("활성화여부는 Y/N 중 하나를 입력하시기 바랍니다.");
                return false;
            }
            if (flag == 'Y') {
                value = wijmo.changeType(grid.collectionView.items[i].분류, wijmo.DataType.String, null);
                if (value == null || value == '') {
                    alert("분류를 입력하시기 바랍니다.");
                    return false;
                }
            }
            // }else if(!(value == 11 || value == 26 || value == 27 || value == 28 || value == 30 || value == 41 || value == 43
            //     || value == 44 || value == 45 || value == 46 || value == 47 || value == 48 || value == 50)){
            //     alert("지역코드는 코드참고표 시트에 있는 코드만 입력하시기 바랍니다.");
            //     return false;
            // }
            // if( (value == 11 && txt != '서울특별시')|| (value == 26 && txt != '부산광역시') || (value == 27 && txt != '대구광역시') || (value == 28 && txt != '인천광역시')
            //     || (value == 30 && txt != '대전광역시')|| (value == 41 && txt != '경기도')     || (value == 43 && txt != '충청북도')  || (value == 44 && txt != '충청남도')
            //     || (value == 45 && txt != '전라북도')  || (value == 46 && txt != '전라남도')   || (value == 47 && txt != '경상북도') || (value == 48 && txt != '경상남도')
            //     || (value == 50 && txt != '제주특별자치도')){
            //     alert("지역코드와 지역명이 일치하지 않습니다.");
            //     return false;
            // }
            value = wijmo.changeType(grid.collectionView.items[i].마감일, wijmo.DataType.String, null);
            var dateRegExp = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;
            if (value != null && value != '' && !dateRegExp.test(value)) {
                alert("마감일은 YYYY-MM-DD 형태로 입력하시기 바랍니다.");
                return false;
            }

            value = wijmo.changeType(grid.collectionView.items[i].기관, wijmo.DataType.String, null);
            if (value == null || value == '') {
                alert("기관을 입력하시기 바랍니다.");
                return false;
            }

            value = wijmo.changeType(grid.collectionView.items[i].제목, wijmo.DataType.String, null);
            if (value == null || value == '') {
                alert("제목을 입력하시기 바랍니다.");
                return false;
            }

            value = wijmo.changeType(grid.collectionView.items[i].금액, wijmo.DataType.String, null);
            if (value == null || value == '') {
                grid.collectionView.items[i].금액 = 0;
            }


            params = {
                siIdx: grid.collectionView.items[i].index,
                targetCostValue: grid.collectionView.items[i].금액,
                siActiveYn: grid.collectionView.items[i].활성화여부,
                siEndDt: new Date(grid.collectionView.items[i].마감일).format("yyyy-mm-dd"),
                siCretDt: new Date(grid.collectionView.items[i].생성일).format("yyyy-mm-dd"),
                targetName: grid.collectionView.items[i].기관,
                targetCatName: grid.collectionView.items[i].분류,
                siTitle: grid.collectionView.items[i].제목,
                mobileUrl: grid.collectionView.items[i].모바일주소,
                pcUrl: grid.collectionView.items[i].피시주소,
                locCode: grid.collectionView.items[i].지역코드,
                businessCtg : grid.collectionView.items[i].사업분야,
                techCtg : grid.collectionView.items[i].기술분야,
                businessType : grid.collectionView.items[i].사업자형태,
                companyType : grid.collectionView.items[i].기업형태,
                startPeriod : grid.collectionView.items[i].창업기간,
                targetCatCd : grid.collectionView.items[i].분류코드
            }

            rows.push(params);
            console.log(rows);
        }

        let url = text == 'support' ? "/cms/support/uploadExcel" : "/cms/corp/support/uploadExcel";
        if (confirm("저장 하시겠습니까?")) {
            $.ajax({
                url: url,
                async: false, // 비동기모드 : true, 동기식모드 : false
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(rows),
                success: function (result) {
                    alert("총 " + result + "건이 저장되었습니다.");

                    if(text == 'support'){
                        $("#excelDiv").hide();
                        $('#supportDiv2').hide();
                        $('#corpExcelDiv').hide();
                        $("#supportDiv").show();

                    }else{
                        $("#corpExcelDiv").hide();
                        $('#supportDiv').hide();
                        $('#excelDiv').hide();
                        $("#supportDiv2").show();
                    }
                    getList();
                },
                error: function (request, status, error) {
                    alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                }
            });
        }
    }



</script>
