<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko" id="html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../../css/community.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-2.2.4.js" integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI=" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/7b32d23811.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <title>exitoBoardDetail</title>
</head>
<body id="body">
    <div class="wrap">
<%--        <div class="header">--%>

<%--            <div class="box1">--%>
<%--                <div class="headTitle">--%>
<%--                    <h5 id="category"></h5>--%>
<%--                </div>--%>
<%--                <ul class="dot">--%>
<%--                    <li>1</li>--%>
<%--                    <li>2</li>--%>
<%--                    <li>3</li>--%>
<%--                    <div class="hidddenBox">--%>
<%--                        <ul class="hiddenBox1">--%>
<%--                            <li><a href="/mobile/community/edit?id=${id}">수정</a></li>--%>
<%--                            <li><a href="#none" onclick="contentConfirm('delete')">삭제</a></li>--%>
<%--                        </ul>--%>
<%--                        <ul class="hiddenBox2">--%>
<%--                            <li><a href="#none" onclick="contentConfirm('declare')">신고</a></li>--%>
<%--                        </ul>--%>
<%--                    </div>--%>
<%--                </ul>--%>
<%--            </div>--%>
<%--            <div class="box2">--%>
<%--                <ul class="box2-1">--%>
<%--                    <li>--%>
<%--                        <img id="image" src="" alt="userImage">--%>
<%--                    </li>--%>
<%--                    <li>--%>
<%--                        <h2 id="nickname"></h2>--%>
<%--                    </li>--%>
<%--                    <li>--%>
<%--                        <h3 id="date"></h3>--%>
<%--                    </li>--%>
<%--                    <li>--%>
<%--                        <h3 id="time"></h3>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--                <ul class="box2-2">--%>
<%--                    <li>--%>
<%--                        <img src="img/eye.png" alt="">--%>
<%--                    </li>--%>
<%--                    <li>--%>
<%--                        <p id="viewCount"></p>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--            </div>--%>
            <div id="inner_wrap">
                <h1 id="title"></h1>
                <p id="content"></p>
            </div>
        </div>
                <input type="hidden" name="id" id="id" value="${id}"/>
                <input type="hidden" name="userId" id="userId" value="${userId}"/>
    </div>
</body>
</html>
<script>
    const id = document.getElementById('id').value;
    const userId = document.getElementById('userId').value;

    getData(id);

    function outputsize() {
        let newHeight = document.getElementById("html").offsetHeight;

        if (typeof window.flutter_inappwebview !== "undefined" && typeof window.flutter_inappwebview.callHandler !== "undefined"){
            window.flutter_inappwebview.callHandler('newHeight', newHeight);
        }

    }

    async function getData(id){
        await axios.get("/mobile/community/one", {
            params:{
                id:id
            }
        }).then((res) => {
            console.log(res);
            if(res.status == 200){
                $('#category').text(res.data.category);
                $('#image').attr('src' , res.data.idprofile);
                $('#nickname').text(res.data.usernickname);
                $('#title').text(res.data.title);
                $('#viewCount').text(res.data.view_count);
                $('#content').html(res.data.content);

                $('a').click(function(e){
                   e.preventDefault();
                    window.flutter_inappwebview.callHandler('urlLink', this.href);
                });

                // 로드된 후 웹뷰 높이값 flutter에 전송
                new ResizeObserver(outputsize).observe(html);

                let date = new Date(res.data.cret_dt);
                $('#date').text(date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate());
                $('#time').text(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());

                if(userId == res.data.user_id){
                    $('.hiddenBox1').css('display', 'block');
                    $('.hiddenBox2').css('display', 'none');
                }else{
                    $('.hiddenBox1').css('display', 'none');
                    $('.hiddenBox2').css('display', 'block');
                }

            }
        })
    }

    async function contentConfirm(type){
        const formData = new FormData();
        formData.append("id", id);
        switch (type){
            case 'delete' :
                if(!confirm("게시글을 삭제하시겠습니까?")) return false;
                await axios.put("/mobile/community/delete", formData, {header:{'Content-Type' : 'multipart/form-data'}})
                    .then((res) => {
                        if(res.status == 200){
                            alert("게시글이 삭제되었습니다.");
                        }else{
                            alert("오류가 발생했습니다. 다시 시도해주세요.");
                        }
                    })
                break;
            case 'declare' :
                if(!confirm("게시글을 신고하시겠습니까?")) return false;
                await axios.put("/mobile/community/declare", formData, {header:{'Content-Type' : 'multipart/form-data'}})
                    .then((res) => {
                        if(res.status == 200){
                            alert("게시글이 신고되었습니다.");
                        }else{
                            alert("오류가 발생했습니다. 다시 시도해주세요.");
                        }
                    })
                break;
        }

    }
    $(document).ready(function(){
        var click1 = false;

        $(".dot").click(function(){

            if(click1 == false){

                //활성화가 안된 상태라면

                $(".hidddenBox").addClass("on");

                click1 = true;

            }else {

                //활성화가 된 상태라면

                $(".hidddenBox").removeClass("on");

                click1 = false;

            }
        });

    });
</script>