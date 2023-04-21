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
                    <dd>개</dd>
                </dl>
                <dl>
                    <dt>KT비즈메카</dt>
                    <dd>개</dd>
                </dl>
                <dl>
                    <dt>정보성 콘텐츠</dt>
                    <dd>개</dd>
                </dl>
                <dl>
                    <dt>교육 콘텐츠</dt>
                    <dd>개</dd>
                </dl>
                <dl>
                    <dt>유료 상품</dt>
                    <dd>개</dd>
                </dl>
                <!-- 클릭시 팝업창 띄움 -->
                <dl>
                    <a href="javascript:void(0);" onclick="showPop('contentManagement')">콘텐츠 추가</a>
                </dl>
            </div>
            <div class="main_utility">
                <div>
                    <label for="fromDate">조회일</label>
                    <input type="date" id="fromDate" name="from">
                    -
                    <input type="date" id="toDate" name="to">
                </div>
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
                    <form action="#" name="searchForm" onsubmit="return showGrid(this);">
                        <input type="hidden" name="from">
                        <input type="hidden" name="to">
                        <input type="hidden" name="limit">
                        <label for="category">검색조건:</label>
                        <select name="category" id="category">
                            <option value="all" selected>전체</option>
                            <option value="title" id="title">제목</option>
                        </select>
                        <input type="text" id="keyword" name="keyword" placeholder=".로 다중검색">
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
                                    onclick="_getUserGridLayout('noticeLayout', noticeGrid);">칼럼위치저장
                            </button>
                            <button type="button" class="btn stroke"
                                    onclick="_resetUserGridLayout('noticeLayout', noticeGrid, noticeColumns);">칼럼초기화
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
                <p class="popup_title">콘텐츠 추가</p>
                <button type="button" class="popup_close">x</button>
            </div>
            <div class="popup_inner">
                <dfn>필수항목 <i>*</i></dfn>
                <form action="#" method="post" id="contentForm" enctype="multipart/form-data" onsubmit="return false;">
                    <input type="hidden" id="id" name="id">
                    <input type="hidden" id="active" name="active">
                    <table>
                        <tbody>
                        <tr>
                            <th>활성화<i>*</i></th>
                            <td>
                                <input type="checkbox" id="activeYn" name="activeYn" checked>
                                <label for="activeYn">체크 시, 활성화</label>
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
<%--                        <tr>--%>
<%--                            <th>콘텐츠 유형<i>*</i></th>--%>
<%--                            <td>--%>
<%--                                <select name="contentType" id="contentType">--%>
<%--                                    <option value="01" selected="selected">정보성 콘텐츠</option>--%>
<%--                                    <option value="02">교육 콘텐츠</option>--%>
<%--                                    <option value="03">유료 상품</option>--%>
<%--                                </select>--%>
<%--                            </td>--%>
<%--                        </tr>--%>
                        <tr>
                            <th>제목<i>*</i></th>
                            <td>
                                <input type="text" name="title" style="width:70%">
                            </td>
                        </tr>
                        <tr>
                            <th>썸네일<i>*</i></th>
                            <td>
                                <input type="file" name="imgFile" accept="image/jpeg,image/png,image/gif"
                                       style="position: relative;">
                                <img class="opt_img"
                                     style="width:200px; height:auto; position: relative; display: none; margin-bottom: 5px;">
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

            contentGridPager = new wijmo.input.CollectionViewNavigator('#contentGridPager', {
                byPage: true,
                headerFormat: '{currentPage:n0} / {pageCount:n0}',
                cv: contentView
            });

            var onoffYnMap = "N,Y".split(",");	//온/오프라인 콤보박스

            contentColumns = [
                {binding: 'id', header: '콘텐츠번호', isReadOnly: true, width: 150, align: "center"},
                {binding: 'exposure', header: '노출 대상', isReadOnly: true, width: 150, align: "center"},
                {binding: 'type', header: '콘텐츠 유형', isReadOnly: true, width: 150, align: "center"},
                {binding: 'title', header: '제목', isReadOnly: true, width: 400, align: "center"},
                {binding: 'thumbnail', header: '썸네일', isReadOnly: true, width: 150, align: "center",
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
                {binding: 'remark', header: '순서', isReadOnly: true, width: 150, align: "center"},
                {binding: 'cretDt', header: '작성날짜', isReadOnly: true, width: 150, align: "center"},
                {binding: 'updtDt', header: '수정날짜', isReadOnly: true, width: 150, align: "center"},
                {
                    binding: 'activeYn',
                    header: '공지활성화',
                    width: 150,
                    align: "center",
                    dataMap: onoffYnMap,
                    isReadOnly : true
                },
                {
                    binding: 'edit', header: '정보수정', isReadOnly: true, width: 150, align: "center",
                    cellTemplate: wijmo.grid.cellmaker.CellMaker.makeButton({
                        text: '조회',
                        click: (e, ctx) => showPop('contentManagement', ctx),
                    })
                },
            ];

            contentGrid = new wijmo.grid.FlexGrid('#contentGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: contentColumns,
                itemsSource: contentView
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
                groupDescriptions: ['type']
            });
            contentGridPager.cv = contentView;
            contentGrid.itemsSource = contentView;
        }

        refreshPaging(contentGrid.collectionView.totalItemCount, 1, contentGrid, 'contentGrid');  // 페이징 초기 셋팅
    }

    const getData = async (form) => {

        try {
            return await axios.get("/cms/content/api", {
                params: {
                    inq: form.keyword.value,
                    con: form.category.value,
                    from: form.from.value,
                    to: form.to.value
                }
            })
        } catch (error) {
            console.log(error);
        }
    }

    const showGrid = (form) => {
        const gridData = getData(form)
            .then(res => {
                console.log(res.data);
                loadGridContentList('search', res.data)
            })
            .catch(error => {
                console.log(error);
            })
        return false;
    }


    //컨텐츠 리스트 불러오기
   function getContentList(){
        var param = {
            category : $('#title').val(),
        }

        $.ajax({
            type : 'POST',
            url : '/cms/getContentList',
            dataType : "json",
            data : param,
            success : function(result) {
                console.log("getContentList success");
                loadGridBannerList('search', result);
            },
            error: function(request, status, error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

            }
        })
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

    //팝업 오픈
    function showPop(pop, ctx = null) {
        const _target = document.getElementById(pop);

        /* 모달안에 인풋초기화 */
        _target.querySelectorAll("input, select").forEach((el, i) => {
            el.value = "";
        })

        _target.classList.add('is_on');

            switch (pop) {
                case "contentManagement":
                    _target.querySelector(".popup_title").textContent = "콘텐츠추가";
                    _target.querySelector("input[name='active_yn']").checked = true;
                    $(".opt_img").css("display", "none");
                    $(".opt_img").attr("src", '');
                    //  $('input[name="sales_to_dt"]').attr("min", _getFormatDate(new Date()));
                    //  $('input[name="sales_from_dt"]').attr("min", _getFormatDate(new Date()));
                    $('#new_content_cont_tb').css("display", "none");
                    $('.confirm').css("display", "block");
                    $('.fill').css("display", "none");
                    $('#delete').css("display", "none");
                    break;
                case "thumnailPopUp" :
                    const imgPath = contentGrid.collectionView.currentItem["thumbnail"];
                    let img = '<img class="content_img" src="'+imgPath+'"art="이미지" onerror="this.oneerror=null; this.src=`https://exitobiz.co.kr/img/app.png`">';
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
                case "contentManagement":
                    _target.querySelector(".popup_title").textContent = "콘텐츠수정"
                    _target.querySelector("input[name='title']").value = ctx.item.title;
                    _target.querySelector("input[name='id']").value = ctx.item.id;
                    _target.querySelector("input[name='url']").value = ctx.item.url;
                    _target.querySelector("input[name='sort']").value = ctx.item.sort;
                    _target.querySelector("input[name='cost']").value = ctx.item.cost;
                    _target.querySelector("input[name='activeYn']").checked = ctx.item.activeYn == 'Y' ? true : false;
                    if (ctx.item.thumbnail != null && ctx.item.thumbnail != '') {
                        $(".opt_img").css("display", "block")
                        $(".opt_img").attr("src", ctx.item.thumbnail)
                        $(".opt_img").attr("onerror", "this.onerror=null; this.src='https://exitobiz.co.kr/img/app.png';");
                    }
                    $('.confirm').css("display", "none");
                    $('.fill').css("display", "block");
                    $('#delete').css("display", "block");
                    break;
                default:
                    break;
            }
        }
    }
    /*팝업 추가, 수정 끝*/

    // function join() {
    //
    //     $.ajax({
    //         url: '/auth/one?userId=' + sessionStorage.getItem("userId"),
    //         type: 'POST',
    //         dataType: "json",
    //         success: function (data) {
    //             console.log("join>>> ", data);
    //             data.map((v, i) => {
    //                 document.getElementById("userId").value = v.user_id;
    //                 document.getElementById("password").value = v.password;
    //                 document.getElementById("name").value = v.name;
    //
    //                 document.getElementById("url").value = v.url;
    //                 if (v.url != null) document.getElementById("url").style.width = "100%";
    //                 if (v.url != null) document.getElementById("rg_btn").style.display = "none";
    //
    //                 //ckeditor 데이터 넣는거에요
    //                 if (v.content != null) _editor.setData(v.content);
    //
    //                 var str = v.sns_url;
    //                 let html = "";
    //
    //                 if (str != null) {
    //                     var words = str.split(',');
    //                     console.log(words);
    //
    //                     console.log(html);
    //
    //                     words.map((v, i) => {
    //                         html += '<div class="showTxtBox">' +
    //                             ' <p class="showTxt" style="margin-bottom: 0">' + v + '</p>' +
    //                             ' <button class="cls_btn" onclick="delete1(this)">x</button>' +
    //                             ' </div>';
    //                     })
    //                     console.log(html);
    //                     $("#sns_btn").parent().after(html);
    //                 }
    //
    //                 if (v.cover_img != null) document.getElementById("inner_img").src = "/auth/download?img=upload/img/" + v.cover_img;
    //                 if (v.cover_img != null) document.getElementById("inner_img").style.display = "inline-block";
    //                 if (v.cover_img != null) document.getElementById("inner_img").style.opacity = "1";
    //                 if (v.cover_img != null) document.querySelector(".file-edit-icon").style.opacity = "1";
    //                 if (v.cover_img != null) document.getElementById("image_preview").style.display = "block";
    //                 if (v.cover_img != null) document.getElementById("img").style.display = "none";
    //
    //                 document.getElementById("accountName").value = v.account_name;
    //                 if (v.bank != null) document.getElementById("bank").value = v.bank;
    //                 document.getElementById("accountNm").value = v.account_nm;
    //
    //                 if (v.qr_img != null) document.getElementById("qr_img").src = "/auth/download?img=upload/img/" + v.qr_img;
    //
    //                 if (v.qr_img != null) document.getElementById("qr_img").style.display = "inline-block";
    //                 if (v.qr_img != null) document.getElementById("qr_img").style.opacity = "1";
    //                 if (v.qr_img != null) document.getElementById("image_preview2").style.display = "block";
    //                 if (v.qr_img != null) document.querySelector(".file-edit-icon2").style.opacity = "1";
    //                 if (v.qr_img != null) document.getElementById("file3").style.display = "none";
    //
    //                 if (v.profile_img != null) document.getElementById("inner_img3").src = "/auth/download?img=upload/img/" + v.profile_img;
    //                 if (v.profile_img != null) document.getElementById("inner_img3").style.opacity = "1";
    //
    //                 if (v.url != null) {
    //                     document.getElementsByTagName("h1")[0].innerHTML = "나의 페이지 수정";
    //                     document.getElementById("submit").innerHTML = "저장";
    //                     document.getElementById("url_ms").innerHTML = "등록된 url 주소는 변경할 수 없습니다.";
    //                     document.getElementById("url").disabled = true;
    //                     urlChk = true;
    //                 }
    //             })
    //         },
    //         error: function () {
    //             if (request.status == 500 || request.status == 404) location.href = "/page/error";
    //         }
    //     })
    //
    // }


    //팝업 추가
    async function contentConfirm(type) {

        console.log("여긴가" + type);

        const f = document.getElementById("contentForm");
        const formData = new FormData(f);

        //validation
        if (f.corpCd.value == "") {
            alert("노출대상을 선택해주세요");
            return false;
        }

        // if (f.contentType.value == "") {
        //     alert("콘텐츠 유형을 선택해주세요");
        //     return false;
        // }

        if (f.title.value == "") {
            alert("제목을 입력해주세요");
            return false;
        }

        if (f.imgFile.value == "") {
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

        f.active.value = f.activeYn.checked ? 'Y' : 'N'

        // formData.append("id", f.id.value);
        // formData.append("contentType", f.contentType.value);
        // formData.append("exposureTarget", f.exposureTarget.value);
        // formData.append("title", f.title.value);
        // formData.append("url", f.url.value);
        // formData.append("activeYn", f.active.value);
        // formData.append("remark", f.remark.value);
        // formData.append("cost", f.cost.value);
        // formData.append("upFile", f.upFile.files[0]);

        for (let key of formData.keys()) {
            console.log(key);
        }

// FormData의 value 확인
        for (let value of formData.values()) {
            console.log(value);
        }



        switch (type) {
            case "add" :
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
            // case "modify" :
            //     if (!confirm("공지를 수정하시겠습니까?")) return false;
            //     await axios.put("/cms/content", formData, {headers: {'Content-Type': 'multipart/form-data'}})
            //         .then((res) => {
            //             console.log(res);
            //             if (res.status == 200) {
            //                 alert("공지수정을 완료했습니다.");
            //                 $('.popup').removeClass('is_on');
            //                 showGrid(document.searchForm);
            //             } else {
            //                 alert("오류가 발생했습니다. 다시 시도해 주세요.");
            //             }
            //         })
            //     break;
            // case "delete" :
            //     if (!confirm("공지를 삭제하시겠습니까?")) return false;
            //     await axios.delete("/cms/notice", {data: formData}, {headers: {'Content-Type': 'multipart/form-data'}})
            //         .then((res) => {
            //             console.log(res);
            //             if (res.status == 200) {
            //                 alert("공지삭제를 완료했습니다.");
            //                 $('.popup').removeClass('is_on');
            //                 showGrid(document.searchForm);
            //             } else {
            //                 alert("오류가 발생했습니다. 다시 시도해 주세요.");
            //             }
            //         })
            //     break;
        }
    }



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
        $('input[name="upFile"]').change(function () {
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
    })

    // function contentChange(e) {
    //     if (e.getAttribute("data-value") == '외부콘텐츠') {
    //         $('#new_content_url_tb').show();
    //         $('#new_content_cont_tb').hide();
    //         editor1.setData("");
    //     } else if (e.getAttribute("{headers") == '자체제작') {
    //         $('#new_content_url_tb').hide();
    //         $('#new_content_cont_tb').show();
    //         document.contentForm.url.value = "";
    //     }
    // }

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
        pageOnLoad();

        // ClassicEditor
        //     .create(document.querySelector('#editor'), {
        //         toolbar: {
        //             items: [
        //                 'heading',
        //                 '|',
        //                 'bold',
        //                 'italic',
        //                 'link',
        //                 'bulletedList',
        //                 'numberedList',
        //                 '|',
        //                 'outdent',
        //                 'indent',
        //                 '|',
        //                 'imageUpload',
        //                 'blockQuote',
        //                 'insertTable',
        //                 'mediaEmbed',
        //                 'undo',
        //                 'redo',
        //                 'htmlEmbed',
        //                 'horizontalLine',
        //                 'fontSize',
        //                 'fontColor',
        //                 'fontBackgroundColor',
        //                 'alignment',
        //             ],
        //             shouldNotGroupWhenFull: true
        //         },
        //         language: 'ko',
        //         image: {
        //             toolbar: [
        //                 'imageTextAlternative',
        //                 'imageStyle:inline',
        //                 'imageStyle:block',
        //                 'imageStyle:side'
        //             ]
        //         },
        //         table: {
        //             contentToolbar: [
        //                 'tableColumn',
        //                 'tableRow',
        //                 'mergeTableCells'
        //             ]
        //         },
        //         licenseKey: '',
        //     })
        //     .then(editor => {
        //         editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        //             return new UploadAdapter(loader);
        //         };
        //         editor1 = editor;
        //     })
        //     .catch(error => {
        //         console.error('Oops, something went wrong!');
        //         console.error('Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:');
        //         console.warn('Build id: eed83e2ex4oz-pejoxvy7ffif');
        //         console.error(error);
        //     });
    });
</script>
