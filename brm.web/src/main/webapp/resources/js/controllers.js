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

app.controller('SkuController', [ '$scope', '$resource', '$location',
		'$timeout', 'SkuFactory',
		function($scope, $resource, $location, $timeout, SkuFactory) {
			$scope.sku = new SkuFactory();
			$scope.sku.frequenciaAnalise = [];
			$scope.sku.origens = [];
			$scope.selected = {};
			$scope.listaItens = $resource('item/all').query();

			$scope.listaSku = [];
			$scope.listaFornecedor = [];

			$scope.loadListaSku = function() {

				var sku = $resource('sku/list/:idItem', {
					idItem : '@idItem'
				});

				sku.query({
					idItem : $scope.sku.item.id
				}).$promise.then(function(data) {
					$scope.listaSku = [];
					angular.forEach(data, function(value, key) {
						if (!$scope.hasOrigem(value.id)) {
							value.showTags = "";
							var sep = "";
							angular.forEach(value.tags, function(val, keySub) {
								value.showTags += sep + val.nome;
								sep = "/";
							});
							$scope.listaSku.push(value);
						}
					});
				});
			}

			$scope.hasOrigem = function(id) {
				var existe = false;
				angular.forEach($scope.sku.origens, function(val, keySub) {
					if (val.id == id) {
						existe = true;
					}
				});
				return existe;
			};

			$scope.loadListaFornecedor = function() {
				var sku = $resource('fornecedor/all');
				sku.query().$promise.then(function(data) {
					$scope.listaFornecedor = [];
					angular.forEach(data, function(value, key) {
						if (!$scope.hasOrigem(value.id)) {
							$scope.listaFornecedor.push(value);
						}
					});
				});
			}

			$scope.selected = {
				tipo : ''
			};

			$scope.checkType = function() {
				if ($scope.selected.tipo == 'SKU') {
					$scope.loadListaSku();
				} else {
					$scope.loadListaFornecedor();
				}
			};

			$scope.definir = function(id) {
				angular.forEach($scope.sku.origens, function(value, key) {
					if (id == value.id) {
						value.padrao = true;
					} else {
						value.padrao = false;
					}
				});
			};

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

			$scope.sku.modelo = {};
			$scope.sku.modelo = $scope.listaPlanejamentos[0].nome;

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

			$scope.closeMessage = function() {
				$('#idMessage').modal('hide');
				if ($scope.trtResponse.go != null) {
					$timeout(function() {
						$location.path($scope.trtResponse.go);
					}, 700);
				}
				;
			};

			$scope.getInfo = function(tipo, id) {
				console.log(tipo);
				console.log(id);
				return "EM DESENV";
			};

			$scope.trtResponse = {};
			$scope.save = function(close) {
				if ($scope.formulario.$valid) {
					$scope.sku.$save(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/sku/sku-view';
						} else {
							url = 'private/sku/sku-edit/' + response.result.id;
						}
						$scope.trtResponse.go = url;
					}, function(response) {
						$scope.trtResponse = response.data;
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
					$scope.sku.origens.push($scope.selected);
					$scope.selected = {
						tipo : ''
					};
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

app.controller('ItemController', [
		'$scope',
		'$resource',
		'$location',
		'$timeout',
		'ItemFactory',
		function($scope, $resource, $location, $timeout, ItemFactory) {
			$scope.item = new ItemFactory();

			$scope.trtResponse = {};
			$scope.categorias = $resource('categoria/all').query();

			$scope.listaStatus = [ {
				'nome' : 'DESCONTINUADO'
			}, {
				'nome' : 'CANCELADO'
			}, {
				'nome' : 'ATIVO'
			} ];

			$scope.mensagem = '';

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
					$scope.item.$save(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/item/item-view';
						} else {
							url = 'private/item/item-edit/'
									+ response.result.id;
						}
						$scope.trtResponse.go = url;
					}, function(response) {
						$scope.trtResponse = response.data;
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

app.controller('ItemEditController', [
		'$scope',
		'$resource',
		'$routeParams',
		'$location',
		'$timeout',
		'ItemFactory',
		function($scope, $resource, $routeParams, $location, $timeout,
				ItemFactory) {
			$scope.trtResponse = {};
			$scope.categorias = $resource('categoria/all').query();

			$scope.listaStatus = [ {
				'nome' : 'DESCONTINUADO'
			}, {
				'nome' : 'CANCELADO'
			}, {
				'nome' : 'ATIVO'
			} ];

			$scope.load = function() {
				$scope.item = ItemFactory.get({
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
					$scope.item.$update(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/item/item-view';
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

app.controller('SkuEditController',
		[
				'$scope',
				'$resource',
				'$routeParams',
				'$location',
				'$timeout',
				'SkuFactory',
				'PedidoFactory',
				function($scope, $resource, $routeParams, $location, $timeout,
						SkuFactory, PedidoFactory) {

					$scope.listaSku = [];
					$scope.listaFornecedor = [];
					$scope.listaPedidos = [];

					$scope.loadPedidos = function() {
						PedidoFactory.query({
							id : $routeParams.id
						}).$promise.then(function(data) {
							console.log(data);
							$scope.listaPedidos = data;
						});
					};
					
					$scope.loadPedidos();

					$scope.createOrder = function() {
						$scope.pedido.$save(function(response) {
							$scope.trtResponse = response;
						}, function(response) {
							$scope.trtResponse = response.data;
						});
						$('#idModalPedido').modal('hide');
						$scope.resetPedido();
					};

					$scope.resetPedido = function() {
						$scope.pedido = new PedidoFactory();
						$scope.pedido.dataSolicitacao = new Date();
						$scope.pedido.origem = {
							id : $routeParams.id
						};
					};

					$scope.resetPedido();

					$scope.loadListaSku = function() {

						var sku = $resource('sku/list/:idItem', {
							idItem : '@idItem'
						});

						sku.query({
							idItem : $scope.sku.item.id
						}).$promise.then(function(data) {
							$scope.listaSku = [];
							angular.forEach(data, function(value, key) {
								if ($scope.sku.id != value.id
										&& !$scope.hasOrigem(value.id)) {
									value.showTags = "";
									var sep = "";
									angular.forEach(value.tags, function(val,
											keySub) {
										value.showTags += sep + val.nome;
										sep = "/";
									});
									$scope.listaSku.push(value);
								}
							});
						});
					}

					$scope.hasOrigem = function(id) {
						var existe = false;
						angular.forEach($scope.sku.origens, function(val,
								keySub) {
							if (val.id == id) {
								existe = true;
							}
						});
						return existe;
					};

					$scope.loadListaFornecedor = function() {
						var sku = $resource('fornecedor/all');
						sku.query().$promise.then(function(data) {
							$scope.listaFornecedor = [];
							angular.forEach(data, function(value, key) {
								if (!$scope.hasOrigem(value.id)) {
									$scope.listaFornecedor.push(value);
								}
							});
						});
					}

					$scope.selected = {
						tipo : ''
					};

					$scope.checkType = function() {
						if ($scope.selected.tipo == 'SKU') {
							$scope.loadListaSku();
						} else {
							$scope.loadListaFornecedor();
						}
					};

					$scope.listaItens = $resource('item/all').query();

					$scope.loadTags = function(query) {
						return $resource('tag/all').query().$promise;
					};

					$scope.load = function() {
						SkuFactory.get({
							id : $routeParams.id
						}).$promise.then(function(data) {
							$scope.sku = data;
							$scope.loadDaysOfWeek();
							if ($scope.sku.dataMaturidade != null) {
								$scope.sku.dataMaturidade = new Date(
										$scope.sku.dataMaturidade);
							}
							if ($scope.sku.dataDescontinuacao != null) {
								$scope.sku.dataDescontinuacao = new Date(
										$scope.sku.dataDescontinuacao);
							}

						});
					};

					$scope.diasDaSemana = [];
					$scope.loadDaysOfWeek = function() {
						$scope.diasDaSemana = [
								{
									"id" : 2,
									"value" : "Segunda",
									"checked" : $scope.sku.frequenciaAnalise
											.indexOf(2) != -1 ? true : false
								},
								{
									"id" : 3,
									"value" : "Terça",
									"checked" : $scope.sku.frequenciaAnalise
											.indexOf(3) != -1 ? true : false
								},
								{
									"id" : 4,
									"value" : "Quarta",
									"checked" : $scope.sku.frequenciaAnalise
											.indexOf(4) != -1 ? true : false
								},
								{
									"id" : 5,
									"value" : "Quinta",
									"checked" : $scope.sku.frequenciaAnalise
											.indexOf(5) != -1 ? true : false
								},
								{
									"id" : 6,
									"value" : "Sexta",
									"checked" : $scope.sku.frequenciaAnalise
											.indexOf(6) != -1 ? true : false
								},
								{
									"id" : 7,
									"value" : "Sábado",
									"checked" : $scope.sku.frequenciaAnalise
											.indexOf(7) != -1 ? true : false
								},
								{
									"id" : 1,
									"value" : "Domingo",
									"checked" : $scope.sku.frequenciaAnalise
											.indexOf(1) != -1 ? true : false
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
							$scope.sku.$update(function(response) {
								$scope.trtResponse = response;
								var url;
								if (close) {
									url = 'private/sku/sku-view';
								} else {
									$scope.load();
								}
								$scope.trtResponse.go = url;
							}, function(response) {
								$scope.trtResponse = response.data;
							});
						}
					};

					$scope.definir = function(id) {
						angular.forEach($scope.sku.origens,
								function(value, key) {
									if (id == value.id) {
										value.padrao = true;
									} else {
										value.padrao = false;
									}
								});
					};

					$scope.deleteLine = function(entry) {

						if (!confirm("Deseja excluir o registro?")) {
							return;
						}

						var index = $scope.sku.origens.indexOf(entry);

						$scope.sku.origens.splice(index, 1);

					};

					$scope.checkedOrNot = function(id, isChecked, index) {
						if (isChecked) {
							$scope.sku.frequenciaAnalise.push(id);
						} else {
							var _index = $scope.sku.frequenciaAnalise
									.indexOf(id);
							$scope.sku.frequenciaAnalise.splice(_index, 1);
						}
					};

					$scope.addOrigem = function() {
						if ($scope.modalOrigem.$valid) {
							$scope.sku.origens.push($scope.selected);
							$scope.selected = {
								tipo : ''
							};
							alert("Adicionado com sucesso!");
						}
					};
					$scope.load();
				} ]);

app.controller('TagController', [ '$scope', '$location', '$timeout',
		'TagFactory', function($scope, $location, $timeout, TagFactory) {
			$scope.mensagem = '';
			$scope.trtResponse = {};
			$scope.tag = new TagFactory();

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
					$scope.tag.$save(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/tag/tag-view';
						} else {
							url = 'private/tag/tag-edit/' + response.result.id;
						}
						$scope.trtResponse.go = url;
					}, function(response) {
						$scope.trtResponse = response.data;
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
		'$timeout', 'TagFactory',
		function($scope, $routeParams, $location, $timeout, TagFactory) {

			$scope.load = function() {
				$scope.tag = TagFactory.get({
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
					$scope.tag.$update(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/tag/tag-view';
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

app.controller('CategoriaController', [
		'$scope',
		'$location',
		'$timeout',
		'CategoriaFactory',
		function($scope, $location, $timeout, CategoriaFactory) {
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

app.controller('CategoriaEditController', [ '$scope', '$routeParams',
		'$timeout', '$location', 'CategoriaFactory',
		function($scope, $routeParams, $timeout, $location, CategoriaFactory) {
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

app.controller('GrupoController', [
		'$scope',
		'$resource',
		'$location',
		'$timeout',
		'GrupoFactory',
		function($scope, $resource, $location, $timeout, GrupoFactory) {

			$scope.mensagem = '';
			$scope.grupo = new GrupoFactory();
			$scope.selected = {};
			$scope.grupo.perfis = [];
			$scope.listaPerfis = $resource('perfil/all').query();

			$scope.addPerfil = function() {
				if ($scope.modalPerfil.$valid) {
					$scope.grupo.perfis.push($scope.selected);
					$scope.selected = {};
					alert("Adicionado com sucesso!");
				}
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
					$scope.grupo.$save(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/grupo/grupo-view';
						} else {
							url = 'private/grupo/grupo-edit/'
									+ response.result.id;
						}
						$scope.trtResponse.go = url;
					}, function(response) {
						$scope.trtResponse = response.data;
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

app.controller('GrupoEditController', [
		'$scope',
		'$resource',
		'$routeParams',
		'$location',
		'$timeout',
		'GrupoFactory',
		function($scope, $resource, $routeParams, $location, $timeout,
				GrupoFactory) {

			$scope.listaPerfis = $resource('perfil/all').query();
			$scope.selected = {};

			$scope.load = function() {
				$scope.grupo = GrupoFactory.get({
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
					$scope.grupo.$update(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/grupo/grupo-view';
						} else {
							$scope.load();
						}
						$scope.trtResponse.go = url;
					}, function(response) {
						$scope.trtResponse = response.data;
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

app.controller('UsuarioController', [
		'$scope',
		'$resource',
		'$location',
		'$timeout',
		'UsuarioFactory',
		function($scope, $resource, $location, $timeout, UsuarioFactory) {
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
					$scope.usuario.$save(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/usuario/usuario-view';
						} else {
							url = 'private/usuario/usuario-edit/'
									+ response.result.id;
						}
						$scope.trtResponse.go = url;
					}, function(response) {
						$scope.trtResponse = response.data;
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

app.controller('UsuarioEditController', [
		'$scope',
		'$resource',
		'$routeParams',
		'$location',
		'$timeout',
		'UsuarioFactory',
		function($scope, $resource, $routeParams, $location, $timeout,
				UsuarioFactory) {
			$scope.selected = {};
			$scope.listaGrupos = $resource('grupo/all').query();

			$scope.load = function() {
				$scope.usuario = UsuarioFactory.get({
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
					$scope.usuario.$update(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/usuario/usuario-view';
						} else {
							$scope.load();
						}
						$scope.trtResponse.go = url;
					}, function(response) {
						$scope.trtResponse = response.data;
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

app.controller('PerfilController', [
		'$scope',
		'$location',
		'$timeout',
		'PerfilFactory',
		function($scope, $location, $timeout, PerfilFactory) {
			$scope.mensagem = '';

			$scope.perfil = new PerfilFactory();

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
					$scope.perfil.$save(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/perfil/perfil-view';
						} else {
							url = 'private/perfil/perfil-edit/'
									+ response.result.id;
						}
						$scope.trtResponse.go = url;
					}, function(response) {
						$scope.trtResponse = response.data;
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

app.controller('PerfilEditController', [ '$scope', '$routeParams', '$location',
		'$timeout', 'PerfilFactory',
		function($scope, $routeParams, $location, $timeout, PerfilFactory) {

			$scope.load = function() {
				$scope.perfil = PerfilFactory.get({
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
					$scope.perfil.$update(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/perfil/perfil-view';
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

app.controller('DfuController', [ '$scope', '$resource', '$location',
		'$timeout', 'DfuFactory',
		function($scope, $resource, $location, $timeout, DfuFactory) {
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
					$scope.dfu.$save(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/dfu/dfu-view';
						} else {
							url = 'private/dfu/dfu-edit/' + response.result.id;
						}
						$scope.trtResponse.go = url;
					}, function(response) {
						$scope.trtResponse = response.data;
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

app.controller('DfuEditController', [
		'$scope',
		'$resource',
		'$routeParams',
		'$location',
		'$timeout',
		'DfuFactory',
		function($scope, $resource, $routeParams, $location, $timeout,
				DfuFactory) {

			$scope.load = function() {
				DfuFactory.get({
					id : $routeParams.id
				}, function(response) {

					$scope.dfu = response;
					if ($scope.dfu.dataMaturidade != null) {
						console.log($scope.dfu.dataMaturidade);
						$scope.dfu.dataMaturidade = new Date(
								$scope.dfu.dataMaturidade);
					}

					if ($scope.dfu.dataLancamento != null) {
						$scope.dfu.dataLancamento = new Date(
								$scope.dfu.dataLancamento);
					}
					if ($scope.dfu.dataDescontinuacao != null) {
						$scope.dfu.dataDescontinuacao = new Date(
								$scope.dfu.dataDescontinuacao);
					}
					if ($scope.dfu.validadeModelo != null) {
						$scope.dfu.validadeModelo = new Date(
								$scope.dfu.validadeModelo);
					}
					if ($scope.dfu.validadePrimeiraSaida != null) {
						$scope.dfu.validadePrimeiraSaida = new Date(
								$scope.dfu.validadePrimeiraSaida);
					}
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
					$scope.dfu.$update(function(response) {
						$scope.trtResponse = response;
						var url;
						if (close) {
							url = 'private/dfu/dfu-view';
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
