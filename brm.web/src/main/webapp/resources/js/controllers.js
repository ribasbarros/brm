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
							$scope.listaFornecedor = $resource('fornecedor/all')
									.query();

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
									var _index = $scope.sku.frequenciaAnalise
											.indexOf(id);
									$scope.sku.frequenciaAnalise.splice(_index,
											1);
								}
							};

							$scope.addOrigem = function() {
								if ($scope.modalOrigem.$valid) {
									$scope.sku.origens.push($scope.selected);
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
							} ];

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
								'title' : 'Data Maturidade',
								'field' : 'dataMaturidade',
								'isDate' : 'true'
							}, {
								'title' : 'Criado por',
								'field' : 'usuarioCriacao',
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
									console.log($scope.item);
									$scope.item
											.$save(

													function(response) {
														$scope.mensagem = 'Cadastro de Item realizado com sucesso!';
														$scope.item = new ItemFactory();

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
								'title' : 'Criado por',
								'field' : 'usuarioCriacao',
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

app.controller('ItemEditController', [ '$scope', '$resource', '$routeParams',
		'$location', 'ItemFactory',
		function($scope, $resource, $routeParams, $location, ItemFactory) {

			$scope.item = ItemFactory.get({
				id : $routeParams.id
			});

			$scope.categorias = $resource('categoria/all').query();

			$scope.listaStatus = [ {
				'nome' : 'DESCONTINUADO'
			}, {
				'nome' : 'CANCELADO'
			}, {
				'nome' : 'ATIVO'
			} ];

			$scope.submeter = function() {
				if ($scope.formulario.$valid) {
					console.log($scope.item);
					$scope.item.$update(function() {
						$scope.mensagem = 'Item Atualizado com sucesso!';
					}, function(erro) {
						$scope.mensagem = 'Alteração de Item não realizada!';
					});
				}
			};

		} ]);

app.controller('FornecedorController', [
		'$scope',
		'$location',
		'$timeout',
		'FornecedorFactory',
		function($scope, $location, $timeout, FornecedorFactory) {

			$scope.selected = {};

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
					$scope.fornecedor.contatos.push($scope.selected);
					$scope.selected = {};
					alert("Adicionado com sucesso!");
				}
			};

			$scope.addCentro = function() {
				if ($scope.modalCentro.$valid) {
					$scope.fornecedor.centros.push($scope.selected);
					$scope.selected = {};
					alert("Adicionado com sucesso!");
				}
			};


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
			}, {
				'title' : 'Criado por',
				'field' : 'usuarioCriacao',
				'subField' : 'nome'
			}, {
				'title' : 'Criação',
				'field' : 'dataCriacao',
				'isDate' : 'true'
			}, {
				'title' : 'Alterado',
				'field' : 'dataAlteracao',
				'isDate' : 'true'
			} ];

			$scope.closeMessage = function() {
				$('#idMessage').modal('hide');
				if ($scope.trtResponse.go != null) {
					$timeout(function() {
						$location.path($scope.trtResponse.go);
					}, 700);
				}
				;
			};

			$scope.trtResponse = {};
			$scope.save = function(close) {
				if ($scope.formulario.$valid) {
					$scope.fornecedor.$save(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/fornecedor/fornecedor-view';
						} else {
							url = 'private/fornecedor/fornecedor-edit/'
									+ response.result.id;
						}
						$scope.trtResponse.go = url;
					}, function(response) {
						$scope.trtResponse = response.data;
					});
				}
			};

			$scope.REST_SEARCH = 'fornecedor/search';
			$scope.URL_CRUD = 'fornecedor/:id';
			$scope.URL_FORM_CREATE = 'private/fornecedor/fornecedor-create';
			$scope.URL_FORM_EDIT = 'private/fornecedor/fornecedor-edit';


		} ]);

