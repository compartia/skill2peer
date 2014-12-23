require.config({
	paths : {
		jquery : '/static/js/bower_components/jquery/dist/jquery.min',
		angular : '/static/js/bower_components/angular/angular.min',
		flatui : '/static/js/vendor/flat-ui.min',
		app : '/static/js/app/app',
		angularresource : '/static/js/bower_components/angular-resource/angular-resource.min',
		angularroute : '/static/js/bower_components/angular-route/angular-route.min',
		controllers : '/static/js/app/controllers',
		services : '/static/js/app/controllers/courseServices',
		autocomplete : '/static/js/vendor/ngAutocomplete',
		//dropzone : '/static/js/bower_components/dropzone/downloads/dropzone-amd-module.min',
		// 'dropzone-directive' : '/static/js/vendor/dropzone-directive',
		'angular-file-upload' : '/static/js/bower_components/angular-file-upload/angular-file-upload',
        'moment' : '/static/js/bower_components/moment/min/moment.min'
        
	},
	shim : {
		'angular' : {
			exports : 'angular'
		},
		'angularroute' : {
			exports : 'ngRoute',
			deps : [ 'angular' ]
		},
		'angularresource' : {
			deps : [ 'angular' ]
		},
		'courseServices' : {
			deps : [ 'angularroute', 'angularresource' ]
		},

		'flatui' : {
			deps : [ 'jquery' ]
		},

		'angular-file-upload' : [ "angular" ],

		priority : [ "angular" ]

	}

});

// require([ 'app', ], function(App) {
// App.initialize();
// });

window.name = "NG_DEFER_BOOTSTRAP!";

require([ 'angular', 'app', 'angularroute' ], function(angular, app, angularroute) {
	var $html = angular.element(document.getElementsByTagName('html')[0]);

	angular.element().ready(function() {
		angular.resumeBootstrap([ app['name'] ]);
	});
});