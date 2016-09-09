Ext.define('Base.Globals', {
	singleton: true,
	owfUrl: '/owf/',
	context: '/jfrg',
	requestSvcUrl: '/services/requestaccess',
	userSvcUrl: '/services/jfrguser',
	planManagerSvcUrl: '/services/jfrgplanmanager',
	ulnManagerSvcUrl: '/services/ulnman',
	ulnDetailsSvcUrl: '/services/jfrguln',
	fmManagerSvcUrl: '/services/dvlfm',
	refSvcUrl: '/services/jfrgreference',
	cccSvcUrl: '/services/jfrgccc',
	messageSvcUrl: '/services/jfrgmessages',
	workflowSvcUrl: '/services/workflow',
	reportSvcUrl: '/services/report',
	downloadSvcUrl: '/services/download',
	transManagerSvcUrl: '/services/transportationmanager',
	jfrgErrorStatus: 409,

	/**
	 * Constant for the no permission.
	 */
	PERMISSION_FLAG_NONE: 0,

	/**
	 * Constant for the Plan Management permission.
	 */
	PERMISSION_FLAG_PLAN: 1,

	/**
	 * Constant for the User Management permission.
	 */
	PERMISSION_FLAG_USER: 2,

	/**
	 * Constant for the Force Module Management permission.
	 */
	PERMISSION_FLAG_FORCE: 4,

	/**
	 * Constant for the Workflow Management permission.
	 */
	PERMISSION_FLAG_WORKFLOW: 8,

	/**
	 * Constant for the Report Management permission.
	 */
	PERMISSION_FLAG_REPORT: 16,
	user: null,
	constructor: function() {
		var base = document.getElementsByTagName('base')[0];

		if (base && base.href && (base.href.length > 0)) {
			base = base.href;
		} else {
			base = document.URL;
		}
		var indexOfContext = base.indexOf("/", base.indexOf("//") + 2);
		this.context = base.substring(indexOfContext, base.indexOf("/", indexOfContext + 1));

		console.log('Auto-detected app context: ' + this.context);
		
		this.requestSvcUrl = this.context + this.requestSvcUrl;
		this.userSvcUrl = this.context + this.userSvcUrl;
		this.planManagerSvcUrl = this.context + this.planManagerSvcUrl;
		this.ulnManagerSvcUrl = this.context + this.ulnManagerSvcUrl;
		this.ulnDetailsSvcUrl = this.context + this.ulnDetailsSvcUrl;
		this.fmManagerSvcUrl = this.context + this.fmManagerSvcUrl;
		this.refSvcUrl = this.context + this.refSvcUrl;
		this.cccSvcUrl = this.context + this.cccSvcUrl;
		this.messageSvcUrl = this.context + this.messageSvcUrl;
		this.workflowSvcUrl = this.context + this.workflowSvcUrl;
		this.reportSvcUrl = this.context + this.reportSvcUrl;
		this.downloadSvcUrl = this.context + this.downloadSvcUrl;
		this.transManagerSvcUrl = this.context + this.transManagerSvcUrl;
	},
	
	checkJfrgException: function(response) {
		return (response.status === 409);
	},
	
	catchJfrgException: function(response) {
		if(this.checkJfrgException(response)) {
			// 409 - Conflict HTTP code is used to carry error messages from the server.
			Ext.MessageBox.show({
				title : 'Error',
				msg : response.responseText,
				buttons : Ext.MessageBox.OK
			});				
		} else {
			console.log('server-side failure with status code ' + response.status);
			Ext.MessageBox.show({
				title : 'Error',
				msg : 'Unable to retrieve data. Status: ' + response.status,
				buttons : Ext.MessageBox.OK
			});
		}		
	},
	
	initExtApplication: function (appConfig) {
		var counter = 2; // NOTE: This counter MUST match the number of concurrent AJAX calls below.
		var joinFn = function() {
			counter--;
			if(counter <= 0) {
				// Now start up the Ext application. 
				Ext.application(appConfig);				
			}			
		};
		
		// Make sure ajax requests are large enough to get results from DVL. 
		Ext.Ajax.timeout = 300000;
		Ext.override(Ext.data.proxy.Ajax, {
			timeout: Ext.Ajax.timeout,
			listeners:{
				'exception': function(proxy, response, oper, opts) {
					Base.Globals.catchJfrgException(response);
				}
			}
		});
		Ext.override(Ext.data.Store, {
			listeners:{
				'beforeload': function(store, oper, opts) {
			        Ext.MessageBox.show({
			            msg: 'Loading...',
			            progressText: 'Loading...',
			            width:300,
			            wait:true,
			            waitConfig: {interval:200}
			        });
				},
				'refresh': function(store, opts) {
					if(!Ext.MessageBox.isHidden()) {
						Ext.MessageBox.hide();
					}
				}
			}
		});

		if(appConfig.stateProvider === null || appConfig.stateProvider === undefined) {
			Ext.state.Manager.setProvider(
					   Ext.create('Base.utils.DBState', {
					     widget: appConfig.name,
					     callback: joinFn
					   })
					);			
		} else {
			Ext.state.Manager.setProvider(appConfig.stateProvider);
			joinFn();
		}
		
		Ext.EventManager.on(document, 'keydown', function(e, t) {
            if (e.getKey() == e.BACKSPACE && ((!/^input$/i.test(t.tagName) && !/^textarea$/i.test(t.tagName)) || t.disabled || t.readOnly)) {
                e.stopEvent();
            }
        });

		Ext.Ajax.request({
			method: 'GET',
			url: this.userSvcUrl + '/current',
			scope: this,
			success: function (response) {
				this.user = Ext.decode(response.responseText);
				console.log('you are:');
				console.log(this.user);
				// Extract the launch function and hook in some OWF readiness code. 
				launchFunction = appConfig.launch;
				appConfig.launch = function (profile) {
					var retVal = launchFunction(profile);
					console.log('Finished launching application.');
					OWF.ready(function () {
						if (OWF.Util.isRunningInOWF()) {
							console.log('OWF widget ready');
							OWF.notifyWidgetReady(); //every widget must make this call after it is finished setting up and is ready for use
						}
					});
					return retVal;
				};
				
				joinFn();
			},
			failure: function (response) {
				console.log('Could not retrieve username for authenticated user.');
				joinFn();
			}
		});
	},
	getGarrisonName: function() {
		return 'GARRISON WORKFLOW TEMPLATE';
	},
	getUsername: function () {
		return this.user.name;
	},
	getUserRole: function () {
		return this.user.role;
	},
	getWorkNodeId: function () {
		return this.user.workId;
	},
	isUserInFmRole: function() {
		return (this.getUserRole() === 'FM');
	},
	isUserInSubFmRole: function() {
		return (this.getUserRole() === 'SubFM');
	},
	isUserInUserRole: function() {
		return (this.getUserRole() === 'User');
	},
	isUserNotVisitorRole: function() {
		return (this.isUserInFmRole() || this.isUserInSubFmRole() || this.isUserInUserRole());
	},
	hasPlanManagerPermissions: function() {
		return (this.isUserInFmRole() || (this.user.permissionFlags & this.PERMISSION_FLAG_PLAN) !== 0);
	},
	hasUserManagerPermissions: function() {
		return (this.isUserInFmRole() || (this.user.permissionFlags & this.PERMISSION_FLAG_USER) !== 0);
	},
	hasForceModulePermissions: function() {
		return (this.isUserInFmRole() || (this.user.permissionFlags & this.PERMISSION_FLAG_FORCE) !== 0);
	},
	hasWorkflowPermissions: function() {
		return (this.isUserInFmRole() || (this.user.permissionFlags & this.PERMISSION_FLAG_WORKFLOW) !== 0);
	},
	hasReportPermissions: function() {
		return (this.isUserInFmRole() || (this.user.permissionFlags & this.PERMISSION_FLAG_REPORT) !== 0);
	},
	strTimeToDate: function(value) {
		if (value) {
			try {
				return new Date(parseInt(value));
			} catch (err) {
				console.log(err);
			}
		}
		return null;
	},
	strToDate: function(value) {
		if (value) {
			try {
				return new Date(value);
			} catch (err) {
				console.log(err);
			}
		}
		return null;
	},
	formatZuluDate: function(dt) {    
		if (dt) {
			return Ext.Date.format(dt,'dHi\\Z\\ M\\ y');
		}
		return '';
	},
	requiredField: function() {
		return '<span style="color:red" data-qtip="Required">*</span>';
	},
	//returns true if arg is not undefined and not null
	isDefined: function(arg) {
	    return ((typeof arg !== typeof undefined) && (arg !== null));
	},

	//returns true if arg is undefined, null, or ''
	isBlank: function(arg) {
	    return (!isDefined(arg) || (arg.trim() === ''));
	},
	
	calculateDistance: function(lat1, lon1, lat2, lon2) {
		var deg2rad = Math.PI / 180;
		lat1 *= deg2rad;
		lon1 *= deg2rad;
		lat2 *= deg2rad;
		lon2 *= deg2rad;
		var diam = 12742; // Earth diam in Km
		var dLat = lat2 - lat1;
		var dLon = lon2 - lon1;
		var a = ((1 - Math.cos(dLat)) + (1 - Math.cos(dLon)) * Math.cos(lat1) * Math.cos(lat2)) / 2;

		var distanceInKm = diam * Math.asin(Math.sqrt(a));
		
		return Math.round(distanceInKm * 0.621371);
	},
	
	parseLatLongPoint: function(input) {
		
		var degree, minutes, seconds, direction;
		
		if (input.length === 7) {
			degree = parseInt(input.substring(0,2));
			minutes = parseInt(input.substring(2,4));
			seconds = parseInt(input.substring(4,6));
			direction = input.substring(6);
		} else if (input.length === 8) {
			degree = parseInt(input.substring(0,3));
			minutes = parseInt(input.substring(3,5));
			seconds = parseInt(input.substring(5,7));
			direction = input.substring(7);
		}
		
		var decDegree = this.convertToDecDegree(degree, minutes, seconds, direction);
		
		return decDegree;
	},
	
	convertToDecDegree: function(deg, min, sec, direction) {
		var dd = deg + (min/60) + (sec/3600);
		
	    if (direction === "S" || direction === "W") {
	        dd *= -1;
	    }
	    return dd;
	},
	
	isConus: function(code) {
		var isConus = true;
		var c = Number(code);
		
		if (isNaN(c) || c === 2 || c === 15 || c < 2 || c > 51) {
			isConus = false;
		}
		return isConus;
	},
	
	formatCDay: function(value, record) {
		if (isNaN(value) ||
				value === null ||
				value === undefined ||
				(typeof value === 'string' && value.trim() === '')) {
			return '';
		} else {
			var cDay = (parseInt(value) >= 0 ? 'C' : 'N');
			var temp = Math.abs(parseInt(value)).toString();
			if (temp.length === 1) {
				cDay += '00' + temp;
			} else if (temp.length === 2) {
				cDay += '0' + temp;
			} else if (temp.length === 3) {
				cDay += temp;
			}
			if (cDay.length !== 4) {
				cDay = '';
			}
			return cDay;
		}
	},
	
	formatCday: function(value) {
		if (isNaN(value) ||
				value === null ||
				value === undefined ||
				(typeof value === 'string' && value.trim() === '')) {
			return '';
		} else {
			var cDay = (parseInt(value) >= 0 ? 'C' : 'N');
			var temp = Math.abs(parseInt(value)).toString();
			if (temp.length === 1) {
				cDay += '00' + temp;
			} else if (temp.length === 2) {
				cDay += '0' + temp;
			} else if (temp.length === 3) {
				cDay += temp;
			}
			return cDay;
		}
	},
	
	getImagePath: function() {
		return '../../images/';
	},
	
	getHelpPath: function() {
		return '../../help/';
	},
	
	getMsgText: function(msg) {
		var bodyText = '';
		var noteText = '';
		var bodies;
		var header = '';
		var footer = '';
		var fullText = '';
		var pluralBodies = false;
		
		var notes = msg.notes;
		if (notes.length > 0) {
			for (var i=0; i<notes.length; i++) {
				noteText += 'NOTE ' + (i+1).toString() + ': ' +
				notes[i].note + '<br>';
			}
		}
		
		bodies = msg.body;
		pluralBodies = (bodies.length > 1);
		
		bodyText = Base.Globals.buildBodyText(bodies, msg.messageType);
		header = Base.Globals.buildHeader(msg, pluralBodies);
		footer = Base.Globals.buildFooter(msg);
		
		fullText += header + bodyText + noteText + footer;
		return fullText;
	},
	
	buildHeader: function(msg, pluralBodies) {
		var txt = '';
		
		var fromUnit = msg.from,
			classification = msg.classification,
			operationName = msg.oper,
			subject = msg.subject,
			msgId = msg.messageId.toString(),
			pid = msg.planId,
			pidClassification = msg.classification,
			refChar = 'A',
			refDesc = '',
			refs = msg.refs,
			pocText = '',
			declassifyDate = msg.declassifyDate.toString();
		
		for (var i=0; i<msg.procs.length; i++) {
			pocText += 'POC/' + msg.procs[i].lastName + '/' + msg.procs[i].rank + '/' +
				msg.procs[i].phoneNumber + '/' + msg.procs[i].phoneType + '//<br>';
		}
		
		//header
		txt = 'FM ' + fromUnit + '//<br>';
		
		for (i=0; i<msg.tos.length; i++) {
			txt += 'TO ' + msg.tos[i].nodeName + '//<br>';
		}
		for (i=0; i<msg.info.length; i++) {
			txt += 'INFO ' + msg.info[i].nodeName + '//<br>';
		}
		
		txt += 'BT<br>';
		txt += classification + '//' + 'N03000//<br>';
		txt += 'OPER/' + operationName + '//<br>';
		txt += 'MSGID/NEWSGROUP/' + msg.newsgroup + '//<br>';
		txt += 'SUBJ/' + subject + '//<br>';
		
		for (i=0; i<refs.length; i++) {
			if (i > 0) refChar = this.nextRefChar(refChar);
			if (refs[i].type === 'MSG') refDesc = refs[i].releaser;
			else {
				refDesc = refs[i].name;
			}
			txt += 'REF/' + refChar + '/' + refs[i].type + '/' + refDesc +
				'/' + refs[i].cmdRelDt + ' (' + refs[i].classification + ')//<br>';
		}
		
		txt += 'NARR/ ';
		
		for (i=0; i<refs.length; i++) {
			if (refs[i].narr !== undefined) {
				txt += refs[i].narr;
				if (i < refs.length - 1) txt += '  ';
			}
		}
		txt += '//<br>';
		
		txt += pocText;
		
		var remarkText = this.buildRemarkText(msg, pluralBodies, refChar);
		
		txt += remarkText;
		txt += '-----------------------------------------------------------------------------<br>';
		
		return txt;
	},
	
	nextRefChar: function(char) {
		return String.fromCharCode(char.charCodeAt(0) + 1);
	},
	
	buildRemarkText: function(msg, plural, refChar) {
		var text = 'RMKS/';
		var pluralText = (plural ? 'ULNS' : 'ULN');
		var pluralHave = (plural ? 'ULNS HAVE' : 'ULN HAS');
		var remarkClass = msg.remarkClass;
		
		switch (msg.messageType) {
			case 'ACTION_COMPLETE_OF_ULNS_IN_TPFDD':
				text += '1. (' + remarkClass + ') ACTION COMPLETE ON THE FOLLOWING';
				text += pluralText + ':<br>';
				break;
			case 'BUILD_REQUEST_OF_ULNS_IN_TPFDD':
				text += '1. (' + remarkClass + ') REQUESTS THE FOLLOWING ';
				text += pluralText + ' ';
				text += 'BUILT INTO ' + msg.planId + ':<br>';
				break;
			case 'CARRIER_AVAILABLE_FOR_MANIFESTING_IN_TPFDD':
				text += '1. (' + remarkClass + ') IN ACCORDANCE WITH REFS A THRU ' + refChar +
					', THE FOLLOWING CARRIERS<br>CONTAINED IN TPFDD ' +
					msg.planId + ' HAVE BEEN ALLOCATED AND REQUIRE MANIFESTING IN WEB<br>' +
					'SCHEDULING AND MOVEMENT SUBSYSTEM OF JOPES WITH THE FOLLOWING ';
				text += pluralText + ':<br>';
				break;
			case 'CARRIER_MANIFEST_FOR_TPFDD':
				text += '1. (' + remarkClass + ') THE FOLLOWING ';
				text += pluralHave + ' ';
				text += 'BEEN MANIFESTED IN TPFDD ' + msg.planId + ':<br>';
				break;
			case 'CERTIFICATION_OF_ULNS_IN_TPFDD':
				text += '1. (' + remarkClass + ') PER REF A AND IN ACCORDANCE WITH REFS B THROUGH ' +
					refChar + ', THE<br>BELOW ' + pluralText + ', CONTAINED IN TPFDD ' + msg.planId +
					', HAVE BEEN PLACEED ON-CALL:<br>';
				break;
			case 'CHANGE_REQUEST_OF_ULNS_IN_TPFDD':
				text += '1. (' + remarkClass + ') REQUEST AUTHORIZATION TO MAKE MODIFICATION(S) TO THE FOLLOWING<br>' +
					pluralText + ' IN TPFDD ' + msg.planId + ':<br>';
				break;
			case 'DEPLOYMENT_VERIFICATION_OF_ULNS_IN_TPFDD':
				text += '1. (' + remarkClass + ') PER REF A AND IN ACCORDANCE WITH REFS B THRU ' + refChar + ', REQUEST<br>' +
					'VERIFICATION OF THE FOLLOWING ' + pluralText + ' IN TPFDD ' + msg.planId + '(FM ' +
					msg.from + '):<br>';
				break;
			case 'FORCE_CLOSURE_OF_ULNS_IN_TPFDD':
				text += '1. (' + remarkClass + ') THE FOLLOWING ' + pluralText + ' ARE FORCE CLOSED IN TPFDD ' + msg.planId + ':<br>';
				break;
			case 'FRN_AVAILABLE_FOR_SOURCING':
				text += '1. (' + remarkClass + ') PER REF A AND IN ACCORDANCE WITH REFS B THRU ' + refChar + ', THE BELOW FRN(S)<br>' +
					'CONTAINED IN TPFDD ' + msg.planId + ' ARE AVAILABLE FOR SOURCING AND REFINEMENT:<br>';
				break;
			case 'GOE_UNLOCK_REQUEST':
				text += '1. (' + remarkClass + ') PER REF A AND IN ACCORDANCE WITH REFS B THROUGH ' + refChar + ', REQUEST<br>' +
					'VERIFICATION OF THE FOLLOWING ' + pluralText + ' IN TPFDD ' + msg.planId + '(FM ' + msg.from + '):<br>';
				break;
			case 'GOE_DEPLOYMENT_VERIFICATION':
				text += '1. (' + remarkClass + ') PER REF A AND IN ACCORDANCE WITH REFS B THRU ' + refChar + ', REQUEST VERIFICATION<br>' +
					' OF THE FOLLOWING ' + pluralText + ' IN TPFDD ' + msg.planId + '(FM ' + msg.from + '):<br>';
				break;
			case 'REQUEST_ID_CARRIERS':
				text += '1. (' + remarkClass + ') ' + msg.tos[0].nodeName + ': IN ACCORDANCE WITH REFS B THROUGH ' + refChar + ', ' +
					msg.from + ' DIRECTS ' + msg.tos[0].nodeName + '<br>TO IDENTIFY THE CARRIER, REPORT MANIFEST, ' +
					'REPORT THE CARRIER ONLOAD DEPARTURE DATE, AND THE<br>CARRIER OFFLOAD ARRIVAL DATE FOR THE BELOW ' +
					pluralText + ' IN THE WEB SCHEDULING & MOVEMENT SUBSYSTEM OF JOPES:<br>';
				break;
			case 'RETURN_OF_FRNS_WITH_NO_ACTION_TAKEN':
				text += '1. (' + remarkClass + ') IN ACCORDANCE WITH REF A, ' + msg.from + ' RETURNS THE BELOW FRNS WITH NO ACTION TAKEN:<br>';
				break;
			case 'SUBMISSION_OF_ITINERARY_OF_ULNS_IN_TPFDD':
				text += '1. (' + remarkClass + ') REQUEST CARRIER GENERATION WITH THE CARRIER ONLOAD DEPARTURE DATE<br>' +
					'AND CARRIER OFFLOAD ARRIVAL DATE IDENTIFIED FOR THE BELOW ' + pluralText + ' IN THE WEB<br>' +
					'SCHEDULING & MOVEMENT SUBSYSTEM OF JOPES:<br>';
				break;
			case 'TRANSFER_OF_ULNS_IN_TPFDD':
				text += '1. (' + remarkClass + ') PER REF A, AND IN ACCORDANCE WITH REFS B THRU ' + refChar + ', ' + msg.from + ' TRANSFERS<br>' +
					'THE BELOW ' + pluralText + ' CONTAINED IN TPFDD ' + msg.planId + ' ' + msg.tos[0].nodeName + '<br>' +
					msg.from + ' DIRECTS ' + msg.tos[0].nodeName + ' TO REVIEW THE BEELOW ' + pluralText + ' FOR ACCURACY:<br>';
				break;
		}
		
		return text;
	},
	
	buildFooter: function(msg) {
		var txt = '',
		declassifyDate = Base.Globals.formatZuluDate( Base.Globals.strToDate(msg.declassifyDate) ),
		dSlash = msg.dSlash,
		rSlash = msg.rSlash;
		
		//footer
		txt += '-----------------------------------------------------------------------------<br>';
		txt += 'DECLASSIFY ON: ' + declassifyDate + '<br>';
		txt += 'D/ ' + dSlash.rank + ' ' + dSlash.lastName + ', ' +
			dSlash.firstInitial + '. ' + dSlash.middleInitial + '<br>';
		txt += 'R/ ' + rSlash;
		
		return txt;
	},
	
	buildBodyText: function(bodies, msgType) {
		var text = '';
		
		var i = 0;
		
		var pid, uln, ulnId, unitName, forceDesc, ustc, ead,
		lad, rld, rdd, pax, pers, stons, origin, dest, pod, poe,
		poeName, podName, geoDepart, crrType, depDtg, geoArr,
		arrDtg, ms, note = '';
		
		if (msgType === 'GOE_UNLOCK_REQUEST') {
			
			text += 
				'<table><tr><td>PID</td><td>ULN</td>' +
				'<td>USTC</td><td>EAD</td><td>LAD</td>' +
				'<td>PAX</td><td>STONS</td><td>POE NAME</td>' +
				'<td>POD NAME</td><td>MS</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				ustc = bodies[i].ustc;
				ead = this.formatCday(bodies[i].ead);
				lad = this.formatCday(bodies[i].lad);
				pax = bodies[i].pax;
				stons = bodies[i].totalWeight;
				poeName = bodies[i].poeName;
				podName = bodies[i].podName;
				ms = bodies[i].mode + bodies[i].source;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' + 
					ulnId + '</td><td>' + 
					ustc + '</td><td>' +
					ead + '</td><td>' +
					lad + '</td><td>' + 
					pax + '</td><td>' + 
					stons + '</td><td>' +
					poeName + '</td><td>' +
					podName + '</td><td>' +
					ms + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'GOE_DEPLOYMENT_VERIFICATION') {
			
			text += 
				'<table><tr><td>PID</td><td>ULN</td>' +
				'<td>EAD</td><td>LAD</td><td>PAX</td>' +
				'<td>STONS</td><td>POE</td><td>POD</td>' +
				'<td>MS</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				ead = this.formatCday(bodies[i].ead);
				lad = this.formatCday(bodies[i].lad);
				pax = bodies[i].pax;
				stons = bodies[i].totalWeight;
				poe = bodies[i].poe;
				pod = bodies[i].pod;
				ms = bodies[i].mode + bodies[i].source;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' + 
					ulnId + '</td><td>' + 
					ead + '</td><td>' +
					lad + '</td><td>' + 
					pax + '</td><td>' + 
					stons + '</td><td>' +
					poe + '</td><td>' +
					pod + '</td><td>' +
					ms + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'REQUEST_ID_CARRIERS') {
			
			text += 
				'<table><tr><td>PID</td><td>ULN</td>' +
				'<td>EAD</td><td>LAD</td><td>PAX</td>' +
				'<td>STONS</td><td>POE</td><td>POD</td>' +
				'<td>MS</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				ead = this.formatCday(bodies[i].ead);
				lad = this.formatCday(bodies[i].lad);
				pax = bodies[i].pax;
				stons = bodies[i].totalWeight;
				poe = bodies[i].poe;
				pod = bodies[i].pod;
				ms = bodies[i].mode + bodies[i].source;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' + 
					ulnId + '</td><td>' + 
					ead + '</td><td>' +
					lad + '</td><td>' + 
					pax + '</td><td>' + 
					stons + '</td><td>' +
					poe + '</td><td>' +
					pod + '</td><td>' +
					ms + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'RETURN_OF_FRNS_WITH_NO_ACTION_TAKEN') {
			
			text += 
				'<table><tr><td>PID</td><td>ULN</td>' +
				'<td>USTC</td><td>EAD</td><td>LAD</td>' +
				'<td>STONS</td><td>FORCE DESC</td>' +
				'<td>PERS</td><td>RLD</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				ustc = bodies[i].ustc;
				ead = this.formatCday(bodies[i].ead);
				lad = this.formatCday(bodies[i].lad);
				stons = bodies[i].totalWeight;
				forceDesc = bodies[i].forceDescription;
				pers = bodies[i].pers;
				rld = this.formatCday(bodies[i].rld);
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' +
					ulnId + '</td><td>' +
					ustc + '</td><td>' +
					ead + '</td><td>' +
					lad + '</td><td>' +
					stons + '</td><td>' +
					forceDesc + '</td><td>' +
					pers + '</td><td>' +
					rld + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'SUBMISSION_OF_ITINERARY_OF_ULNS_IN_TPFDD') {
			
			text += 
				'<table><tr><td>ULN</td><td>PAX</td>' +
				'<td>STONS</td><td>GEO DEPART</td><td>CRR-TYPE</td>' +
				'<td>DEP DTG</td><td>GEO ARR</td><td>ARR DTG</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				uln = bodies[i].ulnId;
				pax = bodies[i].pax;
				stons = bodies[i].totalWeight;
				geoDepart = bodies[i].geoDepart;
				crrType = bodies[i].crrType;
				depDtg = bodies[i].depDtg;
				geoArr = bodies[i].geoArr;
				arrDtg = bodies[i].arrDtg;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					uln + '</td><td>' +
					pax + '</td><td>' +
					stons + '</td><td>' +
					geoDepart + '</td><td>' +
					crrType + '</td><td>' +
					depDtg + '</td><td>' +
					geoArr + '</td><td>' +
					arrDtg + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'TRANSFER_OF_ULNS_IN_TPFDD') {
			
			text += 
				'<table><tr><td>PID</td><td>ULN</td>' +
				'<td>EAD</td><td>LAD</td><td>PAX</td>' +
				'<td>STONS</td><td>POE</td><td>POD</td>' +
				'<td>MS</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				ead = this.formatCday(bodies[i].ead);
				lad = this.formatCday(bodies[i].lad);
				pax = bodies[i].pax;
				stons = bodies[i].totalWeight;
				poe = bodies[i].poe;
				pod = bodies[i].pod;
				ms = bodies[i].mode + bodies[i].source;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' +
					ulnId + '</td><td>' +
					ead + '</td><td>' +
					lad + '</td><td>' +
					pax + '</td><td>' +
					stons + '</td><td>' +
					poe + '</td><td>' +
					pod + '</td><td>' +
					ms + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'ACTION_COMPLETE_OF_ULNS_IN_TPFDD') {
			
			text += '<table><tr><td>PID</td><td>ULN</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' +
					ulnId + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'BUILD_REQUEST_OF_ULNS_IN_TPFDD') {
			
			text += 
				'<table><tr><td>PID</td><td>ULN</td>' +
				'<td>EAD</td><td>LAD</td><td>PAX</td>' +
				'<td>STONS</td><td>POE NAME</td>' +
				'<td>POD NAME</td><td>MS</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				ead = this.formatCday(bodies[i].ead);
				lad = this.formatCday(bodies[i].lad);
				pax = bodies[i].pax;
				stons = bodies[i].totalWeight;
				poeName = bodies[i].poeName;
				podName = bodies[i].podName;
				ms = bodies[i].mode + bodies[i].source;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' + 
					ulnId + '</td><td>' + 
					ead + '</td><td>' +
					lad + '</td><td>' + 
					pax + '</td><td>' + 
					stons + '</td><td>' +
					poeName + '</td><td>' +
					podName + '</td><td>' +
					ms + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'CARRIER_AVAILABLE_FOR_MANIFESTING_IN_TPFDD') {
			
			text += '<table><tr><td>ULN</td><td>POE NAME</td>' +
			'<td>MS</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				ulnId = bodies[i].ulnId;
				poeName = bodies[i].poeName;
				ms = bodies[i].mode + bodies[i].source;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					ulnId + '</td><td>' + 
					poeName + '</td><td>' +
					ms + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'CARRIER_MANIFEST_FOR_TPFDD') {
			
			text += 
				'<table><tr><td>PID</td><td>ULN</td>' +
				'<td>PAX</td><td>STONS</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				pax = bodies[i].pax;
				stons = bodies[i].totalWeight;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' + 
					ulnId + '</td><td>' + 
					pax + '</td><td>' + 
					stons + '</td><td>' + 
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'CERTIFICATION_OF_ULNS_IN_TPFDD') {
			
			text += 
				'<table><tr><td>PID</td><td>ULN</td>' +
				'<td>PAX</td><td>STONS</td><td>UNIT NAME</td>' +
				'<td>ORIGIN</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				pax = bodies[i].pax;
				stons = bodies[i].stons;
				unitName = bodies[i].unitName;
				origin = bodies[i].origin;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' + 
					ulnId + '</td><td>' + 
					pax + '</td><td>' + 
					stons + '</td><td>' +
					unitName + '</td><td>' +
					origin + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'CHANGE_REQUEST_OF_ULNS_IN_TPFDD') {
			
			text += '<table><tr><td>PID</td><td>ULN</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' +
					ulnId + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'DEPLOYMENT_VERIFICATION_OF_ULNS_IN_TPFDD') {
			
			text += 
				'<table><tr><td>PID</td><td>ULN</td>' +
				'<td>EAD</td><td>LAD</td><td>PAX</td>' +
				'<td>STONS</td><td>POE NAME</td>' +
				'<td>POD NAME</td><td>MS</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				ead = this.formatCday(bodies[i].ead);
				lad = this.formatCday(bodies[i].lad);
				pax = bodies[i].pax;
				stons = bodies[i].totalWeight;
				poeName = bodies[i].poeName;
				podName = bodies[i].podName;
				ms = bodies[i].mode + bodies[i].source;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' + 
					ulnId + '</td><td>' + 
					ead + '</td><td>' +
					lad + '</td><td>' + 
					pax + '</td><td>' + 
					stons + '</td><td>' +
					poeName + '</td><td>' +
					podName + '</td><td>' +
					ms + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'FORCE_CLOSURE_OF_ULNS_IN_TPFDD') {
			
			text += 
				'<table><tr><td>PID</td><td>ULN</td>' +
				'<td>PAX</td><td>STONS</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				pax = bodies[i].pax;
				stons = bodies[i].totalWeight;
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' + 
					ulnId + '</td><td>' + 
					pax + '</td><td>' + 
					stons + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		} else if (msgType === 'FRN_AVAILABLE_FOR_SOURCING') {
			
			text += 
				'<table><tr><td>PID</td><td>ULN</td>' +
				'<td>EAD</td><td>LAD</td><td>POD</td>' +
				'<td>DEST</td><td>STONS</td>' +
				'<td>PERS</td><td>FORCE DESC</td>' +
				'<td>RDD</td><td>NOTE</td></tr>';
			
			for (i=0; i<bodies.length; i++) {
				pid = bodies[i].planId;
				ulnId = bodies[i].ulnId;
				ead = this.formatCday(bodies[i].ead);
				lad = this.formatCday(bodies[i].lad);
				pod = bodies[i].pod;
				dest = bodies[i].dest;
				stons = bodies[i].totalWeight;
				pers = bodies[i].pers;
				forceDesc = bodies[i].forceDescription;
				rdd = this.formatCday(bodies[i].rdd);
				note = (bodies[i].notes === undefined ? '' : bodies[i].notes);
				
				text += '<tr><td>' + 
					pid + '</td><td>' + 
					ulnId + '</td><td>' + 
					ead + '</td><td>' +
					lad + '</td><td>' +
					pod + '</td><td>' +
					dest + '</td><td>' +
					stons + '</td><td>' + 
					pers + '</td><td>' +
					forceDesc + '</td><td>' +
					rdd + '</td><td>' +
					note + '</td></tr>';
			}
			
			text += '</table>';
			
		}
		
		text += '<br>';
		
		return text;
	}
	
});