app.controller('FornecedorEditController', [
		'$scope',
		'$routeParams',
		'$location',
		'$route',
		'$timeout',
		'FornecedorFactory',
		function($scope, $routeParams, $location, $route, $timeout,
				FornecedorFactory) {
			$scope.selected = {};

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
					$scope.fornecedor.contatos.push($scope.selected);
					$scope.selected = {};
					alert("Adicionado com sucesso!");
				}
			};

			$scope.addCentro = function() {
				if ($scope.modalCentro.$valid) {
					$scope.fornecedor.centros.push($scope.selected);
					$scope.selected = {};
					alert("Adicionado com sucesso!");
				}
			};

			$scope.load = function() {
				$scope.fornecedor = FornecedorFactory.get({
					id : $routeParams.id
				});
			};

			$scope.closeMessage = function() {
				$('#idMessage').modal('hide');
				if ($scope.trtResponse.go != null) {
					$timeout(function() {
						$location.path($scope.trtResponse.go);
					}, 700);
				}
				;
			};

			$scope.trtResponse = {};
			$scope.save = function(close) {
				if ($scope.formulario.$valid) {
					$scope.fornecedor.$update(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/fornecedor/fornecedor-view';
						} else {
							$scope.load();
						}
						$scope.trtResponse.go = url;
					}, function(response) {
						$scope.trtResponse = response.data;
					});
				}
			};

			$scope.load();

		} ]);

