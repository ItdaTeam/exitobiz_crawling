<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/header.jsp" %>
    <script src="/js/common.js"></script>
    <script src="../ckeditor5/build/ckeditor.js"></script>
    <link rel="stylesheet" type="text/css" href="../ckeditor5/sample/styles.css">
    <title>EXITO 공지사항</title>
    <script src="https://code.jquery.com/jquery-2.2.4.js" integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI=" crossorigin="anonymous"></script>
    <script>
        let isEnd = false;

        $(function(){
            $(".accr_cont").hide();

            $(".accr_box .title").click(function(){
            $(this).toggleClass("on");
            if($(this).hasClass("on")){
                $(this).next().slideDown(300);
            }else{
                $(this).next().slideUp(300);
            }
            return false;
            });
        $(window).scroll(function(){
            if($(window).scrollTop() >= $(document).height() - $(window).height()){
                fetchList();
            }
            })
        })

        function show(e) {
            $(e).toggleClass("on");
            if($(e).hasClass("on")){
                $(e).next().slideDown(300);
            }else{
                $(e).next().slideUp(300);
            }
            return false;
        }

        let fetchList = function(){
            if(isEnd == true){
                return;
            }

            let lastNo = $('#notice li').last().data('no')||0;
            console.log("데이터번호" + lastNo)
            let params = {lastNo : lastNo};
            $.ajax({
                type : 'GET',
                url : '/m/getNotice',
                data : params,
                dataType : null,
                success : function(result) {
                    let length = result.length;
                    if(length <= 5){
                        isEnd = true;
                    }
                    $.each(result,function(index,vo){
                        renderList(false, vo);
                    })
                },
                error: function(request, status, error) {
                    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

                }
            });

            let renderList = function(mode, vo){
                let html = "<li class='accr_box' data-no='"+vo.id+"'>"
                         + "<h2 class='title' onclick='show(this);'>"
                         + "<i>" + "</i>"
                         + "<span class='day'>"+vo.createAt+"</span>"
                         + "<span class='tit'>"+vo.title+"</span>"
                         + "</h2>"
                         + "<div class='accr_cont'>"
                         + vo.contents
                         + "</div>"
                         + "</li>"
                if(mode){
                    $("#notice").prepend(html);
                }
                else{
                    $("#notice").append(html);
                }
            }
        }

    </script>
</head>
<body>
    <div class="wrap">
        <!-- 아코디언 메뉴 리스트 -->
        <ul class="accr_wrp">
        <c:forEach var="mustvo" items="${MustInfo}">
            <li class="accr_box" id="must">
                <h2 class="title">
                <i></i>
                <span class="day">${mustvo.createAt}</span>
                <span class="tit">[필독]${mustvo.title}</span>
                </h2>
                <div class="accr_cont">
                    ${mustvo.contents}
                </div>
            </li>
        </c:forEach>
        </ul>
        <ul class="accr_wrp" id="notice">
            <c:forEach var="noticevo" items="${firstNotice}">
                <li class="accr_box" data-no="${noticevo.id}">
                    <h2 class="title">
                        <i></i>
                        <span class="day">${noticevo.createAt}</span>
                        <span class="tit">${noticevo.title}</span>
                    </h2>
                    <div class="accr_cont">
                            ${noticevo.contents}
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <style>
            *{margin:0; padding:0;}
            ul,li,ol{list-style: none;}
            /* 아코디언 메뉴 .accr*/
            .accr_wrp {overflow-y:auto; font-size:16px;}
            .accr_wrp h2{margin:0;}
            .accr_wrp figure > img,.accr_wrp p > img{position: inherit;}
            .accr_box .title{
                display:block;
                position:relative; border-bottom:1px solid #868686; padding:15px 45px 15px 10px;
                font-weight:400; font-size: 1.16em; cursor:pointer;
            }
            .accr_box .title span{display:block; margin-left:12px;}
            .accr_box .title span.day{margin-bottom:3px; font-size: 0.8em; color:#868686; font-weight:300;}
            .accr_box .title i {
                display:inline-block;
                position:absolute; top:50%; right:20px; transform: translateX(-50%);
                margin-right:8px; border: solid #868686; border-width: 2px 2px 0 0;
                width: 7px; height: 7px;
                transform: rotate(135deg);
                transition: transform 0.3s;
                content: "";
            }
            .accr_box .title.on i {transform: rotate(-45deg);}
            .accr_cont{display:none; height:100%; padding:8px 20px; background-color: #f7f7f7;}
            .accr_cont p, .accr_cont li  {padding:4px;}
             #must {background:#61DE9A;}

    </style>
</body>
