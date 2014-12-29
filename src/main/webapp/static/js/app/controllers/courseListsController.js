define(
//
[],
//
function onReady() {

	return  [ '$scope', '$location','Courses', function($scope,$location, Courses) {       
        $scope.myCourses=Courses.myCourses({});
		$scope.$apply();
	} ];
});
