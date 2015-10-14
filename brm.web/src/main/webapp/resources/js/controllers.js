'use strict';

/* Controllers */
var app = angular.module('brm.controllers', [ 'ngCookies' ]);

app.controller('DummyController', [ '$rootScope', '$scope', '$http', '$window',
		function($rootScope, $scope, $http, $window) {

			$scope.logout = function() {
				$http.post('auth/logout', {}).success(function() {
					$window.location.href = "/brm.web/";
				}).error(function(data) {
					console.log(data);
				});
			}

		} ]);

app.controller('CompanyCtrl',
		[ function($scope, $http, $location, DummyFactory) {

		} ]);

app.controller('FornecedorController', [ '$scope', 'FornecedorFactory',
		function($scope, FornecedorFactory) {

			$scope.entries = {};
			$scope.index = 0;
			$scope.totalPerPage = 10;
			$scope.totalPages = 1;

			$scope.range = function(n) {
				return new Array(n);
			};

			$scope.page = function(index) {
				
				console.log(index);
				
				$scope.index = index;
				
				var pageable = FornecedorFactory.all({
					pageIndex : index,
					numberOfFornecedorPorPagina : $scope.totalPerPage
				});

				pageable.$promise.then(function(data) {
					$scope.entries = data.result;
					$scope.totalPages = data.totalPages;
					$scope.totalPerPage = data.numberOfElements;
				});
			};

			$scope.previous = function() {
				if($scope.index > 0){
					$scope.index--;
					$scope.page($scope.index);
				}
			};

			$scope.next = function() {
				if($scope.index < $scope.totalPages-1){
					$scope.index++;
					$scope.page($scope.index);
				}
			};

			$scope.page($scope.index);

		} ]);

app.controller('CsrfCtrl', [ '$rootScope', '$scope', '$http', '$cookies',
		'$window', function($rootScope, $scope, $http, $cookies, $window) {

			$scope.credentials = {
				username : 'user',
				password : 'user'
			};

			$scope.login = function() {

				var csrfToken = $cookies.get('XSRF-TOKEN');

				/*
				 * $http.post('auth/login_check', $scope.credentials).success(
				 * function(data) { console.log(data); $rootScope.authenticated =
				 * true; // $location.path("/index"); }).error(function(data) // {
				 * console.log(data); $rootScope.authenticated = false; });
				 */

				$http({
					method : 'POST',
					url : 'auth/login_check',
					params : {
						'username' : $scope.credentials.username,
						'password' : $scope.credentials.password,
						'_csrf' : csrfToken
					}
				}).then(function(data, status, headers) {
					$window.location.href = "/brm.web/#/dummy";
				});

			};

		} ]);
