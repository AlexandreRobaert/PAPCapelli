package br.com.capelli.centrodebelezacapelli.model.notafiscal.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import br.com.capelli.centrodebelezacapelli.model.notafiscal.RetornoNota;

public class RetornoNotaConverter implements Converter {

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz == RetornoNota.class;
	}

	@Override
	public void marshal(Object objeto, HierarchicalStreamWriter writer, MarshallingContext context) {

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		RetornoNota retorno = new RetornoNota();
        
		while (reader.hasMoreChildren()) {
            reader.moveDown();
            if("mensagem".equals(reader.getNodeName())){
            	reader.moveDown();
            	if ("codigo".equals(reader.getNodeName())) {
                    String codigo = (String)context.convertAnother(retorno, String.class);
                    retorno.setCodigo(codigo);
                    reader.moveUp();
            	}
            } else if ("alerta".equals(reader.getNodeName())) {
                String alerta = (String) context.convertAnother(retorno, String.class);
                retorno.setAlerta(alerta);
            } else if ("numero_nfse".equals(reader.getNodeName())) {
                Integer numeroNota = (Integer) context.convertAnother(retorno, Integer.class);
                retorno.setNumeroNota(numeroNota);
            } else if ("serie_nfse".equals(reader.getNodeName())) {
                Byte serieNota = (Byte) context.convertAnother(retorno, Byte.class);
                retorno.setSerieNota(serieNota);
            } else if ("data_nfse".equals(reader.getNodeName())) {
                String dataString = (String) context.convertAnother(retorno, String.class);
                SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendar = Calendar.getInstance();
                try {
					calendar.setTime(simple.parse(dataString));
				} catch (ParseException e) {
					e.printStackTrace();
				}
                retorno.setDataHoraNota(calendar);
                
            } else if ("hora_nfse".equals(reader.getNodeName())) {
                String horaString = (String) context.convertAnother(retorno, String.class);
                SimpleDateFormat simple = new SimpleDateFormat("HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                try {
                    calendar.setTime(simple.parse(horaString));
					calendar.set(retorno.getDataHoraNota().YEAR, retorno.getDataHoraNota().MONTH, 
							retorno.getDataHoraNota().DAY_OF_MONTH);
				} catch (ParseException e) {
					e.printStackTrace();
				}
                retorno.setDataHoraNota(calendar);
                
            } else if ("arquivo_gerador_nfse".equals(reader.getNodeName())) {
            	String nomeArquivoXML = (String) context.convertAnother(retorno, String.class);
                retorno.setNomeArquivoXML(nomeArquivoXML);
            } else if ("nome_arquivo_gerado_eletron".equals(reader.getNodeName())) {
                String nomeArquivoNoServidor = (String) context.convertAnother(retorno, String.class);
                retorno.setNomeArquivoNoServidor(nomeArquivoNoServidor);
            } else if ("link_nfse".equals(reader.getNodeName())) {
            	String linkNota = (String) context.convertAnother(retorno, String.class);
                retorno.setLinkNota(linkNota);
            } else if ("cod_verificador_autenticidade".equals(reader.getNodeName())) {
            	String codValidacaoNota = (String) context.convertAnother(retorno, String.class);
                retorno.setCodValidacaoNota(codValidacaoNota);
            }      
            reader.moveUp();
        }        
        return retorno;
	}
}
