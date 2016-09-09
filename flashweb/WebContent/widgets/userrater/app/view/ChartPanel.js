Ext.define('UR.view.ChartPanel', {
	extend: 'Ext.panel.Panel',
	xtype: 'chartpanel',
	itemId: 'chartPanel',
	region: 'east',
	collapsible: true,
	collapsed: false,
	split: true,
	width: '40%',
	layout: 'fit',
	title: 'Ratings',
	titleAlign: 'center',
	autoScroll: true,
	tbar: [{
		xtype: 'button',
		itemId: 'queryActionButton',
		text: 'Queries',
		margin: '0 0 0 15',
		menu: {
			items: [{
		    	text: 'Query 1',
		    	itemId: 'query1Btn'
		    }, {
		    	text: 'Query 2',
		    	itemId: 'query2Btn'
		    }, {
		    	text: 'Query 3',
		    	itemId: 'query3Btn'
		    }]
		}
	}],
	items: [{
		xtype: 'chart',
		width: '100%',
		height: '100%',
		padding: '5 5 5 5',
		insetPadding: 20,
		animate: true,
		shadow: true,
		store: 'User',
		axes: [{
			type: 'Numeric',
			position: 'left',
			fields: ['kudos'],
			minimum: 0
		}, {
			type: 'Category',
			position: 'bottom',
			fields: ['userName'],
			label: {
				rotate: {degrees: -45}
			}
		}],
		series: [{
			type: 'column',
			axis: 'left',
			xField: 'userName',
			yField: 'kudos',
			highlight: {
                fill: '#000',
                'stroke-width': 20,
                stroke: '#fff'
            },
            tips: {
                trackMouse: true,
                style: 'background: #FFF',
                height: 20,
                renderer: function(storeItem, item) {
                    this.setTitle(storeItem.get('userName') + ': ' + storeItem.get('kudos'));
                }
            }
		}]
	}]
});