var JFRG = JFRG || {};
JFRG.utils = {};

// Some versions of IE do not support the javascript String.trim() method so provide it for them.
if(typeof String.prototype.trim !== 'function') {
    String.prototype.trim = function() {
        return this.replace(/^\s+|\s+$/g, ''); 
    };
}

/*#############################################################################
Grid Manipulation (forcing overflow of text in certain coluns)
#############################################################################*/
JFRG.utils.columnWrap = function(val) {
	return '<div style="white-space:normal !important;">'+ val +'</div>';
};

/*#############################################################################
COOKIES
#############################################################################*/
JFRG.utils.cookie = {};
JFRG.utils.cookie.create = function(name, value, hours) {
    var date, expires = '';
    if (hours) {
        date = new Date();
        date.setTime(date.getTime()+(hours*60*60*1000));
        expires = '; expires=' + date.toGMTString();
    }

    document.cookie = name + '=' + value + expires + '; path=/';
};

JFRG.utils.cookie.read = function(name) {
    var nameEQ = name + '=', 
        value = null,
        ca = document.cookie.split(';'),
        i, c;
    
    for(i=0;i < ca.length;i++) {
        c = ca[i];
        while (c.charAt(0)==' ') {
            c = c.substring(1,c.length);
        }
        
        if (c.indexOf(nameEQ) === 0) { 
            value = c.substring(nameEQ.length,c.length);
        }
    }
    return value;
};

JFRG.utils.cookie.remove = function(name) {
    document.cookie = name + '=""; expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/';
};

JFRG.utils.cookie.removeAll = function() {
    var cookies = document.cookie.split(";"), 
        i;
    for (i = 0; i < cookies.length; i++) {
      JFRG.utls.cookie.remove(cookies[i].split("=")[0]);
    }
};

//returns true if arg is not undefined and not null
JFRG.utils.isDefined = function(arg) {
    return ((typeof arg !== typeof undefined) && (arg !== null));
};

//returns true if arg is undefined, null, or ''
JFRG.utils.isBlank = function(arg) {
    return (!JFRG.utils.isDefined(arg) || (arg.trim() === ''));
};

//###############################################################
// SANITIZE DATA FUNCTIONS
//###############################################################
// These functions are intended to process and format any data received from AJAX calls
// for example they should convert longs to dates, or parse results out of SOAP envelopes

JFRG.utils.sanitizeDate = function(data) {
    var date = '';
    if(typeof data !== typeof undefined && data !== null && data !== '') {
        date = new Date(parseInt(data));
    }
    return date;
};

JFRG.utils.sanitizeGradeRankData = function(data) {
    if(typeof data.length === typeof undefined) {   //single phone record
        data.gradeCreateDt = JFRG.utils.sanitizeDate(data.gradeCreateDt);
        data.gradeUpdDt = JFRG.utils.sanitizeDate(data.gradeUpdDt);
    }  else {
        for(var i = 0; i < data.length; i++) {  //array of phone records
            data[i].gradeCreateDt = JFRG.utils.sanitizeDate(data[i].gradeCreateDt);
            data[i].gradeUpdDt = JFRG.utils.sanitizeDate(data[i].gradeUpdDt);
        }
    }
    
    return data;
};

JFRG.utils.sanitizePhoneTypeData = function(data) {
    if(typeof data.length === typeof undefined) {   //single phone record
        data.phoneTpCreateDt = JFRG.utils.sanitizeDate(data.phoneTpCreateDt);
        data.phoneTpUpdDt = JFRG.utils.sanitizeDate(data.phoneTpUpdDt);
    }  else {
        for(var i = 0; i < data.length; i++) {  //array of phone records
            data[i].phoneTpCreateDt = JFRG.utils.sanitizeDate(data[i].phoneTpCreateDt);
            data[i].phoneTpUpdDt = JFRG.utils.sanitizeDate(data[i].phoneTpUpdDt);
        }
    }
    
    return data;
};

