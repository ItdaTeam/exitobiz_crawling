<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko" dir="ltr">
<head>
    <meta charset="utf-8">
    <title>엑시토 cms</title>
    <script src="https://code.jquery.com/jquery-2.2.4.js"
            integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <link rel="stylesheet" href="../../../../css/manage.css">
    <link rel="stylesheet" href="../../../../css/common.css">

    <script src="../../../../js/common.js"></script>
    <!-- wijmo css -->
    <link rel="stylesheet" href="../../../../wijmo/styles/custom.css"/>
    <link rel="stylesheet" href="../../../../wijmo/styles/wijmo.css"/>
    <!-- wijmo js -->
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.grid.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.input.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.xlsx.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.grid.xlsx.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.grid.filter.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/cultures/wijmo.culture.ko.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/jszip.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.grid.cellmaker.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.nav.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.grid.selector.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.gauge.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.chart.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.chart.interaction.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.chart.animation.min.js"></script>
    <script type="text/javascript" src="../../../../wijmo/controls/wijmo.chart.radar.min.js"></script>

    <!-- fontawesome -->
    <script src="https://kit.fontawesome.com/7b32d23811.js" crossorigin="anonymous"></script>
    <!-- material icon -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- googleFont -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Play:wght@400;700&display=swap" rel="stylesheet">
    <!-- Wijmo 배포라이선스키 적용 (배포 시 필요) -->
    <script>
        wijmo.setLicenseKey("exitobiz.co.kr,635866286281551#B0IONwmbBJye0ICRiwiI34TQNNEZh9WOJBVY836YL5kd4IWcrElNmFWYlhzNKZUZSh5bwI7c09EdvQVcK34MN56MzwWTHV7NtVzQNlDVCplMTZnTOp5UPZEWHd5YTVkN6d6dORWVPFWOiRVNSJWY43GSwEGSUV5aMhzR8dURQJDS9hGdxp7aCd5TrNTS8RmQWlDOzZTewtGS8BXRMFTRrIkY5p4dOhDVo9kdXNkSLFFaa5UO5wEW9xUZzgzRT5kQKZ6bCVkQpBTatNmWQFETwJ7RjFWMlVEbJZjexkzSERFSH3CU5xmaDtkMmZzKPhHWOFjU4cjUxdUWKFzK92WY5RGe4s6ZK3ybapFOKtWV03yd9IWeRNXVEF7NpVWOvY6QxVWZBhWZpRFdzE4cS5UU7A5THhmMYpUNiJFSGNnarNXZ4cEVx5mclh6MzsCUrMXS6UmNx4WS5olRr5GRsdXVlV4K6sEWyJkI0IyUiwiI4YjN5EEN7EjI0ICSiwiM7UDM5QDOxYTM0IicfJye35XX3JSSwIjUiojIDJCLi86bpNnblRHeFBCI4VWZoNFelxmRg2Wbql6ViojIOJyes4nI5kkTRJiOiMkIsIibvl6cuVGd8VEIgIXZ7VWaWRncvBXZSBybtpWaXJiOi8kI1xSfis4N8gkI0IyQiwiIu3Waz9WZ4hXRgAydvJVa4xWdNBybtpWaXJiOi8kI1xSfiQjR6QkI0IyQiwiIu3Waz9WZ4hXRgACUBx4TgAybtpWaXJiOi8kI1xSfiMzQwIkI0IyQiwiIlJ7bDBybtpWaXJiOi8kI1xSfiUFO7EkI0IyQiwiIu3Waz9WZ4hXRgACdyFGaDxWYpNmbh9WaGBybtpWaXJiOi8kI1tlOiQmcQJCLiADM7MjNwAiMyMDMyIDMyIiOiQncDJCLiI7au26YuoXai3GdphXZiojIz5GRiwiIFeJ1ImZ1iojIh94QiwiIxUTNxgjM6gjM6YDO5MjNiojIklkIs4XXbpjInxmZiwiIxYXMyAjMiojIyVmdiwSZzxWYmpjIyNIZhs");
    </script>
