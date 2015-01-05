define(
//
['moment'],
//
function onReady() {
    
	return  [ '$scope', '$location', '$routeParams', 'Courses', function($scope, $location, $routeParams, Courses) {   
        
        $scope.selectedTab = 1;
     
        if ($routeParams.tabId) {
            $scope.selectedTab = $routeParams.tabId;
        };
       

        $scope.myCourses=Courses.myCourses({});
        
              
        $scope.setSelectedTab = function(tab) {
            $scope.selectedTab = tab;
        }
 
        $scope.tabClass = function(tab) {
            if ($scope.selectedTab == tab) {
              return "active";
            } else {
              return "";
            }
        }
      
		$scope.$apply();
        
	} ];
});


