package com.kiiik.minafilter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 
 * 编码解码器
 * @author YCL
 *
 */
public class StoreCodecFactory implements ProtocolCodecFactory{

	private final StoreEncoder encoder;
    private final StoreDecoder decoder;

    public StoreCodecFactory() {
        encoder = new StoreEncoder();
        decoder = new StoreDecoder();
    }

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return encoder;
	}

}
