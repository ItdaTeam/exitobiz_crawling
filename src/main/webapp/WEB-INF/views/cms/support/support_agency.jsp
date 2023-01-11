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
<div class="wrap">
    <%@ include file="../include/nav.jsp" %>
    <div class="main">
        <section class="top">
            <h1><span class="material-icons-round"> done_outline</span>지원기관 관리</h1>
            <div class="topMenu">
                <section class="topMenu1">
                    <p>전체 기관수</p>
                    <p>${agencyData.totalAgencyCnt}개</p>
                </section>

                <section class="topMenu2">
                    <p>활성화</p>
                    <p>${agencyData.avtiveAgencyCnt}개</p>
                </section>

                <section class="topMenu3">
                    <p>전체 크롤링 개수(Y)</p>
                    <p>${allCrawlingCnt}개</p>
                </section>

                <section class="topMenu4">
                    <p>에러상태</p>
                    <p>${agencyData.errorCnt}개</p>
                </section>

                <section class="topMenu5">
                    <p>최근 크롤링 시간</p>
                    <p>${agencyData.lastCrawlingDt}</p>
                </section>
            </div>
        </section>
        <div class="mainnav">
            <section class="middle">
                <fieldset class="Msearch1">
                    <form action="#" id="search_form" name="search_form" onsubmit="return showGrid(this)">
                        <input type="hidden" name="viewType">
                        <label for="category">검색조건:</label>
                        <select name="category" id="category">
                            <option value="all" selected>전체</option>
                            <option value="agency">기관명</option>
                            <option value="url">URL</option>
                            <option value="location">지역</option>
                            <option value="remark">비고</option>
                        </select>
                        <input type="text" id="keyword" name="keyword" placeholder=".로 다중검색">
                        <input type="submit" id="search" value="검색">
                    </form>
                </fieldset>

                <div class="Msearch2">
                    <ul>
                        <li><a href="#" onclick="editGrid()"><span class="material-icons-outlined save1">save_grid</span>저장</a></li>
                        <li><a href="#" onclick="_getUserGridLayout('agencyLayout', agencyGrid);"><span class="material-icons-outlined save2">view_list</span>칼럼위치저장</a></li>
                        <li><a href="#" onclick="_resetUserGridLayout('agencyLayout', agencyGrid,agencyColumns);"><span class="material-icons-outlined">restart_alt</span>칼럼초기화</a></li>
                        <li><a href="#" onclick="exportManageExcel()"><span class="material-icons-outlined down">file_download</span>엑셀 다운로드</a></li>
                    </ul>
                </div>
            </section>

            <section class="table">
                <fieldset class="tableS">
                    <select id="viewNum" onchange="$('#search').click();">
                        <option value="30">30</option>
                        <option value="50">50</option>
                        <option value="100" selected>100</option>
                    </select>
                    <select id="viewType" onchange="$('#search').click();">
                        <option value="all">전체</option>
                        <option value="active_y">활성화</option>
                        <option value="active_n">비활성화</option>
                    </select>
                </fieldset>
                <div class="TableDiv">
                    <div id="agencyGrid"></div>
                    <div id="agencyGridPager" class="pager"></div>
                </div>
            </section>

            <div class="lastBt">
                <ul>
                    <li><a href="#" onclick="editGrid()"><span class="material-icons-outlined save1">save_grid</span>저장</a></li>
                    <li><a href="#" onclick="_getUserGridLayout('agencyLayout', agencyGrid);"><span class="material-icons-outlined save2">view_list</span>칼럼위치저장</a></li>
                    <li><a href="#" onclick="_resetUserGridLayout('agencyLayout', agencyGrid,agencyColumns);"><span class="material-icons-outlined">restart_alt</span>칼럼초기화</a></li>
                    <li><a href="#" onclick="exportManageExcel()"><span class="material-icons-outlined down">file_download</span>엑셀 다운로드</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script>
    var agencyGrid;
    var agencyView;
    var agencyGridPager;
    var agencyColumns;

    function pageOnLoad() {
        loadGridAgencyList('init');
        document.getElementById("agency").classList.add("active");
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
            agencyView = new wijmo.collections.CollectionView(result, {
                pageSize: 100,
                trackChanges: true
            });

            agencyGridPager = new wijmo.input.CollectionViewNavigator('#agencyGridPager', {
                byPage: true,
                headerFormat: '{currentPage:n0} / {pageCount:n0}',
                cv: agencyView
            });

            var onoffYnMap = "N,Y".split(",");	//온/오프라인 콤보박스

            agencyColumns = [
                {binding: 'id', header: 'INDEX', isReadOnly: true, width: 80, align: "center"},
                {binding: 'title', header: '기관명', isReadOnly: true, width: 200, align: "center"},
                {binding: 'url', header: 'URL', isReadOnly: true, width: 300, align: "center", maxWidth: 300},
                {binding: 'locCode', header: '지역', isReadOnly: true, width: 100, align: "center"},
                {binding: 'crawlingcnt', header: '크롤링개수(Y)', isReadOnly: true, width: 100, align: "center"},
                {binding: 'activeYn', header: '활성화', isReadOnly: false, width: 100, align: "center", dataMap: onoffYnMap},
                {binding: 'errorYn', header: '에러상태', isReadOnly: false, width: 100, align: "center", dataMap: onoffYnMap},
                {binding: 'remark', header: '비고', isReadOnly: true, width: 100, align: "center"},
                {binding: 'lastCrawlingDt', header: '마지막크롤링시간', isReadOnly: true, width: 200, align: "center"},
                {binding: 'createdAt', header: '생성날짜', isReadOnly: true, width: 200, align: "center"},
            ];

            agencyGrid = new wijmo.grid.FlexGrid('#agencyGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: agencyColumns,
                itemsSource: agencyView,
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

            agencyGrid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                    cell.textContent = (r + 1).toString();
                }
            };

            _setUserGridLayout('agencyLayout', agencyGrid, agencyColumns);
        } else {
            agencyView = new wijmo.collections.CollectionView(result, {
                pageSize: Number($('#viewNum').val()),
                trackChanges: true
            });
            agencyGridPager.cv = agencyView;
            agencyGrid.itemsSource = agencyView;
        }

        refreshPaging(agencyGrid.collectionView.totalItemCount, 1, agencyGrid, 'agencyGrid');  // 페이징 초기 셋팅
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
        const editItem = agencyView.itemsEdited;


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

        var gridView = agencyGrid.collectionView;
        var oldPgSize = gridView.pageSize;
        var oldPgIndex = gridView.pageIndex;

        //전체 데이터를 엑셀다운받기 위해서는 페이징 제거 > 엑셀 다운 > 페이징 재적용 하여야 함.
        agencyGrid.beginUpdate();
        agencyView.pageSize = 0;

        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(agencyGrid, {includeCellStyles: true, includeColumnHeaders: true}, '지원기관리스트.xlsx',
            saved => {
                gridView.pageSize = oldPgSize;
                gridView.moveToPage(oldPgIndex);
                agencyGrid.endUpdate();
            }, null
        );
    }

    $(document).ready(function () {
        pageOnLoad();
    });
</script>
