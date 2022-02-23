var Brewer = Brewer || {};

Brewer.MaskMoney = (function() {


	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}

	MaskMoney.prototype.enable = function() {
		this.decimal.maskMoney({ thousands: '.', decimal: ',' });
		this.plain.maskMoney({ precision: 0, decimal: ',', thousands: '.' });
	}




	return MaskMoney;
}());

Brewer.MaskPhoneNumber = (function() {

	function MaskPhoneNumber() {
		this.input = $('.js-phone');
	}

	MaskPhoneNumber.prototype.enable = function() {
		var SPMaskBehavior = function(val) {
			return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		};
		
		var	spOptions = {
				onKeyPress: function(val, e, field, options) {
					field.mask(SPMaskBehavior.apply({}, arguments), options);
				}
			};
		
		this.input.mask(SPMaskBehavior, spOptions);
	}

	return MaskPhoneNumber;
}());


$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
	
	
	var maskPhoneNumber = new Brewer.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
});