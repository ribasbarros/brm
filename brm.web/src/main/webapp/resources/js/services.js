'use strict';

/* Services */
var services = angular.module('brm.services', []);

services.factory('DummyFactory', function($resource) {
	/* return $resource('dummy/featured', {}, {
        'query': { method: 'GET', isArray: true }
    }) */
});

services.factory('CompanyFactory', function($resource) {
	
});

services.factory('FornecedorFactory', function($resource) {
	
});

