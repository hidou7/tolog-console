/**
 * 通用js方法封装处理
 * Copyright (c) 2019 ruoyi
 */
(function ($) {
    $.extend({
        // 通用方法封装处理
        common: {
            trim(value) {
                if (value == null) {
                    return "";
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
            },
            // 判断字符串是否为空
            isEmpty(value) {
                if (value == null || this.trim(value) == "" || value == undefined || value == "undefined") {
                    return true;
                }
                return false;
            },
            // 判断一个字符串是否为非空串
            isNotEmpty(value) {
                return !$.common.isEmpty(value);
            },
            checkToken(){
                if(this.isEmpty(this.getToken())){
                    return false;
                }
                return true;
            },
            getToken() {
                let token = localStorage.getItem("token");
                let expire = localStorage.getItem("expire");
                if(this.isEmpty(token) || new Date().getTime() >= expire){
                    token = null;
                }
                return token;
            },
            // $.common.parseTime(1672038725974, '{y}-{m}-{d} {h}:{i}:{s}');
            // $.common.parseTime(1672038725974, '{y}-{m}-{d} {h}:{i}:{s}', 7);
            parseTime(time, cFormat, zone = 8) {
                if (arguments.length === 0) {
                    return null;
                }
                const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}';
                let date;
                if (typeof time === 'object') {
                    date = time;
                } else {
                    if (('' + time).length === 10) time = parseInt(time) * 1000;
                    date = new Date(time);
                }
                // 时区调整
                const utc = time + new Date(time).getTimezoneOffset() * 60000;
                const wishTime = utc + (3600000 * zone);
                date = new Date(wishTime);
                const formatObj = {
                    y: date.getFullYear(),
                    m: date.getMonth() + 1,
                    d: date.getDate(),
                    h: date.getHours(),
                    i: date.getMinutes(),
                    s: date.getSeconds(),
                    a: date.getDay()
                }
                return format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
                    let value = formatObj[key];
                    // Note: getDay() returns 0 on Sunday
                    if (key === 'a') {
                        return ['日', '一', '二', '三', '四', '五', '六'][value];
                    }
                    if (result.length > 0 && value < 10) {
                        value = '0' + value;
                    }
                    return value || 0;
                })
            }
        }
    });
})(jQuery);

/** 消息状态码 */
StatusCode = {
    success: 200,
    notLogin: 401
};