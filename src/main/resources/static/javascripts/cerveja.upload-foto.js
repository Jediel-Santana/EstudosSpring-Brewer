var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {
	
	function UploadFoto(){
		this.htmlFotoCervejaTemplate = $('#foto-cerveja').html();
		this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.uploadDrop = $('#upload-drop');
		this.containerFotoCerveja = $('.js-container-foto-cerveja');
	}
	
	UploadFoto.prototype.iniciar = function(){
		var settings = {
						type: 'json',
						filelimit: 1,
						allow: '*.(jpg|jpeg|png)',
						action: this.containerFotoCerveja.data('url-foto'),
						complete: onUploadComplete.bind(this),
						beforeSend: adicionarCsrfToker
						}	
		
		
		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadDrop, settings);
		
		
		if(this.inputNomeFoto.val()){
			onUploadComplete.call(this, {nome: this.inputNomeFoto.val(), contentType: this.inputContentType.val()});
		}
	}	
	
	function onUploadComplete(resposta){
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);
		
		this.uploadDrop.addClass('hidden');
		
		var htmlFotoCerveja = this.template({nomeFoto: resposta.nome});	
		
		this.containerFotoCerveja.append(htmlFotoCerveja);
		
		$('.js-remove-foto').on('click', onRemoveFoto.bind(this));
		
	}
	
	function onRemoveFoto(){
		$('.js-foto-cerveja').remove();
		this.uploadDrop.removeClass('hidden');
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
	}	
	
	function adicionarCsrfToker(xhr){
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		xhr.setRequestHeader(header, token);
	}
	
	return UploadFoto;
}());
	
$(function(){
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.iniciar();
});





