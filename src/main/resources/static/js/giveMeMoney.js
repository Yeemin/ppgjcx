$().ready(function () {
    $('#giveMeMoney').on('click', function () {
        let moneyQrcode = 1
        let contentHtml = '<p id="giveMeMondyText">个人开发，服务器真贵，感谢赞助，截图保存，<span style="color: #1890FF">支付宝</span>扫一扫打开本地相册</p><br>'
        contentHtml += '<img id="giveMeMondyQrcode" style="width: 260px; height: 260px" src="./img/zfbskm.png"></img><br>'
        contentHtml += '<a href="javascript:" id="changeMondyQrcode">更换赞助方式</>';

        weui.dialog({
            title: '赞助一下',
            content: contentHtml,
            className: 'give-me-money',
            buttons: [{
                label: '这次一定',
                type: 'primary',
                onClick: function () {
                }
            }, {
                label: '下次一定',
                type: 'default',
                onClick: function () {
                }
            }]
        });

        $('#changeMondyQrcode').on('click', function () {
            if (moneyQrcode === 1) {
                moneyQrcode = 2
                $('#giveMeMondyQrcode').attr('src', './img/wxskm.png')
                $('#giveMeMondyText').html('个人开发，服务器真贵，感谢赞助，截图保存，<span style="color: #04BE02">微信</span>扫一扫打开本地相册')
            } else {
                moneyQrcode = 1
                $('#giveMeMondyQrcode').attr('src', './img/zfbskm.png')
                $('#giveMeMondyText').html('个人开发，服务器真贵，感谢赞助，截图保存，<span style="color: #1890FF">支付宝</span>扫一扫打开本地相册')
            }
        })
    })
})