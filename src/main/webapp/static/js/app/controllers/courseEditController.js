define(
//
[],
//
function onReady() {

	return [ '$scope', 'Courses', '$routeParams', '$location', function($scope, Courses, $routeParams, $location) {

		this.init = function() {
			if ($routeParams.courseId) {
				Courses.edit({
					id : $routeParams.courseId
				}, function(course) {
					$scope.course = course;
					$scope.selectedLesson = $scope.course.lessons[0];
				});

			} else {
				$scope.course = new Courses({});
				$scope.course.lessons = [ {
					"id" : null,
					"schedule" : {},
					"location" : {}
				} ];

				$scope.selectedLesson = $scope.course.lessons[0];
			}

		}

		$scope.selectLesson = function(lesson) {
			$scope.selectedLesson = lesson;
		};

		$scope.save = function() {
			var savedCourse = Courses.save($scope.course);
			savedCourse.$promise.then(function() {
				$scope.course = savedCourse;
				$scope.selectedLesson = $scope.course.lessons[0];
                $location.path('/home/2');
			});
		};

		init();
		$scope.$apply();
	} ];
});
