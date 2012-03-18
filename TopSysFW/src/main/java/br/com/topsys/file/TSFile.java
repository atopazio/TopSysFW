package br.com.topsys.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;



import br.com.topsys.constant.TSConstant;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSLogUtil;
import br.com.topsys.util.TSUtil;

/**
 * Essa Classe � usada para manipular arquivos. Ex: Para escrever no arquivo: <br>
 * TSFile file = new TSFile(); file.save("/home/topazio/teste.txt","Boas ondas
 * no litoral!"); <br>
 * OU <br>
 * TSFile file = new TSFile(); file.write("Bom Surf para todos!!!!");
 * file.write("Ondas de 3 metros"); file.save("/home/topazio/teste.txt"); <br>
 * Para ler um arquivo: <br>
 * TSFile file = new TSFile(); file.openFile("/home/topazio/teste.txt"); String
 * texto = file.readAllFile(); <br>
 * 
 * @author Andr� Top�zio
 */
public final class TSFile {
	private BufferedReader reader;

	private PrintWriter out;

	private StringBuffer string;

	private String arquivo;

	/**
	 * 
	 */
	public TSFile() {
		string = new StringBuffer();
	}

	/**
	 * @deprecated
	 * @param file
	 */
	public void setFile(String file) {
		this.arquivo = file;
	}

	/**
	 * Esse metodo serve para escrever no arquivo.
	 * 
	 * @param texto
	 */
	public void write(String texto) {
		string.append(texto);
	}

	/**
	 * Esse metodo ser� usado para gravar o arquivo passando como parametro o
	 * caminho fisico do arquivo.
	 * 
	 * @param arquivo
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public void save(String arquivo) {
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(arquivo)));
			out.println(string.toString());
			out.close();
		} catch (IOException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

	}

	/**
	 * Metodo para salvar um arquivo, passando como parametros o caminho fisico
	 * do arquivo mais o conteudo que o arquivo ir� conter.
	 * 
	 * @param arquivo
	 * @param texto
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public void save(String arquivo, String texto) {
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(arquivo)));
			out.println(texto);
			out.close();
		} catch (IOException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}
	}

	/**
	 * Metodo para salvar um arquivo.
	 * 
	 * @deprecated
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public void save() {
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(arquivo)));
			out.println(string.toString());
			out.close();
		} catch (IOException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

	}

	/**
	 * Metodo para abrir um arquivo.
	 * 
	 * @param file
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public void openFile(String file) {
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}
	}

	/**
	 * Metodo para ler o arquivo.
	 * 
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public String readAllFile() {
		StringBuffer string = new StringBuffer();

		try {
			while (reader.ready()) {
				string.append(reader.readLine());
				string.append("\n");
			}
		} catch (IOException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}
		return string.toString();

	}

	public String formatarSQL() {
		StringBuffer string = new StringBuffer();
		String linha = null;
		try {
			while (reader.ready()) {
				linha = reader.readLine().trim();
				if (!TSUtil.isEmpty(linha)) {
					string.append(linha);
					string.append(" ");
				}
			}
		} catch (IOException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}
		return string.toString();

	}

	public String desFormatarSQL() {

		String linha = null;
		try {
			if (reader.ready()) {
				linha = reader.readLine().trim();
			}
			if (!TSUtil.isEmpty(linha)) {
				linha = linha.replaceAll("SELECT", "\nSELECT");
				linha = linha.replaceAll("UNION", "\nUNION");
				linha = linha.replaceAll("AND", "\nAND");
				linha = linha.replaceAll("WHERE", "\nWHERE");
				linha = linha.replaceAll("FROM", "\nFROM");
			}
		} catch (IOException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}
		return linha;
	}

	/**
	 * Esse metodo serve para copiar um ou mais arquivos para outro diretorio.
	 * Exemplo: copy(new
	 * String[]{"/home/andre/teste.txt","/home/andre/teste2.txt"
	 * },"/home/ariella/");
	 * 
	 * @param filenames
	 * @param dest
	 * @throws TSApplicationException
	 */
	public void copy(String[] filenames, String dest) throws TSApplicationException {
		String[] fileNamesNovo = new String[filenames.length];
		FileOutputStream fout = null;
		FileInputStream fin = null;
		// Compress the files
		try {
			for (int i = 0; i < filenames.length; i++) {

				String novoArquivo = filenames[i].substring(filenames[i].lastIndexOf("/") + 1);
				fileNamesNovo[i] = dest + novoArquivo;

				// Cria a stream para ler o arquivo original
				fin = new FileInputStream(filenames[i]);
				fout = new FileOutputStream(fileNamesNovo[i]);

				// Cria a stream para gravar o arquivo de c�pia

				// Usa as streams para criar os canais correspondentes
				FileChannel in = fin.getChannel();
				FileChannel outChannel = fout.getChannel();

				// N�mero de bytes do arquivo original
				long numbytes = in.size();

				// Transfere todo o volume para o arquivo de c�pia.
				in.transferTo(0, numbytes, outChannel);

			}
		} catch (FileNotFoundException e) {
			throw new TSApplicationException(TSConstant.MENSAGEM_FILE_NOT_FOUND);
		} catch (IOException e) {
			throw new TSApplicationException(TSConstant.MENSAGEM_ERRO_ENTRADA_SAIDA);
		}

	}

