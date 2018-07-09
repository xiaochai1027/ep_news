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
            tempCourseHtml = '',
            ctime = '';

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
            var c =timeStamp2String(item.ctime);
            tempCourseHtml +=  '<tr>'
                            +  '<td class="id">' + item.id + '</td>'
                            +  '<td class="type">' + courseType + '</td>'
                            + '<td class="name"> '+ item.title + '</td>'
                            + '<td class="headPic">' + item.headPic + '</td>'
                            + '<td class="index">' + item.indexs + '</td>'
                            + '<td id="ctime" class="ctime">'+c+'</td>'
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

//unix 转换成 yyyy-mm-dd hh:mm
function getCloseMill(UNIX_timestamp){
    var a = new Date(UNIX_timestamp * 1000);
    var months = ['01','02','03','04','05','06','07','08','09','10','11','12'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var sec = a.getSeconds();
    var time = year + '/' + month + '/' + addZero(date) + ' ' + addZero(hour) + ':' + addZero(min) ;
    return time;
}

function timeStamp2String(time){
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "/" + month + "/" + date+" "+hour+":"+minute+":"+second;
}