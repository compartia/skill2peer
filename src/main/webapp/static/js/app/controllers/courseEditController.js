define(
//
[  ],
//
function onReady() {

	return [ '$scope', 'Courses', function($scope, Courses) {

        this.init=function(){
            $scope.course = new Courses({});
            $scope.course.lessons = [ {
                "id" : null, "schedule":{}
            } ];

            $scope.selectedLesson = $scope.course.lessons[0];
        }
        
        
        
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

        init();
		$scope.$apply();
	} ];
});
