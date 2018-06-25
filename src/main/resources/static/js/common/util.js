function getStrLength(str) {
	var cArr = str.match(/[^\x00-\xff]/ig);
	return str.length + (cArr == null ? 0 : cArr.length);
}

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return r[2];
	return null; // 返回参数值
}

function setUrlParam(urlStr, paraName, paraValue) {
    var strNewUrl = '';
    var strUrl = urlStr || window.location.href;
    var url = urlStr || window.location.href;
    if (strUrl.indexOf("?") != -1) {
        strUrl = strUrl.substr(strUrl.indexOf("?") + 1);
        if (strUrl.toLowerCase().indexOf(paraName.toLowerCase()) == -1) {
            strNewUrl = url + "&" + paraName + "=" + paraValue;
            return strNewUrl;
        }
        else {
            var aParam = strUrl.split("&");
            for (var i = 0; i < aParam.length; i++) {
                if (aParam[i].substr(0, aParam[i].indexOf("=")).toLowerCase() == paraName.toLowerCase()) {
                    aParam[i] = aParam[i].substr(0, aParam[i].indexOf("=")) + "=" + paraValue;
                }
            }
            strNewUrl = url.substr(0, url.indexOf("?") + 1) + aParam.join("&");
            return strNewUrl;
        }
    }
    else {
        strUrl += "?" + paraName + "=" + paraValue;
        return strNewUrl;
    }
}

function getUrlParamDetailx() {
	return window.location.href.split('-')[1].split('.')[0];
}
function getUrlParamList() {
	var a =  window.location.href;
	var listType = a.substr(a.indexOf('-')+1,1);
}
function isMobileBrower() {
	var sUserAgent = navigator.userAgent.toLowerCase();
	var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
	var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
	var bIsMidp = sUserAgent.match(/midp/i) == "midp";
	var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
	var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
	var bIsAndroid = sUserAgent.match(/android/i) == "android";
	var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
	var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";

	return bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid
			|| bIsCE || bIsWM;
}

function gotoLightingDetail(id) {
	if (isMobileBrower()) {
		window.location.href ='/n/lightingpay/lpdetailsm.html?id=' + id;
	} else {
		window.open('/n/lightingpay/lpdetails.html?id=' + id);
	}
}

function gotoDiscountDetail(id) {
	window.location.href = '/n/discount/detail-' + id +'.html';
}

function gotoNormalDetail(id) {
	if (isMobileBrower()) {
		window.location.href = '/n/detailm-' + id + '.html';
	} else {
		window.open('/n/detail-' + id + '.html');
	}
}

function isEmpty(txt) {
	return txt == '' || !txt;
}

function genOrderSuccStr(passType, platformName, platformId, data5, data6) {
	var tipStr = '';
	var adviceTipStr = '		如需咨询或退款，请加选课客服交流群，群号码：30316553';

	if (passType == PassType.TEL) {
		tipStr = '恭喜你，成功支付！授课老师会在开课前通过手机短信告知听课频道号和密码，请注意查收。';
	} else if (passType == PassType.USER_ACCOUNT) {
		if (platformId == 30) {
			//腾讯
			tipStr = '恭喜你，成功支付！授课老师会在开课前通过QQ邀请你进入课堂听课。';
		} else {
			//默认其他是粉笔
			tipStr = '恭喜你，成功支付！我们会在12小时内（开课前）将课程放到你的' + platformName + '账号里，请在开课前登陆'
				+ platformName + '按时听课。';
		}
	} else if (passType == PassType.ROOMNUM_AND_PW) {
		tipStr = '恭喜你，成功支付！听课频道号：' + data5 + '，密码：' + data6 + '，请在开课前1小时登陆' + platformName + '并凭借密码进入听课频道等待开课。';
	} else if (passType == PassType.USER_ACCOUNT_AND_CHANNEL) {
		tipStr = '恭喜你，成功支付！听课频道号：' + data5 + '，请在开课前1小时登陆' + platformName  +'并进入听课频道等待开课。';
	}

	return tipStr + '</br>' + adviceTipStr;
}

function splitTags(tag) {
    var tagArray = tag.split(' ');
    var outcome = '';
    $.each(tagArray,function(i,item){
        outcome += '<li>'+ item +'</li>&nbsp';
    })
    return outcome;
}

//遍历一个数组，用符号分割或是用<li>拼接
function eachTags(tags,isLiorSign){
	var _outcome = '';
	$.each(tags,function(i,item){
		if(isLiorSign === true){
			_outcome += '<li>'+ item +'</li>&nbsp';
		} else {
			_outcome += item ;
			if(i < tags.length - 1 ){
				_outcome += isLiorSign;
			}
		}
	})
	return _outcome;
}

