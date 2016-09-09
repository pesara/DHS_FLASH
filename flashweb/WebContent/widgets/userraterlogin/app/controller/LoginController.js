Ext.define('UL.controller.LoginController', {
	extend: 'Ext.app.Controller',
	views: ['MainPanel'],
	stores: [],
	models: [],
	
	currentUser: null,
	
	refs: [{
		ref: 'MainPanel',
		selector: 'mainpanel'
	}],
	
	init: function() {
		
		this.control({
			'mainpanel #enterBtn': {
				click: this.onEnterClick
			},
			
		});
	},
	
	onLaunch: function(app) {
		var me = this;
		
		//any setup operations should be triggered here
		
		me.initialize();
	},
	
	initialize: function() {
		var me = this;
		
	},
	
	onEnterClick: function(btn) {
		//execute login call
	}
	
	
});
