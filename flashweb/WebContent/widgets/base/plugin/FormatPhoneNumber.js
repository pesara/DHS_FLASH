Ext.define('Base.plugin.FormatPhoneNumber', {
	extend: 'Ext.form.TextField',
	alias: 'plugin.formatphonenumber',
	init: function(c) {
		c.on('change', this.onChange, this);
	},
	onChange: function(c) {
		c.setValue(Ext.util.Format.phoneNumber(c.getValue()));
	}
});