'use strict';

define(
//
[ 'angular', 'services' ],
//
function(angular) {

	/* Controllers */

	return angular.module('skill2peerApp.controllers.view', [ 'skill2peerApp.services' ])
	/* */
	.controller('favoriteCourseController', [ '$scope', '$injector', function($scope, $injector) {
		require([ 'controllers/favoriteCourseController' ], function(favoriteCourseController) {

			$injector.invoke(favoriteCourseController, this, {
				'$scope' : $scope
			});
		});
	} ])

	;
});