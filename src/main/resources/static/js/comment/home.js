$(document).ready(function() {
    var pageNum    = getParameter('pageNum') || 1,
        key        = $('#key').val() || getParameter('key');

    getCourseData(pageNum, key);

});

function getCourseData(pageNum, key){

    $('.hint_box').hide();

    if (!key){
        key = '';
    } else {
        key = '&key=' + encodeURI(key);
    }

    var url = '/api/course/listAll?pageSize=20&pageNum=' + pageNum + key;

    ajax({
        url : url
    }).done(function(data){

        var courseType = '',
            tempCourseHtml = '';

        //翻页控件初始化~
        setPager(data.page.pageCount, data.page.totalCount);

        if (data.results.length == 0){
            $('.hint_box').show();
        }

        $.each(data.results,function(i,item){

        	if (item.type == 1){
                courseType = '系统课';
            } else if (item.type == 2) {
                courseType = '公开课';
            }else {
            	courseType = '大讲堂';
            }

            tempCourseHtml +=  '<tr>'
            +  '<td class="id">' + item.id + '</td>'
            +  '<td class="type">' + courseType + '</td>'
            + '<td class="name">' + item.title + '</td>'
            + '<td class="comment"><a href="/comment/list?courseId=' + item.id + '">评论管理</a></td>'
            + '</tr>';
        });

        $('#j-course-con').html(tempCourseHtml);

    },function(){})
}

//翻页控件初始化
var setPager = function (pageCount, totalCount) {

    var totalPage         = parseInt(pageCount),
        totalRecords      = totalCount,
        pageNo            = getParameter('pageNum'),
        key               = getParameter('key'),
        linkKey           = '';

    if (!pageNo) {
        pageNo = 1;
    }

    if (key){
        linkKey = '&key=' + key;
    }

    kkpager.generPageHtml({
        pno: pageNo,
        //总页码
        total: totalPage,
        //总数据条数
        totalRecords: totalRecords,
        //链接前部
        hrefFormer: '',
        //链接尾部
        hrefLatter: '',
        getLink: function (n) {
            return this.hrefFormer + this.hrefLatter + "?pageNum=" + n + linkKey;
        }
    });
};

function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}