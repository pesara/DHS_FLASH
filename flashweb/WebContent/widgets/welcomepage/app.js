Ext.require(['Base.Globals']);
Ext.onReady(function() {
	Base.Globals.initExtApplication({
	
		name: 'WP',
		
		appFolder: 'app',
		
		controllers: ['WelcomeController'],
		
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
