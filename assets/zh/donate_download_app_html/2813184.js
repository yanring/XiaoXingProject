var PP = (function() {
	var eventType = null;
	return {
		init: function() {
			var isSupportTouch = !!('ontouchend' in document);
    		eventType = isSupportTouch ? 'touchend' : 'click';

			// 快速下载模块
			// if (document.getElementById("quickTemplate")) {
			// 	this.choseTemplate();
			// 	this.bindQuickBtnEvent();
			// }

			// // 首页服务升级提醒模块
			// if (document.getElementById("promoDialog")) {
			// 	this.showPromoDialog();
			// 	this.bindDialogEvent();
			// }
                        //需要nav_search.html中加入以下代码
                        //<p id="promoDialog" class="logo-tips"><a id="konwBtn" href="#" class="close" alt="知道了">知道了</a></p>

			// ca 认证模块
			if (document.getElementById("caBtn")) {
				this.bindCaEvent();
			}
		},

		choseTemplate: function() {
			var filePath = document.getElementById("filePath").value;
			var fileSuffix = filePath.substr(filePath.lastIndexOf(".") + 1);
			var agent = navigator.userAgent.toLowerCase();
			var bowserNum = agent.indexOf('ucbrowser') > -1 ? agent.split('ucbrowser\/')[1].split(' ')[0] : false;
			if(fileSuffix == "apk" && !!(window.ucweb) && bowserNum && (!!bowserNum.match(/^9\.9\.[3-9]/) || !!bowserNum.match(/^\d{2,}\.\d+\.\d+/))){
		       document.getElementById("quickTemplate").style.display = "block";
		       document.getElementById("normalTemplate").style.display = "none";
		    } else {
		       document.getElementById("quickTemplate").style.display = "none";
		       document.getElementById("normalTemplate").style.display = "block";
		    }
		},

		bindQuickBtnEvent: function() {
			var quickBtn = document.getElementById("quickBtn");
			if (!!quickBtn) {	
				quickBtn.addEventListener(eventType, function(e) {
					var url = document.getElementById("downloadUrl").href;
					var ts = url.split("app=");
					var quickUrl = ts[0] + "app=1000" + ts[1].substr(ts[1].indexOf("&")) + "&seq=" + Date.parse(new Date());
					var quickFilename = document.getElementById("quickFilename").value;
					var filePath = document.getElementById("filePath").value;
					var fileSuffix = filePath.substr(filePath.lastIndexOf(".") + 1);
					if (window.ucweb && window.ucweb.startRequest) {
						window.ucweb.startRequest('shell.pp.download',[quickUrl, quickFilename + "." + fileSuffix, '2']);
					}
				});
			}
		},

		showPromoDialog: function() {
			if (!localStorage.getItem('isPromoDialogShowed')) {
				document.getElementById("promoDialog").style.display = "inline-block";
				localStorage.setItem("isPromoDialogShowed", true);
			}
		},

		bindDialogEvent: function() {
			var konwBtn = document.getElementById("konwBtn");
			if (!!konwBtn) {
				konwBtn.addEventListener(eventType, function(e) {
					document.getElementById("promoDialog").style.display = "none";
				});
			}
		},

		bindCaEvent: function() {
			var caBtn = document.getElementById("caBtn");
			if (!!caBtn) {
				caBtn.addEventListener(eventType, function(e) {
					document.getElementById("caDetail").style.display = "block";
					document.getElementById("caBg").style.display = "block";
				});
			}

			var caBg = document.getElementById("caBg");
			if (!!caBg) {
				caBg.addEventListener(eventType, function(e) {
					document.getElementById("caDetail").style.display = "none";
					document.getElementById("caBg").style.display = "none";
				});
			}
		}
	}
})();

PP.init();