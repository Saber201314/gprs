<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="/cssjs.jsp"></jsp:include>
<script src="/res/paste-upload-image.js"></script>
<style>
body{
	padding: 0 15px;
}

</style>
<body>
<div contenteditable="true" id="remark" name="remark" style="background-color:#f9f9f9;margin-top:50px; border:1px solid #ccc; transition:border linear .2s,box-shadow linear .2s; padding:4px 6px; color:#555;word-wrap: break-word;white-space: pre-wrap;width: 60%;margin-top: 0;margin-right: 5px; height:200px; overflow:auto;">


</div>
</body>

    
<script>
window.addEventListener('load', function (e) {
		document.body.onpaste = function (e) {
			var items = e.clipboardData.items;
			for (var i = 0; i < items.length; ++i) {
				item = items[i];
				if (item && item.kind === 'file' && item.type.match(/^image\//i)) {
					imgReader(item);
					break;
				}
			}
		};
	});

	var imgReader = function (item) {
		var blob = item.getAsFile(),
        reader = new FileReader();

		reader.onload = function (e) {
			var img = new Image();

			img.src = e.target.result;
			var logBox = document.getElementById('remark');
			logBox.appendChild(img);
		};

		reader.readAsDataURL(blob);
	};

</script>

