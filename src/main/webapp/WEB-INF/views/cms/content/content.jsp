<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/header.jsp" %>
    <script src="../ckeditor5/build/ckeditor.js"></script>
    <link rel="stylesheet" type="text/css" href="../ckeditor5/sample/styles.css">
    <link rel="stylesheet" href="../../../../css/content.css">
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
            <h2 class="main_title"><span class="material-icons-round"> done_outline</span> 콘텐츠관리</h2>
            <div class="main_summary">
                <dl>
                    <dt>HD현대일렉트릭</dt>
                    <dd id="hyundai">개</dd>
                </dl>
                <dl>
                    <dt>KT비즈메카</dt>
                    <dd id="ktbizmeka">개</dd>
                </dl>
                <dl>
                    <dt>정보성 콘텐츠</dt>
                    <dd id="content_info">개</dd>
                </dl>
                <dl>
                    <dt>교육 콘텐츠</dt>
                    <dd id="content_edu">개</dd>
                </dl>
                <dl>
                    <dt>유료 상품</dt>
                    <dd id="paid_product">개</dd>
                </dl>
                <!-- 클릭시 팝업창 띄움 -->
                <dl>
                    <a href="javascript:void(0);" onclick="showPop('contentManagement')">콘텐츠 추가</a>
                </dl>
            </div>
            <div class="main_utility">
                <div>
                    <ul>
                        <li class="btn_wrap"><a class="btn stroke1"   href="#" onclick="editGrid()">그리드 저장</a></li>
                        <li class="btn_wrap">
                            <button class="btn stroke1" onclick="exportExcel();">엑셀다운로드</button>
                        </li>

                    </ul>
                </div>
            </div>
            <div class="main_content">
                <!-- 필터 영역 main_filter-->
                <div class="main_filter">
                    <form action="#" name="searchForm" id="searchForm" onsubmit="return showGrid(this);">
