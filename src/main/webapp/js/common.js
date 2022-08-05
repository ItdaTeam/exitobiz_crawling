// cms 공통 스트립트 입니다 
// 해당 스크립트는 페이지의 원활한 이해를 돕기 위한 참고용으로 봐주세요
// cms 공통 스트립트 입니다
// 해당 스크립트는 페이지의 원활한 이해를 돕기 위한 참고용으로 봐주세요

function _logOut() {
    if (confirm("로그아웃 하시겠습니까??") == true) {
        location.href = "/login/logout";
    }
}

//그리드 레이아웃 저장
function _getUserGridLayout(layoutId, grid) {
    localStorage.setItem(layoutId, grid.columnLayout);
    alert("컬럼위치를 저장하였습니다.");
}

//그리드 레이아웃 복원
function _setUserGridLayout(layoutId, grid, initColumns) {
    /*  // 주석 소스처럼 진행하여도 컬럼위치는 복원되나, cellTemplate 설정이 저장되지않음.
    	var layout = localStorage.getItem(layoutId);
        if (layout) {
        	grid.columnLayout = layout;
        }
    */

    if (window.localStorage[layoutId]) {
        let columnsArr = JSON.parse(window.localStorage[layoutId]).columns;

        grid.columns.clear();
        columnsArr.forEach((col) => {
            initColumns.forEach((col2) => {
                if (col.binding == col2.binding) {
                    grid.columns.push(new wijmo.grid.Column(col2));
                }
            });
        });
    }
}

//페이지 클릭이벤트
function clickPager(idx, grid, gridId) {
    grid.collectionView.moveToPage(idx - 1); // 그리드 0부터 시작
    refreshPaging(grid.collectionView.totalItemCount, idx, grid, gridId); // 그리드의 전체 아이템 수, 클릭한 인덱스 값 넘겨주기
}

//페이징 html 셋팅
function refreshPaging(totalData, currentPage, grid, gridId, gridView, gridPager) {
    //페이지 사이즈
    const dataPerPage = grid.collectionView.pageSize; // 그리드의 한 페이지당 보여지는 행의 개수
    // 페이지 숫자 목록
    const pageCount = 5;
    //전체 페이지
    const totalPage = Math.ceil(totalData / dataPerPage);
    //페이지그룹
    const pageGroup = Math.ceil(currentPage / pageCount);

    let last = pageGroup * pageCount; // 가장 마지막 인덱스

    if (last > totalPage) {
        last = totalPage;
    }

    let first = last - (pageCount - 1);

    const next = last + 1; // 다음
    var prev = first - 1; // 이전

    if (totalPage < 1) {
        first = last;
    }

    const pages = $('#' + gridId + 'Pager');
    pages.empty();

    // <<  <
    pages.append('<span onClick="clickPager(1, ' + gridId + ', ' + "'" + gridId + "'" + ')" > << </span>');
    if (first > pageCount) {
        pages.append('<span onClick="clickPager(' + prev + ', ' + gridId + ', ' + "'" + gridId + "'" + ')" > ' + '<' + ' </span>');
    } else {
        pages.append('<span onClick="clickPager(1, ' + gridId + ', ' + "'" + gridId + "'" + ')" > ' + '<' + ' </span>');
    }

    // 현재 페이지 인덱스 만큼 append
    for (let j = first; j <= last; j++) {
        if (currentPage === j) {
            pages.append('<span class="selectPage" id="' + gridId + 'paging_' + j + '" onClick="clickPager(' + j + ', ' + gridId + ', ' + "'" + gridId + "'" + ')" > ' + j + ' </span>');

        } else if (j > 0) {
            pages.append('<span id="' + gridId + 'paging_' + j + '" onClick="clickPager(' + j + ', ' + gridId + ', ' + "'" + gridId + "'" + ')" > ' + j + ' </span>');

        }
    }

    // >  >>
    if (next > pageCount && next < totalPage) {
        pages.append('<span onClick="clickPager(' + next + ', ' + gridId + ', ' + "'" + gridId + "'" + ')" >  ' + '>' + ' </span>');
    } else {
        pages.append('<span onClick="clickPager(' + totalPage + ', ' + gridId + ', ' + "'" + gridId + "'" + ')" >  ' + '>' + ' </span>');
    }
    pages.append('<span onClick="clickPager(' + totalPage + ', ' + gridId + ', ' + "'" + gridId + "'" + ')" > >> </span>');


//    var selectBox = '<select name="'+gridId+'pageCount" id="'+gridId+'pageCount" onchange="pagingCountChange( monGrid, monView, monGridPager)">'
//    	//'<select name="'+gridId+'pageCount" id="'+gridId+'pageCount" onchange="pagingCountChange('+grid+', '+gridView+', '+gridPager+')">'
//				    +'<option value="30">30</option>'
//				    +'<option value="50">50</option>'
//				    +'<option value="100" selected="selected">100</option>'
//				    +'</select>';
//
//    pages.append(selectBox);

    $(".pager").removeClass('wj-control wj-content wj-pager wj-collectionview-navigator wj-state-empty wj-state-readonly');
}

