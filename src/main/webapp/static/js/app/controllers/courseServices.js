'use strict';

define(
//
[ 'angular' ],
//
function(angular) {

	var skill2peerServices = angular.module('skill2peerApp.services', [ 'ngResource' ]);

	skill2peerServices.factory('Courses', [ '$resource', function($resource) {
		return $resource('/rest/courses/:id/', {}, {
			favorite : {
				method : 'POST',
				url : "/rest/courses/:id/favorite"
			},
            edit : {
				method : 'GET',
				url : "/rest/courses/:id/edit"
			},
            myCourses:{
                method : 'GET',
				url : "/rest/courses/myCourses",
                isArray:true
            },
            availableLocations:{
                method : 'GET',
				url : "/rest/courses/availableLocations",
                isArray:true
            }
		});
	} ]);

});
