<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/header.jsp" %>
</head>
<body>
    <div class="main_wrap">
        <%@ include file="../include/nav.jsp" %>
        <div class="main_container">
            <section class="main_section">
                <h2 class="main_title">관리자관리</h2>
                <div class="main_summary">
                    <dl>
                        <dt>전체 사용자</dt>
                        <dd><%=request.getAttribute("totalUser")%>명</dd>
                    </dl>
                    <dl>
                        <dt>관리자</dt>
                        <dd><%=request.getAttribute("totalStaff")%>명</dd>
                    </dl>
                    <!-- 클릭시 팝업창 띄움 -->
                    <a href="javascript:void(0);" class="popup_trigger" onclick="showPop('new_staff');"><i></i>관리자 추가</a>
                </div>
                <div class="main_utility">
                    <div class="btn_wrap">
                        <button class="btn stroke" onclick="exportExcel();">엑셀다운로드</button>
                    </div>
                </div>
                <div class="main_content">
                    <!-- 필터 영역 main_filter-->
                    <div class="main_filter">
                        <form action="#" onsubmit="return false;">
                            <label for="con">검색조건</label>
                            <select name="con" id="con">
                                <option value="all" selected="selected">전체</option>
                                <option value="name">이름</option>
                                <option value="id">ID</option>
                            </select>
                            <label for="inq" onkeyup="enterkey();">조회</label>
                            <input type="text" name="inq" id="inq" placeholder=",로 다중검색 가능" onkeyup="enterkey();">
                            <button type="button" class="main_filter_btn" onclick="getStaffList();"><i>조회</i></button>
                        </form>
                    </div>
                    <!-- 보드 영역 main_dashboard-->
                    <div class="main_dashboard">
                        <div class="sub_cont">
                            <div class="btn_wrap">
                                <select id="staffGridPageCount" name="viewNum" onchange="getStaffList()">
                                    <option value="100" selected="selected">100개씩</option>
                                    <option value="50">50개씩</option>
                                    <option value="30">30개씩</option>
                                </select>
                                <button type="button" class="btn stroke" onclick="_getUserGridLayout('staffLayout', staffGrid);">칼럼위치저장</button>
                                <button type="button" class="btn stroke" onclick="_resetUserGridLayout('staffLayout', staffGrid, staffColumns);">칼럼초기화</button>
                            </div>
                        </div>
                        <div class="grid_wrap" style="position:relative;">
                            <div id="staffGrid"></div>
                            <div id="staffGridPager" class="pager"></div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <!-- 팝업 -->
        <!-- 팝업 : 관리자추가 추가-->
        <div class="popup" id="new_staff">
            <div class="popup_container">
                <div class="popup_head">
                    <p class="popup_title">관리자추가</p>
                    <button type="button" class="popup_close" onclick="closePop();">x</button>
                </div>
                <div class="popup_inner">
                    <dfn>필수항목 <i>*</i></dfn>
                    <form id="newStaffForm" action="#" method="post" onsubmit="return false;">
                        <table>
                            <tbody>
                                <tr>
                                    <th>아이디<i>*</i></th>
                                    <td>
                                        <input type="text" class="icon" id="new_identity" name="id">
                                        <button class="btn att" onclick="dupCheckId();">중복확인</button>
                                    </td>
                                </tr>
                                <tr>
                                    <th>비밀번호<i>*</i></th>
                                    <td>
                                        <input type="password" class="icon" id="new_password" name="password" onchange="pwCheck();">
                                    </td>
                                </tr>
                                <tr>
                                    <th>비밀번호확인<i>*</i></th>
                                    <td>
                                        <input type="password" id="new_chck_pw" name="chck_pw" style="width:184px;" onchange="pwCheck();">
                                        <p id="valid_pw" style="display:none; color:#F44A5A; font-size:0.9em; line-height:2;">비밀번호가 일치하지 않습니다</p>
                                    </td>
                                </tr>
                                <tr>
                                    <th>이름<i>*</i></th>
                                    <td>
                                        <input type="text" id="new_name" name="name">
                                    </td>
                                </tr>
                                <tr>
                                    <th>전화번호<i>*</i></th>
                                    <td>
                                        <input type="text" id="new_telPhone" name="phoneNum">
                                    </td>
                                </tr>
                                <tr>
                                    <th>이메일</th>
                                    <td>
                                        <input type="text" id="new_mail" name="mail">
                                    </td>
                                </tr>
                                <tr>
                                    <th>메모</th>
                                    <td>
                                        <textarea name="memo" id="new_memo" cols="30" rows="10"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                    <div class="popup_btn_area">
                        <button type="button" class="btn confirm" onclick="saveNewStaff();">추가</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 관리자추가 팝업 영역 끝-->
        <!-- 팝업 : 정보 수정 -->
        <div class="popup" id="modify_staff">
            <div class="popup_container">
                <div class="popup_head">
                    <p class="popup_title">정보수정</p>
                    <button type="button" class="popup_close" onclick="closePop();">x</button>
                </div>
                <div class="popup_inner">
                    <dfn>필수항목 <i>*</i></dfn>
                    <form id="updateStaffForm" action="#" method="post" onsubmit="return false;">
                        <table>
                            <tbody>
                                <tr>
                                    <th>활성화</th>
                                    <td>
                                        <input type="checkbox" id="active" name="active" checked>
                                        <label for="active">체크 시, 활성화</label>
                                    </td>
                                </tr>
                                <tr>
                                    <th>아이디<i>*</i></th>
                                    <td>
                                        <input type="text" class="icon" id="identity" name="id" onfocus="this.blur();" readonly >
                                    </td>
                                </tr>
                                <tr>
                                    <th>비밀번호<i>*</i></th>
                                    <td>
                                        <input type="password" class="icon" id="password" name="password">
                                    </td>
                                </tr>
                                <tr>
                                    <th>비밀번호확인<i>*</i></th>
                                    <td>
                                        <input type="password" id="chck_pw" name="chck_pw" style="width:184px;">
                                    </td>
                                </tr>
                                <tr>
                                    <th>이름<i>*</i></th>
                                    <td>
                                        <input type="text" id="name" name="name">
                                    </td>
                                </tr>
                                <tr>
                                    <th>전화번호<i>*</i></th>
                                    <td>
                                        <input type="text" id="telPhone" name="phoneNum">
                                    </td>
                                </tr>
                                <tr>
                                    <th>이메일</th>
                                    <td>
                                        <input type="text" id="mail" name="mail">
                                    </td>
                                </tr>
                                <tr>
                                    <th>메모</th>
                                    <td>
                                        <textarea id="memo" name="memo" id="" cols="30" rows="10"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                    <div class="popup_btn_area">
                        <button type="button" class="btn stroke" onclick="updateStaff();">수정</button>
                        <button type="button" class="btn fill" onclick="deleteStaff();">삭제</button>
                    </div>
                </div>
            </div>
        </div>
        <!--정보수정 팝업 영역 끝-->
        </div>
