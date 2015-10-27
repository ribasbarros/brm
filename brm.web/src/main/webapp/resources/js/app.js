'use strict';

var app = angular.module('brm', [ 'ngCpfCnpj', 'ui.mask', 'ngRoute', 'ngCookies', 'brm.controllers',
		'brm.controllers.comp', 'brm.directives', 'brm.services' ]);

app.config(function($routeProvider) {
	$routeProvider.when('/dummy', {
		templateUrl : 'dummy.html'
	}).when('/company', {
		templateUrl : 'company.html'
	}).when('/private/:type/:typePage', {
		templateUrl : buildPath
	}).when('/private/:type/:typePage/:id', {
		templateUrl : buildPath
	}).otherwise({
		redirectTo : '/dummy'
	});

	function buildPath(path) {
		var url = 'private/' + path.type + '/' + path.typePage + '.html';
		return url;
	}

});

app.run([ '$http', '$cookies', function($http, $cookies) {
	$http.defaults.transformResponse.unshift(function(data, headers) {
		var csrfToken = $cookies.get('XSRF-TOKEN');
		if (!!csrfToken) {
			$http.defaults.headers.common['X-CSRF-TOKEN'] = csrfToken;
		}

		return data;
	});
} ]);