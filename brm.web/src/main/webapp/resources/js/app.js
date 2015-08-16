'use strict';

var app = angular.module('brm', [ 'ngRoute' , 'brm.controllers' ]);

app.config(function($routeProvider) {
	$routeProvider.when('/dummy', {
		templateUrl : 'dummy.html'
	}).when('/company', {
		templateUrl : 'company.html'
	}).otherwise({
		redirectTo : '/dummy'
	});
})