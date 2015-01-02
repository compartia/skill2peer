define(
//
[],
//
function onReady() {

	return [ '$scope', 'Courses', '$routeParams', '$location', function($scope, Courses, $routeParams, $location) {

		this.init = function() {
			$scope.course = new Courses({});
			$scope.course.lessons = [];
			$scope.addLesson();
			$scope.selectLesson($scope.course.lessons[0]);

			if ($routeParams.courseId) {
				Courses.edit({
					id : $routeParams.courseId
				}, function(course) {
					$scope.course = course;
					$scope.selectLesson($scope.course.lessons[0]);
				});

			}
		}
        
        $scope.isSingle = function() {
			return $scope.course.lessons.length < 2;
		};

		$scope.selectLesson = function(_lesson) {
			$scope.lesson = _lesson;
		};

		$scope.deleteLesson = function(_lesson) {
			if ($scope.course.lessons.length == 1) {
				return;
			}

			var index = $scope.course.lessons.indexOf(_lesson);
			$scope.course.lessons.splice(index, 1);

			if (_lesson == $scope.lesson) {
				$scope.selectLesson($scope.course.lessons[0]);
			}
		};

		$scope.addLesson = function() {
			var newLesson = {
				"id" : null,
                "name": null,
				"schedule" : {},
				"location" : {}
			};

			$scope.course.lessons.push(newLesson);
			$scope.selectLesson(newLesson);

		};

		$scope.save = function() {
			var savedCourse = Courses.save($scope.course);
			savedCourse.$promise.then(function() {
				$scope.course = savedCourse;
				$scope.selectLesson($scope.course.lessons[0]);
				$location.path('/home/2');
			});
		};

		init();
		$scope.$apply();
	} ];
});
