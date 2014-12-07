define([], function() {
	return [ '$scope', 'Courses', function($scope, Courses) {
		$scope.courseId = courseId;

		$scope.switchButtonStyle = function() {
			$scope.course.favorited = !$scope.course.favorited;
			if ($scope.course.favorited) {
				$scope.btnStyle = "btn-danger";
			} else {
				$scope.btnStyle = "btn-default";
			}
		};

		$scope.course = Courses.get({
			id : $scope.courseId
		}, function(course) {
			$scope.switchButtonStyle();
		});

		$scope.favoriteCourse = function() {
			$scope.switchButtonStyle();
			Courses.favorite({
				id : $scope.courseId
			}, $scope.course);
		};

		$scope.$apply();
	} ];
});
//
// skill2peerApp.controller('favoriteCourseController', [ '$scope',
// '$routeParams', 'Courses',
// function($scope, $routeParams, Courses) {
//
// } ]);
