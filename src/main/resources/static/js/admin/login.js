var loginBtn;
var loginForm;
$(function () {
    loginForm = $("#loginForm");
    loginBtn = $("#login");
    loginBtn.click(function () {
        login();
    });
});

function login() {
    if (util.verify(loginForm)) {
        $.ajax({
            type: "post",
            url: "/admin/login",
            data: loginForm.serialize() ,
            success: function (result, status, xhr) {
                if (xhr.status === 200) {
                    window.location = "/admin/index";
                }
            },
            error: function (xhr) {
                if (xhr.status === 401) {
                    util.popWithDelay(loginBtn, "登录失败，用户名或密码错误", 3);
                }else{
                    util.popWithDelay(loginBtn, "登录失败，未知错误", 3);
                }
            }
        })
    }
}