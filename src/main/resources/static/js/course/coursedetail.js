function Detail() {

    this.submit = function() {
        var dialog = new LoadingDialog();
        dialog.show();
        if (!$('#picRules').val()) {
            var type = $('#j-type>option:selected').val();
            classNodeImg(type);
        }
        ajax({
            url : '/api/course/add',
            type : 'post',
            data : $('#form').serialize()
        }).done(function(data) {
            dialog.dismiss();
            var url = document.referrer && document.referrer != window.location.href  ? document.referrer : '/course/list?wsType=2';
            window.location.href = url;
        }, function() {dialog.dismiss();});
    }

    this.uploadImg = function(inputId) {
        var form = 	$('#form_thumb');
        form.ajaxSubmit(
            {type : "POST",
                dataType: "json",
                url : form.attr('action'),
                success : function(data) {
                    $('#' + inputId).val(data.results.url);
                    $('#upload_pic').val('');
                    $('#upload_picLarge').val('');
                    $('#upload_picSquare').val('');
                    $('#upload_picRules').val('');
                },
                error : function() {
                    alert('ok');
                    $('#upload_pic').val('');
                    $('#upload_picLarge').val('');
                    $('#upload_picSquare').val('');
                    $('#upload_picRules').val('');
                }});
    }
}


$(document).ready(function() {
    var detail = new Detail();
    var needAddress = $('#needAddress').val();
    var courseId = $('#courseId').val();
    var ws_type = $('#j-wsType').val() || 1;
    if (courseId) {
        getTags(ws_type, courseId);
    }

    $('#sellStart').datetimepicker({
        mask:'9999/19/39 29:59'
    });

    $('#sellEnd').datetimepicker({
        mask:'9999/19/39 29:59'
    });

    $('#expirationdate').datetimepicker({
        lang:'ch',
        timepicker:false,
        format:'Y/m/d',
        formatDate:'Y/m/d'
    });

    $('#j-needDelay').change(function (){
        if ($('#j-needDelay').is(':checked')) {
            $('#j-delay-time-shade').hide();
        } else {
            $('#j-delay-time-shade').show();
            $('#j-delayTime').val('');
        }
    });

    // $('#j-needIAP').change(function (){
    //     if ($('#j-needIAP').is(':checked')) {
    //         $('#j-iapcon-hd').hide();
    //     } else {
    //         $('#j-iapcon-hd').show();
    //         $('#j-delayTime').val('');
    //     }
    // });

    $('#j-delay-time-shade').click(function (){
        alert('选择延迟发货开关才可以选择延迟发货时间');
    });

    $('#j-delayTime').datetimepicker({
        lang:'ch',
        timepicker:false,
        format:'Y/m/d',
        formatDate:'Y/m/d'
    });


    $('#save').click(function (){
        if ($('#j-needAddress').is(':checked')) {
            if ($('#j-price').val() == 0) {
                alert('免费课不需要收货地址');
                return;
            }
        }

        if ($('#j-refundable').is(':checked')) {
            if ($('#j-price').val() == 0) {
                alert('免费课不允许用户退课');
                return;
            }
        }

        if ($('#j-needDelay').is(':checked')) {
            if (!$('#j-delayTime').val()) {
                alert('请选择延迟发货时间，再点击保存按钮');
                return;
            }
        }

        if ($('#j-regfree-checkbox').is(':checked')) {
            if (!$('#j-regfree-course').val()) {
                alert('使用免登陆听课，请填写分享注册赠课ID');
                return;
            }
        } else {
            if ($('#j-regfree-course').val()) {
                alert('非免登陆听课，不需要填写分享注册赠课ID');
                return;
            }
        }

        detail.submit();
    });

    $('#cancel').click(function() {
        if (confirm('确定取消？')) {
            var url = document.referrer && document.referrer != window.location.href ? document.referrer : '/course/list?wsType=2';
            window.location.href = url;
        }
    });

    function inputChange(inputId) {
        return function() {
            detail.uploadImg(inputId);
        }
    }

    $('#j-type').change(function (){
        var type = $(this).val();
        $('#picRules').val('');
        if (courseId) {
            getTags(type, courseId);
        }
    });

    $('#upload_pic').change(inputChange('pic'));
    $('#upload_picLarge').change(inputChange('picLarge'));
    $('#upload_picSquare').change(inputChange('picSquare'));
    $('#upload_picRules').change(inputChange('picRules'));

    $('div#add_groups').click(function(){
        var index = $('.order_index').val();
        var title = $('.add_groups_title').val();
        if (!index || !title){
            alert('请填完整!');
            return;
        }

        var leng = $('ul#lesson_groups li').length + 1;
        var temp = '<li><input type="text"  class="lg_index"  name="lg_index_'
            + leng
            +  '" value="'
            + index
            +  '"/><input type="text" class="lg_title" name="lg_title_'
            + leng
            + '" maxlength=15 value="'
            + title
            + '"/><input type="hidden" class="lg_del" name="lg_del_'
            + index
            + '" value="0" class="lg_del"><div class="save_groups lesson_groups_btn">保存</div><div class="delete_groups lesson_groups_btn">删除</div></li>';
        $('#lesson_groups').append(temp);

        $('.order_index').val('');
        $('.add_groups_title').val('');
    });

    $('#lesson_groups').delegate('div.save_groups', 'click', function(){
        var id = $(this).parent().find('.lg_id').val();
        var title = $(this).parent().find('.lg_title').val();
        var index = $(this).parent().find('.lg_index').val();
        var url = id ? '/api/course/lessongroup/add?title=' + title + '&courseId=' + courseId + '&index=' + index + '&id=' + id
            : '/api/course/lessongroup/add?title=' + title + '&courseId=' + courseId + '&index=' + index
        ajax({
            url: url
        }).done(function(){
            window.location.reload();
        }, function (){});
    }).delegate('div.delete_groups','click',function(){
        // $(this).parent().hide().find('.lg_del').val(1);
        var id = $(this).parent().find('.lg_id').val();
        ajax({
            url: '/api/course/lessongroup/del?courseId=' + courseId + '&id=' + id
        }).done(function (){
            window.location.reload();
        }, function(){});
    });

    $('#j-card-checkbox').click(function() {
        if ($(this).is(':checked')) {
            if ( !$('.j-tag-checkbox').is(':checked') ) {
                alert('请至少选择一个考试类型');
                $(this).prop('checked', false);
                return;
            }
            $('.j-exam-batch').prop('disabled', false);
        } else {
            $('.j-exam-batch').prop('disabled', true).val('');
        }
    });

    //order
    if (needAddress == 1) {
        $('#j-express-intro').show();
        getOrderList(courseId);
    } else {
        $('#j-express-intro').hide();
    }

    $('#j-needAddress').click(function (){
        if ($(this).is(':checked')) {
            $('#j-express-intro').show();
            getOrderList(courseId);
        } else {
            $('#j-express-intro').hide();
            $('#j-express-list').html('');
        }
    });

    //add-order
    $('#j-add-express').click(function (){
        var html = $(temp);
        html.addClass('j-add-express-li');
        html.find('.j-cut-off-date').datetimepicker({
            mask:'9999/19/39 29:59'
        });
        html.find('.j-effect-btn').remove();
        $('#j-express-list').append(html);
    });

    //save、cancel、effect
    $('#j-express-list').delegate('.j-save-btn', 'click', function (){

        if (window.confirm("确定要保存吗?")) {
            var parent = $(this).parent(),
                id     = parent.find('.j-express-order').data('id') || '',
                index  = parent.find('.j-express-order').val(),
                name   = parent.find('.j-express-name').val(),
                date   = parent.find('.j-cut-off-date').val();

            ajax({
                url: '/api/course/express/add?index=' + index + '&name=' + name + '&close=' + date + '&courseId=' + courseId + '&id=' + id
            }).done(function (){
                window.location.reload();
            }, function (){});
        }

    }).delegate('.j-effect-btn', 'click', function (){

        if (window.confirm("确定要生效吗?")) {
            var parent = $(this).parent(),
                id     = parent.find('.j-express-order').data('id');

            ajax({
                url: '/api/course/express/effect?id=' + id
            }).done(function (){
                window.location.reload();
            }, function (){});
        }


    }).delegate('.j-cancel-btn', 'click', function () {

        $(this).parent().remove();

    });

});

