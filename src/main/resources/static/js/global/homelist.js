$(function(){
    var loadingPop = new LoadingDialog();
    var list = $('.list');
    list.on('click', '.act', function(){
        var _this = $(this);
        var data = {
            groupId: _this.parents('.item').data('id')
        };
        if (_this.hasClass('on')) {
            act('off', data);
        }
        else {
            if (list.find('.act.on').size() && !confirm('已有生效图片组，确定生效该组吗？')) {
                return false;
            }
            act('on', data);
        }
    }).on('click', '.del', function(){
        var _this = $(this);
        if (_this.parents('.item').find('.act.on').size()) {
            alert('此图片组正在生效，无法删除');
            return false;
        }
        if (!confirm('是否确认删除此图片组')) {
            return false;
        }
        loadingPop.show();
        ajax({
            url: '/api/index/del',
            data: {
                groupId: _this.parents('.item').data('id')
            }
        }).done(function(){
            _this.parents('.item').hide();
            loadingPop.dismiss();
        }, function(){
            loadingPop.dismiss();
            alert('操作失败，请重试！');
        });
    });
    // 生效或者不生效
    function act (type, postData) {
        loadingPop.show();
        var url = type === 'on' ? '/api/index/publish' : '/api/index/unpublish';
        ajax({
            url: url,
            type: 'post',
            data: postData
        }).done(function(data){
            if (data.results === 'success') {
                location.reload();
            }
            loadingPop.dismiss();
        }, function(res){
            loadingPop.dismiss();
            alert('操作失败，请重试！');
        });
    }
});