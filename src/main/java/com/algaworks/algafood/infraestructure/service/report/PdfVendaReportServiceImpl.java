package com.algaworks.algafood.infraestructure.service.report;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaReportService;

@Repository
public class PdfVendaReportServiceImpl implements VendaReportService{

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		return null;
	}

}
