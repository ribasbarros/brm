'use strict';

/* Controllers */
var app = angular.module('brm.controllers', []);

app.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });
});

app.controller('DummyCtrl', ['$scope', '$http', '$location', 'DummyFactory', function ($scope, $http, $location, DummyFactory) {

}]);

app.controller('CompanyCtrl', ['$scope', '$http', '$location', function ($scope, $http, $location) {
	
}]);