'use strict';

/* Controllers */
var app = angular.module('brm.controllers', [ 'ngCookies', 'ngResource' ]);

app.controller('DummyController', ['$rootScope', '$scope', '$http', '$resource', 
		'ItemFactory', function( $rootScope, $scope, $http, $resource, ItemFactory) {

			$scope.logout = function() {
				$http.post('auth/logout', {}).success(function() {
					$window.location.href = "/brm.web/";
				}).error(function(data) {
					console.log(data);
				});
			};

			$scope.ENTRIES = [ {
				'nome' : 'nome teste 1',
				'usuarioCriacao' : 'user teste 1'
			}, {
				'nome' : 'nome teste 2',
				'usuarioCriacao' : 'user teste 2'
			} ];

			$scope.MAP = [ {
				'title' : 'Nome',
				'field' : 'nome'
			}, {
				'title' : 'Usuario Criação',
				'field' : 'usuarioCriacao'
			} ];

			$scope.teste = function() {
				alert($scope.ENTRIES);
			};

			var List = $resource('item/all');
			
			$scope.itemsTeste = List.query();
			
			$scope.items = ['item1', 'item2', 'item3'];

			  $scope.animationsEnabled = true;

			  $scope.open = function (size) {

			    var modalInstance = $uibModal.open({
			      animation: $scope.animationsEnabled,
			      templateUrl: 'myModalContent.html',
			      controller: 'ModalInstanceCtrl',
			      size: size,
			      resolve: {
			        items: function () {
			          return $scope.items;
			        }
			      }
			    });

			    modalInstance.result.then(function (selectedItem) {
			      $scope.selected = selectedItem;
			    }, function () {
			      $log.info('Modal dismissed at: ' + new Date());
			    });
			  };

			  $scope.toggleAnimation = function () {
			    $scope.animationsEnabled = !$scope.animationsEnabled;
			  };


		} ]);

app.controller('CompanyCtrl',
		[ function($scope, $http, $location, DummyFactory) {

		} ]);

app
		.controller(
				'SkuController',
				[
						'$scope',
						'$location',
						'SkuFactory',
						function($scope, $location, SkuFactory) {

							$scope.sku = new SkuFactory();
							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.sku
											.$save(
													function(response) {
														$scope.mensagem = 'Cadastro de Sku realizado com sucesso!';
													},
													function(erro) {
														$scope.mensagem = 'Cadastro de Sku não realizado!';
													});
								}
							};

							$scope.REST_SEARCH = 'sku/search';
							$scope.URL_CRUD = 'sku/:id';
							$scope.URL_FORM_CREATE = 'private/sku/sku-create';
							$scope.URL_FORM_EDIT = 'private/sku/sku-edit';

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

app
		.controller(
				'ItemController',
				[
						'$scope',
						'$location',
						'ItemFactory',
						function($scope, $location, ItemFactory) {

							$scope.mensagem = '';

							$scope.item = new ItemFactory();
							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.item
											.$save(
													function(response) {
														$scope.mensagem = 'Cadastro de Item realizado com sucesso!';
													},
													function(erro) {
														$scope.mensagem = 'Cadastro de Item não realizado!';
													});
								}
							};

							$scope.REST_SEARCH = 'item/search';
							$scope.URL_CRUD = 'item/:id';
							$scope.URL_FORM_CREATE = 'private/item/item-create';
							$scope.URL_FORM_EDIT = 'private/item/item-edit';

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

app
		.controller(
				'FornecedorController',
				[
						'$scope',
						'$location',
						'FornecedorFactory',
						function($scope, $location, FornecedorFactory) {

							$scope.selectedContato = {};
							
							$scope.mensagem = '';

							$scope.columns = [ {
								'title' : 'Nome',
								'field' : 'nome'
							} ];

							$scope.fornecedor = new FornecedorFactory();
							$scope.fornecedor.contatos = [];

							$scope.addContato = function() {
								$scope.fornecedor.contatos.push({
									nome : 'Leon GAY!',
									telefone : {
										numero : '111',
										celular : false,
										ramal : ''
									}
								});
							};

							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.fornecedor
											.$save(
													function(response) {
														$scope.mensagem = 'Cadastro de Fornecedor realizado com sucesso!';
													},
													function(erro) {
														$scope.mensagem = 'Cadastro de Fornecedor não realizado!';
													});
								}
							};

							$scope.REST_SEARCH = 'fornecedor/search';
							$scope.URL_CRUD = 'fornecedor/:id';
							$scope.URL_FORM_CREATE = 'private/fornecedor/fornecedor-create';
							$scope.URL_FORM_EDIT = 'private/fornecedor/fornecedor-edit';

							$scope.map = [ {
								'title' : 'Nome Fantasia',
								'field' : 'nomeFantasia'
							}, {
								'title' : 'CNPJ',
								'field' : 'cnpj'
							}, {
								'title' : 'Contato',
								'field' : 'contatos',
								'subField' : 'nome',
								'isArray' : 'true'
							} ];

						} ]);

app.controller('FornecedorEditController', [ '$scope', '$routeParams',
		'$location', 'FornecedorFactory',
		function($scope, $routeParams, $location, FornecedorFactory) {

			$scope.columns = [ {
				'title' : 'Nome',
				'field' : 'nome'
			} ];

			$scope.fornecedor = FornecedorFactory.get({
				id : $routeParams.id
			});

			var temp = [];
			$scope.fornecedor.$promise.then(function(data) {
				temp = data.contatos;
			});

			$scope.fornecedor.contatos = temp;

		} ]);

