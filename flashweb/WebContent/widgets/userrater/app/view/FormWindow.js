Ext.define('UR.view.FormWindow', {
	extend: 'Ext.window.Window',
	xtype: 'formwindow',
	itemId: 'formWindow',
	title: 'User Information Form',
	icon: JFRG.utils.getImagePath() + 'edit_grid.gif',
	collapsed: false,
	maximizable: false,
	minimizable: false,
	resizable: false,
	height: 330,
	width: 400,
	layout: 'border',
	closeAction: 'hide',
	modal: true,
	bodyPadding: 5,
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
    	itemId: 'applyBtn',
    	icon: Base.Globals.getImagePath() + 'save.gif',
    	margin: '5 15 5 0'
    }, {
    	text: 'Cancel',
    	itemId: 'cancelBtn',
    	icon: Base.Globals.getImagePath() + 'win_close_up.png',
    	margin: '5 0 5 15',
    	handler: function(btn) {
			btn.up('formwindow').close();
		}
    }]
});