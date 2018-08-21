(function() {

	var $container = null,
		pull = null;

	var PAGE_SIZE = 15,
		seq = 0;

	function query(clean, cb) {
		// 模拟异步查询数据
		setTimeout(function() {
			if(clean) {
				$container.children().remove();
				seq = 0;
			}

			for(var i = 0; i < PAGE_SIZE; i++) {
				seq++;
				var html =
					'<div class="notifications">' +
					'<div class="persontip gray12txt">支付人</div>' +
					'<div class="persontip black14txt">张三</div>' +
					'<div class="persontip gray12txt center">付款金额</div>' +
					'<div class="persontip black30txt center">¥99.00</div>' +
					'<div class="linedashed math15"></div>' +
					'<div  class="itemwarp" style="margin-top: 0.5rem;">' +
					'<div class="itemleft gray12txt">商品：</div>' +
					'<div class="itemright black12txt">XX商品</div>' +
					'</div>' +
					'<div  class="itemwarp">' +
					'<div class="itemleft gray12txt">商品名称：</div>' +
					'<div class="itemright black12txt">商品名称</div>' +
					'</div>' +
					'<div  class="itemwarp">' +
					'<div class="itemleft gray12txt">当前状态：</div>' +
					'<div class="itemright black12txt">支付成功</div>' +
					'</div>' +
					'<div  class="itemwarp">' +
					'<div class="itemleft gray12txt">时间：</div>' +
					'<div class="itemright black12txt">2016-12-12</div>' +
					'</div>' +
					'<div  class="itemwarp" style="padding-bottom: 0.5rem;">' +
					'<div class="itemleft gray12txt">订单号：</div>' +
					'<div class="itemright black12txt">1234567890</div>' +
					'</div>' +
					'</div>';
				$container.append(html);
			}

			var hasNext = seq <= 50; // 假设只有50条数据
			cb && cb(hasNext);
		}, 2000);
	}

	function init() {
		$container = $('#list');
		pull = new Pull({
			scrollArea: window,
			scroller: document.querySelector('#scroll'),
			pullUp: function(that) {
				query(false, function(hasNext) {
					that.refresh({
						lockUp: false, // 释放上拉锁定
						hasNext: hasNext
					});
				});
			},

			pullDown: function(that) {
				query(true, function(hasNext) {
					that.refresh({
						lockDown: false, // 释放下拉锁定
						hasNext: hasNext
					});
				});
			}
		});

		query(false, function(hasNext) {
			pull.refresh({
				hasNext: hasNext
			});
		});
	}

	$(init);

}());