</body>
</html>
<script>
var staffGrid;
var staffView;
var staffColumns;
var staffGridPager;
var dupCheckIdFlag;
var staffId = "<%=session.getAttribute("staffId")%>";

function pageOnLoad(){
    $("#user").addClass("current");
    $("#admin").addClass("current");
    loadGridStaffList('init');
    enterkey();
    // sessionCheck(staffId);
}

function enterkey() {
    if (window.event.keyCode == 13) {
        getStaffList();
    }
}

//그리드 초기 셋팅
function loadGridStaffList(type, result){
    if(type == "init"){
        staffView = new wijmo.collections.CollectionView(result, {
            pageSize: 100
        });

        staffGridPager = new wijmo.input.CollectionViewNavigator('#staffGridPager', {
            byPage: true,
            headerFormat: '{currentPage:n0} / {pageCount:n0}',
            cv: staffView
        });

        staffColumns = [
            { binding: 'name', header: '이름', isReadOnly: true, width: 100, align:"center" },
            { binding: 'id', header: 'ID', isReadOnly: true, width: 100, align:"center"  },
            { binding: 'activeYn', header: '활성화', isReadOnly: true, width: 60, align:"center"  },
            { binding: 'phoneNum', header: '전화번호', isReadOnly: true, width: 120, align:"center"  },
            { binding: 'email', header: '이메일', isReadOnly: true, width: 200, align:"center"  },
            { binding: 'memo', header: '메모', isReadOnly: true, width: 400, align:"center" },
            { binding: 'lastestDt', header: '최근접속일', isReadOnly: true, width: 100 , align:"center" },
            { binding: 'cretDt', header: '계정생성일', isReadOnly: true, width: 100 , align:"center" },
            { binding: 'edit', header: '정보수정', width: 100, align:"center",
                cellTemplate: wijmo.grid.cellmaker.CellMaker.makeButton({
                    text: '<b>수정</b>',
                    click: (e, ctx) => {
                        showPop('modify_staff');
                    }
                })
            }
        ];

        staffGrid = new wijmo.grid.FlexGrid('#staffGrid', {
            autoGenerateColumns: false,
            alternatingRowStep: 0,
            columns: staffColumns,
            itemsSource: staffView
        });

        staffGrid.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                cell.textContent = (r + 1).toString();
            }
        };

        _setUserGridLayout('staffLayout', staffGrid, staffColumns);

    }else{
        staffView = new wijmo.collections.CollectionView(result, {
            pageSize: Number($('#staffGridPageCount').val())
        });
        staffGridPager.cv = staffView;
        staffGrid.itemsSource = staffView;
    }

    refreshPaging(staffGrid.collectionView.totalItemCount, 1, staffGrid, 'staffGrid');  // 페이징 초기 셋팅

}

