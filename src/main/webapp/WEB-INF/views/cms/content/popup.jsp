<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/header.jsp" %>
</head>
<body>
<div class="main_wrap">
    <%@ include file="../include/nav.jsp" %>
    <div class="main_container">
        <div class="popup" id="thumnailPopUp">
            <div class="popup_container">
                <div class="popup_head">
                    <button type="button" class="popup_close">x</button>
                </div>
                <div class="popup_inner" id="thumnail">
                </div>
            </div>
        </div>
        <section class="main_section">
            <h2 class="main_title"><span class="material-icons-round"> done_outline</span> 팝업관리</h2>
            <div class="main_summary">
                <dl>
                    <dt>팝업 수</dt>
                    <dd>${totalCnt}개</dd>
                </dl>
                <dl>
                    <dt>활성화</dt>
                    <dd>${activeCnt}개</dd>
                </dl>
                <!-- 클릭시 팝업창 띄움 -->
                <a href="javascript:void(0);" onclick="showPop('popupManagement')"><i></i>팝업 추가</a>
            </div>
            <div class="main_utility">
                <div class="btn_wrap">
                    <button class="btn stroke" onclick="exportExcel();">엑셀다운로드</button>
                </div>
            </div>
            <div class="main_content">
                <!-- 필터 영역 main_filter-->
                <div class="main_filter">
                    <form action="#" name="searchForm" onsubmit="return showGrid(this);">
                        <label for="fromDate">조회일</label>
                        <input type="date" id="fromDate" name="from">
                        -
                        <input type="date" id="toDate" name="to">
                        <button type="submit" class="main_filter_btn"><i>조회</i></button>
                    </form>
                </div>
                <!-- 보드 영역 main_dashboard-->
                <div class="main_dashboard">
                    <div class="sub_cont">
                        <div class="btn_wrap">
                            <select id="viewNum" name="viewNum">
                                <option value="100" selected="selected">100개씩</option>
                                <option value="50">50개씩</option>
                                <option value="30">30개씩</option>
                            </select>
                            <button type="button" class="btn stroke"
                                    onclick="_getUserGridLayout('popupLayout', popupGrid);">칼럼위치저장
                            </button>
                            <button type="button" class="btn stroke"
                                    onclick="_resetUserGridLayout('popupLayout', popupGrid, popupColumns);">칼럼초기화
                            </button>
                        </div>
                    </div>
                    <div class="grid_wrap" style="position:relative;">
                        <div id="popupGrid"></div>
                        <div id="popupGridPager" class="pager"></div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <!-- 팝업 -->
    <!-- 팝업 : 팝업 추가-->
    <div class="popup" id="popupManagement">
        <div class="popup_container">
            <div class="popup_head">
                <p class="popup_title">팝업추가</p>
                <button type="button" class="popup_close">x</button>
            </div>
            <div class="popup_inner">
                <dfn>필수항목 <i>*</i></dfn>
                <form id="form" onsubmit="return false;">
                    <input type="hidden" name="active" value="">
                    <input type="hidden" name="id" value="">
                    <table>
                        <tbody>
                        <tr>
                            <th>활성화</th>
                            <td>
                                <input type="checkbox" name="active_yn" id="modalActive">
                                <label for="modalActive"></label>
                            </td>
                        </tr>
                        <tr>
                            <th>게시일자<i>*</i></th>
                            <td>
                                <input type="date" name="from"> ~ <input type="date" name="to">
                            </td>
                        </tr>
                        <tr>
                            <th>제목<i>*</i></th>
                            <td>
                                <input type="text" name="title" style="width:300px;">
                            </td>
                        </tr>
                        <tr>
                            <th>카테고리<i>*</i></th>
                            <td>
                                <select name="category" id="popupCategory" style="width:300px">
                                    <option value="" hidden>카테고리를 선택해주세요</option>
                                    <option value="link">외부링크</option>
                                    <option value="banner">홍보용배너</option>
                                    <option value="notice">공지상세화면이동</option>
                                </select>
                            </td>
                        </tr>
                        <tr id="noticeTb">
                            <th>공지번호</th>
                            <td>
                                <input type="text" name="noticeId">
                            </td>
                        </tr>
                        <tr id="linkTb">
                            <th>링크</th>
                            <td>
                                <input type="text" name="link">
                            </td>
                        </tr>
                        <tr>
                            <th>썸네일<i>*</i></th>
                            <td name="img">
                                <img class="opt_img"
                                     style="width:200px; height:auto; position: relative; display: none; margin-bottom: 5px;">
                                <input type="file" name="upFile" accept="image/jpeg,image/png,image/gif" style="position: relative;">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="popup_btn_area">
                        <button class="btn confirm" onclick="popupAdd()">추가</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- 팝업추가 팝업 영역 끝-->
