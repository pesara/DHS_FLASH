Ext.define('UR.view.MainPanel', {
	extend: 'Ext.panel.Panel',
	xtype: 'mainpanel',
	itemId: 'mainPanel',
    layout: 'border',
    title: 'User Rater',
    titleAlign: 'center',
    items: [{
    	xtype: 'usergrid'
    }, {
    	xtype: 'chartpanel'
    }]
});