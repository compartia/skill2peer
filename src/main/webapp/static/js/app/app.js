'use strict';

define(
//
[ 'angular', 'services', 'controllers', 'angularroute', 'angularresource', 'flatui', 'angular-file-upload' ],
//
function(angular, filters, services, directives, controllers) {

	// Declare app level module which depends on filters, and services

	return angular.module('skill2peerApp',
			[ 'skill2peerApp.services', 'skill2peerApp.controllers', 'angularFileUpload' ]);
});