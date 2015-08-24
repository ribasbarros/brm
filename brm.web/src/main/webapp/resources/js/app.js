'use strict';

var app = angular.module('brm', [ 'ngRoute', 'ngCookies', 'brm.controllers' ]);

app.config(function($routeProvider) {
	$routeProvider.when('/dummy', {
		templateUrl : 'dummy.html'
	}).when('/company', {
		templateUrl : 'company.html'
	}).otherwise({
		redirectTo : '/dummy'
	});
	
});


app.run(['$http', '$cookies', function ($http, $cookies) {
    $http.defaults.transformResponse.unshift(function (data, headers) {
        var csrfToken = $cookies.get('XSRF-TOKEN');
        if (!!csrfToken) {
            $http.defaults.headers.common['X-CSRF-TOKEN'] = csrfToken;
        }

        return data;
    });
}]);