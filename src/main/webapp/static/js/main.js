require.config({
	baseUrl : '/static/js',
	paths : {
        app : '/static/js/app/app',
       'dasha-script':'/static/js/script',
		async : 'bower_components/requirejs-plugins/src/async',
		goog : 'bower_components/requirejs-plugins/src/goog',

		jquery : '/static/js/bower_components/jquery/dist/jquery.min',
		angular : '/static/js/bower_components/angular/angular.min',
		// flatui : '/static/js/vendor/flat-ui.min',
		
		angularresource : '/static/js/bower_components/angular-resource/angular-resource.min',
		angularroute : '/static/js/bower_components/angular-route/angular-route.min',
        angularsanitize  : '/static/js/bower_components/angular-sanitize/angular-sanitize.min',
		controllers : '/static/js/app/controllers',
		services : '/static/js/app/controllers/courseServices',
		'autocomplete' : '/static/js/vendor/ngAutocomplete',
		// dropzone :
		// '/static/js/bower_components/dropzone/downloads/dropzone-amd-module.min',
		// 'dropzone-directive' : '/static/js/vendor/dropzone-directive',
		'angular-file-upload' : '/static/js/bower_components/angular-file-upload/angular-file-upload',
		'moment' : '/static/js/bower_components/moment/min/moment.min',
		'bootstrap-ui' : '/static/js/vendor/ui-bootstrap-custom-build/ui-bootstrap-custom-0.12.0.min',
        'bootstrap' : 'bower_components/bootstrap/dist/js/bootstrap',
        'angularAMD': '/static/js/bower_components/ angularAMD/angularAMD.min'

	},
	shim : {
        'angularAMD': ['angular'],
		'app' : {
			deps : [ 'angular' ]
		},
		'angular' : {
			exports : 'angular'
		},
		'angularroute' : {
			exports : 'ngRoute',
			deps : [ 'angular' ]
		},
        'angularsanitize' : {
			exports : 'ngSanitize',
			deps : [ 'angular' ]
		},
		'angularresource' : {
			deps : [ 'angular' ]
		},
		'courseServices' : {
			deps : [ 'angularroute', 'angularresource' ]
		},

		'angular-file-upload' : {
			deps : [ 'angular' ]
		},
        'bootstrap' : { 
            deps :['jquery'] 
        },
        'dasha-script' : { 
            deps :['jquery'] 
        },
		priority : [ "angular" ]

	}
//    ,
//    
//     // kick start jquery
//    deps: ['jquery', 'bootstrap']

});

// require([ 'app', ], function(App) {
// App.initialize();
// });

//window.name = "NG_DEFER_BOOTSTRAP!";
//
//require([ 'angular' ], function(common) {
//
//});

// require([ 'angular', 'app', 'angularroute' ], function(angular, app,
// angularroute) {
// var $html = angular.element(document.getElementsByTagName('html')[0]);
//
// angular.element().ready(function() {
// angular.resumeBootstrap([ app['name'] ]);
// });
// });
