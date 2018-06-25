/**
 * Created by liuqingqian on 16/11/29.
 */
function LoadingDialog() {
    this.show = function () {
        this.dismiss();
        $('body').append("<div id='j-loading' class='loading'><img src='/static/imgs/loading.gif' class='loading_img' alt='咻咻咻~'/></div>");
    }
    this.dismiss = function () {
        $('#j-loading').remove();
    }
}