<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/header.jsp" %>
    <script src="/js/common.js"></script>
    <script src="../ckeditor5/build/ckeditor.js"></script>
    <title>EXITO 공지사항</title>
    <link rel="stylesheet" href="../css/reset.css">
    <script src="https://code.jquery.com/jquery-2.2.4.js"
            integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI=" crossorigin="anonymous"></script>
    <script>
        let isEnd = false;

        $(function () {
            $(".accr_cont").hide();
            $(".accr_cont").first().show();
            $(".accr_box .title").click(function () {
                $(this).toggleClass("on");
                if ($(this).hasClass("on")) {
                    $(this).next().slideDown(300);
                }
                return false;
            });
        });

    </script>
</head>
<body>
<div class="wrap">
    <!-- 아코디언 메뉴 리스트 -->
    <ul class="accr_wrp">
        <li class="accr_box" id="must">
            <h2 class="title">
                <span class="day">${NoticeInfo.createAt}</span>
                <span class="tit">${NoticeInfo.title}</span>
            </h2>
            <div class="accr_cont">
                ${NoticeInfo.contents}
            </div>
        </li>
    </ul>
    <ul class="accr_wrp" id="notice">
    </ul>
</div>
<style>

    a {   overflow-wrap: break-word;}

    * {
        margin: 0;
        padding: 0;
    }

    ul, li, ol {
        list-style: none;
    }

    img {
        width: auto;
        max-width: 100%;
        height: auto;
        max-height: 100%;
    }

    /* 아코디언 메뉴 .accr*/
    .accr_wrp {
        overflow-y: auto;
        font-size: 16px;
    }

    .accr_wrp h2 {
        margin: 0;
    }

    .accr_box .title {
        display: block;
        position: relative;
        border-bottom: 1px solid #868686;
        padding: 15px 45px 15px 10px;
        font-weight: 400;
        font-size: 1.16em;
        cursor: pointer;
    }

    .accr_box .title span {
        display: block;
        margin-left: 12px;
    }

    .accr_box .title span.day {
        margin-bottom: 3px;
        font-size: 0.8em;
        color: #868686;
        font-weight: 300;
    }

    .accr_box .title i {
        display: inline-block;
        position: absolute;
        top: 50%;
        right: 20px;
        transform: translateX(-50%);
        margin-right: 8px;
        border: solid #ddd;
        border-width: 2px 2px 0 0;
        width: 7px;
        height: 7px;
        transform: rotate(135deg);
        transition: transform 0.3s;
        content: "";
    }

    .accr_box .title.on i {
        transform: rotate(-45deg);
    }

    .accr_cont {
        display: none;
        height: 100%;
        padding: 8px 20px;
        background-color: #f7f7f7;
    }

    .accr_cont p, .accr_cont li {
        padding: 4px;
    }

    .accr_wrp figure > img,.accr_wrp p > img{position: inherit;}

    #must {
        background:#61DE9A;
    }
</style>
</body>
