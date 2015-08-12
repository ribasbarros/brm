'use strict';

/* Directives */

var app = angular.module('brm.directives', []);

app.directive('appVersion', [ 'version', function(version) {
	return function(scope, elm, attrs) {
		elm.text(version);
	};
} ]);

app.directive('validNumber', function() {
	return {
		require : '?ngModel',
		link : function(scope, element, attrs, ngModelCtrl) {
			if (!ngModelCtrl) {
				return;
			}

			ngModelCtrl.$parsers.push(function(val) {
				if (val != null) {
					var clean = val.replace(/[^0-9]+/g, '');
				}
				if (val !== clean) {
					ngModelCtrl.$setViewValue(clean);
					ngModelCtrl.$render();
				}
				return clean;
			});

			element.bind('keypress', function(event) {
				if (event.keyCode === 32) {
					event.preventDefault();
				}
			});

		}
	};
});

app.directive('validDecimal', function() {
	return {
		require : '?ngModel',
		link : function(scope, element, attrs, ngModelCtrl) {
			if (!ngModelCtrl) {
				return;
			}

			ngModelCtrl.$parsers.push(function(val) {
				if (val != null) {
					var clean = val.replace(",", "").replace(".", "");
					if (val != null) {
						var clean = val.replace(/[^0-9]+/g, '');
					}
					if (clean.indexOf("0") == 0) {
						clean = clean.substring(1, clean.length);
					}

					if (clean.indexOf("0") == 0) {
						clean = clean.substring(1, clean.length);
					}

					if (clean.length == 1) {
						clean = "00" + clean;
					}

					if (clean.length == 2) {
						clean = "0" + clean;
					}

					if (clean != "") {
						clean = clean.substring(0, clean.length - 2)
								+ ","
								+ clean.substring(clean.length - 2,
										clean.length)

						var inverse = [];
						for (var i = clean.length - 3; i >= 0; i--) {
							inverse[i - (clean.length - 3)] = clean.substring(
									i - 1, i);
						}

						if (inverse.size > 3) {

						}

					}

				}

				if (val !== clean) {
					ngModelCtrl.$setViewValue(clean);
					ngModelCtrl.$render();
				}

				return clean;
			});

			element.bind('keypress', function(event) {
				if (event.keyCode === 32) {
					event.preventDefault();
				}
			});
			
		}
	};
});

app.directive('validCpf', function() {

	var messageError = {};
	
	var messageFail = function() {
		messageError = {
			type : 'danger',
			text : 'Por favor, digite um cpf valido.',
			show : true
		};
	};

	return {
		require : '?ngModel',
		link : function(scope, element, attrs, ngModelCtrl) {
			if (!ngModelCtrl) {
				return;
			}

			ngModelCtrl.$parsers.push(function(val) {
				if (val != null) {
					var clean = val.replace(/[^0-9]+/g, '');
				}
				if (val !== clean) {
					ngModelCtrl.$setViewValue(clean);
					ngModelCtrl.$render();
				}
				return clean;
			});

			element.bind('keypress', function(event) {
				if (event.keyCode === 32) {
					event.preventDefault();
				}
			});

			element.bind('blur', function(event) {

				var cpf = element.val();
				cpf = cpf.replace(/[^\d]+/g, '');
				if (cpf == '')
					ngModelCtrl.$setViewValue('');
				if (cpf.length != 11 || cpf == "00000000000"
						|| cpf == "11111111111" || cpf == "22222222222"
						|| cpf == "33333333333" || cpf == "44444444444"
						|| cpf == "55555555555" || cpf == "66666666666"
						|| cpf == "77777777777" || cpf == "88888888888"
						|| cpf == "99999999999") {
					ngModelCtrl.$setViewValue('');
					messageFail();
					ngModelCtrl.$render();
					return;
				}
				var add = 0;
				for (var i = 0; i < 9; i++)
					add += parseInt(cpf.charAt(i)) * (10 - i);
				var rev = 11 - (add % 11);
				if (rev == 10 || rev == 11)
					rev = 0;
				if (rev != parseInt(cpf.charAt(9))) {
					ngModelCtrl.$setViewValue('');
					messageFail();
					ngModelCtrl.$render();
					return;
				}
				add = 0;
				for (i = 0; i < 10; i++)
					add += parseInt(cpf.charAt(i)) * (11 - i);
				rev = 11 - (add % 11);
				if (rev == 10 || rev == 11)
					rev = 0;
				if (rev != parseInt(cpf.charAt(10))) {
					ngModelCtrl.$setViewValue('');
					messageFail();
					ngModelCtrl.$render();
					return;
				}

				ngModelCtrl.$render();
				return true;

			});
		}
	};
});

app.directive('fileUpload', function () {
    return {
        scope: true,        //create a new scope
        link: function (scope, el, attrs) {
            el.bind('change', function (event) {
                var files = event.target.files;
                //iterate files since 'multiple' may be specified on the element
                for (var i = 0;i<files.length;i++) {
                    //emit event upward
                    scope.$emit("fileSelected", { file: files[i] });
                }                                       
            });
        }
    };
});