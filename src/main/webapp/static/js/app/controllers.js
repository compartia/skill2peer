'use strict';

define(
//
[ 'angular', 'services', 'autocomplete', 'ngTagsInput' ],
//
function(angular) {

	/* Controllers */

	return angular.module('skill2peerApp.controllers', [ 'skill2peerApp.services', 'ngAutocomplete', 'ngTagsInput' ])
	/* */

	.controller('placeEditController', [ '$scope', '$injector', function($scope, $injector) {
		require([ 'controllers/placeEditController' ], function(placeEditController) {

			$injector.invoke(placeEditController, this, {
				'$scope' : $scope
			});
		});

	} ])

	.controller('lessonEditController', [ '$scope', '$injector', function($scope, $injector) {
		require([ 'controllers/lessonEditController' ], function(lessonEditController) {

			$injector.invoke(lessonEditController, this, {
				'$scope' : $scope
			});
		});

	} ])

	.controller('favoriteCourseController', [ '$scope', '$injector', function($scope, $injector) {
		require([ 'controllers/favoriteCourseController' ], function(favoriteCourseController) {

			$injector.invoke(favoriteCourseController, this, {
				'$scope' : $scope
			});
		});

	} ])

	.controller('courseListsController', [ '$scope', '$injector', function($scope, $injector) {
		require([ 'controllers/courseListsController' ], function(courseListsController) {

			$injector.invoke(courseListsController, this, {
				'$scope' : $scope
			});
		});

	} ])

	.controller('courseEditController',
			[ '$scope', '$injector', 'FileUploader', function($scope, $injector, FileUploader) {

				// $scope.uploader = new FileUploader({
				// url : '/upload'
				// });

				var uploader = $scope.uploader = new FileUploader({
					url : '/upload',
					autoUpload : true
				});

				// FILTERS

				uploader.filters.push({
					name : 'customFilter',
					fn : function(item /* {File|FileLikeObject} */, options) {
						return this.queue.length < 10;
					}
				});

				// CALLBACKS

				uploader.onWhenAddingFileFailed = function(item /* {File|FileLikeObject} */, filter, options) {
					console.info('onWhenAddingFileFailed', item, filter, options);
				};
				uploader.onAfterAddingFile = function(fileItem) {
					console.info('onAfterAddingFile', fileItem);
				};
				uploader.onAfterAddingAll = function(addedFileItems) {
					console.info('onAfterAddingAll', addedFileItems);
				};
				uploader.onBeforeUploadItem = function(item) {
					console.info('onBeforeUploadItem', item);
				};
				uploader.onProgressItem = function(fileItem, progress) {
					console.info('onProgressItem', fileItem, progress);
				};
				uploader.onProgressAll = function(progress) {
					console.info('onProgressAll', progress);
				};
				uploader.onSuccessItem = function(fileItem, response, status, headers) {
					console.info('onSuccessItem', fileItem, response, status, headers);
				};
				uploader.onErrorItem = function(fileItem, response, status, headers) {
					console.info('onErrorItem', fileItem, response, status, headers);
				};
				uploader.onCancelItem = function(fileItem, response, status, headers) {
					console.info('onCancelItem', fileItem, response, status, headers);
				};
				uploader.onCompleteItem = function(fileItem, response, status, headers) {
					console.info('onCompleteItem', fileItem, response, status, headers);
				};
				uploader.onCompleteAll = function() {
					console.info('onCompleteAll');
				};

				console.info('uploader', uploader);

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