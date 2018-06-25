$(document).ready(function(){
	var currentPage = getParameter('pno');
    var courseId = getParameter('courseId');
	$('#j-cr-close').click(function() {
		$('#j-cr-con').hide();
		$('#j-admin-comment-text').val('');
	})
	var commentReply = new CommentReply();
	
	$('#j-submit-rcom').click(function() {
		commentReply.setContent( $('#j-admin-comment-text').val() );
	});
	
	fillData($('#j-data-fill'), commentReply);
})
function countWord() {
	$('#j-now-word').html(  document.getElementById('j-admin-comment-text').value.replace(/\s+/g,"").length );
}
var fillData = function(ul, commentReply){
	var dataContainer     = ul,
	    itemsHtml         = '',
	    pageNo            = getParameter('pno'),
        courseId          = getParameter('courseId'),
		price             = null,
		payTime	          = null;

	if (!pageNo) {
        pageNo = 1 ;
    }
	
	ajax({
		url:'/api/comment/list?pageSize=20&pageNum=' + pageNo + '&courseId=' + courseId
	}).done(function(data) {
		//翻页控件初始化~
		setPager(data.page.pageCount, data.page.totalCount);
		ul.empty();
		$.each(data.results,function(i,item) {
			itemsHtml = $(tpl);
			itemsHtml.find('#id').html(item.id);
			itemsHtml.find('#name').html(item.name);
			itemsHtml.find('#content').html(item.content).addClass('content_' + item.id);
			if(item.reply) {
				itemsHtml.find('#reply').html(item.reply);
			}
			itemsHtml.find('#j-reply').data('id', item.id).click(function() {
				$('#j-usr-comm-text').html( $('.content_' + item.id).html() );
				commentReply.activeReplyBox( $(this).data('id') );
			});
			itemsHtml.find('#j-del').click(function() {
				delComment(item.id);
			});
			itemsHtml.find('#j-query').click(function() {
				queryUser(item.uid, $(this));
			});
			dataContainer.append(itemsHtml);
		})
		
	},function(){})
}

var setPager = function (pageCount, totalCount) {
    var totalPage         = parseInt(pageCount),
        totalRecords      = totalCount,
        pageNo            = getParameter('pno'),
        courseId          = getParameter('courseId');

    if (!pageNo) {
        pageNo = 1;
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
     	   return this.hrefFormer + this.hrefLatter + "?pno=" + n + '&courseId=' + courseId;
        }
	});
};

function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

function delComment(id) {

	if (!confirm('确定删除？')) {
		return;
	}

	ajax({
		url:'/api/comment/remove?id=' + id
	}).done(function(data){
		alert('删除成功！');
		location.reload(true);
	}, function(){})

}
function queryUser(uid, currObj) {
	var userUrl =  '/api/user/get?uid=' + uid;
	ajax({
		url: userUrl
	}).done(function(data){
		showUserBox(data, currObj);
	}, function(){})
}

function showUserBox(data, currObj) {

	$('#j-user-nickname').html(data.results.nickname);
	$('#j-user-tel').html(data.results.tel);
	$('#j-user-box').animate({
		top: currObj.position().top,
		left: currObj.position().left - $('#j-user-box').width() - 10,
	},100).show();
	
	$('#j-close-user-box').click(function() {
		$('#j-user-box').hide();
		$('#j-user-tel').val('');
		$('#j-user-nickname').html('---');
	})

}



var CommentReply = function() {
	var _id = 0;
	var _content = '';

	this.activeReplyBox = function(id) {
		_id = id;
		$('#j-cr-con').show();
	}
	
	this.setContent = function(content) {
		_content = content;
		replyComment();
	}
	
	function replyComment() {
		ajax({
			url: "/api/comment/reply?",
			data:{
				     content: _content,
				     commentId: _id,

				 },
			type: 'post',
		}).done(function(data) {
			alert('回复成功！');
			location.reload(true);
		}, function() {})
	}
}


var tpl = '<li class="row">\
	                    <div>\
	                        <ul class="item_ul nav">\
	                            <li class="id_width" id="id"></li>\
	                            <li class="title_width" id="name"></li>\
	                            <li class="price_width" id="content" style="width:350px"></li>\
								<li class="price_width" id="reply" style="width:300px"></li>\
	                            <li class="op_width comment_op_hd" style="cursor:pointer;"><span id="j-reply">回复</span>&nbsp&nbsp&nbsp<span id="j-del">删除</span>&nbsp&nbsp&nbsp<span id="j-query">查询</span></li>\
	                        </ul>\
	                    </div>\
	                </li>';
