require([ './main' ], function(common) {

	require([ 'angularAMD', 'app', 'dasha-script'], 
            
    function(angularAMD, app) {
        return angularAMD.bootstrap(app);
	});

});
