Ext.define('Base.components.YesNoCombo', {
	extend: 'Ext.form.ComboBox',
	xtype: 'jfrg.yesnocombo',
	width: 40,
	queryMode: 'local',
	displayField: 'item',
	valueField: 'value',
	store: {
		xtype: 'array',
		fields: [{
			name: 'value',
		}, {
			name: 'item'
		}],
		data: [['', ' '], ['N', 'N'], ['Y', 'Y']]
	},
	
	initComponent: function(args) {
		this.callParent(args);
	}
});