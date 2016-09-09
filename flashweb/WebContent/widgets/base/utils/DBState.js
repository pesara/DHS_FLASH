Ext.define('Base.utils.DBState', {
    /* Begin Definitions */
 
    extend : 'Ext.state.Provider',
    requires: 'Base.Globals',
    alias : 'state.dbstate',
 
    config: {
       widget: 'base',
       url: Base.Globals.context + '/services/state/',
       callback: Ext.emptyFn,
       timeout: 30000
    },
 
    constructor : function(config) {
        this.initConfig(config);
        var me = this;
 
        me.restoreState();
        me.callParent(arguments);
    },
    set : function(name, value) {
        var me = this;
 
        if( typeof value == "undefined" || value === null) {
            me.clear(name);
            return;
        }
        me.persist(name, value);
        me.callParent(arguments);
    },
    // private
    restoreState : function() {
        var me = this;
        Ext.Ajax.request({
            url : this.getUrl() + this.getWidget(),
            method : 'GET',
            disableCaching : true,
            success : function(results) {
            	var jsonData = Ext.JSON.decode(results.responseText);
                for(var i in jsonData) {
                    me.state[jsonData[i].name] = me.decodeValue(jsonData[i].value);
                }
                
                me.getCallback()();
            },
            failure : function() {
                console.log('failed', arguments);
                me.getCallback()();
            }
        });
    },
    // private
    clear : function(name) {
        this.clearKey(name);
        this.callParent(arguments);
    },
    // private
    persist : function(name, value) {
    	var me = this;
        Ext.Ajax.request({
            url : this.getUrl() + this.getWidget(),
            method : 'POST',
            params : {
                name : name,
                value : me.encodeValue(value)
            },
            disableCaching : true,
            success : function() {
                // console.log('success');
            },
            failure : function() {
                console.log('failed', arguments);
            }
        });
    },
    // private
    clearKey : function(name) {
        Ext.Ajax.request({
            url : this.getUrl() + this.getWidget(),
            method : 'DELETE',
            params : {
                name : name
            },
            disableCaching : true,
            success : function() {
                console.log('success');
            },
            failure : function() {
                console.log('failed', arguments);
            }
        });
    }
});