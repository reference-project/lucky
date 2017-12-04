<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>抽奖游戏</title>
</head>
<body>
<p>${member.name!}，您好，你已成功加入游戏，等待游戏开始</p>
<#if member??&&member.special>
	<button onclick="wantToCheck()">我要中奖</button>
	<script type="text/javascript">
	    function wantToCheck() {
	        $.ajax({
	            type: 'post',
	            url: '/game/willBeCheck?memberId=${memberId!}',
	            success: function (data) {
	                alert(data.errorText);
	            }
	        });
	    }
	</script>
</#if>
</body>
</html>