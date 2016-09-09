Ext.define('Base.Ajax', {
	requires: 'Base.Globals',
	singleton: true,
	
	requestWithWait: function(reqObj, noErrorHandling, msg, progressText) {
		// Provide default values
		switch(arguments.length) {
			case 1: noErrorHandling = false; break;
			case 2: msg = 'Retrieving data, please wait...'; break;
			case 3: progressText = 'Retrieving...'; break;
			case 4: break;
			default: throw new Error('Illegal argument count');
		}

		var successFn = reqObj.success;
		reqObj.success = function(response) {
			Ext.MessageBox.hide();
			
			if(successFn !== undefined) {
				successFn(response);
			}
		};

		if(!noErrorHandling) {
			var failureFn = reqObj.failure;
			reqObj.failure = function(response, opts) {
				Ext.MessageBox.hide();
				
				Base.Globals.catchJfrgException(response);
	
				if(failureFn !== undefined) {
					failureFn(response);
				}
			};
		}
		
        Ext.MessageBox.show({
            msg: 'Retrieving data, please wait...',
            progressText: 'Retrieving...',
            width:300,
            wait:true,
            waitConfig: {interval:200}
        });
		
		Ext.Ajax.request(reqObj);
	},
	
	request: function(reqObj) {
		// Provide default values
		switch(arguments.length) {
			case 1: break;
			default: throw new Error('Illegal argument count');
		}

		var successFn = reqObj.success;
		reqObj.success = function(response) {
			
			if(successFn !== undefined) {
				successFn(response);
			}
		};

		var failureFn = reqObj.failure;
		reqObj.failure = function(response, opts) {
			
			Base.Globals.catchJfrgException(response);

			if(failureFn !== undefined) {
				failureFn(response);
			}
		};
		
		Ext.Ajax.request(reqObj);
	}
});