//把2015-04-25 转换成时间戳
function timeToUnix(strings){
    var arr = strings.split(" ");
    var arr1 = arr[0].split("-");
    var arr2 = arr[1].split(":");
    var year = arr1[0];
    var month = arr1[1]-1;
    var day = arr1[2];
    var hour = arr2[0];
    var mon = arr2[1];
    var timestamp = new Date(year,month,day,hour,mon).getTime()/1000;
    return timestamp;
}
//unix 转换成 yyyy-mm-dd hh:mm
function unixToTime(UNIX_timestamp, splitMark, type){
    var splitMark = arguments[1] || '.';
    var a = new Date(UNIX_timestamp * 1000);
    var months = ['01','02','03','04','05','06','07','08','09','10','11','12'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var sec = a.getSeconds();
    if( type == 1 ) {
        var time = year + splitMark + month + splitMark + addZero(date);
    } else {
        var time = year + splitMark + month + splitMark + addZero(date) + ' ' + addZero(hour) + ':' + addZero(min) + ':' + addZero(sec) ;
    }
    return time;
}

//传进来10以下的字符，就返回前面带一个'0'
function addZero(num){
	if(num<10){
		return '0'+ num;
	} else {
		return num;
	}
}

//触屏点击反馈【$('dom'),class】
function touch(target,className){
	target.on('touchstart',function(){
        $(this).addClass(className);
    }).on('touchend touchcancel',function(){
        $(this).removeClass(className);
    })
}

//让div随滚轮往下滑固定在窗口最上面
function fixDiv(target, classname, distance){

	 var scrollTop = 0;

 	 $(window).scroll(function(e){
 	 	scrollTop = document.documentElement.scrollTop > 0 ? document.documentElement.scrollTop : document.body.scrollTop;
        if (scrollTop >= distance){
            target.addClass(classname);
        } else if (scrollTop < distance){
            target.removeClass(classname);
        }
    })

}

//锚点过渡动画
function anchorMove(){
	
	var isProceed = false;
	
	$('a[href*=#]').click(function () {
		isProceed = true;
		if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
			var $target = $(this.hash);
			$target = $target.length && $target || $('[name=' + this.hash.slice(1) + ']');
			if ($target.length) {
				var targetOffset = $target.offset().top;
				$('html,body').animate({
						scrollTop: targetOffset
					},
					300,function(){isProceed = false;});
				return false;
			}
		}
		
		
	});
	
	//滚动页面 标签跟随
	$(window).bind('scroll',function(){
		if(!isProceed){
			var top = $(window).scrollTop();
			if(top < Math.floor($('#catelog').offset().top)) {
				$('#detail_tab a').removeClass('active');
				$('#detail_tab a:eq(0)').addClass('active')
			} else if (top < Math.floor($('#attention').offset().top)) {
				$('#detail_tab a').removeClass('active');
				$('#detail_tab a:eq(1)').addClass('active')
			} else if (top < Math.floor($('#comment').offset().top)) {
				$('#detail_tab a').removeClass('active');
				$('#detail_tab a:eq(2)').addClass('active')
			} else if (top < Math.floor($('#comment').offset().top) + 475) {
				$('#detail_tab a').removeClass('active');
				$('#detail_tab a:eq(3)').addClass('active')
			}
		}
	
	});   
}

function openWindow(url) {
	var a = document.createElement("a");
	a.setAttribute("href", url);
	a.setAttribute("target", "_blank");
	a.setAttribute("id", "openwin");
	document.body.appendChild(a);
	a.click();
}

//倒数
var TimeCountMan = function(target,t,callback) {
	var time = t ;
	var f  = function(){
		target.html(t--);
		if(t < 0){
			clearTimeout(f);
			callback();
			return;
		}
		setTimeout(f,1000);
	}
	f();
}

//判断底部导航是否在浏览器的最底部,不在就放在最下面
function setFixed(){
    var bodyW = $('body').height();
    var documentW = $(document).height();
    if (bodyW < documentW){
        $('body').css({height:documentW+'px',position:'relative'});
        $('#foot_bottom').css({position:'absolute',bottom:'0',left:'0'});
    }
}

function touchReact(){

	$('.j-touch').on('touchstart',function(){
		$(this).addClass('touch');
	}).on('touchend touchcancel',function(){
		$(this).removeClass('touch');
	})

}

window.fixSubnav = function(type) {

	var $subnav = $('#j_top_bar');
	var $window = $(window);
	var subnavTop = $subnav.offset() ? $subnav.offset().top : 0;
	var topOld = $(window).scrollTop();
	var topNew = 0;
	var subnavHeight = $subnav.outerHeight();

	var subnavAnimEnd = true; // 是否动画完成
	$window.scroll(function () {
		topNew = $window.scrollTop();
		scrollLeft = $window.scrollLeft();
		if (topNew > subnavTop) {
			$subnav.addClass('fixed').css('left', -scrollLeft);
		} else {
			$subnav.removeClass('fixed').css('left', 0);
		}

		// fixed 且会随滚动收展
		if(type === 1) {
			// 向下滚动(收起)
			if(topNew > topOld) {
				if(topNew > subnavTop + subnavHeight) {
					if(subnavAnimEnd == true) {
						subnavAnimEnd = false;
						$subnav.animate({top: -subnavHeight}, 200, 'linear', function() {subnavAnimEnd = true;});
					}
				} else if (topNew > subnavTop && topNew < subnavTop + subnavHeight) {
					$subnav.stop().css({top: 0});
					subnavAnimEnd = true;
				}
			}
			// 向上滚动(展开)
			else {
				if (topNew > subnavTop) {
					if(subnavAnimEnd == true) {
						subnavAnimEnd = false;
						$subnav.animate({top: 0}, 200,'linear', function() {subnavAnimEnd = true;});
					}
				} else if(topOld > subnavTop) {
					$subnav.stop().css({top: subnavTop});
					subnavAnimEnd = true;
				}
			}
		}
		topOld = topNew;
	});
};


