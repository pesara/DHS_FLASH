Ext.define('UR.controller.UserController', {
	extend: 'Ext.app.Controller',
	views: ['MainPanel', 'UserGrid', 'ChartPanel', 'FormWindow'],
	stores: ['User'],
	models: ['User'],
	
	currentUser: null,
	
	refs: [{
		ref: 'MainPanel',
		selector: 'mainpanel'
	}, {
		ref: 'UserGrid',
		selector: 'usergrid'
	}, {
		ref: 'ChartPanel',
		selector: 'chartpanel'
	}, {
		ref: 'formWindow',
		selector: 'formwindow',
		autoCreate: true,
		xtype: 'formwindow'
	}],
	
	init: function() {
		
		this.control({
			'usergrid': {
				selectionchange: this.onUserSelect
			},
			'#kudosButton': {
				click: this.onGiveKudosClick
			},
			'#refreshButton': {
				click: this.onRefreshClick
			},
			'#createUserBtn': {
				click: this.onCreateUserClick
			},
			'#editUserBtn': {
				click: this.onEditUserClick
			},
			'#deleteUserBtn': {
				click: this.onDeleteUserClick
			},
			'formwindow #applyBtn': {
				click: this.onUserApplyClick
			}
			
		});
	},
	
	onLaunch: function(app) {
		var me = this;
		
		//any setup operations should be triggered here
		
		
		//get current user info to determine what functions are enabled
		
		var fakeCurrentUser = {
			lastName: 'Plissken',
			firstName: 'Snake',
			userName: 'splissken',
			kudos: 21,
			role: 'admin'
		};
		
		me.currentUser = fakeCurrentUser;
		
		me.initialize();
	},
	
	initialize: function() {
		var me = this;
		
		var userStore = me.getUserStore();
		userStore.removeAll();
		
		//Call to back end to get user data will go here
		
		
		//adding fake data for testing
		var fakeUserData = [{
			lastName: 'Plissken',
			firstName: 'Snake',
			userName: 'splissken',
			kudos: 21,
			role: 'admin'
		}, {
			lastName: 'Appleseed',
			firstName: 'Johnny',
			userName: 'jappleseed',
			kudos: 7,
			role: 'user'
		}, {
			lastName: 'Ford',
			firstName: 'Henry',
			userName: 'hford',
			kudos: 17,
			role: 'user'
		}, {
			lastName: 'Anderson',
			firstName: 'Thomas',
			userName: 'tanderson',
			kudos: 34,
			role: 'user'
		}, {
			lastName: 'Saget',
			firstName: 'Bob',
			userName: 'bsaget',
			kudos: 67,
			role: 'user'
		}, {
			lastName: 'Dan',
			firstName: 'Steely',
			userName: 'sdan',
			kudos: 4,
			role: 'user'
		}];
		
		userStore.add(fakeUserData);
		
		if (me.currentUser.role === 'admin') {
			me.getUserGrid().down('#createUserBtn').enable();
		}
	},
	
	onUserSelect: function(selModel, selected, eOpts) {
		var kudosBtn = this.getUserGrid().down('#kudosButton');
		var editBtn = this.getUserGrid().down('#editUserBtn');
		var deleteBtn = this.getUserGrid().down('#deleteUserBtn');
		
		kudosBtn.setDisabled(!selected.length > 0);
		editBtn.setDisabled(!selected.length > 0 || this.currentUser.role !== 'admin');
		deleteBtn.setDisabled(!selected.length > 0 || this.currentUser.role !== 'admin');
	},
	
	onGiveKudosClick: function(btn) {
		var user = this.getUserGrid().getSelectionModel().getSelection()[0];
		
		//this will be replaced with the back end call to give a user a kudos
		user.set('kudos', user.get('kudos') + 1);
	},
	
	onRefreshClick: function(btn) {
		this.initialize();
	},
	
	onCreateUserClick: function(btn) {
		var win = this.getFormWindow();
		
		win.down('#firstNmField').removeCls('jfrg_read_only').setReadOnly(false);
		win.down('#lastNmField').removeCls('jfrg_read_only').setReadOnly(false);
		win.down('#userInfoForm').getForm().reset(true);
		
		win.show();
	},
	
	onEditUserClick: function(btn) {
		var user = this.getUserGrid().getSelectionModel().getSelection()[0];
		var win = this.getFormWindow();
		var userForm = win.down('#userInfoForm').getForm();
		
		userForm.loadRecord(user);
		
		win.down('#firstNmField').removeCls('jfrg_read_only').setReadOnly(false);
		win.down('#lastNmField').removeCls('jfrg_read_only').setReadOnly(false);
		
		win.show();
	},
	
	onDeleteUserClick: function(btn) {
		var user = this.getUserGrid().getSelectionModel().getSelection()[0];
		var userStore = this.getUserStore();
		
		Ext.Msg.confirm('Delete User', 'Are you sure you want to delete user ' +
				user.get('userName') + ' ?', function(id, value) {
				
				if (id === 'yes') {
					//this will be where the delete service is called
					userStore.remove(user);
				}
		});
	},
	
	onUserApplyClick: function(btn) {
		var win = this.getFormWindow();
		var userForm = win.down('#userInfoForm').getForm();
		var selectedUser = userForm.getRecord();
		var userStore = this.getUserStore();
		
		if (selectedUser) {
			Ext.Msg.confirm('Edit User', 'Are you sure you want to make these changes?', function(id, value) {
				if (id === 'yes') {
					//this will be where the update service is called
					userForm.updateRecord(selectedUser);
					win.close();
				}
			});
		} else {
			var userValues = userForm.getFieldValues();
			Ext.Msg.confirm('Create User', 'Are you sure you want to create this user?', function(id, value) {
				if (id === 'yes') {
					//this will be where the create service is called
					userValues.kudos = 0;
					userValues.userName = (userValues.firstName.substring(0,1) + userValues.lastName).toLowerCase();
					
					userStore.add(userValues);
					
					win.close();
				}
			});
		}
	}
	
	
});
