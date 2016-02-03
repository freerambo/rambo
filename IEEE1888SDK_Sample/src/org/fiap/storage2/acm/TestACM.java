package org.fiap.storage2.acm;

import org.apache.axis2.databinding.types.URI;
import org.fiap.storage2.Storage2Admin;

public class TestACM {


	public static void main(String[] args) throws Exception {

		
		Storage2Admin admin=Storage2Admin.getInstance();
		admin.parseConfigFile("config/storage2/storage2.xml");
		
		ACM acm=ACM.getInstance();
		
		String[] target_ids=new String[]{
				"http://gutp.jp/Arduino/001/Temperature",	
				"http://gutp.jp/Arduino/001/Illuminance",	
				"http://gutp.jp/Arduino/001/DIPSW",	
				"http://gutp.jp/Arduino/001/TGLSW"	
		};
		
		try{
			if(acm.checkAccessControlPolicy("jo2lxq@hongo.wide.ad.jp", "127.0.0.1", "query", target_ids)){
				System.out.println("Passed..");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		org.fiap.types.PointSet[] ps=null;
		org.fiap.types.Point[] p=new org.fiap.types.Point[13];
		for(int i=0;i<p.length;i++){
			p[i]=new org.fiap.types.Point(); 
		}
		org.fiap.types.Value v=null;
		
		
		p[0].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/number_of_roledefs"));
		v=new org.fiap.types.Value(); v.setString("1"); p[0].addValue(v);
		p[1].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/roledef0/name"));
		v=new org.fiap.types.Value(); v.setString("newuser"); p[1].addValue(v);
		p[2].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/roledef0/number_of_ids"));
		v=new org.fiap.types.Value(); v.setString("2"); p[2].addValue(v);
		p[3].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/roledef0/id0"));
		v=new org.fiap.types.Value(); v.setString("jo2lxq@hongo.wide.ad.jp"); p[3].addValue(v);
		p[4].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/roledef0/id1"));
		v=new org.fiap.types.Value(); v.setString("jo2@hongo.wide.ad.jp"); p[4].addValue(v);
		
		p[5].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/number_of_roles"));
		v=new org.fiap.types.Value(); v.setString("1"); p[5].addValue(v);
		p[6].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/role0/name"));
		v=new org.fiap.types.Value(); v.setString("newuser"); p[6].addValue(v);
		p[7].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/role0/number_of_rules"));
		v=new org.fiap.types.Value(); v.setString("1"); p[7].addValue(v);
		p[8].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/role0/rule0/policy"));
		v=new org.fiap.types.Value(); v.setString("allow"); p[8].addValue(v);
		p[9].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/role0/rule0/method"));
		v=new org.fiap.types.Value(); v.setString("data"); p[9].addValue(v);
		p[10].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/role0/rule0/number_of_points"));
		v=new org.fiap.types.Value(); v.setString("2"); p[10].addValue(v);
		p[11].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/role0/rule0/point0"));
		v=new org.fiap.types.Value(); v.setString("http://gutp.jp/Hello0/*"); p[11].addValue(v);
		p[12].setId(new URI("http://jo2lxq.hongo.wide.ad.jp/.meta/acl/role0/rule0/point1"));
		v=new org.fiap.types.Value(); v.setString("http://gutp.jp/Hello1/Temperature"); p[12].addValue(v);
		
		// ACM.getInstance().dataPreProcessor(ps, p);  removed (because of the removal of Dynamic ACL configuration function)
		
		ACM.getInstance().dumpACL();
		
	}

}
