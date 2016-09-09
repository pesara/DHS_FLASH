Ext.define('WP.controller.WelcomeController', {
	extend: 'Ext.app.Controller',
	views: ['MainPanel'],
	stores: [],
	models: ['User'],
	createMode: false,
	currentUser: null,
	refs: [{
		ref: 'MainPanel',
		selector: 'mainpanel'
	}],
	
	init: function() {
		
		this.control({
//			'usergrid': {
//				selectionchange: this.onUserSelect
//			},
//			'#kudosButton': {
//				click: this.onGiveKudosClick
//			},
//			'#refreshButton': {
//				click: this.onRefreshClick
//			}
			
		});
	},
	
	onLaunch: function(app) {
		var me = this;
		
		//any setup operations should be triggered here
		
		//get current user info 
		
		var fakeCurrentUser = {
			lastName: 'Plissken',
			firstName: 'Snake',
			userName: 'splissken',
			kudos: 21
		};
		
		me.currentUser = fakeCurrentUser;
		
		me.initialize();
	},
	
	initialize: function() {
		var me = this;
		
		if (me.currentUser !== null) {
			var userModel = Ext.create('WP.model.User', me.currentUser);
			me.getMainPanel().down('#userInfoForm').getForm().loadRecord(userModel);
		}
		
		
		
	},
	
	
});
