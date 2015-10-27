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
