/**
 * A custom form panel to work with REST POSTs.
 */
Ext.define('Base.components.FormPanel', {
	extend : 'Ext.form.Panel',
	xtype : 'jfrg.formpanel',

	/**
	 * @cfg {Ext.data.Model} model Model that matches the data that will be
	 *      returned on submission of the form. Defaults to Ext.data.Model.
	 */
	model : 'Ext.data.Model',

	/**
	 * Initializes the error reader for the form
	 * 
	 * @param {Object}
	 *            args Initialization arguments
	 */
	initComponent : function(args) {

		// This errorReader requires "model" to be set!!
		this.errorReader = {
			type : 'json',
			model : this.model,
			read : function(response) {
				if (response.status === 200) {
					return {
						success : true
					};
				} else if (response.status === undefined && response.responseText.indexOf('{') === 0) {
					// If there is no JSON status property, then at least find out if the returned 
					// response text is JSON or not by detecting a curly brace. 
					return {
						success : true
					};					
				} else {
					return {
						success : false
					};
				}
			}
		};

		this.callParent(args);
	}
});
