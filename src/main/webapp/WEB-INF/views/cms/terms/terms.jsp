<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/header.jsp" %>
    <script src="../ckeditor5/build/ckeditor.js"></script>
    <link rel="stylesheet" type="text/css" href="../ckeditor5/sample/styles.css">
</head>
<body>
<div class="main_wrap">
    <%@ include file="../include/nav.jsp" %>
        <div class="main_container">
            <section class="main_section">
                <h2 class="main_title">약관관리</h2>
                <!-- 탭 메뉴-->
                <div class="main_tab">
                     <!-- 탭 리스트-->
                     <div role="tablist" class="tablist">
                        <a class="tab on" data-id="terms1" role="tab">이용약관</a>
                        <a class="tab" data-id="terms2" role="tab">개인정보처리방침</a>
                         <a class="tab" data-id="terms3" role="tab">위치기반서비스약관</a>
                         <a class="tab" data-id="terms4" role="tab">마케팅정보수신동의</a>
                    </div>
                    <div class="tabcont">
                        <!-- 탭 패널 : 이용약관 .terms1-->
                        <div id="terms1" role="tabpanel" class="tabpanel on">
                            <div class="sub_cont">
                                <div class="btn_wrap">
                                    <button class="btn" onclick="saveTermService();">저장</button>
                                </div>
                            </div>
                            <div class="edt">
                                <textarea id="service" name="service"></textarea>
                            </div>
                        </div>
                        <!-- 탭 패널 : 개인정보처리방침 .terms2-->
                        <div id="terms2" role="tabpanel" class="tabpanel">
                            <div class="sub_cont">
                                <div class="btn_wrap">
                                    <button class="btn" onclick="saveTermPrivacy();">저장</button>
                                </div>
                            </div>
                            <div class="edt">
                                <textarea id="privacy" name="privacy"></textarea>
                            </div>
                        </div>
                        <!-- 탭 패널 : 위치기반서비스 .terms3-->
                        <div id="terms3" role="tabpanel" class="tabpanel">
                            <div class="sub_cont">
                                <div class="btn_wrap">
                                    <button class="btn" onclick="saveTermLocate();">저장</button>
                                </div>
                            </div>
                            <div class="edt">
                                <textarea id="locate" name="privacy"></textarea>
                            </div>
                        </div>
                        <!-- 탭 패널 : 마케팅정보수신동의 .terms4-->
                        <div id="terms4" role="tabpanel" class="tabpanel">
                            <div class="sub_cont">
                                <div class="btn_wrap">
                                    <button class="btn" onclick="saveTermMarketing();">저장</button>
                                </div>
                            </div>
                            <div class="edt">
                                <textarea id="marketing" name="privacy"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</body>
</html>
<script>
var termSerContent;
var termPriContent;
var termLocContent;
var termMarketContent;
<%--var staffId = "<%=session.getAttribute("staffId")%>";--%>

function pageOnLoad(){
    document.getElementById("terms").classList.add("active");
    getSerTerm();
    getPriTerm();
    getLocTerm();
    getMarketTerm();
    ClassicEditor
        .create( document.querySelector( '#service' ), {

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
                    'blockQuote',
                    'insertTable',
                    'undo',
                    'redo',
                    'fontColor',
                    'fontBackgroundColor',
                    'fontSize',
                    'underline',
                    'specialCharacters',
                    'horizontalLine',
                    'htmlEmbed'
                ]
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
            editor1 = editor;
            editor1.setData(termSerContent);



        } )
        .catch( error => {
            console.error( 'Oops, something went wrong!' );
            console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
            console.warn( 'Build id: eed83e2ex4oz-pejoxvy7ffif' );
            console.error( error );
        } );
    ClassicEditor
        .create( document.querySelector( '#privacy' ), {

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
                    'blockQuote',
                    'insertTable',
                    'undo',
                    'redo',
                    'fontColor',
                    'fontBackgroundColor',
                    'fontSize',
                    'underline',
                    'specialCharacters',
                    'horizontalLine',
                    'htmlEmbed'
                ]
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
            editor2 = editor;
            editor2.setData(termPriContent);



        } )
        .catch( error => {
            console.error( 'Oops, something went wrong!' );
            console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
            console.warn( 'Build id: eed83e2ex4oz-pejoxvy7ffif' );
            console.error( error );
        } );
    ClassicEditor
        .create( document.querySelector( '#locate' ), {

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
                    'blockQuote',
                    'insertTable',
                    'undo',
                    'redo',
                    'fontColor',
                    'fontBackgroundColor',
                    'fontSize',
                    'underline',
                    'specialCharacters',
                    'horizontalLine',
                    'htmlEmbed'
                ]
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
            editor3 = editor;
            editor3.setData(termLocContent);



        } )
        .catch( error => {
            console.error( 'Oops, something went wrong!' );
            console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
            console.warn( 'Build id: eed83e2ex4oz-pejoxvy7ffif' );
            console.error( error );
        } );
    
     ClassicEditor
        .create( document.querySelector( '#marketing' ), {

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
                    'blockQuote',
                    'insertTable',
                    'undo',
                    'redo',
                    'fontColor',
                    'fontBackgroundColor',
                    'fontSize',
                    'underline',
                    'specialCharacters',
                    'horizontalLine',
                    'htmlEmbed'
                ]
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
            editor4 = editor;
            editor4.setData(termMarketContent);
         } )
         .catch( error => {
             console.error( 'Oops, something went wrong!' );
             console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
             console.warn( 'Build id: eed83e2ex4oz-pejoxvy7ffif' );
             console.error( error );
         } );
}

