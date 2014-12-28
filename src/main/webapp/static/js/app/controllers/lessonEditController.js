define(
//
[ 'autocomplete', 'moment'],
//
function onReady() {

	return [ '$scope', function($scope) {
        /**
		 * the first version starts from SPB, Russia
		 */
		var spb = new google.maps.LatLng(60, 30);
		var radius = 40000;
        
        
        $scope.lesson = $scope.$parent.selectedLesson;
        $scope.start={};
		$scope.placeSearchOptions = {};
        
        
        
        if($scope.lesson.schedule.dateTime){
            $scope.start.date = moment($scope.lesson.schedule.dateTime).toDate();
            $scope.start.minutes=$scope.start.date.getMinutes();
            $scope.start.hours=$scope.start.date.getHours();
        }else{
            $scope.start.date = moment().toDate();
            $scope.start.minutes=0;
            $scope.start.hours=12;
        }
		

		$scope.placeSearchOptions.location = spb;
		$scope.placeSearchOptions.radius = radius;

        if(!$scope.lesson.schedule.repeatDays){
            $scope.lesson.schedule.repeatDays=[false, false, false, false, false, false, false];
        }

        $scope.$watch('lesson.schedule.repeatDays', function (newVal) { 
             $scope.onRecurrenceChange(newVal);
        }, true);
        
        $scope.onRecurrenceChange = function(newVal) {
            var weekDay=-1;
            for(f=0; f<newVal.length; f++){
                if(newVal[f]===true){
                   weekDay=f;
                    break;
                }
            }
            
            if(weekDay>=0 && $scope.start.week){
                $scope.nextLessonDate=$scope.start.week.clone().add(weekDay, 'd').format("D MMMM (ddd)");
                $scope.weekStartStr=$scope.start.week.format("D MMMM (ddd)");
            }
            
        };
        
        $scope.onDateChange = function() {
            var d=$scope.start.date;
            var h=$scope.start.hours;
            var m=$scope.start.minutes;
            
            if(d){
                /* time in client's timezone
                */
                $scope.lesson.schedule.dateTime = moment(
                    [d.getFullYear(), d.getMonth(), d.getDate(), h, m]) ;

                $scope.start.week = moment($scope.lesson.schedule.dateTime).clone().startOf('isoWeek');
                //$scope.onRecurrenceChange($scope.lesson.schedule.repeatDays);
            }
		};

		$scope.$apply();
	} ];
});
