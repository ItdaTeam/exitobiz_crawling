<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/header.jsp" %>
    <script src="/js/common.js"></script>
    <script src="../ckeditor5/build/ckeditor.js"></script>
    <link rel="stylesheet" type="text/css" href="../ckeditor5/sample/styles.css">
</head>
<body>
<div class="main_wrap">
    <%@ include file="../include/nav.jsp" %>
    <div class="main_container">
        <section class="main_section">
            <h2 class="main_title"><span class="material-icons-round"> done_outline</span> 공지관리</h2>
            <div class="main_summary">
                <dl>
                    <dt>공지 수</dt>
                    <dd>${totalCnt}개</dd>
                </dl>
                <dl>
                    <dt>필독 공지수</dt>
                    <dd>${mustCnt}개</dd>
                </dl>
                <!-- 클릭시 팝업창 띄움 -->
                <a href="javascript:void(0);" onclick="showPop('noticeManagement')"><i></i>공지 추가</a>
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
                        <input type="hidden" name="limit">
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
                                    onclick="_getUserGridLayout('noticeLayout', noticeGrid);">칼럼위치저장
                            </button>
                            <button type="button" class="btn stroke"
                                    onclick="_resetUserGridLayout('noticeLayout', noticeGrid, noticeColumns);">칼럼초기화
                            </button>
                        </div>
                    </div>
                    <div class="grid_wrap" style="position:relative;">
                        <div id="noticeGrid"></div>
                        <div id="noticeGridPager" class="pager"></div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <!-- 팝업 -->
    <!-- 팝업 : 팝업 추가-->
    <div class="popup" id="noticeManagement">
        <div class="popup_container">
            <div class="popup_head">
                <p class="popup_title">공지추가</p>
                <button type="button" class="popup_close">x</button>
            </div>
            <div class="popup_inner">
                <dfn>필수항목 <i>*</i></dfn>
                <form action="" id="noticeForm" name="noticeForm" onsubmit="return false;">
                    <input type="hidden" id="id" name="id">
                    <table>
                        <tbody>
                        <tr>
                            <th>공지 활성화<i>*</i></th>
                            <td>
                                <input type="hidden" id="active" name="active">
                                <input type="checkbox" id="noticeYnCheck" name="active_yn" checked>
                                <label for="noticeYnCheck">체크 시, 활성화</label>
                            </td>
                        </tr>
                        <tr>
                            <th>필독 활성화<i>*</i></th>
                            <td>
                                <input type="hidden" id="must" name="must">
                                <input type="checkbox" id="mustYnCheck" name="must_yn" checked>
                                <label for="mustYnCheck">체크 시, 활성화</label>
                            </td>
                        </tr>
                        <tr>
                            <th>제목<i>*</i></th>
                            <td>
                                <input type="text" id="title" name="title" style="width:300px;">
                            </td>
                        </tr>
                        <tr>
                            <th>내용<i>*</i></th>
                            <td id="modifyEditorWrap">
                                <textarea id="editor" name="editor"></textarea>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="popup_btn_area">
                        <button class="btn confirm" onclick="noticeConfirm('add')">추가</button>
                        <button style="display:none;" class="btn fill" onclick="noticeConfirm('modify')">수정</button>
                        <button style="display:none;" id="delete" type="button" onclick="noticeConfirm('delete')" class="btn stroke">삭제</button>
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
    var noticeGrid;
    var noticeView;
    var noticeColumns;
    var noticeGridPager;
    let editor1;

    function pageOnLoad() {
        calDate();
        document.getElementById("notice").classList.add("active");
        loadGridNoticeList('init');
    }

    //그리드 초기 셋팅
    function loadGridNoticeList(type, result) {
        if (type == "init") {
            noticeView = new wijmo.collections.CollectionView(result, {
                pageSize: 100
            });

            noticeGridPager = new wijmo.input.CollectionViewNavigator('#noticeGridPager', {
                byPage: true,
                headerFormat: '{currentPage:n0} / {pageCount:n0}',
                cv: noticeView
            });

            var onoffYnMap = "N,Y".split(",");	//온/오프라인 콤보박스

            noticeColumns = [
                {binding: 'id', header: '공지번호', isReadOnly: true, width: 150, align: "center"},
                {binding: 'title', header: '제목', isReadOnly: true, width: 150, align: "center"},
                {binding: 'createAt', header: '작성날짜', isReadOnly: true, width: 150, align: "center"},
                {binding: 'updateAt', header: '수정날짜', isReadOnly: true, width: 150, align: "center"},
                {binding: 'contents', header: '컨텐츠', isReadOnly: true, width: 150, align: "center", visible: false},
                {binding: 'mustYn', header: '필독활성화', isReadOnly: false, width: 150, align: "center", dataMap: onoffYnMap},
                {binding: 'activeYn', header: '공지활성화', isReadOnly: false, width: 150, align: "center", dataMap: onoffYnMap},
                {
                    binding: 'edit', header: '정보수정', isReadOnly: true, width: 150, align: "center",
                    cellTemplate: wijmo.grid.cellmaker.CellMaker.makeButton({
                        text: '조회',
                        click: (e, ctx) => showPop('noticeManagement', ctx),
                    })
                },
            ];

            noticeGrid = new wijmo.grid.FlexGrid('#noticeGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: noticeColumns,
                itemsSource: noticeView
            });

            noticeGrid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                    cell.textContent = (r + 1).toString();
                }
            };

            _setUserGridLayout('noticeLayout', noticeGrid, noticeColumns);


        } else {
            noticeView = new wijmo.collections.CollectionView(result, {
                pageSize: Number($('#viewNum').val())
            });
            noticeGridPager.cv = noticeView;
            noticeGrid.itemsSource = noticeView;
        }

        refreshPaging(noticeGrid.collectionView.totalItemCount, 1, noticeGrid, 'noticeGrid');  // 페이징 초기 셋팅
    }

    const getData = async (form) => {

        try{
            return await axios.get("/cms/notice/api",{
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
                loadGridNoticeList('search', res.data)
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

        /* 모달안에 값 있을 경우 */
        if (ctx != null) {
            switch (pop) {
                case "noticeManagement":
                    _target.querySelector(".popup_title").textContent = "공지수정"
                    _target.querySelector("input[name='title']").value = ctx.item.title;
                    _target.querySelector("input[name='id']").value = ctx.item.id;
                    _target.querySelector("input[name='active_yn']").checked = ctx.item.activeYn == 'Y' ? true : false;
                    _target.querySelector("input[name='must_yn']").checked = ctx.item.mustYn == 'Y' ? true : false;
                    editor1.setData(ctx.item.contents);
                    $('.confirm').css("display","none");
                    $('.fill').css("display","block");
                    $('#delete').css("display","block");
                    break;
                default:
                    break;
            }
        }else {
            switch (pop) {
                case "noticeManagement":
                    _target.querySelector(".popup_title").textContent = "공지추가"
                    _target.querySelector("input[name='active_yn']").checked = true;
                    _target.querySelector("input[name='must_yn']").checked = true;
                    editor1.setData("");
                    $('.confirm').css("display","block");
                    $('.fill').css("display","none");
                    $('#delete').css("display","none");
                    break;
                default:
                    break;
            }
        }
    }

    async function noticeConfirm(type) {

        const f = document.getElementById("noticeForm");

        if (f.title.value == "") {
            alert("제목을 입력해주세요");
            return false;
        }

        if(editor1.getData() == "") {
            alert("내용을 입력해주세요");
            return false;
        }

        f.active.value = f.active_yn.checked ? 'Y' : 'N'
        f.must.value = f.must_yn.checked ? 'Y' : 'N'

        const formData = new FormData();

        formData.append("id", f.id.value);
        formData.append("title", f.title.value);
        formData.append("activeYn", f.active.value);
        formData.append("mustYn", f.must.value);
        formData.append("contents", editor1.getData());

        switch (type) {
            case "add" : if(!confirm("공지를 추가하시겠습니까?")) return false;
                await axios.post("/cms/notice", formData, {headers: {'Content-Type': 'multipart/form-data'}})
                    .then((res) => {
                        console.log(res);
                        if (res.status == 200) {
                            alert("공지추가을 완료했습니다.");
                            $('.popup').removeClass('is_on');
                            showGrid(document.searchForm);
                        } else {
                            alert("오류가 발생했습니다. 다시 시도해 주세요.");
                        }
                    })
                break;
            case "modify" : if(!confirm("공지를 수정하시겠습니까?")) return false;
                await axios.put("/cms/notice", formData, {headers: {'Content-Type': 'multipart/form-data'}})
                    .then((res) => {
                        console.log(res);
                        if (res.status == 200) {
                            alert("공지수정을 완료했습니다.");
                            $('.popup').removeClass('is_on');
                            showGrid(document.searchForm);
                        } else {
                            alert("오류가 발생했습니다. 다시 시도해 주세요.");
                        }
                    })
                break;
            case "delete" : if(!confirm("공지를 삭제하시겠습니까?")) return false;
                await axios.delete("/cms/notice", {data : formData}, {headers: {'Content-Type': 'multipart/form-data'}})
                    .then((res) => {
                        console.log(res);
                        if (res.status == 200) {
                            alert("공지삭제를 완료했습니다.");
                            $('.popup').removeClass('is_on');
                            showGrid(document.searchForm);
                        } else {
                            alert("오류가 발생했습니다. 다시 시도해 주세요.");
                        }
                    })
                break;
        }
    }

    //엑셀다운로드
    function exportExcel(){

        var gridView = noticeGrid.collectionView;
        var oldPgSize = gridView.pageSize;
        var oldPgIndex = gridView.pageIndex;

        //전체 데이터를 엑셀다운받기 위해서는 페이징 제거 > 엑셀 다운 > 페이징 재적용 하여야 함.
        noticeGrid.beginUpdate();
        noticeView.pageSize = 0;

        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(noticeGrid, {includeCellStyles: true, includeColumnHeaders: true}, '공지리스트.xlsx',
            saved => {
                gridView.pageSize = oldPgSize;
                gridView.moveToPage(oldPgIndex);
                noticeGrid.endUpdate();
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
        ClassicEditor
            .create( document.querySelector( '#editor' ), {
                toolbar: {
                    items: [
                        'heading',
                        '|',
                        'bold',
                        'italic',
                        'link',
                        'bulletedList',
                        'numberedList',
                        '|',
                        'outdent',
                        'indent',
                        '|',
                        'imageUpload',
                        'blockQuote',
                        'insertTable',
                        'mediaEmbed',
                        'undo',
                        'redo',
                        'htmlEmbed',
                        'horizontalLine',
                        'fontSize',
                        'fontColor',
                        'fontBackgroundColor',
                        'alignment',
                    ],
                    shouldNotGroupWhenFull: true
                },
                language: 'ko',
                image: {
                    toolbar: [
                        'imageTextAlternative',
                        'imageStyle:inline',
                        'imageStyle:block',
                        'imageStyle:side'
                    ]
                },
                table: {
                    contentToolbar: [
                        'tableColumn',
                        'tableRow',
                        'mergeTableCells'
                    ]
                },
                licenseKey: '',
            } )
            .then( editor => {
                editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
                    return new UploadAdapter(loader);
                };
                editor1 = editor;
            } )
            .catch( error => {
                console.error( 'Oops, something went wrong!' );
                console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
                console.warn( 'Build id: eed83e2ex4oz-pejoxvy7ffif' );
                console.error( error );
            } );
    });
</script>