var getSerTerm  = function(){
    $.ajax({
        url : "/cms/terms/getTermService",
        async : false, // 비동기모드 : true, 동기식모드 : false
        type : 'GET',
        cache : false,
        dataType : 'text',
        data : null,
        success : function(data) {
            termSerContent = data;
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}

var getPriTerm  = function(){
    $.ajax({
        url : "/cms/terms/getTermPrivacy",
        async : false, // 비동기모드 : true, 동기식모드 : false
        type : 'GET',
        cache : false,
        dataType : 'text',
        data : null,
        success : function(data) {
            termPriContent = data;
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}

var getLocTerm  = function(){
    $.ajax({
        url : "/cms/terms/getTermLocate",
        async : false, // 비동기모드 : true, 동기식모드 : false
        type : 'GET',
        cache : false,
        dataType : 'text',
        data : null,
        success : function(data) {
            termLocContent = data;
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}

var getMarketTerm  = function(){
    $.ajax({
        url : "/cms/terms/getTermMarket",
        async : false, // 비동기모드 : true, 동기식모드 : false
        type : 'GET',
        cache : false,
        dataType : 'text',
        data : null,
        success : function(data) {
            termMarketContent = data;
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}

//서비스약관추가
function saveTermService(){
    //필수값 체크
    if(editor1.getData() == ""){
        alert("내용을 입력해주세요.");
        return false;
    }

    var params = {
        content :editor1.getData()
    }

    $.ajax({
        url : "/cms/terms/saveTermService",
        async : false, // 비동기모드 : true, 동기식모드 : false
        type : 'POST',
        cache : false,
        dataType : 'text',
        data : params,
        success : function(data) {
            alert("약관 작성이 완료되었습니다.");
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}

//개인정보취급약관추가
function saveTermPrivacy(){
    //필수값 체크
    if(editor2.getData() == ""){
        alert("내용을 입력해주세요.");
        return false;
    }

    var params = {
        content :editor2.getData()
    }

    $.ajax({
        url : "/cms/terms/saveTermPrivacy",
        async : false, // 비동기모드 : true, 동기식모드 : false
        type : 'POST',
        cache : false,
        dataType : 'text',
        data : params,
        success : function(data) {
            alert("약관 작성이 완료되었습니다.");
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}

//위치기반서비스이용약관 저장
function saveTermLocate(){
    //필수값 체크
    if(editor3.getData() == ""){
        alert("내용을 입력해주세요.");
        return false;
    }

    var params = {
        content :editor3.getData()
    }

    $.ajax({
        url : "/cms/terms/saveTermLocate",
        async : false, // 비동기모드 : true, 동기식모드 : false
        type : 'POST',
        cache : false,
        dataType : 'text',
        data : params,
        success : function(data) {
            alert("약관 작성이 완료되었습니다.");
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}
    
    //마케팅정보수신동의 저장
function saveTermMarketing(){
    //필수값 체크
    if(editor4.getData() == ""){
        alert("내용을 입력해주세요.");
        return false;
    }

    console.log(editor4.getData());

    var params = {
        content :editor4.getData()
    }

    $.ajax({
        url : "/cms/terms/saveTermMarket",
        async : false, // 비동기모드 : true, 동기식모드 : false
        type : 'POST',
        cache : false,
        dataType : 'text',
        data : params,
        success : function(data) {
            alert("약관 작성이 완료되었습니다.");
        },
        error : function(request,status,error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
}





$(document).ready(function() {
    pageOnLoad();
});
</script>
