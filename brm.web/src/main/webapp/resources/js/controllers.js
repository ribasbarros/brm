'use strict';

/* Controllers */
var app = angular.module('brm.controllers', [ 'ngCpfCnpj', 'ui.mask',
		'ngCookies', 'ngResource' ]);

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
							$scope.sku = new SkuFactory();
							$scope.sku.frequenciaAnalise = [];
							$scope.sku.origens = [];
							$scope.selected = {};						
							$scope.listaItens = $resource('item/all').query();
							$scope.listaSku = $resource('sku/all').query();
							$scope.listaFornecedor = $resource('fornecedor/all').query();
							
							$scope.loadTags = function(query) {
								return $resource('tag/all').query().$promise;
							};

							$scope.diasDaSemana = [ {
								"id" : 2,
								"value" : "Segunda",
								"checked" : false
							}, {
								"id" : 3,
								"value" : "Terça",
								"checked" : false
							}, {
								"id" : 4,
								"value" : "Quarta",
								"checked" : false
							}, {
								"id" : 5,
								"value" : "Quinta",
								"checked" : false
							}, {
								"id" : 6,
								"value" : "Sexta",
								"checked" : false
							}, {
								"id" : 7,
								"value" : "Sábado",
								"checked" : false
							}, {
								"id" : 1,
								"value" : "Domingo",
								"checked" : false
							} ];
							
							$scope.listaOrigemSku = [ {
								'nome' : 'SKU'
							}, {
								'nome' : 'FORNECEDOR'
							} ];

							$scope.listaStatus = [ {
								'nome' : 'A'
							}, {
								'nome' : 'B'
							}, {
								'nome' : 'C'
							} ];

							$scope.listaPlanejamentos = [ {
								'nome' : 'ESTOQUE'
							}, {
								'nome' : 'SOB_ENCOMENDA'
							}, {
								'nome' : 'PROMOCAO'
							}, {
								'nome' : 'NO_LIMITE'
							} ];

							$scope.listaReposicoes = [ {
								'nome' : 'RASCUNHO'
							}, {
								'nome' : 'DESBLOQUEADA'
							}, {
								'nome' : 'BLOQUEADA'
							}, {
								'nome' : 'CANCELADA'
							}, {
								'nome' : 'FINALIZADA'
							} ];

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

							$scope.checkedOrNot = function(id, isChecked, index) {
								if (isChecked) {
									$scope.sku.frequenciaAnalise.push(id);
								} else {
									var _index = $scope.sku.frequenciaAnalise.indexOf(id);
									$scope.sku.frequenciaAnalise.splice(_index, 1);
								}
							};
							
							$scope.addOrigem = function() {
								if ($scope.modalOrigem.$valid) {
									$scope.sku.origens
											.push($scope.selected);
									$scope.selected = {};
									alert("Adicionado com sucesso!");
								}
							};
						

							$scope.REST_SEARCH = 'sku/search';
							$scope.URL_CRUD = 'sku/:id';
							$scope.URL_FORM_CREATE = 'private/sku/sku-create';
							$scope.URL_FORM_EDIT = 'private/sku/sku-edit';

							$scope.mapColumnsOrigem = [ {
								'title' : 'Tipo',
								'field' : 'tipo'
							}, {
								'title' : 'ID',
								'field' : 'id'
							}];
							
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

							$scope.categorias = $resource('categoria/all')
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
							$scope.salvar = function() {
								if ($scope.formulario.$valid) {
									$scope.item
											.$save(

													function(response) {
														$scope.mensagem = 'Cadastro de Item realizado com sucesso!';
													},
													function(erro) {
														console
																.log($scope.item);
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

app.controller('ItemEditController', [ '$scope', '$routeParams', '$location',
		'ItemFactory', function($scope, $routeParams, $location, ItemFactory) {

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

app
		.controller(
				'FornecedorController',
				[
						'$scope',
						'$location',
						'FornecedorFactory',
						function($scope, $location, FornecedorFactory) {

							$scope.selected = {};

							$scope.mensagem = '';

							$scope.mapColumnsContato = [ {
								'title' : 'Nome',
								'field' : 'nome'
							}, {
								'title' : 'Telefone',
								'field' : 'telefone',
								'subField' : 'numero'
							} ];

							$scope.mapColumnsCentro = [ {
								'title' : 'CNPJ',
								'field' : 'cnpj'
							}, {
								'title' : 'CEP',
								'field' : 'cep',
							}, {
								'title' : 'Centro',
								'field' : 'centro',
							} ];

							$scope.fornecedor = new FornecedorFactory();
							$scope.fornecedor.contatos = [];
							$scope.fornecedor.centros = [];

							$scope.addContato = function() {
								if ($scope.modalContato.$valid) {
									$scope.fornecedor.contatos
											.push($scope.selected);
									$scope.selected = {};
									alert("Adicionado com sucesso!");
								}
							};

							$scope.addCentro = function() {
								if ($scope.modalCentro.$valid) {
									$scope.fornecedor.centros
											.push($scope.selected);
									$scope.selected = {};
									alert("Adicionado com sucesso!");
								}
							};

							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									console.log($scope.fornecedor);
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
						'$route',
						'FornecedorFactory',
						function($scope, $routeParams, $location, $route,
								FornecedorFactory) {

							$scope.mapColumnsContato = [ {
								'title' : 'Nome',
								'field' : 'nome'
							}, {
								'title' : 'Telefone',
								'field' : 'telefone',
								'subField' : 'numero'
							} ];

							$scope.mapColumnsCentro = [ {
								'title' : 'CNPJ',
								'field' : 'cnpj'
							}, {
								'title' : 'CEP',
								'field' : 'cep',
							}, {
								'title' : 'Centro',
								'field' : 'centro',
							} ];

							$scope.addContato = function() {
								if ($scope.modalContato.$valid) {
									$scope.fornecedor.contatos
											.push($scope.selected);
									$scope.selected = {};
									alert("Adicionado com sucesso!");
								}
							};

							$scope.addCentro = function() {
								if ($scope.modalCentro.$valid) {
									$scope.fornecedor.centros
											.push($scope.selected);
									$scope.selected = {};
									alert("Adicionado com sucesso!");
								}
							};

							$scope.load = function() {

								$scope.fornecedor = FornecedorFactory.get({
									id : $routeParams.id
								});

							};

							$scope.saveAndClose = function() {
								$scope.save();
								$location
										.path("#/private/fornecedor/fornecedor-view");
							};

							$scope.save = function() {
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

							$scope.load();

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
				'TagController',
				[
						'$scope',
						'$location',
						'TagFactory',
						function($scope, $location, TagFactory) {
							$scope.mensagem = '';

							$scope.tag = new TagFactory();
							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.tag
											.$save(
													function(response) {
														$scope.mensagem = 'Cadastro de Tag realizado com sucesso!';
													},
													function(erro) {
														$scope.mensagem = 'Cadastro de Tag não realizado!';
													});
								}
							};

							$scope.REST_SEARCH = 'tag/search';
							$scope.URL_CRUD = 'tag/:id';
							$scope.URL_FORM_CREATE = 'private/tag/tag-create';
							$scope.URL_FORM_EDIT = 'private/tag/tag-edit';

							$scope.map = [ {
								'title' : 'Nome',
								'field' : 'nome'
							}, {
								'title' : 'Nível',
								'field' : 'nivel'
							}, {
								'title' : 'Usuario Criação',
								'field' : 'usuarioCriacao'
							} ];

						} ]);

app.controller('TagEditController', [ '$scope', '$routeParams', '$location',
		'TagFactory', function($scope, $routeParams, $location, TagFactory) {

			$scope.tag = TagFactory.get({
				id : $routeParams.id
			});

			$scope.submeter = function() {
				if ($scope.formulario.$valid) {
					$scope.tag.$update(function() {
						$scope.mensagem = 'Tag Atualizado com sucesso!';
					}, function(erro) {
						$scope.mensagem = 'Alteração de Tag não realizada!';
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
						'$resource',
						'$location',
						'GrupoFactory',
						function($scope, $resource, $location, GrupoFactory) {

							$scope.mensagem = '';
							$scope.grupo = new GrupoFactory();
							$scope.selected = {};
							$scope.grupo.perfis = [];
							$scope.listaPerfis = $resource('perfil/all')
									.query();
							$scope.addPerfil = function() {
								if ($scope.modalPerfil.$valid) {
									$scope.grupo.perfis.push($scope.selected);
									$scope.selected = {};
									alert("Adicionado com sucesso!");
								}
							};

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

							$scope.mapColumnsPerfil = [ {
								'title' : 'Nome',
								'field' : 'nome'
							} ];

							$scope.map = [ {
								'title' : 'Nome',
								'field' : 'nome'
							}, {
								'title' : 'Perfis',
								'field' : 'perfis',
								'subField' : 'nome',
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
						'$resource',
						'$location',
						'UsuarioFactory',
						function($scope, $resource, $location, UsuarioFactory) {
							$scope.listaGrupos = $resource('grupo/all').query();
							$scope.mensagem = '';
							$scope.selected = {};

							$scope.usuario = new UsuarioFactory();
							$scope.usuario.grupos = [];

							$scope.addGrupo = function() {
								if ($scope.modalGrupo.$valid) {
									console.log($scope.usuario.grupos);
									console.log($scope.selected);

									$scope.usuario.grupos.push($scope.selected);
									$scope.selected = {};
									alert("Adicionado com sucesso!");
								}
							};

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

							$scope.mapColumnsGrupo = [ {
								'title' : 'Nome',
								'field' : 'nome'
							} ];

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
								'subField' : 'nome',
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

app
		.controller(
				'DfuController',
				[
						'$scope',
						'$resource',
						'$location',
						'DfuFactory',
						function($scope, $resource, $location, DfuFactory) {
							$scope.dfu = new DfuFactory();
							$scope.mensagem = '';
							$scope.dfu.relacaoSku = [];
							$scope.selected = {};						
							$scope.listaItens = $resource('item/all').query();
							$scope.listaDfu = $resource('dfu/all').query();
							$scope.listaSku = $resource('sku/all').query();
							
							$scope.loadTags = function(query) {
								return $resource('tag/all').query().$promise;
							};
							
							$scope.listaStatus = [ {
								'nome' : 'A'
							}, {
								'nome' : 'B'
							}, {
								'nome' : 'C'
							} ];

							$scope.listaPlanejamentos = [ {
								'nome' : 'MANUAL'
							}, {
								'nome' : 'AUTOMATICO'
							} ];

							
							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.dfu
											.$save(
													function(response) {
														$scope.mensagem = 'Cadastro de Dfu realizado com sucesso!';
														$scope.dfu = new DfuFactory();
													},
													function(erro) {
														$scope.mensagem = 'Cadastro de Dfu não realizado!';
													});
								}
							};
							
							$scope.addRelacao = function() {
								if ($scope.modalRelacao.$valid) {
									$scope.dfu.relacaoSku
											.push($scope.selected);
									$scope.selected = {};
									alert("Adicionado com sucesso!");
								}
							};

							$scope.REST_SEARCH = 'dfu/search';
							$scope.URL_CRUD = 'dfu/:id';
							$scope.URL_FORM_CREATE = 'private/dfu/dfu-create';
							$scope.URL_FORM_EDIT = 'private/dfu/dfu-edit';

							$scope.mapColumnsRelacao = [ {
								'title' : 'ID Sku',
								'field' : 'idSku'
							}, {
								'title' : 'ID Dfu',
								'field' : 'idDfu'
							}];
							
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
								'title' : 'Classe',
								'field' : 'classe'
							}, {
								'title' : 'Planejamento Dfu',
								'field' : 'modelo'
							}, {
								'title' : 'Data de Maturidade',
								'field' : 'dataMaturidade',
								'isDate' : 'true'

							}, {
								'title' : 'Data de Lançamento',
								'field' : 'dataLancamento',
								'isDate' : 'true'

							}, {
								'title' : 'Data de Descontinuação',
								'field' : 'dataDescontinuacao',
								'isDate' : 'true'

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

app.controller('ProfileController', [
		'$resource',
		'$http',
		'$window',
		'$scope',
		'$routeParams',
		'$location',
		'UsuarioFactory',
		function($resource, $http, $window, $scope, $routeParams, $location,
				UsuarioFactory) {

			$scope.user = {
				name : 'User'
			};

			$scope.logout = function() {
				$http.post('auth/logout', {}).success(function() {
					$window.location.href = "/brm.web/";
				}).error(function(data) {
					console.log(data);
				});
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
