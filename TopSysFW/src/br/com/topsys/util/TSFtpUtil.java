package br.com.topsys.util;

import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public final class TSFtpUtil {

	public static boolean enviar(String servidor, String usuario, String senha, String nomeArquivo, InputStream arquivo, String pasta) {

		FTPClient ftp = new FTPClient();

		try {

			ftp.connect(servidor);

			if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {

				ftp.login(usuario, senha);

			} else {

				ftp.disconnect();

				System.exit(1);

			}

			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

			ftp.changeWorkingDirectory(pasta);

			ftp.storeFile(nomeArquivo, arquivo);

			ftp.disconnect();

			return true;

		} catch (Exception e) {

			System.exit(1);

			return false;

		}

	}

}