/**
 * 
 */
var mesModule = angular.module('sysMes', []);
var messageArray = {		
			"play_type_tc3":{
				"ds_00":"{number} 双",
				"ds_01":"{number} 单",
				"dx_00":"{number} 小",
				"dx_01":"{number} 大",
				"dwd_0":"{number}",
				"dwd_1":"{number}",
				"dwd_2":"{number}",
				"dwd_3":"{number}",
				"dwd_4":"{number}",
				"bdw_yw":"不定位 一位 {number}"
			},
			"data_item_type":{
				"0":"余额",
				"0":"余额",
				"0":"余额",
				"0":"余额"
			},
			"error_mes":{
				"090003":"取消订单失败!!",
				"030009":"余额不足!!"
			}

};



mesModule
	.constant('CONS_SYS_MES', messageArray)
	.factory('sysCodeTranslateFactory', function(CONS_SYS_MES) {
    return{
        //系统代码翻译
        codeTranslate:function(type, attr)
        {
            if(type)
            {
                var mes = CONS_SYS_MES[type];
                if(mes)
                {	
                	if(mes[attr]){
                		return mes[attr];
                	}
                }
            }
            return null;
        }
    };
}); ;

