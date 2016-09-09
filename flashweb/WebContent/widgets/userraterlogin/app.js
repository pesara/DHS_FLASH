Ext.require(['Base.Globals']);
Ext.onReady(function() {
	Base.Globals.initExtApplication({
	
		name: 'UL',
		
		appFolder: 'app',
		
		controllers: ['LoginController'],
		
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
