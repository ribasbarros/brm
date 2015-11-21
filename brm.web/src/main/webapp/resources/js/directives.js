'use strict';

/* Directives */

var app = angular.module('brm.directives', []);

app.directive('brmDataTable', function() {
	return {
		restrict : 'E',
		scope : {
			column : '=column',
			restEntries : '=restEntries',
			urlFormCreate : '=urlFormCreate',
			urlFormEdit : '=urlFormEdit',
			urlCrud : '=urlCrud',
			isDelete : '=isDelete',
			isEdit : '=isEdit'
		},
		controller : 'DataTableController',
		templateUrl : 'private/components/template-data-table'
	};
});

app.directive('brmShowData', function() {

	return {
		restrict : 'E',
		scope : {
			column : '=column',
			data : '=',
			isDelete : '=isDelete',
			isEdit : '=isEdit'
		},
		controller : 'ShowDataController',
		templateUrl : 'private/components/template-show-data'
	};
});

app.directive('brmShowMessage', function() {

	return {
		restrict : 'E',
		templateUrl : 'private/components/template-message',
		link : function($scope) {
			$scope.$watch("trtResponse", function handleFooChange(newValue,
					oldValue) {
				if (!angular.isUndefined(newValue.httpMensagem)) {
					$('#idMessage').modal();
				}
			});
		}
	};
});

app.directive('brmShowInfo', [ 'FornecedorFactory', 'SkuFactory',
		function(FornecedorFactory, SkuFactory) {

			return {
				restrict : 'E',
				scope : {
					tipo : '=',
					id : '='
				},
				template : '<span ng-show="isFornecedor()">{{result}}</span><span ng-hide="isFornecedor()"><ol class="breadcrumb"><li ng-repeat="l in result track by $index">{{l.nome}}</li></ol></span>',
				link : function($scope) {

					$scope.isFornecedor = function(){
						return $scope.tipo == 'FORNECEDOR';
					};
					
					if ($scope.tipo == 'FORNECEDOR') {

						FornecedorFactory.get({
							id : $scope.id
						}, function(response) {
							$scope.result = response.nomeFantasia;
						});

					} else if ($scope.tipo == 'SKU') {

						$scope.sku = SkuFactory.get({
							id : $scope.id
						}, function(response) {
							$scope.result = response.tags;
						});
						

					} else {
						$scope.result = "ERROR!";
					}
				}
			};
		} ]);
