<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <script src="../../../ckeditor5/build/ckeditor.js"></script>
    <link rel="stylesheet" type="text/css" href="../../../ckeditor5/sample/styles.css">
    <link rel="stylesheet" href="../../../css/community.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-2.2.4.js" integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI=" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/7b32d23811.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <title>exitoBoard</title>
</head>
<body>
<form action="" class="wrap" id="contentForm" name="contentForm" onsubmit="return false;">
    <h3 class="boardTitle">게시판</h3>
    <input type="hidden" id="id" name="id" value="${id}"/>
    <input type="hidden" id="userId" name="userId" value="${userId}"/>
    <div class="select-wrap">
        <select class="board" name="category" aria-placeholder="게시판 선택">
            <!-- <i class="fa-solid fa-angle-down"></i> -->
            <option value="total">게시판 선택</i></option>
            <option value="정보공유">정보공유</option>
            <option value="QnA">QnA</option>
            <option value="기업매칭">기업매칭</option>
            <option value="자유게시판">자유게시판</option>
        </select>
    </div>
    <h3 class="h3title">제목</h3>
        <input type="text" class="title" placeholder="제목">
        <p class="tnum">0 / 50</p>
        <div class="wrap_content">
            <h3 class="h3content">내용</h3>
            <div class="bottom" id="bottom">
                <button class="btn confirm" onclick="contentConfirm('add')">작성하기</button>
                <button style="display:none;" class="btn fill" onclick="contentConfirm('modify')">수정하기</button>
            </div>
        </div>
        <div class="editor" id="editor">
            <!-- <p>test</p> -->
        </div>
        <!-- <textarea type="text" class="content" placeholder="내용을 입력해 주세요"></textarea> -->
</form>

</body>
</html>
<script>
    let editor1;
    const id = document.getElementById('id').value;
    const userId = document.getElementById('userId').value;

    const f = document.getElementById("contentForm");
    console.log(f.id.value == null);
    if(f.id.value != null && f.id.value != ''){
        getData(f.id.value);
        $('.confirm').css('display','none');
        $('.fill').css('display', 'block');
    }


    async function getData(id){
        await axios.get("/mobile/community/one", {
            params:{
                id : id
            }
        }).then((res) => {
            if(res.status == 200){
                f.category.value = res.data.category;
                f.title.value = res.data.title;
                editor1.setData(res.data.content);
            }
        })
    }

    async function contentConfirm(type){

        if(f.category.value == 'total'){
            alert("카테고리를 선택해주세요.");
            return false;
        }

        if(f.title.value == ''){
            alert("제목을 입력해주세요.");
            return false;
        }

        if(editor1.getData() == ''){
            alert("내용을 입력해주세요.");
            return false;
        }

        const formData = new FormData();



        formData.append("id", id);
        formData.append("category", f.category.value);
        formData.append("userId", userId);
        formData.append("title", f.title.value);
        formData.append("content", editor1.getData());

        switch(type){
            case  "add" :
                if(!confirm("게시글을 추가하시겠습니까?")) return false;
                await axios.post("/mobile/community", formData, {headers:{'Content-Type' : 'multipart/form-data'}})
                    .then((res) => {
                        if(res.status == 200){
                            alert("게시글이 추가되었습니다.");
                           location.href = "/mobile/community/detail?id="+ res.data +"&userId=" + userId;
                           window.flutter_inappwebview.callHandler('postMessage', res.data, userId);
                           // JavaScriptChannel.postMessage(res.data + " " + userId);
                        }else{
                            alert("오류가 발생했습니다. 다시 시도해주세요.");
                        }
                    })
                break;
            case "modify" :
                if(!confirm("게시글을 수정하시겠습니까?")) return false;
                await axios.put("/mobile/community/edit", formData, {header:{'Content-Type' : 'multipart/form-data'}})
                    .then((res) => {
                        if(res.status == 200){
                            alert("게시글이 수정되었습니다.");
                            location.href = "/mobile/community/detail?id=${id}&userId=${userId}";
                            window.flutter_inappwebview.callHandler('postMessage', id, userId);
                           // JavaScriptChannel.postMessage(id + " " + userId);
                        }else{
                            alert("오류가 발생했습니다. 다시 시도해주세요.");
                        }
                    })
                break;
        }
    }



    //이미지업로드 ( common.js 와 동일 )
    //TODO : 개발 후 리팩토링 필요

    class UploadAdapter {
        constructor(loader) {
            this.loader = loader;
        }

        upload() {
            return this.loader.file.then( file => new Promise((async (resolve, reject) => {
                await this._initRequest();
                await this._initListeners(resolve, reject, file);
                await this._sendRequest(file);
            })))
        }

        abort() {
            if ( this.xhr ) { this.xhr.abort(); }
        }

        _initRequest() {
            const xhr = this.xhr = new XMLHttpRequest();
            xhr.open('POST', '/mobile/community/uploadImg', true);
            xhr.responseType = 'json';
        }

        async _initListeners(resolve, reject, file) {
            console.log(file);
            const xhr = this.xhr;
            const loader = this.loader;
            const genericErrorText = '파일을 업로드 할 수 없습니다. \n파일용량은 25MB를 초과할수 없습니다.'

            console.log(xhr);

            await xhr.addEventListener('error', () => {reject(genericErrorText)})
            await xhr.addEventListener('abort', () => reject())
            await xhr.addEventListener('load', () => {
                const maxSize = 25000000;
                const response = xhr.response;
                console.log("response", response);

                if(!response || response.error ||file.size > maxSize) {
                    return reject( response && response.error ? response.error.message : genericErrorText );
                }

                resolve({
                    default: response.link //업로드된 파일 주소
                })

                // if ( xhr.upload ) {
                //     xhr.upload.addEventListener( 'progress', evt => {
                //         if ( evt.lengthComputable ) {
                //             loader.uploadTotal = evt.total;
                //             loader.uploaded = evt.loaded;
                //         }
                //     } );
                // }
            })
        }

        _sendRequest(file) {
            // const fr = new FileReader();
            //
            // fr.onload = (base64) => {
            //     const image = new Image();
            //
            //     image.src = base64.target.result;
            //
            //     image.onload = (e) => {
            //         const $canvas = document.createElement(`canvas`);
            //         const ctx = $canvas.getContext(`2d`);
            //
            //         $canvas.width = e.target.width;
            //         $canvas.height = e.target.height;
            //
            //         ctx.drawImage(e.target, 0, 0);
            //
            //         // 용량이 줄어든 base64 이미지
            //         console.log($canvas.toDataURL(`image/jpeg`, 0.5));
            //     }
            // }
            //
            // fr.readAsDataURL(file);
            console.log(file)
            const data = new FormData();
            data.append('file', file);
            this.xhr.send(data)
        }
    }
    $(document).ready(function () {
//   pageOnLoad();
        ClassicEditor
            .create(document.querySelector('#editor'), {
                toolbar: {
                    items: [
                        'imageUpload',
                        '|',
                        'fontSize',
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

