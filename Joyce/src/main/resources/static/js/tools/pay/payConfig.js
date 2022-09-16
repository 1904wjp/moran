$().ready(function () {
    $("#add_dayTask").hide();
    getDayTaskTable();
})

/**
 * 添加每日任务
 */
function addPayConfig() {
    var data = {
        openApiDomain: $('#openApiDomain').val(),
        openApiDomainDev: $('#openApiDomainDev').val(),
        mcloudApiDomain: $('#mcloudApiDomain').val(),
        pid: $('#pid').val(),
        pidDev: $('#pidDev').val(),
        appid: $('#appid').val(),
        appidDev: $('#appidDev').val(),
        privateKey: $('#privateKey').val(),
        publicKey: $('#publicKey').val(),
        alipayPublicKey: $('#alipayPublicKey').val(),
        alipayPublicKeyDev: $('#alipayPublicKeyDev').val(),
        signType: $('#signType').val(),
        maxQueryRetry: $('#maxQueryRetry').val(),
        queryDuration: $('#queryDuration').val(),
        maxCancelRetry: $('#maxCancelRetry').val(),
        cancelDuration: $('#cancelDuration').val(),
        heartbeatDelay: $('#heartbeatDelay').val(),
        heartbeatDuration: $('#heartbeatDuration').val(),
        notifyUrl: $('#notifyUrl').val(),
        status: $('#status').val(),
        id: $('#id').val()
    }
    $.ajax({
        url: '/example/pauv/justTasks',
        type: 'GET',
        dataType: 'json',
    }).done(function (data) {
        tips(data.rs, data.msg)
    }).fail(function () {
        tips(false, ajaxFailMsg);
    });
}