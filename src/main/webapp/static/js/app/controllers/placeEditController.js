define(
//
[ 'autocomplete'],
//
function onReady() {

	return [ '$scope','Courses', function($scope,Courses) {
        
        

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

        $scope.onPlaceChanged = function() {
           $scope.lesson.location = $scope.getLocationById($scope.lesson.location.id);
        };
        
        
       
        $scope.$watchGroup(['locationdetails','location'], function(newlocationdetails) {
            
            if( $scope.lesson ){
                
                $scope.lesson.locationId=-1;
                if( newlocationdetails[0]){
                    $scope.lesson.location = {
                        "id":-1,
                        "address":newlocationdetails[1],
                        "url":newlocationdetails[0].url,
                        "vicinity":newlocationdetails[0].vicinity,
                        "icon":newlocationdetails[0].icon,
                        "html":newlocationdetails[0].adr_address,
                        "name":newlocationdetails[0].name
                    };
                }else{
                    $scope.lesson.location = {}; 
                }
                
                if(newlocationdetails[0].geometry){
                    $scope.lesson.location.geometry=newlocationdetails[0].geometry.location;
                }
              
                
                console.log("$scope.lesson.location.address=" + $scope.lesson.location.address);
            }
        });
        
        $scope.$apply();
        
        

	} ];
});
