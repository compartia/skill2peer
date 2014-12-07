'use strict';

define(
//
[ 'angular', 'services', 'autocomplete' ],
//
function(angular) {

	/* Controllers */

	return angular.module('skill2peerApp.controllers', [ 'skill2peerApp.services', 'ngAutocomplete' ])
	/* */
	.controller('favoriteCourseController', [ '$scope', '$injector', function($scope, $injector) {
		require([ 'controllers/favoriteCourseController' ], function(favoriteCourseController) {

			$injector.invoke(favoriteCourseController, this, {
				'$scope' : $scope
			});
		});
	} ])

	.controller('courseEditController',
			[ '$scope', '$injector', 'FileUploader', function($scope, $injector, FileUploader) {

				$scope.uploader = new FileUploader({
					url : '/upload'
				});

				// $scope.courseImageUploader.onBeforeUploadItem =
				// function(fileItem) {
				// alert('onBeforeUploadItem');
				// fileItem.formData = {
				// 'name' : "someName"
				// };
				// };

				require(
				//
				[ 'controllers/courseEditController' ],
				//
				function(courseEditController) {

					$injector.invoke(courseEditController, this, {
						'$scope' : $scope
					});
				});
			} ]);
});