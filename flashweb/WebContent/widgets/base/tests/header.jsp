<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>JFRGII tests - <%= request.getParameter("title") %></title>
    
    <link rel="stylesheet" type="text/css" href="/jfrg/vendor/siesta/siesta-extjs/css/ext-all.css">
    <link rel="stylesheet" href="/jfrg/vendor/siesta/resources/css/siesta-all.css">
    
    <script type="text/javascript" src="/jfrg/vendor/siesta/siesta-extjs/ext-all.js"></script>
    <script src="/jfrg/vendor/siesta/siesta-all.js"></script>
    <script src="/jfrg/vendor/owf/owf-widget-min.js"></script>

    <script>
        //configure the ExtJS class loader to recognize the JFRG namespace
        //- do this here because all widgets need to have this configured
        /* Ext.Loader.setConfig({
            enabled: true,
            disableCaching: false, //only set to false to support debugging - set to true for production release
            paths: {
                JFRG: '/jfrg'
            }
        }); */
        
        var JFRG = JFRG || {};
    </script>
</head>
<body>
