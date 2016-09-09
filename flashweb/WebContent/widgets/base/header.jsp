<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>flashweb II - <%= request.getParameter("title") %></title>
    
    <meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="-1" />
	<meta http-equiv="pragma" content="no-cache" />
	    
    <link rel="stylesheet" href="/flashweb/vendor/extjs/resources/css/ext-all.css">
    <link rel="stylesheet" href="/flashweb/css/flashweb.css">
    <script src="/flashweb/vendor/extjs/ext-all-dev.js"></script>
    <script src="/flashweb/vendor/owf/owf-widget-min.js"></script>
    
    <script src="/flashweb/widgets/base/utils/Utils.js"></script>
    <script src="/flashweb/widgets/base/utils/WidgetInitializer.js"></script>
    <script src="/flashweb/widgets/base/utils/WigetCommunicate.js"></script>

    <script>
	  	//Point OWF to the location of its relay file for owf event channels to work - DO NOT use absolute url path, only domain relative path
	    OWF.relayFile = '/flashweb/vendor/owf/eventing/rpc_relay.uncompressed.html';
        
    	// Configure the ExtJS class loader to recognize the flashweb namespace here because all widgets need to have this configured.
	    Ext.Loader.setConfig({
	    	enabled: true,
	        disableCaching: true,    //only set to true to support debugging - set to false for production release
	    	paths: {
	    		  flashweb: '/flashweb',       //tell the application what our top-level namespace is and where it is located (domain relative path)
	    		  Base: '/flashweb/widgets/base'       //tell the application where to get central resources. 
	    	}
	    });
	    
	  	//create the flashweb namespace object if it does not exist
	    var flashweb = flashweb || {};	
	    
    </script>
</head>
<body>
