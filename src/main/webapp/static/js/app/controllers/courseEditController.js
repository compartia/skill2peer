define(
//
[ 'autocomplete', 'moment' ],
//
function onReady() {

	return [ '$scope', 'Courses', function($scope, Courses) {

		$scope.placeSearchOptions = {
		};

		/**
		 * the first version starts from SPB, Russia
		 */
		var spb = new google.maps.LatLng(60, 30);
		var radius = 40000;
        
		$scope.placeSearchOptions.location = spb;
		$scope.placeSearchOptions.radius = radius;
		$scope.course = new Courses({});
		$scope.course.lessons = [ {
			"id" : null, "schedule":{}
		} ];
        
        $scope.selectedLesson = $scope.course.lessons[0];
        
        $scope.selectLesson = function (lesson) {
            $scope.selectedLesson = lesson;
        };

		$scope.save = function() {
			var savedCourse = Courses.save($scope.course);
			savedCourse.$promise.then(function() {
				$scope.course = savedCourse;
                $scope.selectedLesson = $scope.course.lessons[0];
			});
		};

		$scope.$apply();
	} ];
});
