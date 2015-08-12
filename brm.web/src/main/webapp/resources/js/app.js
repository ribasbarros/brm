'use strict';

var app = angular.module('brm', ['ngRoute', 'flow', 'brm.filters', 'brm.services', 'brm.directives', 'brm.controllers']);
app.config(['$routeProvider', function ($routeProvider) {
	
    $routeProvider.when('/dummy', {templateUrl: 'dummy.html', controller: 'DummyCtrl'});
    $routeProvider.when('/company', {templateUrl: 'company.html', controller: 'CompanyCtrl'});
    
    $routeProvider.otherwise({redirectTo: '/dummy'});
}]);
