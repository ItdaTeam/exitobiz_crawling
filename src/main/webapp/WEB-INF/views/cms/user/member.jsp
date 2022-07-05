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
                <h2 class="main_title"><span class="material-icons-round"> done_outline</span> 회원관리</h2>
                <!-- 탭 메뉴 -->
                <div class="main_tab">
                <div role="tablist" class="admin_tab">
                    <a class="tab on" id="tab_panel_member" href="javascript:tab_panel('panel_member','panel_staff');" role="tab" >APP회원관리</a>
                    <a class="tab" id="tab_panel_staff" href="javascript:tab_panel('panel_staff','panel_member');" role="tab">STAFF관리</a>
                </div>
                <!-- 탭 패널 : 회원관리 -->
                <div id="panel_member" role="tabpanel" class="tabpanel">
                    <div class="main_summary">
                        <dl>
                            <dt>전체 사용자</dt>
                            <dd><%=request.getAttribute("totalMemberCnt")%>명</dd>
                        </dl>
                        <dl>
                            <dt>금일 접속횟수</dt>
                            <dd><%=request.getAttribute("loginTodayCnt")%>회</dd>
                        </dl>
                        <dl>
                            <dt>금일 가입자</dt>
                            <dd><%=request.getAttribute("joinTodayCnt")%>명</dd>
                        </dl>
                        <dl>
                            <dt>MAU</dt>
                            <dd><%=request.getAttribute("loginMonthMemberCnt")%>명</dd>
                        </dl>
                        <dl>
                            <dt>DAU</dt>
                            <dd><%=request.getAttribute("loginTodayMemberCnt")%>명</dd>
                        </dl>
                        <dl>
                            <dt>비활성 유저</dt>
                            <dd><%=request.getAttribute("activeMemberCnt")%>명</dd>
                        </dl>
                    </div>
                    <div class="main_utility">
                        <div class="btn_wrap">
    <%--                        <button class="btn stroke"><span class="material-icons">attach_file</span>엑셀 템플릿</button>--%>
    <%--                        <button class="btn stroke"><span class="material-icons">upload</span>엑셀 업로드</button>--%>
                            <button class="btn stroke" onclick="exportExcel('member')"><span class="material-icons-outlined">file_download</span>엑셀 다운로드</button>
                        </div>
                    </div>
                    <div class="main_content">
                        <!-- 필터 영역 main_filter-->
                        <div class="main_filter">
                            <form action="#" onsubmit="return false;">
                                <label for="con1">검색조건</label>
                                <select name="con" id="con1">
                                    <option value="all" selected="selected">전체</option>
                                    <option value="id">ID</option>
                                    <option value="nickname">닉네임</option>
                                    <option value="age">연령대</option>
                                    <option value="gender">성별</option>
                                    <option value="os">모바일OS</option>
                                    <option value="mail">이메일</option>
                                </select>
                                <label for="inq1" onkeyup="enterkey();">조회</label>
                                <input type="text" name="inq" id="inq1" placeholder=",로 다중검색 가능" onkeyup="enterkey();">
                                <button type="button" class="main_filter_btn" onclick="getMemberList();"><i>조회</i></button>
                            </form>
                        </div>
                        <!-- 보드 영역 main_dashboard-->
                        <div class="main_dashboard">
                            <div class="sub_cont">
                                <div class="btn_wrap">
                                    <select name="viewType" id="viewType" onchange="getMemberList()">
                                        <option value="opt1" selected>전체</option>
                                        <option value="opt2">30일내 접속수순</option>
                                        <option value="opt3">금일 접속수순</option>
                                        <option value="opt4">접속자순</option>
                                        <option value="opt5">연령대 낮은순</option>
                                        <option value="opt6">연령대 높은순</option>
                                        <option value="opt7">버전순</option>ge
                                        <option value="opt8">관리자순</option>
                                        <option value="opt9">가입 과거순</option>
                                        <option value="opt10">가입 최신순</option>
                                    </select>
                                    <select name="viewNum" id="viewNum" onchange="getMemberList()">
                                        <option value="100" selected>100개씩</option>
                                        <option value="50">50개씩</option>
                                        <option value="30">30개씩</option>
                                    </select>
                                    <button type="button" class="btn stroke" onClick="_getUserGridLayout('memberLayout', appUserGrid);"><span class="material-icons-outlined">view_list</span>칼럼위치저장</button>
                                    <button type="button" class="btn stroke" onClick="_resetUserGridLayout('memberLayout', appUserGrid,appUserColumns);"><span class="material-icons-outlined">restart_alt</span>칼럼초기화</button>
                                </div>
                            </div>
                            <div class="grid_wrap">
                                <div id="appUserGrid"></div>
                                <div id="appUserGridPager" class="pager"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="panel_staff" role="tabpanel" class="tabpanel">
                    <div class="main_summary">
                        <dl>
                            <dt>전체 사용자</dt>
                            <dd><%=request.getAttribute("totalMemberCnt")%>명</dd>
                        </dl>
                        <dl>
                            <dt>관리자</dt>
                            <dd><%=request.getAttribute("totalStaffCnt")%>명</dd>
                        </dl>
                        <!-- 클릭시 팝업창 띄움 -->
                        <a href="javascript:void(0);" onclick="showPop('new_staff')"><i></i>관리자 추가</a>
                    </div>
                    <div class="main_utility">
                        <div class="btn_wrap">
                            <button class="btn stroke" onclick="exportExcel('staff');">엑셀다운로드</button>
                        </div>
                    </div>
                    <div class="main_content">
                        <!-- 필터 영역 main_filter-->
                        <div class="main_filter">
                            <form action="#" onsubmit="return false;">
                                <label for="con2">검색조건</label>
                                <select name="con" id="con2">
                                    <option value="all" selected="selected">전체</option>
                                    <option value="name">이름</option>
                                    <option value="id">ID</option>
                                </select>
                                <label for="inq2" onkeyup="enterkey();">조회</label>
                                <input type="text" name="inq" id="inq2" placeholder=",로 다중검색 가능" onkeyup="enterkey();">
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
    var token = localStorage.getItem("jwt");
    var appUserGrid;
    var appUserView;
    var appUserGridPager;
    var appUserColumns;
    var staffGrid;
    var staffView;
    var staffColumns;
    var staffGridPager;
    var dupCheckIdFlag;

    function pageOnLoad(){
        document.getElementById("member").classList.add("active");
        loadGridUserList('init');
        tab_panel('member','staff');
        // sessionCheck(staffId);
    }

    function enterkey() {
        if (window.event.keyCode == 13) {
            getMemberList();
        }
    }

    function tab_panel(showTab, hideTab){
        $('#'+showTab).css("display","block");
        $('#'+hideTab).css("display","none");

        $('#tab_'+showTab).addClass("on");
        $('#tab_'+hideTab).removeClass("on");
    }

    //그리드 초기 셋팅
    function loadGridUserList(type, result){
        if(type == "init"){
            appUserView = new wijmo.collections.CollectionView(result, {
                pageSize: 100
            });

            appUserGridPager = new wijmo.input.CollectionViewNavigator('#appUserGridPager', {
                byPage: true,
                headerFormat: '{currentPage:n0} / {pageCount:n0}',
                cv: appUserView
            });

            var onoffYnMap = "N,Y".split(",");	//온/오프라인 콤보박스

            appUserColumns = [
                { binding: 'indexid', header: 'INDEX', isReadOnly: true, width: 100, align:"center" , visible: false },
                { binding: 'id', header: 'ID', isReadOnly: true, maxWidth: 100, align:"center" },
                { binding: 'usernickname', header: '닉네임/이름', isReadOnly: true, width: 100, align:"center"  },
                { binding: 'idprofile', header: '프로필사진', isReadOnly: true, width: 120, align:"center",
                    cellTemplate: wijmo.grid.cellmaker.CellMaker.makeImage({
                        click:(e,ctx) =>showPop("profile"),
                        attributes :{
                            onerror:
                                "this.onerror=null; this.src='https://www.exitobiz.co.kr/img/logo2.png';"
                        }
                    })
                },
                { binding: 'userbirthday', header: '생년월일', isReadOnly: true, width: 100, align:"center"  },
                { binding: 'usergender', header: '성별', isReadOnly: true, width: 100, align:"center"  },
                { binding: 'useragerange', header: '연령대', isReadOnly: true, width: 200, align:"center" },
                { binding: 'useremail', header: '이메일', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'username', header: '이름', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'userloc', header: '지역', isReadOnly: true, width: 200 , align:"center" },
                { binding: 'userlocname', header: '지역명', isReadOnly: true, width: 200 , align:"center" },
                { binding: 'usertitle', header: '직책', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'userhp', header: '전화번호', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'companyname', header: '회사명', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'companytype', header: '회사분류', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'companyloc', header: '회사지역', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'mos', header: '모바일OS', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'minfo', header: '모바일정보', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'checkFlag', header: '체크여부', width: 100 , align:"center", dataMap:onoffYnMap },
                { binding: 'adminFlag', header: '관리자여부', width: 100 , align:"center", dataMap:onoffYnMap },
                { binding: 'usertoken', header: '토큰', isReadOnly: true, visible:false, width: 100 , maxWidth: 200, align:"center", visible:false},
                { binding: 'lat', header: 'lat', isReadOnly: true, width: 200 , align:"center" },
                { binding: 'lng', header: 'lng', isReadOnly: true, width: 200 , align:"center" },
                { binding: 'sido', header: '시도', isReadOnly: true, width: 200 , align:"center" },
                { binding: 'sigugun', header: '시구군', isReadOnly: true, width: 200 , align:"center" },
                { binding: 'dongmyun', header: '동면', isReadOnly: true, width: 200 , align:"center" },
                { binding: 'jibun', header: '지번', isReadOnly: true, width: 200 , align:"center" },
                { binding: 'lastloc', header: '마지막접속지', isReadOnly: true, width: 200 , align:"center" },
                { binding: 'appversion', header: '앱버전', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'cretTime', header: '가입일', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'lastTime', header: '최근접속일', isReadOnly: true, width: 100 , align:"center" },
                { binding: 'logintodaycnt', header: '카운트', isReadOnly: true, width: 100 , align:"center", visible: false }
            ];

            appUserGrid = new wijmo.grid.FlexGrid('#appUserGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: appUserColumns,
                itemsSource: appUserView,
                //컬럼 길이 자동정렬
                loadedRows: function(s, e) {
                    s.autoSizeColumns();
                    for (var i =0; i<s.rows.length; i++){
                        var row = s.rows[i];
                        var item = row.dataItem;
                        if(item.activeFlag == 'N') {
                            row.cssClass = 'change_dup';
                        }
                        if(item.expectCloseFlag == 'Y') {
                            row.cssClass = 'change_close';
                        }
                    }
                },
                cellEditEnded: function (s,e) {
                    if(confirm("관리자권한을 변경하시겠습니까?")){
                        var col = s.columns[e.col];
                        if(col.binding == 'adminFlag') {
                            var params = {
                                adminFlag : e.getRow().dataItem.adminFlag,
                                id : e.getRow().dataItem.id
                            }
                            $.ajax({
                                url : "/cms/member/updateAdminFlag",
                                async : false, // 비동기모드 : true, 동기식모드 : false
                                cache : false,
                                type : 'POST',
                                dataType : 'text',
                                data: params,
                                success : function(result) {
                                    alert("수정 완료되었습니다.");
                                    getMemberList();
                                },
                                error : function(request,status,error) {
                                    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                                }
                            });
                        }
                    }
                }
            });

            appUserGrid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                    cell.textContent = (r + 1).toString();
                }
            };

            _setUserGridLayout('memberLayout', appUserGrid, appUserColumns);

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
                { binding: 'memo', header: '메모', isReadOnly: true, width: 200, align:"center" },
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

        }else if(type == 'staff'){
            staffView = new wijmo.collections.CollectionView(result, {
                pageSize: Number($('#staffGridPageCount').val())
            });
            staffGridPager.cv = staffView;
            staffGrid.itemsSource = staffView;
        }else {
            appUserView = new wijmo.collections.CollectionView(result, {
                pageSize: Number($('#viewNum').val())
            });
            appUserGridPager.cv = appUserView;
            appUserGrid.itemsSource = appUserView;
        }

        refreshPaging(staffGrid.collectionView.totalItemCount, 1, staffGrid, 'staffGrid');  // 페이징 초기 셋팅
        refreshPaging(appUserGrid.collectionView.totalItemCount, 1, appUserGrid, 'appUserGrid');  // 페이징 초기 셋팅

    }

    function getMemberList(){
        var param = {
            con 	: $('#con1').val()
            , inq 	: $('#inq1').val()
            , viewType : $('#viewType').val()
        };

        $.ajax({
            type : 'GET',
            url : '/allMember',
            dataType : null,
            data : param,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-AUTH-TOKEN",token);
            },
            success : function(result) {
                console.log("getMemberList success");
                loadGridUserList('search', result);
            },
            error: function(request, status, error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

            }
        });
    }

    function getStaffList(){
        var param = {
            con 	: $('#con2').val()
            , inq 	: $('#inq2').val()
        };

        $.ajax({
            type : 'GET',
            url : '/all-staff',
            dataType : null,
            data : param,
            success : function(result) {
                console.log("getStaffList success");
                loadGridUserList('staff', result);
            },
            error: function(request, status, error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

            }
        });
    }

    // 아이디 중복확인 dupCheckIdFlag
    function dupCheckId(){

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
            url : "/staff/dupCheckId",
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
                ,email	:	newStaffForm.mail.value
                ,memo	:	newStaffForm.memo.value
            }

            $.ajax({
                url : "/staff",
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
        }else if(!telRule.test(updateStaffForm.phoneNum.value)){  // 전화번호
            alert("전화번호를 올바르게 입력하시기 바랍니다. \n전화번호는 '-'없이 숫자 11자리이어야 합니다.' \n예)01012341234");
            updateStaffForm.phoneNum.focus();
            return false;
        }

        var params = {
            activeYn 	: (updateStaffForm.active.checked ? 'Y' : 'N' )
            , id 		: updateStaffForm.id.value
            , password 	: updateStaffForm.password.value
            , name 		: updateStaffForm.name.value
            , phoneNum 	: updateStaffForm.phoneNum.value
            , email 	: updateStaffForm.mail.value
            , memo 		: updateStaffForm.memo.value
        }

        $.ajax({
            url : "/staff",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'PUT',
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
                url : '/staff',
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

    function exportExcel(grid){
        if(grid == 'member'){
            var gridView = appUserGrid.collectionView;
            var oldPgSize = gridView.pageSize;
            var oldPgIndex = gridView.pageIndex;

            //전체 데이터를 엑셀다운받기 위해서는 페이징 제거 > 엑셀 다운 > 페이징 재적용 하여야 함.
            appUserGrid.beginUpdate();
            appUserView.pageSize = 0;

            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(appUserGrid, {includeCellStyles: true, includeColumnHeaders: true}, 'UserList.xlsx',
                saved => {
                    gridView.pageSize = oldPgSize;
                    gridView.moveToPage(oldPgIndex);
                    appUserGrid.endUpdate();
                }, null
            );
        }else {
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

    $(document).ready(function() {
        pageOnLoad();
    });
</script>
