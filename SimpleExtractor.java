package io.github.oliviercailloux.y2019.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * <p>
 * Extracts text from PDF. Uses a default {@link PDFTextStripper} if none is
 * provided. It is also possible to provide an instance of
 * {@link PDFTextStripper} (using {@link #setStripper(PDFTextStripper)}), in
 * which case that instance will be used instead of a default one.
 * </p>
 * <p>
 * Following the usual practice, this object does not close the writers it is
 * given.
 * </p>
 *
 * @author Olivier Cailloux
 *
 */
public interface SimpleExtractor {
	/**
	 * Sets the text stripper to use in place of the default one.
	 *
	 * @param stripper the stripper to use, <code>null</code> to use the default
	 *                 stripper
	 */
	public void setStripper(PDFTextStripper stripper);

	/**
	 * <p>
	 * Extracts text found in the given document, and writes it to the given
	 * <code>Writer</code>.
	 * </p>
	 *
	 * @param document the document to extract text from, if <code>null</code>,
	 *                 nothing is written
	 * @param output   the writer where content should be written, not
	 *                 <code>null</code> if <code>input</code> is not null,
	 *                 otherwise, may be <code>null</code>
	 * @throws IOException in case of a reading, parsing or writing error
	 * @see #setStripper(PDFTextStripper)
	 */
	public void writeText(PDDocument document, Writer output) throws IOException;

	/**
	 * <p>
	 * Extracts text found in the given input stream representing a non-encrypted
	 * PDF, and writes it to the given <code>Writer</code>.
	 * </p>
	 * <p>
	 * Uses the given stripper or a default one if none is provided.
	 * </p>
	 *
	 * @param input  the PDF byte stream, if <code>null</code>, nothing is written
	 * @param output the writer where content should be written, not
	 *               <code>null</code> if <code>input</code> is not null, otherwise,
	 *               may be <code>null</code>
	 * @throws InvalidPasswordException if the PDF required a non-empty password
	 * @throws IOException              in case of a reading, parsing or writing
	 *                                  error
	 * @see #setStripper(PDFTextStripper)
	 */
	public void writeText(InputStream input, Writer output) throws IOException;

	/**
	 * <p>
	 * For each path given as input, extracts the text from the corresponding file,
	 * assuming it is a PDF, and writes it to the given <code>Writer</code>. The
	 * text is written to the output with no separation indicating the boundaries of
	 * the given PDFs.
	 * </p>
	 *
	 * @param inputPaths not <code>null</code>, may be empty
	 * @param output     the writer where content should be written, not
	 *                   <code>null</code> if <code>input</code> is not empty,
	 *                   otherwise, may be <code>null</code>
	 * @throws InvalidPasswordException if a PDF required a non-empty password
	 * @throws IOException              in case of a reading, parsing or writing
	 *                                  error
	 * @see #setStripper(PDFTextStripper)
	 */
	public void writeAllText(Collection<Path> inputPaths, Writer output) throws IOException;

	/**
	 * <p>
	 * For each path given as input, extracts the text from the corresponding file,
	 * assuming it is a PDF.
	 * </p>
	 * <p>
	 * Uses the given stripper or a default one if none is provided.
	 * </p>
	 *
	 * @param inputPaths not <code>null</code>, may be empty
	 * @return a list containing as many entries as in the given collection, not
	 *         <code>null</code>, but may be empty
	 * 
	 * @throws InvalidPasswordException if a PDF required a non-empty password
	 * @throws IOException              in case of a reading or parsing error
	 * @see #setStripper(PDFTextStripper)
	 */
	public List<String> getAllText(Collection<Path> inputPaths) throws IOException;
}
