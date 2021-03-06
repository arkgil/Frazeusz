package pl.edu.agh.ki.frazeusz.parser;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import pl.edu.agh.ki.frazeusz.parser.helpers.UrlContent;

/**
 *
 * @author Micha� Zgli�ski
 */
public class DocxParser extends AbstractParser{

	protected List<String> validMimeTypes = Arrays.asList(
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
	);

	public DocxParser(ITargetedParser nextParser) {
		super(nextParser);
	}

	@Override
	void parseInternal(UrlContent url) {
		try {
			XWPFDocument docx = new XWPFDocument(new ByteArrayInputStream(url.content.getBytes(StandardCharsets.UTF_8)));
			XWPFWordExtractor we = new XWPFWordExtractor(docx);
			url.content = we.getText();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
