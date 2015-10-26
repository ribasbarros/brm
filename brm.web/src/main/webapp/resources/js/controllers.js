'use strict';

/* Controllers */
var app = angular.module('brm.controllers', [ 'ngCookies', 'ngResource' ]);

app.controller('DummyController', [ '$rootScope', '$scope', '$http',
		'$resource', 'ItemFactory',
		function($rootScope, $scope, $http, $resource, ItemFactory) {

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

			$scope.items = [ 'item1', 'item2', 'item3' ];

			$scope.animationsEnabled = true;

			$scope.open = function(size) {

				var modalInstance = $uibModal.open({
					animation : $scope.animationsEnabled,
					templateUrl : 'myModalContent.html',
					controller : 'ModalInstanceCtrl',
					size : size,
					resolve : {
						items : function() {
							return $scope.items;
						}
					}
				});

				modalInstance.result.then(function(selectedItem) {
					$scope.selected = selectedItem;
				}, function() {
					$log.info('Modal dismissed at: ' + new Date());
				});
			};

			$scope.toggleAnimation = function() {
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
						'$resource',
						'$location',
						'SkuFactory',
						function($scope, $resource, $location, SkuFactory) {
							$scope.listaItens = $resource('item/all').query();
							$scope.listaStatus = $resource('item/allStatus')
									.query();

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
						'$resource',
						'$location',
						'ItemFactory',
						function($scope, $resource, $location, ItemFactory) {

							$scope.selectedCategoria = {};
							
							$scope.listaCategoria = $resource('categoria/all')
									.query();
							$scope.listaStatus = [ {
								'nome' : 'DESCONTINUADO'
							}, {
								'nome' : 'CANCELADO'
							}, {
								'nome' : 'ATIVO'
							} ];

							$scope.mensagem = '';

							$scope.item = new ItemFactory();
							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									
									console.log($scope.selectedCategoria);
									$scope.item.categoria = $scope.selectedCategoria.originalObject;
									
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

app.controller('ItemEditController', [ '$scope', '$routeParams',
                                      		'$location', 'ItemFactory',
                                      		function($scope, $routeParams, $location, ItemFactory) {
	
                                      			$scope.item = ItemFactory.get({
                                      				id : $routeParams.id
                                      			});
                                      		                    							
                                      			$scope.submeter = function() {
                                      				if ($scope.formulario.$valid) {
                                      					$scope.item.$update(function() {
                                      						$scope.mensagem = 'Item Atualizado com sucesso!';
                                      					}, function(erro) {
                                      						$scope.mensagem = 'Alteração de Item não realizada!';
                                      					});
                                      				}
                                      			};

                                      		} ]);



app.controller('FornecedorController',
				[
						'$scope',
						'$location',
						'FornecedorFactory',
						function($scope, $location, FornecedorFactory) {

							$scope.selectedContato = {};
							$scope.selectedCentro = {};

							$scope.mensagem = '';

							$scope.columns = [ {
								'title' : 'Nome',
								'field' : 'nome'
							}, {
								'title' : 'Telefone',
								'field' : 'telefone',
								'subField' : 'numero'
							} ];

							$scope.fornecedor = new FornecedorFactory();
							$scope.fornecedor.contatos = [];

							$scope.addContato = function() {
								$scope.fornecedor.contatos
										.push($scope.selectedContato);

								$scope.selectedContato = {};
							};

							$scope.addCentro = function() {
								$scope.fornecedor.centros
										.push($scope.selectedCentro);

								$scope.selectedCentro = {};
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

app
		.controller(
				'FornecedorEditController',
				[
						'$scope',
						'$routeParams',
						'$location',
						'FornecedorFactory',
						function($scope, $routeParams, $location,
								FornecedorFactory) {

							$scope.fornecedor = {};

							$scope.columns = [ {
								'title' : 'Nome',
								'field' : 'nome'
							}, {
								'title' : 'Telefone',
								'field' : 'telefone',
								'subField' : 'numero'

							} ];

							$scope.fornecedor = FornecedorFactory.get({
								id : $routeParams.id
							}, function(data) {
								data.$promise.then(function(response) {
									$scope.fornecedor = data;
								});
							});

							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.fornecedor
											.$update(
													function() {
														$scope.mensagem = 'Alteração de Fornecedor realizado com sucesso!';
													},
													function(erro) {
														$scope.mensagem = 'Alteração de Fornecedor não realizado!';
													});
								}
							};

						} ]);

app.controller('SkuEditController', [ '$scope', '$routeParams', '$location',
		'SkuFactory', function($scope, $routeParams, $location, SkuFactory) {

			$scope.sku = SkuFactory.get({
				id : $routeParams.id
			});

			$scope.submeter = function() {
				if ($scope.formulario.$valid) {
					$scope.sku.$update(function() {
						$scope.mensagem = 'Sku Atualizado com sucesso!';
					}, function(erro) {
						$scope.mensagem = 'Alteração de Sku não realizada!';
					});
				}
			};

		} ]);

app
		.controller(
				'CategoriaController',
				[
						'$scope',
						'$location',
						'CategoriaFactory',
						function($scope, $location, CategoriaFactory) {
							$scope.mensagem = '';

							$scope.categoria = new CategoriaFactory();
							$scope.submeter = function() {
								if ($scope.formularioCategoria.$valid) {
									$scope.categoria
											.$save(
													function(response) {
														$scope.mensagem = 'Cadastro de Categoria realizado com sucesso!';
													},
													function(erro) {
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

app
		.controller(
				'CategoriaEditController',
				[
						'$scope',
						'$routeParams',
						'$location',
						'CategoriaFactory',
						function($scope, $routeParams, $location,
								CategoriaFactory) {

							$scope.categoria = CategoriaFactory.get({
								id : $routeParams.id
							});

							$scope.submeter = function() {
								if ($scope.formularioCategoria.$valid) {
									$scope.categoria
											.$update(
													function() {
														$scope.mensagem = 'Categoria Atualizado com sucesso!';
													},
													function(erro) {
														$scope.mensagem = 'Alteração de Categoria não realizada!';
													});
								}
							};

						} ]);

app
		.controller(
				'GrupoController',
				[
						'$scope',
						'$location',
						'GrupoFactory',
						function($scope, $location, GrupoFactory) {
							$scope.mensagem = '';

							$scope.grupo = new GrupoFactory();
							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.grupo
											.$save(
													function(response) {
														$scope.mensagem = 'Cadastro de Grupo realizado com sucesso!';
													},
													function(erro) {
														$scope.mensagem = 'Cadastro de Grupo não realizado!';
													});
								}
							};

							$scope.REST_SEARCH = 'grupo/search';
							$scope.URL_CRUD = 'grupo/:id';
							$scope.URL_FORM_CREATE = 'private/grupo/grupo-create';
							$scope.URL_FORM_EDIT = 'private/grupo/grupo-edit';

							$scope.map = [ {
								'title' : 'Nome',
								'field' : 'nome'
							}, {
								'title' : 'Perfis',
								'field' : 'perfis',
								'isArray' : 'true'
							} ];
						} ]);

app
		.controller(
				'GrupoEditController',
				[
						'$scope',
						'$routeParams',
						'$location',
						'GrupoFactory',
						function($scope, $routeParams, $location, GrupoFactory) {

							$scope.grupo = GrupoFactory.get({
								id : $routeParams.id
							});

							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.grupo
											.$update(
													function() {
														$scope.mensagem = 'Alteração de Grupo realizada com sucesso!';
													},
													function(erro) {
														console
																.log($scope.usuario);
														$scope.mensagem = 'Alteração de Grupo não realizada!';
													});
								}
							};

						} ]);

app
		.controller(
				'UsuarioController',
				[
						'$scope',
						'$location',
						'UsuarioFactory',
						function($scope, $location, UsuarioFactory) {
							$scope.mensagem = '';

							$scope.usuario = new UsuarioFactory();
							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.usuario
											.$save(
													function(response) {
														$scope.mensagem = 'Cadastro de Usuario realizado com sucesso!';
													},
													function(erro) {
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

app
		.controller(
				'UsuarioEditController',
				[
						'$scope',
						'$routeParams',
						'$location',
						'UsuarioFactory',
						function($scope, $routeParams, $location,
								UsuarioFactory) {

							$scope.usuario = UsuarioFactory.get({
								id : $routeParams.id
							});

							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.usuario
											.$update(
													function() {
														$scope.mensagem = 'Alteração de Usuário realizada com sucesso!';
													},
													function(erro) {
														console
																.log($scope.usuario);
														$scope.mensagem = 'Alteração de Usuário não realizada';
													});
								}
							};

						} ]);

app
		.controller(
				'PerfilController',
				[
						'$scope',
						'$location',
						'PerfilFactory',
						function($scope, $location, PerfilFactory) {
							$scope.mensagem = '';

							$scope.perfil = new PerfilFactory();
							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.perfil
											.$save(
													function(response) {
														$scope.mensagem = 'Cadastro de Perfil realizado com sucesso!';
													},
													function(erro) {
														console
																.log($scope.perfil);
														$scope.mensagem = 'Cadastro de Perfil não realizado!';
													});
								}
							};

							$scope.REST_SEARCH = 'perfil/search';
							$scope.URL_CRUD = 'perfil/:id';
							$scope.URL_FORM_CREATE = 'private/perfil/perfil-create';
							$scope.URL_FORM_EDIT = 'private/perfil/perfil-edit';

							$scope.map = [ {
								'title' : 'Nome',
								'field' : 'nome'
							}, {
								'title' : 'Usuario Criação',
								'field' : 'usuarioCriacao'
							} ];

						} ]);

app
		.controller(
				'PerfilEditController',
				[
						'$scope',
						'$routeParams',
						'$location',
						'PerfilFactory',
						function($scope, $routeParams, $location, PerfilFactory) {

							$scope.perfil = PerfilFactory.get({
								id : $routeParams.id
							});

							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.perfil
											.$update(
													function() {
														$scope.mensagem = 'Alteração de Perfil realizada com sucesso!';
													},
													function(erro) {
														$scope.mensagem = 'Alteração de Perfil não realizada!';
													});
								}
							};

						} ]);


app.controller(
		'DfuController',
		[
				'$scope',
				'$location',
				'DfuFactory',
				function($scope, $location, DfuFactory) {
					$scope.mensagem = '';

					$scope.dfu = new DfuFactory();
					$scope.submeter = function() {
						if ($scope.formularioCategoria.$valid) {
							$scope.dfu
									.$save(
											function(response) {
												$scope.mensagem = 'Cadastro de Dfu realizado com sucesso!';
											},
											function(erro) {
												$scope.mensagem = 'Cadastro de Dfu não realizado!';
											});
						}
					};

					$scope.REST_SEARCH = 'dfu/search';
					$scope.URL_CRUD = 'dfu/:id';
					$scope.URL_FORM_CREATE = 'private/dfu/dfu-create';
					$scope.URL_FORM_EDIT = 'private/dfu/dfu-edit';

					$scope.map = [ {
						'title' : 'Item',
						'field' : 'item',
						'subField' : 'nome'
						
					} , {
						'title' : 'Tags',
						'field' : 'tags',
						'subField' : 'nome',
						'isArray' : 'true'
						
					} , {
						'title' : 'Data de Maturidade',
						'field' : 'dataMaturidade',
						'isDate' : 'true'
						
					}, {
						'title' : 'Data de Lançamento',
						'field' : 'dataLancamento',
						'isDate' : 'true'
						
					},{
						'title' : 'Data de Descontinuação',
						'field' : 'dataDescontinuacao',
						'isDate' : 'true'
						
					},{
						'title' : 'Classe',
						'field' : 'classe'						
					},{
						'title' : 'Planejamento Dfu',
						'field' : 'modelo'						
					} ];

				} ]);

app.controller('CategoriaEditController', [ '$scope', '$routeParams',
                          		'$location', 'CategoriaFactory',
                          		function($scope, $routeParams, $location, CategoriaFactory) {

                          			$scope.categoria = CategoriaFactory.get({
                          				id : $routeParams.id
                          			});

                          			$scope.submeter = function() {
                          				if ($scope.formularioCategoria.$valid) {
                          					$scope.categoria.$update(function() {
                          						$scope.mensagem = 'Categoria Atualizado com sucesso!';
                          					}, function(erro) {
                          						$scope.mensagem = 'Alteração de Categoria não realizada!';
                          					});
                          				}
                          			};

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