app
		.controller(
				'SkuEditController',
				[
						'$scope',
						'$resource',
						'$routeParams',
						'$location',
						'SkuFactory',
						function($scope, $resource, $routeParams, $location,
								SkuFactory) {
							$scope.listaItens = $resource('item/all').query();
							$scope.listaSku = $resource('sku/all').query();
							$scope.listaFornecedor = $resource('fornecedor/all')
									.query();
							$scope.loadTags = function(query) {
								return $resource('tag/all').query().$promise;
							};

							$scope.load = function() {
								SkuFactory.get({
									id : $routeParams.id
								}).$promise.then(function(data) {
									$scope.sku = data;
									$scope.loadDaysOfWeek();
									$scope.sku.dataMaturidade = new Date($scope.sku.dataMaturidade);
									$scope.sku.dataDescontinuacao = new Date($scope.sku.dataDescontinuacao);
								});
							};

							$scope.diasDaSemana = [];
							$scope.loadDaysOfWeek = function() {
								$scope.diasDaSemana = [
										{
											"id" : 2,
											"value" : "Segunda",
											"checked" : $scope.sku.frequenciaAnalise
													.indexOf(2) != -1 ? true
													: false
										},
										{
											"id" : 3,
											"value" : "Terça",
											"checked" : $scope.sku.frequenciaAnalise
													.indexOf(3) != -1 ? true
													: false
										},
										{
											"id" : 4,
											"value" : "Quarta",
											"checked" : $scope.sku.frequenciaAnalise
													.indexOf(4) != -1 ? true
													: false
										},
										{
											"id" : 5,
											"value" : "Quinta",
											"checked" : $scope.sku.frequenciaAnalise
													.indexOf(5) != -1 ? true
													: false
										},
										{
											"id" : 6,
											"value" : "Sexta",
											"checked" : $scope.sku.frequenciaAnalise
													.indexOf(6) != -1 ? true
													: false
										},
										{
											"id" : 7,
											"value" : "Sábado",
											"checked" : $scope.sku.frequenciaAnalise
													.indexOf(7) != -1 ? true
													: false
										},
										{
											"id" : 1,
											"value" : "Domingo",
											"checked" : $scope.sku.frequenciaAnalise
													.indexOf(1) != -1 ? true
													: false
										} ];
							};

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
											.$update(
													function() {
														$scope.mensagem = 'Sku Atualizado com sucesso!';
													},
													function(erro) {
														$scope.mensagem = 'Alteração de Sku não realizada!';
													});
								}
							};

							$scope.checkedOrNot = function(id, isChecked, index) {
								if (isChecked) {
									$scope.sku.frequenciaAnalise.push(id);
								} else {
									var _index = $scope.sku.frequenciaAnalise
											.indexOf(id);
									$scope.sku.frequenciaAnalise.splice(_index,
											1);
								}
							};

							$scope.addOrigem = function() {
								if ($scope.modalOrigem.$valid) {
									$scope.sku.origens.push($scope.selected);
									$scope.selected = {};
									alert("Adicionado com sucesso!");
								}
							};

							$scope.mapColumnsOrigem = [ {
								'title' : 'Tipo',
								'field' : 'tipo'
							}, {
								'title' : 'ID',
								'field' : 'id'
							} ];

							$scope.load();
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
								'title' : 'Criado por',
								'field' : 'usuarioCriacao',
								'subField' : 'nome'
							}, {
								'title' : 'Criação',
								'field' : 'dataCriacao',
								'isDate' : 'true'
							}, {
								'title' : 'Alterado',
								'field' : 'dataAlteracao',
								'isDate' : 'true'
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
						'$timeout',
						'CategoriaFactory',
						function($scope, $location,$timeout, CategoriaFactory) {
							$scope.mensagem = '';
							$scope.categoria = new CategoriaFactory();
							$scope.trtResponse = {};
							
							$scope.closeMessage = function() {
								$('#idMessage').modal('hide');
								if ($scope.trtResponse.go != null) {
									$timeout(function() {
										$location.path($scope.trtResponse.go);
									}, 700);
								}
								;
							};

							$scope.save = function(close) {
								if ($scope.formulario.$valid) {
									$scope.categoria.$save(function(response) {
										$scope.trtResponse = response;
										var url;
										if (close) {
											url = 'private/categoria/categoria-view';
										} else {
											url = 'private/categoria/categoria-edit/'
													+ response.result.id;
										}
										$scope.trtResponse.go = url;
									}, function(response) {
										$scope.trtResponse = response.data;
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
								'title' : 'Criado por',
								'field' : 'usuarioCriacao',
								'subField' : 'nome'
							}, {
								'title' : 'Criação',
								'field' : 'dataCriacao',
								'isDate' : 'true'
							}, {
								'title' : 'Alterado',
								'field' : 'dataAlteracao',
								'isDate' : 'true'
							} ];

						} ]);

app
		.controller(
				'CategoriaEditController',
				[
						'$scope',
						'$routeParams',
						'$timeout',
						'$location',
						'CategoriaFactory',
						function($scope, $routeParams, $timeout, $location,
								CategoriaFactory) {
							$scope.trtResponse = {};

							$scope.load = function() {
								$scope.categoria = CategoriaFactory.get({
									id : $routeParams.id
								});
							};

							
							$scope.closeMessage = function() {
								$('#idMessage').modal('hide');
								if ($scope.trtResponse.go != null) {
									$timeout(function() {
										$location.path($scope.trtResponse.go);
									}, 700);
								}
								;
							};

							$scope.save = function(close) {
								if ($scope.formulario.$valid) {
									$scope.categoria.$update(function(response) {
										$scope.trtResponse = response;
										var url;
										if (close) {
											url = 'private/categoria/categoria-view';
										} 
										$scope.categoria = response.result;
										$scope.trtResponse.go = url;
									}, function(response) {
										$scope.trtResponse = response.data;
									});
								}
							};
							
							$scope.load();

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
							}, {
								'title' : 'Criado por',
								'field' : 'usuarioCriacao',
								'subField' : 'nome'
							}, {
								'title' : 'Criação',
								'field' : 'dataCriacao',
								'isDate' : 'true'
							}, {
								'title' : 'Alterado',
								'field' : 'dataAlteracao',
								'isDate' : 'true'
							} ];
						} ]);