function exportExcel(){
    var gridView = staffGrid.collectionView;
    var oldPgSize = gridView.pageSize;
    var oldPgIndex = gridView.pageIndex;

    //전체 데이터를 엑셀다운받기 위해서는 페이징 제거 > 엑셀 다운 > 페이징 재적용 하여야 함.
    staffGrid.beginUpdate();
    staffView.pageSize = 0;

    wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(staffGrid, {includeCellStyles: true, includeColumnHeaders: true}, 'StaffList.xlsx',
        saved => {
            gridView.pageSize = oldPgSize;
            gridView.moveToPage(oldPgIndex);
            staffGrid.endUpdate();
        }, null
    );
}

function getStaffList(){
    var param = {
        con 	: $('#con').val()
        , inq 	: $('#inq').val()
    };

    $.ajax({
        type : 'GET',
        url : '/cms/allStaff',
        dataType : null,
        data : param,
        success : function(result) {
            console.log("getStaffList success");
            loadGridStaffList('search', result);
        },
        error: function(request, status, error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

        }
    });
}

//팝업 오픈
function showPop(pop){
    if(pop == "new_staff"){
        dupCheckIdFlag = false;

        newStaffForm.id.value = "";
        newStaffForm.password.value = "";
        newStaffForm.chck_pw.value = "";
        newStaffForm.name.value = "";
        newStaffForm.phoneNum.value = "";
        newStaffForm.mail.value = "";
        newStaffForm.memo.value = "";

    }else if(pop == "modify_staff"){

        updateStaffForm.active.checked = (staffGrid.collectionView.currentItem["activeYn"] == 'Y' ? true : false );
        updateStaffForm.id.value = staffGrid.collectionView.currentItem["id"];
        updateStaffForm.password.value = "";
        updateStaffForm.chck_pw.value = "";
        updateStaffForm.name.value = staffGrid.collectionView.currentItem["name"];
        updateStaffForm.phoneNum.value = staffGrid.collectionView.currentItem["phoneNum"];
        updateStaffForm.mail.value = staffGrid.collectionView.currentItem["email"];
        updateStaffForm.memo.value = staffGrid.collectionView.currentItem["memo"];

    }

    $('#'+pop).addClass('is_on');
}

