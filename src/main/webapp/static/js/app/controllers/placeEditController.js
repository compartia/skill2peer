define(
//
[ 'autocomplete' ],
//
function onReady() {

	return [ '$scope', function($scope) {

		/**
		 * the first version starts from SPB, Russia
		 */
		var spb;
        try{
            spb = new google.maps.LatLng(60, 30);
            var radius = 40000;
        } catch (e){
            console.error("Google map services are not available");  
        }
        
		$scope.placeSearchOptions = {};
		$scope.locationdetails = {};

		$scope.placeSearchOptions.location = spb;
		$scope.placeSearchOptions.radius = radius;
		
        
       
        
        $scope.$watch('locationdetails', function() {
            $scope.lesson = $scope.$parent.selectedLesson;
            
            if( $scope.lesson ){
                $scope.lesson.location = {};
                $scope.lesson.location.address = $scope.locationdetails.formatted_address;
                console.log("$scope.lesson.location.address=" + $scope.lesson.location.address);
                console.log("$scope.locationdetails.formatted_address=" + $scope.locationdetails.formatted_address);
            }
        });
        
		 $scope.$apply();
        
        

	} ];
});
