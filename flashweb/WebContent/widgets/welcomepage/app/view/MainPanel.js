Ext.define('WP.view.MainPanel', {
	extend: 'Ext.panel.Panel',
	xtype: 'mainpanel',
	itemId: 'mainPanel',
    layout: 'border',
    title: 'Welcome Page',
    titleAlign: 'center',
    tbar: [{
    	xtype: 'button',
    	text: 'Create User',
    	itemId: 'createUserBtn',
    	icon: Base.Globals.getImagePath() + 'add.gif',
    	margin: '5 5 5 10'
    }, {
    	xtype: 'button',
    	text: 'Edit User',
    	itemId: 'editUserBtn',
    	icon: Base.Globals.getImagePath() + 'actionEdit.gif',
    	margin: '5 5 5 10',
    	disabled: true
    }, {
    	xtype: 'button',
    	text: 'Delete User',
    	itemId: 'deleteUserBtn',
    	icon: Base.Globals.getImagePath() + 'delete.gif',
    	margin: '5 5 5 10',
    	disabled: true
    }],
    items: [{
    	xtype: 'form',
    	itemId: 'userInfoForm',
    	region: 'center',
    	layout: 'vbox',
    	autoScroll: true,
    	margin: '5 5 5 5',
    	defaults: {
    		labelAlign: 'right',
    		labelWidth: 80,
    		width: 270,
    		margin: {top: 20}
    	},
    	items: [{
    		xtype: 'textfield',
    		itemId: 'firstNmField',
    		fieldLabel: 'First Name',
    		name: 'firstName',
    		readOnly: true,
    		cls: 'jfrg_read_only'
    	}, {
    		xtype: 'textfield',
    		itemId: 'lastNmField',
    		fieldLabel: 'Last Name',
    		name: 'lastName',
    		readOnly: true,
    		cls: 'jfrg_read_only'
    	}, {
    		xtype: 'numberfield',
    		itemId: 'kudosField',
    		fieldLabel: 'Kudos',
    		name: 'kudos',
    		readOnly: true,
    		cls: 'jfrg_read_only'
    	}, {
    		xtype: 'textfield',
    		itemId: 'userNmField',
    		fieldLabel: 'Username',
    		name: 'userName',
    		readOnly: true,
    		cls: 'jfrg_read_only'
    	}]
    }],
    buttonAlign: 'center',
    buttons: [{
    	text: 'Apply',
    	itemId: 'userApplyBtn',
    	icon: Base.Globals.getImagePath() + 'save.gif',
    	margin: '5 15 5 0'
    }, {
    	text: 'Cancel',
    	itemId: 'userCancelBtn',
    	icon: Base.Globals.getImagePath() + 'win_close_up.png',
    	margin: '5 0 5 15'
    }]
});