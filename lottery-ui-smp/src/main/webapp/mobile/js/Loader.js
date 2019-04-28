/**
 * loading 
 */

function Loader(conf){
	var _this = this;
	var _conf = {
		id:'ajax-loading',
		text:'加载中'
	};
	$.extend(_conf,conf);
	
	var _textId = _conf.id+'-text';
	var _loadHtml = [
	'<div id = "'+_conf.id+'" class="loader">',
//	'	<div class="screen"></div>',
	'	<div class="loader-block">',
	'		<div class="loading"></div>',
	'		<div id="'+_textId+'" class="text"></div>',
	'	</div>',
	'</div>'].join('');
	
	var _$loader;
	var _$text;
	var _openCount = 0;
	
	if(!document.getElementById(_conf.id)){
		$('body').append(_loadHtml);
		_$loader = $('#'+_conf.id);
		_$text = $('#'+_textId);
	}else{
		_$loader = $('#'+_conf.id);
		_$text = $('#'+_textId);
	}
	
	/**
	 * open loading
	 * 
	 */
	_this.open = function(msg){
	    if(msg){
	    	_$text.html(msg);
	    }else{
	    	_$text.html(_conf.text);
	    }
	    _$loader.addClass('show');
	    $('body').addClass('loader-hidden');
	    _openCount++;
	}
	
	/**
	 * open close
	 */
	_this.close = function(){
		if(--_openCount!=0)return;
	    _$loader.removeClass('show');
	    $('body').removeClass('loader-hidden');
	}
	
}