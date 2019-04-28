$(function () {
    new  PromotionPage();
    $(window).scroll(function () {
        var _top=$(document).scrollTop();
        if(_top>210){
            $(".promotion_left").addClass("fixed");
        }else{
            $(".promotion_left").removeClass("fixed");
        }
    });
});
function PromotionPage(){
    var that=this;
    this.tpl_menu=['<a data-type="{{typeId}}" class="pro_btn">{{typeName}}</a>'].join('');
    this.tpl=[
        '<div class="promotion_item j-p-item" id="{{id}}" data-type="{{type}}" >',
            '<img src="{{image}}">',
            '<p class="pro_title">{{title}}</p>',
            '<a {{href}} target="_blank" class="pro_control j-detail"  data-id="{{id}}" ">查看详情</a>',
            '<div class="pro_des j-info"></div>',
        '</div>',
    ].join('');
    this.$container=$('#j-container');
    this.$nav=$('#j-prom-nav');
    this.$navBtn=that.$nav.find('a[data-type]');
    // 读取数据
    this.getData=function () {
        return $.post('/youhui/queryLatestPreferential.do',{'platFormType':'pc','type':'000','pageIndex':0,'pageSize':0});
    };
    this.buildMenu=function(data){
      var htmlArr=[];
      for (var i = 0; i < data.length; i++) {
          var obj=data[i];
          htmlArr.push(that.tpl_menu.replace(/\{\{typeId\}\}/g,obj.typeId)
              .replace(/\{\{typeName\}\}/g,obj.typeName)
          );
      }
      return htmlArr.join('');
    }
    // 渲染页面
    this.buildHtml=function(data){
        var htmlArr=[];
        for (var i = 0; i < data.length; i++) {
            var obj=data[i];
            that.$container.append(that.tpl.replace(/\{\{title\}\}/g,obj.activityTitle)
                .replace(/\{\{image\}\}/g,obj.activityImageUrl)
                .replace(/\{\{typeDesc\}\}/g,obj.typeDesc)
                .replace(/\{\{type\}\}/g,obj.type)
                .replace(/\{\{id\}\}/g,obj.id)
                .replace(/\{\{href\}\}/g,obj.activityUrl?' href="'+obj.activityUrl+'"':'')
            );
        }
        return htmlArr.join('');
    };
    // 绑定操作
    this.eventHandle=function () {
        that.$nav.find('a[data-type]').click(function () {
            // 分类
            var type=$(this).data('type');
            $(this).addClass('active').siblings().removeClass('active');
            if(type){
                $('.j-p-item').hide();
                $('.j-p-item[data-type*="'+type+'"]').show();
            }else{
                $('.j-p-item').show();
            }
        });
        $(document).on('click','.j-detail',function () {
          // 查看详细
          var $this=$(this);
          // var url= $(this).data('url');
          var id= $(this).data('id');
          var geted=$(this).hasClass("geted")
          if(!geted){
              $.post('/youhui/findLatestPreferentialById.php',{"id":id,"platFormType":"pc"},function (res) {
                if(res.code="10000"){
                  $this.addClass("geted");
                  $this.parents('.j-p-item').find(".j-info").html(res.data.activityContent);
                  $this.parents('.j-p-item').find(".j-info").slideToggle(500);
                  setTimeout(function(){
                      $this.toggleClass("active");
                  },500);
                  return;
                }else{
                  toast_tip(res.message)
                }
              }).fail(function () {
                  that.$detail.html('获取失败！')
                  return;
              });
          }else{
              $this.parents('.j-p-item').find(".j-info").slideToggle(500);
              setTimeout(function(){
                  $this.toggleClass("active");
              },500);
          }
        });
        var _target=getQueryString('showpromotion');
        if(_target){
            $(".j-detail").each(function(){
                var $this=$(this);
                if($this.data("id")==_target){
                    $this.click();
                    return;
                }
            })
        }
    };
    // 初始化
    this.init=function () {
        var def=that.getData();
        def.done(function (data) {
            that.buildHtml(data.data.records)
            // that.$container.html();
            data.data.types.unshift({"typeId":"","typeName":"全部优惠"})
            that.$nav.html(that.buildMenu(data.data.types))
            that.eventHandle();
            that.$nav.find('a[data-type]').eq(0).trigger('click');

            // 根据链接参数显示对应的优惠内容
            var promotion=getQueryString('showpromotion');
            if(promotion){
                $('#j-container').find('a[data-id="'+promotion+'"]').click();
            }
        });
    };
    this.init();
}
