define(
//
['moment'],
//
function onReady() {

	return  [ '$scope', '$location','Courses', function($scope, $location, Courses) {       
        $scope.myCourses=Courses.myCourses({});
        
        $scope.sampleDate=moment( "2018-11-30T23:00:00.000+04:00").format("d.MM HH:mm");
		$scope.$apply();
	} ];
});