var temp = '<li class="add_express_li">\
                        <input type="text" class="express_order  j-express-order" value="" data-id="">\
                        快递名称:\
                        <input type="text" class="express_name j-express-name" maxlength="8">\
                        截止日期:\
                        <input type="text" class="express_cut_off_date j-cut-off-date" value="">\
                        <div class="delete_express j-effect-btn hover">生效</div>\
                        <div class="delete_express j-cancel-btn hover">取消</div>\
                        <div class="delete_express j-save-btn hover">保存</div>\
                    </li>';

var tags_temp = '<div class="tags_item_hd"><b class="tags_type"></b>\
                 <input type="checkbox" class="tags_item j-tags-item" value=""></div>';

function getOrderList (id) {
    ajax({
        url : '/api/course/express/list?courseId=' + id
    }).done(function (data){

        if (data.results.length == 0) {
            var html = $(temp);
            html.addClass('default');
            html.find('.j-cut-off-date').removeClass('j-cut-off-date').attr({readonly: 'readonly'}).addClass('default_input');
            html.find('.j-express-order').attr({readonly: 'readonly'}).addClass('default_input').val(1);
            html.find('.j-express-name').val('考虫炸药包');
            html.find('.j-cancel-btn').remove();
            $('#j-express-list').append(html);
        } else {
            $.each(data.results, function (i, item) {
                var html = $(temp);
                if (i == 0) {
                    html.addClass('default');
                    html.find('.j-cut-off-date').removeClass('j-cut-off-date').attr({readonly: 'readonly'}).addClass('default_input');
                    html.find('.j-express-order').attr({readonly: 'readonly'}).addClass('default_input');
                }
                html.find('.j-express-order').val(item.index).attr({'data-id': item.id});
                html.find('.j-express-name').val(item.name);
                html.find('.j-cut-off-date').val(getCloseMill(item.closeMill / 1000)).datetimepicker({
                    mask:'9999/19/39 29:59'
                });
                if (item.state == 1) {
                    html.find('.j-effect-btn').html('已生效').removeClass('hover j-effect-btn');
                }
                html.find('.j-cancel-btn').remove();
                $('#j-express-list').append(html);
            });
        }

    }, function (){});
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

function classNodeImg(type){
    //添加公开课大讲堂(大帝)   --by 17.8.25
    //去掉大讲堂(大帝)  --by 17.8.28
    var imgUrl = 'http://cdn.bos.kaochong.com/web/';
    if (type == 1) {
        imgUrl += 'img_detail_classNotes_ky_06af7f7.png';
    } else if (type == 2) {
        imgUrl += 'img_detail_classNotes_cet_02e9f71.png';
    } else if (type == 3) {
        imgUrl += 'img_detail_classNotes_ys_75a86f0.png';
    } else if (type == 4) {
        imgUrl += 'img_detail_classNotes_tf_308bd66.png';
    } else if (type == 5) {
        imgUrl += 'img_detail_classNotes_48_bfc26ed.png';
    } else if (type == 6) {
        imgUrl += 'img_detail_classNotes_sy_5566377.png';
    } else if (type == 7) {
        imgUrl += 'img_detail_classNotes_gk_582bf18.png';
    }

    $('#picRules').val(imgUrl);
}

function getTags (type, courseId){
    var ws_type = parseInt(type);
    var courseId = courseId;
    var url =  '';
    if(courseId){
        url = '/api/course/tag/list/by/type?type=' + ws_type + '&courseId=' + courseId;
    } else {
        url =  '/api/course/tag/list/by/type?type=' + ws_type;
    }
    ajax({
        url: url
    }).done(function (data){
        $('#j-tags-list').html('<span class="sell_intro_sub">课程Tag</span>');
        if (data.results.length == 0) {
            //不显示tags
            $('#j-tags-intro-hd').hide();
        } else {
            $.each(data.results, function (i, item){
                var html = $(tags_temp);
                html.find('.tags_type').html(item.name);
                html.find('.tags_item').val(item.id);
                if(item.selected) {
                    html.find('.tags_item').attr('checked','checked');
                }
                $('#j-tags-list').append(html);
            });
            $('#j-tags-list').append('<div class="tags_sure_btn" id="j-tags-sure-btn">保存</div>').show();
            $('#j-tags-intro-hd').show();
        }
        $('#j-tags-list').delegate('#j-tags-sure-btn', 'click', function (){
            var courseId = $('#courseId').val();
            if (courseId) {
                var tags = '';
                $('.j-tags-item:checked').each(function (i, item){
                    var id = $(item).val();
                    tags = tags + ',' + id;
                });
                tags= tags.substring(1);

                ajax({
                    url:'/api/course/tag/set?courseId=' + courseId + '&tags=' + tags + '&wsType=' + ws_type
                }).done(function(data){
                    window.location.reload();
                }, function (data){
                    alert('保存失败了');
                });
            } else {
                alert('请先保存课程，再来保存课程Tag');
            }

        });

    }, function (data){
        console.log('获取tags失败');
    });
}
