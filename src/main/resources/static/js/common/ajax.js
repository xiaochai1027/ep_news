/**
 * Created by lsc on 2014/12/1.
 */
(function(root,factory){
    if(typeof define === 'function' && define.amd){
        define(['jquery', 'q','util/constant'], function ($, Q, Constant) {
            return factory($,Q,Constant);
        });
    }else{
        root.ajax = factory(root.$,root.Q,root.Constant);
    }
})(this,function($,Q,Constant){
    var requestData = function (opt, ctx) {
        return Q.promise(function (resolve, reject, notify) {
        	if (opt.url.indexOf('?') != -1) {
        		opt.url += '&_' + new Date().getTime();
        	} else {
        		opt.url += '?_' + new Date().getTime();
        	}
            $.ajax({
                url: opt.url,
                data: opt.data || {},
                dataType: opt.dataType || 'json',
                type: (opt.dataType == 'jsonp' ? 'get' : false) || opt.type || 'get',
                success: function (data) {
                    resolve.apply(ctx || null, arguments);
                },
                error: function () {
                    reject.apply(ctx || null, arguments);
                }
            });
        });
    };
    return requestData;
});
