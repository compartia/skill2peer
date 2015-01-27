define(
//
[ 'angularsanitize', 'moment', 'ngTagsInput' ],
//
function onReady() {

	return [ '$scope', 'Courses', '$routeParams', '$location', '$sce',
			function($scope, Courses, $routeParams, $location, $sce) {

				$scope.selectLesson = function(_lesson) {
					$scope.lesson = _lesson;
					console.log("new lesson selected");
				};

				this.createNewCourse = function() {
					$scope.course = new Courses({});
					$scope.course.lessons = [];
					$scope.addLesson();
					console.log("course created: " + $scope.course);
				};

				this.init = function() {
					createNewCourse();

					$scope.availableLocations = Courses.availableLocations({}, function(locations) {
						loadCourse();
					});
				};

				this.loadCourse = function() {
					if ($routeParams.courseId && $routeParams.courseId != "new") {
						Courses.edit({
							id : $routeParams.courseId
						}, function(course) {
							$scope.course = course;
							console.log("course loaded, id: " + course.id);
							$scope.selectLesson($scope.course.lessons[0]);
							// $scope.$apply();
						});

					} else {
						$scope.selectLesson($scope.course.lessons[0]);
						// $scope.$apply();
					}

					$scope.$watch('course.name', function(newVal) {
						if ($scope.isSingle()) {
							$scope.course.lessons[0].name = newVal;
						}
					}, true);

					console.log("course editor inited");
				};

				$scope.isSingle = function() {
					return $scope.course.lessons.length < 2;
				};

				$scope.deleteLesson = function(_lesson) {
					if ($scope.course.lessons.length == 1) {
						return;
					}

					var index = $scope.course.lessons.indexOf(_lesson);
					$scope.course.lessons.splice(index, 1);

					if (_lesson == $scope.lesson) {
						$scope.selectLesson($scope.course.lessons[0]);
					}
				};

				$scope.addLesson = function() {
					var locationId = -1;
					var newDate = moment().toDate();

					if ($scope.course.lessons.length > 0) {
						locationId = $scope.course.lessons[$scope.course.lessons.length - 1].location.id;
						newDate = $scope.course.lessons[$scope.course.lessons.length - 1].schedule.dateTime;
					}
					var newLesson = {
						"id" : null,
						"name" : null,
						"schedule" : {
							"dateTime" : newDate,
							"repeatDays" : [ false, false, false, false, false, false, false ]
						},
						"location" : {
							id : locationId
						}
					};

					newLesson.location = $scope.getLocationById(newLesson.location.id);

					if ($scope.course.lessons.length == 1) {
						$scope.course.lessons[0].name = $scope.course.name;
					}

					$scope.course.lessons.push(newLesson);
					console.log("1 lesson added:" + newLesson);
					$scope.selectLesson(newLesson);

				};

				var emptyLocation = {
					id : -1
				};

				$scope.getLocationById = function(id) {
					if (id == -1) {
						return emptyLocation;
					}

					for (f = 0; f < $scope.availableLocations.length; f++) {
						if ($scope.availableLocations[f].id == id) {
							return $scope.availableLocations[f];
						}
					}
					;
					return emptyLocation;

				};

				$scope.getTrustedHtml = function(string) {
					return $sce.trustAsHtml(string);
				}

				$scope.save = function() {
					var savedCourse = Courses.save($scope.course);
					savedCourse.$promise.then(function() {
						$scope.course = savedCourse;
						$scope.selectLesson($scope.course.lessons[0]);
						$location.path('/home/2');
					});
				};

				init();

			} ];
});
