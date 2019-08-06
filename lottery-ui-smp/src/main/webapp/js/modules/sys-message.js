/**
 * 
 */
var mesModule = angular.module('sysMes', []);
var messageArray = {
		"play_type_cqssc":{
				"5ds_00":"{number} 双",
				"5ds_01":"{number} 单",
				"5dx_00":"{number} 小",
				"5dx_01":"{number} 大",
				"dwd_0":"万位 {number}",
				"dwd_1":"千位 {number}",
				"dwd_2":"百位 {number}",
				"dwd_3":"十位 {number}",
				"dwd_4":"个位 {number}"
			},
			"play_type_xjssc":{
				"5ds_00":"{number} 双",
				"5ds_01":"{number} 单",
				"5dx_00":"{number} 小",
				"5dx_01":"{number} 大",
				"dwd_0":"万位 {number}",
				"dwd_1":"千位 {number}",
				"dwd_2":"百位 {number}",
				"dwd_3":"十位 {number}",
				"dwd_4":"个位 {number}"
			},
			"play_type_xyft":{
				"ds_00":"{number} 双",
				"ds_01":"{number} 单",
				"dx_00":"{number} 小",
				"dx_01":"{number} 大",
				"dwd_0":"冠军 {number}",
				"dwd_1":"亚军 {number}",
				"dwd_2":"季军 {number}",
				"dwd_3":"第四名 {number}",
				"dwd_4":"第五名 {number}",
				"dwd_5":"第六名 {number}",
				"dwd_6":"第七名 {number}",
				"dwd_7":"第八名 {number}",
				"dwd_8":"第九名 {number}",
				"dwd_9":"第十名 {number}"
			},
			"play_type_bjpk10":{
				"ds_00":"{number} 双",
				"ds_01":"{number} 单",
				"dx_00":"{number} 小",
				"dx_01":"{number} 大",
				"dwd_0":"冠军 {number}",
				"dwd_1":"亚军 {number}",
				"dwd_2":"季军 {number}",
				"dwd_3":"第四名 {number}",
				"dwd_4":"第五名 {number}",
				"dwd_5":"第六名 {number}",
				"dwd_6":"第七名 {number}",
				"dwd_7":"第八名 {number}",
				"dwd_8":"第九名 {number}",
				"dwd_9":"第十名 {number}"
			},
			"play_type_5fc":{
				"5ds_00":"{number} 双",
				"5ds_01":"{number} 单",
				"5dx_00":"{number} 小",
				"5dx_01":"{number} 大",
				"dwd_0":"万位 {number}",
				"dwd_1":"千位 {number}",
				"dwd_2":"百位 {number}",
				"dwd_3":"十位 {number}",
				"dwd_4":"个位 {number}"
			},
			"play_type_yfb":{
				"ds_00":"{number} 双",
				"ds_01":"{number} 单",
				"dx_00":"{number} 小",
				"dx_01":"{number} 大",
				"dwd_0":"冠军 {number}",
				"dwd_1":"亚军 {number}",
				"dwd_2":"季军 {number}",
				"dwd_3":"第四名 {number}",
				"dwd_4":"第五名 {number}"
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

