$(document).ready(function(){
    var courseId = getUrlParam('courseId');

    //删除按钮
    $('#j-delete').click(function() {
        if (!confirm('你是否要删除本课程所有信息？（一旦删除不可恢复）')) {
            return;
        }
        ajax(
            {
                url : '/api/course/del?courseId=' + courseId
            }
        ).done(function(data) {
            alert('删除成功！');
            window.location = '/course/list';
        }, function() {});
    }).hover(function() { $('#j-think-twice').show() }, function() { $('#j-think-twice').hide() });


    var sellStart = $('#sellStart').val();
    var currentTime = Date.parse(new Date());
    if(sellStart  - currentTime > 0){
        $('#give').click(function() {
            alert('课程还未开始销售,不能赠送');
        });
    } else{
        //赠送按钮
        $('#give').click(function() {
            if ($('#tel').val()) {
                var phone = $('#tel').val();
                var phoneReg = /^1[0-9]{10}$/;
                if (!phoneReg.test(phone)) {
                    $('.error_words_login').show().html('请输入合法的手机号').removeClass('error_words_success');
                    $('.mo_login_box').html('登录');
                    alert('请正确填写赠送手机号');
                } else {
                    $('#j-give-tel').html($('#tel').val());
                    $('#j-give-hd').show();
                }
            } else {
                alert('请先填写赠送手机号');
            }
        });
    }

    $('#j-sure-give-btn').click(function (){

        var optAttribute = $('#j-give-select').val();
        var optDesc = $('#j-give-desc').val();

        ajax({
            url : '/api/course/give?courseId='
            + courseId
            + '&tel=' + $('#tel').val()
            + '&optAttribute=' + optAttribute
            + '&optDesc=' + optDesc
        }).done(function(data) {
            alert('赠送成功');
            window.location.reload();
        }, function(data) {
            var json = JSON.parse(data.responseText);
            if  (json.errorCode == 40106){
                alert('课程还未开始销售,不能赠送');
            }  else {
                alert('赠送失败');
            }
        });
    });

    $('#j-cancle-give-btn').click(function (){
        $('#j-give-hd').hide();
        $('.j-give-input').val('');
    });

    //下载excel
    $('#begin').datetimepicker({
        lang:'ch',
        timepicker:false,
        format:'Y/m/d',
        formatDate:'Y/m/d'
    });
    $('#end').datetimepicker({
        lang:'ch',
        timepicker:false,
        format:'Y/m/d',
        formatDate:'Y/m/d'
    });

    $('#submit').click(function(){
        var index = $('#j-excel-select option:selected').data('id');
        var begin = toDateTime($('#begin').val().replace(/\//g, '-'));
        var end = toDateTime($('#end').val().replace(/\//g, '-'));
        if (end < begin){
            alert('结束时间不可以大于开始时间');
            return;
        }

        window.location = '/api/order/download?start=' + $('#begin').val() + '&end=' + $('#end').val() + '&courseId=' + courseId + '&expressIndex=' + index;
    });

    //上传支付宝csv
    $('#ap_upload').click(function() {
        var form =  $('#form_thumb');
        console.log(form);
        console.log(form.attr('action'));
        form.ajaxSubmit(
            {type : "POST",
                dataType: "json",
                url : form.attr('action'),
                success : function(data) {
                    alert('成功');
                },
                error : function() {
                    alert('失败');
                }});
    });

    //上传批量赠送文件
    var giveFlag = true;
    $('#give_upload').click(function (){

        var obj = document.getElementById('give_meterias');
        if (obj.value == '') {
            alert('请选择要上传的文件');
            return false;
        }

        var stuff = obj.value.substr(obj.value.length-3, 3);
        if (stuff != 'xls') {
            alert('文件类型不正确，请选择xls文件');
            return false;
        }

        if (giveFlag) {
            var form =  $('#form_give');
            giveFlag = false;
            console.log(form);
            console.log(form.attr('action'));
            form.ajaxSubmit({
                type : "POST",
                dataType: "json",
                url : form.attr('action'),
                success : function(data) {
                    giveFlag = true;
                    alert('上传成功，本次赠送总计' + data.results.telSize + '人， 本次需要赠送' + data.results.giveSize + '人，已有课程' + data.results.buySize + '人。 ');

                },
                error : function() {
                    giveFlag = true;
                    alert('失败');
                }
            });
        }

    });

    ajax({
        url : '/api/course/express/list?courseId=' + courseId
    }).done(function (data) {
        var options = '';
        $.each(data.results, function(i, item){
            options += '<option data-id="' + item.index + '">' + item.name + '</option>';
        });
        $('#j-excel-select').html(options);
    }, function (){});

});

function toDateTime(str) {
    var arr = str.split("-");
    var year = arr[0];
    var month = arr[1]-1;
    var day = arr[2];
    var timestamp = new Date(year,month,day).getTime()/1000;
    return timestamp;
}