app
		.controller(
				'GrupoEditController',
				[
						'$scope',
						'$resource',
						'$routeParams',
						'$location',
						'GrupoFactory',
						function($scope, $resource, $routeParams, $location,
								GrupoFactory) {

							$scope.listaPerfis = $resource('perfil/all')
									.query();
							$scope.selected = {};

							$scope.load = function() {
								$scope.grupo = GrupoFactory.get({
									id : $routeParams.id
								});
							};

							$scope.submeter = function() {
								if ($scope.formulario.$valid) {
									$scope.grupo
											.$update(
													function() {
														$scope.mensagem = 'Alteração de Grupo realizada com sucesso!';
													},
													function(erro) {
														$scope.mensagem = 'Alteração de Grupo não realizada!';
													});
								}
							};

							$scope.addPerfil = function() {
								if ($scope.modalPerfil.$valid) {
									$scope.grupo.perfis.push($scope.selected);
									$scope.selected = {};
									alert("Adicionado com sucesso!");
								}
							};

							$scope.mapColumnsPerfil = [ {
								'title' : 'Nome',
								'field' : 'nome'
							} ];

							$scope.load();

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
														$scope.usuario = new UsuarioFactory();

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
							}, {
								'title' : 'Criado por',
								'field' : 'usuarioCriacao',
								'subField' : 'nome'
							}, {
								'title' : 'Criação',
								'field' : 'dataCriacao',
								'isDate' : 'true'
							}, {
								'title' : 'Alterado',
								'field' : 'dataAlteracao',
								'isDate' : 'true'
							} ];
						} ]);

app
		.controller(
				'UsuarioEditController',
				[
						'$scope',
						'$resource',
						'$routeParams',
						'$location',
						'UsuarioFactory',
						function($scope, $resource, $routeParams, $location,
								UsuarioFactory) {
							$scope.selected = {};
							$scope.listaGrupos = $resource('grupo/all').query();

							$scope.load = function() {
								$scope.usuario = UsuarioFactory.get({
									id : $routeParams.id
								});
							};
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

							$scope.addGrupo = function() {
								if ($scope.modalGrupo.$valid) {

									$scope.usuario.grupos.push($scope.selected);
									$scope.selected = {};
									alert("Adicionado com sucesso!");
								}
							};

							$scope.mapColumnsGrupo = [ {
								'title' : 'Nome',
								'field' : 'nome'
							} ];

							$scope.load();
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
								'title' : 'Criado por',
								'field' : 'usuarioCriacao',
								'subField' : 'nome'
							}, {
								'title' : 'Criação',
								'field' : 'dataCriacao',
								'isDate' : 'true'
							}, {
								'title' : 'Alterado',
								'field' : 'dataAlteracao',
								'isDate' : 'true'
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
									console.log($scope.dfu);
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
									$scope.dfu.relacaoSku.push($scope.selected);
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
							} ];

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

							}, {
								'title' : 'Criado por',
								'field' : 'usuarioCriacao',
								'subField' : 'nome'
							}, {
								'title' : 'Criação',
								'field' : 'dataCriacao',
								'isDate' : 'true'
							}, {
								'title' : 'Alterado',
								'field' : 'dataAlteracao',
								'isDate' : 'true'
							} ];

						} ]);

app.controller('DfuEditController', [ '$scope', '$resource', '$routeParams',
		'$location', 'DfuFactory',
		function($scope, $resource, $routeParams, $location, DfuFactory) {

			$scope.load = function() {
				DfuFactory.get({
					id : $routeParams.id
				}, function(response){
					
					$scope.dfu = response;
					
					$scope.dfu.dataMaturidade = new Date($scope.dfu.dataMaturidade);
					$scope.dfu.dataLancamento = new Date($scope.dfu.dataLancamento);
					$scope.dfu.dataDescontinuacao = new Date($scope.dfu.dataDescontinuacao);
					$scope.dfu.validadeModelo = new Date($scope.dfu.validadeModelo);
					$scope.dfu.validadePrimeiraSaida = new Date($scope.dfu.validadePrimeiraSaida);
				});
				
			};

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
					$scope.dfu.$update(function() {
						$scope.mensagem = 'DFU Atualizado com sucesso!';
					}, function(erro) {
						$scope.mensagem = 'Alteração de DFU não realizada!';
					});
				}
			};
			
			$scope.load();

		} ]);

var userLogado = {};

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

			$scope.user = $resource('usuario/user').get();

			userLogado = $scope.user;

			console.log($scope.user);

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

			$scope.credentials = {};

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