	/**
	 * Esse metodo serve para compactar arquivos. Exemplo: compactar(new
	 * String[]{"/home/andre/teste1.txt" , "/home/andre/teste2.txt"} ,
	 * "/home/andre/testeZipado.zip");
	 * 
	 * @param arquivos
	 * @param arquivo
	 * @throws TSApplicationException
	 */
	public void compactar(String[] filenames, String outFilename) throws TSApplicationException {

		// Create a buffer for reading the files
		byte[] buf = new byte[1024];

		// Create the ZIP file

		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(outFilename));
			FileInputStream in = null;

			for (int i = 0; i < filenames.length; i++) {
				try {
					// Add ZIP entry to output stream.
					in = new FileInputStream(filenames[i]);
					out.putNextEntry(new ZipEntry(filenames[i]));

					// Transfer bytes from the file to the ZIP file

					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
				} finally {
					try {
						out.closeEntry();
					} catch (Exception e) {
					}
					try {
						in.close();
					} catch (Exception e) {
					}
				}
				// Complete the entry

			}

			// Complete the ZIP file

		} catch (FileNotFoundException e) {
			throw new TSApplicationException(TSConstant.MENSAGEM_FILE_NOT_FOUND);
		} catch (IOException e) {
			throw new TSApplicationException(TSConstant.MENSAGEM_ERRO_ENTRADA_SAIDA);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}

	}

	/**
	 * Este método serve para transformar um dado vindo do campo Blob do banco
	 * em um aquivo no caminho e com o nome especificado em pathFileName
	 * 
	 * @author Henrique Machado
	 * @param blob
	 * @param pathFileName
	 * @throws TSApplicationException
	 */
	public static void blobToFile(Blob blob, String pathFileName) throws TSApplicationException {

		InputStream blobStream = null;
		FileOutputStream fileOutStream = null;
		try {

			blobStream = blob.getBinaryStream();

			// Open a file stream to save the Blob data
			fileOutStream = new FileOutputStream(pathFileName);

			// buffer holding bytes to be transferred
			byte[] buffer = new byte[10];
			int nbytes = 0; // Number of bytes read

			// Read from the Blob data input stream, and write to the
			// file output stream
			while ((nbytes = blobStream.read(buffer)) != -1) { // Read from
				// Blob stream
				fileOutStream.write(buffer, 0, nbytes); // Write to file stream
			}

		} catch (FileNotFoundException e) {
			throw new TSApplicationException(e);
		} catch (Exception e) {
			throw new TSSystemException(e);
		} finally {
			try {
				blobStream.close();
			} catch (Exception e) {
			}
			try {
				fileOutStream.close();
			} catch (Exception e) {
			}

		}

	}

	public static void inputStreamToFile(InputStream byteStream, String pathFileName) throws TSApplicationException {

		InputStream bStream = null;
		FileOutputStream fileOutStream = null;
		try {

			bStream = byteStream;

			fileOutStream = new FileOutputStream(pathFileName);

			byte[] buffer = new byte[10];
			int nbytes = 0; // Number of bytes read

			// file output stream
			while ((nbytes = bStream.read(buffer)) != -1) { // Read from

				fileOutStream.write(buffer, 0, nbytes); // Write to file stream
			}

		} catch (FileNotFoundException e) {
			throw new TSApplicationException(e);
		} catch (Exception e) {
			throw new TSSystemException(e);
		} finally {
			try {
				bStream.close();
			} catch (Exception e) {
			}
			try {
				fileOutStream.close();
			} catch (Exception e) {
			}

		}

	}

	public static final boolean criarArquivo(String arquivo, String conteudo, String charset) {

		boolean criado = true;

		try {

			FileOutputStream fos = new FileOutputStream(arquivo);

			OutputStreamWriter osw = new OutputStreamWriter(fos, charset);

			BufferedWriter bw = new BufferedWriter(osw);

			bw.write(conteudo);

			bw.close();

			osw.close();

			fos.close();

		} catch (Exception e) {

			criado = false;

			e.printStackTrace();

		}

		return criado;

	}

	public static final List<String> listaArquivos(String pasta) {

		List<String> arquivos = new ArrayList<String>();

		File diretorio = new File(pasta);

		String[] subpastas = diretorio.list();

		if (subpastas != null) {

			for (int i = 0; i < subpastas.length; i++) {

				arquivos.add(subpastas[i]);

			}

		}

		return arquivos;

	}

	public static final boolean descompactar(String arquivo, String pasta) {

		final int BUFFER = 2048;

		try {

			FileInputStream fis = new FileInputStream(arquivo);

			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));

			ZipEntry entry;

			while ((entry = zis.getNextEntry()) != null) {

				int count;

				byte data[] = new byte[BUFFER];

				FileOutputStream fos = new FileOutputStream(pasta + entry.getName());

				BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

				while ((count = zis.read(data, 0, BUFFER)) != -1) {

					dest.write(data, 0, count);

				}

				dest.flush();

				dest.close();

			}

			zis.close();

			return true;

		} catch (Exception e) {

			return false;

		}

	}

	public static String obterExtensaoArquivo(String arquivo) {

		String extensao = "";

		if (!TSUtil.isEmpty(arquivo) && arquivo.indexOf(".") != -1) {

			extensao = arquivo.substring(arquivo.lastIndexOf("."), arquivo.length()).toLowerCase();

		}

		return extensao;

	}

}