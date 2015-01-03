define([],
//
//

function() {
	return [ '$scope', 'Courses', function($scope, Courses) {
		$scope.courseId = courseId;

		$scope.switchButtonStyle = function() {
			$scope.course.favorited = !$scope.course.favorited;
			if ($scope.course.favorited) {
				$scope.btnStyle = "inactive";
			} else {
				$scope.btnStyle = "";
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
