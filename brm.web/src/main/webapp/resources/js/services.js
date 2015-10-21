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

services.factory('CategoriaFactory', [ '$resource', function($resource) {
	return $resource('categoria/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    }
	  });
} ]);

services.factory('ItemFactory', [ '$resource', function($resource) {
	return $resource('item/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    }
	  });
} ]);

services.factory('SkuFactory', [ '$resource', function($resource) {
	return $resource('sku/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    }
	  });
} ]);

services.factory('PerfilFactory', [ '$resource', function($resource) {
	return $resource('perfil/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    }
	  });
} ]);


services.factory('GrupoFactory', [ '$resource', function($resource) {
	return $resource('grupo/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    }
	  });
} ]);


services.factory('UsuarioFactory', [ '$resource', function($resource) {
	return $resource('usuario/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    }
	  });
} ]);
