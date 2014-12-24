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
				// alert($scope.course.id);
				// alert($scope.uploader.isHTML5);
			});
		};

		$scope.getWeekStart = function(lesson) {
//			if ($scope.course.schedule && $scope.course.schedule.start) {
//                alert(scope.course.schedule.start);
				$scope.weeks[lesson].weekStart = moment(lesson.schedule.dateTime.startDateStr).startOf('isoWeek').add(1, 'd');
              
//			} else {
//				$scope.weekStart = moment().clone().startOf('week');
//			}
		};

		$scope.$apply();
	} ];
});
