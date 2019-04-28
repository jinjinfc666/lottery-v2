$ (document).ready (function () {
    var pathname = window.location.pathname;
    var hash = window.location.hash;
    var query=getQueryString('showtype');
    $('.j-agent_nav').removeClass('active');
    // console.log(pathname);
    if(pathname.indexOf( '/agent.jsp')>-1){
        $('.agent_nav a[data-link="agent"]').addClass('active');
    }
    if(pathname.indexOf( '/agentproject.jsp')>-1){
        $('.agent_nav a[data-link="agentproject"]').addClass('active');
    }
    $(".j-join").click(function(){
        getDomainName();
        $(".agent_register").show();
    });
    bannerBind();
});
//////////// 获取代理域名  ///////////////
function getDomainName() {
    $.ajax({
        url: '/asp/getDomainName.php',
        type: 'POST',
        dataType: 'json',
        success: function (json) {
            if(json.code=='10000'){
                $('#domainname').text('.' + json.data);
            }
        }
    });
};
//////////////// 轮播 /////////////////
function getBanner() {
    $.ajax ({
        url: "/asp/queryBannerList.php",
        cache: true,
        dataType: "json",
        data: {bannerType: "0"},//只查询PC端banner
        type: 'POST',
        success: function (result) {
            var bdiv = '';
            var pagerAdd = '';
            if ( result.code == "10000" && result.data.length>0) {
                var values = result.data;
                for (var i = 0; i < values.length; i++) {
                    bdiv += '<a href="' + values[ i ].hyperlinkUrl + '" style="background-image:url(' + values[ i ].showUrl + ')"></a>';
                    pagerAdd += '<span data-num="'+i+'"></span>'
                }
                //<a href="/t1/promotion.jsp" class="banner1" style="background-image: url(/images/banner/pc4.jpg);"></a>
                $ (".j-bannerImg").html (bdiv);
                $ (".j-bannerPage").html (pagerAdd);
                // 开始轮播
                bannerBind();
            }
            return;
        },
        error: function () {
            return;
        }
    })
}
function bannerBind() {
    bannerAuto();
    $('.j-bannerPage > span').on('click',function () {
        clearInterval(bannerInterval);
        var num = $(this).data('num');
        bannerChange(num);
        bannerAuto();
    })
}
var bannerInterval;
function bannerAuto() {
    bannerInterval = setInterval(function () {
        var num = $('.j-bannerPage > .active').data('num');
        var total = $('.j-bannerPage > span').length;

        if (num+1 == total) {
            num = 0;
        }
        else{
            num++;
        }

        bannerChange(num);
    },3000)
}
function bannerChange(num) {
    $('.j-bannerPage > span').removeClass('active');
    $('.j-bannerPage > span').eq(num).addClass('active');
    $('.j-bannerImg > a').removeClass('active');
    $('.j-bannerImg > a').eq(num).addClass('active');
}
////////公告//////////
