'use strict';

/* Controllers */
var app = angular.module('brm.controllers.comp', [ 'ngCookies', 'ngResource' ]);

app.controller('DataTableController', [ '$scope', '$http', '$location',
		'$resource', function($scope, $http, $location, $resource) {

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
				'size' : 0
			};

			$scope.page = function(index) {

				$scope.view.index = index;

				$scope.visualizationIndex();

				var List = $resource($scope.restEntries, {}, {
					'query' : {
						method : 'POST',
						isArray : false
					}
				});

				var pageable = List.query({
					searchTerm : $scope.view.searchTerm,
					pageIndex : index,
					size : $scope.view.totalPerPage.selectedOption.name
				});

				pageable.$promise.then(function(data) {
					$scope.view.entries = data.result;
					// $scope.view.totalPerPage.selectedOption.id = data.size
					// $scope.view.totalPerPage.selectedOption.name = data.size
					$scope.view.size = data.size;
					$scope.view.totalPages = data.totalPages;
				}, function(data) {

					$scope.view.entries = [];

					if (data.status == 400) {
						alert('Nenhum registro encontrado!');
					} else {
						alert('Error ' + data.status + ' - Erro não esperado!');
					}
				});

			};

			$scope.range = function(n) {
				return new Array(n);
			};

			$scope.permitir = [];

			$scope.visualizationIndex = function() {
				var mark = $scope.view.index;
				var totalPages = $scope.view.totalPages;

				$scope.permitir = [];

				if (mark > 0)
					$scope.permitir.push(mark - 1);
				if (mark > 1)
					$scope.permitir.push(mark - 2);
				if (mark > 2)
					$scope.permitir.push(mark - 3);
				if (mark > 3)
					$scope.permitir.push(mark - 4);

				$scope.permitir.push(mark);
				$scope.permitir.push(mark + 1);
				$scope.permitir.push(mark + 2);
				$scope.permitir.push(mark + 3);
				$scope.permitir.push(mark + 4);
				$scope.permitir.push(mark + 5);

				if (mark == 0) {
					for (var i = 6; i < 10; i++) {
						$scope.permitir.push(mark + i)
					}
				}
				if (mark == 1) {
					for (var i = 6; i < 9; i++) {
						$scope.permitir.push(mark + i)
					}
				}
				if (mark == 2) {
					for (var i = 6; i < 8; i++) {
						$scope.permitir.push(mark + i)
					}
				}
				if (mark == 3) {
					for (var i = 6; i < 7; i++) {
						$scope.permitir.push(mark + i)
					}
				}

				if (mark == totalPages - 1) {
					$scope.permitir.push(mark - 5);
					$scope.permitir.push(mark - 6);
					$scope.permitir.push(mark - 7);
					$scope.permitir.push(mark - 8);
					$scope.permitir.push(mark - 9);
				}

				if (mark == totalPages - 2) {
					$scope.permitir.push(mark - 5);
					$scope.permitir.push(mark - 6);
					$scope.permitir.push(mark - 7);
					$scope.permitir.push(mark - 8);
				}

				if (mark == totalPages - 3) {
					$scope.permitir.push(mark - 5);
					$scope.permitir.push(mark - 6);
					$scope.permitir.push(mark - 7);
				}

				if (mark == totalPages - 4) {
					$scope.permitir.push(mark - 5);
					$scope.permitir.push(mark - 6);
				}

				if (mark == totalPages - 5) {
					$scope.permitir.push(mark - 5);
				}

			};

			$scope.hideIndex = function(index) {
				var has = $scope.permitir.indexOf(index)
				return has < 0;
			};

			$scope.search = function(index) {
				$scope.page(index);
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
					$scope.page($scope.view.index);
				}
			};

			$scope.newRegister = function() {
				$location.path($scope.urlFormCreate);
			};

			$scope.edit = function(id) {
				$location.path($scope.urlFormEdit + "/" + id);
			};

			$scope.deleteRegister = function(id) {

				if (!confirm("Deseja excluir o registro?")) {
					return;
				}

				var obj = $resource($scope.urlCrud, {
					id : '@id'
				});

				obj.get({
					id : id
				}, function(response) {
					response.$delete(function(u) {
						$scope.view.searchTerm = '';
						if ($scope.view.size == 1) {
							$scope.view.index--;
						}
						$scope.page($scope.view.index);
					});
				});
			};

			$scope.page($scope.view.index);

		} ]);
