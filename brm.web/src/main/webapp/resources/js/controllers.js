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

			$scope.view = {
				'searchTerm' : '',
				'totalPerPage' : {
					'availableOptions' : [ {
						'id' : '10',
						'name' : '10'
					}, {
						'id' : '50',
						'name' : '50'
					}, {
						'id' : '100',
						'name' : '100'
					}, {
						'id' : '500',
						'name' : '500'
					}, {
						'id' : '1000',
						'name' : '1000'
					} ],
					'selectedOption' : {
						'id' : '10',
						'name' : '10'
					}
				},
				'entries' : [],
				'index' : 0,
				'totalPages' : 1,

			}

			$scope.range = function(n) {
				return new Array(n);
			};

			$scope.search = function(index) {
				$scope.page(index);
			};

			$scope.page = function(index) {

				$scope.view.index = index;

				var pageable = FornecedorFactory.query({
					searchTerm : $scope.view.searchTerm,
					pageIndex : index,
					size : $scope.view.totalPerPage.selectedOption.name
				});

				pageable.$promise.then(function(data) {
					$scope.view.entries = data.result;
					$scope.view.totalPerPage.selectedOption.id = data.size
					$scope.view.totalPerPage.selectedOption.name = data.size
					$scope.view.totalPages = data.totalPages;
				});
			};

			$scope.previous = function() {
				if ($scope.view.index > 0) {
					$scope.view.index--;
					$scope.page($scope.view.index);
				}
			};

			$scope.next = function() {
				if ($scope.view.index < $scope.view.totalPages - 1) {
					$scope.view.index++;
					$scope.view.page($scope.view.index);
				}
			};
			
			$scope.page($scope.view.index);

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
