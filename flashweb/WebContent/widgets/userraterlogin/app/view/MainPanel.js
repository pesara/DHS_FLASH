Ext.define('UL.view.MainPanel', {
	extend: 'Ext.panel.Panel',
	xtype: 'mainpanel',
	itemId: 'mainPanel',
    layout: 'border',
    title: 'User Rater Login',
    titleAlign: 'center',
    items: [{
    	xtype: 'container',
    	region: 'center',
    	layout: {
    		type: 'vbox',
    		align: 'center',
    		pack: 'center'
    	},
    	defaults: {
    		allowBlank: false,
        	labelWidth: 80,
        	labelAlign: 'right',
        	width: 290,
        	margin: '5 5 5 5'
    	},
    	items: [{
        	xtype: 'textfield',
        	itemId: 'usernameField',
        	fieldLabel: 'Username'
        }, {
        	xtype: 'textfield',
        	itemId: 'passwordField',
        	fieldLabel: 'Password'
        }, {
        	xtype: 'container',
        	layout: {
        		type: 'hbox',
        		align: 'center',
        		pack: 'center'
        	},
        	items: [{
        		xtype: 'button',
        		itemId: 'forgotPwBtn',
        		text: 'Forgot Password?',
        		icon: Base.Globals.getImagePath() + 'help.gif',
        		margin: '10 10 0 0',
        		width: 140
        	}, {
        		xtype: 'button',
        		itemId: 'enterBtn',
        		text: 'Enter',
        		icon: Base.Globals.getImagePath() + 'check.png',
        		margin: '10 0 0 10',
        		width: 140
        	}]
        	
        }]
    }],
});