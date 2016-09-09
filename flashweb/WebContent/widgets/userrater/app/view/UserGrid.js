Ext.define('UR.view.UserGrid', {
	extend: 'Ext.grid.Panel',
	xtype: 'usergrid',
	itemId: 'userGrid',
    layout: 'anchor',
    region: 'center',
    store: 'User',
    selModel: {
		mode: 'SINGLE',
		allowDeselect: true,
		toggleOnClick: true
	},
    tbar: [{
		xtype: 'textfield',
		itemId: 'searchField',
		fieldLabel: 'Search',
		labelAlign: 'left',
		labelWidth: 40,
		fieldStyle: { textTransform: 'uppercase' },
		width: 230,
		margin: '5 0 5 10'
	}, {
		xtype: 'button',
		itemId: 'searchButton',
		margin: '0 0 0 10',
		icon: Base.Globals.getImagePath() + 'lov_ena.png'
	}, {
		xtype: 'button',
		itemId: 'kudosButton',
		text: 'Give Kudos',
		margin: '0 0 0 50',
		disabled: true,
		border: 1,
		icon: Base.Globals.getImagePath() + 'thumbs-up.gif'
	}, {
		xtype: 'button',
		itemId: 'adminActionsButton',
		text: 'Admin Actions',
		margin: '0 0 0 30',
		menu: {
			items: [{
		    	text: 'Create User',
		    	itemId: 'createUserBtn',
		    	icon: Base.Globals.getImagePath() + 'add.gif',
		    	disabled: true
		    }, {
		    	text: 'Edit User',
		    	itemId: 'editUserBtn',
		    	icon: Base.Globals.getImagePath() + 'actionEdit.gif',
		    	disabled: true
		    }, {
		    	text: 'Delete User',
		    	itemId: 'deleteUserBtn',
		    	icon: Base.Globals.getImagePath() + 'delete.gif',
		    	disabled: true
		    }]
		}
	}, {
		xtype: 'tbfill'
	}, '-', {
		xtype: 'button',
		id: 'refreshButton',
		margin: '0 7.5 0 7.5',
		icon: Base.Globals.getImagePath() + 'refresh.gif',
		tooltip: 'Refresh the Grid'
	}],
    items: [],
    columns: [{
    	text: 'Last Name',
    	flex: 1,
    	dataIndex: 'lastName'
    }, {
    	text: 'First Name',
    	flex: 1,
    	dataIndex: 'firstName'
    }, {
    	text: 'Username',
    	flex: 1,
    	dataIndex: 'userName'
    }, {
    	text: 'Kudos',
    	flex: 1,
    	dataIndex: 'kudos'
    }]
});