<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/header.jsp" %>
    <style>
        .wj-lineargauge .wj-value {
            fill: black;
        }
    </style>
</head>
<body>
    <div class="main_wrap">
        <%@ include file="../include/nav.jsp" %>
        <div class="main_container">
            <section class="main_section">
                <h2 class="main_title"><span class="material-icons-round"> done_outline</span> 엑시토 금일 현황</h2>
                <div class="main_summary dashboard">
                    <dl>
                        <dt>전체 유저</dt>
                        <dd>
                            <p>${dashBoard.totalCnt}명</p>
                            <p class="indicator" id="totalPer" name="dd"></p>
                        </dd>

                    </dl>
                    <dl>
                        <dt>금일 접속자</dt>
                        <dd>
                            <p>${dashBoard.inCnt}명</p>
                            <p class="indicator" id="inPer"></p>
                        </dd>
                    </dl>
                    <dl>
                        <dt>금일 가입자</dt>
                        <dd>
                            <p>${dashBoard.joinCnt}명</p>
                            <p class="indicator" id="joinPer"></p>
                        </dd>
                    </dl>
                    <dl>
                        <dt>비활성 유저</dt>
                        <dd>
                            <p>${dashBoard.inActiveCnt}명</p>
                            <p class="indicator"><span class="material-icons-outlined" style="color:rgb(93, 93, 93);">minimize</span></p>
                        </dd>
                    </dl>
                    <dl>
                        <dt>금일 클릭 수</dt>
                        <dd>
                            <p>${dashBoard.clickCnt}회</p>
                            <p class="indicator" id="clickPer"></p>
                        </dd>
                    </dl>
<%--                    <dl>--%>
<%--                        <dt>금일 평균 체류시간</dt>--%>
<%--                        <dd>--%>
<%--                            <p>00초</p>--%>
<%--                            <p class="indicator"><span class="material-icons-outlined" style="color:rgb(255, 0, 0);">arrow_drop_up</span>3%</p>--%>
<%--                        </dd>--%>
<%--                    </dl>--%>
                    <dl>
                        <dt>금일 크롤링 수</dt>
                        <dd>
                            <p>${dashBoard.crawlingCnt}개</p>
                            <p class="indicator" id="crawlingPer"></p>
                        </dd>
                    </dl>
                    <dl>
                        <dt>금일 크롤링 처리수</dt>
                        <dd>
                            <p>${dashBoard.activeCrawlingCnt}개</p>
                            <p class="indicator"><span class="material-icons-outlined" style="color:rgb(93, 93, 93);">minimize</span></p>
<%--                            <p class="indicator"><span class="material-icons-outlined" style="color:rgb(255, 0, 0);">arrow_drop_up</span></p>--%>
                        </dd>
                    </dl>
                </div>
                <div class="main_box">
                    <div class="box" style="flex-basis:100%;">
                        <div class="sub_cont">
                            <h3 class="title">DAU + 일 클릭수</h3>
                            <form action="#" class="fr" method="post" onsubmit="return false;">
                                <input type="date" id="fromDauDate" onchange="showChart('dauChart')">
                                -
                                <input type="date" id="toDauDate" onchange="showChart('dauChart')">
                            </form>
                        </div>
                        <div class="cont">
                            <div id="dauChart" style="clear:both; width: 80vw;">
                            </div>
                        </div>
                    </div>
                    <div class="box">
                        <div class="sub_cont">
                            <h3 class="title">WAU + 주 누적 클릭수</h3>
                            <form action="#" method="post" style="margin-top:12px; " onsubmit="return false;">
                                <input type="week" id="fromWauDate" onchange="showChart('wauChart')">
                                -
                                <input type="week" id="toWauDate" onchange="showChart('wauChart')">
                            </form>
                        </div>
                        <div class="cont">
                            <div style="clear:both;">
                                <div id="wauChart" style="width: 35vw;"></div>
                            </div>
                        </div>
                    </div>
                    <div class="box">
                        <div class="sub_cont">
                            <h3 class="title">MAU + 월 누적 클릭수</h3>
                            <form action="#" method="post" style="margin-top:12px;" onsubmit="return false;">
                                <input type="month" id="fromMauDate" onchange="showChart('mauChart')">
                                -
                                <input type="month" id="toMauDate" onchange="showChart('mauChart')">
                            </form>
                        </div>
                        <div class="cont">
                            <div style="clear:both;">
                                <div id="mauChart" style="width: 35vw;"></div>
                            </div>
                        </div>
                    </div>
                    <div class="box chrt">
                        <div class="group">
                            <h4>전전주 대비</h4>
                            <div>
                                <strong>유저수</strong>
                                <span id="beforeLastWeekTotalPer"></span>
                                <span id="beforeLastWeekTotalCnt"></span>
                            </div>
                            <div>
                                <strong>클릭수</strong>
                                <span id="beforeLastWeekClickPer"></span>
                                <span id="beforeLastWeekClickCnt"></span>
                            </div>
                        </div>
                        <div class="group">
                            <h4>전주 대비</h4>
                            <div>
                                <strong>유저수</strong>
                                <span id="lastWeekTotalPer"></span>
                                <span id="lastWeekTotalCnt"></span>
                            </div>
                            <div>
                                <strong>클릭수</strong>
                                <span id="lastWeekClickPer"></span>
                                <span id="lastWeekClickCnt"></span>
                            </div>
                        </div>
                    </div>
                    <div class="box chrt">
                        <div class="group">
                            <h4>전전월 대비</h4>
                            <div>
                                <strong>유저수</strong>
                                <span id="beforeLastMonthTotalPer"></span>
                                <span id="beforeLastMonthTotalCnt"></span>
                            </div>
                            <div>
                                <strong>클릭수</strong>
                                <span id="beforeLastMonthClickCnt"></span>
