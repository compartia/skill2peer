'use strict';

define(
//
[ 'angular', 'services', 'controllers', 'angularroute', 'angularresource','angular-file-upload',
		'bootstrap', 'jquery', 'bootstrap-ui' ],
//
function(angular, filters, services, directives, controllers) {

    var HEADER_NAME = 'MyApp-Handle-Errors-Generically';
    var specificallyHandleInProgress = false;
    
	// Declare app level module which depends on filters, and services
	var app = angular.module('skill2peerApp', [ 'ngRoute', 'skill2peerApp.services', 'skill2peerApp.controllers',
			'angularFileUpload', 'ui.bootstrap' ]);

	app.config([ "$httpProvider", '$routeProvider', function($httpProvider, $routeProvider) {
		$httpProvider.defaults.transformResponse.push(function(responseData) {
			convertDateStringsToDates(responseData);
			return responseData;
		});
        
        
        $routeProvider.
            when('/home/:tabId', {
                templateUrl: 'static/partials/dashboard.jsp',
                controller: 'courseListsController'
            }).
            when('/edit/:courseId', {
                templateUrl: 'static/partials/edit-course.jsp',
                controller: 'courseEditController'
            }).
            otherwise({
                redirectTo: '/home/1'
            });
	} ]);
    
    
    app.factory('RequestsErrorHandler', ['$q', function($q) {
        return {
            // --- The user's API for claiming responsiblity for requests ---
            specificallyHandled: function(specificallyHandledBlock) {
                specificallyHandleInProgress = true;
                try {
                    return specificallyHandledBlock();
                } finally {
                    specificallyHandleInProgress = false;
                }
            },

            // --- Response interceptor for handling errors generically ---
            responseError: function(rejection) {
                var shouldHandle = (rejection && rejection.config && rejection.config.headers
                    && rejection.config.headers[HEADER_NAME]);

                if (shouldHandle) {
                    // --- generic error handling goes here ---
                    alert ("error "+rejection.data.message);
                }

                return $q.reject(rejection);
            }
        };
    }]);
    
    
    app.config(['$provide', '$httpProvider', function($provide, $httpProvider) {
        $httpProvider.interceptors.push('RequestsErrorHandler');

        // --- Decorate $http to add a special header by default ---

        function addHeaderToConfig(config) {
            config = config || {};
            config.headers = config.headers || {};

            // Add the header unless user asked to handle errors himself
            if (!specificallyHandleInProgress) {
                config.headers[HEADER_NAME] = true;
            }

            return config;
        }

        // The rest here is mostly boilerplate needed to decorate $http safely
        $provide.decorator('$http', ['$delegate', function($delegate) {
            function decorateRegularCall(method) {
                return function(url, config) {
                    return $delegate[method](url, addHeaderToConfig(config));
                };
            }

            function decorateDataCall(method) {
                return function(url, data, config) {
                    return $delegate[method](url, data, addHeaderToConfig(config));
                };
            }

            function copyNotOverriddenAttributes(newHttp) {
                for (var attr in $delegate) {
                    if (!newHttp.hasOwnProperty(attr)) {
                        if (typeof($delegate[attr]) === 'function') {
                            newHttp[attr] = function() {
                                return $delegate.apply($delegate, arguments);
                            };
                        } else {
                            newHttp[attr] = $delegate[attr];
                        }
                    }
                }
            }

            var newHttp = function(config) {
                return $delegate(addHeaderToConfig(config));
            };

            newHttp.get = decorateRegularCall('get');
            newHttp.delete = decorateRegularCall('delete');
            newHttp.head = decorateRegularCall('head');
            newHttp.jsonp = decorateRegularCall('jsonp');
            newHttp.post = decorateDataCall('post');
            newHttp.put = decorateDataCall('put');

            copyNotOverriddenAttributes(newHttp);

            return newHttp;
        }]);
    }]);

	return app;

});

var regexIso8601 = /^(\d{4}|\+\d{6})(?:-(\d{2})(?:-(\d{2})(?:T(\d{2}):(\d{2}):(\d{2})\.(\d{1,})(Z|([\-+])(\d{2}):(\d{2}))?)?)?)?$/;

function convertDateStringsToDates(input) {
	// Ignore things that aren't objects.
	if (typeof input !== "object") {
		return input;
	}

	for ( var key in input) {
		if (!input.hasOwnProperty(key)) {
			continue;
		}

		var value = input[key];
		var match;
		// Check for string properties which look like dates.
		if (typeof value === "string" && (match = value.match(regexIso8601))) {
			var milliseconds = Date.parse(match[0])
			if (!isNaN(milliseconds)) {
				input[key] = new Date(milliseconds);
			}
		} else if (typeof value === "object") {
			// Recurse into object
			convertDateStringsToDates(value);
		}
	}
}