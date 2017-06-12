package br.com.capelli.centrodebelezacapelli.model.notafiscal;

import android.content.Context;
import android.graphics.Path;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import br.com.capelli.centrodebelezacapelli.model.notafiscal.converters.ItemConverter;
import br.com.capelli.centrodebelezacapelli.model.notafiscal.converters.NfEnvioConverter;
import br.com.capelli.centrodebelezacapelli.model.notafiscal.converters.RetornoNotaConverter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestaXML {

	static final String URL = "http://sync.nfs-e.net/datacenter/include/nfw/importa_nfw/nfw_import_upload.php?eletron=1";

    public static RetornoNota enviaNota(String conteudoXML, XStream xstream, Context context) throws IOException{

        final MediaType mediaType = MediaType.parse("application/xml; charset=utf-8");

        String stringConteudo = "<?xml version='1.0' encoding='iso-8859-1'?>\n".concat(conteudoXML);

        File file = new File(context.getFilesDir(), "notaEnvio.xml");

        FileOutputStream outputStream = context.openFileOutput("notaEnvio.xml", Context.MODE_PRIVATE);
        outputStream.write(stringConteudo.getBytes());
        outputStream.close();

        /*OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("login", "15425039000128")
                .addFormDataPart("senha", "154250")
                .addFormDataPart("cidade", "7513")
                .addFormDataPart("f1", outputStream.,
                        RequestBody.create(mediaType, new File("")))
                .build();

        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
            return (RetornoNota) xstream.fromXML(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

		*/
        return new RetornoNota();
	}
	
	public static void main(String[] args) throws IOException {
		
		Nf nf = new Nf();
		nf.setObservacoes("Serviços prestados ao salão de beleza");
		nf.setValorcofins(0);
		nf.setValorContribuicaoSocial(0);
		nf.setValorDesconto(10);
		nf.setValorinss(0);
		nf.setValorir(0);
		nf.setValorpis(0);
		nf.setValorrps(0);
		
		Prestador prestador = new Prestador();
		prestador.setCpfCnpj("15425039000128");
		
		Tomador tomador = new Tomador();
		tomador.setTipo('F');
		
		Item item1 = new Item();
		item1.setDescricaoServico("Manicure");
		item1.setValorItem(15.00);
		item1.setQuantidadeItem(1);
		
		Item item2 = new Item();
		item2.setDescricaoServico("Pintura de cabelo com mechas");
		item2.setValorItem(15.00);
		item2.setQuantidadeItem(2);
		
		List<Item> itens = new ArrayList<>();
		itens.add(item1);
		itens.add(item2);
		
		double valor = 0;
		for (Item item : itens) {
			valor += item.getValorTributavel();
		}
		nf.setValorTotal(valor);
		
		Nfse nota = new Nfse();
		nota.setNf(nf);
		nota.setPrestador(prestador);
		nota.setTomador(tomador);
		nota.setItens(itens);
		
		XmlFriendlyNameCoder replacer = new XmlFriendlyNameCoder("ddd", "_");
		XStream xstream = new XStream(new DomDriver("UTF-8", replacer));
		xstream.processAnnotations(Nfse.class);
		xstream.processAnnotations(Nf.class);
		xstream.processAnnotations(Item.class);
		xstream.processAnnotations(Prestador.class);
		xstream.processAnnotations(Tomador.class);
		xstream.processAnnotations(RetornoNota.class);
		xstream.registerConverter(new ItemConverter());
		xstream.registerConverter(new RetornoNotaConverter());
		xstream.registerConverter(new NfEnvioConverter());
	
		String xml = xstream.toXML(nota);
		//RetornoNota retornoNota = enviaNota(xml, xstream);
		//nota.setRetornoNota(retornoNota);
		
		System.out.println("Nota Número " + nota.getRetornoNota().getNumeroNota() + " emitida com sucesso!");
		
	}
}