app.controller('SkuEditController', [ '$scope', '$routeParams', '$location',
		'SkuFactory',
		function($scope, $routeParams, $location, FornecedorFactory) {

		} ]);

app.controller('ItemEditController', [ '$scope', '$routeParams', '$location',
		'ItemFactory',
		function($scope, $routeParams, $location, FornecedorFactory) {

		} ]);

app.controller('CategoriaController', [ '$scope', '$location',
		'CategoriaFactory', function($scope, $location, CategoriaFactory) {
			$scope.mensagem = '';

			$scope.categoria = new CategoriaFactory();
			$scope.submeter = function() {
				if ($scope.formularioCategoria.$valid) {
					$scope.categoria.$save(function(response) {
						$scope.mensagem = 'Cadastro de Categoria realizado com sucesso!';
					}, function(erro) {
						$scope.mensagem = 'Cadastro de Categoria não realizado!';
					});
				}
			};

			$scope.REST_SEARCH = 'categoria/search';
			$scope.URL_CRUD = 'categoria/:id';
			$scope.URL_FORM_CREATE = 'private/categoria/categoria-create';
			$scope.URL_FORM_EDIT = 'private/categoria/categoria-edit';

			$scope.map = [ {
				'title' : 'Nome',
				'field' : 'nome'
			}, {
				'title' : 'Usuario Criação',
				'field' : 'usuarioCriacao'
			} ];

		} ]);



app.controller('GrupoController', [ '$scope', '$location',
	'GrupoFactory', function($scope, $location, GrupoFactory) {
		$scope.mensagem = '';

		$scope.grupo = new GrupoFactory();
		$scope.submeter = function() {
			if ($scope.formulario.$valid) {
				$scope.grupo.$save(function(response) {
					$scope.mensagem = 'Cadastro de Grupo realizado com sucesso!';
				}, function(erro) {
					$scope.mensagem = 'Cadastro de Grupo não realizado!';
				});
			}
		};

		$scope.REST_SEARCH = 'grupo/search';
		$scope.URL_CRUD = 'grupo/:id';
		$scope.URL_FORM = 'private/grupo/grupo-form';

		$scope.map = [ {
			'title' : 'Nome',
			'field' : 'nome'
		}, {
			'title' : 'Perfis',
			'field' : 'perfis',
			'isArray' : 'true'
		} ];
	} ]);



app.controller('UsuarioController', [ '$scope', '$location',
	'UsuarioFactory', function($scope, $location, UsuarioFactory) {
		$scope.mensagem = '';

		$scope.usuario = new UsuarioFactory();
		$scope.submeter = function() {
			if ($scope.formulario.$valid) {
				$scope.usuario.$save(function(response) {
					$scope.mensagem = 'Cadastro de Usuario realizado com sucesso!';
				}, function(erro) {
					$scope.mensagem = 'Cadastro de Usuario não realizado!';
				});
			}
		};

		$scope.REST_SEARCH = 'usuario/search';
		$scope.URL_CRUD = 'usuario/:id';
		$scope.URL_FORM_CREATE = 'private/usuario/usuario-create';
		$scope.URL_FORM_EDIT = 'private/usuario/usuario-edit';

		$scope.map = [ {
			'title' : 'Nome',
			'field' : 'nome'
		}, {
			'title' : 'Cargo',
			'field' : 'cargo'
		}, {
			'title' : 'Email',
			'field' : 'email'
		}, {
			'title' : 'Grupos',
			'field' : 'grupos',
			'isArray' : 'true'
		} ];
	} ]);


app.controller('UsuarioEditController', [ '$scope', '$routeParams',
                                     		'$location', 'UsuarioFactory',
                                     		function($scope, $routeParams, $location, UsuarioFactory) {
                                     			
                                     			$scope.usuario = UsuarioFactory.get({
                                     				id : $routeParams.id
                                     			});
                                     			                                     			
                                     			 $scope.submeter = function() {
                                                     if ($scope.formulario.$valid) {
                                                         $scope.usuario.$update(function() {
                                                             $scope.mensagem = 'Cadastro Atualizado com sucesso!';
                                                         }, function(erro) {
                                                        	 console.log($scope.usuario);
                                                             $scope.mensagem = 'ERRO';
                                                         });
                                                     }
                                                 };
                                     			                                     			
                                     		} ]);


app.controller('PerfilController', [ '$scope', '$location',
                                     'PerfilFactory', function($scope, $location, PerfilFactory) {
                                         $scope.mensagem = '';

                                         $scope.perfil = new PerfilFactory();
                                         $scope.submeter = function() {
                                             if ($scope.formulario.$valid) {
                                                 $scope.perfil.$save(function(response) {
                                                     $scope.mensagem = 'Cadastro de Perfil realizado com sucesso!';
                                                 }, function(erro) {
                                                	 console.log($scope.perfil);
                                                     $scope.mensagem = 'Cadastro de Perfil não realizado!';
                                                 });
                                             }
                                         };

                                         $scope.REST_SEARCH = 'perfil/search';
                                         $scope.URL_CRUD = 'perfil/:id';
                                         $scope.URL_FORM = 'private/perfil/perfil-form';

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
