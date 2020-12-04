/**
 * 
 */
var mesModule = angular.module('sysMes', []);
var messageArray = {		
			"play_type_tc3":{				
				"dwd_0":"{number}",
				"dwd_1":"{number}",
				"dwd_2":"{number}",
				"dwd_3":"{number}",
				"dwd_4":"{number}",
				"ywdw_bw_dx_00":"百位小",
				"ywdw_bw_dx_01":"百位大",
				"ywdw_sw_dx_00":"十位小",
				"ywdw_sw_dx_01":"十位大",
				"ywdw_gw_dx_00":"个位小",
				"ywdw_gw_dx_01":"个位大",				
				"ywdw_bw_zh_00":"百位合",
				"ywdw_bw_zh_01":"百位质",
				"ywdw_sw_zh_00":"十位合",
				"ywdw_sw_zh_01":"十位质",
				"ywdw_gw_zh_00":"个位合",
				"ywdw_gw_zh_01":"个位质",
				
				"ywdw_bw_ds_00":"百位偶",
				"ywdw_bw_ds_01":"百位奇",
				"ywdw_sw_ds_00":"十位偶",
				"ywdw_sw_ds_01":"十位奇",
				"ywdw_gw_ds_00":"个位偶",
				"ywdw_gw_ds_01":"个位奇",
				"ywdw_bw_sz":"百位 {number}",
				"ywdw_sw_sz":"十位 {number}",
				"ywdw_gw_sz":"个位 {number}",
				"bdw_yw":"一位不定位 {number}",
				"bdw_ew":"二位不定位 {number}",
				"bdw_sw":"三字组合 {number}",
				"ewhs_sg_ds_00":"十个和数偶",
				"ewhs_sg_ds_01":"十个和数奇",
				"ewhs_bg_ds_00":"百个和数偶",
				"ewhs_bg_ds_01":"百个和数奇",
				"ewhs_bs_ds_00":"百十和数偶",
				"ewhs_bs_ds_01":"百十和数奇",
				"ewhs_bs_sz":"百十和数 {number}",
				"ewhs_bg_sz":"百个和数 {number}",
				"ewhs_sg_sz":"十个和数 {number}",
				"swhs_dx_00":"三位和数小",
				"swhs_dx_01":"三位和数大",
				"swhs_ds_00":"三位和数偶",
				"swhs_ds_01":"三位和数奇",				
				"swhs_sz":"三位和数 {number}",
				"swdw_sz":"三位 {number}",
				"ewdw_bs_sz":"百十 {number}",
				"ewdw_bg_sz":"百个 {number}",
				"ewdw_sg_sz":"十个 {number}",
				"zx3_5m":"组三5码 {number}",
				"zx3_6m":"组三6码 {number}",
				"zx3_7m":"组三7码 {number}",
				"zx3_8m":"组三8码 {number}",
				"zx3_9m":"组三9码 {number}",
				"zx3_qb":"组三全包",
				"zx6_4m":"组六4码 {number}",
				"zx6_5m":"组六5码 {number}",
				"zx6_6m":"组六6码 {number}",
				"zx6_7m":"组六7码 {number}",
				"zx6_8m":"组六8码 {number}",
				"kd_0k":"0跨",
				"kd_1k":"1跨",
				"kd_2k":"2跨",
				"kd_3k":"3跨",
				"kd_4k":"4跨",
				"kd_5k":"5跨",
				"kd_6k":"6跨",
				"kd_7k":"7跨",
				"kd_8k":"8跨",
				"kd_9k":"9跨",
			},
			"data_item_type":{
				"0":"余额",
				"0":"余额",
				"0":"余额",
				"0":"余额"
			},
			"error_mes":{
				"090003":"取消订单失败!!",
				"030009":"余额不足!!",
				"030019":"当前赔率无效"
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

