

$(function () {
   // getUserInfo();
    // 当前所处的tab
	var pathname = window.location.pathname;
	var hash = window.location.hash;
    var query=getQueryString('showtype');
    var $tabs_nav=$(".j-userNav");
    $tabs_nav.find(".u_b_item").removeClass('active');
	if(pathname.indexOf( '/userHome.jsp')>-1 || pathname == '/'){
		$tabs_nav.find('a[data-link="userHome"]').addClass('active');
	}
	else if(pathname.indexOf( '/userMaterial.jsp')>-1){
        $tabs_nav.find('a[data-link="userMaterial"]').addClass('active');
	}
    else if(pathname.indexOf( '/userManage.jsp')>-1){
        $tabs_nav.find('a[data-link="userManage"]').addClass('active');
    }
    else if(pathname.indexOf( '/userCoupons.jsp')>-1){
        $tabs_nav.find('a[data-link="userCoupons"]').addClass('active');
    }
    else if(pathname.indexOf( '/userInfoSet.jsp')>-1){
        $tabs_nav.find('a[data-link="userInfoSet"]').addClass('active');
    }
    else if(pathname.indexOf( '/userRecords.jsp')>-1){
        $tabs_nav.find('a[data-link="userRecords"]').addClass('active');
    }
    else if(pathname.indexOf( '/userLetter.jsp')>-1){
        $tabs_nav.find('a[data-link="userLetter"]').addClass('active');
    }
});
function getUserInfo () {
    var accountName
    $.getJSON("/asp/ajaxGetSessionPersonalData.php", function (result) {
    	if(result.code=='10000'){
            var data=result.data;
            $(".j-loginname").text(data.loginname);
            $(".j-level").text(data.level).val(data.level);
            $(".j-levelnum").text(data.levelNumber).val(data.levelNumber);
            $(".j-MAIN-info").text(data.accountMoney);
            $(".j-DEPUTY-info").text(data.deputyCredit);
            $(".j-accountName").text(data.accountName).val(data.accountName);
            $(".j-email").text(data.email).val(data.email);
            $(".j-mobilenum").text(data.phone).val(data.phone);
            $(".j-qq").text(data.qq).attr('placeholder',data.qq);
            $(".j-last_login").text(data.lastLoginTime).val(data.lastLoginTime);
            // 新用户没有birthday
            try{
              $(".j-birth_date").text(data.birthday?data.birthday.slice(0,-9):'').val(data.birthday?data.birthday.slice(0,-9):'');
            }
            catch(err){
              console.log(err);
            }
            return data.accountName;
		}
	})

}
