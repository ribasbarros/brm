'use strict';

/* Services */
var services = angular.module('brm.services', [ 'ngResource' ]);

services.factory('DummyFactory', function($resource) {
	/*
	 * return $resource('dummy/featured', {}, { 'query': { method: 'GET',
	 * isArray: true } })
	 */
});

services.factory('CompanyFactory', function($resource) {

});

services.factory('FornecedorFactory', [ '$resource', function($resource) {
	
	return $resource('fornecedor/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    }
	  });
	
	
} ]);
