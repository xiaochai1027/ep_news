$(function(){
    var loadingPop = new LoadingDialog();
    var listPageUrl = '/global/index/list';
    var form = $('#fm');
    $('.save').click(function(){
        loadingPop.show();
        ajax({
            url: '/api/index/add',
            type: 'post',
            data: form.serialize()
        }).done(function(data){
            location.href = listPageUrl;
            loadingPop.dismiss();
        }, function(){
            loadingPop.dismiss();
            alert('保存失败，请重试！');
        });
    });

    $('.cancel').click(function(){
        location.href = listPageUrl;
    });

    // 传图
    var uploaderList = {};
    form.find(':file').each(function(i, elem){
        var _this = $(elem);
        var tr = _this.parents('tr');
        var type = tr.data('type');
        uploaderList[type] = new Uploader({
            fileDom: elem,
            bucket: 'kaochong',
            dir: 'index/icon',
            sendDone: function(data){
                tr.find('.url').val(data.results.url);
                tr.find('img').attr('src', data.results.url);
            },
            sendFail: function(data){
                alert('上传图片失败，请重新上传');
                _this.val('');
            }
        });
        _this.change(function(){
            var file = elem.files[0];
            if (!/(\.jpg|\.png|\.bmp|\.gif)$/i.test(file.name) || file.size > 50 * 1024) {
                alert('上传图片尺寸不正确\n\n仅支持150px*150px以下的尺寸\n图片大小最大不得超过50kb\n图片格式支持，jpg，png，bmp，gif');
                _this.val('');
                return false;
            }
            uploaderList[type].send();
        });
    });
});
