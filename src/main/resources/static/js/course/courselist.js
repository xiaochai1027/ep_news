function CourseList() {

}

CourseList.operate = function(method, id) {
    if (!confirm('确定' + method + '?')) {
        return;
    }
    ajax(
        {
            url : '/api/admin/new/' + method + '?id=' + id
        }
    ).done(function(data) {
        window.location.reload();
    }, function() {});
}

$(document).ready(function() {
    var courseList = new CourseList(),
        pageNum    = getParameter('pageNum') || 1,
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

    var wsType = getParameter('wsType');

    if (wsType) {
        $('.wsType_tab').removeClass('tab_selected');
        if ( wsType == 1) {
            $('#tab_kaoyan').addClass('tab_selected');
        } else if (wsType == 2) {
            $('#tab_cet').addClass('tab_selected');
        } else if (wsType == 3) {
            $('#tab_ielts').addClass('tab_selected');
        } else if (wsType == 4) {
            $('#tab_toefl').addClass('tab_selected');
        } else if (wsType == 5) {
            $('#tab_tem').addClass('tab_selected');
        } else if (wsType == 6) {
            $('#tab_skills').addClass('tab_selected');
        } else if (wsType == 7) {
            $('#tab_gongkao').addClass('tab_selected');
        }
        key += '&wsType=' + wsType;
    }

    var url = '/api/admin/new/list?pageSize=20&pageNum=' + pageNum + key;

    ajax({
        url : url
    }).done(function(data){

        var isReleased = '',
            isPublish = '',
            courseType = '',
            tempCourseHtml = '';

        //翻页控件初始化~
        setPager(data.page.pageCount, data.page.totalCount);

        if (data.results.length == 0){
            $('.hint_box').show();
        }

        $.each(data.results,function(i,item){
            if (item.state == 1){
                isReleased = '<div class="unreleased">未发布</div>';
                isPublish = '<a id="unpublish" class="unpublish" onclick="CourseList.operate(\'publish\', '+ item.id + ');" href="javascript:;" class="act_release">发布</a>';
            } else if (item.state == 2){
                isReleased = '<div class="released">已发布</div>';
                isPublish = '<a id="publish" class="publish" onclick="CourseList.operate( \'unpublish\', ' + item.id + ');" href="javascript:;">取消发布</a>';
            } else{
                isReleased = '<div class="delete">已删除</div>';
            }
            //1荣誉榜 2热门资讯 3个人专栏 4合作专栏
            if (item.type == 1){
                courseType = '荣誉榜';
            } else if (item.type == 2) {
                courseType = '热门资讯';
            } else if  (item.type == 3){
                courseType = '个人专栏';
            } else if  (item.type == 4){
                courseType = '合作专栏';
            }

            // var stateHtml = '';
            //
            // if (data.stime < item.sellStart){
            //     //未开售
            //     stateHtml = '未开售';
            //
            // } else if (data.stime >= item.sellEnd) {
            //     //停售
            //     stateHtml = '停售';
            //
            // } else {
            //     //售卖中
            //     if (item.usedQuota >= item.quota) {
            //         //售完
            //         stateHtml = '售完';
            //
            //     } else {
            //         //ing
            //         stateHtml = '售卖中';
            //     }
            // }

                // `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                // `title` char(50) DEFAULT '' COMMENT '标题',
                // `headPic` char(200) DEFAULT NULL COMMENT '新闻展示图片',
                // `content` text COMMENT '内容',
                // `type` int(4) DEFAULT NULL COMMENT '新闻类型:1荣誉榜 2热门资讯 3个人专栏 4合作专栏',
                // `index` int(11) DEFAULT NULL COMMENT '序号',
                // `ctime` datetime DEFAULT NULL,
                // `utime` datetime DEFAULT NULL,

            tempCourseHtml +=  '<tr>'
                            +  '<td class="id">' + item.id + '</td>'
                            +  '<td class="type">' + courseType + '</td>'
                            + '<td class="name"> '+ item.title + '</td>'
                            + '<td class="headPic">' +'new.chaifangchen.com'+ item.headPic + '</td>'
                            + '<td class="index">' + item.index + '</td>'
                            + '<td class="ctime" th:text="${#dates.format(user.date, "yyyy-MM-dd")}">1111</td>'
                            + '<td class="operate">'
                            + '<a href="/admin/new/detail?id=' + item.id+ '">编辑信息</a>/'
                            // + '<a href="/course/lesson/list?courseId=' + item.id+ '">编辑课次</a>/'
                            // + isPublish
                           /* + '/<a id="del" class="del" onclick="CourseList.operate(\'del\', ' + item.id + ');" href="javascript:;">删除</a>/'*/
                            + '/<a id="give" href="/admin/new/delete?id=' + item.id+ '">删除</a>'
                            + '</td>'
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
        wsType = getParameter('wsType'),
        linkKey           = '';

    if (!pageNo) {
        pageNo = 1;
    }

    if (key){
        linkKey = '&key=' + key;
    }

    if (wsType) {
        linkKey += '&wsType=' + wsType;
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