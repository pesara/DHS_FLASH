Ext.define('Base.InterWidget', {
	singleton: true,

	notify: function(widgetName, widgetChannel, data) {
		if (OWF.Util.isRunningInOWF()) {
			
			var successCallback = function(guid) {
				if (widgetChannel !== null && widgetChannel !== '') {
					if (guid !== null && typeof guid === 'string') {

						var dataString = OWF.Util.toString(data); 
						OWF.Launcher.launch(
							{ 
								guid: guid,
								launchOnlyIfClosed: true,
								title: widgetName,
								data: dataString
							}, 
							function(response) {
								console.log(response);
								if(response.newWidgetLaunched === false) {
									OWF.Eventing.publish(widgetChannel, dataString);
								}
							}
						);
					}
				}
			};

			var shoutInit = owfdojo.hitch(this, function() {
				OWF.Preferences.findWidgets({
					searchParams: {
						widgetName: widgetName
					},
					onSuccess: function(result) {
						var widgetGuid = result[0].id;
						console.log(widgetName + ' GUID: ' + widgetGuid);
						successCallback(widgetGuid);
					},
					onFailure: function(err) {
						console.log('get ' + widgetName + ' failed');
						console.log(err);
					}
				});
			});
			OWF.ready(shoutInit);
		} else {
			console.log('Could not open widget; not running in OWF');
		}
	},
	
	setup: function(channel, launchFn, updateFn, devFn) {
		console.log('Widget Launch...');
		//if running in owf, set this widget up to be opened by other widgets and receive messages from other widgets
		if(OWF.Util.isRunningInOWF()) {
			OWF.ready(function() {
				OWF.Eventing.subscribe(channel, 
					function (sender, message) {
						var data = Ozone.util.parseJson(message);
						if(typeof updateFn !== 'undefined' && updateFn !== null) {
							updateFn(data);
						}
					});
				var launchData = OWF.Launcher.getLaunchData();
				if(typeof launchFn !== 'undefined' && launchFn !== null) {
					if(typeof launchData !== 'undefined' && launchData !== null) {
						var data = Ozone.util.parseJson(launchData);
						launchFn(data);
					} else {
						launchFn(null);
					}
				}
			});
		} else {
			//XXX: run the widget outside of OWF - Development only
			if(typeof devFn !== 'undefined' && devFn !== null) {
				devFn();
			}
		}
	}
});
