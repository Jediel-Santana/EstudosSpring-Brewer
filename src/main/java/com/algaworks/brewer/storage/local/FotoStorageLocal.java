package com.algaworks.brewer.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {

	private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);

	private Path local;

	private Path localTemporario;

	public FotoStorageLocal() {
		this(getDefault().getPath(System.getenv("HOME"), ".brewerfotos"));
	}

	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}

	private void criarPastas() {
		try {
			Path dirLocal = Files.createDirectories(local);
			System.out.println(dirLocal.toString());
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(localTemporario);
			if (logger.isDebugEnabled()) {
				logger.debug("Pasta criadas!");
			}

		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		if (Objects.nonNull(files) && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(localTemporario.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome));
			} catch (Exception e) {
				throw new RuntimeException("Erro salvando a foto na pasta temporária!", e);
			}

		}
		return novoNome;
	}

	private String renomearArquivo(String originalFilename) {
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		String nomeAlteradoArquivo = UUID.randomUUID().toString() + "_" + originalFilename;
		return nomeAlteradoArquivo;
	}

	@Override
	public byte[] recuperarFotoTemporaria(String nome) {
		try {
			System.out.println(this.localTemporario.resolve(nome));
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto temporária!");
		}
	}

	@Override
	public void salvar(String foto) {

		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao salvar foto!", e);
		}

		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(40, 70).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao redimensionar foto!", e);
		}

	}

	@Override
	public byte[] recuperar(String nome) {
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto!");
		}
	}

}
