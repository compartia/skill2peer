'use strict';

define(
//
[ 'angular', 'services', 'controllers', 'angularroute', 'angularresource', /* 'flatui', */'angular-file-upload', 'bootstrap' ],
//
function(angular, filters, services, directives, controllers) {

	// Declare app level module which depends on filters, and services
	var app = angular.module('skill2peerApp', [ 'skill2peerApp.services', 'skill2peerApp.controllers',
			'angularFileUpload', 'ui.bootstrap' ]);

	app.config([ "$httpProvider", function($httpProvider) {
		$httpProvider.defaults.transformResponse.push(function(responseData) {
			convertDateStringsToDates(responseData);
			return responseData;
		});
	} ]);

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