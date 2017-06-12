package br.com.capelli.centrodebelezacapelli.model.notafiscal.converters;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import br.com.capelli.centrodebelezacapelli.model.notafiscal.Nfse;

public class NFConsultaConverter implements Converter {

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz == Nfse.class;
	}

	@Override
	public void marshal(Object objeto, HierarchicalStreamWriter writer, MarshallingContext context) {
		Nfse nota = (Nfse) objeto;
		
		writer.startNode("pesquisa");
			writer.startNode("codigo_autenticidade");
			context.convertAnother(nota.getRetornoNota().getCodValidacaoNota());
			writer.endNode();
			
			writer.startNode("numero");
			context.convertAnother(nota.getRetornoNota().getNumeroNota());
			writer.endNode();
			
			writer.startNode("serie");
			context.convertAnother(nota.getRetornoNota().getSerieNota());
			writer.endNode();
			
			writer.startNode("cadastro");
			context.convertAnother("");
			writer.endNode();
			
		writer.endNode();

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
