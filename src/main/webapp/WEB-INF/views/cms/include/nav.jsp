<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
    $(document).ready(function(){
        var staffId = "<%=session.getAttribute("staffId")%>";
        sessionCheck(staffId);
    });
</script>
<aside class="main_sidebar">
    <h1 class="logo">EXITO</h1>
    <ul class="main_nav">
        <li><a href="/cms/main" id="main" class="dept1"><span class="material-icons">home</span>홈 화면</a></li>
        <li>
            <a href="/cms/member" id="member" class="dept1"><span class="material-icons-round">person</span>회원관리</a>
        </li>
        <li>
            <a href="/cms/support" id="support" class="dept1"><span class="material-icons">dvr</span>지원사업 관리</a>
        </li>
        <li>
            <a href="/cms/support/agency" id="agency" class="dept1"><span class="material-icons">domain_add</span>지원기관 관리</a>
        </li>
        <li>
            <a href="/cms/banner" id="banner" class="dept1"><span class="material-icons-round">report</span>배너 관리</a>
        </li>
        <li>
            <a href="/cms/content" id="content" class="dept1"><span class="material-icons-round">campaign</span>콘텐츠 관리</a>
        </li>
        <li>
            <a href="/cms/popup" id="popup" class="dept1"><span class="material-icons-round">campaign</span>팝업관리</a>
        </li>
        <li>
            <a href="/cms/notice" id="notice" class="dept1"><span class="material-icons-round">campaign</span>공지관리</a>
        </li>
        <li>
            <a href="/cms/terms" id="terms" class="dept1"><span class="material-icons-round">summarize</span>약관관리</a>
        </li>
<%--        <li>--%>
<%--            <a href="/cms/corp/support" id="business" class="dept1"><span class="material-icons-round">work</span>사업공고 관리</a>--%>
<%--        </li>--%>
    </ul>
    <div class="terms_area">
        <a href="/standard">이용약관</a>
        <a href="/personinfo">개인정보처리약관</a>
        <a href="/locationinfo">위치기반서비스이용약관</a>
        <a href="/marketingInfo">마케팅정보수신동의</a>
    </div>
    <a href="javascript:_logOut()" class="main_out" data-hover="exit"><span class="material-icons-outlined">logout</span> 관리자모드 나가기</a>
</aside>
