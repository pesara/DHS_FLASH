var JFRG = JFRG || {};

(function(){
    JFRG.currentUser = "Not Loaded Yet";    //put a default value to help with debugging
    
    //create an instance of the widget and save a copy of the currently logged in user's details
    function initExt(widgetName, username) {
		Ext.require('Base.Globals');
		console.log('initializing ' + widgetName);
		Ext.onReady(function() {
			
		    //get the user details for the currently logged in user and create an instance of the widget
		    Ext.Ajax.request({
		        method: 'POST',
		        url: Base.Globals.userSvcUrl + '/getuser',
		        params: {
		            usrUsernameTx: username
		        },
		        success: function(response) {
		            JFRG.currentUser = Ext.JSON.decode(response.responseText);
		            console.log('Loading: ' + widgetName);
		            console.log("current user is: ");
		            console.log(JFRG.currentUser);
		            
		            var widget = Ext.create(widgetName);
		            widget.init();
		        }
		    });
		});
    }
    
    /**
     * initialize OWF and prepare to load an instance of the widget
     * fakeUsername can be null - it's only used if you are not running in OWF
     */
    JFRG.initWidget = function(widgetName, fakeUsername) {
        OWF.ready(function () {
            //console.log('get owf current user');
            OWF.Preferences.getCurrentUser({
                onSuccess: function(result) {
                    /*console.log('got owf current user - result: ');
                    console.log(result);
                    */
                    initExt(widgetName, result.currentUserName);
                },
                onFailure: function() {
                    console.error('Failed to get OWF user');
                }
            });
            
            if(OWF.Util.isRunningInOWF()) {
                //console.log('OWF widget ready');
                OWF.notifyWidgetReady(); //every widget must make this call after it is finished setting up and is ready for use
            }
        });

        /**
         * support standalone (non-OWF) mode for development
         * in eclipse:  click Run As -> Run on Server
         *              then browse to localhost:8080/jfrg/widgets/[widgetname]
         */
        if(!OWF.Util.isRunningInOWF()) {
            //console.log('not running in OWF');
            var fun = fakeUsername || 'testAdmin1';
            initExt(widgetName, fun);
        } else {
            //console.log('running in OWF');
        }
    };
    
}());