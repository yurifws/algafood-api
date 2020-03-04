package com.algaworks.algafood.infraestructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Repository
public class PdfVendaReportServiceImpl implements VendaReportService {
	
	private static final String MSG_EMITIR_RELATORIO_VENDAS_DIARIAS = "Não foi possível emitir relatório de vendas diárias";

	@Autowired
	private VendaQueryService vendaQueryService;

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		try {
			var inputStream = this.getClass().getResourceAsStream(
					"/relatorios/vendas-diarias.jasper");
			var parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
			var vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
			var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
			var jasperPring = JasperFillManager.fillReport(inputStream, parameters, dataSource);
			return JasperExportManager.exportReportToPdf(jasperPring);
			
		} catch (Exception ex) {
			throw new ReportException(MSG_EMITIR_RELATORIO_VENDAS_DIARIAS, ex);
		}
	}

}
