<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <%@ include file="../include/header.jsp" %>
    <script src="../ckeditor5/build/ckeditor.js"></script>
    <link rel="stylesheet" type="text/css" href="../ckeditor5/sample/styles.css">
    <link rel="stylesheet" href="./style.css">
    <meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1.0,width=device-width" />
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://kit.fontawesome.com/7b32d23811.js" crossorigin="anonymous"></script>
    <script src="./js/index.js"></script>
    <title>exitoBoard</title>
</head>
<body>
    <div class="wrap">
        <h3 class="boardTitle">게시판</h3>
        <div class="select-wrap">
            <select class="board" name="" aria-placeholder="게시판을 선택해 주세요.">
                <!-- <i class="fa-solid fa-angle-down"></i> -->
                <option value="total">게시판을 선택해 주세요</i></option>
                <option value="info">정보공유</option>
                <option value="qna">Q & A</option>
                <option value="matching">기업 매칭</option>
                <option value="free">자유게시판</option>
            </select>
        </div>
        <h3>제목</h3>
        <input type="text" class="title" placeholder="제목을 입력해 주세요">
        <p class="tnum">0 / 50</p>
        <h3>내용</h3>
        <div class="editor" id="editor">
            <!-- <p>test</p> -->
        </div>
        <!-- <textarea type="text" class="content" placeholder="내용을 입력해 주세요"></textarea> -->
    </div>
    <div class="bottom" id="bottom">
        <button class="btn confirm" onclick="contentConfirm('add')">등록하기</button>
        <button style="display:none;" class="btn fill" onclick="contentConfirm('modify')">수정하기</button>
    </div>
</body>
</html>
<script>
    $(document).ready(function () {
//   pageOnLoad();
  ClassicEditor
      .create(document.querySelector('#editor'), {
          toolbar: {
              items: [
                //   'heading',
                //   '|',
                //   'bold',
                //   'italic',
                //   'link',
                //   'bulletedList',
                //   'numberedList',
                //   '|',
                //   'outdent',
                //   'indent',
                  
                  'imageUpload',
                  '|',
                //   'blockQuote',
                //   'insertTable',
                //   'mediaEmbed',
                //   'undo',
                //   'redo',
                //   'htmlEmbed',
                //   'horizontalLine',
                  'fontSize',
                //   'fontColor',
                //   'fontBackgroundColor',
                //   'alignment',
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
      })
      .then(editor => {
          editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
              return new UploadAdapter(loader);
          };
          editor1 = editor;
      })
      .catch(error => {
          console.error('Oops, something went wrong!');
          console.error('Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:');
          console.warn('Build id: eed83e2ex4oz-pejoxvy7ffif');
          console.error(error);
      });
}); 
</script>