<%--                        <input type="hidden" name="from">--%>
<%--                        <input type="hidden" name="to">--%>
<%--                        <input type="hidden" name="limit">--%>
                        <div class="date">
                            <label for="fromDate">조회일</label>
                            <input type="date" id="fromDate" name="from">
                            -
                            <input type="date" id="toDate" name="to">
                        </div>
                        <div class="categoryDiv">
                            <label for="category">검색조건:</label>
                            <select name="category" id="category">
                                <option value="all" selected>전체</option>
                                <option value="title" id="title">제목</option>
                            </select>
                            <input type="text" id="keyword" name="keyword" placeholder=".로 다중검색">
                        </div>
                        <button type="submit" class="main_filter_btn"><i>조회</i></button>
                    </form>
                </div>
                <!-- 보드 영역 main_dashboard-->
                <div class="main_dashboard">
                    <div class="sub_cont">
                        <div class="btn_wrap">
                            <select id="viewNum" name="viewNum" onchange="showGrid(document.getElementById('searchForm'))">
                                <option value="100" selected="selected">100개씩</option>
                                <option value="50">50개씩</option>
                                <option value="30">30개씩</option>
                            </select>
                            <button type="button" class="btn stroke"
                                    onclick="_getUserGridLayout('contentLayout', contentGrid);">칼럼위치저장
                            </button>
                            <button type="button" class="btn stroke"
                                    onclick="_resetUserGridLayout('contentLayout', contentGrid, contentColumns);">칼럼초기화
                            </button>
                        </div>
                    </div>
                    <div class="grid_wrap" style="position:relative;">
                        <div id="contentGrid"></div>
                        <div id="contentGridPager" class="pager"></div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <!-- 팝업 -->
    <!-- 팝업 : 팝업 추가-->
    <div class="popup" id="contentManagement">
        <div class="popup_container">
            <div class="popup_head">
                <p class="popup_title" name="popup_title">콘텐츠 추가</p>
                <button type="button" class="popup_close">x</button>
            </div>
            <div class="popup_inner">
                <dfn>필수항목 <i>*</i></dfn>
                <form action="#" method="post" id="form" enctype="multipart/form-data" onsubmit="return false;">
                    <input type="hidden" id="contentId" name="contentId">

                    <table>
                        <tbody>
                        <tr>
                            <th>활성화<i>*</i></th>
                            <td>
                                <input type="hidden" id="active" name="active">
                                <input type="checkbox" id="activeYnCheck" name="activeYn" checked>
                                <label for="activeYnCheck">체크 시, 활성화</label>
                            </td>
                        </tr>
                        <tr>
                            <th>노출 대상<i>*</i></th>
                            <td>
                                <select name="corpCd" id="corpCd">
                                    <option value="00" selected="selected">전체</option>
                                    <option value="01">HD현대일렉트릭</option>
                                    <option value="02">KT비즈메카</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>콘텐츠 유형<i>*</i></th>
                            <td>
                                <select name="contentType" id="contentType">
                                    <option value="01" selected="selected">정보성 콘텐츠</option>
                                    <option value="02">교육 콘텐츠</option>
                                    <option value="03">유료 상품</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>제목<i>*</i></th>
                            <td>
                                <input type="text" name="title" style="width:70%">
                            </td>
                        </tr>
                        <tr>
                            <th>썸네일<i>*</i></th>
                            <td>
                                <input type="file" id="imgFile" name="imgFile" accept="image/jpeg,image/png,image/gif" style="position: relative;">
                                <img id="preview" style="width:200px; height:auto; position: relative; display: none; margin-bottom: 5px;">

                            </td>
                        </tr>
                        <tr>
                            <th>금액<i>*</i></th>
                            <td>
                                <input type="number" name="cost">&nbsp;원
                            </td>
                        </tr>
                        <tr id="new_content_url_tb">
                            <th>URL<i>*</i></th>
                            <td>
                                <input type="text" name="url">
                            </td>
                        </tr>
                        <tr>
                            <th>순서<i>*</i></th>
                            <td>
                                <input type="number" name="sort">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="popup_btn_area">
                        <button class="btn confirm" onclick="contentConfirm('add')">추가</button>
                        <button style="display:none;" class="btn fill" onclick="contentConfirm('modify')">수정</button>
                        <button style="display:none;" id="delete" type="button" onclick="contentConfirm('delete')"
                                class="btn stroke">삭제
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="popup" id="image">
        <div id="img" onmousedown="closePop();">
        </div>
    </div>
    <!-- 팝업추가 팝업 영역 끝-->

