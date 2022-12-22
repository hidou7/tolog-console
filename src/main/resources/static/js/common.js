/**
 * 通用js方法封装处理
 * Copyright (c) 2019 ruoyi
 */
(function ($) {
    $.extend({
        // 通用方法封装处理
        common: {
            trim: function (value) {
                if (value == null) {
                    return "";
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
            },
            // 判断字符串是否为空
            isEmpty: function (value) {
                if (value == null || this.trim(value) == "" || value == undefined || value == "undefined") {
                    return true;
                }
                return false;
            },
            // 判断一个字符串是否为非空串
            isNotEmpty: function (value) {
                return !$.common.isEmpty(value);
            },
            checkToken: function(){
                if(this.isEmpty(this.getToken())){
                    return false;
                }
                return true;
            },
            getToken: function () {
                let token = localStorage.getItem("token");
                let expire = localStorage.getItem("expire");
                if(this.isEmpty(token) || new Date().getTime() >= expire){
                    token = null;
                }
                return token;
            }
        }
    });
})(jQuery);

/** 消息状态码 */
StatusCode = {
    success: 200,
    notLogin: 401
};