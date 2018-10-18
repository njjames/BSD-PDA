package org.aotu;

import org.nutz.dao.pager.Pager;

public class offsetPager extends Pager {
	
	private static final long serialVersionUID = -1385308131663113162L;
	
	protected int offset = -1;
	
	protected offsetPager() {
	}

	/**
	 * 构建一个指定偏移量及大小的Pager
	 * @param offset 偏移量
	 * @param size 数据大小
	 */
	public offsetPager(int offset, int size) {
		super();
		this.offset = offset;
		setPageSize(size);
	}

	@Override
	public int getOffset() {
		if (offset > -1)
			return offset;
		return super.getOffset();
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
}