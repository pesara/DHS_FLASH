Ext.require(['Base.Globals']);
Ext.onReady(function() {
	Base.Globals.initExtApplication({
	
		name: 'UR',
		
		appFolder: 'app',
		
		controllers: ['UserController'],
		
		launch: function() {
			Ext.create('Ext.container.Viewport', {
				layout: 'fit',
				items: [{
						xtype: 'mainpanel'
				}]
			});
		}
	});
});
