<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../include/header.jsp" %>
    <script src="/js/common.js"></script>
    <script src="../ckeditor5/build/ckeditor.js"></script>
    <link rel="stylesheet" type="text/css" href="../ckeditor5/sample/styles.css">
    <style>
        #content{padding:20px; overflow:hidden;}
        figure{position:relative; margin:8px; width:100%; height:auto; overflow: hidden; background-color: #fafafa;}
        figure.image img, figure.image video{max-width:100%; max-height:100%; top:0; left:0; bottom:0; right:0; margin:auto;}
        img {top:0; left:0; right:0; bottom: 0; margin: auto; height: auto; max-height: 100%; position:inherit;}
        .cont_title{font-weight:600; font-size: 1.5em; line-height:1.8;}
        @media screen and (min-width:768px) {
            #content{margin:10px 80px; text-align:center;}
            figure{margin:20px;}
            .cont_title{font-size:2.2em;}

        }
        .ck.ck-editor__editable.ck-blurred .ck-widget.ck-widget_selected, .ck.ck-editor__editable.ck-blurred .ck-widget.ck-widget_selected:hover {
            outline: none !important;
        }
        .ck.ck-editor__main>.ck-editor__editable:not(.ck-focused){border:none;padding:0;}
        .ck.ck-editor *{line-height:1.5;}
        p i {font-style: italic;}
        p {padding: 0 20px;}
    </style>
</head>
<body>
    <textarea id="content"></textarea>
</body>
<script>
</script>
</html>
<script>
    var pjContent = '<%=request.getAttribute("contentPage")%>';

    function setProjectContent(){
        ClassicEditor
            .create( document.querySelector( '#content' ), {
                language: 'ko',
            } )
            .then( editor => {
                editor.plugins.get('FileRepository').createUploadAdapter = (loader)=>{
                    return new UploadAdapter(loader);
                };

                const toolbarElement = editor.ui.view.toolbar.element;
                toolbarElement.style.display = 'none';

                editor.isReadOnly = true;
                editor.setData(pjContent);
            } )
            .catch( error => {
                console.error( 'Oops, something went wrong!' );
                console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
                console.warn( 'Build id: eed83e2ex4oz-pejoxvy7ffif' );
                console.error( error );
            } );
    }

    $(document).ready(function() {
       setProjectContent();
    });
</script>