KISSY.add(function(S, Util) {
        var S = KISSY, $ = S.all, Dom = S.DOM, Event = S.Event, JSON = S.IO,doc = document, ie = S.UA.ie,
    _Dialog = {
        config : {
            uid:null,
            trigger:null,
            center:true,
            close:true,
            destroy:false,
            /*drag:false,*/
            scrolling:false,
            mask:true,
			width:'720px',
            CLASSNAME_MASK:'ui-dialog-mask',
            CLASSNAME_WINDOW:'ui-dialog-window',
            CLASSNAME_ENTITY:'ui-dialog-entity',
            CLASSNAME_HEAD:'ui-dialog-head',
            CLASSNAME_BODY:'ui-dialog-body',
            CLASSNAME_FOOT:'ui-dialog-foot'
        },
        /*EVENT_LIST : {
            'CHANGE_HEADER': 'changeHeader',
            'CHANGE_BODY': 'changeBody',
            'CHANGE_FOOTER': 'changeFooter',
            'RENDER': 'render',	
            'BEFORE_SHOW': 'beforeShow',
            'SHOW': 'show',		
            'BEFORE_HIDE': 'beforeHide',		
            'HIDE': 'hide'						
        },*/
        Dialog : function(conf){ 
            conf = S.merge(_Dialog.config, conf || {});//合并配置信息
            return new _Dialog.fn.init(conf); 
        },
        DialogManage : {
            updataConfig: null,
            LIST: {},
            get: function(id,config) {
                if(!id || !this.LIST[id]) {
                    if (config) {
                        var o = _Dialog.Dialog(config);
                        id = !id ? o.elem.id : id;
                        this.LIST[id] = o;
                        return o;
                    } else {
                        return null; 
                    };
                } else {
                    return this.LIST[id];
                };
            },
            destroy:function(id){
                /*add garbagebin for memory-leaks,maybe invalid */
                if(!id || !this.LIST[id]) {return};
                var dialog = this.LIST[id],elem = dialog.elem,mask = dialog.mask;

                ie && (elem.innerHTML = '');
                GPRS.Util.Recycle([elem,mask]);

                elem.parentNode ? elem.parentNode.removeChild(elem) : doc.body.removeChild(elem);
                mask.parentNode ? mask.parentNode.removeChild(mask) : doc.body.removeChild(mask);

                this.LIST[id] = null;
                delete this.LIST[id];
                GPRS.Util.Flush();
            }
        }
};
    _Dialog.fn = _Dialog.prototype = {
        init: function(conf){
            this.config = conf;
            this.create();
			/*for(k in Dialog.EVENT_LIST) {
                this.createEvent(Dialog.EVENT_LIST[k]);
			}*/
            if(conf.keypress) {
                Event.on(doc,'keypress',function(evt) {
                    if (27 == Event.getCharCode(evt)) this.hide();
                },this, true);
            };
            if (conf.scrolling) {
                Event.on(window,'scroll',function(evt) {
                    this.center();
                },this, true);
            };
        },
        set: function(el,o){
            if (o && o.tagName) {
                while (el.lastChild) {
                    el.lastChild.parentNode.removeChild(el.lastChild);
                };
                el.appendChild(o); 
            } else {
                el.innerHTML = o;
            };
        },
        setHead: function(o) {
            this.set(this.elemHead,o); 
        },
        setBody: function(o) {
            this.set(this.elemBody,o); 
			this.fire('changeBody');
        },
        setFoot: function(o) {
            this.set(this.elemFoot,o); 
        },
        create: function(o){
            var conf = this.config;
            
            this.uid = conf.uid || S.guid('ui-dialog-');
            
            //window
			this.elem = doc.createElement('div');
			this.elem.id = this.uid;
			this.elem.className = conf.CLASSNAME_WINDOW;
			
            //entity
			this.elemEntity = doc.createElement('div');
            this.elemEntity.className = conf.CLASSNAME_ENTITY;
            this.elem.appendChild(this.elemEntity);
            doc.body.appendChild(this.elem);

            //head
            if (conf.head) {
                this.elemHead = doc.createElement('div');
                this.elemHead.className = conf.CLASSNAME_HEAD;
                
                var head='<a class="J_DialogClose btn-close" title="关闭" href="#"></a>'; 
                if(conf.title){
                	head='<span class="J_Title">'+conf.title+'</span>'+head;
                }
                this.setHead(head);
                this.elemHead && this.elemEntity.appendChild(this.elemHead);
                
                Event.on($('.J_DialogClose', this.elemHead),'click',function(evt) {
					evt.preventDefault();
					this.hide();
				},this);
            };

            //body
            if (conf.body) {
            	this.elemBody = doc.createElement('div');
                this.elemBody.className = conf.CLASSNAME_BODY;
                this.setBody(conf.body);
                
                this.elemEntity.appendChild(this.elemBody);
                var body=$(".popup-body",this.elem)[0],width=body.scrollWidth;
                Dom.css(this.elem,'width',width+40+"px");
            }
            
            if (conf.foot) {
                this.elemFoot = doc.createElement('div');
                this.elemFoot.className = conf.CLASSNAME_FOOT;
                this.elemFoot && this.elemEntity.appendChild(this.elemFoot);
            };
           
            
			if(conf.mask) {
				this.mask = doc.createElement('div');
				this.mask.id = this.elem.id + '-mask';
				this.mask.className = 'ui-dialog-mask';
                Dom.css(this.mask,'height',Dom.docHeight() + 'px');
				doc.body.appendChild(this.mask);
                //add by weiming.zw @ 20110913 for ie6 select bug
                if (ie && 6 === ie) {
                    var iframe = doc.createElement('iframe');
                    iframe.src = 'about:blank';
                    iframe.setAttribute('frameborder','0');
                    iframe.setAttribute('scrolling','no');
                    Dom.addClass(iframe,'overlay-iframe');
                    Dom.css(iframe,'opacity','0');
                    Dom.css(iframe,'zIndex','-1');
                    Dom.css(iframe,'height', '100%');
                    Dom.css(iframe,'width',  '100%');
                    this.mask.appendChild(iframe);
                };
            };
            
            

			/*if(conf.drag) {
				Dom.addClass(this.elem,'ui-dialog-dd');
				this.DD = new YAHOO.util.DD(this.elem);
				this.DD.setHandleElId(this.elemHead);
            };*/

            _Dialog.DialogManage.LIST[this.uid] = this;
             
        },
        beforeShow: function(){
            this.fire('beforeShow');
        },
        show: function(){
            var conf = this.config,h;
            this.beforeShow();

			if(conf.center) {this.center()};
		    h = parseFloat(Dom.css(this.elem, 'top'))	
			Dom.css(this.elem, 'top', h + 'px');
			Dom.css(this.elem, 'visibility', 'visible');			
			Dom.css(this.mask, 'visibility', 'visible');
            Dom.css(this.mask, 'height', doc.documentElement.clientHeight+ 'px');
			//if(ie && 6 === ie) {
				Dom.addClass(doc.body, 'fix-select');
            //};			

			this.fire('show');
			return this;
             
        },
        beforeHide: function(){
            this.fire('beforeHide');
        },
        hide: function(){
            this.beforeHide();
			var conf = this.config;
			Dom.css(this.elem, 'visibility', '');
			Dom.css(this.elem, 'top', '-999em');
			Dom.css(this.mask, 'visibility', '');
			Dom.css(this.mask, 'height', '0');
            //if(ie&& 6 === ie) {
				Dom.removeClass(doc.body, 'fix-select');
           // };

            conf.destroy && GPRS.DialogManage.destroy(this.uid); 
            this.fire('hide');

            return this;
        },
        pos: function(x,y){
            var elem = this.elem;
            x = parseInt(x,10) || 0;
            y = parseInt(y,10) || 0;
            Dom.css(elem,'left', x + 'px');
            Dom.css(elem,'top', y + 'px');
        },
        resize: function(w){
            var elem = this.elem,elemBody = this.elemBody,offset = elem.offsetWidth - elemBody.offsetWidth;
			w = w || Dom.children(elemBody)[0].offsetWidth + offset;
            Dom.css(elem,'width',w + 'px');
        },
        center: function(){
            var elem = this.elem,x,y,
                elemWidth = elem.offsetWidth,
                elemHeight = elem.offsetHeight,
                viewPortWidth = Dom.viewportWidth(),
                viewPortHeight = Dom.viewportHeight();
                
            if (elemWidth < viewPortWidth) {
                x = (viewPortWidth / 2) - (elemWidth / 2) + Dom.scrollLeft();
            } else {
                x = Dom.scrollLeft();
            };

            if (elemHeight < viewPortHeight) {
                y = (viewPortHeight / 2) - (elemHeight / 2) + Dom.scrollTop();
            } else {
                y = Dom.scrollTop();
            };
            
            this.pos(x,y);
            Dom.css(this.mask, 'height', Dom.docHeight() + 'px');
            this.fire('render');
            return this;
        }
        
    };

    S.augment(_Dialog, S.EventTarget);//继承KISSY的自定义事件
    _Dialog.fn.init.prototype = _Dialog.fn;

    return GPRS.Dialog = _Dialog.Dialog, GPRS.DialogManage=_Dialog.DialogManage;
},{
    requires : [
        'gprs/util/function'
    ]
});
