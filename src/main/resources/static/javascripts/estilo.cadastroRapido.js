
var Brewer = Brewer || {};

Brewer.CadastroEstiloRapido = (function(){
	
	
	
	function CadastroEstiloRapido(){
		this.modal = $('#modalCadastroRapidoEstilo');
		this.form = this.modal.find('form');
		this.botaoSalvar = this.modal.find('.js-modal-cadastro-estilo-salvar-btn');
		this.url = this.form.attr('action');
		this.inputNomeEstilo = $('#nomeEstilo');
		this.containerMensagemErroEstilo = $('.js-mensagem-cadastro-rapido-estilo');
	}
	
	CadastroEstiloRapido.prototype.iniciar = function(){
		this.form.on('submit', function(event) {event.preventDefault()});
		this.modal.on('shown.bs.modal', onShowModal.bind(this));	
		this.modal.on('hide.bs.modal', onCloseModal.bind(this));
		this.botaoSalvar.on('click', onBotaoSalvarEstilo.bind(this));	
	}	
	
	function onShowModal(){
		this.inputNomeEstilo.focus();
	}
	
	function onCloseModal(){
		this.inputNomeEstilo.val('');
		this.containerMensagemErroEstilo.addClass('hidden');
		this.form.find('.form-group').removeClass('has-error');
	}
	
	function onBotaoSalvarEstilo(){
		var nomeEstilo = this.inputNomeEstilo.val();
		$.ajax({ url: this.url,
				 method: 'POST',
				 contentType: 'application/json',
			     data: JSON.stringify({ 'nome': nomeEstilo}),
				 error: onErroSalvandoEstilo.bind(this),
				 success: onSucessSalvandoEstilo.bind(this)
			}); 
		
		}
		
	function onErroSalvandoEstilo(obj){
		var mensagemErro = obj.responseText;
		this.containerMensagemErroEstilo.removeClass('hidden');
		this.containerMensagemErroEstilo.html('<span>' + mensagemErro + '</span>');
		this.form.find('.form-group').addClass('has-error');
	}
	
	function onSucessSalvandoEstilo(estilo){
		var comboEstilo = $('#estilo');
		comboEstilo.append('<option value='+ estilo.codigo + '>' + estilo.nome + '</option>' )
		comboEstilo.val(estilo.codigo);
		this.modal.modal('hide');
	}
	
	
	return CadastroEstiloRapido;
}());

$(function(){
	var cadastroEstiloRapido = new Brewer.CadastroEstiloRapido();
	cadastroEstiloRapido.iniciar();
			
	});
		