package org.fiap.storage2;

import org.fiap.types.PointSet;
import org.fiap.types.Point;
import org.fiap.types.Value;

import org.fiap.types.Key;
import org.fiap.types.Query;
import org.fiap.types.Header;
import org.fiap.types.Body;
import org.fiap.types.Transport;

import org.fiap.types.QueryType;
import org.fiap.types.AttrNameType;
import org.fiap.types.Uuid;

import org.fiap.soap.QueryRQ;
import org.fiap.soap.QueryRS;
import org.fiap.soap.DataRQ;
import org.fiap.soap.DataRS;

import org.w3c.dom.Document;

public class TestStorage2QueryApp {

	public static void main(String[] args) throws Exception {
		
		Document doc=org.fiap.util.Xml.newDocument();
		
		Key key=new Key();
		key.setId(new org.apache.axis2.databinding.types.URI("http://gw.hogehoge/obix/ut_hongo/t2/102B2/VFAN/ALARM"));
		key.setAttrName(AttrNameType.time);
		key.setLt("2009-11-20T01:41:00.0000000+09:00");
		
		Key key2=new Key();
		key2.setId(new org.apache.axis2.databinding.types.URI("http://gw.hogehoge/obix/ut_hongo/t2/102B2/VFAN/SWCfb"));
		key2.setAttrName(AttrNameType.time);
		key2.setLt("2009-11-20T01:41:00.0000000+09:00");

		Query query=new Query();
		Uuid id=new Uuid();
		id.setUuid(java.util.UUID.randomUUID().toString());
		query.setId(id);		
		query.setAcceptableSize(new org.apache.axis2.databinding.types.PositiveInteger("20"));
		query.setType(QueryType.storage);
		query.addKey(key);
		query.addKey(key2);
		
		Header header=new Header();
		header.setQuery(query);
		
		Transport request=new Transport();
		request.setHeader(header);

		Storage2 ws=new Storage2();
		
		QueryRQ queryRQ0=new QueryRQ();
		queryRQ0.setTransport(request);
		QueryRS queryRS0=ws.query(queryRQ0);
		Transport mediate0=queryRS0.getTransport();
		
	/*	QueryRQ queryRQ1=new QueryRQ();
		queryRQ1.setT(mediate0);
		QueryRS queryRS1=ws.query(queryRQ1);
		Transport mediate1=queryRS1.getQueryResult();

		QueryRQ queryRQ2=new QueryRQ();
		queryRQ2.setT(mediate1);
		QueryRS queryRS2=ws.query(queryRQ2);
		Transport mediate2=queryRS2.getQueryResult();

		QueryRQ queryRQ3=new QueryRQ();
		queryRQ3.setT(mediate2);
		QueryRS queryRS3=ws.query(queryRQ3);
		Transport mediate3=queryRS3.getQueryResult();

		QueryRQ queryRQ4=new QueryRQ();
		queryRQ4.setT(mediate2);
		QueryRS queryRS4=ws.query(queryRQ4);
		Transport response=queryRS4.getQueryResult(); */
				
		System.out.println(request.toString());
	/*	System.out.println(mediate0.toString());
		System.out.println(mediate1.toString());
		System.out.println(mediate2.toString());
		System.out.println(mediate3.toString());
		System.out.println(response.toString()); */

	}

}
