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
<div class="main_wrap">
    <%@ include file="../include/nav.jsp" %>
    <div class="main_container">
        <section class="main_section">
            <h2 class="main_title"><span class="material-icons-round"> done_outline</span> 지원사업 관리</h2>
            <span id="PUSH" class="push">PUSH발송<i class="fa-solid fa-share-from-square"></i></span>
            <div class="main_summary">
                <dl>
                    <dt>전체 수</dt>
                    <dd><%=request.getAttribute("totalSupportCnt")%>개</dd>
                </dl>
                <dl>
                    <dt>지원사업(Y)</dt>
                    <dd><%=request.getAttribute("activeYCnt")%>개</dd>
                </dl>
                <dl>
                    <dt>지원사업(N)</dt>
                    <dd><%=request.getAttribute("activeNCnt")%>개</dd>
                </dl>
                <dl>
                    <dt>전체 조회수</dt>
                    <dd><%=request.getAttribute("allViewCnt")%>개</dd>
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
                <div class="btn_wrap">
                    <input type="file" class="form-control" style="display:none" id="importFile"
                           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>
                    <button class="btn stroke" onclick="downTemplate()"><span class="material-icons">attach_file</span>엑셀
                        템플릿
                    </button>
                    <button class="btn stroke" onClick="findFile();"><span class="material-icons"> upload</span>엑셀 업로드
                    </button>
                    <button class="btn stroke" onclick="exportExcel()"><span class="material-icons-outlined">file_download</span>엑셀
                        다운로드
                    </button>
                </div>
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
                        <button type="button" class="main_filter_btn" onclick="getSupportList()"><i>조회</i></button>
                    </form>
                </div>
                <!-- 보드 영역 main_dashboard-->
                <div class="main_dashboard">
                    <div class="sub_cont">
                        <div class="btn_wrap">
                            <select name="viewType" id="viewType" onchange="getSupportList()">
                                <option value="opt1">전체</option>
                                <option value="opt2">비활성화</option>
                                <option value="opt3">활성화</option>
                                <option value="opt4">마감임박순</option>
                                <option value="opt5">실시간인기순</option>
                                <option value="opt6">금액높은순</option>
                            </select>
                            <select name="viewNum" id="viewNum" onchange="getSupportList()">
                                <option value="100">100개씩</option>
                                <option value="50">50개씩</option>
                                <option value="30">30개씩</option>
                            </select>
                            <button type="button" class="btn stroke"
                                    onClick="_getUserGridLayout('supportLayout', supportGrid);"><span
                                    class="material-icons-outlined">view_list</span>칼럼위치저장
                            </button>
                            <button type="button" class="btn stroke"
                                    onClick="_resetUserGridLayout('supportLayout', supportGrid,supportColumns);"><span
                                    class="material-icons-outlined">restart_alt</span>칼럼초기화
                            </button>
                            <button type="button" class="btn stroke" onClick="editGrid();" id="gridBtn"><span
                                    class="material-icons-outlined" style="width:28px;">save_grid</span>그리드저장
                            </button>
                            <button type="button" class="btn stroke" onClick="saveGrid();" id="excelBtn"><span
                                    class="material-icons-outlined" style="width:28px;">save_grid</span>엑셀저장
                            </button>
                        </div>
                    </div>
                    <div class="grid_wrap" id="supportDiv" style="position:relative;">
                        <div id="supportGrid"></div>
                        <div id="supportGridPager" class="pager"></div>
                    </div>
                    <div class="grid_wrap" id="excelDiv" style="position:relative;">
                        <div id="excelGrid"></div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <div class="exitoModal">
        <section class="exitoModal1">
            <h1>푸쉬 알람 발송<span onclick="closeModal()">X</span></h1>
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
</div>
</body>
</html>
<script>
    var supportGrid;
    var supportView;
    var supportGridPager;
    var supportColumns;
    var excelGrid;
    var excelView;
    var excelSelector;
    var excelColumns;


    //조회일 기본값 설정
    const month = new Date().getMonth();

    document.getElementById("fromDate").value = new Date(new Date().setMonth(month - 1)).toISOString().slice(0,10);
    document.getElementById("toDate").value = new Date().toISOString().slice(0,10);

    function pageOnLoad() {
        loadGridSupportList('init');
        // sessionCheck(staffId);
        document.getElementById("support").classList.add("active");
        $("#excelDiv").hide();
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


    $(".push").click(function(){
        $('#push_form')[0].reset();

        $(".modal_bg").addClass("on");
        $(".exitoModal").addClass("on");

    });

    function closeModal(){
      $(".modal_bg").removeClass("on");
      $('.exitoModal').removeClass('on');
    }


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

        const response = axios.post("/push/sendSplit", null, {
            params : {
                userloc : result.slice(0,-1),
                title : push_form.title.value,
                body : push_form.body.value,
                idx : push_form.idx.value,
                keyId : 1
            }
        });

        alert("전송을 완료했습니다.");
        $('.exitoModal').removeClass('on');
    }

    function enterkey() {
        if (window.event.keyCode == 13) {
            getSupportList();
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

            _setUserGridLayout('supportLayout', supportGrid, supportColumns);

            excelColumns = [
                {binding: 'siIdx', header: 'Index', isReadOnly: true, width: 100, align: "center", dataType: "Number"},
                {binding: 'targetName', header: '기관', isReadOnly: true, width: 100, align: "center"},
                {binding: 'targetCatName', header: '분류', isReadOnly: true, width: 100, align: "center"},
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
                }
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
        }

        refreshPaging(supportGrid.collectionView.totalItemCount, 1, supportGrid, 'supportGrid');  // 페이징 초기 셋팅

    }

    function getSupportList() {
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

        $.ajax({
            type: 'GET',
            url: '/cms/allSupport',
            dataType: null,
            data: param,
            success: function (result) {
                console.log("getSupportList success");
                console.log(result);
                loadGridSupportList('search', result);
            },
            error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);

            }
        });
    }

    //템플릿 다운로드
    function downTemplate() {
        window.location.assign("<%=request.getContextPath()%>" + "/template/지원사업관리양식.xlsx");
    }

    //엑셀 다운로드
    function exportExcel() {
        var gridView = supportGrid.collectionView;
        var oldPgSize = gridView.pageSize;
        var oldPgIndex = gridView.pageIndex;

        //전체 데이터를 엑셀다운받기 위해서는 페이징 제거 > 엑셀 다운 > 페이징 재적용 하여야 함.
        if(gridView._src.length > 350){
            alert("조회일을 줄여주세요.");
            return false;
        }

        supportGrid.beginUpdate();
        supportView.pageSize = gridView._src.length;

        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(supportGrid, {includeCellStyles: true, includeColumnHeaders: true}, '지원사업리스트.xlsx',
            saved => {
               gridView.pageSize = oldPgSize;
               gridView.moveToPage(oldPgIndex);
                supportGrid.endUpdate();
            }, null
        );

    }

    //업로드 파일 찾기
    function findFile() {
        $("#importFile").val("");
        document.all.importFile.click();
    }

    //엑셀 업로드
    function importExcel() {
        $("#supportDiv").hide();
        supportView = new wijmo.collections.CollectionView(null, {
            pageSize: 100
        });
        $("#excelDiv").show();
        var inputEle = document.querySelector('#importFile');
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
        // 체크박스 생성
        // excelSelector = new wijmo.grid.selector.Selector(excelGrid);
        // excelSelector.column = excelGrid.columns[0];
    }

    const editGrid = async () => {
        const editItem = supportView.itemsEdited;


        if (editItem.length < 1) {
            alert("수정된 내역이 없습니다.");
            return false;
        }

        if (!confirm(editItem.length + "건의 상태를 변경하시겠습니까?")) return false;

        /* observer배열에서 기본배열로 옮겨담는다 */
        let rows = editItem.map(obj => obj);

        await axios.put("/cms/support", rows).then((res) => {
            if (res.status == 200) {
                alert("저장했습니다.");
                getSupportList();
            } else {
                alert("오류가 발생했습니다. 다시 시도해 주세요.");
            }
        })
    }

    // 엑셀그리드 저장
    function saveGrid() {
        // 엑셀 업로드 저장하기
        var item = excelGrid.rows;
        var rows = [];
        var params;
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
            var flag = wijmo.changeType(excelGrid.collectionView.items[i].활성화여부, wijmo.DataType.String, null);
            if (flag != 'Y' && flag != 'N') {
                alert("활성화여부는 Y/N 중 하나를 입력하시기 바랍니다.");
                return false;
            }
            if (flag == 'Y') {
                value = wijmo.changeType(excelGrid.collectionView.items[i].분류, wijmo.DataType.String, null);
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
            value = wijmo.changeType(excelGrid.collectionView.items[i].마감일, wijmo.DataType.String, null);
            var dateRegExp = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;
            if (value != null && value != '' && !dateRegExp.test(value)) {
                alert("마감일은 YYYY-MM-DD 형태로 입력하시기 바랍니다.");
                return false;
            }

            value = wijmo.changeType(excelGrid.collectionView.items[i].기관, wijmo.DataType.String, null);
            if (value == null || value == '') {
                alert("기관을 입력하시기 바랍니다.");
                return false;
            }

            value = wijmo.changeType(excelGrid.collectionView.items[i].제목, wijmo.DataType.String, null);
            if (value == null || value == '') {
                alert("제목을 입력하시기 바랍니다.");
                return false;
            }

            value = wijmo.changeType(excelGrid.collectionView.items[i].금액, wijmo.DataType.String, null);
            if (value == null || value == '') {
                excelGrid.collectionView.items[i].금액 = 0;
            }

            // value = wijmo.changeType(excelGrid.collectionView.items[i].Mobile주소, wijmo.DataType.String, null);
            // if(value == null || value == ''){
            //     alert("MOBILE주소를 입력하시기 바랍니다.");
            //     return false;
            // }




            params = {
                siIdx: excelGrid.collectionView.items[i].index,
                targetCostValue: excelGrid.collectionView.items[i].금액,
                siActiveYn: excelGrid.collectionView.items[i].활성화여부,
                siEndDt: new Date(excelGrid.collectionView.items[i].마감일).format("yyyy-mm-dd"),
                siCretDt: new Date(excelGrid.collectionView.items[i].생성일).format("yyyy-mm-dd"),
                targetName: excelGrid.collectionView.items[i].기관,
                targetCatName: excelGrid.collectionView.items[i].분류,
                siTitle: excelGrid.collectionView.items[i].제목,
                mobileUrl: excelGrid.collectionView.items[i].모바일주소,
                pcUrl: excelGrid.collectionView.items[i].피시주소,
                locCode: excelGrid.collectionView.items[i].지역코드
            }

            rows.push(params);
            console.log(rows);
        }
        console.log('#######3' , rows);
        if (confirm("저장 하시겠습니까?")) {
            $.ajax({
                url: "/cms/support/uploadExcel",
                async: false, // 비동기모드 : true, 동기식모드 : false
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(rows),
                success: function (result) {
                    alert("총 " + result + "건이 저장되었습니다.");
                    $("#excelDiv").hide();
                    $("#supportDiv").show();
                    getSupportList();
                },
                error: function (request, status, error) {
                    alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                }
            });
        }
    }

    $(document).ready(function () {
        pageOnLoad();
    });
</script>