window.isMac = function () {
    return navigator.platform.indexOf('Mac') !== -1;
};

$(document).ready(function(){
   if (isMac()) {
	    $('body').addClass('mac');
	  }
})

/*
 *  获取倒计时
 * */
function getCountdown(now){
    //获取时，分，秒
    var hour = parseInt(now / 60 / 60);
    var minute = parseInt(now / 60 % 60);
    var second = now - hour * 60 * 60 - minute * 60;
    //小于10的展示形态
    if (hour < 10){
        hour = '0'+ hour;
    }
    if (minute < 10){
        minute = '0' + minute;
    }
    if (second < 10){
        second = '0' + second;
    }
    if (hour == 00 && minute == 00 && second == 00){
        return false;
    } else {
        return hour + '时' + minute + '分' + second + '秒';
    }
}


/*验证*/
var Verify  = new Object();

Verify.checkNickName = function(nickName){
	/*昵称验证*/
	var nickNameReg = /^([0-9a-zA-Z]|[\u4E00-\u9FA5])*$/;
    if (nickName == undefined || nickName == '') {
        return false;
    }
    if (!nickNameReg.test(nickName)) {
    	return false
    }
    return true;

}
/*验证*/

// 上传文件
function Uploader (options) {
    var _this = this;
    this.uploading = false;
    this.fileMaxSize = 200;

    this.progressBox = options.progressBox;
    this.progressBar = this.progressBox.find('.bar');
    this.progressNum = this.progressBox.find('.num');
    this.btnUpload = options.btnUpload;
    this.btnSave = options.btnSave;

    this.fileUrlInput = options.fileUrlInput;
    this.packageId = options.packageId;
    this.basePath = options.basePath || 'web/material';

    this.bosConf = {
        bucket: 'kaochong',
        endpoint: 'http://bj.bcebos.com',
        ak: '4f2625feb27144b1aab825365968bac7',
        sk: '94e92f73fa0b41c1aff8b2a3ae1c37e3',
        cdn: 'http://kaochong.cdn.bcebos.com'
    };
    this.bosUploader = new baidubce.bos.Uploader({
        browse_button: '#file',
        bos_bucket: this.bosConf.bucket,
        bos_endpoint: this.bosConf.endpoint,
        bos_ak: this.bosConf.ak, 
        bos_sk: this.bosConf.sk,
        max_file_size: this.fileMaxSize + 'M',
        bos_multipart_min_size: '0',
        chunk_size: '1M',
        init: {
            FilesFilter: function (_, files) {
                var file = files[0];
                if (!file) {
                    alert('禁止上传文件大小超过'+ _this.fileMaxSize +'M的文件');
                    return false;
                }
            },
            FileUploaded: function (_, file, info) {
                _this.fileUrlInput.val(_this.bosConf.cdn + '/' + info.body.object);
            },
            UploadComplete: function() {
                // 上传结束
                _this.uploading = false;
                _this.btnSave.removeClass('disable');
            },
            BeforeUpload: function (_, file) {
                _this.progressBox.show();
                _this.progressBar.width(0);
                _this.uploading = true;
                _this.btnSave.addClass('disable');
            },
            UploadProgress: function (_, file, progress, event) {
                var p = Math.floor(progress * 100) + '%';
                _this.progressBar.width(p);
                _this.progressNum.text(p);
            },
            Key: function (_, file) {
                var key = _this.basePath;
                key += '/' + _this.packageId;
                key += '/' + file.name;
                return key;
            },
            Error: function (_, error, file) {
                alert('文件上传失败，请重新上传！');
                console.log(JSON.stringify(error));
            }
        }
    });
    // 点击上传按钮开始上传
    this.btnUpload.click(function () {
        _this.bosUploader.start();
        _this.progressBox.show();
        return false;
    });
}

//转换时间戳到"2015-02-08 06:58"
function timeConverter(timestamp) {
    var a = new Date(timestamp * 1000);
    var year = a.getFullYear();
    var month = a.getMonth() + 1;
    if (month < 10) {
        month = '0' + month;
    }
    var date = a.getDate();
    if (date < 10) {
        date = '0' + date;
    }
    var hour = a.getHours();
    if (hour < 10) {
        hour = '0' + hour;
    }
    var min = a.getMinutes();
    if (min < 10) {
        min = '0' + min;
    }

    var time = year + '-' + month + '-' + date + ' ' + hour + ':' + min;

    return time;
}


function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}