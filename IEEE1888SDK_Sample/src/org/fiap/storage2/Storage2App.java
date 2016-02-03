/*
 * Copyright (c) 2013 Hideya Ochiai, the University of Tokyo,  All rights reserved.
 * 
 * Permission of redistribution and use in source and binary forms, 
 * with or without modification, are granted, free of charge, to any person 
 * obtaining the copy of this software under the following conditions:
 * 
 *  1. Any copies of this source code must include the above copyright notice,
 *  this permission notice and the following statement without modification 
 *  except possible additions of other copyright notices. 
 * 
 *  2. Redistributions of the binary code must involve the copy of the above 
 *  copyright notice, this permission notice and the following statement 
 *  in documents and/or materials provided with the distribution.  
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.fiap.storage2;

import org.w3c.dom.Element;

import org.fiap.common.PointBusObserver;
import org.fiap.common.PointData;


/**
 * @author Hideya Ochiai
 * @since 2011-05-21
 */
public class Storage2App extends PointBusObserver {
	
	/**
	 * FETCHCopyThread �I�u�W�F�N�g(����)�̕ێ��p
	 */
	private FETCHCopyThread[] m_FETCHCopyThreads;
	
	/**
	  * WRITECopyThread �I�u�W�F�N�g(����)�̕ێ��p
	  */
	private WRITECopyThread[] m_WRITECopyThreads;

	/**
	 * �R���X�g���N�^<br />
	 * (*) ��ɁAStorageAdmin������ݒ�t�@�C����ǂݍ���ł���K�v������<br />
	 * <br />
	 * @throws Storage2ConfigException
	 */
	protected Storage2App() throws Storage2ConfigException, org.apache.axis2.databinding.types.URI.MalformedURIException {
		super();
		
		Storage2Admin admin=Storage2Admin.getInstance();
		
		Element[] fetchs=admin.getFETCHs();
		m_FETCHCopyThreads=new FETCHCopyThread[fetchs.length];
		for(int i=0;i<fetchs.length;i++){
			m_FETCHCopyThreads[i]=new FETCHCopyThread(fetchs[i],this);			
		}

		Element[] writes=admin.getWRITEs();
		m_WRITECopyThreads=new WRITECopyThread[writes.length];
		for(int i=0;i<writes.length;i++){
			m_WRITECopyThreads[i]=new WRITECopyThread(writes[i],this);			
		}
	}
	
	/**
	 * PointBus ����@�f�[�^�������̓R�}���h�̍X�V�M�� ����M����<br />
	 * StorageApp �͉����A�N�V�������N�����Ȃ�
	 */
	@Override	
	public void recvUpdate(PointData o) {
		
	}
}
