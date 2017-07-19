package com.transprenciajoinville.diarioextractor;

import static java.lang.System.getProperty;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.transprenciajoinville.diarioextractor.domain.Indicacao;
import com.transprenciajoinville.diarioextractor.domain.Rua;
import com.transprenciajoinville.diarioextractor.domain.Vereador;
import com.transprenciajoinville.diarioextractor.indicacao.IndicacaoExtractor;
import com.transprenciajoinville.diarioextractor.pdfextractor.PDFToText;

// FIXME Make tests for more cases
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndicacaoExtractorImplTest {

	@Autowired
	@Qualifier("PDFToTextImpl")
	private PDFToText pdfExtractor;

	@Autowired
	@Qualifier("IndicacaoExtractorImpl")
	private IndicacaoExtractor indicacaoExtractor;

	@Test
	public void sample() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/main/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		
		Indicacao completeIndicacao = Indicacao.builder().number("8422/2017") //
				.vereadores(asList(Vereador.builder().name("Pelé").build())) //
				.descricao("Substituição de tampa da boca de lobo na Rua Antônio do Livramento, em frente ao Nº 284, no Bairro Espinheiros.") //
				.ruas(asList(Rua.builder().name("Antônio do Livramento").build())) //
				.bairro("Espinheiros") //
				.build();

		assertThat(indicacoes.size(), equalTo(170));
		
		assertThat(indicacoes.get(6).equals(completeIndicacao), equalTo(true));
	}
	
	@Test
	public void withAccentuation() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/main/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		
		Indicacao completeIndicacao = Indicacao.builder().number("8552/2017") //
				.vereadores(asList(Vereador.builder().name("Wilson Paraiba").build())) //
				.descricao("Operação tapa-buraco na Rua dos Tucanos, nas proximidades do nº 392 e 408, no Bairro Jardim Iririu.") //
				.ruas(asList(Rua.builder().name("dos Tucanos").build())) //
				.bairro("Jardim Iririú") //
				.build();

		assertThat(indicacoes.size(), equalTo(170));
		
		assertThat(indicacoes.get(21).equals(completeIndicacao), equalTo(true));
	}
}