</head>

<body>
<div id="graybg"></div>
<%--사업공고 모달--%>
<div class="modal business_modal">
    <h1>제목<span class="X" onclick="modalOFF()">X</span></h1>
    <p>필수항목<span class="Essential">*</span></p>
    <div>
        <section>
            <p>활성화<span class="Essential">*</span></p>
            <input type="radio" name="or" id="or">
            <label for="or">활성화</label>
            <input type="radio" name="or" id="Nor">
            <label for="Nor">비활성화</label>
        </section>
        <section>
            <p>사업개요<span class="Essential">*</span></p>
            <textarea name="" id="" cols="30" rows="10" placeholder="사업 개요를 작성하세요"></textarea>
        </section>
        <section>
            <p>지원자격<span class="Essential">*</span></p>
            <textarea name="" id="" cols="30" rows="10" placeholder="지원자격을 작성하세요"></textarea>
        </section>
        <section>
            <p>지원내용<span class="Essential">*</span></p>
            <textarea name="" id="" cols="30" rows="10" placeholder="페이지에 대해 소개글을 작성하세요"></textarea>
        </section>
        <section>
            <p>신청방법 및 서류<span class="Essential">*</span></p>
            <textarea name="" id="" cols="30" rows="10" placeholder="지원자격을 작성하세요"></textarea>
        </section>
    </div>
    <div class="B_btns">
        <section>
            <p>삭제</p>
            <p>저장</p>
        </section>
    </div>
</div>
<%--사업공고 모달 끝--%>
<div class="wrap business">
    <%@ include file="../include/nav.jsp" %>
    <div class="main">
        <section class="top">
            <h1 onclick="modalON()"><span class="material-icons-round"> done_outline</span>사업공고 관리</h1>
            <div class="main_summary main_summary3">
                <dl>
                    <dt onclick="a()">전체</dt>
                    <dd>${businessData.totalAgencyCnt}개</dd>
                </dl>
                <dl>
                    <dt>활성화 사업공고</dt>
                    <dd>${businessDataData.avtivebusinessDataCnt}개</dd>
                </dl>
                <dl>
                    <dt>비활성화 사업공고</dt>
                    <dd>${allCrawlingCnt}개</dd>
                </dl>
            </div>
        </section>
        <div class="Msearch3">
            <ul>
                <li><a href="#"><span class="material-icons-outlined">view_list</span>엑셀템플릿</a></li>
                <li><a href="#"><span class="material-icons-outlined">restart_alt</span>엑셀업로드</a></li>
                <li><a href="#"><span class="material-icons-outlined">file_download</span>엑셀 다운로드</a></li>
            </ul>
        </div>

        <div class="tabMenu">
            <ul>
                <li class="TabM1">탭메뉴 1</li>
                <li class="TabM2">탭메뉴 2</li>
            </ul>
        </div>


        <div class="mainnav">
            <section class="middle">
                <fieldset class="Msearch1">
                    <form action="#" id="search_form" name="search_form" onsubmit="return showGrid(this)">
                        <input type="hidden" name="viewType">
                        <label for="category">검색조건:</label>
                        <select name="category" id="category">
                            <option value="all" selected>전체</option>
                            <option value="businessData">기관명</option>
                            <option value="url">URL</option>
                            <option value="location">지역</option>
                            <option value="remark">비고</option>
                        </select>
                        <input type="text" id="keyword" name="keyword" placeholder=".로 다중검색">
                        <input type="submit" id="search" value="검색">
                    </form>
                </fieldset>

                <div class="Msearch2">
                    <select onchange="$('#search').click();">
                        <option value="30">30</option>
                        <option value="50">50</option>
                        <option value="100" selected>100</option>
                    </select>
                    <ul>
                        <li><span class="material-icons-outlined">view_list</span><a href="#" onClick="_getUserGridLayout('supportLayout', supportGrid);">칼럼위치저장</a></li>
                        <li><span class="material-icons-outlined">restart_alt</span><a href="#" onClick="_resetUserGridLayout('supportLayout', supportGrid,supportColumns);">칼럼초기화</a></li>
                        <li><span class="material-icons-outlined">add_circle</span><a href="#" onclick="editGrid()">추가</a></li>
                        <li><span class="material-icons-outlined">do_not_disturb_on</span><a href="#" onclick="editGrid()">삭제</a></li>
                        <li><span class="material-icons-outlined">save</span><a href="#" onclick="editGrid()">저장</a></li>
                    </ul>
                </div>
            </section>

            <div class="grid_wrap" style="position:relative;">
                <div id="businessGrid"></div>
                <div id="businessGridPager" class="pager"></div>
            </div>

            <div class="lastBt">
                <ul>
                    <li><span class="material-icons-outlined">view_list</span><a href="#" onClick="_getUserGridLayout('supportLayout', supportGrid);">칼럼위치저장</a></li>
                    <li><span class="material-icons-outlined">restart_alt</span><a href="#" onClick="_resetUserGridLayout('supportLayout', supportGrid,supportColumns);">칼럼초기화</a></li>
                    <li><span class="material-icons-outlined">add_circle</span><a href="#" onclick="editGrid()">추가</a></li>
                    <li><span class="material-icons-outlined">do_not_disturb_on</span><a href="#" onclick="editGrid()">삭제</a></li>
                    <li><span class="material-icons-outlined">save</span><a href="#" onclick="editGrid()">저장</a></li>
                </ul>
            </div>