function pagingCountChange(grid, gridView, gridPager){
    console.log("pagingCountChange");

    gridView = new wijmo.collections.CollectionView(result, {
        pageSize: 100,
        trackChanges: true
    });
    gridPager.cv = gridView;
    grid.itemsSource = gridView;
}

//그리드 초기 레이아웃 복원
function _resetUserGridLayout(layoutId, grid, initColumns) {

    grid.columns.clear();
    initColumns.forEach((col) => {
        grid.columns.push(new wijmo.grid.Column(col));
    });

    localStorage.setItem(layoutId, grid.columnLayout);
    alert("컬컴위치를 초기화하였습니다.");
    _setUserGridLayout(layoutId, grid, initColumns);
}

function sessionCheck(staffId){
    if(staffId=="null" || staffId == null){
        alert("세션이 종료되어 로그인화면으로 이동합니다.");
        location.href = "/cms";
        return false;

    }else{
        return true;

    }
}

//엑셀 업로드
function bindImportedDataIntoModel(grid) {
    const newData = (getImportedCVData(grid));
    grid.columns.clear();
    data = new wijmo.collections.CollectionView(newData);
    grid.autoGenerateColumns = true;
    grid.itemsSource = data;
}
//엑셀 업로드
function getImportedCVData(grid) {
    const arr = [];
    let nullRow = true;
    for (let row = 0; row < grid.rows.length; row++) {
        const item = {};
        for (let column = 0; column < grid.columns.length; column++) {
            const cellValue = grid.getCellData(row, column, false);
            //병합된 헤더 처리
            // let header = grid.columns[column].header ? grid.columns[column].header : grid.columns[column - 1].header + '-2';
            // 만약 열 헤더가 있으면
            if (grid.columns[column].header){
                var header =  grid.columns[column].header
            } else{
                //           만약 열 헤더가 없으면 본래 병합된 값으로 판단
                for(var i = column-1; i >= 0; i--){
                    if (grid.columns[i].header){
                        var header =  grid.columns[i].header + " - "+column+" index"
                        break;
                    }
                }
            }
            var binding = _convertHeaderToBinding(header);
            item[binding] = cellValue;
        }
        arr.push(item);
    }
    return arr;
}
//엑셀 업로드
function _convertHeaderToBinding(header) {
    return header.replace(/\s/, '').toLowerCase();
}

// 팝업
// $(function(){
//   $('.popup_trigger').on('click',function(e){
//       $('.popup').addClass("is_on")
//       e.preventDefault();
//   });
//   $('.popup_close').on('click',function(e){
//       $('.popup').removeClass("is_on")
//       e.preventDefault();
//   });
// });

//날짜포맷 yyyy-MM-dd 변환
//input : date 포맷
function _getFormatDate(date, type) {
    if(type == 'm'){
        var year = date.getFullYear();
        var month = ("0" + (1 + date.getMonth())).slice(-2);

        return year + "-" + month;
    }else if(type == 'w'){
        var year = date.getFullYear();
        var week = ("0" + date.getWeek()).slice(-2);

        return year + "-W" + week;
    } else{
        var year = date.getFullYear();
        var month = ("0" + (1 + date.getMonth())).slice(-2);
        var day = ("0" + date.getDate()).slice(-2);

        return year + "-" + month + "-" + day;
    }
}

Date.prototype.getWeek = function (dowOffset) {
    /*getWeek() was developed by Nick Baicoianu at MeanFreePath: http://www.meanfreepath.com */

    dowOffset = typeof(dowOffset) == 'number' ? dowOffset : 0; // dowOffset이 숫자면 넣고 아니면 0
    var newYear = new Date(this.getFullYear(),0,1);
    var day = newYear.getDay() - dowOffset; //the day of week the year begins on
    day = (day >= 0 ? day : day + 7);
    var daynum = Math.floor((this.getTime() - newYear.getTime() -
        (this.getTimezoneOffset()-newYear.getTimezoneOffset())*60000)/86400000) + 1;
    var weeknum;
    //if the year starts before the middle of a week
    if(day < 4) {
        weeknum = Math.floor((daynum+day-1)/7) + 1;
        if(weeknum > 52) {
            let nYear = new Date(this.getFullYear() + 1,0,1);
            let nday = nYear.getDay() - dowOffset;
            nday = nday >= 0 ? nday : nday + 7;
            /*if the next year starts before the middle of
              the week, it is week #1 of that year*/
            weeknum = nday < 4 ? 1 : 53;
        }
    }
    else {
        weeknum = Math.floor((daynum+day-1)/7);
    }
    return weeknum;
};