</div>
</body>
</html>
<script>
    var token = localStorage.getItem("jwt");
    var popupGrid;
    var popupView;
    var popupColumns;
    var popupGridPager;

    function pageOnLoad() {
        calDate();
        document.getElementById("popup").classList.add("active");
        loadGridPopUpList('init');
        // sessionCheck(staffId);
    }

    function enterkey() {
        if (window.event.keyCode == 13) {
            getMemberList();
        }
    }

    //그리드 초기 셋팅
    function loadGridPopUpList(type, result) {
        if (type == "init") {
            popupView = new wijmo.collections.CollectionView(result, {
                pageSize: 100
            });

            popupGridPager = new wijmo.input.CollectionViewNavigator('#popupGridPager', {
                byPage: true,
                headerFormat: '{currentPage:n0} / {pageCount:n0}',
                cv: popupView
            });

            var onoffYnMap = "N,Y".split(",");	//온/오프라인 콤보박스

            popupColumns = [
                {binding: 'id', header: '팝업번호', isReadOnly: true, width: 100, align: "center"},
                {binding: 'title', header: '제목', isReadOnly: true, width: 150, align: "center"},
                {binding: 'category', header: '카테고리', isReadOnly: true, width: 150, align: "center"},
                {binding: 'image', header: '썸네일', isReadOnly: true, width: 150, align: "center",
                    cellTemplate: wijmo.grid.cellmaker.CellMaker.makeImage({
                        click:() =>showPop("thumnailPopUp"),
                        attributes :{
                            onerror:
                                "this.onerror=null; this.src='https://exitobiz.co.kr/img/app.png';"
                        }
                    })
                },
                {binding: 'fromDt', header: '시작일', isReadOnly: true, width: 150, align: "center"},
                {binding: 'toDt', header: '종료일', isReadOnly: true, width: 150, align: "center"},
                {binding: 'active', header: '활성화', isReadOnly: true, width: 150, align: "center", dataMap: onoffYnMap},
                {binding: 'noticeId', header: '공지번호', isReadOnly: true, width: 150, align: "center"},
                {binding: 'link', header: '링크', isReadOnly: true, width: 150, align: "center"},
                {
                    binding: 'detail', header: '정보수정', isReadOnly: true, width: 150, align: "center",
                    cellTemplate: wijmo.grid.cellmaker.CellMaker.makeButton({
                        text: '조회',
                        click: (e, ctx) => showPop('popupManagement', ctx),
                    })
                },
            ];

            popupGrid = new wijmo.grid.FlexGrid('#popupGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: popupColumns,
                itemsSource: popupView
            });

            popupGrid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                    cell.textContent = (r + 1).toString();
                }
            };

            _setUserGridLayout('popupLayout', popupGrid, popupColumns);


        } else {
            popupView = new wijmo.collections.CollectionView(result, {
                pageSize: Number($('#viewNum').val())
            });
            popupGridPager.cv = popupView;
            popupGrid.itemsSource = popupView;
        }

        refreshPaging(popupGrid.collectionView.totalItemCount, 1, popupGrid, 'popupGrid');  // 페이징 초기 셋팅
    }

    const getData = async (form) => {

        try{
            return await axios.get("/cms/popup/api",{
                params : {
                    from : form.from.value,
                    to : form.to.value
                }
            })
        }catch (error){
            console.log(error);
        }
    }

    const showGrid = (form) => {
        const gridData = getData(form)
            .then(res => {
                console.log(res.data);
                loadGridPopUpList('search', res.data)
            })
            .catch(error => {
                console.log(error);
            })
        return false;
    }

    //팝업 오픈
    function showPop(pop, ctx = null) {
        const _target = document.getElementById(pop);

        /* 모달안에 인풋초기화 */
        _target.querySelectorAll("input, select").forEach((el, i) => {
            el.value = "";
        })

        _target.classList.add('is_on');

        switch (pop) {
            case "popupManagement":
                _target.querySelector(".popup_title").textContent = "팝업추가"
                _target.querySelector(".confirm").textContent = "추가"
                $(".opt_img").css("display", "none");
                $(".opt_img").attr("src", '');
                $("input[name='active_yn']").prop("checked", true)
                $('.confirm').attr('onclick', "popupAdd()");
                break;
            case "thumnailPopUp" :
                const imgPath = popupGrid.collectionView.currentItem["image"];
                console.log(imgPath);
                let img = '<img class="popup_img" src="'+imgPath+'"art="이미지" onerror="this.oneerror=null; this.src=`https://exitobiz.co.kr/img/app.png`">';
                $('#thumnail')
                    .empty()
                    .append(img)
                break;
            default:
                break;
        }

        /* 모달안에 값 있을 경우 */
        if (ctx != null) {

            switch (pop) {
                case "popupManagement":
                    _target.querySelector("input[name='from']").value = new Date(ctx.item.fromDt).format("yyyy-mm-dd");
                    _target.querySelector("input[name='to']").value = new Date(ctx.item.toDt).format("yyyy-mm-dd");
                    _target.querySelector("input[name='title']").value = ctx.item.title;
                    _target.querySelector("input[name='id']").value = ctx.item.id;
                    _target.querySelector("input[name='noticeId']").value = ctx.item.noticeId;
                    ctx.item.active == 'Y' ? $("input[name='active_yn']").prop("checked", true) : $("input[name='active_yn']").prop("checked", false);
                    if (ctx.item.image != null && ctx.item.image != '') {
                        $(".opt_img").css("display", "block")
                        $(".opt_img").attr("src", ctx.item.image)
                    }
                    _target.querySelector(".popup_title").textContent = "팝업수정"
                    _target.querySelector(".confirm").textContent = "수정"
                    $('.confirm').attr('onclick', "editPopup()");
                    _target.querySelectorAll("input, select").forEach((el, i) => {

                        if (el.tagName.toLowerCase() == "input") {
                            if (el.getAttribute("type").toLowerCase() == "text" || el.getAttribute("type").toLowerCase() == "hidden") {
                                el.value = ctx.item[el.getAttribute("name")]
                            }
                        } else if (el.tagName.toLowerCase() == "select") {
                            el.value = ctx.item[el.getAttribute("name")]
                        }

                    })
                    break;
                default:
                    break;
            }

        }
    }

    async function popupAdd() {

        const f = document.getElementById("form");

        if (f.from.value == "") {
            alert("시작일을 설정해주세요");
            return false;
        }
        if (f.to.value == "") {
            alert("종료일을 설정해주세요");
            return false;
        }

        if (f.title.value == "") {
            alert("제목을 입력해주세요");
            return false;
        }

        if (f.category.value == "") {
            alert("카테고리를 선택해주세요");
            return false;
        }
        if (f.upFile.value == "") {
            alert("썸네일을 입력해주세요");
            return false;
        }

        f.active.value = f.active_yn.checked ? 'Y' : 'N'

        const formData = new FormData();

        formData.append("from", f.from.value);
        formData.append("to", f.to.value);
        formData.append("title", f.title.value);
        formData.append("category", f.category.value);
        formData.append("link", f.link.value);
        formData.append("noticeId", f.noticeId.value);
        formData.append("active", f.active.value);
        formData.append("upFile", f.upFile.files[0])

        if (confirm("팝업을 추가하시겠습니까?")){
            await axios.post("/cms/popup", formData, {headers: {'Content-Type': 'multipart/form-data'}})
                .then((res) => {
                    console.log(res);
                    if (res.status == 200) {
                        alert("팝업추가를 완료했습니다.");
                        $('.popup').removeClass('is_on');
                        showGrid(document.searchForm);
                    } else {
                        alert("오류가 발생했습니다. 다시 시도해 주세요.");
                    }
                })
        }
        return false;
    }

    async function editPopup(){

        const f = document.getElementById("form");

        if (f.from.value == "") {
            alert("시작일을 설정해주세요");
            return false;
        }
        if (f.to.value == "") {
            alert("종료일을 설정해주세요");
            return false;
        }

        if (f.title.value == "") {
            alert("제목을 입력해주세요");
            return false;
        }

        if (f.category.value == "") {
            alert("카테고리를 선택해주세요");
            return false;
        }

        f.active.value = f.active_yn.checked ? 'Y' : 'N'

        const formData = new FormData();

        formData.append("id", f.id.value);
        formData.append("from", f.from.value);
        formData.append("to", f.to.value);
        formData.append("title", f.title.value);
        formData.append("category", f.category.value);
        formData.append("noticeId", f.noticeId.value);
        formData.append("link", f.link.value);
        formData.append("active", f.active.value);
        if(f.upFile.files[0]!=null){
            formData.append("upFile", f.upFile.files[0])
        }

        if (confirm("팝업을 수정하시겠습니까?")) {
            await axios.put("/cms/popup", formData, {headers: {'Content-Type': 'multipart/form-data'}})
                .then((res) => {
                    console.log(res);
                    if (res.status == 200) {
                        alert("팝업 수정을 완료했습니다.")
                        $('.popup').removeClass('is_on');
                        showGrid(document.searchForm);
                    } else {
                        alert("오류가 발생했습니다. 다시 시도해 주세요.");
                    }
                })
        }
        return false;
    }

    $(function(){

        $('input[name="upFile"]').change(function(){
            if(this.files && this.files[0]) {
                var reader = new FileReader;
                reader.onload = function(e) {
                    $(".opt_img").css("display","block")
                    $(".opt_img").attr("src", e.target.result)
                } // onload_function
                $('td[name="img"]').css("height","auto")
                reader.readAsDataURL(this.files[0]);
            }else{
                $(".opt_img").css("display","none")
                $(".opt_img").attr("src", '')
            }
        });

        $(document).on("click", ".popup_close", function () {
            $('.popup').removeClass('is_on');
            $(".opt_img").css("display", "none");
            $(".opt_img").attr("src", '');
        });

    })

    //엑셀다운로드
    function exportExcel(){

        var gridView = popupGrid.collectionView;
        var oldPgSize = gridView.pageSize;
        var oldPgIndex = gridView.pageIndex;

        //전체 데이터를 엑셀다운받기 위해서는 페이징 제거 > 엑셀 다운 > 페이징 재적용 하여야 함.
        popupGrid.beginUpdate();
        popupView.pageSize = 0;

        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(popupGrid, {includeCellStyles: true, includeColumnHeaders: true}, '팝업리스트.xlsx',
            saved => {
                gridView.pageSize = oldPgSize;
                gridView.moveToPage(oldPgIndex);
                popupGrid.endUpdate();
            }, null
        );
    }

    //날짜포맷 yyyy-MM-dd 변환
    //input : date 포맷
    function _getFormatDate(date, type) {
        if(type == 'm'){
            var year = date.getFullYear();
            var month = ("0" + (1 + date.getMonth())).slice(-2);

            return year + "-" + month;
        }else if(type == 'w'){
            var year = date.getFullYear();
            var week = ("0" + date.getWeek()).slice(-2);

            return year + "-W" + week;
        } else{
            var year = date.getFullYear();
            var month = ("0" + (1 + date.getMonth())).slice(-2);
            var day = ("0" + date.getDate()).slice(-2);

            return year + "-" + month + "-" + day;
        }
    }

    function calDate(){
        var fromDate = new Date()
        fromDate.setDate(fromDate.getDate() - 30);
        var fromday = _getFormatDate(fromDate);
        var today = _getFormatDate(new Date());
        $('#fromDate').val(fromday);
        $('#toDate').val(today);
    }

    $(document).ready(function () {
        pageOnLoad();
    });
</script>