<%--            <div class="grid_wrap" id="supportDiv" style="position:relative;">--%>
<%--                <div id="supportGrid"></div>--%>
<%--                <div id="supportGridPager" class="pager"></div>--%>
<%--            </div>--%>
<%--            <div class="grid_wrap" id="excelDiv" style="position:relative;">--%>
<%--                <div id="excelGrid"></div>--%>
<%--            </div>--%>

        </div>
    </div>
</div>
</body>
</html>
<script>
<%--  모달창 On/Off  --%>
function modalON(){
    document.querySelector(".modal").classList.add("active")
    document.getElementById("graybg").classList.add("active")
}

function modalOFF(){
    document.querySelector(".modal").classList.remove("active")
    document.getElementById("graybg").classList.remove("active")
}
<%--  모달창 On/Off 끝  --%>


    var businessGrid;
    var businessView;
    var businessGridPager;
    var businessColumns;

    function pageOnLoad() {
        loadGridAgencyList('init');
        document.getElementById("business").classList.add("active");
        $("#excelDiv").hide();
        $("#importFile").on('change', function (params) {
            importExcel();
        });
    }

    function enterkey() {
        if (window.event.keyCode == 13) {

        }
    }

    //그리드 초기 셋팅
    function loadGridAgencyList(type, result) {
        if (type == "init") {
            businessView = new wijmo.collections.CollectionView(result, {
                pageSize: 100,
                trackChanges: true
            });

            businessGridPager = new wijmo.input.CollectionViewNavigator('#businessGridPager', {
                byPage: true,
                headerFormat: '{currentPage:n0} / {pageCount:n0}',
                cv: businessView
            });

            var onoffYnMap = "N,Y".split(",");	//온/오프라인 콤보박스

            businessColumns = [
                {binding: 'id', header: 'INDEX', isReadOnly: true, width: 80, align: "center"},
                {binding: 'title', header: '정보제공기관', isReadOnly: true, width: 200, align: "center"},
                {binding: 'url', header: '기업형태', isReadOnly: true, width: 300, align: "center", maxWidth: 300},
                {binding: 'locCode', header: '지원분야', isReadOnly: true, width: 100, align: "center"},
                {binding: 'crawlingcnt', header: '기술분야', isReadOnly: true, width: 100, align: "center"},
                {binding: 'activeYn', header: '지원금', isReadOnly: false, width: 100, align: "center", dataMap: onoffYnMap},
                {binding: 'errorYn', header: '지역코드', isReadOnly: false, width: 100, align: "center", dataMap: onoffYnMap},
                {binding: 'remark', header: 'URL', isReadOnly: true, width: 100, align: "center"},
                {binding: 'lastCrawlingDt', header: '사업공고명', isReadOnly: true, width: 200, align: "center"},
                {binding: 'createdAt', header: '마감일자', isReadOnly: true, width: 200, align: "center"},
                {binding: 'createdAt', header: '생성일자', isReadOnly: true, width: 200, align: "center"},
                {binding: 'createdAt', header: '활성화여부', isReadOnly: true, width: 200, align: "center"},
                {binding: 'createdAt', header: '조회수', isReadOnly: true, width: 200, align: "center"},
                {binding: 'createdAt', header: '세부내용', isReadOnly: true, width: 200, align: "center"},
            ];

            businessGrid = new wijmo.grid.FlexGrid('#businessGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: businessColumns,
                itemsSource: businessView,
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

            businessGrid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                    cell.textContent = (r + 1).toString();
                }
            };

            _setUserGridLayout('businessLayout', businessGrid, businessColumns);
        } else {
            businessView = new wijmo.collections.CollectionView(result, {
                pageSize: Number($('#viewNum').val()),
                trackChanges: true
            });
            businessGridPager.cv = businessView;
            businessGrid.itemsSource = businessView;
        }

        refreshPaging(businessGrid.collectionView.totalItemCount, 1, businessGrid, 'businessGrid');  // 페이징 초기 셋팅
    }

    const getData = async (form) => {

        // form.limit.value = document.getElementById("viewNum").value;
        form.viewType.value = document.getElementById("viewType").value;

        try{
            return await axios.get("/cms/support/agency/api",{
                params : {
                    inq : form.keyword.value,
                    con : form.category.value,
                    viewType : form.viewType.value
                }
            })
        }catch (error){
            console.log(error);
        }
    }

    const showGrid = (form) => {
        const gridData = getData(form)
            .then(res => {
                loadGridAgencyList('search', res.data)
            })
            .catch(error => {
                console.log(error);
            })
        return false;
    }

    const editGrid = async () => {
        const editItem = businessView.itemsEdited;


        if (editItem.length < 1) {
            alert("수정된 내역이 없습니다.");
            return false;
        }

        if (!confirm(editItem.length + "건의 상태를 변경하시겠습니까?")) return false;

        /* observer배열에서 기본배열로 옮겨담는다 */
        let rows = editItem.map(obj => obj);

        await axios.put("/cms/support/agency/api", rows).then((res) => {
            if (res.status == 200) {
                alert("저장했습니다.");
                showGrid(document.search_form)
            } else {
                alert("오류가 발생했습니다. 다시 시도해 주세요.");
            }
        })
    }

    function exportManageExcel(){

        var gridView = businessGrid.collectionView;
        var oldPgSize = gridView.pageSize;
        var oldPgIndex = gridView.pageIndex;

        //전체 데이터를 엑셀다운받기 위해서는 페이징 제거 > 엑셀 다운 > 페이징 재적용 하여야 함.
        businessGrid.beginUpdate();
        businessView.pageSize = 0;

        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(businessGrid, {includeCellStyles: true, includeColumnHeaders: true}, '지원기관리스트.xlsx',
            saved => {
                gridView.pageSize = oldPgSize;
                gridView.moveToPage(oldPgIndex);
                businessGrid.endUpdate();
            }, null
        );
    }

    $(document).ready(function () {
        pageOnLoad();



        //  사업공고관리 탭메뉴
        $(".TabM1").click(function(){
            $(".TabM1").css("background-color","#37f1aa").css("border","1px solid #37f1aa");
            $(".TabM2").css("background-color","#fff").css("border","1px solid #ddd");
            $("#businessGrid").css("display","block");
            $("#businessGrid2").css("display","none");
        });

        $(".TabM2").click(function(){
            $(".TabM2").css("background-color","#37f1aa").css("border","1px solid #37f1aa");
            $(".TabM1").css("background-color","#fff").css("border","1px solid #ddd");
            $("#businessGrid").css("display","none");
            $("#businessGrid2").css("display","block");
        });

    });
</script>