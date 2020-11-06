function CusMap() {
                /** 存放键的数组(遍历用到) */
                this.keys = new Array();
                /** 存放数据 */
                this.data = new Object();
                /** 存放key */
                this.removeKeys = new Array();
                /**
                 * 放入一个键值对
                 * @param {String} key
                 * @param {Object} value
                 */
                this.put = function(key, value) {
                    if(this.data[key] == null){
                        this.keys.push(key);
                    }
                    this.data[key] = value;
                };

                /**
                 * 获取某键对应的值
                 * @param {String} key
                 * @return {Object} value
                 */
                this.get = function(key) {
                    return this.data[key];
                };

                /**
                 * 删除一个键值对
                 * @param {String} key
                 */
                this.remove = function(key) {
                    var a=null;
                    for (var i = 0; i < this.keys.length; i++) {
                        if (this.keys[i] == key) {
                            //a=i+1;
                        	this.data[this.keys[i]] = null;
                        	this.keys.splice(i, 1);
                        }
                         if(a!=null){
                            this.data[this.keys[a]] = null;
                        } 
                    }
                };
                /**
                 * 删除所有的键值对
                 * @param {String} key
                 */
                this.removeAll = function() {
                    for (var i = 0; i < this.keys.length; i++) {
                            this.keys.splice(0, 1);
                            this.data[this.keys[0]] = null;
                    }
                };
                /**
                 * 遍历Map,执行处理函数
                 *
                 * @param {Function} 回调函数 function(key,value,index){..}
                 */
                this.each = function(fn){
                    if(typeof fn != 'function'){
                        return;
                    }
                    var len = this.keys.length;
                    for(var i=0;i<len;i++){
                        var k = this.keys[i];
                        fn(k,this.data[k],i);
                    }
                };

                /**
                 * 获取键值数组(类似<a href="http://lib.csdn.net/base/java" class='replace_word' title="Java 知识库" target='_blank' style='color:#df3434; font-weight:bold;'>Java</a>的entrySet())
                 * @return 键值对象{key,value}的数组
                 */
                this.entrys = function() {
                    var len = this.keys.length;
                    var entrys = new Array(len);
                    for (var i = 0; i < len; i++) {
                        entrys[i] = {
                            key : this.keys[i],
                            value : this.data[i]
                        };
                    }
                    return entrys;
                };

                /**
                 * 判断Map是否为空
                 */
                this.isEmpty = function() {
                    return this.keys.length == 0;
                };

                /**
                 * 获取键值对数量
                 */
                this.size = function(){
                    return this.keys.length;
                };

                /**
                 * 重写toString
                 */
                this.toString = function(){
                    var s = "{";
                    for(var i=0;i<this.keys.length;i++,s+=','){
                        var k = this.keys[i];
                        s += k+"="+this.data[k];
                    }
                };
            };
            
 /*function Map(){  
    this.init();  
};  
//map的大小  
Map.prototype.size = function(){  
    return this._size;  
};  
//将数据放入map中  
Map.prototype.put = function(key,value){  
    if(!this.containsKey(key)){  
        this.array[key] = value;  
        this._size++;  
    }  
};  
//根据key获得map的其中一个value  
Map.prototype.get = function(key){  
    return this.array[key];  
};  
//map是否为空集合  
Map.prototype.isEmpty = function(){  
    return this._size === 0;  
};  
//map集合是否包含key  
Map.prototype.containsKey = function(key){  
    return this.array[key]!==undefined?true:false;  
};  
//移除指定的key  
Map.prototype.remove = function(key){  
    if(this.containsKey(key)){  
        delete this.array[key];  
        this._size--;  
    }  
};  
//移除所有集合  
Map.prototype.clear = function(){  
    this.init();  
};  
//map集合转化成Array  
Map.prototype.toArray = function(){  
    return this.array;  
};  
//初始化map集合  
Map.prototype.init = function(){  
    this.array = new Array();  
    this._size = 0;  
    this._keySet = new Array();  
};  */