JFRG.utils.sanitizeRoleData = function(data) {
    if(typeof data.length === typeof undefined) {   //single phone record
        data.roleCreateDt = JFRG.utils.sanitizeDate(data.roleCreateDt);
        data.roleUpdDt = JFRG.utils.sanitizeDate(data.roleUpdDt);
    }  else {
        for(var i = 0; i < data.length; i++) {  //array of phone records
            data[i].roleCreateDt = JFRG.utils.sanitizeDate(data[i].roleCreateDt);
            data[i].roleUpdDt = JFRG.utils.sanitizeDate(data[i].roleUpdDt);
        }
    }
    
    return data;
};

JFRG.utils.sanitizeUnitData = function(data) {
    if(typeof data.length === typeof undefined) {   //single phone record
        data.unitCreateDt = JFRG.utils.sanitizeDate(data.unitCreateDt);
        data.unitUpdDt = JFRG.utils.sanitizeDate(data.unitUpdDt);
    }  else {
        for(var i = 0; i < data.length; i++) {  //array of phone records
            data[i].unitCreateDt = JFRG.utils.sanitizeDate(data[i].unitCreateDt);
            data[i].unitUpdDt = JFRG.utils.sanitizeDate(data[i].unitUpdDt);
        }
    }
    
    return data;
};

JFRG.utils.sanitizeUserData = function(data) {
    if(typeof data.length === typeof undefined) {   //single user record
        data.usrCreateDt = JFRG.utils.sanitizeDate(data.usrCreateDt);
        data.usrProjRotDt = JFRG.utils.sanitizeDate(data.usrProjRotDt);
        data.usrPwdExpireDt = JFRG.utils.sanitizeDate(data.usrPwdExpireDt);
        data.usrUpdDt = JFRG.utils.sanitizeDate(data.usrUpdDt);
    } else {
        for(var i = 0; i < data.length; i++) {  //array of user records
            data[i].usrCreateDt = JFRG.utils.sanitizeDate(data[i].usrCreateDt);
            data[i].usrProjRotDt = JFRG.utils.sanitizeDate(data[i].usrProjRotDt);
            data[i].usrPwdExpireDt = JFRG.utils.sanitizeDate(data[i].usrPwdExpireDt);
            data[i].usrUpdDt = JFRG.utils.sanitizeDate(data[i].usrUpdDt);
        }
    }
    return data;
};

JFRG.utils.sanitizeWorkflowNodeData = function(data) {
    if(typeof data.length === typeof undefined) {   //single user record
        data.wfnCreateDt = JFRG.utils.sanitizeDate(data.wfnCreateDt);
        data.wfnUpdDt = JFRG.utils.sanitizeDate(data.wfnUpdDt);
    } else {
        for(var i = 0; i < data.length; i++) {  //array of user records
            data[i].wfnCreateDt = JFRG.utils.sanitizeDate(data[i].wfnCreateDt);
            data[i].wfnUpdDt = JFRG.utils.sanitizeDate(data[i].wfnUpdDt);
        }
    }
    return data;
};


JFRG.utils.roundTo = function(num, precision) {    
    return (+(Math.round(num + ("e+" + precision))  + ("e-" + precision))).toFixed(precision);
};

JFRG.utils.showExtJsComponent = function(poExtJsComp, pbShow) {    
	if (poExtJsComp) {
		if (pbShow)
			poExtJsComp.show();
		else
			poExtJsComp.hide();
	}
	return poExtJsComp;
};

JFRG.utils.enableExtJsComponent = function(poExtJsComp, pbEnable) {    
	if (poExtJsComp) {
		poExtJsComp.setDisabled(!pbEnable);
	}
	return poExtJsComp;
};

JFRG.utils.formatZuluDate = function(dt) {    
	if (dt) {
		return Ext.Date.format(dt,'dHi\\Z\\ M\\ y');
	} else
		return '';
};

JFRG.utils.getParameterByName = function(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
};

JFRG.utils.strTimeToDate = function(value) {
	if (value) {
		try {
			return new Date(parseInt(value));
		} catch (err) {
			console.log(err);
		}
	}
	return null;
};

JFRG.utils.strToDate = function(value) {
	if (value) {
		try {
			return new Date(value);
		} catch (err) {
			console.log(err);
		}
	}
	return null;
};

JFRG.utils.getImagePath = function() {
	return '../../images/';
};

/**/