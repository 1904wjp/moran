/**
 * toastr配置文件
 */
$(function (){
  initToastr();
});

function initToastr(){
    toastr.options.positionClass = 'toast-bottom-right';
    toastr.options = {
        closeButton: false,
        debug: false,
        progressBar: true,
        positionClass: "toast-top-center",
        onclick: null,
        showDuration: "300",
        hideDuration: "1000",
        timeOut: "2000",
        extendedTimeOut: "1000",
        showEasing: "swing",
        hideEasing: "linear",
        showMethod: "fadeIn",
        hideMethod: "fadeOut" ,
        newestOnTop: true,
        preventDuplicates: true,
        preventOpenDuplicates: true,
        maxOpened:1 ,
        allowHtml: false,
        closeButton: false,
        closeHtml: '<button>×</button>',
        extendedTimeOut: 1000,
        iconClasses: {
            error: 'toast-error',
            info: 'toast-info',
            success: 'toast-success',
            warning: 'toast-warning'
        },
        messageClass: 'toast-message',
        onHidden: null,
        onShown: null,
        onTap: null,
        progressBar: false,
        tapToDismiss: true,
        templates: {
            toast: 'directives/toast/toast.html',
            progressbar: 'directives/progressbar/progressbar.html'
        },
        timeOut: 5000,
        titleClass: 'toast-title',
        toastClass: 'toast'

    };
}

/**
 * 提示框
 * @param params1
 * @param params2
 */
function tips(params1,params2){
    if (params1===true){
        toastr.success(params2);
    }
    if (params1===false){
        initToastr();
        toastr.options.positionClass="toast-bottom-full-width";
        toastr.error(params2);
    }
    if (params1==""){
        initToastr();
        toastr.options.positionClass="toast-bottom-center";
        toastr.warning(params2);
    }
    if (params1=null) {
        toastr.info(params2);
    }
}

