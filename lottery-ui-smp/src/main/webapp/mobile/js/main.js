$(document).ready(function() {
    $('.ul_auto_wrap .auto_width').each(function(index, ele) {
        var items = $(ele).find('li');
        var itemWidth = 0;
        items.each(function(index, ele2) {
            if ( items.eq(index).hasClass('hidden') ) {
            }
            else{
                itemWidth = itemWidth + $(ele2).outerWidth(true);
            }
        });
        $(ele).width(itemWidth+15);

    });
    $('.layout_form .form_field_password .right_label').click(function() {
        var $this = $(this);
        var $form_field_item = $this.parents('.form_field_password');
        $form_field_item.toggleClass('opened');
        if ($form_field_item.hasClass('opened')) {
            $form_field_item.find('.form_field_input input').prop('type', 'text');
        } else {
            $form_field_item.find('.form_field_input input').prop('type', 'password');
        }
    });
    $('.dialog_close').click(function() {
        $(this).parents('.dialog_wrap').hide();
    });
    $('#call_back_trigger').click(function() {
        $('.dialog_call_back_option').show();
        $('#trigger_call_mobile_input').click(function() {
           $('.dialog_wrap').hide();
            $('.dialog_call_mobile_input').show();
        });
    });
    $('.date_picker').each(function(index, ele) {
        var $date_picker = $(ele);
        var $date_picker_value = $date_picker.find('.date_picker_value');
        var $date_picker_real = $date_picker.find('.date_picker_real');
        $date_picker_real.on('focus', function(e) {
            $date_picker.addClass('show_picker');
        });
        $date_picker_real.on('focusout', function(e) {
            $date_picker.removeClass('show_picker');
        });
        $date_picker_real.on('change', function(e) {
            if (this.value) {
                var date_picked = new Date(this.value);
                if ( !isNaN(date_picked.getFullYear()) ) {
                    $date_picker_value.val(date_picked.getFullYear()+"年"+(date_picked.getMonth()+1)+"月"+date_picked.getDate()+"日");
                    $date_picker.removeClass('show_picker');
                }
                else{
                    $date_picker_value.val( $date_picker_real.val() );
                }
            }
        })
    });

    //判断登录状态
    $(document).on('click','.j-isLogin',function(){
        if(sessionStorage.getItem('permission')==='0'){
            toast_tip('请先登录账户！！');
            return false;
        }
    });

    //后退
    $(document).on('click','[data-back]',function(){
        history.go(-1);
        return false;
    });

    //关闭弹窗
    $('.j-indexTips').on('click', function() {
        $(this).remove();
    });
    //礼品
    $("#gift").on('click',function(){
        if($("#toolbar .j-logout").length>0){
                $.post('/asp/isExistGift.php',function (data) {
                    //获取礼品列表
                    if(data.success && data.data){
                        //没有领取,获取礼品列表
                        var id=data.data.id;
                        $("#gift_id").val(id);
                        $.post('/asp/canApplyGift.php',{giftID:id},function (data) {
                            if(data.success){
                                $("#gift_box").removeClass("hidden");
                            }else{
                                toast_tip(data.message);
                            }
                        }).error(function(){
                            toast_tip("查询数据失败！");
                        });
                    }else{
                        toast_tip(data.message);
                    }
                })
        }else{
            return ;
        }
    })
    //提交礼品
    function gift_submit(){
        var formdata={
            giftID:$("#gift_id").val(),
            addressee:$("#gift_name").val(),
            address:$("#gift_add").val(),
            cellphoneNo:$("#gift_tel").val()
        }
        if(!formdata.giftID){
            alert("无礼品ID,请刷新后重试！");
            return;
        }
        if(!formdata.addressee){
            alert("请输入姓名！");
            return;
        }
        if(!formdata.address){
            alert("请输入邮寄地址！");
            return;
        }
        if(!formdata.cellphoneNo){
            alert("请输入手机号码！");
            return;
        }
        $.ajax ({
            url: "/asp/applyGift.php",
            cache: true,
            dataType: "text",
            data: formdata,
            type: 'POST',
            success: function (data) {
                toast_tip(data);
                $("#gift").attr("data-get","true")
                $("#gift_box").addClass("hidden");
            }})
    }
    function gift_cancle(){
        $("#gift_box").addClass("hidden");
    }

});

/** 
* 英文日期字符串转化为数字日期  
* Ranyut  2011-5-30 
**/  
function pasreEnDate(dateStr) {
    var mm = dateStr.slice(0, 3).toUpperCase();  
    var dd = dateStr.slice(4, 6);
    dd = dd.replace(',','');

    var em = [];
    em['JAN'] = 1;
    em['FEB'] = 2;
    em['MAR'] = 3;
    em['APR'] = 4;
    em['MAY'] = 5;
    em['JUN'] = 6;
    em['JUL'] = 7;
    em['AUG'] = 8;
    em['SEP'] = 9;
    em['OCT'] = 10;
    em['NOV'] = 11;
    em['DEC'] = 12;


    var date = [];

    if (em[mm] > 0) {
        date = [ em[mm], dd, getDateNum(em[mm], dd) ];
    }
    else{
        var array = dateStr.split("-");

        date[0] = array[1];
        date[1] = array[2];
        date[2] = getDateNum(array[1], array[2]);
    }

    return date;  
}

