define(
//
[],
//
function onReady() {

	return  [ '$scope', '$location','Courses', function($scope,$location, Courses) {

//        $http.get('/courses/myCourses').
//        success(function(data, status, headers, config) {
//            $scope.myCourses=data;
//        }).
//        //
//        error(function(data, status, headers, config) {
//        // called asynchronously if an error occurs
//        // or server returns response with an error status.
//        });
        
        $scope.myCourses=Courses.myCourses({});
        
       
		$scope.$apply();
	} ];
});
