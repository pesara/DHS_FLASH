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
			'mainpanel #submitBtn': {
				click: this.onSubmitClick
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
	
	onSubmitClick: function(btn) {
		//execute login call
		
		var username = this.getMainPanel().down('#usernameField').getValue();
		var pw = this.getMainPanel().down('#passwordField').getValue();
		
		console.log(username);
		console.log(pw);
		
		var testJson = {
			username: username,
			password: pw
		};
		
		if (username === '' || pw === '') {
			return;
		} else {
			Ext.Ajax.request({
				method: 'POST',
				url: 'http://ec2-54-172-160-23.compute-1.amazonaws.com:8080/users',
				dataType: 'json',
				contentType: 'application/json; charset=UTF-8',
				data: testJson,
				success: function(response) {
					console.log(response);
					var resp = Ext.JSON.decode(response.responseText);
					console.log(resp);
				}
			});
		}
	}
	
	
});