</div>
</body>
</html>
<script>
    var token = localStorage.getItem("jwt");
    var contentGrid;
    var contentView;
    var contentColumns;
    var contentGridPager;
    let editor1;

    function pageOnLoad() {
        calDate();
        document.getElementById("content").classList.add("active");
        loadGridContentList('init');
    }

    //그리드 초기 셋팅
    function loadGridContentList(type, result) {
        if (type == "init") {
            contentView = new wijmo.collections.CollectionView(result, {
                pageSize: 100,
                groupDescriptions: ['type']
            })

            console.log("contentView1>>>>>>",contentView);

            contentGridPager = new wijmo.input.CollectionViewNavigator('#contentGridPager', {
                byPage: true,
                headerFormat: '{currentPage:n0} / {pageCount:n0}',
                cv: contentView
            });

            var onoffYnMap = "N,Y".split(",");	//온/오프라인 콤보박스

            contentColumns = [
                {binding: 'content_id', header: '콘텐츠번호', isReadOnly: true, width: 150, align: "center"},
                {binding: 'corp_nm', header: '노출 대상', isReadOnly: true, width: 150, align: "center"},
                {binding: 'content_type_nm', header: '콘텐츠 유형', isReadOnly: true, width: 150, align: "center"},
                {binding: 'title', header: '제목', isReadOnly: true, width: 400, align: "center"},
                {binding: 'img', header: '썸네일', isReadOnly: true, width: 150, align: "center",
                    cellTemplate: wijmo.grid.cellmaker.CellMaker.makeImage({
                        click:() =>showPop("thumnailPopUp"),
                        attributes :{
                            onerror:
                                "this.onerror=null; this.src='https://exitobiz.co.kr/img/app.png';"
                        }
                    })
                },
                {binding: 'url', header: 'URL', isReadOnly: true, width: 150, align: "center"},
                {binding: 'cost', header: '금액', isReadOnly: true, width: 150, align: "center"},
                {binding: 'sort', header: '순서', isReadOnly: true, width: 150, align: "center"},
                {binding: 'cret_dt', header: '작성날짜', isReadOnly: true, width: 150, align: "center"},
                {binding: 'updt_dt', header: '수정날짜', isReadOnly: true, width: 150, align: "center"},
                {binding: 'active_yn', header: '활성화', width: 150, align: "center", dataMap: onoffYnMap},
                {
                    binding: 'edit', header: '정보수정', isReadOnly: true, width: 150, align: "center",
                    cellTemplate: wijmo.grid.cellmaker.CellMaker.makeButton({
                        text: '수정',
                        click: (e, ctx) => showPop('contentManagement', ctx),
                    })
                },
            ];

            contentGrid = new wijmo.grid.FlexGrid('#contentGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: contentColumns,
                itemsSource: contentView,
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

            contentGrid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                    cell.textContent = (r + 1).toString();
                }
            };

            _setUserGridLayout('contentLayout', contentGrid, contentColumns);


        } else {
            contentView = new wijmo.collections.CollectionView(result, {
                pageSize: Number($('#viewNum').val()),
                trackChanges: true,
            });
            contentGridPager.cv = contentView;
            contentGrid.itemsSource = contentView;
        }

        refreshPaging(contentGrid.collectionView.totalItemCount, 1, contentGrid, 'contentGrid');  // 페이징 초기 셋팅
    }

    // function previewImage(event) {
    //     const preview = document.getElementById("preview");
    //     const file = event.target.files[0];
    //     const reader = new FileReader();
    //
    //
    //
    //     reader.onloadend = function() {
    //         preview.src = reader.result;
    //     }
    //
    //
    //
    //     if (file) {
    //         reader.readAsDataURL(file);
    //     } else {
    //         preview.src = "";
    //     }
    // }

    $('input[name="imgFile"]').change(function () {
        if (this.files && this.files[0]) {
            var reader = new FileReader;
            reader.onload = function (e) {
                $("#preview").css("display", "block")
                $("#preview").attr("src", e.target.result)
            } // onload_function
            $('td[name="img"]').css("height", "auto")
            reader.readAsDataURL(this.files[0]);
        } else {
            $("#preview").css("display", "none")
            $("#preview").attr("src", '')
        }
    });



    const getData = async (form) => {
        try {
            console.log(form);
            // 오늘 날짜 기준으로 1달 전 날짜 계산
            const today = new Date();
            const oneMonthAgo = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
            const fromDate = form.from.value || oneMonthAgo.toISOString().slice(0, 10);

            // 오늘 날짜 계산
            const toDate = form.to.value || today.toISOString().slice(0, 10);

            const response = await axios.post("/cms/getContentList", {
                keyword: form.keyword.value,
                category: form.category.value,
                fromDate,
                toDate
            },
            );
            console.log("response.data>>>>>>>>",response.data);
            document.getElementById("hyundai").innerHTML = getCountFromType(response.data,"01") + "개";
            document.getElementById("ktbizmeka").innerHTML = getCountFromType(response.data,"02") + "개";
            document.getElementById("content_info").innerHTML = getCountFromContent(response.data,"01") + "개";
            document.getElementById("content_edu").innerHTML = getCountFromContent(response.data,"02") + "개";
            document.getElementById("paid_product").innerHTML = getCountFromContent(response.data,"03") + "개";

            console.log("contentView2>>>>>>",contentView);
            return response.data;
        } catch (error) {
            console.log(error.response.data);
            throw error;
        }
    };

    function getCountFromType(data,type){
        const sum = data.filter((v)=>(v.corp_cd === type));
        return sum.length;
    };

    function getCountFromContent(data,contentType){
        const sum = data.filter((v)=>(v.content_type === contentType));
        return sum.length;
    };



    const showGrid = (form) => {
        getData(form)
            .then(data => {
                console.log(data);
                loadGridContentList('search', data);
            })
            .catch(error => {
                console.log(error);
            });
        return false;
    };


    //팝업 오픈
    function showPop(pop, ctx = null) {
        const _target = document.getElementById(pop);

        /* 모달안에 인풋초기화 */
        _target.querySelectorAll("input, select").forEach((el, i) => {
            el.value = "";
        })

        _target.classList.add('is_on');

        /* 모달안에 값 있을 경우 */
        if (ctx != null) {
            switch (pop) {
                case "contentManagement":
                    // Set popup title
                    _target.querySelector(".popup_title").textContent = "콘텐츠수정";
                    // Set input values
                    console.log("ctx.item>>>", ctx.item);
                    let res = {...ctx.item}
                    console.log("res>>>", res);

                    _target.querySelector("input[name='activeYn']").checked = ctx.item.active_yn == 'Y' ? true : false;
                    _target.querySelector("select[name='corpCd']").value = res.corp_cd;
                    _target.querySelector("select[name='contentType']").value = res.content_type;
                    _target.querySelector("input[name='title']").value = res.title;
                    _target.querySelector("input[name='contentId']").value = res.content_id;
                    _target.querySelector("input[name='url']").value = res.url;
                    _target.querySelector("input[name='sort']").value = res.sort;
                    _target.querySelector("input[name='cost']").value = res.cost;
                    // _target.querySelector("input[name='imgFile']").value = res.img;
                    if (ctx.item.img != null && ctx.item.img != '') {
                        $("#preview").css("display", "block")
                        $("#preview").attr("src", ctx.item.img)
                        $("#preview").attr("onerror", "this.onerror=null; this.src='https://exitobiz.co.kr/img/app.png';");
                    }
                    // Show/hide elements
                    $('.confirm').css("display", "none");
                    $('.fill').css("display", "block");
                    $('#delete').css("display", "block");

                    break;
                default:
                    break;
            }
        } else {
            switch (pop) {
                case "contentManagement":
                    _target.querySelector(".popup_title").textContent = "콘텐츠추가";
                    _target.querySelector("input[name='activeYn']").checked = true;
                    $('#new_content_cont_tb').css("display", "none");
                    $('.confirm').css("display", "block");
                    $('.fill').css("display", "none");
                    $('#delete').css("display", "none");
                    $("#preview").css("display", "none");
                    $("#preview").attr("src", '');
                    break;

                case "thumnailPopUp" :
                    const imgPath = contentGrid.collectionView.currentItem["preview"];
                    let img = '<img class="content_img" src="'+imgPath+'"art="이미지" onerror="this.oneerror=null; this.src=`https://exitobiz.co.kr/img/app.png`">';
                    $('#preview')
                        .empty()
                        .append(img)
                    break;
                default:
                    break;
            }
        }
    }


    //팝업 추가
    async function contentConfirm(type) {

        console.log("여긴가" + type);

        const f = document.getElementById("form");
        const formData = new FormData(f);

        //validation
        if (f.corpCd.value == "") {
            alert("노출대상을 선택해주세요");
            return false;
        }

        if (f.contentType.value == "") {
            alert("콘텐츠 유형을 선택해주세요");
            return false;
        }

        if (f.title.value == "") {
            alert("제목을 입력해주세요");
            return false;
        }

        if ( document.getElementById("preview").src == "") {
            alert("썸네일을 첨부해주세요");
            return false;
        }

        if (f.cost.value == "") {
            alert("금액을 입력해주세요");
            return false;
        }

        if (f.url.value == "") {
            alert("url을 입력해주세요");
            return false;
        }

        if (f.sort.value == "") {
            alert("순서를 입력해주세요");
            return false;
        }

        // f.active.value = f.activeYn.checked ? 'Y' : 'N'
        formData.delete('activeYn');
        formData.append('activeYn', f.activeYn.checked ? 'Y' : 'N');


        for (let key of formData.keys()) {
            console.log(key);
        }

        // FormData의 value 확인
        for (let value of formData.values()) {
            console.log(value);
        }


        switch (type) {
            case "add" :
                // $('#preview').empty();
                if (!confirm("콘텐츠를 추가하시겠습니까?")) return false;
                await axios.post("/cms/saveContent", formData, {headers: {'Content-Type': 'multipart/form-data'}})
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
                break;

            case "modify" :
                if (!confirm("콘텐츠를 수정하시겠습니까?")) return false;
                await axios.post("/cms/saveContent", formData, {headers: {'Content-Type': 'multipart/form-data'}})
                    .then((res) => {
                        console.log("수정>>>>>>",res);
                        if (res.status == 200) {
                            alert("콘텐츠 수정을 완료했습니다.");
                            $('.popup').removeClass('is_on');
                            showGrid(document.searchForm);
                        } else {
                            alert("오류가 발생했습니다. 다시 시도해 주세요.");
                        }
                    })
                break;

            case "delete" :
                if (!confirm("콘텐츠를 삭제하시겠습니까?")) return false;
                var param = {contentId : f.contentId.value }

                console.log('param >>> ', param);
                await axios.post("/cms/deleteContent", param, {headers: {'Content-Type': 'application/json'}})
                    .then((res) => {
                        console.log(res);
                        if (res.status == 200) {
                            alert("콘텐츠 삭제를 완료했습니다.");
                            $('.popup').removeClass('is_on');
                            showGrid(document.searchForm);
                        } else {
                            alert("오류가 발생했습니다. 다시 시도해 주세요.");
                        }
                    })
                break;
        }



    }


    //
    // async function contentTop() {
    //     const response2 = await axios.post("/cms/getTopInfo", {});
    //     console.log("RES2+++++++++++",response2)
    // }



    //엑셀다운로드
    function exportExcel() {

        var gridView = contentGrid.collectionView;
        var oldPgSize = gridView.pageSize;
        var oldPgIndex = gridView.pageIndex;

        //전체 데이터를 엑셀다운받기 위해서는 페이징 제거 > 엑셀 다운 > 페이징 재적용 하여야 함.
        contentGrid.beginUpdate();
        contentView.pageSize = 0;

        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(contentGrid, {
                includeCellStyles: true,
                includeColumnHeaders: true
            }, '콘텐츠리스트.xlsx',
            saved => {
                gridView.pageSize = oldPgSize;
                gridView.moveToPage(oldPgIndex);
                contentGrid.endUpdate();
            }, null
        );
    }

    $(function () {
        $('input[name="imgFile"]').change(function () {
            if (this.files && this.files[0]) {
                var reader = new FileReader;
                reader.onload = function (e) {
                    $(".opt_img").css("display", "block")
                    $(".opt_img").attr("src", e.target.result)
                } // onload_function
                $('td[name="img"]').css("height", "auto")
                reader.readAsDataURL(this.files[0]);
            } else {
                $(".opt_img").css("display", "none")
                $(".opt_img").attr("src", '')
            }
        });

        $(document).on("click", ".popup_close", function () {
            $('.popup').removeClass('is_on');
            $(".opt_img").css("display", "none");
            $(".opt_img").attr("src", '');
        });
    });

    //날짜포맷 yyyy-MM-dd 변환
    //input : date 포맷
    function _getFormatDate(date, type) {
        if (type == 'm') {
            var year = date.getFullYear();
            var month = ("0" + (1 + date.getMonth())).slice(-2);

            return year + "-" + month;
        } else if (type == 'w') {
            var year = date.getFullYear();
            var week = ("0" + date.getWeek()).slice(-2);

            return year + "-W" + week;
        } else {
            var year = date.getFullYear();
            var month = ("0" + (1 + date.getMonth())).slice(-2);
            var day = ("0" + date.getDate()).slice(-2);
            return year + "-" + month + "-" + day;
        }
    }

    function calDate() {
        var fromDate = new Date()
        fromDate.setDate(fromDate.getDate() - 30);
        var fromday = _getFormatDate(fromDate);
        var today = _getFormatDate(new Date());
        $('#fromDate').val(fromday);
        $('#toDate').val(today);
    }

    $(document).ready(function () {
        console.log("test");
        showGrid(document.getElementById('searchForm'));
        pageOnLoad();

    });

</script>
