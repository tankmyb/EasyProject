package com.netty.udp.split;

public class SubPack implements Comparable<SubPack>{

	private Integer seq;
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	private byte[] data;
	@Override
	public int compareTo(SubPack o) {
		// TODO 自动生成的方法存根
		return this.getSeq().compareTo(o.getSeq());

	} 
}
