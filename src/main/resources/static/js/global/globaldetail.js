function Detail() {
	
	this.submit = function(formId) {
		return function() {
			ajax({
				url : '/api/global/add',
				type : 'post',
				data : $('#' + formId).serialize()
			}).done(function(data) {
				alert('保存成功');
				window.location.href='/global';
			}, function() {
				alert('保存失败');
			});
		}
	}
}

$(document).ready(function() {
	var detail = new Detail();
	
	$('#save').click(detail.submit('form_global'));
	
	$('#cancel').click(function() {
		if (confirm('确定取消？')) {
			window.location.href='/global';
		}
	});
});