<%--                                <span><span class="material-icons-outlined" style="color:rgb(255, 0, 0);">arrow_drop_up</span>${dashBoard.beforeLastMonthClickPer}%</span>--%>
                            </div>
                        </div>
                        <div class="group">
                            <h4>전월 대비</h4>
                            <div>
                                <strong>유저수</strong>
                                <span id="lastMonthTotalPer"></span>
                                <span id="lastMonthTotalCnt"></span>
                            </div>
                            <div>
                                <strong>클릭수</strong>
                                <span id="lastMonthClickPer"></span>
                                <span id="lastMonthClickCnt"></span>
                            </div>
                        </div>
                        <div class="bar">
                            <h4>월 목표 달성률 <button class="btn att" onclick="showPop('goal_popup')">입력</button> </h4>
                            <div style="margin-top:30px; width: 150%;">
                                <div id="theGauge"></div>
                            </div>
                        </div>

                    </div>
                    <div class="box table">
                        <div class="sub_cont">
                            <h3 class="title">접속률 TOP 유저</h3>
                            <button class="btn fr" onclick="showPop('user_pop')">전체보기</button>
                        </div>
                        <table class="rank">
                            <tr>
                                <th></th>
                                <th>닉네임</th>
                                <th>접속수</th>
                                <th>클릭수</th>
                            </tr>
                            <c:forEach var="user" items="${topUser}" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${user.usernickname}</td>
                                    <td>${user.inCnt}회</td>
                                    <td>${user.clickCnt}회</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="box table">
                        <div class="sub_cont">
                            <h3 class="title">메인화면 버튼 클릭수 최하위 (3개월)</h3>
                            <button class="btn fr" onclick="showPop('click_popup')">전체보기</button>
                        </div>
                        <table class="rank">
                            <tr>
                                <th></th>
                                <th colspan="2">지원사업</th>
                                <th colspan="2">공급기업</th>
                            </tr>
                            <c:forEach var="page" items="${lowPage}" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${page.supPage}</td>
                                    <td>${page.supCnt}회</td>
                                    <td>${page.proPage}</td>
                                    <td>${page.proCnt}회</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </section>
        </div>
    </div>
    <!-- 팝업 -->
    <!-- 팝업 : 접속률 TOP 유저 -->
    <div class="popup" id="user_pop">
        <div class="popup_container wide">
            <div class="popup_head">
                <p class="popup_title">접속률 TOP 유저</p>
                <button type="button" class="popup_close">x</button>
            </div>
            <div class="popup_inner">
                <div class="sub_cont">
                    <form onsubmit="return false;">
                        <select name="userGridPageCount" id="userGridPageCount" onchange="getAccessUserList();">
                            <option value="20">20</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                            <option value="200">200</option>
                        </select>
                        <select name="con" id="con">
                            <option value="all" selected="selected">전체</option>
                            <option value="identity">ID</option>
                            <option value="nickname">닉네임</option>
                            <option value="locate">최종위치</option>
                        </select>
                        <input type="text" id="inq" name="inq" placeholder=",로 다중검색 가능" onkeyup="enterkey();">
                        <button type="button" class="main_filter_btn" onclick="getAccessUserList();"><i>검색</i></button>
                    </form>
                    <div class="fr">
                        <form onsubmit="return false;" >
                            <label for="fromUserDate">기간조회</label>
                            <input type="date" id="fromUserDate" onchange="getAccessUserList()"> -
                            <input type="date" id="toUserDate" onchange="getAccessUserList()">
                        </form>
                        <button class="btn stroke fr" style="width:120px; margin-left:20px;" onclick="exportExcel();"><span class="material-icons-outlined">file_download</span>엑셀 다운로드</button>
                    </div>
                </div>
                <div class="grid_wrap">
                    <div id="userGrid"></div>
                    <div id="userGridPager" class="pager"></div>
                </div>
            </div>
        </div>
    </div>
     <!-- 접속률 TOP 유저 팝업영역 끝 -->
     <!-- 팝업 : 팝업 탭 -->
    <div class="popup" id="click_popup">
        <div class="popup_container wide">
            <div class="popup_head">
                <p class="popup_title tablist">
                    <a class="on" data-id="tabpannel1">지원사업</a>
                    <a  data-id="tabpannel2">시제품 제작</a>
                </p>
                <button type="button" class="popup_close">x</button>
            </div>
            <div class="popup_inner">
                <div class="tabpanel" id="tabpannel1">
                    <div class="sub_cont">
                        <span>메인화면 버튼클릭수 최하위 (3개월)</span>
                        <form onsubmit="return false;" method="post" class="fr">
                            <input type="date" id="fromSupDate" onchange="getSupBtnList()"> -
                            <input type="date" id="toSupDate" onchange="getSupBtnList()">
                        </form>
                    </div>
                    <div class="flex_wrap">
                        <div class="lt"><img src="../img/img_dashboard1.png" alt="" style="height: 100%"></div>
                        <div class="rt">
                            <table id="supBtnList">
                            </table>
                        </div>
                    </div>

                </div>
                <div class="tabpanel" id="tabpannel2">
                    <div class="sub_cont">
                        <span>메인화면 버튼클릭수 최하위 (3개월)</span>
                        <form onsubmit="return false;" method="post" class="fr">
                            <input type="date" id="fromProDate" onchange="getProBtnList()"> -
                            <input type="date" id="toProDate" onchange="getProBtnList()">
                        </form>
                    </div>
                    <div class="flex_wrap">
                        <div class="lt"><img src="../img/img_dashboard2.png" alt="" style="height: 100%"></div>
                        <div class="rt">
                            <table id="proBtnList">
                            </table>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
     <!-- 팝업 탭 팝업영역 끝 -->
     <!-- 팝업 : 월 목표 달성률 입력 -->
    <div class="popup" id="goal_popup">
        <div class="popup_container">
            <div class="popup_head">
                <p class="popup_title">월 목표 달성률 입력</p>
                <button type="button" class="popup_close">x</button>
            </div>
            <div class="popup_inner">
                <div class="flex_wrap">
                    <div>
                        <table id="goal_tb1">
                        </table>
                    </div>
                    <div>
                        <table id="goal_tb2">
                        </table>
                    </div>
                </div>
                <div class="popup_btn_area">
                    <button type="button" class="btn confirm" onclick="updateGoal();">저장</button>
                </div>
            </div>
        </div>
    </div>
     <!-- 월 목표 달성률 입력 팝업영역 끝 -->
