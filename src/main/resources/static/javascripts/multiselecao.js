Brewer = Brewer || {};


Brewer.MultiSelecao = (function() {

	function MultiSelecao() {
		this.botaoStatus = $('.js-status-btn');
		this.checkSelecao = $('.js-selecao');
	}

	MultiSelecao.prototype.iniciar = function() {
		this.botaoStatus.on('click', botaoStatusClicado.bind(this))
	}

	function botaoStatusClicado(event) {
		var botaoClicado = $(event.currentTarget);
		var status = botaoClicado.data('status')

		var checkboxSelecionados = this.checkSelecao.filter(':checked');
		var codigos = $.map(checkboxSelecionados, function(c) {
			return $(c).data('codigo');
		});

		if (codigos.length > 0) {
			$.ajax({
				url: '/brewer/usuarios/status',
				method: 'PUT',
				data: {
					codigos: codigos,
					status: status
				}, success: function(){
					window.location.reload();
				}
			});
		}

	}

	return MultiSelecao;
}());

$(function(){
	var multiSelecao = new Brewer.MultiSelecao();
	multiSelecao.iniciar();
});