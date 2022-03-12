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

Brewer.MaskDate = (function(){
	
	function MaskDate(){
		this.inputDate = $('.js-date');
	}
	
	MaskDate.prototype.enable = function() {
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true
		});
	}``
	
	return MaskDate;
}());

$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
	
	var maskPhoneNumber = new Brewer.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	var maskDate = new Brewer.MaskDate();
	maskDate.enable();	
});