</body>
</html>
<script>
    var lastWeekTotalCnt = ${dashBoard.lastWeekTotalCnt}; //전주
    var lastWeekClickCnt = ${dashBoard.lastWeekClickCnt};
    var beforeLastWeekTotalCnt = ${dashBoard.beforeLastWeekTotalCnt}; //전전주
    var beforeLastWeekClickCnt = ${dashBoard.beforeLastWeekClickCnt};
    var lastMonthTotalCnt = ${dashBoard.lastMonthTotalCnt}; //전월
    var lastMonthClickCnt = ${dashBoard.lastMonthClickCnt};
    var beforeLastMonthTotalCnt = ${dashBoard.beforeLastMonthTotalCnt}; //전전월
    var beforeLastMonthClickCnt = ${dashBoard.beforeLastMonthClickCnt};
    var lastWeekTotalPer = ${dashBoard.lastWeekTotalPer}; //전주
    var lastWeekClickPer = ${dashBoard.lastWeekClickPer};
    var beforeLastWeekTotalPer = ${dashBoard.beforeLastWeekTotalPer}; //전전주
    var beforeLastWeekClickPer = ${dashBoard.beforeLastWeekClickPer};
    var lastMonthTotalPer = ${dashBoard.lastMonthTotalPer}; //전월
    var lastMonthClickPer = ${dashBoard.lastMonthClickPer};
    var beforeLastMonthTotalPer = ${dashBoard.beforeLastMonthTotalPer}; //전전월
    var totalPer = ${dashBoard.totalPer};
    var inPer = ${dashBoard.inPer};
    var joinPer = ${dashBoard.joinPer};
    var clickPer = ${dashBoard.clickPer};
    var crawlingPer = ${dashBoard.crawlingPer};
    var lastWeekTotalCnt; //전주
    var lastWeekClickCnt;
    var goal = ${dashBoard.goal};
    var userView;
    var userGrid;
    var userColumns;
    var userGridPager;
    let dauChart;
    let wauChart;
    let dmauChart;

    //퍼센트 인디케이터 계산
    function calPer(_data){
        var drop_down = "<span class='material-icons-outlined' style='color: rgb(0,0,255)'>arrow_drop_down</span>";
        var drop_up = "<span class='material-icons-outlined' style='color: rgb(255,0,0)'>arrow_drop_up</span>";

        if(String(totalPer).substring(0,1) == '-'){
            $('#totalPer').append(drop_down + String(totalPer).substring(1,totalPer.length) + '%');
        }else {
            $('#totalPer').append(drop_up + String(totalPer) + '%');
        }
        if(String(inPer).substring(0,1) == '-'){
            $('#inPer').append(drop_down + String(inPer).substring(1,inPer.length) + '%');
        }else {
            $('#inPer').append(drop_up + String(inPer) + '%');
        }
        if(String(joinPer).substring(0,1) == '-'){
            $('#joinPer').append(drop_down + String(joinPer).substring(1,joinPer.length) + '%');
        }else {
            $('#joinPer').append(drop_up + String(joinPer) + '%');
        }
        if(String(clickPer).substring(0,1) == '-'){
            $('#clickPer').append(drop_down + String(clickPer).substring(1,clickPer.length) + '%');
        }else {
            $('#clickPer').append(drop_up + String(clickPer) + '%');
        }
        if(String(crawlingPer).substring(0,1) == '-'){
            $('#crawlingPer').append(drop_down + String(crawlingPer).substring(1,crawlingPer.length) + '%');
        }else {
            $('#crawlingPer').append(drop_up + String(crawlingPer) + '%');
        }
        if(String(lastWeekTotalCnt).substring(0,1) == '-'){
            $('#lastWeekTotalCnt').append(drop_down + String(lastWeekTotalCnt).substring(1,lastWeekTotalCnt.length) + '명');
            $('#lastWeekTotalPer').append(drop_down + + String(lastWeekTotalPer).substring(1,lastWeekTotalPer.length) + '%');
        }else {
            $('#lastWeekTotalCnt').append(drop_up + String(lastWeekTotalCnt) + '명');
            $('#lastWeekTotalPer').append(drop_up + String(lastWeekTotalPer) + '%');
        }
        if(String(lastWeekClickCnt).substring(0,1) == '-'){
            $('#lastWeekClickCnt').append(drop_down + String(lastWeekClickCnt).substring(1,lastWeekClickCnt.length) + '회');
            $('#lastWeekClickPer').append(drop_down + + String(lastWeekClickPer).substring(1,lastWeekClickPer.length) + '%');
        }else {
            $('#lastWeekClickCnt').append(drop_up + String(lastWeekClickCnt) + '회');
            $('#lastWeekClickPer').append(drop_up + String(lastWeekClickPer) + '%');
        }
        if(String(beforeLastWeekTotalCnt).substring(0,1) == '-'){
            $('#beforeLastWeekTotalCnt').append(drop_down + String(beforeLastWeekTotalCnt).substring(1,beforeLastWeekTotalCnt.length) + '명');
            $('#beforeLastWeekTotalPer').append(drop_down + + String(beforeLastWeekTotalPer).substring(1,beforeLastWeekTotalPer.length) + '%');
        }else {
            $('#beforeLastWeekTotalCnt').append(drop_up + String(beforeLastWeekTotalCnt) + '명');
            $('#beforeLastWeekTotalPer').append(drop_up + String(beforeLastWeekTotalPer) + '%');
        }
        if(String(beforeLastWeekClickCnt).substring(0,1) == '-'){
            $('#beforeLastWeekClickCnt').append(drop_down + String(beforeLastWeekClickCnt).substring(1,beforeLastWeekClickCnt.length) + '회');
            $('#beforeLastWeekClickPer').append(drop_down + + String(beforeLastWeekClickPer).substring(1,beforeLastWeekClickPer.length) + '%');
        }else {
            $('#beforeLastWeekClickCnt').append(drop_up + String(beforeLastWeekClickCnt) + '회');
            $('#beforeLastWeekClickPer').append(drop_up + String(beforeLastWeekClickPer) + '%');
        }
        if(String(lastMonthTotalCnt).substring(0,1) == '-'){
            $('#lastMonthTotalCnt').append(drop_down + String(lastMonthTotalCnt).substring(1,lastMonthTotalCnt.length) + '명');
            $('#lastMonthTotalPer').append(drop_down + + String(lastMonthTotalPer).substring(1,lastMonthTotalPer.length) + '%');
        }else {
            $('#lastMonthTotalCnt').append(drop_up + String(lastMonthTotalCnt) + '명');
            $('#lastMonthTotalPer').append(drop_up + String(lastMonthTotalPer) + '%');
        }
        if(String(lastMonthClickCnt).substring(0,1) == '-'){
            $('#lastMonthClickCnt').append(drop_down + String(lastMonthClickCnt).substring(1,lastMonthClickCnt.length) + '회');
            $('#lastMonthClickPer').append(drop_down + + String(lastMonthClickPer).substring(1,lastMonthClickPer.length) + '%');
        }else {
            $('#lastMonthClickCnt').append(drop_up + String(lastMonthClickCnt) + '회');
            $('#lastMonthClickPer').append(drop_up + String(lastMonthClickPer) + '%');
        }
        if(String(beforeLastMonthTotalCnt).substring(0,1) == '-'){
            $('#beforeLastMonthTotalCnt').append(drop_down + String(beforeLastMonthTotalCnt).substring(1,beforeLastMonthTotalCnt.length) + '명');
            $('#beforeLastMonthTotalPer').append(drop_down + + String(beforeLastMonthTotalPer).substring(1,beforeLastMonthTotalPer.length) + '%');
        }else {
            $('#beforeLastMonthTotalCnt').append(drop_up + String(beforeLastMonthTotalCnt) + '명');
            $('#beforeLastMonthTotalPer').append(drop_up + String(beforeLastMonthTotalPer) + '%');
        }
        if(String(beforeLastMonthClickCnt).substring(0,1) == '-'){
            $('#beforeLastMonthClickCnt').append(drop_down + String(beforeLastMonthClickCnt).substring(1,beforeLastMonthClickCnt.length) + '회');
        }else {
            $('#beforeLastMonthClickCnt').append(drop_up + String(beforeLastMonthTotalCnt) + '회');
        }
    }

    function pageOnLoad(){
        $("#main").addClass("active");
        enterkey();
        calDau();
        calMau();
        calWau();
        calPer();
        chartInit();
        loadGridUserList('init');
        // sessionCheck(staffId);
    }

    function enterkey() {
        if (window.event.keyCode == 13) {
            getAccessUserList();
        }
    }

    function chartInit(){
        dauChart = new wijmo.chart.FlexChart('#dauChart',{
            header : 'DAU',
            legend : {
                position : wijmo.chart.Position.Bottom
            },
            bindingX : 'date',
            series : [
                {
                    binding: 'totalCnt',
                    name: '접속회원수'
                },
                {
                    binding: 'joinCnt',
                    name: '신규회원수'
                },
                {
                    binding: 'clickCnt',
                    name: '클릭수',
                    chartType: wijmo.chart.ChartType.Line
                }
            ],
            stacking: wijmo.chart.Stacking.Stacked,
            axisY: {
                axisLine: true,
                max: 1000,
                title: '회원수'
            },
            itemsSource: getDauChart()
        });

        new wijmo.chart.animation.ChartAnimation(dauChart);

        let axisY2 = new wijmo.chart.Axis();
        axisY2.position = wijmo.chart.Position.Right;
        axisY2.axisLine = true;
        axisY2.min = 0;
        axisY2.title = "클릭수";
        dauChart.series[2].axisY = axisY2;

        wauChart = new wijmo.chart.FlexChart('#wauChart',{
            header : 'WAU',
            legend : {
                position : wijmo.chart.Position.Bottom
            },
            bindingX : 'date',
            series : [
                {
                    binding: 'totalCnt',
                    name: '접속회원수'
                },
                {
                    binding: 'joinCnt',
                    name: '신규회원수'
                },
                {
                    binding: 'clickCnt',
                    name: '클릭수',
                    chartType: wijmo.chart.ChartType.Line
                }
            ],
            stacking: wijmo.chart.Stacking.Stacked,
            axisY: {
                axisLine: true,
                max: 2500,
                title: '회원수'
            },
            itemsSource: getWauChart()
        });

        new wijmo.chart.animation.ChartAnimation(wauChart);

        let axisY3 = new wijmo.chart.Axis();
        axisY3.position = wijmo.chart.Position.Right;
        axisY3.axisLine = true;
        axisY3.min = 0;
        axisY3.title = "클릭수";
        wauChart.series[2].axisY = axisY3;

        mauChart = new wijmo.chart.FlexChart('#mauChart',{
            header : 'MAU',
            legend : {
                position : wijmo.chart.Position.Bottom
            },
            bindingX : 'date',
            series : [
                {
                    binding: 'totalCnt',
                    name: '기존회원수'
                },
                {
                    binding: 'joinCnt',
                    name: '신규회원수'
                },
                {
                    binding: 'clickCnt',
                    name: '클릭수',
                    chartType: wijmo.chart.ChartType.Line
                }
            ],
            stacking: wijmo.chart.Stacking.Stacked,
            axisY: {
                axisLine: true,
                max: 5000,
                title: '회원수'
            },
            itemsSource: getMauChart()
        })

        new wijmo.chart.animation.ChartAnimation(mauChart);

        let axisY4 = new wijmo.chart.Axis();
        axisY4.position = wijmo.chart.Position.Right;
        axisY4.axisLine = true;
        axisY4.min = 0;
        axisY4.title = "클릭수";
        mauChart.series[2].axisY = axisY4;

        // create the gauge
        let theGauge = new wijmo.gauge.LinearGauge('#theGauge', {
            isReadOnly: false,
            min: 0,
            max: 100,
            step: 1,
            value: goal,
            valueChanged: (s, e) => value.value = s.value,
            showRanges: false,
            isReadOnly: true,
            direction: 'Right',
            showText: 'All',
            ranges: [
                { min: 0, max: 33, color: 'red' },
                { min: 33, max: 66, color: 'yellow' },
                { min: 66, max: 100, color: 'green' },
            ]
        });
    }

    function showChart(_chart){
        if(_chart == 'dauChart'){
            dauChart.itemsSource = getDauChart();
        }else if(_chart == 'wauChart'){
            wauChart.itemsSource = getWauChart();
        }else {
            mauChart.itemsSource = getMauChart();
        }
    }

    //그리드 초기 셋팅
    function loadGridUserList(type, result){
        if(type == "init"){
            userView = new wijmo.collections.CollectionView(result, {
                pageSize: 100
            });

            userGridPager = new wijmo.input.CollectionViewNavigator('#userGridPager', {
                byPage: true,
                headerFormat: '{currentPage:n0} / {pageCount:n0}',
                cv: userView
            });

            userColumns = [
                { binding: 'id', header: 'ID', isReadOnly: true, width: 100, align:"center"  },
                { binding: 'usernickname', header: '닉네임', isReadOnly: true, width: 100, align:"center" },
                { binding: 'useremail', header: '이메일', isReadOnly: true, width: 180, align:"center" },
                { binding: 'inCnt', header: '접속수', isReadOnly: true, width: 60, align:"center"  },
                { binding: 'clickCnt', header: '클릭수', isReadOnly: true, width: 120, align:"center"  },
                { binding: 'lastTime', header: '최근접속일', isReadOnly: true, width: 200, align:"center"  },
                { binding: 'lastloc', header: '최근위치', isReadOnly: true, width: 200, align:"center" },
            ];

            userGrid = new wijmo.grid.FlexGrid('#userGrid', {
                autoGenerateColumns: false,
                alternatingRowStep: 0,
                columns: userColumns,
                itemsSource: userView
            });

            userGrid.itemFormatter = function (panel, r, c, cell) {
                if (panel.cellType == wijmo.grid.CellType.RowHeader) {
                    cell.textContent = (r + 1).toString();
                }
            };

            _setUserGridLayout('userLayout', userGrid, userColumns);

        }else{
            userView = new wijmo.collections.CollectionView(result, {
                pageSize: Number($('#userGridPageCount').val())
            });
            userGridPager.cv = userView;
            userGrid.itemsSource = userView;
        }

        refreshPaging(userGrid.collectionView.totalItemCount, 1, userGrid, 'userGrid');  // 페이징 초기 셋팅

    }

    function getDauChart() {
        var returnVal;

        var param = {
            fromDate : $('#fromDauDate').val(),
            toDate : $('#toDauDate').val()
        }

        $.ajax({
            url : "/cms/getDauChart",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'GET',
            dataType : null,
            data : param,
            success : function(result) {
                if(result.length > 0){
                    var item = [];

                    for(var i =0; i<result.length; i++){
                        item[i] = { date: result[i].date.substring(result[i].date.lastIndexOf("-")+1,result[i].date.length), clickCnt: Number(result[i].clickCnt),  joinCnt: Number(result[i].joinCnt), totalCnt: Number(result[i].totalCnt)};
                    }
                    returnVal = item;
                }
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });

        return returnVal;
    }

    function getWauChart() {
        var returnVal;

        var param = {
            fromDate : $('#fromWauDate').val().replace("W",""),
            toDate : $('#toWauDate').val().replace("W","")
        }

        $.ajax({
            url : "/cms/getWauChart",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'GET',
            dataType : null,
            data : param,
            success : function(result) {
                if(result.length > 0){
                    var item = [];

                    for(var i =0; i<result.length; i++){
                        item[i] = { date: result[i].date.substring(result[i].date.lastIndexOf("-")+1,result[i].date.length), clickCnt: Number(result[i].clickCnt),  joinCnt: Number(result[i].joinCnt), totalCnt: Number(result[i].totalCnt)};
                    }
                    returnVal = item;
                }
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });

        return returnVal;
    }

    function getMauChart() {
        var returnVal;

        var param = {
            fromDate : $('#fromMauDate').val(),
            toDate : $('#toMauDate').val()
        }

        $.ajax({
            url : "/cms/getMauChart",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'GET',
            dataType : null,
            data : param,
            success : function(result) {
                if(result.length > 0){
                    var item = [];

                    for(var i =0; i<result.length; i++){
                        item[i] = { date: result[i].date.substring(result[i].date.lastIndexOf("-")+1,result[i].date.length), clickCnt: Number(result[i].clickCnt),  joinCnt: Number(result[i].joinCnt), totalCnt: Number(result[i].totalCnt)};
                    }
                    returnVal = item;
                }
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });

        return returnVal;
    }

    function getAccessUserList(){
        var param = {
            fromDate : $('#fromUserDate').val(),
            toDate : $('#toUserDate').val(),
            con 	: $('#con').val(),
            inq 	: $('#inq').val()
        }

        $.ajax({
            url : "/cms/getAccessUserList",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'GET',
            dataType : null,
            data : param,
            success : function(result) {
                console.log("getUserList success");
                loadGridUserList('search', result);
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }

    function getSupBtnList() {
        $("#supBtnList").empty();

        var param = {
            fromDate : $('#fromSupDate').val(),
            toDate : $('#toSupDate').val()
        }

        $.ajax({
            url : "/cms/getSupButton",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'GET',
            dataType : null,
            data : param,
            success : function(result) {
                console.log(result);
                $.each(result,function(index,vo){
                    renderSupList(index+1, vo)
                })
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }

    let renderSupList = function(index,vo){
        let html = "<tr>"
            + "<td>"+index+"</td>"
            + "<td>"+vo.supPage+"</td>"
            + "<td>"+vo.supCnt+"</td>"
            + "<td>회</td>"
            + "</tr>"

        $("#supBtnList").append(html);
    }

    function getProBtnList() {
        $("#proBtnList").empty();

        var param = {
            fromDate : $('#fromProDate').val(),
            toDate : $('#toProDate').val()
        }

        $.ajax({
            url : "/cms/getProButton",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'GET',
            dataType : null,
            data : param,
            success : function(result) {
                console.log(result);
                $.each(result,function(index,vo){
                    renderProList(index+1, vo)
                })
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }

    let renderProList = function(index,vo){
        let html = "<tr>"
            + "<td>"+index+"</td>"
            + "<td>"+vo.proPage+"</td>"
            + "<td>"+vo.proCnt+"</td>"
            + "<td>회</td>"
            + "</tr>"

        $("#proBtnList").append(html);
    }

    function getGoalList() {
        $("#goal_tb1").empty();
        $("#goal_tb2").empty();

        $.ajax({
            url : "/cms/goal",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'GET',
            success : function(result) {
                console.log(result);
                $.each(result,function(index,vo){
                    renderGoalList(index+1, vo)
                })
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }

    let renderGoalList = function(index,vo){
        let html = "<tr>"
            + "<td id='month_"+vo.index+"' data-index='"+vo.index+"'>"+vo.month+"월</td>"
            + "<td><input type='text' id='goal_"+vo.index+"' value='"+vo.goal+"'>명</td>"
            + "</tr>"

        if(index < 7){
            $("#goal_tb1").append(html);
        }else {
            $("#goal_tb2").append(html);
        }
    }

    function updateGoal() {

        var goalList = [];
        var params;

        for(let i =0; i<13; i++){

            params = {
                month :$('#month_'+i).data("index"),
                goal :$('#goal_'+i).val()
            }

            goalList.push(params);
        }

        $.ajax({
            url : "/cms/goal",
            async : false, // 비동기모드 : true, 동기식모드 : false
            type : 'POST',
            contentType: 'application/json',
            data: JSON.stringify(goalList),
            success : function(result) {
                alert("저장이 완료되었습니다");
                closePop();
                location.reload();
            },
            error : function(request,status,error) {
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }

    function calDau(){
        var fromDate = new Date()
        fromDate.setDate(fromDate.getDate() - 30);
        var fromday = _getFormatDate(fromDate);
        var today = _getFormatDate(new Date());
        $('#fromDauDate').val(fromday);
        $('#toDauDate').val(today);
        $('#fromDauDate').attr('max',today);
        $('#toDauDate').attr('max',today);
        $('#fromUserDate').val(fromday);
        $('#toUserDate').val(today);
        $('#fromUserDate').attr('max',today);
        $('#toUserDate').attr('max',today);
    }

    function calMau(){
        var fromDate = new Date();
        fromDate.setMonth(fromDate.getMonth() - 5);
        var fromMonth = _getFormatDate(fromDate, 'm');
        var today = _getFormatDate(new Date(), 'm');
        $('#fromMauDate').val(fromMonth);
        $('#toMauDate').val(today);
        $('#fromMauDate').attr('max',today);
        $('#toMauDate').attr('max',today);
    }

    function calWau(){
        var fromDate = new Date();
        fromDate.setMonth(fromDate.getMonth() - 3);
        var fromWeek = _getFormatDate(fromDate, 'w');
        var today = _getFormatDate(new Date(), 'w');
        $('#fromWauDate').val(fromWeek);
        $('#toWauDate').val(today);
        $('#fromWauDate').attr('max',today);
        $('#toWauDate').attr('max',today);
    }

    function calBtnDt(){
        var fromDate = new Date()
        fromDate.setDate(fromDate.getDate() - 90);
        var fromday = _getFormatDate(fromDate);
        var today = _getFormatDate(new Date());
        $('#fromSupDate').val(fromday);
        $('#toSupDate').val(today);
        $('#fromSupDate').attr('max',today);
        $('#toSupDate').attr('max',today);
        $('#fromProDate').val(fromday);
        $('#toProDate').val(today);
        $('#fromProDate').attr('max',today);
        $('#toProDate').attr('max',today);
    }

    //팝업 오픈
    function showPop(pop){
        if(pop == "user_pop"){
            $('#inq').val("");
            $('#con').val("all").prop("selected", true);
            getAccessUserList();
        }else if(pop == "click_popup"){
            calBtnDt();
            getSupBtnList();
            getProBtnList();
        }else if(pop == "goal_popup"){
            getGoalList();
        }

        $('#'+pop).addClass('is_on');
    }

    //팝업 종료
    function closePop(){
        $('.popup').removeClass('is_on');
    }

    //엑셀다운로드
    function exportExcel(){
        var gridView = userGrid.collectionView;
        var oldPgSize = gridView.pageSize;
        var oldPgIndex = gridView.pageIndex;

        //전체 데이터를 엑셀다운받기 위해서는 페이징 제거 > 엑셀 다운 > 페이징 재적용 하여야 함.
        userGrid.beginUpdate();
        userView.pageSize = 0;

        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(userGrid, {includeCellStyles: true, includeColumnHeaders: true}, '유저접속률리스트.xlsx',
            saved => {
                gridView.pageSize = oldPgSize;
                gridView.moveToPage(oldPgIndex);
                userGrid.endUpdate();
            }, null
        );
    }

    $(document).ready(function() {
        pageOnLoad();
    });
</script>