var dateFormat = function () {
    var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
        timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
        timezoneClip = /[^-+\dA-Z]/g,
        pad = function (val, len) {
            val = String(val);
            len = len || 2;
            while (val.length < len) val = "0" + val;
            return val;
        };

    // Regexes and supporting functions are cached through closure
    return function (date, mask, utc) {
        var dF = dateFormat;

        // You can't provide utc if you skip other args (use the "UTC:" mask prefix)
        if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
            mask = date;
            date = undefined;
        }

        // Passing date through Date applies Date.parse, if necessary
        date = date ? new Date(date) : new Date;
        if (isNaN(date)) throw SyntaxError("invalid date");

        mask = String(dF.masks[mask] || mask || dF.masks["default"]);

        // Allow setting the utc argument via the mask
        if (mask.slice(0, 4) == "UTC:") {
            mask = mask.slice(4);
            utc = true;
        }

        var	_ = utc ? "getUTC" : "get",
            d = date[_ + "Date"](),
            D = date[_ + "Day"](),
            m = date[_ + "Month"](),
            y = date[_ + "FullYear"](),
            H = date[_ + "Hours"](),
            M = date[_ + "Minutes"](),
            s = date[_ + "Seconds"](),
            L = date[_ + "Milliseconds"](),
            o = utc ? 0 : date.getTimezoneOffset(),
            flags = {
                d:    d,
                dd:   pad(d),
                ddd:  dF.i18n.dayNames[D],
                dddd: dF.i18n.dayNames[D + 7],
                m:    m + 1,
                mm:   pad(m + 1),
                mmm:  dF.i18n.monthNames[m],
                mmmm: dF.i18n.monthNames[m + 12],
                yy:   String(y).slice(2),
                yyyy: y,
                h:    H % 12 || 12,
                hh:   pad(H % 12 || 12),
                H:    H,
                HH:   pad(H),
                M:    M,
                MM:   pad(M),
                s:    s,
                ss:   pad(s),
                l:    pad(L, 3),
                L:    pad(L > 99 ? Math.round(L / 10) : L),
                t:    H < 12 ? "a"  : "p",
                tt:   H < 12 ? "am" : "pm",
                T:    H < 12 ? "A"  : "P",
                TT:   H < 12 ? "AM" : "PM",
                Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
                o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
                S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
            };

        return mask.replace(token, function ($0) {
            return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
        });
    };
}();

// Some common format strings
dateFormat.masks = {
    "default":      "ddd mmm dd yyyy HH:MM:ss",
    shortDate:      "m/d/yy",
    mediumDate:     "mmm d, yyyy",
    longDate:       "mmmm d, yyyy",
    fullDate:       "dddd, mmmm d, yyyy",
    shortTime:      "h:MM TT",
    mediumTime:     "h:MM:ss TT",
    longTime:       "h:MM:ss TT Z",
    isoDate:        "yyyy-mm-dd",
    isoTime:        "HH:MM:ss",
    isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
    isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
    dayNames: [
        "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    ],
    monthNames: [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    ]
};

// For convenience…
Date.prototype.format = function (mask, utc) {
    return dateFormat(this, mask, utc);
};

//이미지업로드
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
        xhr.open('POST', '/cms/ckeditor', true);
        xhr.responseType = 'json';
    }

    async _initListeners(resolve, reject, file) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = '파일을 업로드 할 수 없습니다. \n파일용량은 25MB를 초과할수 없습니다.'

        await xhr.addEventListener('error', () => {reject(genericErrorText)})
        await xhr.addEventListener('abort', () => reject())
        await xhr.addEventListener('load', () => {
            const maxSize = 25000000;
            const response = xhr.response
            console.log(response);
            if(!response || response.error ||file.size > maxSize) {
                return reject( response && response.error ? response.error.message : genericErrorText );
            }

            resolve({
                default: response.link //업로드된 파일 주소
            })
        })
    }

    _sendRequest(file) {
        const data = new FormData()
        data.append('file', file);
        this.xhr.send(data)
    }
}

function MyCustomUploadAdapterPlugin(editor) {
    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        return new UploadAdapter(loader)
    }
}


// 탭 메뉴
$(document).ready(function() {
  $(".tabpanel").hide(); 
  $(".tabpanel:first").show();
  $('.tablist > a').click(function(){
    $('.tablist > a').removeClass('on');
    $(this).addClass('on');
    var tab_id = $(this).attr('data-id');
    $("#" + tab_id).show();
    $("#" + tab_id).siblings().hide();
  })
});
