'use strict';

/* Controllers */
var app = angular.module('brm.controllers', [ 'ngCookies', 'ngResource' ]);

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

app.controller('SkuController', [ '$scope', '$location',
		function($scope, $location) {

			$scope.REST_SEARCH = 'sku/search';
			$scope.URL_CRUD = 'sku/:id'
			$scope.URL_FORM = 'private/sku/sku-form';

			$scope.map = [ {
				'title' : 'Item',
				'field' : 'item',
				'subField' : 'nome'
			}, {
				'title' : 'Tags',
				'field' : 'tags',
				'subField' : 'nome',
				'isArray' : 'true'
			}, {
				'title' : 'Situação',
				'field' : 'status'
			}, {
				'title' : 'Planejamento',
				'field' : 'modelo'
			}, {
				'title' : 'Classe',
				'field' : 'classe'
			}, {
				'title' : 'Origem',
				'field' : 'origens',
				'subField' : 'tipo',
				'isArray' : 'true'
			}, {
				'title' : 'Data Maturidade',
				'field' : 'dataMaturidade',
				'isDate' : 'true'
			}, {
				'title' : 'Criação',
				'field' : 'dataCriacao',
				'isDate' : 'true'
			}, {
				'title' : 'Alteração',
				'field' : 'dataAlteracao',
				'isDate' : 'true'
			} ];

		} ]);

app.controller('ItemController', [ '$scope', '$location',
		function($scope, $location) {

			$scope.REST_SEARCH = 'item/search';
			$scope.URL_CRUD = 'item/:id'
			$scope.URL_FORM = 'private/item/item-form';

			$scope.map = [ {
				'title' : 'Nome',
				'field' : 'nome'
			}, {
				'title' : 'Situação',
				'field' : 'status'
			}, {
				'title' : 'Valor Unitário',
				'field' : 'valorUnitario',
				'isCurrency' : 'true'
			}, {
				'title' : 'Unitilização',
				'field' : 'unitizacao'
			}, {
				'title' : 'Categoria',
				'field' : 'categoria',
				'subField' : 'nome'
			}, {
				'title' : 'Criação',
				'field' : 'dataCriacao',
				'isDate' : 'true'
			}, {
				'title' : 'Alteração',
				'field' : 'dataAlteracao',
				'isDate' : 'true'
			} ];

		} ]);

app.controller('FornecedorController', [ '$scope', '$location',
		'FornecedorFactory', function($scope, $location, FornecedorFactory) {

			$scope.fornecedor = new FornecedorFactory();

			$scope.submeter = function() {

				$scope.fornecedor.$save(function(response) {
					console.log(response);
				});

			};

			$scope.REST_SEARCH = 'fornecedor/search';
			$scope.URL_CRUD = 'fornecedor/:id'
			$scope.URL_FORM = 'private/fornecedor/fornecedor-form';

			$scope.map = [ {
				'title' : 'Nome Fantasia',
				'field' : 'nomeFantasia'
			}, {
				'title' : 'CNPJ',
				'field' : 'cnpj'
			}, {
				'title' : 'Contato',
				'field' : 'contatos'
			} ];

		} ]);

app.controller('CategoriaController', [ '$scope', '$location',
		function($scope, $location) {

			$scope.REST_SEARCH = 'categoria/search';
			$scope.URL_CRUD = 'categoria/:id'
			$scope.URL_FORM = 'private/categoria/categoria-form';

			$scope.map = [ {
				'title' : 'Nome',
				'field' : 'nome'
			}, {
				'title' : 'Usuario Criação',
				'field' : 'usuarioCriacao'
			} ];

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
