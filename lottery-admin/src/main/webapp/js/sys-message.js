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
			"data_item_type":{
				"0":"余额",
				"0":"余额",
				"0":"余额",
				"0":"余额"
			}

};

function getBettingNum(lotteryType, playType, bettingNum) {
	var codeType = "play_type_" + lotteryType;
	
	if(playType.indexOf('ds') >= 0 || playType.indexOf('dx') >= 0){
		var playType_ = playType.substring(0, playType.indexOf('|'));
		var attr = playType_ + "_" + bettingNum;
    	var codeVal =  codeTranslate(codeType, attr);
    	if(codeVal != null){
    		var number = playType.substring(playType.length - 2, playType.length - 1);
    		var numberDes = number;
    		if(lotteryType == 'cqssc' || lotteryType == 'xjssc'){
    			if(number == '一'){
    				numberDes = '万位';
    			}else if(number == '二'){
    				numberDes = '千位';
    			}else if(number == '三'){
    				numberDes = '百位';
    			}else if(number == '四'){
    				numberDes = '十位';
    			}else if(number == '五'){
    				numberDes = '个位';
    			}
    		}else if(lotteryType == 'bjpk10' || lotteryType == 'xyft'){
    			if(number == '一'){
    				numberDes = '冠军';
    			}else if(number == '二'){
    				numberDes = '亚军';
    			}else if(number == '三'){
    				numberDes = '季军';
    			}else{
    				numberDes = '第' + number + '名';
    			}
    		}
    		codeVal = codeVal.replace('{number}', numberDes);
    	}
	}else if(playType.indexOf('dwd') >= 0){
		var playType_ = playType.substring(0, playType.indexOf('|'));
		var bettingNumArray = bettingNum.split(',');
		var bettingNumIndex = -1;
		var bettingNumVal = '';
		var attr = null;
		
		for(var i = 0;i < bettingNumArray.length; i++){
			if(bettingNumArray[i]){
				bettingNumIndex = i;
				bettingNumVal = bettingNumArray[i];
				
				attr = playType_ + "_" + bettingNumIndex;
				break;
			}
		}    		
		
    	var codeVal =  codeTranslate(codeType, attr);
    	if(codeVal != null){
    		codeVal = codeVal.replace('{number}', bettingNumVal);
    	}
	}
	
	return codeVal;
};

function codeTranslate(type, attr)
{
    if(type)
    {
        var mes = messageArray[type];
        if(mes)
        {	
        	if(mes[attr]){
        		return mes[attr];
        	}
        }
    }
    return null;
}
