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

app.directive('brmPedido', function() {
	return {
		restrict : 'E',
		templateUrl : 'private/components/template-pedido',
	};
});

app.directive('brmSkuChart', function() {
	return {
		restrict : 'E',
		scope : {
			data : '='
		},
		template : '<nvd3 options="options" data="data"></nvd3>',
		link : function($scope){
		
			$scope.options = {
		            chart: {
		                type: 'lineChart',
		                height: 450,
		                margin : {
		                    top: 20,
		                    right: 20,
		                    bottom: 40,
		                    left: 55
		                },
		                x: function(d){ return d.x; },
		                y: function(d){ return d.y; },
		                useInteractiveGuideline: true,
		                dispatch: {
		                    stateChange: function(e){ console.log("stateChange"); },
		                    changeState: function(e){ console.log("changeState"); },
		                    tooltipShow: function(e){ console.log("tooltipShow"); },
		                    tooltipHide: function(e){ console.log("tooltipHide"); }
		                },
		                xAxis: {
		                    axisLabel: 'Data', 
		                    tickFormat: function(d){
		                        return d3.time.format('%m/%y')(new Date(d))
		                    },
		                },
		                yAxis: {
		                    axisLabel: 'Demanda',
		                    tickFormat: function(d){
		                    	return d;
		                    },
		                    axisLabelDistance: -10
		                },
		                callback: function(chart){
		                    console.log("!!! lineChart callback !!!");
		                }
		            },
		            title: {
		                enable: true,
		                text: 'Saúde Histórica da SKU'
		            },
		            subtitle: {
		                enable: true,
		                text: '',
		                css: {
		                    'text-align': 'center',
		                    'margin': '10px 13px 0px 7px'
		                }
		            },
		            caption: {
		                enable: true,
		                html: '<b>Dados Históricos da SKU</b>',
		                css: {
		                    'text-align': 'justify',
		                    'margin': '10px 13px 0px 7px'
		                }
		            }
		}
	}
}});


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