//팝업 종료
function closePop(){
    $('.popup').removeClass('is_on');
}

//직원추가
function saveNewStaff(){
    //필수값 체크
    if(newStaffForm.id.value == ""){
        alert("ID를 입력해주세요.");
        newStaffForm.id.focus();
        return false;

    }else if(newStaffForm.password.value == ""){
        alert("비밀번호를 입력해주세요.");
        newStaffForm.password.focus();
        return false;

    }else if(newStaffForm.name.value == ""){
        alert("이름을 입력해주세요.");
        newStaffForm.name.focus();
        return false;

    }else if(newStaffForm.phoneNum.value == ""){
        alert("전화번호를 입력해주세요.");
        newStaffForm.phoneNum.focus();
        return false;
    }else if(newStaffForm.password.value != newStaffForm.chck_pw.value){
        $('#valid_pw').show();
        return false;
    }

    //벨리데이션 체크
    var idRule1  = /^(?=.*[A-Za-z])[A-Za-z\d]{6,}$/;
    var idRule2  = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
    var pwdRule1  = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{10,}$/;
    var pwdRule2  = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
    var pwdRule3  = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    var telRule   = /^[0-9]{11}$/;

    if(!idRule1.test(newStaffForm.id.value) && !idRule2.test(newStaffForm.id.value)){
        alert("ID를 확인하시기 바랍니다.\nID는 영문자(대,소문자) 6자 이상 혹은 \n영문자(대,소문자), 숫자를 포함하여 6자 이상이어야 합니다.");
        newStaffForm.password.focus();
        return false;
    }else if(!pwdRule1.test(newStaffForm.password.value) && !pwdRule2.test(newStaffForm.password.value) && !pwdRule3.test(newStaffForm.password.value)){
        alert("비밀번호를 확인하시기 바랍니다.\n비밀번호는 영문자(대,소문자), 숫자를 포함하여 최소 10자 이상,\n 혹은 영문자(대,소문자), 숫자, 특수문자를 포함하여 최소 8자 이상이어야 합니다.");
        newStaffForm.password.focus();
        return false;
    }else if(newStaffForm.mail.value != null && newStaffForm.mail.value != "" && !emailRule.test(newStaffForm.mail.value)){ //이메일
        alert("이메일을 확인하시기 바랍니다.");
        newStaffForm.mail.focus();
        return false;
    }else if(!telRule.test(newStaffForm.phoneNum.value)){  // 전화번호
        alert("전화번호를 올바르게 입력하시기 바랍니다. \n전화번호는 '-'없이 숫자 11자리이어야 합니다.' \n예)01012341234");
        newStaffForm.phoneNum.focus();
        return false;
    }

    //중복확인
    if(!dupCheckIdFlag){
        alert('중복확인을 해주세요.');
        return false;

    }else{
        var params = {
            id 		:	newStaffForm.id.value
            ,password:	newStaffForm.password.value
            ,name	:	newStaffForm.name.value
            ,phoneNum:	newStaffForm.phoneNum.value
            ,mail	:	newStaffForm.mail.value
            ,memo	:	newStaffForm.memo.value
        }

        $.ajax({
            url : "/cms/saveNewStaff",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'POST',
            cache : false,
            dataType : 'text',
            data : params,
            success : function(data) {
                alert("직원 생성이 완료되었습니다.");
                closePop();
                getStaffList();
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }
}

// 아이디 중복확인 dupCheckIdFlag
function dupCheckId(){

    console.log(newStaffForm.id.value);
    var idRule1  = /^(?=.*[A-Za-z])[A-Za-z\d]{6,}$/;
    var idRule2  = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;

    if(newStaffForm.id.value == ""){
        alert("아이디를 입력하세요.");
        return false;
    }
    if(!idRule1.test(newStaffForm.id.value) && !idRule2.test(newStaffForm.id.value)){
        alert("ID를 확인하시기 바랍니다.\nID는 영문자(대,소문자) 6자 이상 혹은 \n영문자(대,소문자), 숫자를 포함하여 6자 이상이어야 합니다.");
        newStaffForm.id.focus();
        return false;
    }

    var param = {
        id : newStaffForm.id.value
    }

    $.ajax({
        url : "/object/dupCheckId",
        async : false, // 비동기모드 : true, 동기식모드 : false
        type : 'GET',
        cache : false,
        dataType : 'text',
        data : param,
        success : function(data) {
            if(data != ""){
                alert('이미 존재하는 아이디입니다.');
                dupCheckIdFlag = false;
            }else{
                alert('사용가능한 아이디입니다.');
                dupCheckIdFlag = true;
            }
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}

function updateStaff(){
    //필수값 체크
    if(updateStaffForm.name.value == ""){
        alert("이름을 입력해주세요.");
        updateStaffForm.name.focus();
        return false;

    }else if(updateStaffForm.phoneNum.value == ""){
        alert("전화번호를 입력해주세요.");
        updateStaffForm.phoneNum.focus();
        return false;

    }else if(updateStaffForm.password.value != updateStaffForm.chck_pw.value){
        $('#valid_pw').show();
        return false;

    }

    //벨리데이션 체크
    var pwdRule1  = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{10,}$/;
    var pwdRule2  = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
    var pwdRule3  = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    var telRule   = /^[0-9]{11}$/;

    if(updateStaffForm.password.value != '' && !pwdRule1.test(updateStaffForm.password.value) && !pwdRule2.test(updateStaffForm.password.value) && !pwdRule3.test(updateStaffForm.password.value)){
        alert("비밀번호를 확인하시기 바랍니다.\n비밀번호는 영문자(대,소문자), 숫자를 포함하여 최소 10자 이상,\n 혹은 영문자(대,소문자), 숫자, 특수문자를 포함하여 최소 8자 이상이어야 합니다.");
        updateStaffForm.password.focus();
        return false;
    }else if(updateStaffForm.mail.value != null && updateStaffForm.mail.value != "" && !emailRule.test(updateStaffForm.mail.value)){ //이메일
        alert("이메일을 확인하시기 바랍니다.");
        updateStaffForm.mail.focus();
        return false;
    }else if(!telRule.test(updateStaffForm.phoneNum.value)){  // 전화번호
        alert("전화번호를 올바르게 입력하시기 바랍니다. \n전화번호는 '-'없이 숫자 11자리이어야 합니다.' \n예)01012341234");
        updateStaffForm.phoneNum.focus();
        return false;
    }

    var params = {
        active 		: (updateStaffForm.active.checked ? 'Y' : 'N' )
        , id 		: updateStaffForm.id.value
        , password 	: updateStaffForm.password.value
        , name 		: updateStaffForm.name.value
        , phoneNum 	: updateStaffForm.phoneNum.value
        , mail 		: updateStaffForm.mail.value
        , memo 		: updateStaffForm.memo.value
    }

    $.ajax({
        url : "/object/updateStaff",
        async : false, // 비동기모드 : true, 동기식모드 : false
        type : 'POST',
        cache : false,
        dataType : 'text',
        data : params,
        success : function(data) {
            alert('정상적으로 수정되었습니다.');
            closePop();
            getStaffList();
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}

function deleteStaff(){
    if(confirm("삭제하시겠습니까?")){
        var params = {
            id : updateStaffForm.id.value
        };

        $.ajax({
            url : '/object/deleteStaff',
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'DELETE',
            cache : false,
            dataType : null,
            data : params,
            success : function(data) {
                alert('정상적으로 삭제되었습니다.');
                closePop();
                getStaffList();
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }
}

function pwCheck() {
    if($('#new_password').val() != $('#new_chck_pw').val()) {
        $('#valid_pw').show();
    }else {
        $('#valid_pw').hide();
    }
}

$(document).ready(function() {
    pageOnLoad();
});
</script>