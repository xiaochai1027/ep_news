function Detail() {

    this.submit = function() {
        var dialog = new LoadingDialog();
        dialog.show();
        if (!$('#picRules').val()) {
            var type = $('#j-type>option:selected').val();
            classNodeImg(type);
        }
        ajax({
            url : '/api/admin/new/add',
            type : 'post',
            data : $('#form').serialize()
        }).done(function(data) {
            dialog.dismiss();
            var url = document.referrer && document.referrer != window.location.href  ? document.referrer : '/admin/new/list';
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
                },
                error : function() {
                    alert('ok');
                    $('#upload_pic').val('');
                }});
    }
}


$(document).ready(function() {
    var detail = new Detail();
    var needAddress = $('#needAddress').val();
    var courseId = $('#courseId').val();
    var ws_type = $('#j-wsType').val() || 1;

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


    $('#j-delayTime').datetimepicker({
        lang:'ch',
        timepicker:false,
        format:'Y/m/d',
        formatDate:'Y/m/d'
    });


    $('#save').click(function (){

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
