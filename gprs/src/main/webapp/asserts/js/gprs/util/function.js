/*
 * @ util function
 * @ desc none
 * @ date 2011-11-23
 */

KISSY.add(function(S) {
        var S = KISSY, $ = S.all, Dom = S.DOM, Event = S.Event, JSON = S.IO,doc = document, ie = S.UA.ie,
        _Util = {
            JSON: {
                parse:function(data){
                    try{
                        var ret = new Function('return' + data.replace(/\n|\r|\t/g,''))(); 
                    }catch(e){
                        alert('��ݽ������ￄ1�7'); 
                    };
                    return ret;
                },
                stringify:function(data){

                } 
            },
            Recycle: function(mix){
                var fn = function(node){
                    if (node.clearAttributes) {
                        node.clearAttributes();
                    } else {
                        for (attr in node) {
                            if (node.hasOwnProperty(attr)) {
                                delete node[attr];
                            };
                        };
                    };                    
                },i,j;
                if (mix.length) {
                    for (i = 0,j = mix.length; i < j; i+=1) {
                        fn(mix[i]);
                    };
                } else {
                    fn(mix); 
                };
            },    
            Flush:function(){
                ie && CollectGarbage();
            },
            FormatURL: function(s) {
                s = s.indexOf('?') > 0 ? s + '&_input_charset=utf-8&': s + '?_input_charset=utf-8&';
                return s;
            },
            JoinParam: function(s){
                var arr;
                if (arguments.length > 1) {
                    arr = [].slice.call(arguments,1)
                };
                if (arr) {
                    s = s.indexOf('?') > 0 ? s + '&' + arr.join('&') : s + '?' + arr.join('&');
                };
                return s;
            },
            ArrayUnique: function(arr,n,d){
                var arrO = arr && arr.length !== 0 ? arr.slice(0) : [];
                if (!!n){
                    S.each(n, function(str){
                            if (S.indexOf(str,arrO) === -1) {arrO.push(str)};
                });
                };
                if (!!d) {
                    S.forEach(d, function(str){
                            if (S.indexOf(str,arrO) > -1) {arrO.splice(S.indexOf(str,arrO),1)};
                });                
                };
                return arrO;
            },
            CombineField: function(el){
                var s,arr,re,arrEL;
                if (!el || el.tagName.toLowerCase() !== 'form') {
                    s = '';
                } else {
                    arr = [];
                    re = /input|select|textarea/;
                    arrEl = Dom.filter('*',function(elem) {
                            return re.test(elem.tagName.toLowerCase());
                        }, el);
                    S.each(arrEl, function(ele) {
                            arr.push(ele.name + '=' + ele.value);
                        });
                    s = arr.join('&');
                }
                return s;
            },
            NumInput: function(el,type){
                if (!el || el.tagName.toLowerCase() !== 'input') {return};
                var fn = arguments[2];
                Event.on(el, 'keyup', function(evt) {
                        /*[>var sKeyCode = window.event ? window.event.keyCode: (evt ? evt.which: null),b;<]*/
                    if (type === 'float') {
                        this.value = this.value.replace(/[^\d.]/g,"");
                        /*b =  (sKeyCode < 46 || (sKeyCode > 57 && sKeyCode < 96) || sKeyCode > 105 );*/
                } else {
                    this.value = this.value.replace(/[^\d]/g,"");
                    /*b =  (sKeyCode < 48 || (sKeyCode > 57 && sKeyCode < 96) || (sKeyCode > 105 && sKeyCode !== 110 ));*/
                }
                /*if (b && sKeyCode !== 8 && sKeyCode !== 0) {Event.preventDefault(evt)};*/

                });

            Event.on(el,'blur',function(evt){
                    var s = S.trim(this.value) !== '' ? type === 'float' ? parseFloat(S.trim(this.value)) : parseInt(S.trim(this.value),10) : '';
                    var result = !(s.toString() === NaN.toString());
                    this.value = (!result ? '' : s);
                    if (fn && S.isFunction(fn)) {fn(result,el)};
                });
            },
            /*NumInput: function(el,type){
                if (!el || el.tagName.toLowerCase() !== 'input') {return};
                var fn = arguments[2];
                Event.on(el, 'keydown', function(evt) {
                        var sKeyCode = window.event ? window.event.keyCode: (evt ? evt.which: null),b;
                        if (type === 'float') {
                            b =  (sKeyCode < 46 || (sKeyCode > 57 && sKeyCode < 96) || sKeyCode > 105);
                        } else {
                            b =  (sKeyCode < 48 ||(sKeyCode > 57 && sKeyCode < 96) || (sKeyCode > 105 && sKeyCode !== 110 ));
                        }
                        if (b && sKeyCode !== 8 && sKeyCode !== 0) {evt.preventDefault()};

                    });

                Event.on(el,'blur',function(evt){
                        var s = S.trim(this.value) !== '' ? type === 'float' ? parseFloat(S.trim(this.value)) : parseInt(S.trim(this.value),10) : '';
                        var result = !(s.toString() === NaN.toString());
                        this.value = (!result ? '' : s);
                        if (fn && S.isFunction(fn)) {fn(result,el)};
                    });
            },*/
			checkAllByCls : function(el, cls){
                Event.on(el,'click',function(evt){
						bChecked = this.checked;
						var elems = $(cls);
						S.each(elems,function(ele){
							ele.checked = bChecked;
						 }); 	
						S.each(el,function(ele){
							ele.checked = bChecked;
						 });
                    });

				var elems = $(cls);
                Event.on(elems,'click',function(evt){
                        var arrayTemp = [];
						var ckState = this.checked;
                        S.each(elems,function(ele){
                                if (ele.checked) {arrayTemp.push(ele)};
                            });
                        bChecked = (arrayTemp.length === elems.length);
						S.each(el,function(ele){
							ele.checked = ckState ? (bChecked && ckState) : bChecked;
						});
                  });
			},
            CheckAll: function(el,mix) {
                var bChecked = false, elems;
                if (mix && S.isFunction(mix)) {
                    elems = mix(el); 
                } else {
                    elems = mix; 
                };

                Event.on(el,'click',function(evt){
						bChecked = this.checked;
						S.each(elems,function(ele){
							!ele.disabled && (ele.checked = bChecked);
						 }); 	
						S.each(el,function(ele){
								ele.checked = bChecked;
						 });
                    });
                Event.on(elems,'click',function(evt){
                        var arrayTemp = [];
						var ckState = this.checked;
                        S.each(elems,function(ele){
                                if (ele.checked) {arrayTemp.push(ele)};
                            });
                        bChecked = (arrayTemp.length === elems.length);
						S.each(el,function(ele){
							ele.checked = ckState ? (bChecked && ckState) : bChecked;
						});
                  });
            },        
            WordCount: function(s,l){
                var clone,re = /[\u4e00-\u9fff\uf900-\ufaff]/g,str = '',ret;
                l = l || 2;
                if (!s) {return 0};
                if (l === 1) {
                    ret =  s.length;
                } else {
                    for (var i = 0; i < l; i++) {
                        str += 'x';
                    };
                    clone = s.replace(re,str);
                };
                return ret;
            },
            TbraInputHint : {
                decorate : function(inputField, cfg){
                    var defConfig = {
                        hintGPRS: '',
                        hintClass: 'tb-input-hint'
                    }, EMPTY_PATTERN = /^\s*$/;
                    cfg = S.merge(defConfig, cfg || {}),
                    hintGPRS = cfg.hintGPRS || inputField.title;
                    if (inputField.getAttribute('title') == undefined || inputField.value === '' ) {
                        inputField.setAttribute('title', cfg.hintGPRS);
                        inputField.value = cfg.hintGPRS;
                        Dom.addClass(inputField, cfg.hintClass)
                    }
                    Event.on(inputField, 'focus', function(){
                            if (hintGPRS === inputField.value) {
                                inputField.value = '';
                                Dom.removeClass(this, cfg.hintClass);
                            }
                        })
                    Event.on(inputField, 'blur', function(){
                            if(EMPTY_PATTERN.test(this.value) || hintGPRS === inputField.value){
                                Dom.addClass(this, cfg.hintClass);
                                inputField.value = hintGPRS;				
                            }
                        })
                }
            },

            InputHint: function(input,config){
                if (S.isArray(input)) {
                    S.each(input,function(el){
                            this.TbraInputHint.decorate(el,config);
                        });
                } else {
                    this.TbraInputHint.decorate(input,config);
                }
            },
            ClipBoard: function(meintext){
                if (window.clipboardData){
                    window.clipboardData.setData("Text", meintext);
                    return true;
                } else if (window.netscape) {
                    try{
                        netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
                    } catch (e) {
                        return false;
                    }
                    var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
                    if (!clip) return false;
                    var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
                    if (!trans) return false;
                    trans.addDataFlavor('text/unicode');
                    var str = new Object();
                    var len = new Object();
                    var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
                    var copytext=meintext;
                    str.data=copytext;
                    trans.setTransferData("text/unicode",str,copytext.length*2);
                    var clipid=Components.interfaces.nsIClipboard;
                    if (!clip) return false;
                    clip.setData(trans,null,clipid.kGlobalClipboard);
                };
                return true;
            },
            Clock:function(){
                var arrExpired = $('.J_Expired'),
                fn = function(el){
                    if (!el) {return};
                    var str,arr,timer,serialize,replace,go,expired = parseInt(el.getAttribute('data-expired'),10); 

                    serialize = function (c){
                        var d = Math.floor(c/86400),
                        h = Math.floor(c%86400/3600),
                        m = Math.floor(c%86400%3600/60),
                        s = Math.floor(c%86400%3600%60);
                        d = d > 0 ? d + '天' : '';
                        h = h > 0 ? h + '时' : '';
                        m = m > 0 ? m + '分' : '';
                        s = s > 0 ? s + '秒' : '';
                        return [d,h,m,s];
                    };
                    arr = serialize(expired);
                    replace = function(){
                        if(arr[3] && arr[3] > 0){
                            --arr[3]; 
                        } else {
                            arr = serialize(expired); 
                        };
                        expired--; 
                        el.innerHTML = arr.join('');
                    };

                    timer = setInterval(function(){
                            if (expired < 0) {timer && clearInterval(timer)}else{replace()};
                        },1000);
                };
                S.each(arrExpired,function(el){fn(el);});
            }
        };
        return GPRS.Util = _Util;
    },{
        requiers : [
            //
        ]
    });
