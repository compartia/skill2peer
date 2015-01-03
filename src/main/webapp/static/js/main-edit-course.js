require([ './main' ], function(common) {

	require([ 'angularAMD', 'app'], 
            
    function(angularAMD, app) {
        return angularAMD.bootstrap(app);
	});

});