function getDateNum(mm,dd){
    var date = new Date();
    var daysTotal = 0;
    var year = date.getFullYear();//当前年份
    var _month = date.getMonth() + 1;//当前月份
    var _day =date.getDate();//当前天数
    var month = parseInt(mm);//生日月份
    var day = parseInt(dd);//生日天数
    if (month > _month)
    {
        for (var j = _month; j < month; j++)
        {
            daysTotal = TotalDays(year, j, daysTotal);
        }
        daysTotal = daysTotal + day - _day;
    } else if (month < _month) {
        for (var k = month; k < _month; k++) {
            daysTotal = TotalDays(year,k, daysTotal);
        }
        var dayFull = TotalDays(year, 0, daysTotal);
        daysTotal = dayFull - daysTotal + day - _day;
    } else
    {
        if (day >= _day) {
            daysTotal = day - _day;
        } else
        {
            daysTotal = TotalDays(year, 0, daysTotal) - (_day - day);
        }
    }

    return daysTotal;
}

function isLeapYear(Year) {
    if (((Year % 4) == 0) && ((Year % 100) != 0) || ((Year % 400) == 0)) {
        return (true);
    } else { return (false); }
}

function TotalDays(year,month,days)
{
    switch (month) {
        case 1:
        case 3: 
        case 5: 
        case 7: 
        case 8: 
        case 10:
        case 12:
            days += 31;
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            days += 30;
            break;
        case 2:
            if (isLeapYear(year)) {
                days += 29;
            } else {
                days += 28;
            }
            break;
        default:
            if (isLeapYear(year)) {
                days = 366;
            } else {
                days = 365;
            }
            break;
    }
    return days;
}


// toast 提示， msg是内容，type是类型，1代表哭脸, 2代表笑脸;
function toast(msg, type) {
    $('.toast_wrap').remove();
    var toastclass = 'error';
    if (type == 1) {
        toastclass = 'error'
    } else if (type == 2) {
        toastclass = 'success'
    }
    var html = '<div class="toast_wrap ' + toastclass + '"><p>'+msg+'</p></div>';
//    $(html).appendto('body');
    $('body').append(html);
    $('.toast_wrap').delay(4000).fadeOut(1000);
}
function toast_tip(msg) {
    $('.toast_wrap').remove();
    var toastclass = 'no_icon';
    var html = '<div class="toast_wrap_no_icon"><div class="toast_wrap ' + toastclass + '"><p>'+msg+'</p></div></div>';
//    $(html).appendto('body');
    $('body').append(html);
    $('.toast_wrap').delay(3000).fadeOut(500);
}

// 提示并且跳转地址
function toast_tip2(msg,url) {
    $('.toast_wrap').remove();
    var toastclass = 'no_icon';
    var html = '<div class="toast_wrap_no_icon"><div class="toast_wrap ' + toastclass + '"><p>'+msg+'</p></div></div>';
    $('body').append(html);
    $('.toast_wrap').delay(3000);
    setTimeout(function(){
        window.location = url; 
    }, 2000);
}



// 提示并且跳转goback
function toast_tip3(msg) {
    $('.toast_wrap').remove();
    var toastclass = 'no_icon';
    var html = '<div class="toast_wrap_no_icon"><div class="toast_wrap ' + toastclass + '"><p>'+msg+'</p></div></div>';
    $('body').append(html);
    $('.toast_wrap').delay(3000);
    setTimeout(function(){
        history.go(-1);
    }, 2000);
}

// toolbar
(function() {
    var $toolbar = $('#toolbar');
    function _init() {
        $('[data-live-action]').on('click', function() {
            $toolbar.show();
            setTimeout(function() {
                $toolbar.addClass('active');
            }, 100);
            $toolbar.find('.tool-close').on('click', function() {
                $toolbar.removeClass('active');
                setTimeout(function() {
                    $toolbar.hide();
                }, 400);
            });
            /*   $toolbar.find('.j-qq').click(openQQ);
             $toolbar.find('.j-cs').click(openLive800);
             $toolbar.find('.j-email').click(openEmail);*/
            $toolbar.find('.j-logout').click(logout);
        });
    }
    function _close() {
        $toolbar.find('.tool-close').trigger('click');
    }
    function openEmail() {
        //webapp.redirect('mailto:vip@lehu.ph');
    }
    function openQQ() {
        var download = getMobileKind() == 'Android' ? 'http://gdown.baidu.com/data/wisegame/dc429998555b7d4d/QQ_398.apk' : 'https://itunes.apple.com/cn/app/qq-2011/id444934666?mt=8';
        //webapp.redirect('mqq://im/chat?chat_type=wpa&uin=800104622', '尚未安装QQ！', download);
    }
    function openLive800() {
        //mobileManage.redirect('online800');
    }
    function logout() {
        mobileManage.getLoader().open('登出中');
        mobileManage.getUserManage().logout( function(data){
            if(data.code=='10000'){
                sessionStorage.setItem('permission',0);
                sessionStorage.setItem('notice',1);
                mobileManage.getLoader().close();
                window.location = '/mobile/index.jsp'

            }else{
                toast_tip(data.message);
                mobileManage.getLoader().close();
            }
        });
    }

    _init();
})();
